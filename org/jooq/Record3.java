package org.jooq;

public abstract interface Record3<T1, T2, T3>
  extends Record
{
  public abstract Row3<T1, T2, T3> fieldsRow();
  
  public abstract Row3<T1, T2, T3> valuesRow();
  
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract Field<T3> field3();
  
  public abstract T1 value1();
  
  public abstract T2 value2();
  
  public abstract T3 value3();
  
  public abstract Record3<T1, T2, T3> value1(T1 paramT1);
  
  public abstract Record3<T1, T2, T3> value2(T2 paramT2);
  
  public abstract Record3<T1, T2, T3> value3(T3 paramT3);
  
  public abstract Record3<T1, T2, T3> values(T1 paramT1, T2 paramT2, T3 paramT3);
}
