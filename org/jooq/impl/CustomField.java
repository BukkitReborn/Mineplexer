package org.jooq.impl;

import org.jooq.BindContext;
import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.RenderContext;
import org.jooq.exception.DataAccessException;

public abstract class CustomField<T>
  extends AbstractField<T>
{
  private static final long serialVersionUID = -1778024624798672262L;
  private static final Clause[] CLAUSES = { Clause.CUSTOM };
  
  protected CustomField(String name, DataType<T> type)
  {
    super(name, type);
  }
  
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
  
  public final Field<T> as(String alias)
  {
    return super.as(alias);
  }
  
  public final Field<T> add(Field<?> value)
  {
    return super.add(value);
  }
  
  public final Field<T> mul(Field<? extends Number> value)
  {
    return super.mul(value);
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
