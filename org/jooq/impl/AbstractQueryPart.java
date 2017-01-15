package org.jooq.impl;

import java.sql.SQLException;
import org.jooq.BindContext;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DSLContext;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;
import org.jooq.RenderContext;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.SQLDialectNotSupportedException;

abstract class AbstractQueryPart
  implements QueryPartInternal
{
  private static final long serialVersionUID = 2078114876079493107L;
  
  Configuration configuration()
  {
    return new DefaultConfiguration();
  }
  
  @Deprecated
  public void toSQL(RenderContext ctx)
  {
    accept(ctx);
  }
  
  public void accept(Context<?> ctx)
  {
    if ((ctx instanceof RenderContext)) {
      toSQL((RenderContext)ctx);
    } else {
      bind((BindContext)ctx);
    }
  }
  
  @Deprecated
  public void bind(BindContext ctx)
    throws DataAccessException
  {
    accept(ctx);
  }
  
  public boolean declaresFields()
  {
    return false;
  }
  
  public boolean declaresTables()
  {
    return false;
  }
  
  public boolean declaresWindows()
  {
    return false;
  }
  
  public boolean declaresCTE()
  {
    return false;
  }
  
  public boolean equals(Object that)
  {
    if (this == that) {
      return true;
    }
    if ((that instanceof QueryPart))
    {
      String sql1 = create().renderInlined(this);
      String sql2 = create().renderInlined((QueryPart)that);
      
      return sql1.equals(sql2);
    }
    return false;
  }
  
  public int hashCode()
  {
    return create().renderInlined(this).hashCode();
  }
  
  public String toString()
  {
    try
    {
      return create(configuration().derive(SettingsTools.clone(configuration().settings()).withRenderFormatted(Boolean.valueOf(true)))).renderInlined(this);
    }
    catch (SQLDialectNotSupportedException e)
    {
      return "[ ... " + e.getMessage() + " ... ]";
    }
  }
  
  protected final DSLContext create()
  {
    return create(configuration());
  }
  
  protected final DSLContext create(Configuration configuration)
  {
    return DSL.using(configuration);
  }
  
  protected final DSLContext create(Context<?> ctx)
  {
    return DSL.using(ctx.configuration());
  }
  
  protected final DataAccessException translate(String sql, SQLException e)
  {
    return Utils.translate(sql, e);
  }
}
