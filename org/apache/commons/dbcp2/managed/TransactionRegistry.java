package org.apache.commons.dbcp2.managed;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.WeakHashMap;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;
import org.apache.commons.dbcp2.DelegatingConnection;

public class TransactionRegistry
{
  private final TransactionManager transactionManager;
  private final Map<Transaction, TransactionContext> caches = new WeakHashMap();
  private final Map<Connection, XAResource> xaResources = new WeakHashMap();
  
  public TransactionRegistry(TransactionManager transactionManager)
  {
    this.transactionManager = transactionManager;
  }
  
  public synchronized void registerConnection(Connection connection, XAResource xaResource)
  {
    if (connection == null) {
      throw new NullPointerException("connection is null");
    }
    if (xaResource == null) {
      throw new NullPointerException("xaResource is null");
    }
    this.xaResources.put(connection, xaResource);
  }
  
  public synchronized XAResource getXAResource(Connection connection)
    throws SQLException
  {
    if (connection == null) {
      throw new NullPointerException("connection is null");
    }
    Connection key = getConnectionKey(connection);
    XAResource xaResource = (XAResource)this.xaResources.get(key);
    if (xaResource == null) {
      throw new SQLException("Connection does not have a registered XAResource " + connection);
    }
    return xaResource;
  }
  
  public TransactionContext getActiveTransactionContext()
    throws SQLException
  {
    Transaction transaction = null;
    try
    {
      transaction = this.transactionManager.getTransaction();
      if (transaction == null) {
        return null;
      }
      int status = transaction.getStatus();
      if ((status != 0) && (status != 1)) {
        return null;
      }
    }
    catch (SystemException e)
    {
      throw new SQLException("Unable to determine current transaction ", e);
    }
    synchronized (this)
    {
      TransactionContext cache = (TransactionContext)this.caches.get(transaction);
      if (cache == null)
      {
        cache = new TransactionContext(this, transaction);
        this.caches.put(transaction, cache);
      }
      return cache;
    }
  }
  
  public synchronized void unregisterConnection(Connection connection)
  {
    Connection key = getConnectionKey(connection);
    this.xaResources.remove(key);
  }
  
  private Connection getConnectionKey(Connection connection)
  {
    Connection result;
    Connection result;
    if ((connection instanceof DelegatingConnection)) {
      result = ((DelegatingConnection)connection).getInnermostDelegateInternal();
    } else {
      result = connection;
    }
    return result;
  }
}
