package org.jooq.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.Row;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.StoreQuery;
import org.jooq.Table;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.Settings;
import org.jooq.tools.jdbc.JDBCUtils;
import org.jooq.util.sqlite.SQLiteDSL;

abstract class AbstractStoreQuery<R extends Record>
  extends AbstractQuery
  implements StoreQuery<R>
{
  private static final long serialVersionUID = 6864591335823160569L;
  final Table<R> into;
  final QueryPartList<Field<?>> returning;
  Result<R> returned;
  
  AbstractStoreQuery(Configuration configuration, Table<R> into)
  {
    super(configuration);
    
    this.into = into;
    this.returning = new QueryPartList();
  }
  
  protected abstract Map<Field<?>, Field<?>> getValues();
  
  final Table<R> getInto()
  {
    return this.into;
  }
  
  public final void setRecord(R record)
  {
    for (int i = 0; i < record.size(); i++) {
      if (record.changed(i)) {
        addValue(record.field(i), record.getValue(i));
      }
    }
  }
  
  final <T> void addValue(R record, Field<T> field)
  {
    addValue(field, record.getValue(field));
  }
  
  public final <T> void addValue(Field<T> field, T value)
  {
    getValues().put(field, Utils.field(value, field));
  }
  
  public final <T> void addValue(Field<T> field, Field<T> value)
  {
    getValues().put(field, Utils.field(value, field));
  }
  
  public final void setReturning()
  {
    setReturning(getInto().fields());
  }
  
  public final void setReturning(Identity<R, ? extends Number> identity)
  {
    if (identity != null) {
      setReturning(new Field[] { identity.getField() });
    }
  }
  
  public final void setReturning(Field<?>... fields)
  {
    setReturning(Arrays.asList(fields));
  }
  
  public final void setReturning(Collection<? extends Field<?>> fields)
  {
    this.returning.clear();
    this.returning.addAll(fields);
  }
  
  public final R getReturnedRecord()
  {
    if (getReturnedRecords().size() == 0) {
      return null;
    }
    return (Record)getReturnedRecords().get(0);
  }
  
  public final Result<R> getReturnedRecords()
  {
    if (this.returned == null) {
      this.returned = new ResultImpl(configuration(), this.returning);
    }
    return this.returned;
  }
  
  public final void accept(Context<?> ctx)
  {
    accept0(ctx);
  }
  
  abstract void accept0(Context<?> paramContext);
  
  class FinalTable
    extends CustomQueryPart
  {
    private static final long serialVersionUID = 2722190100084947406L;
    
    FinalTable() {}
    
    public final void accept(Context<?> ctx)
    {
      ctx.keyword("final table").sql(" (").formatIndentStart().formatNewLine().visit(AbstractStoreQuery.this).formatIndentEnd().formatNewLine().sql(")");
    }
  }
  
  final void toSQLReturning(Context<?> ctx)
  {
    if (!this.returning.isEmpty()) {
      switch (ctx.configuration().dialect())
      {
      case FIREBIRD: 
      case POSTGRES: 
        ctx.formatSeparator().keyword("returning").sql(" ").visit(this.returning);
        break;
      }
    }
  }
  
  protected final void prepare(ExecuteContext ctx)
    throws SQLException
  {
    Connection connection = ctx.connection();
    if (this.returning.isEmpty())
    {
      super.prepare(ctx);
      return;
    }
    switch (ctx.configuration().dialect().family())
    {
    case FIREBIRD: 
    case POSTGRES: 
    case SQLITE: 
    case CUBRID: 
      super.prepare(ctx);
      return;
    case DERBY: 
    case H2: 
    case MARIADB: 
    case MYSQL: 
      ctx.statement(connection.prepareStatement(ctx.sql(), 1));
      return;
    }
    List<String> names = new ArrayList();
    RenderNameStyle style = configuration().settings().getRenderNameStyle();
    for (Field<?> field : this.returning) {
      if (style == RenderNameStyle.UPPER) {
        names.add(field.getName().toUpperCase());
      } else if (style == RenderNameStyle.LOWER) {
        names.add(field.getName().toLowerCase());
      } else {
        names.add(field.getName());
      }
    }
    ctx.statement(connection.prepareStatement(ctx.sql(), (String[])names.toArray(new String[names.size()])));
  }
  
  protected final int execute(ExecuteContext ctx, ExecuteListener listener)
    throws SQLException
  {
    if (this.returning.isEmpty()) {
      return super.execute(ctx, listener);
    }
    int result = 1;
    switch (ctx.configuration().dialect().family())
    {
    case SQLITE: 
      listener.executeStart(ctx);
      result = ctx.statement().executeUpdate();
      ctx.rows(result);
      listener.executeEnd(ctx);
      
      DSLContext create = DSL.using(ctx.configuration());
      
      this.returned = create.select(this.returning).from(getInto()).where(new Condition[] {SQLiteDSL.rowid().equal(SQLiteDSL.rowid().getDataType().convert(create.lastID())) }).fetchInto(getInto());
      
      return result;
    case CUBRID: 
      listener.executeStart(ctx);
      result = ctx.statement().executeUpdate();
      ctx.rows(result);
      listener.executeEnd(ctx);
      
      selectReturning(ctx.configuration(), new Object[] { create(ctx.configuration()).lastID() });
      return result;
    case DERBY: 
    case H2: 
    case MARIADB: 
    case MYSQL: 
      listener.executeStart(ctx);
      result = ctx.statement().executeUpdate();
      ctx.rows(result);
      listener.executeEnd(ctx);
      
      ResultSet rs = ctx.statement().getGeneratedKeys();
      try
      {
        List<Object> list = new ArrayList();
        if (rs != null) {
          while (rs.next()) {
            list.add(rs.getObject(1));
          }
        }
        selectReturning(ctx.configuration(), list.toArray());
        return result;
      }
      finally
      {
        JDBCUtils.safeClose(rs);
      }
    case FIREBIRD: 
    case POSTGRES: 
      listener.executeStart(ctx);
      ResultSet rs = ctx.statement().executeQuery();
      listener.executeEnd(ctx);
      break;
    }
    listener.executeStart(ctx);
    result = ctx.statement().executeUpdate();
    ctx.rows(result);
    listener.executeEnd(ctx);
    
    ResultSet rs = ctx.statement().getGeneratedKeys();
    
    ExecuteContext ctx2 = new DefaultExecuteContext(ctx.configuration());
    Object listener2 = new ExecuteListeners(ctx2);
    
    ctx2.resultSet(rs);
    this.returned = new CursorImpl(ctx2, (ExecuteListener)listener2, Utils.fieldArray(this.returning), null, false, true).fetch();
    if (getInto().fields().length > 0) {
      this.returned = this.returned.into(getInto());
    }
    return result;
  }
  
  private final void selectReturning(Configuration configuration, Object... values)
  {
    if ((values != null) && (values.length > 0)) {
      if (this.into.getIdentity() != null)
      {
        final Field<Number> field = this.into.getIdentity().getField();
        Number[] ids = new Number[values.length];
        for (int i = 0; i < values.length; i++) {
          ids[i] = ((Number)field.getDataType().convert(values[i]));
        }
        if ((this.returning.size() == 1) && (new Fields(this.returning).field(field) != null)) {
          for (final Number id : ids) {
            getReturnedRecords().add(
              Utils.newRecord(true, this.into, configuration)
              .operate(new RecordOperation()
              {
                public R operate(R record)
                  throws RuntimeException
                {
                  int index = record.fieldsRow().indexOf(field);
                  
                  ((AbstractRecord)record).values[index] = id;
                  ((AbstractRecord)record).originals[index] = id;
                  
                  return record;
                }
              }));
          }
        } else {
          this.returned = create(configuration).select(this.returning).from(this.into).where(new Condition[] {field.in(ids) }).fetchInto(this.into);
        }
      }
    }
  }
}
