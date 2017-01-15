package org.jooq.impl;

import java.util.Arrays;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Context;
import org.jooq.Field;

class InCondition<T>
  extends AbstractCondition
{
  private static final long serialVersionUID = -1653924248576930761L;
  private static final int IN_LIMIT = 1000;
  private static final Clause[] CLAUSES_IN = { Clause.CONDITION, Clause.CONDITION_IN };
  private static final Clause[] CLAUSES_IN_NOT = { Clause.CONDITION, Clause.CONDITION_NOT_IN };
  private final Field<T> field;
  private final Field<?>[] values;
  private final Comparator comparator;
  
  InCondition(Field<T> field, Field<?>[] values, Comparator comparator)
  {
    this.field = field;
    this.values = values;
    this.comparator = comparator;
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return this.comparator == Comparator.IN ? CLAUSES_IN : CLAUSES_IN_NOT;
  }
  
  public final void accept(Context<?> ctx)
  {
    List<Field<?>> list = Arrays.asList(this.values);
    if (list.size() == 0)
    {
      if (this.comparator == Comparator.IN) {
        ctx.visit(DSL.falseCondition());
      } else {
        ctx.visit(DSL.trueCondition());
      }
    }
    else if (list.size() > 1000) {
      switch (ctx.configuration().dialect().family())
      {
      case FIREBIRD: 
        ctx.sql("(").formatIndentStart().formatNewLine();
        for (int i = 0; i < list.size(); i += 1000)
        {
          if (i > 0) {
            if (this.comparator == Comparator.IN) {
              ctx.formatSeparator().keyword("or").sql(" ");
            } else {
              ctx.formatSeparator().keyword("and").sql(" ");
            }
          }
          toSQLSubValues(ctx, list.subList(i, Math.min(i + 1000, list.size())));
        }
        ctx.formatIndentEnd().formatNewLine().sql(")");
        break;
      default: 
        toSQLSubValues(ctx, list);
        break;
      }
    } else {
      toSQLSubValues(ctx, list);
    }
  }
  
  private void toSQLSubValues(Context<?> ctx, List<Field<?>> subValues)
  {
    ctx.visit(this.field).sql(" ").keyword(this.comparator.toSQL()).sql(" (");
    if (subValues.size() > 1) {
      ctx.formatIndentStart().formatNewLine();
    }
    String separator = "";
    for (Field<?> value : subValues)
    {
      ctx.sql(separator).formatNewLineAfterPrintMargin().visit(value);
      
      separator = ", ";
    }
    if (subValues.size() > 1) {
      ctx.formatIndentEnd().formatNewLine();
    }
    ctx.sql(")");
  }
}
