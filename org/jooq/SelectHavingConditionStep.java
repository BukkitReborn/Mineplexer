package org.jooq;

public abstract interface SelectHavingConditionStep<R extends Record>
  extends SelectWindowStep<R>
{
  @Support
  public abstract SelectHavingConditionStep<R> and(Condition paramCondition);
  
  @Support
  public abstract SelectHavingConditionStep<R> and(Field<Boolean> paramField);
  
  @Support
  public abstract SelectHavingConditionStep<R> and(String paramString);
  
  @Support
  public abstract SelectHavingConditionStep<R> and(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectHavingConditionStep<R> and(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectHavingConditionStep<R> andNot(Condition paramCondition);
  
  @Support
  public abstract SelectHavingConditionStep<R> andNot(Field<Boolean> paramField);
  
  @Support
  public abstract SelectHavingConditionStep<R> andExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectHavingConditionStep<R> andNotExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectHavingConditionStep<R> or(Condition paramCondition);
  
  @Support
  public abstract SelectHavingConditionStep<R> or(Field<Boolean> paramField);
  
  @Support
  public abstract SelectHavingConditionStep<R> or(String paramString);
  
  @Support
  public abstract SelectHavingConditionStep<R> or(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectHavingConditionStep<R> or(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectHavingConditionStep<R> orNot(Condition paramCondition);
  
  @Support
  public abstract SelectHavingConditionStep<R> orNot(Field<Boolean> paramField);
  
  @Support
  public abstract SelectHavingConditionStep<R> orExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectHavingConditionStep<R> orNotExists(Select<?> paramSelect);
}
