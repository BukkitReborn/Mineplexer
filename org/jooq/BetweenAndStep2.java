package org.jooq;

public abstract interface BetweenAndStep2<T1, T2>
{
  @Support
  public abstract Condition and(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract Condition and(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract Condition and(Row2<T1, T2> paramRow2);
  
  @Support
  public abstract Condition and(Record2<T1, T2> paramRecord2);
}
