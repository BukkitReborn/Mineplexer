package org.jooq;

import java.util.Collection;

public abstract interface SelectWhereStep<R extends Record>
  extends SelectConnectByStep<R>
{
  @Support
  public abstract SelectConditionStep<R> where(Condition... paramVarArgs);
  
  @Support
  public abstract SelectConditionStep<R> where(Collection<? extends Condition> paramCollection);
  
  @Support
  public abstract SelectConditionStep<R> where(Field<Boolean> paramField);
  
  @Support
  public abstract SelectConditionStep<R> where(String paramString);
  
  @Support
  public abstract SelectConditionStep<R> where(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectConditionStep<R> where(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectConditionStep<R> whereExists(Select<?> paramSelect);
  
  @Support
  public abstract SelectConditionStep<R> whereNotExists(Select<?> paramSelect);
}
