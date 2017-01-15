package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SQLDialect;

class CompareCondition
  extends AbstractCondition
{
  private static final long serialVersionUID = -747240442279619486L;
  private static final Clause[] CLAUSES = { Clause.CONDITION, Clause.CONDITION_COMPARISON };
  private final Field<?> field1;
  private final Field<?> field2;
  private final Comparator comparator;
  private final Character escape;
  
  CompareCondition(Field<?> field1, Field<?> field2, Comparator comparator)
  {
    this(field1, field2, comparator, null);
  }
  
  CompareCondition(Field<?> field1, Field<?> field2, Comparator comparator, Character escape)
  {
    this.field1 = field1;
    this.field2 = field2;
    this.comparator = comparator;
    this.escape = escape;
  }
  
  public final void accept(Context<?> ctx)
  {
    SQLDialect family = ctx.configuration().dialect().family();
    Field<?> lhs = this.field1;
    Field<?> rhs = this.field2;
    Comparator op = this.comparator;
    if (((op == Comparator.LIKE) || (op == Comparator.NOT_LIKE)) && 
      (this.field1.getType() != String.class)) {
      if (Arrays.asList(new SQLDialect[] { SQLDialect.DERBY, SQLDialect.POSTGRES }).contains(family))
      {
        lhs = lhs.cast(String.class);
        break label160;
      }
    }
    if (((op == Comparator.LIKE_IGNORE_CASE) || (op == Comparator.NOT_LIKE_IGNORE_CASE)) && (SQLDialect.POSTGRES != family))
    {
      lhs = lhs.lower();
      rhs = rhs.lower();
      op = op == Comparator.LIKE_IGNORE_CASE ? Comparator.LIKE : Comparator.NOT_LIKE;
    }
    label160:
    ctx.visit(lhs)
      .sql(" ");
    boolean castRhs = false;
    
    ctx.keyword(op.toSQL()).sql(" ");
    if (castRhs) {
      ctx.keyword("cast").sql("(");
    }
    ctx.visit(rhs);
    if (castRhs) {
      ctx.sql(" ").keyword("as").sql(" ").keyword("varchar").sql("(4000))");
    }
    if (this.escape != null) {
      ctx.sql(" ").keyword("escape").sql(" '").sql(this.escape.charValue()).sql("'");
    }
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
}
