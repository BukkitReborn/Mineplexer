package org.apache.commons.dbcp2.managed;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.pool2.ObjectPool;

public class PoolableManagedConnection
  extends PoolableConnection
{
  private final TransactionRegistry transactionRegistry;
  
  public PoolableManagedConnection(TransactionRegistry transactionRegistry, Connection conn, ObjectPool<PoolableConnection> pool)
  {
    super(conn, pool, null);
    this.transactionRegistry = transactionRegistry;
  }
  
  public void reallyClose()
    throws SQLException
  {
    try
    {
      super.reallyClose();
    }
    finally
    {
      this.transactionRegistry.unregisterConnection(this);
    }
  }
}
