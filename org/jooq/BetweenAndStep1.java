package org.jooq;

public abstract interface BetweenAndStep1<T1>
{
  @Support
  public abstract Condition and(Field<T1> paramField);
  
  @Support
  public abstract Condition and(T1 paramT1);
  
  @Support
  public abstract Condition and(Row1<T1> paramRow1);
  
  @Support
  public abstract Condition and(Record1<T1> paramRecord1);
}
