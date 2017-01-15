package org.jooq;

public abstract interface TransactionalRunnable
{
  public abstract void run(Configuration paramConfiguration)
    throws Exception;
}
