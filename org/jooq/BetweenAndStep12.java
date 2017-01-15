package org.jooq;

public abstract interface BetweenAndStep12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>
{
  @Support
  public abstract Condition and(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9, Field<T11> paramField10, Field<T12> paramField11);
  
  @Support
  public abstract Condition and(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10, T11 paramT11, T12 paramT12);
  
  @Support
  public abstract Condition and(Row12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> paramRow12);
  
  @Support
  public abstract Condition and(Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> paramRecord12);
}
