package org.jooq.impl;

import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Converter;
import org.jooq.Cursor;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.FutureResult;
import org.jooq.GroupField;
import org.jooq.JoinType;
import org.jooq.Operator;
import org.jooq.Param;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.Row;
import org.jooq.Select;
import org.jooq.SelectConditionStep;
import org.jooq.SelectConnectByConditionStep;
import org.jooq.SelectForUpdateOfStep;
import org.jooq.SelectHavingConditionStep;
import org.jooq.SelectIntoStep;
import org.jooq.SelectJoinStep;
import org.jooq.SelectOffsetStep;
import org.jooq.SelectOnConditionStep;
import org.jooq.SelectOnStep;
import org.jooq.SelectOptionalOnStep;
import org.jooq.SelectQuery;
import org.jooq.SelectSeekLimitStep;
import org.jooq.SelectSeekStep1;
import org.jooq.SelectSeekStep10;
import org.jooq.SelectSeekStep11;
import org.jooq.SelectSeekStep12;
import org.jooq.SelectSeekStep13;
import org.jooq.SelectSeekStep14;
import org.jooq.SelectSeekStep15;
import org.jooq.SelectSeekStep16;
import org.jooq.SelectSeekStep17;
import org.jooq.SelectSeekStep18;
import org.jooq.SelectSeekStep19;
import org.jooq.SelectSeekStep2;
import org.jooq.SelectSeekStep20;
import org.jooq.SelectSeekStep21;
import org.jooq.SelectSeekStep22;
import org.jooq.SelectSeekStep3;
import org.jooq.SelectSeekStep4;
import org.jooq.SelectSeekStep5;
import org.jooq.SelectSeekStep6;
import org.jooq.SelectSeekStep7;
import org.jooq.SelectSeekStep8;
import org.jooq.SelectSeekStep9;
import org.jooq.SelectSeekStepN;
import org.jooq.SelectSelectStep;
import org.jooq.SortField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.WindowDefinition;
import org.jooq.exception.DataAccessException;

class SelectImpl<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>
  extends AbstractDelegatingQuery<Select<R>>
  implements SelectSelectStep<R>, SelectOptionalOnStep<R>, SelectOnConditionStep<R>, SelectConditionStep<R>, SelectConnectByConditionStep<R>, SelectHavingConditionStep<R>, SelectSeekStep1<R, T1>, SelectSeekStep2<R, T1, T2>, SelectSeekStep3<R, T1, T2, T3>, SelectSeekStep4<R, T1, T2, T3, T4>, SelectSeekStep5<R, T1, T2, T3, T4, T5>, SelectSeekStep6<R, T1, T2, T3, T4, T5, T6>, SelectSeekStep7<R, T1, T2, T3, T4, T5, T6, T7>, SelectSeekStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>, SelectSeekStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>, SelectSeekStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>, SelectSeekStep11<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>, SelectSeekStep12<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>, SelectSeekStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>, SelectSeekStep14<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, SelectSeekStep15<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, SelectSeekStep16<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, SelectSeekStep17<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, SelectSeekStep18<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, SelectSeekStep19<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, SelectSeekStep20<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, SelectSeekStep21<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>, SelectSeekStep22<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, SelectSeekStepN<R>, SelectSeekLimitStep<R>, SelectOffsetStep<R>, SelectForUpdateOfStep<R>
{
  private static final long serialVersionUID = -5425308887382166448L;
  private transient TableLike<?> joinTable;
  private transient Field<?>[] joinPartitionBy;
  private transient JoinType joinType;
  private transient ConditionProviderImpl joinConditions;
  private transient ConditionStep conditionStep;
  private transient Integer limit;
  private transient Param<Integer> limitParam;
  
  SelectImpl(WithImpl with, Configuration configuration)
  {
    this(with, configuration, false);
  }
  
  SelectImpl(WithImpl with, Configuration configuration, boolean distinct)
  {
    this(new SelectQueryImpl(with, configuration, distinct));
  }
  
  SelectImpl(Select<R> query)
  {
    super(query);
  }
  
  public final SelectQuery<R> getQuery()
  {
    return (SelectQuery)getDelegate();
  }
  
  @Deprecated
  public final int fetchCount()
  {
    return ((Select)getDelegate()).fetchCount();
  }
  
  public final SelectImpl select(Field<?>... fields)
  {
    getQuery().addSelect(fields);
    return this;
  }
  
  public final SelectImpl select(Collection<? extends Field<?>> fields)
  {
    getQuery().addSelect(fields);
    return this;
  }
  
  public final SelectIntoStep<R> on(Field<?>... fields)
  {
    return distinctOn(Arrays.asList(fields));
  }
  
  public final SelectIntoStep<R> on(Collection<? extends Field<?>> fields)
  {
    return distinctOn(fields);
  }
  
  public final SelectIntoStep<R> distinctOn(Field<?>... fields)
  {
    getQuery().addDistinctOn(fields);
    return this;
  }
  
  public final SelectIntoStep<R> distinctOn(Collection<? extends Field<?>> fields)
  {
    getQuery().addDistinctOn(fields);
    return this;
  }
  
  public final SelectImpl into(Table<?> table)
  {
    getQuery().setInto(table);
    return this;
  }
  
  public final SelectImpl hint(String hint)
  {
    getQuery().addHint(hint);
    return this;
  }
  
  public final SelectImpl option(String hint)
  {
    getQuery().addOption(hint);
    return this;
  }
  
  public final SelectImpl from(TableLike<?> table)
  {
    getQuery().addFrom(table);
    return this;
  }
  
  public final SelectImpl from(TableLike<?>... tables)
  {
    getQuery().addFrom(tables);
    return this;
  }
  
  public final SelectImpl from(Collection<? extends TableLike<?>> tables)
  {
    getQuery().addFrom(tables);
    return this;
  }
  
  public final SelectImpl from(String sql)
  {
    return from(DSL.table(sql));
  }
  
  public final SelectImpl from(String sql, Object... bindings)
  {
    return from(DSL.table(sql, bindings));
  }
  
  public final SelectImpl from(String sql, QueryPart... parts)
  {
    return from(DSL.table(sql, parts));
  }
  
  public final SelectImpl where(Condition... conditions)
  {
    this.conditionStep = ConditionStep.WHERE;
    getQuery().addConditions(conditions);
    return this;
  }
  
  public final SelectImpl where(Collection<? extends Condition> conditions)
  {
    this.conditionStep = ConditionStep.WHERE;
    getQuery().addConditions(conditions);
    return this;
  }
  
  public final SelectImpl where(Field<Boolean> condition)
  {
    return where(new Condition[] { DSL.condition(condition) });
  }
  
  public final SelectImpl where(String sql)
  {
    return where(new Condition[] { DSL.condition(sql) });
  }
  
  public final SelectImpl where(String sql, Object... bindings)
  {
    return where(new Condition[] { DSL.condition(sql, bindings) });
  }
  
  public final SelectImpl where(String sql, QueryPart... parts)
  {
    return where(new Condition[] { DSL.condition(sql, parts) });
  }
  
  public final SelectImpl whereExists(Select<?> select)
  {
    this.conditionStep = ConditionStep.WHERE;
    return andExists(select);
  }
  
  public final SelectImpl whereNotExists(Select<?> select)
  {
    this.conditionStep = ConditionStep.WHERE;
    return andNotExists(select);
  }
  
  public final SelectImpl and(Condition condition)
  {
    switch (this.conditionStep)
    {
    case WHERE: 
      getQuery().addConditions(new Condition[] { condition });
      break;
    case CONNECT_BY: 
      getQuery().addConnectBy(condition);
      break;
    case HAVING: 
      getQuery().addHaving(new Condition[] { condition });
      break;
    case ON: 
      this.joinConditions.addConditions(new Condition[] { condition });
    }
    return this;
  }
  
  public final SelectImpl and(Field<Boolean> condition)
  {
    return and(DSL.condition(condition));
  }
  
  public final SelectImpl and(String sql)
  {
    return and(DSL.condition(sql));
  }
  
  public final SelectImpl and(String sql, Object... bindings)
  {
    return and(DSL.condition(sql, bindings));
  }
  
  public final SelectImpl and(String sql, QueryPart... parts)
  {
    return and(DSL.condition(sql, parts));
  }
  
  public final SelectImpl andNot(Condition condition)
  {
    return and(condition.not());
  }
  
  public final SelectImpl andNot(Field<Boolean> condition)
  {
    return andNot(DSL.condition(condition));
  }
  
  public final SelectImpl andExists(Select<?> select)
  {
    return and(DSL.exists(select));
  }
  
  public final SelectImpl andNotExists(Select<?> select)
  {
    return and(DSL.notExists(select));
  }
  
  public final SelectImpl or(Condition condition)
  {
    switch (this.conditionStep)
    {
    case WHERE: 
      getQuery().addConditions(Operator.OR, new Condition[] { condition });
      break;
    case CONNECT_BY: 
      throw new IllegalStateException("Cannot connect conditions for the CONNECT BY clause using the OR operator");
    case HAVING: 
      getQuery().addHaving(Operator.OR, new Condition[] { condition });
      break;
    case ON: 
      this.joinConditions.addConditions(Operator.OR, new Condition[] { condition });
    }
    return this;
  }
  
  public final SelectImpl or(Field<Boolean> condition)
  {
    return or(DSL.condition(condition));
  }
  
  public final SelectImpl or(String sql)
  {
    return or(DSL.condition(sql));
  }
  
  public final SelectImpl or(String sql, Object... bindings)
  {
    return or(DSL.condition(sql, bindings));
  }
  
  public final SelectImpl or(String sql, QueryPart... parts)
  {
    return or(DSL.condition(sql, parts));
  }
  
  public final SelectImpl orNot(Condition condition)
  {
    return or(condition.not());
  }
  
  public final SelectImpl orNot(Field<Boolean> condition)
  {
    return orNot(DSL.condition(condition));
  }
  
  public final SelectImpl orExists(Select<?> select)
  {
    return or(DSL.exists(select));
  }
  
  public final SelectImpl orNotExists(Select<?> select)
  {
    return or(DSL.notExists(select));
  }
  
  public final SelectImpl connectBy(Condition condition)
  {
    this.conditionStep = ConditionStep.CONNECT_BY;
    getQuery().addConnectBy(condition);
    return this;
  }
  
  public final SelectImpl connectBy(Field<Boolean> condition)
  {
    return connectBy(DSL.condition(condition));
  }
  
  public final SelectImpl connectBy(String sql)
  {
    return connectBy(DSL.condition(sql));
  }
  
  public final SelectImpl connectBy(String sql, Object... bindings)
  {
    return connectBy(DSL.condition(sql, bindings));
  }
  
  public final SelectImpl connectBy(String sql, QueryPart... parts)
  {
    return connectBy(DSL.condition(sql, parts));
  }
  
  public final SelectImpl connectByNoCycle(Condition condition)
  {
    this.conditionStep = ConditionStep.CONNECT_BY;
    getQuery().addConnectByNoCycle(condition);
    return this;
  }
  
  public final SelectImpl connectByNoCycle(Field<Boolean> condition)
  {
    return connectByNoCycle(DSL.condition(condition));
  }
  
  public final SelectImpl connectByNoCycle(String sql)
  {
    return connectByNoCycle(DSL.condition(sql));
  }
  
  public final SelectImpl connectByNoCycle(String sql, Object... bindings)
  {
    return connectByNoCycle(DSL.condition(sql, bindings));
  }
  
  public final SelectImpl connectByNoCycle(String sql, QueryPart... parts)
  {
    return connectByNoCycle(DSL.condition(sql, parts));
  }
  
  public final SelectImpl startWith(Condition condition)
  {
    getQuery().setConnectByStartWith(condition);
    return this;
  }
  
  public final SelectImpl startWith(Field<Boolean> condition)
  {
    return startWith(DSL.condition(condition));
  }
  
  public final SelectImpl startWith(String sql)
  {
    return startWith(DSL.condition(sql));
  }
  
  public final SelectImpl startWith(String sql, Object... bindings)
  {
    return startWith(DSL.condition(sql, bindings));
  }
  
  public final SelectImpl startWith(String sql, QueryPart... parts)
  {
    return startWith(DSL.condition(sql, parts));
  }
  
  public final SelectImpl groupBy(GroupField... fields)
  {
    getQuery().addGroupBy(fields);
    return this;
  }
  
  public final SelectImpl groupBy(Collection<? extends GroupField> fields)
  {
    getQuery().addGroupBy(fields);
    return this;
  }
  
  public final SelectSeekStep1 orderBy(Field t1)
  {
    return orderBy(new Field[] { t1 });
  }
  
  public final SelectSeekStep2 orderBy(Field t1, Field t2)
  {
    return orderBy(new Field[] { t1, t2 });
  }
  
  public final SelectSeekStep3 orderBy(Field t1, Field t2, Field t3)
  {
    return orderBy(new Field[] { t1, t2, t3 });
  }
  
  public final SelectSeekStep4 orderBy(Field t1, Field t2, Field t3, Field t4)
  {
    return orderBy(new Field[] { t1, t2, t3, t4 });
  }
  
  public final SelectSeekStep5 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5 });
  }
  
  public final SelectSeekStep6 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6 });
  }
  
  public final SelectSeekStep7 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7 });
  }
  
  public final SelectSeekStep8 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8 });
  }
  
  public final SelectSeekStep9 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
  }
  
  public final SelectSeekStep10 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 });
  }
  
  public final SelectSeekStep11 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 });
  }
  
  public final SelectSeekStep12 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12 });
  }
  
  public final SelectSeekStep13 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13 });
  }
  
  public final SelectSeekStep14 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14 });
  }
  
  public final SelectSeekStep15 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15 });
  }
  
  public final SelectSeekStep16 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16 });
  }
  
  public final SelectSeekStep17 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17 });
  }
  
  public final SelectSeekStep18 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 });
  }
  
  public final SelectSeekStep19 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19 });
  }
  
  public final SelectSeekStep20 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20 });
  }
  
  public final SelectSeekStep21 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21 });
  }
  
  public final SelectSeekStep22 orderBy(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22)
  {
    return orderBy(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22 });
  }
  
  public final SelectImpl orderBy(Field<?>... fields)
  {
    getQuery().addOrderBy(fields);
    return this;
  }
  
  public final SelectSeekStep1 orderBy(SortField t1)
  {
    return orderBy(new SortField[] { t1 });
  }
  
  public final SelectSeekStep2 orderBy(SortField t1, SortField t2)
  {
    return orderBy(new SortField[] { t1, t2 });
  }
  
  public final SelectSeekStep3 orderBy(SortField t1, SortField t2, SortField t3)
  {
    return orderBy(new SortField[] { t1, t2, t3 });
  }
  
  public final SelectSeekStep4 orderBy(SortField t1, SortField t2, SortField t3, SortField t4)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4 });
  }
  
  public final SelectSeekStep5 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5 });
  }
  
  public final SelectSeekStep6 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6 });
  }
  
  public final SelectSeekStep7 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7 });
  }
  
  public final SelectSeekStep8 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8 });
  }
  
  public final SelectSeekStep9 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
  }
  
  public final SelectSeekStep10 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 });
  }
  
  public final SelectSeekStep11 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 });
  }
  
  public final SelectSeekStep12 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12 });
  }
  
  public final SelectSeekStep13 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13 });
  }
  
  public final SelectSeekStep14 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14 });
  }
  
  public final SelectSeekStep15 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15 });
  }
  
  public final SelectSeekStep16 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16 });
  }
  
  public final SelectSeekStep17 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17 });
  }
  
  public final SelectSeekStep18 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 });
  }
  
  public final SelectSeekStep19 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18, SortField t19)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19 });
  }
  
  public final SelectSeekStep20 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18, SortField t19, SortField t20)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20 });
  }
  
  public final SelectSeekStep21 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18, SortField t19, SortField t20, SortField t21)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21 });
  }
  
  public final SelectSeekStep22 orderBy(SortField t1, SortField t2, SortField t3, SortField t4, SortField t5, SortField t6, SortField t7, SortField t8, SortField t9, SortField t10, SortField t11, SortField t12, SortField t13, SortField t14, SortField t15, SortField t16, SortField t17, SortField t18, SortField t19, SortField t20, SortField t21, SortField t22)
  {
    return orderBy(new SortField[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22 });
  }
  
  public final SelectImpl orderBy(SortField<?>... fields)
  {
    getQuery().addOrderBy(fields);
    return this;
  }
  
  public final SelectImpl orderBy(Collection<? extends SortField<?>> fields)
  {
    getQuery().addOrderBy(fields);
    return this;
  }
  
  public final SelectImpl orderBy(int... fieldIndexes)
  {
    getQuery().addOrderBy(fieldIndexes);
    return this;
  }
  
  public final SelectImpl orderSiblingsBy(Field<?>... fields)
  {
    getQuery().addOrderBy(fields);
    getQuery().setOrderBySiblings(true);
    return this;
  }
  
  public final SelectImpl orderSiblingsBy(SortField<?>... fields)
  {
    getQuery().addOrderBy(fields);
    getQuery().setOrderBySiblings(true);
    return this;
  }
  
  public final SelectImpl orderSiblingsBy(Collection<? extends SortField<?>> fields)
  {
    getQuery().addOrderBy(fields);
    getQuery().setOrderBySiblings(true);
    return this;
  }
  
  public final SelectImpl orderSiblingsBy(int... fieldIndexes)
  {
    getQuery().addOrderBy(fieldIndexes);
    getQuery().setOrderBySiblings(true);
    return this;
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1)
  {
    return seek(new Object[] { t1 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1)
  {
    return seekBefore(new Object[] { t1 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1)
  {
    return seekAfter(new Object[] { t1 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2)
  {
    return seek(new Object[] { t1, t2 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2)
  {
    return seekBefore(new Object[] { t1, t2 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2)
  {
    return seekAfter(new Object[] { t1, t2 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3)
  {
    return seek(new Object[] { t1, t2, t3 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3)
  {
    return seekBefore(new Object[] { t1, t2, t3 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3)
  {
    return seekAfter(new Object[] { t1, t2, t3 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4)
  {
    return seek(new Object[] { t1, t2, t3, t4 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21, Object t22)
  {
    return seek(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21, Object t22)
  {
    return seekBefore(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Object t1, Object t2, Object t3, Object t4, Object t5, Object t6, Object t7, Object t8, Object t9, Object t10, Object t11, Object t12, Object t13, Object t14, Object t15, Object t16, Object t17, Object t18, Object t19, Object t20, Object t21, Object t22)
  {
    return seekAfter(new Object[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1)
  {
    return seek(new Field[] { t1 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1)
  {
    return seekBefore(new Field[] { t1 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1)
  {
    return seekAfter(new Field[] { t1 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2)
  {
    return seek(new Field[] { t1, t2 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2)
  {
    return seekBefore(new Field[] { t1, t2 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2)
  {
    return seekAfter(new Field[] { t1, t2 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3)
  {
    return seek(new Field[] { t1, t2, t3 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3)
  {
    return seekBefore(new Field[] { t1, t2, t3 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3)
  {
    return seekAfter(new Field[] { t1, t2, t3 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4)
  {
    return seek(new Field[] { t1, t2, t3, t4 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21 });
  }
  
  public final SelectSeekLimitStep<R> seek(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22)
  {
    return seek(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22 });
  }
  
  public final SelectSeekLimitStep<R> seekBefore(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22)
  {
    return seekBefore(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22 });
  }
  
  public final SelectSeekLimitStep<R> seekAfter(Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22)
  {
    return seekAfter(new Field[] { t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22 });
  }
  
  public final SelectSeekLimitStep<R> seek(Object... values)
  {
    getQuery().addSeekAfter(Utils.fields(values));
    return this;
  }
  
  public final SelectSeekLimitStep<R> seek(Field<?>... fields)
  {
    getQuery().addSeekAfter(fields);
    return this;
  }
  
  public SelectSeekLimitStep<R> seekAfter(Object... values)
  {
    getQuery().addSeekAfter(Utils.fields(values));
    return this;
  }
  
  public SelectSeekLimitStep<R> seekAfter(Field<?>... fields)
  {
    getQuery().addSeekAfter(fields);
    return this;
  }
  
  public SelectSeekLimitStep<R> seekBefore(Object... values)
  {
    getQuery().addSeekBefore(Utils.fields(values));
    return this;
  }
  
  public SelectSeekLimitStep<R> seekBefore(Field<?>... fields)
  {
    getQuery().addSeekBefore(fields);
    return this;
  }
  
  public final SelectImpl limit(int numberOfRows)
  {
    this.limit = Integer.valueOf(numberOfRows);
    this.limitParam = null;
    getQuery().addLimit(numberOfRows);
    return this;
  }
  
  public final SelectImpl limit(Param<Integer> numberOfRows)
  {
    this.limit = null;
    this.limitParam = numberOfRows;
    getQuery().addLimit(numberOfRows);
    return this;
  }
  
  public final SelectImpl limit(int offset, int numberOfRows)
  {
    getQuery().addLimit(offset, numberOfRows);
    return this;
  }
  
  public final SelectImpl limit(int offset, Param<Integer> numberOfRows)
  {
    getQuery().addLimit(offset, numberOfRows);
    return this;
  }
  
  public final SelectImpl limit(Param<Integer> offset, int numberOfRows)
  {
    getQuery().addLimit(offset, numberOfRows);
    return this;
  }
  
  public final SelectImpl limit(Param<Integer> offset, Param<Integer> numberOfRows)
  {
    getQuery().addLimit(offset, numberOfRows);
    return this;
  }
  
  public final SelectImpl offset(int offset)
  {
    if (this.limit != null) {
      getQuery().addLimit(offset, this.limit.intValue());
    } else if (this.limitParam != null) {
      getQuery().addLimit(offset, this.limitParam);
    }
    return this;
  }
  
  public final SelectImpl offset(Param<Integer> offset)
  {
    if (this.limit != null) {
      getQuery().addLimit(offset, this.limit.intValue());
    } else if (this.limitParam != null) {
      getQuery().addLimit(offset, this.limitParam);
    }
    return this;
  }
  
  public final SelectImpl forUpdate()
  {
    getQuery().setForUpdate(true);
    return this;
  }
  
  public final SelectImpl of(Field<?>... fields)
  {
    getQuery().setForUpdateOf(fields);
    return this;
  }
  
  public final SelectImpl of(Collection<? extends Field<?>> fields)
  {
    getQuery().setForUpdateOf(fields);
    return this;
  }
  
  public final SelectImpl of(Table<?>... tables)
  {
    getQuery().setForUpdateOf(tables);
    return this;
  }
  
  public final SelectImpl noWait()
  {
    getQuery().setForUpdateNoWait();
    return this;
  }
  
  public final SelectImpl forShare()
  {
    getQuery().setForShare(true);
    return this;
  }
  
  public final SelectImpl union(Select<? extends R> select)
  {
    return new SelectImpl(((Select)getDelegate()).union(select));
  }
  
  public final SelectImpl unionAll(Select<? extends R> select)
  {
    return new SelectImpl(((Select)getDelegate()).unionAll(select));
  }
  
  public final SelectImpl except(Select<? extends R> select)
  {
    return new SelectImpl(((Select)getDelegate()).except(select));
  }
  
  public final SelectImpl intersect(Select<? extends R> select)
  {
    return new SelectImpl(((Select)getDelegate()).intersect(select));
  }
  
  public final SelectImpl having(Condition... conditions)
  {
    this.conditionStep = ConditionStep.HAVING;
    getQuery().addHaving(conditions);
    return this;
  }
  
  public final SelectImpl having(Collection<? extends Condition> conditions)
  {
    this.conditionStep = ConditionStep.HAVING;
    getQuery().addHaving(conditions);
    return this;
  }
  
  public final SelectImpl having(Field<Boolean> condition)
  {
    return having(new Condition[] { DSL.condition(condition) });
  }
  
  public final SelectImpl having(String sql)
  {
    return having(new Condition[] { DSL.condition(sql) });
  }
  
  public final SelectImpl having(String sql, Object... bindings)
  {
    return having(new Condition[] { DSL.condition(sql, bindings) });
  }
  
  public final SelectImpl having(String sql, QueryPart... parts)
  {
    return having(new Condition[] { DSL.condition(sql, parts) });
  }
  
  public final SelectImpl window(WindowDefinition... definitions)
  {
    getQuery().addWindow(definitions);
    return this;
  }
  
  public final SelectImpl window(Collection<? extends WindowDefinition> definitions)
  {
    getQuery().addWindow(definitions);
    return this;
  }
  
  public final SelectImpl on(Condition... conditions)
  {
    this.conditionStep = ConditionStep.ON;
    this.joinConditions = new ConditionProviderImpl();
    this.joinConditions.addConditions(conditions);
    
    getQuery().addJoin(this.joinTable, this.joinType, new Condition[] { this.joinConditions });
    
    this.joinTable = null;
    this.joinPartitionBy = null;
    this.joinType = null;
    return this;
  }
  
  public final SelectImpl on(Field<Boolean> condition)
  {
    return on(new Condition[] { DSL.condition(condition) });
  }
  
  public final SelectImpl on(String sql)
  {
    return on(new Condition[] { DSL.condition(sql) });
  }
  
  public final SelectImpl on(String sql, Object... bindings)
  {
    return on(new Condition[] { DSL.condition(sql, bindings) });
  }
  
  public final SelectImpl on(String sql, QueryPart... parts)
  {
    return on(new Condition[] { DSL.condition(sql, parts) });
  }
  
  public final SelectImpl onKey()
    throws DataAccessException
  {
    this.conditionStep = ConditionStep.ON;
    getQuery().addJoinOnKey(this.joinTable, this.joinType);
    this.joinTable = null;
    this.joinPartitionBy = null;
    this.joinType = null;
    return this;
  }
  
  public final SelectImpl onKey(TableField<?, ?>... keyFields)
    throws DataAccessException
  {
    this.conditionStep = ConditionStep.ON;
    getQuery().addJoinOnKey(this.joinTable, this.joinType, keyFields);
    this.joinTable = null;
    this.joinPartitionBy = null;
    this.joinType = null;
    return this;
  }
  
  public final SelectImpl onKey(ForeignKey<?, ?> key)
  {
    this.conditionStep = ConditionStep.ON;
    getQuery().addJoinOnKey(this.joinTable, this.joinType, key);
    this.joinTable = null;
    this.joinPartitionBy = null;
    this.joinType = null;
    return this;
  }
  
  public final SelectImpl using(Field<?>... fields)
  {
    return using(Arrays.asList(fields));
  }
  
  public final SelectImpl using(Collection<? extends Field<?>> fields)
  {
    getQuery().addJoinUsing(this.joinTable, this.joinType, fields);
    this.joinTable = null;
    this.joinPartitionBy = null;
    this.joinType = null;
    return this;
  }
  
  public final SelectImpl join(TableLike<?> table)
  {
    return join(table, JoinType.JOIN);
  }
  
  public final SelectImpl leftOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.LEFT_OUTER_JOIN);
  }
  
  public final SelectImpl rightOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.RIGHT_OUTER_JOIN);
  }
  
  public final SelectOnStep<R> fullOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.FULL_OUTER_JOIN);
  }
  
  public final SelectImpl join(TableLike<?> table, JoinType type)
  {
    switch (type)
    {
    case CROSS_JOIN: 
    case NATURAL_JOIN: 
    case NATURAL_LEFT_OUTER_JOIN: 
    case NATURAL_RIGHT_OUTER_JOIN: 
    case CROSS_APPLY: 
    case OUTER_APPLY: 
      getQuery().addJoin(table, type, new Condition[0]);
      this.joinTable = null;
      this.joinPartitionBy = null;
      this.joinType = null;
      
      return this;
    }
    this.conditionStep = ConditionStep.ON;
    this.joinTable = table;
    this.joinType = type;
    this.joinPartitionBy = null;
    this.joinConditions = null;
    
    return this;
  }
  
  public final SelectJoinStep<R> crossJoin(TableLike<?> table)
  {
    return join(table, JoinType.CROSS_JOIN);
  }
  
  public final SelectImpl naturalJoin(TableLike<?> table)
  {
    return join(table, JoinType.NATURAL_JOIN);
  }
  
  public final SelectImpl naturalLeftOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.NATURAL_LEFT_OUTER_JOIN);
  }
  
  public final SelectImpl naturalRightOuterJoin(TableLike<?> table)
  {
    return join(table, JoinType.NATURAL_RIGHT_OUTER_JOIN);
  }
  
  public final SelectImpl join(String sql)
  {
    return join(DSL.table(sql));
  }
  
  public final SelectImpl join(String sql, Object... bindings)
  {
    return join(DSL.table(sql, bindings));
  }
  
  public final SelectImpl join(String sql, QueryPart... parts)
  {
    return join(DSL.table(sql, parts));
  }
  
  public final SelectImpl leftOuterJoin(String sql)
  {
    return leftOuterJoin(DSL.table(sql));
  }
  
  public final SelectImpl leftOuterJoin(String sql, Object... bindings)
  {
    return leftOuterJoin(DSL.table(sql, bindings));
  }
  
  public final SelectImpl leftOuterJoin(String sql, QueryPart... parts)
  {
    return leftOuterJoin(DSL.table(sql, parts));
  }
  
  public final SelectImpl rightOuterJoin(String sql)
  {
    return rightOuterJoin(DSL.table(sql));
  }
  
  public final SelectImpl rightOuterJoin(String sql, Object... bindings)
  {
    return rightOuterJoin(DSL.table(sql, bindings));
  }
  
  public final SelectImpl rightOuterJoin(String sql, QueryPart... parts)
  {
    return rightOuterJoin(DSL.table(sql, parts));
  }
  
  public final SelectOnStep<R> fullOuterJoin(String sql)
  {
    return fullOuterJoin(DSL.table(sql));
  }
  
  public final SelectOnStep<R> fullOuterJoin(String sql, Object... bindings)
  {
    return fullOuterJoin(DSL.table(sql, bindings));
  }
  
  public final SelectOnStep<R> fullOuterJoin(String sql, QueryPart... parts)
  {
    return fullOuterJoin(DSL.table(sql, parts));
  }
  
  public final SelectJoinStep<R> crossJoin(String sql)
  {
    return crossJoin(DSL.table(sql));
  }
  
  public final SelectJoinStep<R> crossJoin(String sql, Object... bindings)
  {
    return crossJoin(DSL.table(sql, bindings));
  }
  
  public final SelectJoinStep<R> crossJoin(String sql, QueryPart... parts)
  {
    return crossJoin(DSL.table(sql, parts));
  }
  
  public final SelectImpl naturalJoin(String sql)
  {
    return naturalJoin(DSL.table(sql));
  }
  
  public final SelectImpl naturalJoin(String sql, Object... bindings)
  {
    return naturalJoin(DSL.table(sql, bindings));
  }
  
  public final SelectImpl naturalJoin(String sql, QueryPart... parts)
  {
    return naturalJoin(DSL.table(sql, parts));
  }
  
  public final SelectImpl naturalLeftOuterJoin(String sql)
  {
    return naturalLeftOuterJoin(DSL.table(sql));
  }
  
  public final SelectImpl naturalLeftOuterJoin(String sql, Object... bindings)
  {
    return naturalLeftOuterJoin(DSL.table(sql, bindings));
  }
  
  public final SelectImpl naturalLeftOuterJoin(String sql, QueryPart... parts)
  {
    return naturalLeftOuterJoin(DSL.table(sql, parts));
  }
  
  public final SelectImpl naturalRightOuterJoin(String sql)
  {
    return naturalRightOuterJoin(DSL.table(sql));
  }
  
  public final SelectImpl naturalRightOuterJoin(String sql, Object... bindings)
  {
    return naturalRightOuterJoin(DSL.table(sql, bindings));
  }
  
  public final SelectImpl naturalRightOuterJoin(String sql, QueryPart... parts)
  {
    return naturalRightOuterJoin(DSL.table(sql, parts));
  }
  
  public final ResultQuery<R> maxRows(int rows)
  {
    return ((Select)getDelegate()).maxRows(rows);
  }
  
  public final ResultQuery<R> fetchSize(int rows)
  {
    return ((Select)getDelegate()).fetchSize(rows);
  }
  
  public final ResultQuery<R> resultSetConcurrency(int resultSetConcurrency)
  {
    return ((Select)getDelegate()).resultSetConcurrency(resultSetConcurrency);
  }
  
  public final ResultQuery<R> resultSetType(int resultSetType)
  {
    return ((Select)getDelegate()).resultSetType(resultSetType);
  }
  
  public final ResultQuery<R> resultSetHoldability(int resultSetHoldability)
  {
    return ((Select)getDelegate()).resultSetHoldability(resultSetHoldability);
  }
  
  public final ResultQuery<R> intern(Field<?>... fields)
  {
    return ((Select)getDelegate()).intern(fields);
  }
  
  public final ResultQuery<R> intern(int... fieldIndexes)
  {
    return ((Select)getDelegate()).intern(fieldIndexes);
  }
  
  public final ResultQuery<R> intern(String... fieldNames)
  {
    return ((Select)getDelegate()).intern(fieldNames);
  }
  
  public final Class<? extends R> getRecordType()
  {
    return ((Select)getDelegate()).getRecordType();
  }
  
  public final List<Field<?>> getSelect()
  {
    return ((Select)getDelegate()).getSelect();
  }
  
  public final Result<R> getResult()
  {
    return ((Select)getDelegate()).getResult();
  }
  
  public final Result<R> fetch()
  {
    return ((Select)getDelegate()).fetch();
  }
  
  public final ResultSet fetchResultSet()
  {
    return ((Select)getDelegate()).fetchResultSet();
  }
  
  public final Iterator<R> iterator()
  {
    return ((Select)getDelegate()).iterator();
  }
  
  public final Cursor<R> fetchLazy()
  {
    return ((Select)getDelegate()).fetchLazy();
  }
  
  @Deprecated
  public final Cursor<R> fetchLazy(int fetchSize)
  {
    return ((Select)getDelegate()).fetchLazy(fetchSize);
  }
  
  public final List<Result<Record>> fetchMany()
  {
    return ((Select)getDelegate()).fetchMany();
  }
  
  public final <T> List<T> fetch(Field<T> field)
  {
    return ((Select)getDelegate()).fetch(field);
  }
  
  public final <T> List<T> fetch(Field<?> field, Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetch(field, type);
  }
  
  public final <T, U> List<U> fetch(Field<T> field, Converter<? super T, U> converter)
  {
    return ((Select)getDelegate()).fetch(field, converter);
  }
  
  public final List<?> fetch(int fieldIndex)
  {
    return ((Select)getDelegate()).fetch(fieldIndex);
  }
  
  public final <T> List<T> fetch(int fieldIndex, Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetch(fieldIndex, type);
  }
  
  public final <U> List<U> fetch(int fieldIndex, Converter<?, U> converter)
  {
    return ((Select)getDelegate()).fetch(fieldIndex, converter);
  }
  
  public final List<?> fetch(String fieldName)
  {
    return ((Select)getDelegate()).fetch(fieldName);
  }
  
  public final <T> List<T> fetch(String fieldName, Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetch(fieldName, type);
  }
  
  public final <U> List<U> fetch(String fieldName, Converter<?, U> converter)
  {
    return ((Select)getDelegate()).fetch(fieldName, converter);
  }
  
  public final <T> T fetchOne(Field<T> field)
  {
    return (T)((Select)getDelegate()).fetchOne(field);
  }
  
  public final <T> T fetchOne(Field<?> field, Class<? extends T> type)
  {
    return (T)((Select)getDelegate()).fetchOne(field, type);
  }
  
  public final <T, U> U fetchOne(Field<T> field, Converter<? super T, U> converter)
  {
    return (U)((Select)getDelegate()).fetchOne(field, converter);
  }
  
  public final Object fetchOne(int fieldIndex)
  {
    return ((Select)getDelegate()).fetchOne(fieldIndex);
  }
  
  public final <T> T fetchOne(int fieldIndex, Class<? extends T> type)
  {
    return (T)((Select)getDelegate()).fetchOne(fieldIndex, type);
  }
  
  public final <U> U fetchOne(int fieldIndex, Converter<?, U> converter)
  {
    return (U)((Select)getDelegate()).fetchOne(fieldIndex, converter);
  }
  
  public final Object fetchOne(String fieldName)
  {
    return ((Select)getDelegate()).fetchOne(fieldName);
  }
  
  public final <T> T fetchOne(String fieldName, Class<? extends T> type)
  {
    return (T)((Select)getDelegate()).fetchOne(fieldName, type);
  }
  
  public final <U> U fetchOne(String fieldName, Converter<?, U> converter)
  {
    return (U)((Select)getDelegate()).fetchOne(fieldName, converter);
  }
  
  public final R fetchOne()
  {
    return ((Select)getDelegate()).fetchOne();
  }
  
  public final Map<String, Object> fetchOneMap()
  {
    return ((Select)getDelegate()).fetchOneMap();
  }
  
  public final Object[] fetchOneArray()
  {
    return ((Select)getDelegate()).fetchOneArray();
  }
  
  public final <E> E fetchOneInto(Class<? extends E> type)
  {
    return (E)((Select)getDelegate()).fetchOneInto(type);
  }
  
  public final <Z extends Record> Z fetchOneInto(Table<Z> table)
  {
    return ((Select)getDelegate()).fetchOneInto(table);
  }
  
  public final <T> T fetchAny(Field<T> field)
  {
    return (T)((Select)getDelegate()).fetchAny(field);
  }
  
  public final <T> T fetchAny(Field<?> field, Class<? extends T> type)
  {
    return (T)((Select)getDelegate()).fetchAny(field, type);
  }
  
  public final <T, U> U fetchAny(Field<T> field, Converter<? super T, U> converter)
  {
    return (U)((Select)getDelegate()).fetchAny(field, converter);
  }
  
  public final Object fetchAny(int fieldIndex)
  {
    return ((Select)getDelegate()).fetchAny(fieldIndex);
  }
  
  public final <T> T fetchAny(int fieldIndex, Class<? extends T> type)
  {
    return (T)((Select)getDelegate()).fetchAny(fieldIndex, type);
  }
  
  public final <U> U fetchAny(int fieldIndex, Converter<?, U> converter)
  {
    return (U)((Select)getDelegate()).fetchAny(fieldIndex, converter);
  }
  
  public final Object fetchAny(String fieldName)
  {
    return ((Select)getDelegate()).fetchAny(fieldName);
  }
  
  public final <T> T fetchAny(String fieldName, Class<? extends T> type)
  {
    return (T)((Select)getDelegate()).fetchAny(fieldName, type);
  }
  
  public final <U> U fetchAny(String fieldName, Converter<?, U> converter)
  {
    return (U)((Select)getDelegate()).fetchAny(fieldName, converter);
  }
  
  public final R fetchAny()
  {
    return ((Select)getDelegate()).fetchAny();
  }
  
  public final Map<String, Object> fetchAnyMap()
  {
    return ((Select)getDelegate()).fetchAnyMap();
  }
  
  public final Object[] fetchAnyArray()
  {
    return ((Select)getDelegate()).fetchAnyArray();
  }
  
  public final <E> E fetchAnyInto(Class<? extends E> type)
  {
    return (E)((Select)getDelegate()).fetchAnyInto(type);
  }
  
  public final <Z extends Record> Z fetchAnyInto(Table<Z> table)
  {
    return ((Select)getDelegate()).fetchAnyInto(table);
  }
  
  public final <K> Map<K, R> fetchMap(Field<K> key)
  {
    return ((Select)getDelegate()).fetchMap(key);
  }
  
  public final <K, V> Map<K, V> fetchMap(Field<K> key, Field<V> value)
  {
    return ((Select)getDelegate()).fetchMap(key, value);
  }
  
  public final Map<Record, R> fetchMap(Field<?>[] keys)
  {
    return ((Select)getDelegate()).fetchMap(keys);
  }
  
  public final <E> Map<List<?>, E> fetchMap(Field<?>[] keys, Class<? extends E> type)
  {
    return ((Select)getDelegate()).fetchMap(keys, type);
  }
  
  public final <E> Map<List<?>, E> fetchMap(Field<?>[] keys, RecordMapper<? super R, E> mapper)
  {
    return ((Select)getDelegate()).fetchMap(keys, mapper);
  }
  
  public final <S extends Record> Map<S, R> fetchMap(Table<S> table)
  {
    return ((Select)getDelegate()).fetchMap(table);
  }
  
  public final <E, S extends Record> Map<S, E> fetchMap(Table<S> table, Class<? extends E> type)
  {
    return ((Select)getDelegate()).fetchMap(table, type);
  }
  
  public final <E, S extends Record> Map<S, E> fetchMap(Table<S> table, RecordMapper<? super R, E> mapper)
  {
    return ((Select)getDelegate()).fetchMap(table, mapper);
  }
  
  public final <K, E> Map<K, E> fetchMap(Field<K> key, Class<? extends E> type)
  {
    return ((Select)getDelegate()).fetchMap(key, type);
  }
  
  public final <K, E> Map<K, E> fetchMap(Field<K> key, RecordMapper<? super R, E> mapper)
  {
    return ((Select)getDelegate()).fetchMap(key, mapper);
  }
  
  public final List<Map<String, Object>> fetchMaps()
  {
    return ((Select)getDelegate()).fetchMaps();
  }
  
  public final <K> Map<K, Result<R>> fetchGroups(Field<K> key)
  {
    return ((Select)getDelegate()).fetchGroups(key);
  }
  
  public final <K, V> Map<K, List<V>> fetchGroups(Field<K> key, Field<V> value)
  {
    return ((Select)getDelegate()).fetchGroups(key, value);
  }
  
  public final Map<Record, Result<R>> fetchGroups(Field<?>[] keys)
  {
    return ((Select)getDelegate()).fetchGroups(keys);
  }
  
  public final <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys, Class<? extends E> type)
  {
    return ((Select)getDelegate()).fetchGroups(keys, type);
  }
  
  public final <E> Map<Record, List<E>> fetchGroups(Field<?>[] keys, RecordMapper<? super R, E> mapper)
  {
    return ((Select)getDelegate()).fetchGroups(keys, mapper);
  }
  
  public final <S extends Record> Map<S, Result<R>> fetchGroups(Table<S> table)
  {
    return ((Select)getDelegate()).fetchGroups(table);
  }
  
  public final <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, Class<? extends E> type)
  {
    return ((Select)getDelegate()).fetchGroups(table, type);
  }
  
  public final <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> table, RecordMapper<? super R, E> mapper)
  {
    return ((Select)getDelegate()).fetchGroups(table, mapper);
  }
  
  public final <K, E> Map<K, List<E>> fetchGroups(Field<K> key, Class<? extends E> type)
  {
    return ((Select)getDelegate()).fetchGroups(key, type);
  }
  
  public final <K, E> Map<K, List<E>> fetchGroups(Field<K> key, RecordMapper<? super R, E> mapper)
  {
    return ((Select)getDelegate()).fetchGroups(key, mapper);
  }
  
  public final Object[][] fetchArrays()
  {
    return ((Select)getDelegate()).fetchArrays();
  }
  
  public final Object[] fetchArray(int fieldIndex)
  {
    return ((Select)getDelegate()).fetchArray(fieldIndex);
  }
  
  public final <T> T[] fetchArray(int fieldIndex, Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetchArray(fieldIndex, type);
  }
  
  public final <U> U[] fetchArray(int fieldIndex, Converter<?, U> converter)
  {
    return ((Select)getDelegate()).fetchArray(fieldIndex, converter);
  }
  
  public final Object[] fetchArray(String fieldName)
  {
    return ((Select)getDelegate()).fetchArray(fieldName);
  }
  
  public final <T> T[] fetchArray(String fieldName, Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetchArray(fieldName, type);
  }
  
  public final <U> U[] fetchArray(String fieldName, Converter<?, U> converter)
  {
    return ((Select)getDelegate()).fetchArray(fieldName, converter);
  }
  
  public final <T> T[] fetchArray(Field<T> field)
  {
    return ((Select)getDelegate()).fetchArray(field);
  }
  
  public final <T> T[] fetchArray(Field<?> field, Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetchArray(field, type);
  }
  
  public final <T, U> U[] fetchArray(Field<T> field, Converter<? super T, U> converter)
  {
    return ((Select)getDelegate()).fetchArray(field, converter);
  }
  
  public final Set<?> fetchSet(int fieldIndex)
  {
    return ((Select)getDelegate()).fetchSet(fieldIndex);
  }
  
  public final <T> Set<T> fetchSet(int fieldIndex, Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetchSet(fieldIndex, type);
  }
  
  public final <U> Set<U> fetchSet(int fieldIndex, Converter<?, U> converter)
  {
    return ((Select)getDelegate()).fetchSet(fieldIndex, converter);
  }
  
  public final Set<?> fetchSet(String fieldName)
  {
    return ((Select)getDelegate()).fetchSet(fieldName);
  }
  
  public final <T> Set<T> fetchSet(String fieldName, Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetchSet(fieldName, type);
  }
  
  public final <U> Set<U> fetchSet(String fieldName, Converter<?, U> converter)
  {
    return ((Select)getDelegate()).fetchSet(fieldName, converter);
  }
  
  public final <T> Set<T> fetchSet(Field<T> field)
  {
    return ((Select)getDelegate()).fetchSet(field);
  }
  
  public final <T> Set<T> fetchSet(Field<?> field, Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetchSet(field, type);
  }
  
  public final <T, U> Set<U> fetchSet(Field<T> field, Converter<? super T, U> converter)
  {
    return ((Select)getDelegate()).fetchSet(field, converter);
  }
  
  public final <T> List<T> fetchInto(Class<? extends T> type)
  {
    return ((Select)getDelegate()).fetchInto(type);
  }
  
  public final <Z extends Record> Result<Z> fetchInto(Table<Z> table)
  {
    return ((Select)getDelegate()).fetchInto(table);
  }
  
  public final <H extends RecordHandler<? super R>> H fetchInto(H handler)
  {
    return ((Select)getDelegate()).fetchInto(handler);
  }
  
  public final <E> List<E> fetch(RecordMapper<? super R, E> mapper)
  {
    return ((Select)getDelegate()).fetch(mapper);
  }
  
  @Deprecated
  public final FutureResult<R> fetchLater()
  {
    return ((Select)getDelegate()).fetchLater();
  }
  
  @Deprecated
  public final FutureResult<R> fetchLater(ExecutorService executor)
  {
    return ((Select)getDelegate()).fetchLater(executor);
  }
  
  public final Table<R> asTable()
  {
    return ((Select)getDelegate()).asTable();
  }
  
  public final Table<R> asTable(String alias)
  {
    return ((Select)getDelegate()).asTable(alias);
  }
  
  public final Table<R> asTable(String alias, String... fieldAliases)
  {
    return ((Select)getDelegate()).asTable(alias, fieldAliases);
  }
  
  public final <T> Field<T> asField()
  {
    return ((Select)getDelegate()).asField();
  }
  
  public final <T> Field<T> asField(String alias)
  {
    return ((Select)getDelegate()).asField(alias);
  }
  
  public final Row fieldsRow()
  {
    return ((Select)getDelegate()).fieldsRow();
  }
  
  public final <T> Field<T> field(Field<T> field)
  {
    return ((Select)getDelegate()).field(field);
  }
  
  public final Field<?> field(String string)
  {
    return ((Select)getDelegate()).field(string);
  }
  
  public final Field<?> field(int index)
  {
    return ((Select)getDelegate()).field(index);
  }
  
  public final Field<?>[] fields()
  {
    return ((Select)getDelegate()).fields();
  }
  
  private static enum ConditionStep
  {
    ON,  WHERE,  CONNECT_BY,  HAVING;
    
    private ConditionStep() {}
  }
}
