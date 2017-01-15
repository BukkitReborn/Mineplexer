package org.jooq;

public abstract interface UpdateConditionStep<R extends Record>
  extends UpdateFinalStep<R>, UpdateReturningStep<R>
{
  @Support
  public abstract UpdateConditionStep<R> and(Condition paramCondition);
  
  @Support
  public abstract UpdateConditionStep<R> and(Field<Boolean> paramField);
  
  @Support
  public abstract UpdateConditionStep<R> and(String paramString);
  
  @Support
  public abstract UpdateConditionStep<R> and(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract UpdateConditionStep<R> and(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract UpdateConditionStep<R> andNot(Condition paramCondition);
  
  @Support
  public abstract UpdateConditionStep<R> andNot(Field<Boolean> paramField);
  
  @Support
  public abstract UpdateConditionStep<R> andExists(Select<?> paramSelect);
  
  @Support
  public abstract UpdateConditionStep<R> andNotExists(Select<?> paramSelect);
  
  @Support
  public abstract UpdateConditionStep<R> or(Condition paramCondition);
  
  @Support
  public abstract UpdateConditionStep<R> or(Field<Boolean> paramField);
  
  @Support
  public abstract UpdateConditionStep<R> or(String paramString);
  
  @Support
  public abstract UpdateConditionStep<R> or(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract UpdateConditionStep<R> or(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract UpdateConditionStep<R> orNot(Condition paramCondition);
  
  @Support
  public abstract UpdateConditionStep<R> orNot(Field<Boolean> paramField);
  
  @Support
  public abstract UpdateConditionStep<R> orExists(Select<?> paramSelect);
  
  @Support
  public abstract UpdateConditionStep<R> orNotExists(Select<?> paramSelect);
}
