package org.jooq;

import org.jooq.exception.DataAccessException;

public abstract interface QueryPartInternal
  extends QueryPart
{
  public abstract void accept(Context<?> paramContext);
  
  @Deprecated
  public abstract void toSQL(RenderContext paramRenderContext);
  
  @Deprecated
  public abstract void bind(BindContext paramBindContext)
    throws DataAccessException;
  
  public abstract Clause[] clauses(Context<?> paramContext);
  
  public abstract boolean declaresFields();
  
  public abstract boolean declaresTables();
  
  public abstract boolean declaresWindows();
  
  public abstract boolean declaresCTE();
}
