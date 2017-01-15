package org.jooq.impl;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.jooq.AlterSequenceRestartStep;
import org.jooq.AlterTableStep;
import org.jooq.Attachable;
import org.jooq.Batch;
import org.jooq.BatchBindStep;
import org.jooq.BindContext;
import org.jooq.CommonTableExpression;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.CreateIndexStep;
import org.jooq.CreateSequenceFinalStep;
import org.jooq.CreateTableAsStep;
import org.jooq.CreateViewAsStep;
import org.jooq.Cursor;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DeleteQuery;
import org.jooq.DeleteWhereStep;
import org.jooq.DropIndexFinalStep;
import org.jooq.DropSequenceFinalStep;
import org.jooq.DropTableStep;
import org.jooq.DropViewFinalStep;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.InsertQuery;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStep1;
import org.jooq.InsertValuesStep10;
import org.jooq.InsertValuesStep11;
import org.jooq.InsertValuesStep12;
import org.jooq.InsertValuesStep13;
import org.jooq.InsertValuesStep14;
import org.jooq.InsertValuesStep15;
import org.jooq.InsertValuesStep16;
import org.jooq.InsertValuesStep17;
import org.jooq.InsertValuesStep18;
import org.jooq.InsertValuesStep19;
import org.jooq.InsertValuesStep2;
import org.jooq.InsertValuesStep20;
import org.jooq.InsertValuesStep21;
import org.jooq.InsertValuesStep22;
import org.jooq.InsertValuesStep3;
import org.jooq.InsertValuesStep4;
import org.jooq.InsertValuesStep5;
import org.jooq.InsertValuesStep6;
import org.jooq.InsertValuesStep7;
import org.jooq.InsertValuesStep8;
import org.jooq.InsertValuesStep9;
import org.jooq.InsertValuesStepN;
import org.jooq.LoaderOptionsStep;
import org.jooq.MergeKeyStep1;
import org.jooq.MergeKeyStep10;
import org.jooq.MergeKeyStep11;
import org.jooq.MergeKeyStep12;
import org.jooq.MergeKeyStep13;
import org.jooq.MergeKeyStep14;
import org.jooq.MergeKeyStep15;
import org.jooq.MergeKeyStep16;
import org.jooq.MergeKeyStep17;
import org.jooq.MergeKeyStep18;
import org.jooq.MergeKeyStep19;
import org.jooq.MergeKeyStep2;
import org.jooq.MergeKeyStep20;
import org.jooq.MergeKeyStep21;
import org.jooq.MergeKeyStep22;
import org.jooq.MergeKeyStep3;
import org.jooq.MergeKeyStep4;
import org.jooq.MergeKeyStep5;
import org.jooq.MergeKeyStep6;
import org.jooq.MergeKeyStep7;
import org.jooq.MergeKeyStep8;
import org.jooq.MergeKeyStep9;
import org.jooq.MergeKeyStepN;
import org.jooq.MergeUsingStep;
import org.jooq.Meta;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.RenderContext;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.SQLDialect;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectQuery;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.TableRecord;
import org.jooq.Template;
import org.jooq.TransactionProvider;
import org.jooq.TransactionalCallable;
import org.jooq.TransactionalRunnable;
import org.jooq.TruncateIdentityStep;
import org.jooq.UDT;
import org.jooq.UDTRecord;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.UpdateQuery;
import org.jooq.UpdateSetFirstStep;
import org.jooq.WithAsStep;
import org.jooq.WithStep;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.SQLDialectNotSupportedException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.csv.CSVReader;
import org.jooq.tools.reflect.Reflect;
import org.jooq.tools.reflect.ReflectException;

public class DefaultDSLContext
  extends AbstractScope
  implements DSLContext, Serializable
{
  private static final long serialVersionUID = 2681360188806309513L;
  private static final JooqLogger log = JooqLogger.getLogger(DefaultDSLContext.class);
  
  public DefaultDSLContext(SQLDialect dialect)
  {
    this(dialect, null);
  }
  
  public DefaultDSLContext(SQLDialect dialect, Settings settings)
  {
    this(new DefaultConfiguration(new NoConnectionProvider(), null, null, null, null, null, dialect, settings, null));
  }
  
  public DefaultDSLContext(Connection connection, SQLDialect dialect)
  {
    this(connection, dialect, null);
  }
  
  public DefaultDSLContext(Connection connection, SQLDialect dialect, Settings settings)
  {
    this(new DefaultConfiguration(new DefaultConnectionProvider(connection), null, null, null, null, null, dialect, settings, null));
  }
  
  public DefaultDSLContext(DataSource datasource, SQLDialect dialect)
  {
    this(datasource, dialect, null);
  }
  
  public DefaultDSLContext(DataSource datasource, SQLDialect dialect, Settings settings)
  {
    this(new DefaultConfiguration(new DataSourceConnectionProvider(datasource), null, null, null, null, null, dialect, settings, null));
  }
  
  public DefaultDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect)
  {
    this(connectionProvider, dialect, null);
  }
  
  public DefaultDSLContext(ConnectionProvider connectionProvider, SQLDialect dialect, Settings settings)
  {
    this(new DefaultConfiguration(connectionProvider, null, null, null, null, null, dialect, settings, null));
  }
  
  public DefaultDSLContext(Configuration configuration)
  {
    super(configuration);
  }
  
  public Schema map(Schema schema)
  {
    return Utils.getMappedSchema(configuration(), schema);
  }
  
  public <R extends Record> Table<R> map(Table<R> table)
  {
    return Utils.getMappedTable(configuration(), table);
  }
  
  public Meta meta()
  {
    return new MetaImpl(configuration());
  }
  
  public <T> T transactionResult(TransactionalCallable<T> transactional)
  {
    T result = null;
    
    DefaultTransactionContext ctx = new DefaultTransactionContext(configuration().derive());
    TransactionProvider provider = ctx.configuration().transactionProvider();
    try
    {
      provider.begin(ctx);
      result = transactional.run(ctx.configuration());
      provider.commit(ctx);
    }
    catch (Exception cause)
    {
      try
      {
        provider.rollback(ctx.cause(cause));
      }
      catch (Exception suppress)
      {
        try
        {
          Reflect.on(cause).call("addSuppressed", new Object[] { suppress });
        }
        catch (ReflectException ignore)
        {
          log.error("Error when rolling back", suppress);
        }
      }
      if ((cause instanceof RuntimeException)) {
        throw ((RuntimeException)cause);
      }
      throw new DataAccessException("Rollback caused", cause);
    }
    return result;
  }
  
  public void transaction(final TransactionalRunnable transactional)
  {
    transactionResult(new TransactionalCallable()
    {
      public Void run(Configuration c)
        throws Exception
      {
        transactional.run(c);
        return null;
      }
    });
  }
  
  public RenderContext renderContext()
  {
    return new DefaultRenderContext(configuration());
  }
  
  public String render(QueryPart part)
  {
    return renderContext().render(part);
  }
  
  public String renderNamedParams(QueryPart part)
  {
    return renderContext().paramType(ParamType.NAMED).render(part);
  }
  
  public String renderNamedOrInlinedParams(QueryPart part)
  {
    return renderContext().paramType(ParamType.NAMED_OR_INLINED).render(part);
  }
  
  public String renderInlined(QueryPart part)
  {
    return renderContext().paramType(ParamType.INLINED).render(part);
  }
  
  public List<Object> extractBindValues(QueryPart part)
  {
    List<Object> result = new ArrayList();
    for (Param<?> param : extractParams0(part, false).values()) {
      result.add(param.getValue());
    }
    return Collections.unmodifiableList(result);
  }
  
  public Map<String, Param<?>> extractParams(QueryPart part)
  {
    return extractParams0(part, true);
  }
  
  final Map<String, Param<?>> extractParams0(QueryPart part, boolean includeInlinedParams)
  {
    ParamCollector collector = new ParamCollector(configuration(), includeInlinedParams);
    collector.visit(part);
    return Collections.unmodifiableMap(collector.result);
  }
  
  public Param<?> extractParam(QueryPart part, String name)
  {
    return (Param)extractParams(part).get(name);
  }
  
  public BindContext bindContext(PreparedStatement stmt)
  {
    return new DefaultBindContext(configuration(), stmt);
  }
  
  @Deprecated
  public int bind(QueryPart part, PreparedStatement stmt)
  {
    return ((BindContext)bindContext(stmt).visit(part)).peekIndex();
  }
  
  public void attach(Attachable... attachables)
  {
    attach(Arrays.asList(attachables));
  }
  
  public void attach(Collection<? extends Attachable> attachables)
  {
    for (Attachable attachable : attachables) {
      attachable.attach(configuration());
    }
  }
  
  public <R extends TableRecord<R>> LoaderOptionsStep<R> loadInto(Table<R> table)
  {
    return new LoaderImpl(configuration(), table);
  }
  
  public Query query(String sql)
  {
    return query(DSL.template(sql), new Object[0]);
  }
  
  public Query query(String sql, Object... bindings)
  {
    return query(DSL.template(sql), bindings);
  }
  
  public Query query(String sql, QueryPart... parts)
  {
    return query(DSL.template(sql), (Object[])parts);
  }
  
  Query query(Template template, Object... parameters)
  {
    return new SQLQuery(configuration(), DSL.queryPart(template, parameters));
  }
  
  public Result<Record> fetch(String sql)
  {
    return resultQuery(sql).fetch();
  }
  
  public Result<Record> fetch(String sql, Object... bindings)
  {
    return resultQuery(sql, bindings).fetch();
  }
  
  public Result<Record> fetch(String sql, QueryPart... parts)
  {
    return resultQuery(sql, parts).fetch();
  }
  
  Result<Record> fetch(Template template, Object... parameters)
  {
    return resultQuery(template, parameters).fetch();
  }
  
  public Cursor<Record> fetchLazy(String sql)
  {
    return resultQuery(sql).fetchLazy();
  }
  
  public Cursor<Record> fetchLazy(String sql, Object... bindings)
  {
    return resultQuery(sql, bindings).fetchLazy();
  }
  
  public Cursor<Record> fetchLazy(String sql, QueryPart... parts)
  {
    return resultQuery(sql, parts).fetchLazy();
  }
  
  Cursor<Record> fetchLazy(Template template, Object... parameters)
  {
    return resultQuery(template, parameters).fetchLazy();
  }
  
  public List<Result<Record>> fetchMany(String sql)
  {
    return resultQuery(sql).fetchMany();
  }
  
  public List<Result<Record>> fetchMany(String sql, Object... bindings)
  {
    return resultQuery(sql, bindings).fetchMany();
  }
  
  public List<Result<Record>> fetchMany(String sql, QueryPart... parts)
  {
    return resultQuery(sql, parts).fetchMany();
  }
  
  List<Result<Record>> fetchMany(Template template, Object... parameters)
  {
    return resultQuery(template, parameters).fetchMany();
  }
  
  public Record fetchOne(String sql)
  {
    return resultQuery(sql).fetchOne();
  }
  
  public Record fetchOne(String sql, Object... bindings)
  {
    return resultQuery(sql, bindings).fetchOne();
  }
  
  public Record fetchOne(String sql, QueryPart... parts)
  {
    return resultQuery(sql, parts).fetchOne();
  }
  
  Record fetchOne(Template template, Object... parameters)
  {
    return resultQuery(template, parameters).fetchOne();
  }
  
  public Object fetchValue(String sql)
  {
    return fetchValue(resultQuery(sql));
  }
  
  public Object fetchValue(String sql, Object... bindings)
  {
    return fetchValue(resultQuery(sql, bindings));
  }
  
  public Object fetchValue(String sql, QueryPart... parts)
  {
    return fetchValue(resultQuery(sql, parts));
  }
  
  public List<?> fetchValues(String sql)
  {
    return fetchValues(resultQuery(sql));
  }
  
  public List<?> fetchValues(String sql, Object... bindings)
  {
    return fetchValues(resultQuery(sql, bindings));
  }
  
  public List<?> fetchValues(String sql, QueryPart... parts)
  {
    return fetchValues(resultQuery(sql, parts));
  }
  
  public int execute(String sql)
  {
    return query(sql).execute();
  }
  
  public int execute(String sql, Object... bindings)
  {
    return query(sql, bindings).execute();
  }
  
  public int execute(String sql, QueryPart... parts)
  {
    return query(sql, parts).execute();
  }
  
  int execute(Template template, Object... parameters)
  {
    return query(template, parameters).execute();
  }
  
  public ResultQuery<Record> resultQuery(String sql)
  {
    return resultQuery(DSL.template(sql), new Object[0]);
  }
  
  public ResultQuery<Record> resultQuery(String sql, Object... bindings)
  {
    return resultQuery(DSL.template(sql), bindings);
  }
  
  public ResultQuery<Record> resultQuery(String sql, QueryPart... parts)
  {
    return resultQuery(DSL.template(sql), (Object[])parts);
  }
  
  ResultQuery<Record> resultQuery(Template template, Object... parameters)
  {
    return new SQLResultQuery(configuration(), DSL.queryPart(template, parameters));
  }
  
  public Result<Record> fetch(ResultSet rs)
  {
    return fetchLazy(rs).fetch();
  }
  
  public Result<Record> fetch(ResultSet rs, Field<?>... fields)
  {
    return fetchLazy(rs, fields).fetch();
  }
  
  public Result<Record> fetch(ResultSet rs, DataType<?>... types)
  {
    return fetchLazy(rs, types).fetch();
  }
  
  public Result<Record> fetch(ResultSet rs, Class<?>... types)
  {
    return fetchLazy(rs, types).fetch();
  }
  
  public Record fetchOne(ResultSet rs)
  {
    return Utils.fetchOne(fetchLazy(rs));
  }
  
  public Record fetchOne(ResultSet rs, Field<?>... fields)
  {
    return Utils.fetchOne(fetchLazy(rs, fields));
  }
  
  public Record fetchOne(ResultSet rs, DataType<?>... types)
  {
    return Utils.fetchOne(fetchLazy(rs, types));
  }
  
  public Record fetchOne(ResultSet rs, Class<?>... types)
  {
    return Utils.fetchOne(fetchLazy(rs, types));
  }
  
  public Object fetchValue(ResultSet rs)
  {
    return value1((Record1)fetchOne(rs));
  }
  
  public <T> T fetchValue(ResultSet rs, Field<T> field)
  {
    return (T)value1((Record1)fetchOne(rs, new Field[] { field }));
  }
  
  public <T> T fetchValue(ResultSet rs, DataType<T> type)
  {
    return (T)value1((Record1)fetchOne(rs, new DataType[] { type }));
  }
  
  public <T> T fetchValue(ResultSet rs, Class<T> type)
  {
    return (T)value1((Record1)fetchOne(rs, new Class[] { type }));
  }
  
  public List<?> fetchValues(ResultSet rs)
  {
    return fetch(rs).getValues(0);
  }
  
  public <T> List<T> fetchValues(ResultSet rs, Field<T> field)
  {
    return fetch(rs).getValues(field);
  }
  
  public <T> List<T> fetchValues(ResultSet rs, DataType<T> type)
  {
    return fetch(rs).getValues(0, type.getType());
  }
  
  public <T> List<T> fetchValues(ResultSet rs, Class<T> type)
  {
    return fetch(rs).getValues(0, type);
  }
  
  public Cursor<Record> fetchLazy(ResultSet rs)
  {
    try
    {
      return fetchLazy(rs, new MetaDataFieldProvider(configuration(), rs.getMetaData()).getFields());
    }
    catch (SQLException e)
    {
      throw new DataAccessException("Error while accessing ResultSet meta data", e);
    }
  }
  
  public Cursor<Record> fetchLazy(ResultSet rs, Field<?>... fields)
  {
    ExecuteContext ctx = new DefaultExecuteContext(configuration());
    ExecuteListener listener = new ExecuteListeners(ctx);
    
    ctx.resultSet(rs);
    return new CursorImpl(ctx, listener, fields, null, false, true);
  }
  
  public Cursor<Record> fetchLazy(ResultSet rs, DataType<?>... types)
  {
    try
    {
      Field<?>[] fields = new Field[types.length];
      ResultSetMetaData meta = rs.getMetaData();
      int columns = meta.getColumnCount();
      for (int i = 0; (i < types.length) && (i < columns); i++) {
        fields[i] = DSL.field(meta.getColumnLabel(i + 1), types[i]);
      }
      return fetchLazy(rs, fields);
    }
    catch (SQLException e)
    {
      throw new DataAccessException("Error while accessing ResultSet meta data", e);
    }
  }
  
  public Cursor<Record> fetchLazy(ResultSet rs, Class<?>... types)
  {
    return fetchLazy(rs, Utils.dataTypes(types));
  }
  
  public Result<Record> fetchFromTXT(String string)
  {
    return fetchFromTXT(string, "{null}");
  }
  
  public Result<Record> fetchFromTXT(String string, String nullLiteral)
  {
    return fetchFromStringData(Utils.parseTXT(string, nullLiteral));
  }
  
  public Result<Record> fetchFromCSV(String string)
  {
    return fetchFromCSV(string, ',');
  }
  
  public Result<Record> fetchFromCSV(String string, char delimiter)
  {
    CSVReader reader = new CSVReader(new StringReader(string), delimiter);
    data = null;
    try
    {
      data = reader.readAll();
      
      return fetchFromStringData(data);
    }
    catch (IOException e)
    {
      throw new DataAccessException("Could not read the CSV string", e);
    }
    finally
    {
      try
      {
        reader.close();
      }
      catch (IOException localIOException2) {}
    }
  }
  
  public Result<Record> fetchFromJSON(String string)
  {
    data = new LinkedList();
    JSONReader reader = null;
    try
    {
      reader = new JSONReader(new StringReader(string));
      List<String[]> records = reader.readAll();
      String[] fields = reader.getFields();
      data.add(fields);
      data.addAll(records);
      
      return fetchFromStringData(data);
    }
    catch (IOException e)
    {
      throw new DataAccessException("Could not read the JSON string", e);
    }
    finally
    {
      try
      {
        if (reader != null) {
          reader.close();
        }
      }
      catch (IOException localIOException2) {}
    }
  }
  
  public Result<Record> fetchFromStringData(String[]... data)
  {
    return fetchFromStringData(Utils.list(data));
  }
  
  public Result<Record> fetchFromStringData(List<String[]> data)
  {
    if (data.size() == 0) {
      return new ResultImpl(configuration(), new Field[0]);
    }
    List<Field<?>> fields = new ArrayList();
    for (String name : (String[])data.get(0)) {
      fields.add(DSL.fieldByName(String.class, new String[] { name }));
    }
    Object result = new ResultImpl(configuration(), fields);
    if (data.size() > 1) {
      for (String[] values : data.subList(1, data.size()))
      {
        RecordImpl record = new RecordImpl(fields);
        for (int i = 0; i < Math.min(values.length, fields.size()); i++)
        {
          record.values[i] = values[i];
          record.originals[i] = values[i];
        }
        ((Result)result).add(record);
      }
    }
    return (Result<Record>)result;
  }
  
  public WithAsStep with(String alias)
  {
    return new WithImpl(configuration(), false).with(alias);
  }
  
  public WithAsStep with(String alias, String... fieldAliases)
  {
    return new WithImpl(configuration(), false).with(alias, fieldAliases);
  }
  
  public WithStep with(CommonTableExpression<?>... tables)
  {
    return new WithImpl(configuration(), false).with(tables);
  }
  
  public WithAsStep withRecursive(String alias)
  {
    return new WithImpl(configuration(), true).with(alias);
  }
  
  public WithAsStep withRecursive(String alias, String... fieldAliases)
  {
    return new WithImpl(configuration(), true).with(alias, fieldAliases);
  }
  
  public WithStep withRecursive(CommonTableExpression<?>... tables)
  {
    return new WithImpl(configuration(), true).with(tables);
  }
  
  public <R extends Record> SelectWhereStep<R> selectFrom(Table<R> table)
  {
    SelectWhereStep<R> result = DSL.selectFrom(table);
    result.attach(configuration());
    return result;
  }
  
  public SelectSelectStep<Record> select(Collection<? extends Field<?>> fields)
  {
    SelectSelectStep<Record> result = DSL.select(fields);
    result.attach(configuration());
    return result;
  }
  
  public SelectSelectStep<Record> select(Field<?>... fields)
  {
    SelectSelectStep<Record> result = DSL.select(fields);
    result.attach(configuration());
    return result;
  }
  
  public <T1> SelectSelectStep<Record1<T1>> select(Field<T1> field1)
  {
    return select(new Field[] { field1 });
  }
  
  public <T1, T2> SelectSelectStep<Record2<T1, T2>> select(Field<T1> field1, Field<T2> field2)
  {
    return select(new Field[] { field1, field2 });
  }
  
  public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return select(new Field[] { field1, field2, field3 });
  }
  
  public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return select(new Field[] { field1, field2, field3, field4 });
  }
  
  public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return select(new Field[] { field1, field2, field3, field4, field5 });
  }
  
  public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return select(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  public SelectSelectStep<Record> selectDistinct(Collection<? extends Field<?>> fields)
  {
    SelectSelectStep<Record> result = DSL.selectDistinct(fields);
    result.attach(configuration());
    return result;
  }
  
  public SelectSelectStep<Record> selectDistinct(Field<?>... fields)
  {
    SelectSelectStep<Record> result = DSL.selectDistinct(fields);
    result.attach(configuration());
    return result;
  }
  
  public <T1> SelectSelectStep<Record1<T1>> selectDistinct(Field<T1> field1)
  {
    return selectDistinct(new Field[] { field1 });
  }
  
  public <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(Field<T1> field1, Field<T2> field2)
  {
    return selectDistinct(new Field[] { field1, field2 });
  }
  
  public <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return selectDistinct(new Field[] { field1, field2, field3 });
  }
  
  public <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4 });
  }
  
  public <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5 });
  }
  
  public <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return selectDistinct(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  public SelectSelectStep<Record1<Integer>> selectZero()
  {
    SelectSelectStep<Record1<Integer>> result = DSL.selectZero();
    result.attach(configuration());
    return result;
  }
  
  public SelectSelectStep<Record1<Integer>> selectOne()
  {
    SelectSelectStep<Record1<Integer>> result = DSL.selectOne();
    result.attach(configuration());
    return result;
  }
  
  public SelectSelectStep<Record1<Integer>> selectCount()
  {
    SelectSelectStep<Record1<Integer>> result = DSL.selectCount();
    result.attach(configuration());
    return result;
  }
  
  public SelectQuery<Record> selectQuery()
  {
    return new SelectQueryImpl(null, configuration());
  }
  
  public <R extends Record> SelectQuery<R> selectQuery(TableLike<R> table)
  {
    return new SelectQueryImpl(null, configuration(), table);
  }
  
  public <R extends Record> InsertQuery<R> insertQuery(Table<R> into)
  {
    return new InsertQueryImpl(configuration(), into);
  }
  
  public <R extends Record> InsertSetStep<R> insertInto(Table<R> into)
  {
    return new InsertImpl(configuration(), into, Collections.emptyList());
  }
  
  public <R extends Record, T1> InsertValuesStep1<R, T1> insertInto(Table<R> into, Field<T1> field1)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1 }));
  }
  
  public <R extends Record, T1, T2> InsertValuesStep2<R, T1, T2> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2 }));
  }
  
  public <R extends Record, T1, T2, T3> InsertValuesStep3<R, T1, T2, T3> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3 }));
  }
  
  public <R extends Record, T1, T2, T3, T4> InsertValuesStep4<R, T1, T2, T3, T4> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5> InsertValuesStep5<R, T1, T2, T3, T4, T5> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6> InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertValuesStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertValuesStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertValuesStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertValuesStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertValuesStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertValuesStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertValuesStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertValuesStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertValuesStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertValuesStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertValuesStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 }));
  }
  
  public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Field<?>... fields)
  {
    return new InsertImpl(configuration(), into, Arrays.asList(fields));
  }
  
  public <R extends Record> InsertValuesStepN<R> insertInto(Table<R> into, Collection<? extends Field<?>> fields)
  {
    return new InsertImpl(configuration(), into, fields);
  }
  
  public <R extends Record> UpdateQuery<R> updateQuery(Table<R> table)
  {
    return new UpdateQueryImpl(configuration(), table);
  }
  
  public <R extends Record> UpdateSetFirstStep<R> update(Table<R> table)
  {
    return new UpdateImpl(configuration(), table);
  }
  
  public <R extends Record> MergeUsingStep<R> mergeInto(Table<R> table)
  {
    return new MergeImpl(configuration(), table);
  }
  
  public <R extends Record, T1> MergeKeyStep1<R, T1> mergeInto(Table<R> table, Field<T1> field1)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1 }));
  }
  
  public <R extends Record, T1, T2> MergeKeyStep2<R, T1, T2> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2 }));
  }
  
  public <R extends Record, T1, T2, T3> MergeKeyStep3<R, T1, T2, T3> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3 }));
  }
  
  public <R extends Record, T1, T2, T3, T4> MergeKeyStep4<R, T1, T2, T3, T4> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5> MergeKeyStep5<R, T1, T2, T3, T4, T5> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6> MergeKeyStep6<R, T1, T2, T3, T4, T5, T6> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeKeyStep7<R, T1, T2, T3, T4, T5, T6, T7> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeKeyStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeKeyStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeKeyStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeKeyStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeKeyStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeKeyStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeKeyStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeKeyStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeKeyStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeKeyStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeKeyStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeKeyStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeKeyStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeKeyStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 }));
  }
  
  public <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeKeyStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return new MergeImpl(configuration(), table, Arrays.asList(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 }));
  }
  
  public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Field<?>... fields)
  {
    return mergeInto(table, Arrays.asList(fields));
  }
  
  public <R extends Record> MergeKeyStepN<R> mergeInto(Table<R> table, Collection<? extends Field<?>> fields)
  {
    return new MergeImpl(configuration(), table, fields);
  }
  
  public <R extends Record> DeleteQuery<R> deleteQuery(Table<R> table)
  {
    return new DeleteQueryImpl(configuration(), table);
  }
  
  public <R extends Record> DeleteWhereStep<R> delete(Table<R> table)
  {
    return new DeleteImpl(configuration(), table);
  }
  
  public Batch batch(Query... queries)
  {
    return new BatchMultiple(configuration(), queries);
  }
  
  public Batch batch(String... queries)
  {
    Query[] result = new Query[queries.length];
    for (int i = 0; i < queries.length; i++) {
      result[i] = query(queries[i]);
    }
    return batch(result);
  }
  
  public Batch batch(Collection<? extends Query> queries)
  {
    return batch((Query[])queries.toArray(new Query[queries.size()]));
  }
  
  public BatchBindStep batch(Query query)
  {
    return new BatchSingle(configuration(), query);
  }
  
  public BatchBindStep batch(String sql)
  {
    return batch(query(sql));
  }
  
  public Batch batch(Query query, Object[]... bindings)
  {
    return batch(query).bind(bindings);
  }
  
  public Batch batch(String sql, Object[]... bindings)
  {
    return batch(query(sql), bindings);
  }
  
  public Batch batchStore(UpdatableRecord<?>... records)
  {
    return new BatchCRUD(configuration(), BatchCRUD.Action.STORE, records);
  }
  
  public Batch batchStore(Collection<? extends UpdatableRecord<?>> records)
  {
    return batchStore((UpdatableRecord[])records.toArray(new UpdatableRecord[records.size()]));
  }
  
  public Batch batchInsert(TableRecord<?>... records)
  {
    return new BatchCRUD(configuration(), BatchCRUD.Action.INSERT, records);
  }
  
  public Batch batchInsert(Collection<? extends TableRecord<?>> records)
  {
    return batchInsert((TableRecord[])records.toArray(new TableRecord[records.size()]));
  }
  
  public Batch batchUpdate(UpdatableRecord<?>... records)
  {
    return new BatchCRUD(configuration(), BatchCRUD.Action.UPDATE, records);
  }
  
  public Batch batchUpdate(Collection<? extends UpdatableRecord<?>> records)
  {
    return batchUpdate((UpdatableRecord[])records.toArray(new UpdatableRecord[records.size()]));
  }
  
  public Batch batchDelete(UpdatableRecord<?>... records)
  {
    return new BatchCRUD(configuration(), BatchCRUD.Action.DELETE, records);
  }
  
  public Batch batchDelete(Collection<? extends UpdatableRecord<?>> records)
  {
    return batchDelete((UpdatableRecord[])records.toArray(new UpdatableRecord[records.size()]));
  }
  
  public CreateViewAsStep<Record> createView(String viewName, String... fieldNames)
  {
    return createView(DSL.tableByName(new String[] { viewName }), Utils.fieldsByName(viewName, fieldNames));
  }
  
  public CreateViewAsStep<Record> createView(Table<?> view, Field<?>... fields)
  {
    return new CreateViewImpl(configuration(), view, fields);
  }
  
  public CreateTableAsStep<Record> createTable(String tableName)
  {
    return createTable(DSL.tableByName(new String[] { tableName }));
  }
  
  public CreateTableAsStep<Record> createTable(Table<?> table)
  {
    return new CreateTableImpl(configuration(), table);
  }
  
  public CreateIndexStep createIndex(String index)
  {
    return new CreateIndexImpl(configuration(), index);
  }
  
  public CreateSequenceFinalStep createSequence(Sequence<?> sequence)
  {
    return new CreateSequenceImpl(configuration(), sequence);
  }
  
  public CreateSequenceFinalStep createSequence(String sequence)
  {
    return createSequence(DSL.sequenceByName(new String[] { sequence }));
  }
  
  public <T extends Number> AlterSequenceRestartStep<T> alterSequence(Sequence<T> sequence)
  {
    return new AlterSequenceImpl(configuration(), sequence);
  }
  
  public AlterSequenceRestartStep<BigInteger> alterSequence(String sequence)
  {
    return alterSequence(DSL.sequenceByName(new String[] { sequence }));
  }
  
  public AlterTableStep alterTable(Table<?> table)
  {
    return new AlterTableImpl(configuration(), table);
  }
  
  public AlterTableStep alterTable(String table)
  {
    return alterTable(DSL.tableByName(new String[] { table }));
  }
  
  public DropViewFinalStep dropView(Table<?> table)
  {
    return new DropViewImpl(configuration(), table);
  }
  
  public DropViewFinalStep dropView(String table)
  {
    return dropView(DSL.tableByName(new String[] { table }));
  }
  
  public DropViewFinalStep dropViewIfExists(Table<?> table)
  {
    return new DropViewImpl(configuration(), table, true);
  }
  
  public DropViewFinalStep dropViewIfExists(String table)
  {
    return dropViewIfExists(DSL.tableByName(new String[] { table }));
  }
  
  public DropTableStep dropTable(Table<?> table)
  {
    return new DropTableImpl(configuration(), table);
  }
  
  public DropTableStep dropTable(String table)
  {
    return dropTable(DSL.tableByName(new String[] { table }));
  }
  
  public DropTableStep dropTableIfExists(Table<?> table)
  {
    return new DropTableImpl(configuration(), table, true);
  }
  
  public DropTableStep dropTableIfExists(String table)
  {
    return dropTableIfExists(DSL.tableByName(new String[] { table }));
  }
  
  public DropIndexFinalStep dropIndex(String index)
  {
    return new DropIndexImpl(configuration(), index);
  }
  
  public DropIndexFinalStep dropIndexIfExists(String index)
  {
    return new DropIndexImpl(configuration(), index, true);
  }
  
  public DropSequenceFinalStep dropSequence(Sequence<?> sequence)
  {
    return new DropSequenceImpl(configuration(), sequence);
  }
  
  public DropSequenceFinalStep dropSequence(String sequence)
  {
    return dropSequence(DSL.sequenceByName(new String[] { sequence }));
  }
  
  public DropSequenceFinalStep dropSequenceIfExists(Sequence<?> sequence)
  {
    return new DropSequenceImpl(configuration(), sequence, true);
  }
  
  public DropSequenceFinalStep dropSequenceIfExists(String sequence)
  {
    return dropSequenceIfExists(DSL.sequenceByName(new String[] { sequence }));
  }
  
  public <R extends Record> TruncateIdentityStep<R> truncate(Table<R> table)
  {
    return new TruncateImpl(configuration(), table);
  }
  
  public BigInteger lastID()
  {
    switch (configuration().dialect().family())
    {
    case DERBY: 
      Field<BigInteger> field = DSL.field("identity_val_local()", BigInteger.class);
      return (BigInteger)select(field).fetchOne(field);
    case H2: 
    case HSQLDB: 
      Field<BigInteger> field = DSL.field("identity()", BigInteger.class);
      return (BigInteger)select(field).fetchOne(field);
    case CUBRID: 
    case MARIADB: 
    case MYSQL: 
      Field<BigInteger> field = DSL.field("last_insert_id()", BigInteger.class);
      return (BigInteger)select(field).fetchOne(field);
    case SQLITE: 
      Field<BigInteger> field = DSL.field("last_insert_rowid()", BigInteger.class);
      return (BigInteger)select(field).fetchOne(field);
    }
    throw new SQLDialectNotSupportedException("identity functionality not supported by " + configuration().dialect());
  }
  
  public BigInteger nextval(String sequence)
  {
    return (BigInteger)nextval(DSL.sequenceByName(new String[] { sequence }));
  }
  
  public <T extends Number> T nextval(Sequence<T> sequence)
  {
    Field<T> nextval = sequence.nextval();
    return (Number)select(nextval).fetchOne(nextval);
  }
  
  public BigInteger currval(String sequence)
  {
    return (BigInteger)currval(DSL.sequenceByName(new String[] { sequence }));
  }
  
  public <T extends Number> T currval(Sequence<T> sequence)
  {
    Field<T> currval = sequence.currval();
    return (Number)select(currval).fetchOne(currval);
  }
  
  public Record newRecord(Field<?>... fields)
  {
    return Utils.newRecord(false, RecordImpl.class, fields, configuration()).operate(null);
  }
  
  public <T1> Record1<T1> newRecord(Field<T1> field1)
  {
    return (Record1)newRecord(new Field[] { field1 });
  }
  
  public <T1, T2> Record2<T1, T2> newRecord(Field<T1> field1, Field<T2> field2)
  {
    return (Record2)newRecord(new Field[] { field1, field2 });
  }
  
  public <T1, T2, T3> Record3<T1, T2, T3> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return (Record3)newRecord(new Field[] { field1, field2, field3 });
  }
  
  public <T1, T2, T3, T4> Record4<T1, T2, T3, T4> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return (Record4)newRecord(new Field[] { field1, field2, field3, field4 });
  }
  
  public <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return (Record5)newRecord(new Field[] { field1, field2, field3, field4, field5 });
  }
  
  public <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return (Record6)newRecord(new Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return (Record7)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return (Record8)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return (Record9)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return (Record10)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return (Record11)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return (Record12)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return (Record13)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return (Record14)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return (Record15)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return (Record16)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return (Record17)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return (Record18)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return (Record19)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return (Record20)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return (Record21)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> newRecord(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return (Record22)newRecord(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  public <R extends UDTRecord<R>> R newRecord(UDT<R> type)
  {
    return (UDTRecord)Utils.newRecord(false, type, configuration()).operate(null);
  }
  
  public <R extends Record> R newRecord(Table<R> table)
  {
    return Utils.newRecord(false, table, configuration()).operate(null);
  }
  
  public <R extends Record> R newRecord(Table<R> table, final Object source)
  {
    Utils.newRecord(false, table, configuration()).operate(new RecordOperation()
    {
      public R operate(R record)
      {
        record.from(source);
        return record;
      }
    });
  }
  
  public <R extends Record> Result<R> newResult(Table<R> table)
  {
    return new ResultImpl(configuration(), table.fields());
  }
  
  public Result<Record> newResult(Field<?>... fields)
  {
    return new ResultImpl(configuration(), fields);
  }
  
  public <T1> Result<Record1<T1>> newResult(Field<T1> field1)
  {
    return newResult(new Field[] { field1 });
  }
  
  public <T1, T2> Result<Record2<T1, T2>> newResult(Field<T1> field1, Field<T2> field2)
  {
    return newResult(new Field[] { field1, field2 });
  }
  
  public <T1, T2, T3> Result<Record3<T1, T2, T3>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3)
  {
    return newResult(new Field[] { field1, field2, field3 });
  }
  
  public <T1, T2, T3, T4> Result<Record4<T1, T2, T3, T4>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4)
  {
    return newResult(new Field[] { field1, field2, field3, field4 });
  }
  
  public <T1, T2, T3, T4, T5> Result<Record5<T1, T2, T3, T4, T5>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5 });
  }
  
  public <T1, T2, T3, T4, T5, T6> Result<Record6<T1, T2, T3, T4, T5, T6>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7> Result<Record7<T1, T2, T3, T4, T5, T6, T7>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8> Result<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Result<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Result<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Result<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Result<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Result<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Result<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Result<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Result<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Result<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Result<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Result<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Result<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Result<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21 });
  }
  
  public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Result<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> newResult(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22)
  {
    return newResult(new Field[] { field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22 });
  }
  
  public <R extends Record> Result<R> fetch(ResultQuery<R> query)
  {
    Configuration previous = Utils.getConfiguration(query);
    try
    {
      query.attach(configuration());
      return query.fetch();
    }
    finally
    {
      query.attach(previous);
    }
  }
  
  public <R extends Record> Cursor<R> fetchLazy(ResultQuery<R> query)
  {
    Configuration previous = Utils.getConfiguration(query);
    try
    {
      query.attach(configuration());
      return query.fetchLazy();
    }
    finally
    {
      query.attach(previous);
    }
  }
  
  public <R extends Record> List<Result<Record>> fetchMany(ResultQuery<R> query)
  {
    Configuration previous = Utils.getConfiguration(query);
    try
    {
      query.attach(configuration());
      return query.fetchMany();
    }
    finally
    {
      query.attach(previous);
    }
  }
  
  public <R extends Record> R fetchOne(ResultQuery<R> query)
  {
    Configuration previous = Utils.getConfiguration(query);
    try
    {
      query.attach(configuration());
      return query.fetchOne();
    }
    finally
    {
      query.attach(previous);
    }
  }
  
  public <T, R extends Record1<T>> T fetchValue(ResultQuery<R> query)
  {
    Configuration previous = Utils.getConfiguration(query);
    try
    {
      query.attach(configuration());
      return (T)value1((Record1)fetchOne(query));
    }
    finally
    {
      query.attach(previous);
    }
  }
  
  public <T, R extends Record1<T>> List<T> fetchValues(ResultQuery<R> query)
  {
    return fetch(query).getValues(0);
  }
  
  private final <T, R extends Record1<T>> T value1(R record)
  {
    if (record == null) {
      return null;
    }
    if (record.size() != 1) {
      throw new InvalidResultException("Record contains more than one value : " + record);
    }
    return (T)record.value1();
  }
  
  public int fetchCount(Select<?> query)
  {
    return ((Integer)((Record1)new FetchCount(configuration(), query).fetchOne()).value1()).intValue();
  }
  
  public int fetchCount(Table<?> table)
  {
    return fetchCount(table, DSL.trueCondition());
  }
  
  public int fetchCount(Table<?> table, Condition condition)
  {
    return ((Integer)selectCount().from(table).where(new Condition[] { condition }).fetchOne(0, Integer.TYPE)).intValue();
  }
  
  public boolean fetchExists(Select<?> query)
    throws DataAccessException
  {
    return selectOne().whereExists(query).fetchOne() != null;
  }
  
  public boolean fetchExists(Table<?> table)
    throws DataAccessException
  {
    return fetchExists(table, DSL.trueCondition());
  }
  
  public boolean fetchExists(Table<?> table, Condition condition)
    throws DataAccessException
  {
    return fetchExists(selectOne().from(table).where(new Condition[] { condition }));
  }
  
  public int execute(Query query)
  {
    Configuration previous = Utils.getConfiguration(query);
    try
    {
      query.attach(configuration());
      return query.execute();
    }
    finally
    {
      query.attach(previous);
    }
  }
  
  public <R extends Record> Result<R> fetch(Table<R> table)
  {
    return fetch(table, DSL.trueCondition());
  }
  
  public <R extends Record> Result<R> fetch(Table<R> table, Condition condition)
  {
    return selectFrom(table).where(new Condition[] { condition }).fetch();
  }
  
  public <R extends Record> R fetchOne(Table<R> table)
  {
    return Utils.fetchOne(fetchLazy(table));
  }
  
  public <R extends Record> R fetchOne(Table<R> table, Condition condition)
  {
    return Utils.fetchOne(fetchLazy(table, condition));
  }
  
  public <R extends Record> R fetchAny(Table<R> table)
  {
    return Utils.filterOne(selectFrom(table).limit(1).fetch());
  }
  
  public <R extends Record> R fetchAny(Table<R> table, Condition condition)
  {
    return Utils.filterOne(selectFrom(table).where(new Condition[] { condition }).limit(1).fetch());
  }
  
  public <R extends Record> Cursor<R> fetchLazy(Table<R> table)
  {
    return fetchLazy(table, DSL.trueCondition());
  }
  
  public <R extends Record> Cursor<R> fetchLazy(Table<R> table, Condition condition)
  {
    return selectFrom(table).where(new Condition[] { condition }).fetchLazy();
  }
  
  public <R extends TableRecord<R>> int executeInsert(R record)
  {
    InsertQuery<R> insert = insertQuery(record.getTable());
    insert.setRecord(record);
    return insert.execute();
  }
  
  public <R extends UpdatableRecord<R>> int executeUpdate(R record)
  {
    UpdateQuery<R> update = updateQuery(record.getTable());
    Utils.addConditions(update, record, record.getTable().getPrimaryKey().getFieldsArray());
    update.setRecord(record);
    return update.execute();
  }
  
  public <R extends TableRecord<R>, T> int executeUpdate(R record, Condition condition)
  {
    UpdateQuery<R> update = updateQuery(record.getTable());
    update.addConditions(new Condition[] { condition });
    update.setRecord(record);
    return update.execute();
  }
  
  public <R extends UpdatableRecord<R>> int executeDelete(R record)
  {
    DeleteQuery<R> delete = deleteQuery(record.getTable());
    Utils.addConditions(delete, record, record.getTable().getPrimaryKey().getFieldsArray());
    return delete.execute();
  }
  
  public <R extends TableRecord<R>, T> int executeDelete(R record, Condition condition)
  {
    DeleteQuery<R> delete = deleteQuery(record.getTable());
    delete.addConditions(new Condition[] { condition });
    return delete.execute();
  }
  
  static
  {
    try
    {
      Class.forName(SQLDataType.class.getName());
    }
    catch (Exception localException) {}
  }
  
  public String toString()
  {
    return configuration().toString();
  }
}
