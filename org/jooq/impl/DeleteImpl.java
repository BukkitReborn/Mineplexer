package org.jooq.impl;

import java.util.Collection;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DeleteConditionStep;
import org.jooq.DeleteWhereStep;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.Table;

class DeleteImpl<R extends Record>
  extends AbstractDelegatingQuery<DeleteQueryImpl<R>>
  implements DeleteWhereStep<R>, DeleteConditionStep<R>
{
  private static final long serialVersionUID = 2747566322757517382L;
  
  DeleteImpl(Configuration configuration, Table<R> table)
  {
    super(new DeleteQueryImpl(configuration, table));
  }
  
  public final DeleteImpl<R> where(Condition... conditions)
  {
    ((DeleteQueryImpl)getDelegate()).addConditions(conditions);
    return this;
  }
  
  public final DeleteImpl<R> where(Collection<? extends Condition> conditions)
  {
    ((DeleteQueryImpl)getDelegate()).addConditions(conditions);
    return this;
  }
  
  public final DeleteImpl<R> where(Field<Boolean> condition)
  {
    return where(new Condition[] { DSL.condition(condition) });
  }
  
  public final DeleteImpl<R> where(String sql)
  {
    return where(new Condition[] { DSL.condition(sql) });
  }
  
  public final DeleteImpl<R> where(String sql, Object... bindings)
  {
    return where(new Condition[] { DSL.condition(sql, bindings) });
  }
  
  public final DeleteImpl<R> where(String sql, QueryPart... parts)
  {
    return where(new Condition[] { DSL.condition(sql, parts) });
  }
  
  public final DeleteImpl<R> whereExists(Select<?> select)
  {
    return andExists(select);
  }
  
  public final DeleteImpl<R> whereNotExists(Select<?> select)
  {
    return andNotExists(select);
  }
  
  public final DeleteImpl<R> and(Condition condition)
  {
    ((DeleteQueryImpl)getDelegate()).addConditions(new Condition[] { condition });
    return this;
  }
  
  public final DeleteImpl<R> and(Field<Boolean> condition)
  {
    return and(DSL.condition(condition));
  }
  
  public final DeleteImpl<R> and(String sql)
  {
    return and(DSL.condition(sql));
  }
  
  public final DeleteImpl<R> and(String sql, Object... bindings)
  {
    return and(DSL.condition(sql, bindings));
  }
  
  public final DeleteImpl<R> and(String sql, QueryPart... parts)
  {
    return and(DSL.condition(sql, parts));
  }
  
  public final DeleteImpl<R> andNot(Condition condition)
  {
    return and(condition.not());
  }
  
  public final DeleteImpl<R> andNot(Field<Boolean> condition)
  {
    return andNot(DSL.condition(condition));
  }
  
  public final DeleteImpl<R> andExists(Select<?> select)
  {
    return and(DSL.exists(select));
  }
  
  public final DeleteImpl<R> andNotExists(Select<?> select)
  {
    return and(DSL.notExists(select));
  }
  
  public final DeleteImpl<R> or(Condition condition)
  {
    ((DeleteQueryImpl)getDelegate()).addConditions(Operator.OR, new Condition[] { condition });
    return this;
  }
  
  public final DeleteImpl<R> or(Field<Boolean> condition)
  {
    return or(DSL.condition(condition));
  }
  
  public final DeleteImpl<R> or(String sql)
  {
    return or(DSL.condition(sql));
  }
  
  public final DeleteImpl<R> or(String sql, Object... bindings)
  {
    return or(DSL.condition(sql, bindings));
  }
  
  public final DeleteImpl<R> or(String sql, QueryPart... parts)
  {
    return or(DSL.condition(sql, parts));
  }
  
  public final DeleteImpl<R> orNot(Condition condition)
  {
    return or(condition.not());
  }
  
  public final DeleteImpl<R> orNot(Field<Boolean> condition)
  {
    return orNot(DSL.condition(condition));
  }
  
  public final DeleteImpl<R> orExists(Select<?> select)
  {
    return or(DSL.exists(select));
  }
  
  public final DeleteImpl<R> orNotExists(Select<?> select)
  {
    return or(DSL.notExists(select));
  }
}
