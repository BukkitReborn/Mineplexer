package org.jooq;

public abstract interface SelectConditionStep<R extends Record>
  extends SelectConnectByStep<R>
{
  @Support
  public abstract SelectConditionStep<R> and(Condition paramCondition);
  
  @Support
  public abstract SelectConditionStep<R> and(Field<Boolean> paramField);
  
  @Support
  public abstract SelectConditionStep<R> and(String paramString);
  
  @Support
  public abstract SelectConditionStep<R> and(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectConditionStep<R> and(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectConditionStep<R> andNot(Condition paramCondition);
  
  @Support
  public abstract SelectConditionStep<R> andNot(Field<Boolean> paramField);
  
  @Support
  public abstract SelectConditionStep<R> andExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectConditionStep<R> andNotExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectConditionStep<R> or(Condition paramCondition);
  
  @Support
  public abstract SelectConditionStep<R> or(Field<Boolean> paramField);
  
  @Support
  public abstract SelectConditionStep<R> or(String paramString);
  
  @Support
  public abstract SelectConditionStep<R> or(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectConditionStep<R> or(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectConditionStep<R> orNot(Condition paramCondition);
  
  @Support
  public abstract SelectConditionStep<R> orNot(Field<Boolean> paramField);
  
  @Support
  public abstract SelectConditionStep<R> orExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectConditionStep<R> orNotExists(Select<?> paramSelect);
}
