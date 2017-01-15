package org.jooq.impl;

import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.Field;

class Contains<T>
  extends AbstractCondition
{
  private static final long serialVersionUID = 6146303086487338550L;
  private static final Clause[] CLAUSES = { Clause.CONDITION, Clause.CONDITION_COMPARISON };
  private final Field<T> lhs;
  private final Field<T> rhs;
  private final T value;
  
  Contains(Field<T> field, T value)
  {
    this.lhs = field;
    this.rhs = null;
    this.value = value;
  }
  
  Contains(Field<T> field, Field<T> rhs)
  {
    this.lhs = field;
    this.rhs = rhs;
    this.value = null;
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(condition(ctx.configuration()));
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  private final Condition condition(Configuration configuration)
  {
    if (this.lhs.getDataType().isArray()) {
      return new PostgresArrayContains(null);
    }
    Field<String> concat;
    Field<String> concat;
    if (this.rhs == null) {
      concat = DSL.concat(new Field[] { DSL.inline("%"), Utils.escapeForLike(this.value, configuration), DSL.inline("%") });
    } else {
      concat = DSL.concat(new Field[] { DSL.inline("%"), Utils.escapeForLike(this.rhs, configuration), DSL.inline("%") });
    }
    return this.lhs.like(concat, '!');
  }
  
  private class PostgresArrayContains
    extends AbstractCondition
  {
    private static final long serialVersionUID = 8083622843635168388L;
    
    private PostgresArrayContains() {}
    
    public final void accept(Context<?> ctx)
    {
      ctx.visit(Contains.this.lhs).sql(" @> ").visit(rhs());
    }
    
    public final Clause[] clauses(Context<?> ctx)
    {
      return Contains.CLAUSES;
    }
    
    private final Field<T> rhs()
    {
      return Contains.this.rhs == null ? DSL.val(Contains.this.value, Contains.this.lhs) : Contains.this.rhs;
    }
  }
}
