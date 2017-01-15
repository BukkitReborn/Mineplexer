package org.apache.commons.dbcp2.datasources;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.sql.ConnectionPoolDataSource;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

public class SharedPoolDataSource
  extends InstanceKeyDataSource
{
  private static final long serialVersionUID = -1458539734480586454L;
  private int maxTotal = -1;
  private transient KeyedObjectPool<UserPassKey, PooledConnectionAndInfo> pool = null;
  private transient KeyedCPDSConnectionFactory factory = null;
  
  public void close()
    throws Exception
  {
    if (this.pool != null) {
      this.pool.close();
    }
    InstanceKeyDataSourceFactory.removeInstance(getInstanceKey());
  }
  
  public int getMaxTotal()
  {
    return this.maxTotal;
  }
  
  public void setMaxTotal(int maxTotal)
  {
    assertInitializationAllowed();
    this.maxTotal = maxTotal;
  }
  
  public int getNumActive()
  {
    return this.pool == null ? 0 : this.pool.getNumActive();
  }
  
  public int getNumIdle()
  {
    return this.pool == null ? 0 : this.pool.getNumIdle();
  }
  
  protected PooledConnectionAndInfo getPooledConnectionAndInfo(String username, String password)
    throws SQLException
  {
    synchronized (this)
    {
      if (this.pool == null) {
        try
        {
          registerPool(username, password);
        }
        catch (NamingException e)
        {
          throw new SQLException("RegisterPool failed", e);
        }
      }
    }
    PooledConnectionAndInfo info = null;
    
    UserPassKey key = new UserPassKey(username, password);
    try
    {
      info = (PooledConnectionAndInfo)this.pool.borrowObject(key);
    }
    catch (Exception e)
    {
      throw new SQLException("Could not retrieve connection info from pool", e);
    }
    return info;
  }
  
  protected PooledConnectionManager getConnectionManager(UserPassKey upkey)
  {
    return this.factory;
  }
  
  public Reference getReference()
    throws NamingException
  {
    Reference ref = new Reference(getClass().getName(), SharedPoolDataSourceFactory.class.getName(), null);
    
    ref.add(new StringRefAddr("instanceKey", getInstanceKey()));
    return ref;
  }
  
  private void registerPool(String username, String password)
    throws NamingException, SQLException
  {
    ConnectionPoolDataSource cpds = testCPDS(username, password);
    
    this.factory = new KeyedCPDSConnectionFactory(cpds, getValidationQuery(), getValidationQueryTimeout(), isRollbackAfterValidation());
    
    this.factory.setMaxConnLifetimeMillis(getMaxConnLifetimeMillis());
    
    GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
    
    config.setBlockWhenExhausted(getDefaultBlockWhenExhausted());
    config.setEvictionPolicyClassName(getDefaultEvictionPolicyClassName());
    config.setLifo(getDefaultLifo());
    config.setMaxIdlePerKey(getDefaultMaxIdle());
    config.setMaxTotal(getMaxTotal());
    config.setMaxTotalPerKey(getDefaultMaxTotal());
    config.setMaxWaitMillis(getDefaultMaxWaitMillis());
    config.setMinEvictableIdleTimeMillis(getDefaultMinEvictableIdleTimeMillis());
    
    config.setMinIdlePerKey(getDefaultMinIdle());
    config.setNumTestsPerEvictionRun(getDefaultNumTestsPerEvictionRun());
    config.setSoftMinEvictableIdleTimeMillis(getDefaultSoftMinEvictableIdleTimeMillis());
    
    config.setTestOnCreate(getDefaultTestOnCreate());
    config.setTestOnBorrow(getDefaultTestOnBorrow());
    config.setTestOnReturn(getDefaultTestOnReturn());
    config.setTestWhileIdle(getDefaultTestWhileIdle());
    config.setTimeBetweenEvictionRunsMillis(getDefaultTimeBetweenEvictionRunsMillis());
    
    KeyedObjectPool<UserPassKey, PooledConnectionAndInfo> tmpPool = new GenericKeyedObjectPool(this.factory, config);
    
    this.factory.setPool(tmpPool);
    this.pool = tmpPool;
  }
  
  protected void setupDefaults(Connection con, String username)
    throws SQLException
  {
    Boolean defaultAutoCommit = isDefaultAutoCommit();
    if ((defaultAutoCommit != null) && (con.getAutoCommit() != defaultAutoCommit.booleanValue())) {
      con.setAutoCommit(defaultAutoCommit.booleanValue());
    }
    int defaultTransactionIsolation = getDefaultTransactionIsolation();
    if (defaultTransactionIsolation != -1) {
      con.setTransactionIsolation(defaultTransactionIsolation);
    }
    Boolean defaultReadOnly = isDefaultReadOnly();
    if ((defaultReadOnly != null) && (con.isReadOnly() != defaultReadOnly.booleanValue())) {
      con.setReadOnly(defaultReadOnly.booleanValue());
    }
  }
  
  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    try
    {
      in.defaultReadObject();
      SharedPoolDataSource oldDS = (SharedPoolDataSource)new SharedPoolDataSourceFactory().getObjectInstance(getReference(), null, null, null);
      
      this.pool = oldDS.pool;
    }
    catch (NamingException e)
    {
      throw new IOException("NamingException: " + e);
    }
  }
}
