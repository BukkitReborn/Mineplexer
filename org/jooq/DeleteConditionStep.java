package org.jooq;

public abstract interface DeleteConditionStep<R extends Record>
  extends DeleteFinalStep<R>
{
  @Support
  public abstract DeleteConditionStep<R> and(Condition paramCondition);
  
  @Support
  public abstract DeleteConditionStep<R> and(Field<Boolean> paramField);
  
  @Support
  public abstract DeleteConditionStep<R> and(String paramString);
  
  @Support
  public abstract DeleteConditionStep<R> and(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract DeleteConditionStep<R> and(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract DeleteConditionStep<R> andNot(Condition paramCondition);
  
  @Support
  public abstract DeleteConditionStep<R> andNot(Field<Boolean> paramField);
  
  @Support
  public abstract DeleteConditionStep<R> andExists(Select<?> paramSelect);
  
  public abstract DeleteConditionStep<R> andNotExists(Select<?> paramSelect);
  
  @Support
  public abstract DeleteConditionStep<R> or(Condition paramCondition);
  
  @Support
  public abstract DeleteConditionStep<R> or(Field<Boolean> paramField);
  
  @Support
  public abstract DeleteConditionStep<R> or(String paramString);
  
  @Support
  public abstract DeleteConditionStep<R> or(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract DeleteConditionStep<R> or(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract DeleteConditionStep<R> orNot(Condition paramCondition);
  
  @Support
  public abstract DeleteConditionStep<R> orNot(Field<Boolean> paramField);
  
  @Support
  public abstract DeleteConditionStep<R> orExists(Select<?> paramSelect);
  
  @Support
  public abstract DeleteConditionStep<R> orNotExists(Select<?> paramSelect);
}
