package org.jooq.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import org.jooq.ConditionProvider;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DeleteQuery;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectQuery;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableRecord;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.UpdateQuery;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataChangedException;
import org.jooq.exception.InvalidResultException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

public class UpdatableRecordImpl<R extends UpdatableRecord<R>>
  extends TableRecordImpl<R>
  implements UpdatableRecord<R>
{
  private static final long serialVersionUID = -1012420583600561579L;
  private static final JooqLogger log = JooqLogger.getLogger(UpdatableRecordImpl.class);
  
  public UpdatableRecordImpl(Table<R> table)
  {
    super(table);
  }
  
  public Record key()
  {
    RecordImpl result = new RecordImpl(getPrimaryKey().getFields());
    result.setValues(result.fields.fields.fields, this);
    return result;
  }
  
  public final <O extends TableRecord<O>> O fetchChild(ForeignKey<O, R> key)
  {
    return (TableRecord)Utils.filterOne(fetchChildren(key));
  }
  
  public final <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> key)
  {
    return key.fetchChildren(this);
  }
  
  final UniqueKey<R> getPrimaryKey()
  {
    return getTable().getPrimaryKey();
  }
  
  public final int store()
  {
    return store(this.fields.fields.fields);
  }
  
  public final int store(final Field<?>... storeFields)
    throws DataAccessException, DataChangedException
  {
    final int[] result = new int[1];
    
    RecordDelegate.delegate(configuration(), this, RecordDelegate.RecordLifecycleType.STORE)
      .operate(new RecordOperation()
      {
        public Record operate(Record record)
          throws RuntimeException
        {
          result[0] = UpdatableRecordImpl.this.store0(storeFields);
          return record;
        }
      });
    return result[0];
  }
  
  public final int update()
  {
    return update(this.fields.fields.fields);
  }
  
  public int update(Field<?>... storeFields)
    throws DataAccessException, DataChangedException
  {
    return storeUpdate(storeFields, getPrimaryKey().getFieldsArray());
  }
  
  private final int store0(Field<?>[] storeFields)
  {
    TableField<R, ?>[] keys = getPrimaryKey().getFieldsArray();
    boolean executeUpdate = false;
    if (SettingsTools.updatablePrimaryKeys(Utils.settings(this))) {
      executeUpdate = this.fetched;
    } else {
      for (TableField<R, ?> field : keys)
      {
        if ((changed(field)) || (
        
          (!field.getDataType().nullable()) && (getValue(field) == null)))
        {
          executeUpdate = false;
          break;
        }
        executeUpdate = true;
      }
    }
    int result = 0;
    if (executeUpdate) {
      result = storeUpdate(storeFields, keys);
    } else {
      result = storeInsert(storeFields);
    }
    return result;
  }
  
  private final int storeUpdate(final Field<?>[] storeFields, final TableField<R, ?>[] keys)
  {
    final int[] result = new int[1];
    
    RecordDelegate.delegate(configuration(), this, RecordDelegate.RecordLifecycleType.UPDATE)
      .operate(new RecordOperation()
      {
        public Record operate(Record record)
          throws RuntimeException
        {
          result[0] = UpdatableRecordImpl.this.storeUpdate0(storeFields, keys);
          return record;
        }
      });
    return result[0];
  }
  
  private final int storeUpdate0(Field<?>[] storeFields, TableField<R, ?>[] keys)
  {
    UpdateQuery<R> update = create().updateQuery(getTable());
    addChangedValues(storeFields, update);
    Utils.addConditions(update, this, keys);
    if (!update.isExecutable())
    {
      if (log.isDebugEnabled()) {
        log.debug("Query is not executable", update);
      }
      return 0;
    }
    BigInteger version = addRecordVersion(update);
    Timestamp timestamp = addRecordTimestamp(update);
    if (isExecuteWithOptimisticLocking()) {
      if (isTimestampOrVersionAvailable()) {
        addConditionForVersionAndTimestamp(update);
      } else {
        checkIfChanged(keys);
      }
    }
    int result = update.execute();
    checkIfChanged(result, version, timestamp);
    if (result > 0) {
      for (Field<?> storeField : storeFields) {
        changed(storeField, false);
      }
    }
    return result;
  }
  
  public final int delete()
  {
    final int[] result = new int[1];
    
    RecordDelegate.delegate(configuration(), this, RecordDelegate.RecordLifecycleType.DELETE)
      .operate(new RecordOperation()
      {
        public Record operate(Record record)
          throws RuntimeException
        {
          result[0] = UpdatableRecordImpl.this.delete0();
          return record;
        }
      });
    return result[0];
  }
  
  private final int delete0()
  {
    TableField<R, ?>[] keys = getPrimaryKey().getFieldsArray();
    try
    {
      DeleteQuery<R> delete1 = create().deleteQuery(getTable());
      Utils.addConditions(delete1, this, keys);
      if (isExecuteWithOptimisticLocking()) {
        if (isTimestampOrVersionAvailable()) {
          addConditionForVersionAndTimestamp(delete1);
        } else {
          checkIfChanged(keys);
        }
      }
      int result = delete1.execute();
      checkIfChanged(result, null, null);
      return result;
    }
    finally
    {
      changed(true);
      this.fetched = false;
    }
  }
  
  public final void refresh()
  {
    refresh(this.fields.fields.fields);
  }
  
  public final void refresh(final Field<?>... refreshFields)
  {
    SelectQuery<Record> select = create().selectQuery();
    select.addSelect(refreshFields);
    select.addFrom(getTable());
    Utils.addConditions(select, this, getPrimaryKey().getFieldsArray());
    if (select.execute() == 1)
    {
      final AbstractRecord source = (AbstractRecord)select.getResult().get(0);
      
      RecordDelegate.delegate(configuration(), this, RecordDelegate.RecordLifecycleType.REFRESH)
        .operate(new RecordOperation()
        {
          public Record operate(Record record)
            throws RuntimeException
          {
            UpdatableRecordImpl.this.setValues(refreshFields, source);
            return record;
          }
        });
    }
    else
    {
      throw new InvalidResultException("Exactly one row expected for refresh. Record does not exist in database.");
    }
  }
  
  public final R copy()
  {
    (UpdatableRecord)Utils.newRecord(false, getTable(), configuration()).operate(new RecordOperation()
    {
      public R operate(R copy)
        throws RuntimeException
      {
        List<TableField<R, ?>> key = UpdatableRecordImpl.this.getPrimaryKey().getFields();
        for (Field<?> field : UpdatableRecordImpl.this.fields.fields.fields) {
          if (!key.contains(field)) {
            setValue(copy, field);
          }
        }
        return copy;
      }
      
      private final <T> void setValue(Record record, Field<T> field)
      {
        record.setValue(field, UpdatableRecordImpl.this.getValue(field));
      }
    });
  }
  
  private final boolean isExecuteWithOptimisticLocking()
  {
    Configuration configuration = configuration();
    if (configuration != null) {
      return Boolean.TRUE.equals(configuration.settings().isExecuteWithOptimisticLocking());
    }
    return false;
  }
  
  private final void addConditionForVersionAndTimestamp(ConditionProvider query)
  {
    TableField<R, ?> v = getTable().getRecordVersion();
    TableField<R, ?> t = getTable().getRecordTimestamp();
    if (v != null) {
      Utils.addCondition(query, this, v);
    }
    if (t != null) {
      Utils.addCondition(query, this, t);
    }
  }
  
  private final void checkIfChanged(TableField<R, ?>[] keys)
  {
    SelectQuery<R> select = create().selectQuery(getTable());
    Utils.addConditions(select, this, keys);
    if (!Arrays.asList(new SQLDialect[] { SQLDialect.SQLITE }).contains(create().configuration().dialect().family())) {
      select.setForUpdate(true);
    }
    R record = (UpdatableRecord)select.fetchOne();
    if (record == null) {
      throw new DataChangedException("Database record no longer exists");
    }
    for (Field<?> field : this.fields.fields.fields)
    {
      Object thisObject = original(field);
      Object thatObject = record.original(field);
      if (!StringUtils.equals(thisObject, thatObject)) {
        throw new DataChangedException("Database record has been changed");
      }
    }
  }
  
  private final void checkIfChanged(int result, BigInteger version, Timestamp timestamp)
  {
    if (result > 0) {
      setRecordVersionAndTimestamp(version, timestamp);
    } else if (isExecuteWithOptimisticLocking()) {
      throw new DataChangedException("Database record has been changed or doesn't exist any longer");
    }
  }
}
