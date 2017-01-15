package org.jooq;

public abstract interface BetweenAndStep4<T1, T2, T3, T4>
{
  @Support
  public abstract Condition and(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract Condition and(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract Condition and(Row4<T1, T2, T3, T4> paramRow4);
  
  @Support
  public abstract Condition and(Record4<T1, T2, T3, T4> paramRecord4);
}
