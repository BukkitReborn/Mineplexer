package org.jooq;

public abstract interface TransactionContext
  extends Scope
{
  public abstract Transaction transaction();
  
  public abstract TransactionContext transaction(Transaction paramTransaction);
  
  public abstract Exception cause();
  
  public abstract TransactionContext cause(Exception paramException);
}
