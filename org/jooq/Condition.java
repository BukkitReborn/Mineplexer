package org.jooq;

public abstract interface Condition
  extends QueryPart
{
  @Support
  public abstract Condition and(Condition paramCondition);
  
  @Support
  public abstract Condition and(Field<Boolean> paramField);
  
  @Support
  public abstract Condition and(String paramString);
  
  @Support
  public abstract Condition and(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract Condition and(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract Condition andNot(Condition paramCondition);
  
  @Support
  public abstract Condition andNot(Field<Boolean> paramField);
  
  @Support
  public abstract Condition andExists(Select<?> paramSelect);
  
  @Support
  public abstract Condition andNotExists(Select<?> paramSelect);
  
  @Support
  public abstract Condition or(Condition paramCondition);
  
  @Support
  public abstract Condition or(Field<Boolean> paramField);
  
  @Support
  public abstract Condition or(String paramString);
  
  @Support
  public abstract Condition or(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract Condition or(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract Condition orNot(Condition paramCondition);
  
  @Support
  public abstract Condition orNot(Field<Boolean> paramField);
  
  @Support
  public abstract Condition orExists(Select<?> paramSelect);
  
  @Support
  public abstract Condition orNotExists(Select<?> paramSelect);
  
  @Support
  public abstract Condition not();
}
