package org.jooq;

public abstract interface BetweenAndStep3<T1, T2, T3>
{
  @Support
  public abstract Condition and(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract Condition and(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract Condition and(Row3<T1, T2, T3> paramRow3);
  
  @Support
  public abstract Condition and(Record3<T1, T2, T3> paramRecord3);
}
