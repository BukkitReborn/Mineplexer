package org.jooq;

public abstract interface DivideByOnStep
{
  @Support
  public abstract DivideByOnConditionStep on(Condition... paramVarArgs);
  
  @Support
  public abstract DivideByOnConditionStep on(Field<Boolean> paramField);
  
  @Support
  public abstract DivideByOnConditionStep on(String paramString);
  
  @Support
  public abstract DivideByOnConditionStep on(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract DivideByOnConditionStep on(String paramString, QueryPart... paramVarArgs);
}
