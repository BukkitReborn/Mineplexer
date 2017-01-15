package org.jooq.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Cursor;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.FutureResult;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.exception.DataTypeException;
import org.jooq.tools.Convert;
import org.jooq.tools.JooqLogger;

abstract class AbstractResultQuery<R extends Record>
  extends AbstractQuery
  implements ResultQuery<R>
{
  private static final long serialVersionUID = -5588344253566055707L;
  private static final JooqLogger log = JooqLogger.getLogger(AbstractResultQuery.class);
  private int maxRows;
  private int fetchSize;
  private int resultSetConcurrency;
  private int resultSetType;
  private int resultSetHoldability;
  private transient boolean lazy;
  private transient boolean many;
  private transient Cursor<R> cursor;
  private Result<R> result;
  private List<Result<Record>> results;
  private final Intern intern = new Intern();
  
  AbstractResultQuery(Configuration configuration)
  {
    super(configuration);
  }
  
  protected abstract Field<?>[] getFields(ResultSetMetaData paramResultSetMetaData)
    throws SQLException;
  
  public final ResultQuery<R> bind(String param, Object value)
    throws IllegalArgumentException, DataTypeException
  {
    return (ResultQuery)super.bind(param, value);
  }
  
  public final ResultQuery<R> bind(int index, Object value)
    throws IllegalArgumentException, DataTypeException
  {
    return (ResultQuery)super.bind(index, value);
  }
  
  public final ResultQuery<R> queryTimeout(int timeout)
  {
    return (ResultQuery)super.queryTimeout(timeout);
  }
  
  public final ResultQuery<R> keepStatement(boolean k)
  {
    return (ResultQuery)super.keepStatement(k);
  }
  
  public final ResultQuery<R> maxRows(int rows)
  {
    this.maxRows = rows;
    return this;
  }
  
  public final ResultQuery<R> fetchSize(int rows)
  {
    this.fetchSize = rows;
    return this;
  }
  
  public final ResultQuery<R> resultSetConcurrency(int concurrency)
  {
    this.resultSetConcurrency = concurrency;
    return this;
  }
  
  public final ResultQuery<R> resultSetType(int type)
  {
    this.resultSetType = type;
    return this;
  }
  
  public final ResultQuery<R> resultSetHoldability(int holdability)
  {
    this.resultSetHoldability = holdability;
    return this;
  }
  
  public final ResultQuery<R> intern(Field<?>... fields)
  {
    this.intern.internFields = fields;
    return this;
  }
  
  public final ResultQuery<R> intern(int... fieldIndexes)
  {
    this.intern.internIndexes = fieldIndexes;
    return this;
  }
  
  public final ResultQuery<R> intern(String... fieldNames)
  {
    this.intern.internNames = fieldNames;
    return this;
  }
  
  protected final void prepare(ExecuteContext ctx)
    throws SQLException
  {
    if ((this.resultSetConcurrency != 0) || (this.resultSetType != 0) || (this.resultSetHoldability != 0))
    {
      int type = this.resultSetType != 0 ? this.resultSetType : 1003;
      int concurrency = this.resultSetConcurrency != 0 ? this.resultSetConcurrency : 1007;
      if (this.resultSetHoldability == 0) {
        ctx.statement(ctx.connection().prepareStatement(ctx.sql(), type, concurrency));
      } else {
        ctx.statement(ctx.connection().prepareStatement(ctx.sql(), type, concurrency, this.resultSetHoldability));
      }
    }
    else
    {
      if (isForUpdate()) {
        if (Arrays.asList(new SQLDialect[] { SQLDialect.CUBRID }).contains(ctx.configuration().dialect().family()))
        {
          ctx.data("org.jooq.configuration.lock-rows-for-update", Boolean.valueOf(true));
          ctx.statement(ctx.connection().prepareStatement(ctx.sql(), 1005, 1008));
          break label234;
        }
      }
      ctx.statement(ctx.connection().prepareStatement(ctx.sql()));
    }
    label234:
    if (this.fetchSize != 0)
    {
      if (log.isDebugEnabled()) {
        log.debug("Setting fetch size", Integer.valueOf(this.fetchSize));
      }
      ctx.statement().setFetchSize(this.fetchSize);
    }
    if (this.maxRows != 0) {
      ctx.statement().setMaxRows(this.maxRows);
    }
  }
  
  protected final int execute(ExecuteContext ctx, ExecuteListener listener)
    throws SQLException
  {
    listener.executeStart(ctx);
    if (ctx.statement().execute()) {
      ctx.resultSet(ctx.statement().getResultSet());
    }
    listener.executeEnd(ctx);
    if (!this.many)
    {
      if (ctx.resultSet() != null)
      {
        Field<?>[] fields = getFields(ctx.resultSet().getMetaData());
        this.cursor = new CursorImpl(ctx, listener, fields, this.intern.internIndexes(fields), keepStatement(), keepResultSet(), getRecordType());
        if (!this.lazy)
        {
          this.result = this.cursor.fetch();
          this.cursor = null;
        }
      }
      else
      {
        this.result = new ResultImpl(ctx.configuration(), new Field[0]);
      }
    }
    else
    {
      this.results = new ArrayList();
      Utils.consumeResultSets(ctx, listener, this.results, this.intern);
    }
    return this.result != null ? this.result.size() : 0;
  }
  
  protected final boolean keepResultSet()
  {
    return this.lazy;
  }
  
  abstract boolean isForUpdate();
  
  public final Result<R> fetch()
  {
    execute();
    return this.result;
  }
  
  public final ResultSet fetchResultSet()
  {
    return fetchLazy().resultSet();
  }
  
  public final Iterator<R> iterator()
  {
    return fetch().iterator();
  }
  
  public final Cursor<R> fetchLazy()
  {
    return fetchLazy(this.fetchSize);
  }
  
  /* Error */
  @Deprecated
  public final Cursor<R> fetchLazy(int size)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 13	org/jooq/impl/AbstractResultQuery:fetchSize	I
    //   4: istore_2
    //   5: aload_0
    //   6: iconst_1
    //   7: putfield 61	org/jooq/impl/AbstractResultQuery:lazy	Z
    //   10: aload_0
    //   11: iload_1
    //   12: putfield 13	org/jooq/impl/AbstractResultQuery:fetchSize	I
    //   15: aload_0
    //   16: invokevirtual 72	org/jooq/impl/AbstractResultQuery:execute	()I
    //   19: pop
    //   20: aload_0
    //   21: iconst_0
    //   22: putfield 61	org/jooq/impl/AbstractResultQuery:lazy	Z
    //   25: aload_0
    //   26: iload_2
    //   27: putfield 13	org/jooq/impl/AbstractResultQuery:fetchSize	I
    //   30: goto +16 -> 46
    //   33: astore_3
    //   34: aload_0
    //   35: iconst_0
    //   36: putfield 61	org/jooq/impl/AbstractResultQuery:lazy	Z
    //   39: aload_0
    //   40: iload_2
    //   41: putfield 13	org/jooq/impl/AbstractResultQuery:fetchSize	I
    //   44: aload_3
    //   45: athrow
    //   46: aload_0
    //   47: getfield 60	org/jooq/impl/AbstractResultQuery:cursor	Lorg/jooq/Cursor;
    //   50: areturn
    // Line number table:
    //   Java source line #312	-> byte code offset #0
    //   Java source line #315	-> byte code offset #5
    //   Java source line #316	-> byte code offset #10
    //   Java source line #319	-> byte code offset #15
    //   Java source line #322	-> byte code offset #20
    //   Java source line #323	-> byte code offset #25
    //   Java source line #324	-> byte code offset #30
    //   Java source line #322	-> byte code offset #33
    //   Java source line #323	-> byte code offset #39
    //   Java source line #326	-> byte code offset #46
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	51	0	this	AbstractResultQuery<R>
    //   0	51	1	size	int
    //   4	37	2	previousFetchSize	int
    //   33	12	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   15	20	33	finally
  }
  
  /* Error */
  public final List<Result<Record>> fetchMany()
  {
    // Byte code:
    //   0: aload_0
    //   1: iconst_1
    //   2: putfield 50	org/jooq/impl/AbstractResultQuery:many	Z
    //   5: aload_0
    //   6: invokevirtual 72	org/jooq/impl/AbstractResultQuery:execute	()I
    //   9: pop
    //   10: aload_0
    //   11: iconst_0
    //   12: putfield 50	org/jooq/impl/AbstractResultQuery:many	Z
    //   15: goto +11 -> 26
    //   18: astore_1
    //   19: aload_0
    //   20: iconst_0
    //   21: putfield 50	org/jooq/impl/AbstractResultQuery:many	Z
    //   24: aload_1
    //   25: athrow
    //   26: aload_0
    //   27: getfield 69	org/jooq/impl/AbstractResultQuery:results	Ljava/util/List;
    //   30: areturn
    // Line number table:
    //   Java source line #333	-> byte code offset #0
    //   Java source line #336	-> byte code offset #5
    //   Java source line #339	-> byte code offset #10
    //   Java source line #340	-> byte code offset #15
    //   Java source line #339	-> byte code offset #18
    //   Java source line #342	-> byte code offset #26
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	31	0	this	AbstractResultQuery<R>
    //   18	7	1	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   5	10	18	finally
  }
  
  public final <T> List<T> fetch(Field<T> field)
  {
    return fetch().getValues(field);
  }
  
  public final <T> List<T> fetch(Field<?> field, Class<? extends T> type)
  {
    return fetch().getValues(field, type);
  }
  
  public final <T, U> List<U> fetch(Field<T> field, Converter<? super T, U> converter)
  {
    return fetch().getValues(field, converter);
  }
  
  public final List<?> fetch(int fieldIndex)
  {
    return fetch().getValues(fieldIndex);
  }
  
  public final <T> List<T> fetch(int fieldIndex, Class<? extends T> type)
  {
    return fetch().getValues(fieldIndex, type);
  }
  
  public final <U> List<U> fetch(int fieldIndex, Converter<?, U> converter)
  {
    return fetch().getValues(fieldIndex, converter);
  }
  
  public final List<?> fetch(String fieldName)
  {
    return fetch().getValues(fieldName);
  }
  
  public final <T> List<T> fetch(String fieldName, Class<? extends T> type)
  {
    return fetch().getValues(fieldName, type);
  }
  
  public final <U> List<U> fetch(String fieldName, Converter<?, U> converter)
  {
    return fetch().getValues(fieldName, converter);
  }
  
  public final <T> T fetchOne(Field<T> field)
  {
    R record = fetchOne();
    return record == null ? null : record.getValue(field);
  }
  
  public final <T> T fetchOne(Field<?> field, Class<? extends T> type)
  {
    return (T)Convert.convert(fetchOne(field), type);
  }
  
  public final <T, U> U fetchOne(Field<T> field, Converter<? super T, U> converter)
  {
    return (U)Convert.convert(fetchOne(field), converter);
  }
  
  public final Object fetchOne(int fieldIndex)
  {
    R record = fetchOne();
    return record == null ? null : record.getValue(fieldIndex);
  }
  
  public final <T> T fetchOne(int fieldIndex, Class<? extends T> type)
  {
    return (T)Convert.convert(fetchOne(fieldIndex), type);
  }
  
  public final <U> U fetchOne(int fieldIndex, Converter<?, U> converter)
  {
    return (U)Convert.convert(fetchOne(fieldIndex), converter);
  }
  
  public final Object fetchOne(String fieldName)
  {
    R record = fetchOne();
    return record == null ? null : record.getValue(fieldName);
  }
  
  public final <T> T fetchOne(String fieldName, Class<? extends T> type)
  {
    return (T)Convert.convert(fetchOne(fieldName), type);
  }
  
  public final <U> U fetchOne(String fieldName, Converter<?, U> converter)
  {
    return (U)Convert.convert(fetchOne(fieldName), converter);
  }
  
  public final R fetchOne()
  {
    return Utils.fetchOne(fetchLazy());
  }
  
  public final Map<String, Object> fetchOneMap()
  {
    R record = fetchOne();
    return record == null ? null : record.intoMap();
  }
  
  public final Object[] fetchOneArray()
  {
    R record = fetchOne();
    return record == null ? null : record.intoArray();
  }
  
  public final <E> E fetchOneInto(Class<? extends E> type)
  {
    R record = fetchOne();
    return record == null ? null : record.into(type);
  }
  
  public final <Z extends Record> Z fetchOneInto(Table<Z> table)
  {
    R record = fetchOne();
    return record == null ? null : record.into(table);
  }
  
  public final <T> T fetchAny(Field<T> field)
  {
    R record = fetchAny();
    return record == null ? null : record.getValue(field);
  }
  
  public final <T> T fetchAny(Field<?> field, Class<? extends T> type)
  {
    return (T)Convert.convert(fetchAny(field), type);
  }
  
  public final <T, U> U fetchAny(Field<T> field, Converter<? super T, U> converter)
  {
    return (U)Convert.convert(fetchAny(field), converter);
  }
  
  public final Object fetchAny(int fieldIndex)
  {
    R record = fetchAny();
    return record == null ? null : record.getValue(fieldIndex);
  }
  
  public final <T> T fetchAny(int fieldIndex, Class<? extends T> type)
  {
    return (T)Convert.convert(fetchAny(fieldIndex), type);
  }
  
  public final <U> U fetchAny(int fieldIndex, Converter<?, U> converter)
  {
    return (U)Convert.convert(fetchAny(fieldIndex), converter);
  }
  
  public final Object fetchAny(String fieldName)
  {
    R record = fetchAny();
    return record == null ? null : record.getValue(fieldName);
  }
  
  public final <T> T fetchAny(String fieldName, Class<? extends T> type)
  {
    return (T)Convert.convert(fetchAny(fieldName), type);
  }
  
  public final <U> U fetchAny(String fieldName, Converter<?, U> converter)
  {
    return (U)Convert.convert(fetchAny(fieldName), converter);
  }
  
  public final R fetchAny()
  {
    Cursor<R> c = fetchLazy();
    try
    {
      return c.fetchOne();
    }
    finally
    {
      c.close();
    }
  }
  
  public final Map<String, Object> fetchAnyMap()
  {
    R record = fetchAny();
    return record == null ? null : record.intoMap();
  }
  
  public final Object[] fetchAnyArray()
  {
    R record = fetchAny();
    return record == null ? null : record.intoArray();
  }
  
  public final <E> E fetchAnyInto(Class<? extends E> type)
  {
    R record = fetchAny();
    return record == null ? null : record.into(type);
  }
  
  public final <Z extends Record> Z fetchAnyInto(Table<Z> table)
  {
    R record = fetchAny();
    return record == null ? null : record.into(table);
  }
  
  public final <K> Map<K, R> fetchMap(Field<K> key)
  {
    return fetch().intoMap(key);
  }
  
  public final <K, V> Map<K, V> fetchMap(Field<K> key, Field<V> value)
  {
    return fetch().intoMap(key, value);
  }
  
  public final Map<Record, R> fetchMap(Field<?>[] keys)
  {
    return fetch().intoMap(keys);
  }
  
  public final <E> Map<List<?>, E> fetchMap(Field<?>[] keys, Class<? extends E> type)
  {
    return fetch().intoMap(keys, type);
  }
  
  public final <E> Map<List<?>, E> fetchMap(Field<?>[] keys, RecordMapper<? super R, E> mapper)
  {
    return fetch().intoMap(keys, mapper);
  }
  
  public final <S extends Record> Map<S, R> fetchMap(Table<S> table)
  {
    return fetch().intoMap(table);
  }
  
  public final <E, S extends Record> Map<S, E> fetchMap(Table<S> table, Class<? extends E> type)
  {
    return fetch().intoMap(table, type);
  }
  
  public final <E, S extends Record> Map<S, E> fetchMap(Table<S> table, RecordMapper<? super R, E> mapper)
  {
    return fetch().intoMap(table, mapper);
  }
  
  public final <K, E> Map<K, E> fetchMap(Field<K> key, Class<? extends E> type)
  {
    return fetch().intoMap(key, type);
  }
  
  public final <K, E> Map<K, E> fetchMap(Field<K> key, RecordMapper<? super R, E> mapper)
  {
    return fetch().intoMap(key, mapper);
  }
  
  public final List<Map<String, Object>> fetchMaps()
  {
    return fetch().intoMaps();
  }
  
  public final <K> Map<K, Result<R>> fetchGroups(Field<K> key)
  {
    return fetch().intoGroups(key);
  }
  
  public final <K, V> Map<K, List<V>> fetchGroups(Field<K> key, Field<V> value)
  {
    return fetch().intoGroups(key, value);
  }
  
  public final Map<Record, Result<R>> fetchGroups(Field<?>[] keys)
  {
    return fetch().intoGroups(keys);
  }
  
  public final <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys, Class<? extends E> type)
  {
    return fetch().intoGroups(keys, type);
  }
  
  public final <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys, RecordMapper<? super R, E> mapper)
  {
    return fetch().intoGroups(keys, mapper);
  }
  
  public final <S extends Record> Map<S, Result<R>> fetchGroups(Table<S> table)
  {
    return fetch().intoGroups(table);
  }
  
  public final <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, Class<? extends E> type)
  {
    return fetch().intoGroups(table, type);
  }
  
  public final <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, RecordMapper<? super R, E> mapper)
  {
    return fetch().intoGroups(table, mapper);
  }
  
  public final <K, E> Map<K, List<E>> fetchGroups(Field<K> key, Class<? extends E> type)
  {
    return fetch().intoGroups(key, type);
  }
  
  public final <K, E> Map<K, List<E>> fetchGroups(Field<K> key, RecordMapper<? super R, E> mapper)
  {
    return fetch().intoGroups(key, mapper);
  }
  
  public final Object[][] fetchArrays()
  {
    return fetch().intoArray();
  }
  
  public final Object[] fetchArray(int fieldIndex)
  {
    return fetch().intoArray(fieldIndex);
  }
  
  public final <T> T[] fetchArray(int fieldIndex, Class<? extends T> type)
  {
    return fetch().intoArray(fieldIndex, type);
  }
  
  public final <U> U[] fetchArray(int fieldIndex, Converter<?, U> converter)
  {
    return fetch().intoArray(fieldIndex, converter);
  }
  
  public final Object[] fetchArray(String fieldName)
  {
    return fetch().intoArray(fieldName);
  }
  
  public final <T> T[] fetchArray(String fieldName, Class<? extends T> type)
  {
    return fetch().intoArray(fieldName, type);
  }
  
  public final <U> U[] fetchArray(String fieldName, Converter<?, U> converter)
  {
    return fetch().intoArray(fieldName, converter);
  }
  
  public final <T> T[] fetchArray(Field<T> field)
  {
    return fetch().intoArray(field);
  }
  
  public final <T> T[] fetchArray(Field<?> field, Class<? extends T> type)
  {
    return fetch().intoArray(field, type);
  }
  
  public final <T, U> U[] fetchArray(Field<T> field, Converter<? super T, U> converter)
  {
    return fetch().intoArray(field, converter);
  }
  
  public final Set<?> fetchSet(int fieldIndex)
  {
    return fetch().intoSet(fieldIndex);
  }
  
  public final <T> Set<T> fetchSet(int fieldIndex, Class<? extends T> type)
  {
    return fetch().intoSet(fieldIndex, type);
  }
  
  public final <U> Set<U> fetchSet(int fieldIndex, Converter<?, U> converter)
  {
    return fetch().intoSet(fieldIndex, converter);
  }
  
  public final Set<?> fetchSet(String fieldName)
  {
    return fetch().intoSet(fieldName);
  }
  
  public final <T> Set<T> fetchSet(String fieldName, Class<? extends T> type)
  {
    return fetch().intoSet(fieldName, type);
  }
  
  public final <U> Set<U> fetchSet(String fieldName, Converter<?, U> converter)
  {
    return fetch().intoSet(fieldName, converter);
  }
  
  public final <T> Set<T> fetchSet(Field<T> field)
  {
    return fetch().intoSet(field);
  }
  
  public final <T> Set<T> fetchSet(Field<?> field, Class<? extends T> type)
  {
    return fetch().intoSet(field, type);
  }
  
  public final <T, U> Set<U> fetchSet(Field<T> field, Converter<? super T, U> converter)
  {
    return fetch().intoSet(field, converter);
  }
  
  public Class<? extends R> getRecordType()
  {
    return null;
  }
  
  public final <T> List<T> fetchInto(Class<? extends T> type)
  {
    return fetch().into(type);
  }
  
  public final <Z extends Record> Result<Z> fetchInto(Table<Z> table)
  {
    return fetch().into(table);
  }
  
  public final <H extends RecordHandler<? super R>> H fetchInto(H handler)
  {
    return fetch().into(handler);
  }
  
  public final <E> List<E> fetch(RecordMapper<? super R, E> mapper)
  {
    return fetch().map(mapper);
  }
  
  @Deprecated
  public final FutureResult<R> fetchLater()
  {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    Future<Result<R>> future = executor.submit(new ResultQueryCallable(null));
    return new FutureResultImpl(future, executor);
  }
  
  @Deprecated
  public final FutureResult<R> fetchLater(ExecutorService executor)
  {
    Future<Result<R>> future = executor.submit(new ResultQueryCallable(null));
    return new FutureResultImpl(future);
  }
  
  public final Result<R> getResult()
  {
    return this.result;
  }
  
  private final class ResultQueryCallable
    implements Callable<Result<R>>
  {
    private ResultQueryCallable() {}
    
    public final Result<R> call()
      throws Exception
    {
      return AbstractResultQuery.this.fetch();
    }
  }
}
