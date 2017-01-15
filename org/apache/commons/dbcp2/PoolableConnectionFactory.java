package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.ObjectName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

public class PoolableConnectionFactory
  implements PooledObjectFactory<PoolableConnection>
{
  private static final Log log = LogFactory.getLog(PoolableConnectionFactory.class);
  private final ConnectionFactory _connFactory;
  private final ObjectName dataSourceJmxName;
  
  public PoolableConnectionFactory(ConnectionFactory connFactory, ObjectName dataSourceJmxName)
  {
    this._connFactory = connFactory;
    this.dataSourceJmxName = dataSourceJmxName;
  }
  
  public void setValidationQuery(String validationQuery)
  {
    this._validationQuery = validationQuery;
  }
  
  public void setValidationQueryTimeout(int timeout)
  {
    this._validationQueryTimeout = timeout;
  }
  
  public void setConnectionInitSql(Collection<String> connectionInitSqls)
  {
    this._connectionInitSqls = connectionInitSqls;
  }
  
  public synchronized void setPool(ObjectPool<PoolableConnection> pool)
  {
    if ((null != this._pool) && (pool != this._pool)) {
      try
      {
        this._pool.close();
      }
      catch (Exception e) {}
    }
    this._pool = pool;
  }
  
  public synchronized ObjectPool<PoolableConnection> getPool()
  {
    return this._pool;
  }
  
  public void setDefaultReadOnly(Boolean defaultReadOnly)
  {
    this._defaultReadOnly = defaultReadOnly;
  }
  
  public void setDefaultAutoCommit(Boolean defaultAutoCommit)
  {
    this._defaultAutoCommit = defaultAutoCommit;
  }
  
  public void setDefaultTransactionIsolation(int defaultTransactionIsolation)
  {
    this._defaultTransactionIsolation = defaultTransactionIsolation;
  }
  
  public void setDefaultCatalog(String defaultCatalog)
  {
    this._defaultCatalog = defaultCatalog;
  }
  
  public void setCacheState(boolean cacheState)
  {
    this._cacheState = cacheState;
  }
  
  public void setPoolStatements(boolean poolStatements)
  {
    this.poolStatements = poolStatements;
  }
  
  public void setMaxOpenPrepatedStatements(int maxOpenPreparedStatements)
  {
    this.maxOpenPreparedStatements = maxOpenPreparedStatements;
  }
  
  public void setMaxConnLifetimeMillis(long maxConnLifetimeMillis)
  {
    this.maxConnLifetimeMillis = maxConnLifetimeMillis;
  }
  
  public boolean isEnableAutoCommitOnReturn()
  {
    return this.enableAutoCommitOnReturn;
  }
  
  public void setEnableAutoCommitOnReturn(boolean enableAutoCommitOnReturn)
  {
    this.enableAutoCommitOnReturn = enableAutoCommitOnReturn;
  }
  
  public boolean isRollbackOnReturn()
  {
    return this.rollbackOnReturn;
  }
  
  public void setRollbackOnReturn(boolean rollbackOnReturn)
  {
    this.rollbackOnReturn = rollbackOnReturn;
  }
  
  public Integer getDefaultQueryTimeout()
  {
    return this.defaultQueryTimeout;
  }
  
  public void setDefaultQueryTimeout(Integer defaultQueryTimeout)
  {
    this.defaultQueryTimeout = defaultQueryTimeout;
  }
  
  public PooledObject<PoolableConnection> makeObject()
    throws Exception
  {
    Connection conn = this._connFactory.createConnection();
    if (conn == null) {
      throw new IllegalStateException("Connection factory returned null from createConnection");
    }
    try
    {
      initializeConnection(conn);
    }
    catch (SQLException sqle)
    {
      try
      {
        conn.close();
      }
      catch (SQLException ignore) {}
      throw sqle;
    }
    long connIndex = this.connectionIndex.getAndIncrement();
    if (this.poolStatements)
    {
      conn = new PoolingConnection(conn);
      GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
      config.setMaxTotalPerKey(-1);
      config.setBlockWhenExhausted(false);
      config.setMaxWaitMillis(0L);
      config.setMaxIdlePerKey(1);
      config.setMaxTotal(this.maxOpenPreparedStatements);
      if (this.dataSourceJmxName != null)
      {
        StringBuilder base = new StringBuilder(this.dataSourceJmxName.toString());
        base.append(",connectionpool=connections,connection=");
        base.append(Long.toString(connIndex));
        config.setJmxNameBase(base.toString());
        config.setJmxNamePrefix(",statementpool=statements");
      }
      KeyedObjectPool<PStmtKey, DelegatingPreparedStatement> stmtPool = new GenericKeyedObjectPool((PoolingConnection)conn, config);
      
      ((PoolingConnection)conn).setStatementPool(stmtPool);
      ((PoolingConnection)conn).setCacheState(this._cacheState);
    }
    ObjectName connJmxName;
    ObjectName connJmxName;
    if (this.dataSourceJmxName == null) {
      connJmxName = null;
    } else {
      connJmxName = new ObjectName(this.dataSourceJmxName.toString() + ",connectionpool=connections,connection=" + connIndex);
    }
    PoolableConnection pc = new PoolableConnection(conn, this._pool, connJmxName);
    
    return new DefaultPooledObject(pc);
  }
  
  protected void initializeConnection(Connection conn)
    throws SQLException
  {
    Collection<String> sqls = this._connectionInitSqls;
    if (conn.isClosed()) {
      throw new SQLException("initializeConnection: connection closed");
    }
    if (null != sqls)
    {
      Statement stmt = conn.createStatement();Throwable localThrowable2 = null;
      try
      {
        for (String sql : sqls)
        {
          if (sql == null) {
            throw new NullPointerException("null connectionInitSqls element");
          }
          stmt.execute(sql);
        }
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (stmt != null) {
          if (localThrowable2 != null) {
            try
            {
              stmt.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            stmt.close();
          }
        }
      }
    }
  }
  
  public void destroyObject(PooledObject<PoolableConnection> p)
    throws Exception
  {
    ((PoolableConnection)p.getObject()).reallyClose();
  }
  
  public boolean validateObject(PooledObject<PoolableConnection> p)
  {
    try
    {
      validateLifetime(p);
      
      validateConnection((PoolableConnection)p.getObject());
      return true;
    }
    catch (Exception e)
    {
      if (log.isDebugEnabled()) {
        log.debug(Utils.getMessage("poolableConnectionFactory.validateObject.fail"), e);
      }
    }
    return false;
  }
  
  public void validateConnection(PoolableConnection conn)
    throws SQLException
  {
    if (conn.isClosed()) {
      throw new SQLException("validateConnection: connection closed");
    }
    conn.validate(this._validationQuery, this._validationQueryTimeout);
  }
  
  public void passivateObject(PooledObject<PoolableConnection> p)
    throws Exception
  {
    validateLifetime(p);
    
    PoolableConnection conn = (PoolableConnection)p.getObject();
    Boolean connAutoCommit = null;
    if (this.rollbackOnReturn)
    {
      connAutoCommit = Boolean.valueOf(conn.getAutoCommit());
      if ((!connAutoCommit.booleanValue()) && (!conn.isReadOnly())) {
        conn.rollback();
      }
    }
    conn.clearWarnings();
    if (this.enableAutoCommitOnReturn)
    {
      if (connAutoCommit == null) {
        connAutoCommit = Boolean.valueOf(conn.getAutoCommit());
      }
      if (!connAutoCommit.booleanValue()) {
        conn.setAutoCommit(true);
      }
    }
    conn.passivate();
  }
  
  public void activateObject(PooledObject<PoolableConnection> p)
    throws Exception
  {
    validateLifetime(p);
    
    PoolableConnection conn = (PoolableConnection)p.getObject();
    conn.activate();
    if ((this._defaultAutoCommit != null) && (conn.getAutoCommit() != this._defaultAutoCommit.booleanValue())) {
      conn.setAutoCommit(this._defaultAutoCommit.booleanValue());
    }
    if ((this._defaultTransactionIsolation != -1) && (conn.getTransactionIsolation() != this._defaultTransactionIsolation)) {
      conn.setTransactionIsolation(this._defaultTransactionIsolation);
    }
    if ((this._defaultReadOnly != null) && (conn.isReadOnly() != this._defaultReadOnly.booleanValue())) {
      conn.setReadOnly(this._defaultReadOnly.booleanValue());
    }
    if ((this._defaultCatalog != null) && (!this._defaultCatalog.equals(conn.getCatalog()))) {
      conn.setCatalog(this._defaultCatalog);
    }
    conn.setDefaultQueryTimeout(this.defaultQueryTimeout);
  }
  
  private void validateLifetime(PooledObject<PoolableConnection> p)
    throws Exception
  {
    if (this.maxConnLifetimeMillis > 0L)
    {
      long lifetime = System.currentTimeMillis() - p.getCreateTime();
      if (lifetime > this.maxConnLifetimeMillis) {
        throw new Exception(Utils.getMessage("connectionFactory.lifetimeExceeded", new Object[] { Long.valueOf(lifetime), Long.valueOf(this.maxConnLifetimeMillis) }));
      }
    }
  }
  
  protected ConnectionFactory getConnectionFactory()
  {
    return this._connFactory;
  }
  
  protected boolean getPoolStatements()
  {
    return this.poolStatements;
  }
  
  protected int getMaxOpenPreparedStatements()
  {
    return this.maxOpenPreparedStatements;
  }
  
  protected boolean getCacheState()
  {
    return this._cacheState;
  }
  
  private volatile String _validationQuery = null;
  private volatile int _validationQueryTimeout = -1;
  private Collection<String> _connectionInitSqls = null;
  private volatile ObjectPool<PoolableConnection> _pool = null;
  private Boolean _defaultReadOnly = null;
  private Boolean _defaultAutoCommit = null;
  private boolean enableAutoCommitOnReturn = true;
  private boolean rollbackOnReturn = true;
  private int _defaultTransactionIsolation = -1;
  private String _defaultCatalog;
  private boolean _cacheState;
  private boolean poolStatements = false;
  private int maxOpenPreparedStatements = 8;
  private long maxConnLifetimeMillis = -1L;
  private final AtomicLong connectionIndex = new AtomicLong(0L);
  private Integer defaultQueryTimeout = null;
  static final int UNKNOWN_TRANSACTIONISOLATION = -1;
}
