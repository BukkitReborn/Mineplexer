package org.jooq.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.jooq.AttachableInternal;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.Param;
import org.jooq.Query;
import org.jooq.RenderContext;
import org.jooq.conf.ParamType;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.ControlFlowSignal;
import org.jooq.exception.DetachedException;
import org.jooq.tools.JooqLogger;

abstract class AbstractQuery
  extends AbstractQueryPart
  implements Query, AttachableInternal
{
  private static final long serialVersionUID = -8046199737354507547L;
  private static final JooqLogger log = JooqLogger.getLogger(AbstractQuery.class);
  private Configuration configuration;
  private int timeout;
  private boolean keepStatement;
  private transient PreparedStatement statement;
  private transient String sql;
  
  AbstractQuery(Configuration configuration)
  {
    this.configuration = configuration;
  }
  
  public final void attach(Configuration c)
  {
    this.configuration = c;
  }
  
  public final void detach()
  {
    attach(null);
  }
  
  public final Configuration configuration()
  {
    return this.configuration;
  }
  
  final void toSQLSemiColon(RenderContext ctx) {}
  
  public final List<Object> getBindValues()
  {
    return create().extractBindValues(this);
  }
  
  public final Map<String, Param<?>> getParams()
  {
    return create().extractParams(this);
  }
  
  public final Param<?> getParam(String name)
  {
    return create().extractParam(this, name);
  }
  
  public Query bind(String param, Object value)
  {
    try
    {
      int index = Integer.valueOf(param).intValue();
      return bind(index, value);
    }
    catch (NumberFormatException e)
    {
      Param<?> p = getParam(param);
      if (p == null) {
        throw new IllegalArgumentException("No such parameter : " + param);
      }
      p.setConverted(value);
      closeIfNecessary(p);
    }
    return this;
  }
  
  public Query bind(int index, Object value)
  {
    Param<?>[] params = (Param[])getParams().values().toArray(new Param[0]);
    if ((index < 1) || (index > params.length)) {
      throw new IllegalArgumentException("Index out of range for Query parameters : " + index);
    }
    Param<?> param = params[(index - 1)];
    param.setConverted(value);
    closeIfNecessary(param);
    return this;
  }
  
  private final void closeIfNecessary(Param<?> param)
  {
    if ((keepStatement()) && (this.statement != null)) {
      if (param.isInline()) {
        close();
      } else if (SettingsTools.getParamType(configuration().settings()) == ParamType.INLINED) {
        close();
      }
    }
  }
  
  public Query queryTimeout(int t)
  {
    this.timeout = t;
    return this;
  }
  
  public Query keepStatement(boolean k)
  {
    this.keepStatement = k;
    return this;
  }
  
  protected final boolean keepStatement()
  {
    return this.keepStatement;
  }
  
  public final void close()
  {
    if (this.statement != null) {
      try
      {
        this.statement.close();
        this.statement = null;
      }
      catch (SQLException e)
      {
        throw Utils.translate(this.sql, e);
      }
    }
  }
  
  public final void cancel()
  {
    if (this.statement != null) {
      try
      {
        this.statement.cancel();
      }
      catch (SQLException e)
      {
        throw Utils.translate(this.sql, e);
      }
    }
  }
  
  public final int execute()
  {
    if (isExecutable())
    {
      Configuration c = configuration();
      
      DefaultExecuteContext ctx = new DefaultExecuteContext(c, this);
      ExecuteListener listener = new ExecuteListeners(ctx);
      
      int result = 0;
      try
      {
        if ((keepStatement()) && (this.statement != null))
        {
          ctx.sql(this.sql);
          ctx.statement(this.statement);
          
          ctx.connection(c.connectionProvider(), this.statement.getConnection());
        }
        else
        {
          listener.renderStart(ctx);
          ctx.sql(getSQL0(ctx));
          listener.renderEnd(ctx);
          
          this.sql = ctx.sql();
          if (ctx.connection() == null) {
            throw new DetachedException("Cannot execute query. No Connection configured");
          }
          listener.prepareStart(ctx);
          prepare(ctx);
          listener.prepareEnd(ctx);
          
          this.statement = ctx.statement();
        }
        if (this.timeout != 0) {
          ctx.statement().setQueryTimeout(this.timeout);
        }
        if (SettingsTools.executePreparedStatements(c.settings())) {
          if (!Boolean.TRUE.equals(ctx.data("org.jooq.configuration.force-static-statement")))
          {
            listener.bindStart(ctx);
            DSL.using(c).bindContext(ctx.statement()).visit(this);
            listener.bindEnd(ctx);
          }
        }
        result = execute(ctx, listener);
        return result;
      }
      catch (ControlFlowSignal e)
      {
        throw e;
      }
      catch (RuntimeException e)
      {
        ctx.exception(e);
        listener.exception(ctx);
        throw ctx.exception();
      }
      catch (SQLException e)
      {
        ctx.sqlException(e);
        listener.exception(ctx);
        throw ctx.exception();
      }
      finally
      {
        if ((!keepResultSet()) || (ctx.exception() != null)) {
          Utils.safeClose(listener, ctx, keepStatement());
        }
        if (!keepStatement())
        {
          this.statement = null;
          this.sql = null;
        }
      }
    }
    if (log.isDebugEnabled()) {
      log.debug("Query is not executable", this);
    }
    return 0;
  }
  
  protected boolean keepResultSet()
  {
    return false;
  }
  
  protected void prepare(ExecuteContext ctx)
    throws SQLException
  {
    ctx.statement(ctx.connection().prepareStatement(ctx.sql()));
  }
  
  protected int execute(ExecuteContext ctx, ExecuteListener listener)
    throws SQLException
  {
    int result = 0;
    PreparedStatement stmt = ctx.statement();
    try
    {
      listener.executeStart(ctx);
      if (!stmt.execute())
      {
        result = stmt.getUpdateCount();
        ctx.rows(result);
      }
      listener.executeEnd(ctx);
      return result;
    }
    catch (SQLException e)
    {
      Utils.consumeExceptions(ctx.configuration(), stmt, e);
      throw e;
    }
  }
  
  public boolean isExecutable()
  {
    return true;
  }
  
  private final String getSQL0(ExecuteContext ctx)
  {
    String result;
    if (SettingsTools.executePreparedStatements(configuration().settings()))
    {
      String result;
      try
      {
        RenderContext render = new DefaultRenderContext(this.configuration);
        render.data("org.jooq.configuration.count-bind-values", Boolean.valueOf(true));
        result = ((RenderContext)render.visit(this)).render();
      }
      catch (DefaultRenderContext.ForceInlineSignal e)
      {
        String result;
        ctx.data("org.jooq.configuration.force-static-statement", Boolean.valueOf(true));
        result = getSQL(ParamType.INLINED);
      }
    }
    else
    {
      result = getSQL(ParamType.INLINED);
    }
    return result;
  }
  
  public final String getSQL()
  {
    return getSQL(SettingsTools.getParamType(configuration().settings()));
  }
  
  public final String getSQL(ParamType paramType)
  {
    switch (paramType)
    {
    case INDEXED: 
      return create().render(this);
    case INLINED: 
      return create().renderInlined(this);
    case NAMED: 
      return create().renderNamedParams(this);
    case NAMED_OR_INLINED: 
      return create().renderNamedOrInlinedParams(this);
    }
    throw new IllegalArgumentException("ParamType not supported: " + paramType);
  }
  
  @Deprecated
  public final String getSQL(boolean inline)
  {
    return getSQL(inline ? ParamType.INLINED : ParamType.INDEXED);
  }
}
