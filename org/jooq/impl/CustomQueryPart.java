package org.jooq.impl;

import org.jooq.BindContext;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.RenderContext;
import org.jooq.exception.DataAccessException;

public abstract class CustomQueryPart
  extends AbstractQueryPart
{
  private static final long serialVersionUID = -3439681086987884991L;
  private static final Clause[] CLAUSES = { Clause.CUSTOM };
  
  @Deprecated
  public void toSQL(RenderContext context) {}
  
  public void accept(Context<?> ctx)
  {
    if ((ctx instanceof RenderContext)) {
      toSQL((RenderContext)ctx);
    } else {
      bind((BindContext)ctx);
    }
  }
  
  @Deprecated
  public void bind(BindContext context)
    throws DataAccessException
  {}
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public final boolean declaresFields()
  {
    return super.declaresFields();
  }
  
  public final boolean declaresTables()
  {
    return super.declaresTables();
  }
}
