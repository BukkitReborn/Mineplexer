package org.jooq;

public abstract interface BetweenAndStep8<T1, T2, T3, T4, T5, T6, T7, T8>
{
  @Support
  public abstract Condition and(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7);
  
  @Support
  public abstract Condition and(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8);
  
  @Support
  public abstract Condition and(Row8<T1, T2, T3, T4, T5, T6, T7, T8> paramRow8);
  
  @Support
  public abstract Condition and(Record8<T1, T2, T3, T4, T5, T6, T7, T8> paramRecord8);
}
