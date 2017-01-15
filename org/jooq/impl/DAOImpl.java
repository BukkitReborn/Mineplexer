package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.jooq.Batch;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DAO;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DeleteConditionStep;
import org.jooq.DeleteWhereStep;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.jooq.RecordMapperProvider;
import org.jooq.Result;
import org.jooq.RowN;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.conf.Settings;

public abstract class DAOImpl<R extends UpdatableRecord<R>, P, T>
  implements DAO<R, P, T>
{
  private final Table<R> table;
  private final Class<P> type;
  private RecordMapper<R, P> mapper;
  private Configuration configuration;
  
  protected DAOImpl(Table<R> table, Class<P> type)
  {
    this(table, type, null);
  }
  
  protected DAOImpl(Table<R> table, Class<P> type, Configuration configuration)
  {
    this.table = table;
    this.type = type;
    
    setConfiguration(configuration);
  }
  
  public final void setConfiguration(Configuration configuration)
  {
    this.configuration = configuration;
    this.mapper = Utils.configuration(configuration).recordMapperProvider().provide(this.table.recordType(), this.type);
  }
  
  public final Configuration configuration()
  {
    return this.configuration;
  }
  
  public final Settings settings()
  {
    return Utils.settings(configuration());
  }
  
  public final SQLDialect dialect()
  {
    return Utils.configuration(configuration()).dialect();
  }
  
  public final SQLDialect family()
  {
    return dialect().family();
  }
  
  public RecordMapper<R, P> mapper()
  {
    return this.mapper;
  }
  
  public final void insert(P object)
  {
    insert(Collections.singletonList(object));
  }
  
  public final void insert(P... objects)
  {
    insert(Arrays.asList(objects));
  }
  
  public final void insert(Collection<P> objects)
  {
    if (objects.size() > 1) {
      DSL.using(this.configuration).batchInsert(records(objects, false)).execute();
    } else if (objects.size() == 1) {
      ((UpdatableRecord)records(objects, false).get(0)).insert();
    }
  }
  
  public final void update(P object)
  {
    update(Collections.singletonList(object));
  }
  
  public final void update(P... objects)
  {
    update(Arrays.asList(objects));
  }
  
  public final void update(Collection<P> objects)
  {
    if (objects.size() > 1) {
      DSL.using(this.configuration).batchUpdate(records(objects, true)).execute();
    } else if (objects.size() == 1) {
      ((UpdatableRecord)records(objects, true).get(0)).update();
    }
  }
  
  public final void delete(P... objects)
  {
    delete(Arrays.asList(objects));
  }
  
  public final void delete(Collection<P> objects)
  {
    List<T> ids = new ArrayList();
    for (P object : objects) {
      ids.add(getId(object));
    }
    deleteById(ids);
  }
  
  public final void deleteById(T... ids)
  {
    deleteById(Arrays.asList(ids));
  }
  
  public final void deleteById(Collection<T> ids)
  {
    Field<?>[] pk = pk();
    if (pk != null) {
      DSL.using(this.configuration).delete(this.table).where(new Condition[] { equal(pk, ids) }).execute();
    }
  }
  
  public final boolean exists(P object)
  {
    return existsById(getId(object));
  }
  
  public final boolean existsById(T id)
  {
    Field<?>[] pk = pk();
    if (pk != null) {
      return ((Integer)DSL.using(this.configuration).selectCount().from(this.table).where(new Condition[] { equal(pk, id) }).fetchOne(0, Integer.class)).intValue() > 0;
    }
    return false;
  }
  
  public final long count()
  {
    return ((Long)DSL.using(this.configuration).selectCount().from(this.table).fetchOne(0, Long.class)).longValue();
  }
  
  public final List<P> findAll()
  {
    return DSL.using(this.configuration).selectFrom(this.table).fetch().map(mapper());
  }
  
  public final P findById(T id)
  {
    Field<?>[] pk = pk();
    R record = null;
    if (pk != null) {
      record = (UpdatableRecord)DSL.using(this.configuration).selectFrom(this.table).where(new Condition[] { equal(pk, id) }).fetchOne();
    }
    return record == null ? null : mapper().map(record);
  }
  
  public final <Z> List<P> fetch(Field<Z> field, Z... values)
  {
    return DSL.using(this.configuration).selectFrom(this.table).where(new Condition[] { field.in(values) }).fetch().map(mapper());
  }
  
  public final <Z> P fetchOne(Field<Z> field, Z value)
  {
    R record = (UpdatableRecord)DSL.using(this.configuration).selectFrom(this.table).where(new Condition[] { field.equal(value) }).fetchOne();
    
    return record == null ? null : mapper().map(record);
  }
  
  public final Table<R> getTable()
  {
    return this.table;
  }
  
  public final Class<P> getType()
  {
    return this.type;
  }
  
  protected abstract T getId(P paramP);
  
  protected final T compositeKeyRecord(Object... values)
  {
    UniqueKey<R> key = this.table.getPrimaryKey();
    if (key == null) {
      return null;
    }
    TableField<R, Object>[] fields = (TableField[])key.getFieldsArray();
    
    Record result = DSL.using(this.configuration).newRecord(fields);
    for (int i = 0; i < values.length; i++) {
      result.setValue(fields[i], fields[i].getDataType().convert(values[i]));
    }
    return result;
  }
  
  private final Condition equal(Field<?>[] pk, T id)
  {
    if (pk.length == 1) {
      return pk[0].equal(pk[0].getDataType().convert(id));
    }
    return DSL.row(pk).equal((Record)id);
  }
  
  private final Condition equal(Field<?>[] pk, Collection<T> ids)
  {
    if (pk.length == 1)
    {
      if (ids.size() == 1) {
        return equal(pk, ids.iterator().next());
      }
      return pk[0].in(pk[0].getDataType().convert(ids));
    }
    return DSL.row(pk).in((Record[])ids.toArray(new Record[ids.size()]));
  }
  
  private final Field<?>[] pk()
  {
    UniqueKey<?> key = this.table.getPrimaryKey();
    return key == null ? null : key.getFieldsArray();
  }
  
  private final List<R> records(Collection<P> objects, boolean forUpdate)
  {
    List<R> result = new ArrayList();
    Field<?>[] pk = pk();
    for (P object : objects)
    {
      R record = (UpdatableRecord)DSL.using(this.configuration).newRecord(this.table, object);
      if ((forUpdate) && (pk != null)) {
        for (Field<?> field : pk) {
          record.changed(field, false);
        }
      }
      Utils.resetChangedOnNotNull(record);
      result.add(record);
    }
    return result;
  }
}
