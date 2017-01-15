package org.jooq;

public abstract interface BetweenAndStep<T>
{
  @Support
  public abstract Condition and(T paramT);
  
  @Support
  public abstract Condition and(Field<T> paramField);
}
