package org.jooq;

public abstract interface DivideByOnConditionStep
  extends DivideByReturningStep
{
  @Support
  public abstract DivideByOnConditionStep and(Condition paramCondition);
  
  @Support
  public abstract DivideByOnConditionStep and(Field<Boolean> paramField);
  
  @Support
  public abstract DivideByOnConditionStep and(String paramString);
  
  @Support
  public abstract DivideByOnConditionStep and(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract DivideByOnConditionStep and(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract DivideByOnConditionStep andNot(Condition paramCondition);
  
  @Support
  public abstract DivideByOnConditionStep andNot(Field<Boolean> paramField);
  
  @Support
  public abstract DivideByOnConditionStep andExists(Select<?> paramSelect);
  
  @Support
  public abstract DivideByOnConditionStep andNotExists(Select<?> paramSelect);
  
  @Support
  public abstract DivideByOnConditionStep or(Condition paramCondition);
  
  @Support
  public abstract DivideByOnConditionStep or(Field<Boolean> paramField);
  
  @Support
  public abstract DivideByOnConditionStep or(String paramString);
  
  @Support
  public abstract DivideByOnConditionStep or(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract DivideByOnConditionStep or(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract DivideByOnConditionStep orNot(Condition paramCondition);
  
  @Support
  public abstract DivideByOnConditionStep orNot(Field<Boolean> paramField);
  
  @Support
  public abstract DivideByOnConditionStep orExists(Select<?> paramSelect);
  
  @Support
  public abstract DivideByOnConditionStep orNotExists(Select<?> paramSelect);
}
