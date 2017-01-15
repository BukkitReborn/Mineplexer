package org.jooq.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InsertQuery;
import org.jooq.Record;
import org.jooq.Row;
import org.jooq.StoreQuery;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableRecord;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.tools.JooqLogger;

public class TableRecordImpl<R extends TableRecord<R>>
  extends AbstractRecord
  implements TableRecord<R>
{
  private static final long serialVersionUID = 3216746611562261641L;
  private static final JooqLogger log = JooqLogger.getLogger(TableRecordImpl.class);
  private final Table<R> table;
  
  public TableRecordImpl(Table<R> table)
  {
    super(table.fields());
    
    this.table = table;
  }
  
  public final Table<R> getTable()
  {
    return this.table;
  }
  
  public Row fieldsRow()
  {
    return this.fields;
  }
  
  public Row valuesRow()
  {
    return new RowImpl(Utils.fields(intoArray(), this.fields.fields.fields()));
  }
  
  public final R original()
  {
    return (TableRecord)super.original();
  }
  
  public final <O extends UpdatableRecord<O>> O fetchParent(ForeignKey<R, O> key)
  {
    return (UpdatableRecord)key.fetchParent(this);
  }
  
  public final int insert()
  {
    return insert(this.fields.fields.fields);
  }
  
  public final int insert(Field<?>... storeFields)
  {
    return storeInsert(storeFields);
  }
  
  final int storeInsert(final Field<?>[] storeFields)
  {
    final int[] result = new int[1];
    
    RecordDelegate.delegate(configuration(), this, RecordDelegate.RecordLifecycleType.INSERT)
      .operate(new RecordOperation()
      {
        public Record operate(Record record)
          throws RuntimeException
        {
          result[0] = TableRecordImpl.this.storeInsert0(storeFields);
          return record;
        }
      });
    return result[0];
  }
  
  final int storeInsert0(Field<?>[] storeFields)
  {
    DSLContext create = create();
    InsertQuery<R> insert = create.insertQuery(getTable());
    addChangedValues(storeFields, insert);
    if (!insert.isExecutable())
    {
      if (log.isDebugEnabled()) {
        log.debug("Query is not executable", insert);
      }
      return 0;
    }
    BigInteger version = addRecordVersion(insert);
    Timestamp timestamp = addRecordTimestamp(insert);
    
    Collection<Field<?>> key = null;
    if (!Boolean.TRUE.equals(create.configuration().data("org.jooq.configuration.omit-returning-clause")))
    {
      key = getReturning();
      insert.setReturning(key);
    }
    int result = insert.execute();
    if (result > 0)
    {
      setRecordVersionAndTimestamp(version, timestamp);
      Object localObject1;
      if ((key != null) && (!key.isEmpty()) && 
        (insert.getReturnedRecord() != null)) {
        for (localObject1 = key.iterator(); ((Iterator)localObject1).hasNext();)
        {
          field = (Field)((Iterator)localObject1).next();
          index = Utils.indexOrFail(fieldsRow(), field);
          Object value = ((TableRecord)insert.getReturnedRecord()).getValue(field);
          
          this.values[index] = value;
          this.originals[index] = value;
        }
      }
      Field<?> field;
      int index;
      for (Field<?> storeField : storeFields) {
        changed(storeField, false);
      }
      this.fetched = true;
    }
    return result;
  }
  
  final void setRecordVersionAndTimestamp(BigInteger version, Timestamp timestamp)
  {
    if (version != null)
    {
      TableField<R, ?> field = getTable().getRecordVersion();
      int fieldIndex = Utils.indexOrFail(fieldsRow(), field);
      Object value = field.getDataType().convert(version);
      
      this.values[fieldIndex] = value;
      this.originals[fieldIndex] = value;
      this.changed.clear(fieldIndex);
    }
    if (timestamp != null)
    {
      TableField<R, ?> field = getTable().getRecordTimestamp();
      int fieldIndex = Utils.indexOrFail(fieldsRow(), field);
      Object value = field.getDataType().convert(timestamp);
      
      this.values[fieldIndex] = value;
      this.originals[fieldIndex] = value;
      this.changed.clear(fieldIndex);
    }
  }
  
  final void addChangedValues(Field<?>[] storeFields, StoreQuery<R> query)
  {
    Fields<Record> f = new Fields(storeFields);
    for (Field<?> field : this.fields.fields.fields) {
      if ((changed(field)) && (f.field(field) != null)) {
        addValue(query, field);
      }
    }
  }
  
  final <T> void addValue(StoreQuery<?> store, Field<T> field, Object value)
  {
    store.addValue(field, Utils.field(value, field));
  }
  
  final <T> void addValue(StoreQuery<?> store, Field<T> field)
  {
    addValue(store, field, getValue(field));
  }
  
  final Timestamp addRecordTimestamp(StoreQuery<?> store)
  {
    Timestamp result = null;
    if (isTimestampOrVersionAvailable())
    {
      TableField<R, ? extends Date> timestamp = getTable().getRecordTimestamp();
      if (timestamp != null)
      {
        result = new Timestamp(System.currentTimeMillis());
        addValue(store, timestamp, result);
      }
    }
    return result;
  }
  
  final BigInteger addRecordVersion(StoreQuery<?> store)
  {
    BigInteger result = null;
    if (isTimestampOrVersionAvailable())
    {
      TableField<R, ? extends Number> version = getTable().getRecordVersion();
      if (version != null)
      {
        Number value = (Number)getValue(version);
        if (value == null) {
          result = BigInteger.ONE;
        } else {
          result = new BigInteger(value.toString()).add(BigInteger.ONE);
        }
        addValue(store, version, result);
      }
    }
    return result;
  }
  
  final boolean isTimestampOrVersionAvailable()
  {
    return (getTable().getRecordTimestamp() != null) || (getTable().getRecordVersion() != null);
  }
  
  final Collection<Field<?>> getReturning()
  {
    Collection<Field<?>> result = new LinkedHashSet();
    
    Identity<R, ?> identity = getTable().getIdentity();
    if (identity != null) {
      result.add(identity.getField());
    }
    UniqueKey<?> key = getPrimaryKey();
    if (key != null) {
      result.addAll(key.getFields());
    }
    return result;
  }
}
