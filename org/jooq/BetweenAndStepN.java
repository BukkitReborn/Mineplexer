package org.jooq;

public abstract interface BetweenAndStepN
{
  @Support
  public abstract Condition and(Field<?>... paramVarArgs);
  
  @Support
  public abstract Condition and(Object... paramVarArgs);
  
  @Support
  public abstract Condition and(RowN paramRowN);
  
  @Support
  public abstract Condition and(Record paramRecord);
}
