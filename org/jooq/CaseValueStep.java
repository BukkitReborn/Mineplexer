package org.jooq;

public abstract interface CaseValueStep<V>
{
  @Support
  public abstract <T> CaseWhenStep<V, T> when(V paramV, T paramT);
  
  @Support
  public abstract <T> CaseWhenStep<V, T> when(V paramV, Field<T> paramField);
  
  @Support
  public abstract <T> CaseWhenStep<V, T> when(V paramV, Select<? extends Record1<T>> paramSelect);
  
  @Support
  public abstract <T> CaseWhenStep<V, T> when(Field<V> paramField, T paramT);
  
  @Support
  public abstract <T> CaseWhenStep<V, T> when(Field<V> paramField, Field<T> paramField1);
  
  @Support
  public abstract <T> CaseWhenStep<V, T> when(Field<V> paramField, Select<? extends Record1<T>> paramSelect);
}
