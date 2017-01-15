package org.jooq;

public abstract interface SelectSeekStep5<R extends Record, T1, T2, T3, T4, T5>
  extends SelectLimitStep<R>
{
  public abstract SelectSeekLimitStep<R> seek(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  public abstract SelectSeekLimitStep<R> seek(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  public abstract SelectSeekLimitStep<R> seekAfter(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  public abstract SelectSeekLimitStep<R> seekAfter(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
  
  public abstract SelectSeekLimitStep<R> seekBefore(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5);
  
  public abstract SelectSeekLimitStep<R> seekBefore(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4);
}
