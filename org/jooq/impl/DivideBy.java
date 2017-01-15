package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jooq.Condition;
import org.jooq.ConditionProvider;
import org.jooq.DivideByOnConditionStep;
import org.jooq.DivideByOnStep;
import org.jooq.Field;
import org.jooq.Operator;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectSelectStep;
import org.jooq.Table;

class DivideBy
  implements DivideByOnStep, DivideByOnConditionStep
{
  private final Table<?> dividend;
  private final Table<?> divisor;
  private final ConditionProviderImpl condition;
  private final QueryPartList<Field<?>> returning;
  
  DivideBy(Table<?> dividend, Table<?> divisor)
  {
    this.dividend = dividend;
    this.divisor = divisor;
    
    this.condition = new ConditionProviderImpl();
    this.returning = new QueryPartList();
  }
  
  private final Table<Record> table()
  {
    ConditionProviderImpl selfJoin = new ConditionProviderImpl();
    List<Field<?>> select = new ArrayList();
    Table<?> outer = this.dividend.as("dividend");
    for (Field<?> field : this.returning)
    {
      Field<?> outerField = outer.field(field);
      if (outerField == null)
      {
        select.add(field);
      }
      else
      {
        select.add(outerField);
        selfJoin(selfJoin, outer, this.dividend, field);
      }
    }
    return DSL.selectDistinct(select).from(outer).whereNotExists(DSL.selectOne().from(this.divisor).whereNotExists(DSL.selectOne().from(this.dividend).where(new Condition[] { selfJoin }).and(this.condition))).asTable();
  }
  
  private final <T> void selfJoin(ConditionProvider selfJoin, Table<?> outer, Table<?> inner, Field<T> field)
  {
    Field<T> outerField = outer.field(field);
    Field<T> innerField = inner.field(field);
    if ((outerField == null) || (innerField == null)) {
      return;
    }
    selfJoin.addConditions(new Condition[] { outerField.equal(innerField) });
  }
  
  public final DivideByOnConditionStep on(Condition... conditions)
  {
    this.condition.addConditions(conditions);
    return this;
  }
  
  public final DivideByOnConditionStep on(Field<Boolean> c)
  {
    return on(new Condition[] { DSL.condition(c) });
  }
  
  public final DivideByOnConditionStep on(String sql)
  {
    and(sql);
    return this;
  }
  
  public final DivideByOnConditionStep on(String sql, Object... bindings)
  {
    and(sql, bindings);
    return this;
  }
  
  public final DivideByOnConditionStep on(String sql, QueryPart... parts)
  {
    and(sql, parts);
    return this;
  }
  
  public final Table<Record> returning(Field<?>... fields)
  {
    return returning(Arrays.asList(fields));
  }
  
  public final Table<Record> returning(Collection<? extends Field<?>> fields)
  {
    this.returning.addAll(fields);
    return table();
  }
  
  public final DivideByOnConditionStep and(Condition c)
  {
    this.condition.addConditions(new Condition[] { c });
    return this;
  }
  
  public final DivideByOnConditionStep and(Field<Boolean> c)
  {
    return and(DSL.condition(c));
  }
  
  public final DivideByOnConditionStep and(String sql)
  {
    return and(DSL.condition(sql));
  }
  
  public final DivideByOnConditionStep and(String sql, Object... bindings)
  {
    return and(DSL.condition(sql, bindings));
  }
  
  public final DivideByOnConditionStep and(String sql, QueryPart... parts)
  {
    return and(DSL.condition(sql, parts));
  }
  
  public final DivideByOnConditionStep andNot(Condition c)
  {
    return and(c.not());
  }
  
  public final DivideByOnConditionStep andNot(Field<Boolean> c)
  {
    return andNot(DSL.condition(c));
  }
  
  public final DivideByOnConditionStep andExists(Select<?> select)
  {
    return and(DSL.exists(select));
  }
  
  public final DivideByOnConditionStep andNotExists(Select<?> select)
  {
    return and(DSL.notExists(select));
  }
  
  public final DivideByOnConditionStep or(Condition c)
  {
    this.condition.addConditions(Operator.OR, new Condition[] { c });
    return this;
  }
  
  public final DivideByOnConditionStep or(Field<Boolean> c)
  {
    return or(DSL.condition(c));
  }
  
  public final DivideByOnConditionStep or(String sql)
  {
    return or(DSL.condition(sql));
  }
  
  public final DivideByOnConditionStep or(String sql, Object... bindings)
  {
    return or(DSL.condition(sql, bindings));
  }
  
  public final DivideByOnConditionStep or(String sql, QueryPart... parts)
  {
    return or(DSL.condition(sql, parts));
  }
  
  public final DivideByOnConditionStep orNot(Condition c)
  {
    return or(c.not());
  }
  
  public final DivideByOnConditionStep orNot(Field<Boolean> c)
  {
    return orNot(DSL.condition(c));
  }
  
  public final DivideByOnConditionStep orExists(Select<?> select)
  {
    return or(DSL.exists(select));
  }
  
  public final DivideByOnConditionStep orNotExists(Select<?> select)
  {
    return or(DSL.notExists(select));
  }
}
