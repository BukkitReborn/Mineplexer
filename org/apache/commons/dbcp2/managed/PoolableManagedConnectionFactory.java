package org.apache.commons.dbcp2.managed;

import java.sql.Connection;
import javax.management.ObjectName;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DelegatingPreparedStatement;
import org.apache.commons.dbcp2.PStmtKey;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingConnection;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

public class PoolableManagedConnectionFactory
  extends PoolableConnectionFactory
{
  private final TransactionRegistry transactionRegistry;
  
  public PoolableManagedConnectionFactory(XAConnectionFactory connFactory, ObjectName dataSourceJmxName)
  {
    super(connFactory, dataSourceJmxName);
    this.transactionRegistry = connFactory.getTransactionRegistry();
  }
  
  public synchronized PooledObject<PoolableConnection> makeObject()
    throws Exception
  {
    Connection conn = getConnectionFactory().createConnection();
    if (conn == null) {
      throw new IllegalStateException("Connection factory returned null from createConnection");
    }
    initializeConnection(conn);
    if (getPoolStatements())
    {
      conn = new PoolingConnection(conn);
      GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
      config.setMaxTotalPerKey(-1);
      config.setBlockWhenExhausted(false);
      config.setMaxWaitMillis(0L);
      config.setMaxIdlePerKey(1);
      config.setMaxTotal(getMaxOpenPreparedStatements());
      KeyedObjectPool<PStmtKey, DelegatingPreparedStatement> stmtPool = new GenericKeyedObjectPool((PoolingConnection)conn, config);
      
      ((PoolingConnection)conn).setStatementPool(stmtPool);
      ((PoolingConnection)conn).setCacheState(getCacheState());
    }
    return new DefaultPooledObject(new PoolableManagedConnection(this.transactionRegistry, conn, getPool()));
  }
}
