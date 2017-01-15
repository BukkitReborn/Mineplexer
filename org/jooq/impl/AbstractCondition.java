package org.jooq.impl;

import java.util.Arrays;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Select;

abstract class AbstractCondition
  extends AbstractQueryPart
  implements Condition
{
  private static final long serialVersionUID = -6683692251799468624L;
  private static final Clause[] CLAUSES = { Clause.CONDITION };
  
  public Clause[] clauses(Context<?> ctx)
  {
    return CLAUSES;
  }
  
  public final Condition and(Condition other)
  {
    return new CombinedCondition(Operator.AND, Arrays.asList(new Condition[] { this, other }));
  }
  
  public Condition and(Field<Boolean> other)
  {
    return and(DSL.condition(other));
  }
  
  public final Condition or(Condition other)
  {
    return new CombinedCondition(Operator.OR, Arrays.asList(new Condition[] { this, other }));
  }
  
  public final Condition or(Field<Boolean> other)
  {
    return or(DSL.condition(other));
  }
  
  public final Condition and(String sql)
  {
    return and(DSL.condition(sql));
  }
  
  public final Condition and(String sql, Object... bindings)
  {
    return and(DSL.condition(sql, bindings));
  }
  
  public final Condition and(String sql, QueryPart... parts)
  {
    return and(DSL.condition(sql, parts));
  }
  
  public final Condition or(String sql)
  {
    return or(DSL.condition(sql));
  }
  
  public final Condition or(String sql, Object... bindings)
  {
    return or(DSL.condition(sql, bindings));
  }
  
  public final Condition or(String sql, QueryPart... parts)
  {
    return or(DSL.condition(sql, parts));
  }
  
  public final Condition andNot(Condition other)
  {
    return and(other.not());
  }
  
  public final Condition andNot(Field<Boolean> other)
  {
    return andNot(DSL.condition(other));
  }
  
  public final Condition orNot(Condition other)
  {
    return or(other.not());
  }
  
  public final Condition orNot(Field<Boolean> other)
  {
    return orNot(DSL.condition(other));
  }
  
  public final Condition andExists(Select<?> select)
  {
    return and(DSL.exists(select));
  }
  
  public final Condition andNotExists(Select<?> select)
  {
    return and(DSL.notExists(select));
  }
  
  public final Condition orExists(Select<?> select)
  {
    return or(DSL.exists(select));
  }
  
  public final Condition orNotExists(Select<?> select)
  {
    return or(DSL.notExists(select));
  }
  
  public final Condition not()
  {
    return new NotCondition(this);
  }
}
