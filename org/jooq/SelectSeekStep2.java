package org.jooq;

public abstract interface SelectSeekStep2<R extends Record, T1, T2>
  extends SelectLimitStep<R>
{
  public abstract SelectSeekLimitStep<R> seek(T1 paramT1, T2 paramT2);
  
  public abstract SelectSeekLimitStep<R> seek(Field<T1> paramField, Field<T2> paramField1);
  
  public abstract SelectSeekLimitStep<R> seekAfter(T1 paramT1, T2 paramT2);
  
  public abstract SelectSeekLimitStep<R> seekAfter(Field<T1> paramField, Field<T2> paramField1);
  
  public abstract SelectSeekLimitStep<R> seekBefore(T1 paramT1, T2 paramT2);
  
  public abstract SelectSeekLimitStep<R> seekBefore(Field<T1> paramField, Field<T2> paramField1);
}
