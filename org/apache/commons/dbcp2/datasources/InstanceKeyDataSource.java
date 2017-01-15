package org.apache.commons.dbcp2.datasources;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Referenceable;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import javax.sql.PooledConnection;

public abstract class InstanceKeyDataSource
  implements DataSource, Referenceable, Serializable
{
  private static final long serialVersionUID = -6819270431752240878L;
  private static final String GET_CONNECTION_CALLED = "A Connection was already requested from this source, further initialization is not allowed.";
  private static final String BAD_TRANSACTION_ISOLATION = "The requested TransactionIsolation level is invalid.";
  protected static final int UNKNOWN_TRANSACTIONISOLATION = -1;
  private volatile boolean getConnectionCalled = false;
  private ConnectionPoolDataSource dataSource = null;
  private String dataSourceName = null;
  private String description = null;
  private Properties jndiEnvironment = null;
  private int loginTimeout = 0;
  private PrintWriter logWriter = null;
  private String instanceKey = null;
  private boolean defaultBlockWhenExhausted = true;
  private String defaultEvictionPolicyClassName = "org.apache.commons.pool2.impl.DefaultEvictionPolicy";
  private boolean defaultLifo = true;
  private int defaultMaxIdle = 8;
  private int defaultMaxTotal = -1;
  private long defaultMaxWaitMillis = -1L;
  private long defaultMinEvictableIdleTimeMillis = 1800000L;
  private int defaultMinIdle = 0;
  private int defaultNumTestsPerEvictionRun = 3;
  private long defaultSoftMinEvictableIdleTimeMillis = -1L;
  private boolean defaultTestOnCreate = false;
  private boolean defaultTestOnBorrow = false;
  private boolean defaultTestOnReturn = false;
  private boolean defaultTestWhileIdle = false;
  private long defaultTimeBetweenEvictionRunsMillis = -1L;
  private String validationQuery = null;
  private int validationQueryTimeout = -1;
  private boolean rollbackAfterValidation = false;
  private long maxConnLifetimeMillis = -1L;
  private Boolean defaultAutoCommit = null;
  private int defaultTransactionIsolation = -1;
  private Boolean defaultReadOnly = null;
  
  protected void assertInitializationAllowed()
    throws IllegalStateException
  {
    if (this.getConnectionCalled) {
      throw new IllegalStateException("A Connection was already requested from this source, further initialization is not allowed.");
    }
  }
  
  public abstract void close()
    throws Exception;
  
  protected abstract PooledConnectionManager getConnectionManager(UserPassKey paramUserPassKey);
  
  public boolean isWrapperFor(Class<?> iface)
    throws SQLException
  {
    return false;
  }
  
  public <T> T unwrap(Class<T> iface)
    throws SQLException
  {
    throw new SQLException("InstanceKeyDataSource is not a wrapper.");
  }
  
  public Logger getParentLogger()
    throws SQLFeatureNotSupportedException
  {
    throw new SQLFeatureNotSupportedException();
  }
  
  public boolean getDefaultBlockWhenExhausted()
  {
    return this.defaultBlockWhenExhausted;
  }
  
  public void setDefaultBlockWhenExhausted(boolean blockWhenExhausted)
  {
    assertInitializationAllowed();
    this.defaultBlockWhenExhausted = blockWhenExhausted;
  }
  
  public String getDefaultEvictionPolicyClassName()
  {
    return this.defaultEvictionPolicyClassName;
  }
  
  public void setDefaultEvictionPolicyClassName(String evictionPolicyClassName)
  {
    assertInitializationAllowed();
    this.defaultEvictionPolicyClassName = evictionPolicyClassName;
  }
  
  public boolean getDefaultLifo()
  {
    return this.defaultLifo;
  }
  
  public void setDefaultLifo(boolean lifo)
  {
    assertInitializationAllowed();
    this.defaultLifo = lifo;
  }
  
  public int getDefaultMaxIdle()
  {
    return this.defaultMaxIdle;
  }
  
  public void setDefaultMaxIdle(int maxIdle)
  {
    assertInitializationAllowed();
    this.defaultMaxIdle = maxIdle;
  }
  
  public int getDefaultMaxTotal()
  {
    return this.defaultMaxTotal;
  }
  
  public void setDefaultMaxTotal(int maxTotal)
  {
    assertInitializationAllowed();
    this.defaultMaxTotal = maxTotal;
  }
  
  public long getDefaultMaxWaitMillis()
  {
    return this.defaultMaxWaitMillis;
  }
  
  public void setDefaultMaxWaitMillis(long maxWaitMillis)
  {
    assertInitializationAllowed();
    this.defaultMaxWaitMillis = maxWaitMillis;
  }
  
  public long getDefaultMinEvictableIdleTimeMillis()
  {
    return this.defaultMinEvictableIdleTimeMillis;
  }
  
  public void setDefaultMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis)
  {
    assertInitializationAllowed();
    this.defaultMinEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
  }
  
  public int getDefaultMinIdle()
  {
    return this.defaultMinIdle;
  }
  
  public void setDefaultMinIdle(int minIdle)
  {
    assertInitializationAllowed();
    this.defaultMinIdle = minIdle;
  }
  
  public int getDefaultNumTestsPerEvictionRun()
  {
    return this.defaultNumTestsPerEvictionRun;
  }
  
  public void setDefaultNumTestsPerEvictionRun(int numTestsPerEvictionRun)
  {
    assertInitializationAllowed();
    this.defaultNumTestsPerEvictionRun = numTestsPerEvictionRun;
  }
  
  public long getDefaultSoftMinEvictableIdleTimeMillis()
  {
    return this.defaultSoftMinEvictableIdleTimeMillis;
  }
  
  public void setDefaultSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis)
  {
    assertInitializationAllowed();
    this.defaultSoftMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
  }
  
  public boolean getDefaultTestOnCreate()
  {
    return this.defaultTestOnCreate;
  }
  
  public void setDefaultTestOnCreate(boolean testOnCreate)
  {
    assertInitializationAllowed();
    this.defaultTestOnCreate = testOnCreate;
  }
  
  public boolean getDefaultTestOnBorrow()
  {
    return this.defaultTestOnBorrow;
  }
  
  public void setDefaultTestOnBorrow(boolean testOnBorrow)
  {
    assertInitializationAllowed();
    this.defaultTestOnBorrow = testOnBorrow;
  }
  
  public boolean getDefaultTestOnReturn()
  {
    return this.defaultTestOnReturn;
  }
  
  public void setDefaultTestOnReturn(boolean testOnReturn)
  {
    assertInitializationAllowed();
    this.defaultTestOnReturn = testOnReturn;
  }
  
  public boolean getDefaultTestWhileIdle()
  {
    return this.defaultTestWhileIdle;
  }
  
  public void setDefaultTestWhileIdle(boolean testWhileIdle)
  {
    assertInitializationAllowed();
    this.defaultTestWhileIdle = testWhileIdle;
  }
  
  public long getDefaultTimeBetweenEvictionRunsMillis()
  {
    return this.defaultTimeBetweenEvictionRunsMillis;
  }
  
  public void setDefaultTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis)
  {
    assertInitializationAllowed();
    this.defaultTimeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
  }
  
  public ConnectionPoolDataSource getConnectionPoolDataSource()
  {
    return this.dataSource;
  }
  
  public void setConnectionPoolDataSource(ConnectionPoolDataSource v)
  {
    assertInitializationAllowed();
    if (this.dataSourceName != null) {
      throw new IllegalStateException("Cannot set the DataSource, if JNDI is used.");
    }
    if (this.dataSource != null) {
      throw new IllegalStateException("The CPDS has already been set. It cannot be altered.");
    }
    this.dataSource = v;
    this.instanceKey = InstanceKeyDataSourceFactory.registerNewInstance(this);
  }
  
  public String getDataSourceName()
  {
    return this.dataSourceName;
  }
  
  public void setDataSourceName(String v)
  {
    assertInitializationAllowed();
    if (this.dataSource != null) {
      throw new IllegalStateException("Cannot set the JNDI name for the DataSource, if already set using setConnectionPoolDataSource.");
    }
    if (this.dataSourceName != null) {
      throw new IllegalStateException("The DataSourceName has already been set. It cannot be altered.");
    }
    this.dataSourceName = v;
    this.instanceKey = InstanceKeyDataSourceFactory.registerNewInstance(this);
  }
  
  public Boolean isDefaultAutoCommit()
  {
    return this.defaultAutoCommit;
  }
  
  public void setDefaultAutoCommit(Boolean v)
  {
    assertInitializationAllowed();
    this.defaultAutoCommit = v;
  }
  
  public Boolean isDefaultReadOnly()
  {
    return this.defaultReadOnly;
  }
  
  public void setDefaultReadOnly(Boolean v)
  {
    assertInitializationAllowed();
    this.defaultReadOnly = v;
  }
  
  public int getDefaultTransactionIsolation()
  {
    return this.defaultTransactionIsolation;
  }
  
  public void setDefaultTransactionIsolation(int v)
  {
    assertInitializationAllowed();
    switch (v)
    {
    case 0: 
    case 1: 
    case 2: 
    case 4: 
    case 8: 
      break;
    case 3: 
    case 5: 
    case 6: 
    case 7: 
    default: 
      throw new IllegalArgumentException("The requested TransactionIsolation level is invalid.");
    }
    this.defaultTransactionIsolation = v;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String v)
  {
    this.description = v;
  }
  
  protected String getInstanceKey()
  {
    return this.instanceKey;
  }
  
  public String getJndiEnvironment(String key)
  {
    String value = null;
    if (this.jndiEnvironment != null) {
      value = this.jndiEnvironment.getProperty(key);
    }
    return value;
  }
  
  public void setJndiEnvironment(String key, String value)
  {
    if (this.jndiEnvironment == null) {
      this.jndiEnvironment = new Properties();
    }
    this.jndiEnvironment.setProperty(key, value);
  }
  
  void setJndiEnvironment(Properties properties)
  {
    if (this.jndiEnvironment == null) {
      this.jndiEnvironment = new Properties();
    } else {
      this.jndiEnvironment.clear();
    }
    this.jndiEnvironment.putAll(properties);
  }
  
  public int getLoginTimeout()
  {
    return this.loginTimeout;
  }
  
  public void setLoginTimeout(int v)
  {
    this.loginTimeout = v;
  }
  
  public PrintWriter getLogWriter()
  {
    if (this.logWriter == null) {
      this.logWriter = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
    }
    return this.logWriter;
  }
  
  public void setLogWriter(PrintWriter v)
  {
    this.logWriter = v;
  }
  
  public String getValidationQuery()
  {
    return this.validationQuery;
  }
  
  public void setValidationQuery(String validationQuery)
  {
    assertInitializationAllowed();
    this.validationQuery = validationQuery;
  }
  
  public int getValidationQueryTimeout()
  {
    return this.validationQueryTimeout;
  }
  
  public void setValidationQueryTimeout(int validationQueryTimeout)
  {
    this.validationQueryTimeout = validationQueryTimeout;
  }
  
  public boolean isRollbackAfterValidation()
  {
    return this.rollbackAfterValidation;
  }
  
  public void setRollbackAfterValidation(boolean rollbackAfterValidation)
  {
    assertInitializationAllowed();
    this.rollbackAfterValidation = rollbackAfterValidation;
  }
  
  public long getMaxConnLifetimeMillis()
  {
    return this.maxConnLifetimeMillis;
  }
  
  public void setMaxConnLifetimeMillis(long maxConnLifetimeMillis)
  {
    this.maxConnLifetimeMillis = maxConnLifetimeMillis;
  }
  
  public Connection getConnection()
    throws SQLException
  {
    return getConnection(null, null);
  }
  
  public Connection getConnection(String username, String password)
    throws SQLException
  {
    if (this.instanceKey == null) {
      throw new SQLException("Must set the ConnectionPoolDataSource through setDataSourceName or setConnectionPoolDataSource before calling getConnection.");
    }
    this.getConnectionCalled = true;
    PooledConnectionAndInfo info = null;
    try
    {
      info = getPooledConnectionAndInfo(username, password);
    }
    catch (NoSuchElementException e)
    {
      closeDueToException(info);
      throw new SQLException("Cannot borrow connection from pool", e);
    }
    catch (RuntimeException e)
    {
      closeDueToException(info);
      throw e;
    }
    catch (SQLException e)
    {
      closeDueToException(info);
      throw e;
    }
    catch (Exception e)
    {
      closeDueToException(info);
      throw new SQLException("Cannot borrow connection from pool", e);
    }
    if (null == password ? null != info.getPassword() : !password.equals(info.getPassword()))
    {
      try
      {
        testCPDS(username, password);
      }
      catch (SQLException ex)
      {
        closeDueToException(info);
        throw new SQLException("Given password did not match password used to create the PooledConnection.");
      }
      catch (NamingException ne)
      {
        throw new SQLException("NamingException encountered connecting to database", ne);
      }
      UserPassKey upkey = info.getUserPassKey();
      PooledConnectionManager manager = getConnectionManager(upkey);
      manager.invalidate(info.getPooledConnection());
      manager.setPassword(upkey.getPassword());
      info = null;
      for (int i = 0; i < 10; i++)
      {
        try
        {
          info = getPooledConnectionAndInfo(username, password);
        }
        catch (NoSuchElementException e)
        {
          closeDueToException(info);
          throw new SQLException("Cannot borrow connection from pool", e);
        }
        catch (RuntimeException e)
        {
          closeDueToException(info);
          throw e;
        }
        catch (SQLException e)
        {
          closeDueToException(info);
          throw e;
        }
        catch (Exception e)
        {
          closeDueToException(info);
          throw new SQLException("Cannot borrow connection from pool", e);
        }
        if ((info != null) && (password != null) && (password.equals(info.getPassword()))) {
          break;
        }
        if (info != null) {
          manager.invalidate(info.getPooledConnection());
        }
        info = null;
      }
      if (info == null) {
        throw new SQLException("Cannot borrow connection from pool - password change failure.");
      }
    }
    Connection con = info.getPooledConnection().getConnection();
    try
    {
      setupDefaults(con, username);
      con.clearWarnings();
      return con;
    }
    catch (SQLException ex)
    {
      try
      {
        con.close();
      }
      catch (Exception exc)
      {
        getLogWriter().println("ignoring exception during close: " + exc);
      }
      throw ex;
    }
  }
  
  protected abstract PooledConnectionAndInfo getPooledConnectionAndInfo(String paramString1, String paramString2)
    throws SQLException;
  
  protected abstract void setupDefaults(Connection paramConnection, String paramString)
    throws SQLException;
  
  private void closeDueToException(PooledConnectionAndInfo info)
  {
    if (info != null) {
      try
      {
        info.getPooledConnection().getConnection().close();
      }
      catch (Exception e)
      {
        getLogWriter().println("[ERROR] Could not return connection to pool during exception handling. " + e.getMessage());
      }
    }
  }
  
  protected ConnectionPoolDataSource testCPDS(String username, String password)
    throws NamingException, SQLException
  {
    cpds = this.dataSource;
    if (cpds == null)
    {
      Context ctx = null;
      if (this.jndiEnvironment == null) {
        ctx = new InitialContext();
      } else {
        ctx = new InitialContext(this.jndiEnvironment);
      }
      Object ds = ctx.lookup(this.dataSourceName);
      if ((ds instanceof ConnectionPoolDataSource)) {
        cpds = (ConnectionPoolDataSource)ds;
      } else {
        throw new SQLException("Illegal configuration: DataSource " + this.dataSourceName + " (" + ds.getClass().getName() + ")" + " doesn't implement javax.sql.ConnectionPoolDataSource");
      }
    }
    PooledConnection conn = null;
    try
    {
      if (username != null) {
        conn = cpds.getPooledConnection(username, password);
      } else {
        conn = cpds.getPooledConnection();
      }
      if (conn == null) {
        throw new SQLException("Cannot connect using the supplied username/password");
      }
      return cpds;
    }
    finally
    {
      if (conn != null) {
        try
        {
          conn.close();
        }
        catch (SQLException e) {}
      }
    }
  }
}
