package org.jooq;

public abstract interface Record1<T1>
  extends Record
{
  public abstract Row1<T1> fieldsRow();
  
  public abstract Row1<T1> valuesRow();
  
  public abstract Field<T1> field1();
  
  public abstract T1 value1();
  
  public abstract Record1<T1> value1(T1 paramT1);
  
  public abstract Record1<T1> values(T1 paramT1);
}
