package org.jooq;

public abstract interface CaseWhenStep<V, T>
  extends Field<T>
{
  @Support
  public abstract CaseWhenStep<V, T> when(V paramV, T paramT);
  
  @Support
  public abstract CaseWhenStep<V, T> when(V paramV, Field<T> paramField);
  
  @Support
  public abstract CaseWhenStep<V, T> when(Field<V> paramField, T paramT);
  
  @Support
  public abstract CaseWhenStep<V, T> when(Field<V> paramField, Field<T> paramField1);
  
  @Support
  public abstract Field<T> otherwise(T paramT);
  
  @Support
  public abstract Field<T> otherwise(Field<T> paramField);
}
