package org.jooq;

import java.util.Collection;

public abstract interface DeleteWhereStep<R extends Record>
  extends DeleteFinalStep<R>
{
  @Support
  public abstract DeleteConditionStep<R> where(Condition... paramVarArgs);
  
  @Support
  public abstract DeleteConditionStep<R> where(Collection<? extends Condition> paramCollection);
  
  @Support
  public abstract DeleteConditionStep<R> where(Field<Boolean> paramField);
  
  @Support
  public abstract DeleteConditionStep<R> where(String paramString);
  
  @Support
  public abstract DeleteConditionStep<R> where(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract DeleteConditionStep<R> where(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract DeleteConditionStep<R> whereExists(Select<?> paramSelect);
  
  @Support
  public abstract DeleteConditionStep<R> whereNotExists(Select<?> paramSelect);
}
