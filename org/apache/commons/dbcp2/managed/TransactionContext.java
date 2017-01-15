package org.apache.commons.dbcp2.managed;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.SQLException;
import javax.transaction.RollbackException;
import javax.transaction.Synchronization;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

public class TransactionContext
{
  private final TransactionRegistry transactionRegistry;
  private final WeakReference<Transaction> transactionRef;
  private Connection sharedConnection;
  
  public TransactionContext(TransactionRegistry transactionRegistry, Transaction transaction)
  {
    if (transactionRegistry == null) {
      throw new NullPointerException("transactionRegistry is null");
    }
    if (transaction == null) {
      throw new NullPointerException("transaction is null");
    }
    this.transactionRegistry = transactionRegistry;
    this.transactionRef = new WeakReference(transaction);
  }
  
  public Connection getSharedConnection()
  {
    return this.sharedConnection;
  }
  
  public void setSharedConnection(Connection sharedConnection)
    throws SQLException
  {
    if (this.sharedConnection != null) {
      throw new IllegalStateException("A shared connection is already set");
    }
    Transaction transaction = getTransaction();
    try
    {
      XAResource xaResource = this.transactionRegistry.getXAResource(sharedConnection);
      transaction.enlistResource(xaResource);
    }
    catch (RollbackException e) {}catch (SystemException e)
    {
      throw new SQLException("Unable to enlist connection the transaction", e);
    }
    this.sharedConnection = sharedConnection;
  }
  
  public void addTransactionContextListener(final TransactionContextListener listener)
    throws SQLException
  {
    try
    {
      getTransaction().registerSynchronization(new Synchronization()
      {
        public void beforeCompletion() {}
        
        public void afterCompletion(int status)
        {
          listener.afterCompletion(TransactionContext.this, status == 3);
        }
      });
    }
    catch (RollbackException e) {}catch (Exception e)
    {
      throw new SQLException("Unable to register transaction context listener", e);
    }
  }
  
  public boolean isActive()
    throws SQLException
  {
    try
    {
      Transaction transaction = (Transaction)this.transactionRef.get();
      if (transaction == null) {
        return false;
      }
      int status = transaction.getStatus();
      return (status == 0) || (status == 1);
    }
    catch (SystemException e)
    {
      throw new SQLException("Unable to get transaction status", e);
    }
  }
  
  private Transaction getTransaction()
    throws SQLException
  {
    Transaction transaction = (Transaction)this.transactionRef.get();
    if (transaction == null) {
      throw new SQLException("Unable to enlist connection because the transaction has been garbage collected");
    }
    return transaction;
  }
}
