package org.jooq;

public abstract interface SelectSeekStepN<R extends Record>
  extends SelectLimitStep<R>
{
  public abstract SelectSeekLimitStep<R> seek(Object... paramVarArgs);
  
  public abstract SelectSeekLimitStep<R> seek(Field<?>... paramVarArgs);
  
  public abstract SelectSeekLimitStep<R> seekAfter(Object... paramVarArgs);
  
  public abstract SelectSeekLimitStep<R> seekAfter(Field<?>... paramVarArgs);
  
  public abstract SelectSeekLimitStep<R> seekBefore(Object... paramVarArgs);
  
  public abstract SelectSeekLimitStep<R> seekBefore(Field<?>... paramVarArgs);
}
