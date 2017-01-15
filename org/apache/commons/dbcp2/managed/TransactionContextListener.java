package org.apache.commons.dbcp2.managed;

public abstract interface TransactionContextListener
{
  public abstract void afterCompletion(TransactionContext paramTransactionContext, boolean paramBoolean);
}
