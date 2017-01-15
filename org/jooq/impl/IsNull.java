package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Context;
import org.jooq.Field;

class IsNull
  extends AbstractCondition
{
  private static final long serialVersionUID = -747240442279619486L;
  private static final Clause[] CLAUSES_NULL = { Clause.CONDITION, Clause.CONDITION_IS_NULL };
  private static final Clause[] CLAUSES_NULL_NOT = { Clause.CONDITION, Clause.CONDITION_IS_NOT_NULL };
  private final Field<?> field;
  private final boolean isNull;
  
  IsNull(Field<?> field, boolean isNull)
  {
    this.field = field;
    this.isNull = isNull;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(this.field).sql(" ").keyword(this.isNull ? "is null" : "is not null");
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return this.isNull ? CLAUSES_NULL : CLAUSES_NULL_NOT;
  }
}
