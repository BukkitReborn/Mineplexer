package org.jooq;

public abstract interface BetweenAndStep6<T1, T2, T3, T4, T5, T6>
{
  @Support
  public abstract Condition and(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract Condition and(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract Condition and(Row6<T1, T2, T3, T4, T5, T6> paramRow6);
  
  @Support
  public abstract Condition and(Record6<T1, T2, T3, T4, T5, T6> paramRecord6);
}
