package org.apache.commons.dbcp2.managed;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;

public class ManagedDataSource<C extends Connection>
  extends PoolingDataSource<C>
{
  private TransactionRegistry transactionRegistry;
  
  public ManagedDataSource(ObjectPool<C> pool, TransactionRegistry transactionRegistry)
  {
    super(pool);
    this.transactionRegistry = transactionRegistry;
  }
  
  public void setTransactionRegistry(TransactionRegistry transactionRegistry)
  {
    if (this.transactionRegistry != null) {
      throw new IllegalStateException("TransactionRegistry already set");
    }
    if (transactionRegistry == null) {
      throw new NullPointerException("TransactionRegistry is null");
    }
    this.transactionRegistry = transactionRegistry;
  }
  
  public Connection getConnection()
    throws SQLException
  {
    if (getPool() == null) {
      throw new IllegalStateException("Pool has not been set");
    }
    if (this.transactionRegistry == null) {
      throw new IllegalStateException("TransactionRegistry has not been set");
    }
    Connection connection = new ManagedConnection(getPool(), this.transactionRegistry, isAccessToUnderlyingConnectionAllowed());
    return connection;
  }
}
