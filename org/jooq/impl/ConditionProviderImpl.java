package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Select;

class ConditionProviderImpl
  extends AbstractQueryPart
  implements ConditionProvider, Condition
{
  private static final long serialVersionUID = 6073328960551062973L;
  private Condition condition;
  
  final Condition getWhere()
  {
    if (this.condition == null) {
      return DSL.trueCondition();
    }
    return this.condition;
  }
  
  public final void addConditions(Condition... conditions)
  {
    addConditions(Operator.AND, conditions);
  }
  
  public final void addConditions(Collection<? extends Condition> conditions)
  {
    addConditions(Operator.AND, conditions);
  }
  
  public final void addConditions(Operator operator, Condition... conditions)
  {
    addConditions(operator, Arrays.asList(conditions));
  }
  
  public final void addConditions(Operator operator, Collection<? extends Condition> conditions)
  {
    if (!conditions.isEmpty())
    {
      Condition c;
      Condition c;
      if (conditions.size() == 1) {
        c = (Condition)conditions.iterator().next();
      } else {
        c = new CombinedCondition(operator, conditions);
      }
      if ((getWhere() instanceof TrueCondition)) {
        this.condition = c;
      } else {
        this.condition = new CombinedCondition(operator, Arrays.asList(new Condition[] { getWhere(), c }));
      }
    }
  }
  
  public final void accept(Context<?> ctx)
  {
    ctx.visit(getWhere());
  }
  
  public final Clause[] clauses(Context<?> ctx)
  {
    return null;
  }
  
  public final Condition and(Condition other)
  {
    return getWhere().and(other);
  }
  
  public final Condition and(Field<Boolean> other)
  {
    return getWhere().and(other);
  }
  
  public final Condition and(String sql)
  {
    return getWhere().and(sql);
  }
  
  public final Condition and(String sql, Object... bindings)
  {
    return getWhere().and(sql, bindings);
  }
  
  public final Condition and(String sql, QueryPart... parts)
  {
    return getWhere().and(sql, parts);
  }
  
  public final Condition andNot(Condition other)
  {
    return getWhere().andNot(other);
  }
  
  public final Condition andNot(Field<Boolean> other)
  {
    return getWhere().andNot(other);
  }
  
  public final Condition andExists(Select<?> select)
  {
    return getWhere().andExists(select);
  }
  
  public final Condition andNotExists(Select<?> select)
  {
    return getWhere().andNotExists(select);
  }
  
  public final Condition or(Condition other)
  {
    return getWhere().or(other);
  }
  
  public final Condition or(Field<Boolean> other)
  {
    return getWhere().or(other);
  }
  
  public final Condition or(String sql)
  {
    return getWhere().or(sql);
  }
  
  public final Condition or(String sql, Object... bindings)
  {
    return getWhere().or(sql, bindings);
  }
  
  public final Condition or(String sql, QueryPart... parts)
  {
    return getWhere().or(sql, parts);
  }
  
  public final Condition orNot(Condition other)
  {
    return getWhere().orNot(other);
  }
  
  public final Condition orNot(Field<Boolean> other)
  {
    return getWhere().orNot(other);
  }
  
  public final Condition orExists(Select<?> select)
  {
    return getWhere().orExists(select);
  }
  
  public final Condition orNotExists(Select<?> select)
  {
    return getWhere().orNotExists(select);
  }
  
  public final Condition not()
  {
    return getWhere().not();
  }
}
