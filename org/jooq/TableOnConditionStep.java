package org.jooq;

public abstract interface TableOnConditionStep
  extends Table<Record>
{
  @Support
  public abstract TableOnConditionStep and(Condition paramCondition);
  
  @Support
  public abstract TableOnConditionStep and(Field<Boolean> paramField);
  
  @Support
  public abstract TableOnConditionStep and(String paramString);
  
  @Support
  public abstract TableOnConditionStep and(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract TableOnConditionStep and(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract TableOnConditionStep andNot(Condition paramCondition);
  
  @Support
  public abstract TableOnConditionStep andNot(Field<Boolean> paramField);
  
  @Support
  public abstract TableOnConditionStep andExists(Select<?> paramSelect);
  
  @Support
  public abstract TableOnConditionStep andNotExists(Select<?> paramSelect);
  
  @Support
  public abstract TableOnConditionStep or(Condition paramCondition);
  
  @Support
  public abstract TableOnConditionStep or(Field<Boolean> paramField);
  
  @Support
  public abstract TableOnConditionStep or(String paramString);
  
  @Support
  public abstract TableOnConditionStep or(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract TableOnConditionStep or(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract TableOnConditionStep orNot(Condition paramCondition);
  
  @Support
  public abstract TableOnConditionStep orNot(Field<Boolean> paramField);
  
  @Support
  public abstract TableOnConditionStep orExists(Select<?> paramSelect);
  
  @Support
  public abstract TableOnConditionStep orNotExists(Select<?> paramSelect);
}
