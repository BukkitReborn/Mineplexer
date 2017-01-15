package org.jooq;

import java.util.Collection;

public abstract interface UpdateWhereStep<R extends Record>
  extends UpdateFinalStep<R>, UpdateReturningStep<R>
{
  @Support
  public abstract UpdateConditionStep<R> where(Condition... paramVarArgs);
  
  @Support
  public abstract UpdateConditionStep<R> where(Collection<? extends Condition> paramCollection);
  
  @Support
  public abstract UpdateConditionStep<R> where(Field<Boolean> paramField);
  
  @Support
  public abstract UpdateConditionStep<R> where(String paramString);
  
  @Support
  public abstract UpdateConditionStep<R> where(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract UpdateConditionStep<R> where(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract UpdateConditionStep<R> whereExists(Select<?> paramSelect);
  
  @Support
  public abstract UpdateConditionStep<R> whereNotExists(Select<?> paramSelect);
}
