package org.jooq;

public abstract interface BetweenAndStep5<T1, T2, T3, T4, T5>
{
  @Support
  public abstract Condition and(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  @Support
  public abstract Condition and(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  @Support
  public abstract Condition and(Row5<T1, T2, T3, T4, T5> paramRow5);
  
  @Support
  public abstract Condition and(Record5<T1, T2, T3, T4, T5> paramRecord5);
}
