package org.apache.commons.dbcp2.managed;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.DelegatingConnection;
import org.apache.commons.pool2.ObjectPool;

public class ManagedConnection<C extends Connection>
  extends DelegatingConnection<C>
{
  private final ObjectPool<C> pool;
  private final TransactionRegistry transactionRegistry;
  private final boolean accessToUnderlyingConnectionAllowed;
  private TransactionContext transactionContext;
  private boolean isSharedConnection;
  
  public ManagedConnection(ObjectPool<C> pool, TransactionRegistry transactionRegistry, boolean accessToUnderlyingConnectionAllowed)
    throws SQLException
  {
    super(null);
    this.pool = pool;
    this.transactionRegistry = transactionRegistry;
    this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
    updateTransactionStatus();
  }
  
  protected void checkOpen()
    throws SQLException
  {
    super.checkOpen();
    updateTransactionStatus();
  }
  
  private void updateTransactionStatus()
    throws SQLException
  {
    if (this.transactionContext != null)
    {
      if (this.transactionContext.isActive())
      {
        if (this.transactionContext != this.transactionRegistry.getActiveTransactionContext()) {
          throw new SQLException("Connection can not be used while enlisted in another transaction");
        }
        return;
      }
      transactionComplete();
    }
    this.transactionContext = this.transactionRegistry.getActiveTransactionContext();
    if ((this.transactionContext != null) && (this.transactionContext.getSharedConnection() != null))
    {
      C connection = getDelegateInternal();
      setDelegate(null);
      if (connection != null) {
        try
        {
          this.pool.returnObject(connection);
        }
        catch (Exception ignored)
        {
          try
          {
            this.pool.invalidateObject(connection);
          }
          catch (Exception ignore) {}
        }
      }
      this.transactionContext.addTransactionContextListener(new CompletionListener());
      
      C shared = this.transactionContext.getSharedConnection();
      setDelegate(shared);
      
      this.isSharedConnection = true;
    }
    else
    {
      if (getDelegateInternal() == null) {
        try
        {
          C connection = (Connection)this.pool.borrowObject();
          setDelegate(connection);
        }
        catch (Exception e)
        {
          throw new SQLException("Unable to acquire a new connection from the pool", e);
        }
      }
      if (this.transactionContext != null)
      {
        this.transactionContext.addTransactionContextListener(new CompletionListener());
        try
        {
          this.transactionContext.setSharedConnection(getDelegateInternal());
        }
        catch (SQLException e)
        {
          this.transactionContext = null;
          throw e;
        }
      }
    }
    clearCachedState();
  }
  
  public void close()
    throws SQLException
  {
    if (!isClosedInternal()) {
      try
      {
        if (this.transactionContext == null) {
          super.close();
        }
      }
      finally
      {
        setClosedInternal(true);
      }
    }
  }
  
  protected class CompletionListener
    implements TransactionContextListener
  {
    protected CompletionListener() {}
    
    public void afterCompletion(TransactionContext completedContext, boolean commited)
    {
      if (completedContext == ManagedConnection.this.transactionContext) {
        ManagedConnection.this.transactionComplete();
      }
    }
  }
  
  protected void transactionComplete()
  {
    this.transactionContext = null;
    if (this.isSharedConnection)
    {
      setDelegate(null);
      this.isSharedConnection = false;
    }
    Connection delegate = getDelegateInternal();
    if ((isClosedInternal()) && (delegate != null)) {
      try
      {
        setDelegate(null);
        if (!delegate.isClosed()) {
          delegate.close();
        }
      }
      catch (SQLException ignored) {}
    }
  }
  
  public void setAutoCommit(boolean autoCommit)
    throws SQLException
  {
    if (this.transactionContext != null) {
      throw new SQLException("Auto-commit can not be set while enrolled in a transaction");
    }
    super.setAutoCommit(autoCommit);
  }
  
  public void commit()
    throws SQLException
  {
    if (this.transactionContext != null) {
      throw new SQLException("Commit can not be set while enrolled in a transaction");
    }
    super.commit();
  }
  
  public void rollback()
    throws SQLException
  {
    if (this.transactionContext != null) {
      throw new SQLException("Commit can not be set while enrolled in a transaction");
    }
    super.rollback();
  }
  
  public void setReadOnly(boolean readOnly)
    throws SQLException
  {
    if (this.transactionContext != null) {
      throw new SQLException("Read-only can not be set while enrolled in a transaction");
    }
    super.setReadOnly(readOnly);
  }
  
  public boolean isAccessToUnderlyingConnectionAllowed()
  {
    return this.accessToUnderlyingConnectionAllowed;
  }
  
  public C getDelegate()
  {
    if (isAccessToUnderlyingConnectionAllowed()) {
      return getDelegateInternal();
    }
    return null;
  }
  
  public Connection getInnermostDelegate()
  {
    if (isAccessToUnderlyingConnectionAllowed()) {
      return super.getInnermostDelegateInternal();
    }
    return null;
  }
}
