package org.jooq;

public abstract interface SelectSeekStep1<R extends Record, T1>
  extends SelectLimitStep<R>
{
  public abstract SelectSeekLimitStep<R> seek(T1 paramT1);
  
  public abstract SelectSeekLimitStep<R> seek(Field<T1> paramField);
  
  public abstract SelectSeekLimitStep<R> seekAfter(T1 paramT1);
  
  public abstract SelectSeekLimitStep<R> seekAfter(Field<T1> paramField);
  
  public abstract SelectSeekLimitStep<R> seekBefore(T1 paramT1);
  
  public abstract SelectSeekLimitStep<R> seekBefore(Field<T1> paramField);
}
