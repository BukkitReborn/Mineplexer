package org.jooq;

public abstract interface TransactionalCallable<T>
{
  public abstract T run(Configuration paramConfiguration)
    throws Exception;
}
