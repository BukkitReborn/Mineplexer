package org.jooq;

import org.jooq.exception.DataAccessException;

public abstract interface TransactionProvider
{
  public abstract void begin(TransactionContext paramTransactionContext)
    throws DataAccessException;
  
  public abstract void commit(TransactionContext paramTransactionContext)
    throws DataAccessException;
  
  public abstract void rollback(TransactionContext paramTransactionContext)
    throws DataAccessException;
}
