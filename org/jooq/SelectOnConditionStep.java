package org.jooq;

public abstract interface SelectOnConditionStep<R extends Record>
  extends SelectJoinStep<R>
{
  @Support
  public abstract SelectOnConditionStep<R> and(Condition paramCondition);
  
  @Support
  public abstract SelectOnConditionStep<R> and(Field<Boolean> paramField);
  
  @Support
  public abstract SelectOnConditionStep<R> and(String paramString);
  
  @Support
  public abstract SelectOnConditionStep<R> and(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectOnConditionStep<R> and(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectOnConditionStep<R> andNot(Condition paramCondition);
  
  @Support
  public abstract SelectOnConditionStep<R> andNot(Field<Boolean> paramField);
  
  @Support
  public abstract SelectOnConditionStep<R> andExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectOnConditionStep<R> andNotExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectOnConditionStep<R> or(Condition paramCondition);
  
  @Support
  public abstract SelectOnConditionStep<R> or(Field<Boolean> paramField);
  
  @Support
  public abstract SelectOnConditionStep<R> or(String paramString);
  
  @Support
  public abstract SelectOnConditionStep<R> or(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectOnConditionStep<R> or(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectOnConditionStep<R> orNot(Condition paramCondition);
  
  @Support
  public abstract SelectOnConditionStep<R> orNot(Field<Boolean> paramField);
  
  @Support
  public abstract SelectOnConditionStep<R> orExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectOnConditionStep<R> orNotExists(Select<?> paramSelect);
}
