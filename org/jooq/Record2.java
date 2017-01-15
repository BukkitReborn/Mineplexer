package org.jooq;

public abstract interface Record2<T1, T2>
  extends Record
{
  public abstract Row2<T1, T2> fieldsRow();
  
  public abstract Row2<T1, T2> valuesRow();
  
  public abstract Field<T1> field1();
  
  public abstract Field<T2> field2();
  
  public abstract T1 value1();
  
  public abstract T2 value2();
  
  public abstract Record2<T1, T2> value1(T1 paramT1);
  
  public abstract Record2<T1, T2> value2(T2 paramT2);
  
  public abstract Record2<T1, T2> values(T1 paramT1, T2 paramT2);
}
