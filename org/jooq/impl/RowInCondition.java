package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jooq.Clause;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Operator;
import org.jooq.QueryPartInternal;
import org.jooq.Row;
import org.jooq.SQLDialect;

class RowInCondition
  extends AbstractCondition
{
  private static final long serialVersionUID = -1806139685201770706L;
  private static final Clause[] CLAUSES_IN = { Clause.CONDITION, Clause.CONDITION_IN };
  private static final Clause[] CLAUSES_IN_NOT = { Clause.CONDITION, Clause.CONDITION_NOT_IN };
  private final Row left;
  private final QueryPartList<? extends Row> right;
  private final Comparator comparator;
  
  RowInCondition(Row left, QueryPartList<? extends Row> right, Comparator comparator)
  {
    this.left = left;
    this.right = right;
    this.comparator = comparator;
  }
  
  public final void accept(Context<?> ctx)
  {
    delegate(ctx.configuration()).accept(ctx);
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return delegate(ctx.configuration()).clauses(ctx);
  }
  
  private final QueryPartInternal delegate(Configuration configuration)
  {
    if (Arrays.asList(new SQLDialect[] { SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.SQLITE }).contains(configuration.dialect().family()))
    {
      List<Condition> conditions = new ArrayList();
      for (Row row : this.right) {
        conditions.add(new RowCondition(this.left, row, Comparator.EQUALS));
      }
      Object result = new CombinedCondition(Operator.OR, conditions);
      if (this.comparator == Comparator.NOT_IN) {
        result = ((Condition)result).not();
      }
      return (QueryPartInternal)result;
    }
    return new Native(null);
  }
  
  private class Native
    extends AbstractCondition
  {
    private static final long serialVersionUID = -7019193803316281371L;
    
    private Native() {}
    
    public final void accept(Context<?> ctx)
    {
      ctx.visit(RowInCondition.this.left).sql(" ").keyword(RowInCondition.this.comparator.toSQL()).sql(" (").visit(RowInCondition.this.right).sql(")");
    }
    
    public final Clause[] clauses(Context<?> ctx)
    {
      return RowInCondition.this.comparator == Comparator.IN ? RowInCondition.CLAUSES_IN : RowInCondition.CLAUSES_IN_NOT;
    }
  }
}
