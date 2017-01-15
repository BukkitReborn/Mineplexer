package org.jooq;

public abstract interface Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>
  extends Record
{
  public abstract Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> fieldsRow();
  
  public abstract Row9<T1, T2, T3, T4, T5, T6, T7, T8, T9> valuesRow();
  
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract Field<T3> field3();
  
  public abstract Field<T4> field4();
  
  public abstract Field<T5> field5();
  
  public abstract Field<T6> field6();
  
  public abstract Field<T7> field7();
  
  public abstract Field<T8> field8();
  
  public abstract Field<T9> field9();
  
  public abstract T1 value1();
  
  public abstract T2 value2();
  
  public abstract T3 value3();
  
  public abstract T4 value4();
  
  public abstract T5 value5();
  
  public abstract T6 value6();
  
  public abstract T7 value7();
  
  public abstract T8 value8();
  
  public abstract T9 value9();
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value1(T1 paramT1);
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value2(T2 paramT2);
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value3(T3 paramT3);
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value4(T4 paramT4);
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value5(T5 paramT5);
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value6(T6 paramT6);
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value7(T7 paramT7);
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value8(T8 paramT8);
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> value9(T9 paramT9);
  
  public abstract Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> values(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9);
}
