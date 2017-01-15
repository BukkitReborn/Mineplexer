package org.jooq;

public abstract interface Record6<T1, T2, T3, T4, T5, T6>
  extends Record
{
  public abstract Row6<T1, T2, T3, T4, T5, T6> fieldsRow();
  
  public abstract Row6<T1, T2, T3, T4, T5, T6> valuesRow();
  
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract Field<T3> field3();
  
  public abstract Field<T4> field4();
  
  public abstract Field<T5> field5();
  
  public abstract Field<T6> field6();
  
  public abstract T1 value1();
  
  public abstract T2 value2();
  
  public abstract T3 value3();
  
  public abstract T4 value4();
  
  public abstract T5 value5();
  
  public abstract T6 value6();
  
  public abstract Record6<T1, T2, T3, T4, T5, T6> value1(T1 paramT1);
  
  public abstract Record6<T1, T2, T3, T4, T5, T6> value2(T2 paramT2);
  
  public abstract Record6<T1, T2, T3, T4, T5, T6> value3(T3 paramT3);
  
  public abstract Record6<T1, T2, T3, T4, T5, T6> value4(T4 paramT4);
  
  public abstract Record6<T1, T2, T3, T4, T5, T6> value5(T5 paramT5);
  
  public abstract Record6<T1, T2, T3, T4, T5, T6> value6(T6 paramT6);
  
  public abstract Record6<T1, T2, T3, T4, T5, T6> values(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
}
