package org.apache.commons.dbcp2;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import javax.management.InstanceAlreadyExistsException;
import javax.management.JMException;
import javax.management.MBeanRegistration;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class BasicDataSource
  implements DataSource, BasicDataSourceMXBean, MBeanRegistration
{
  private static final Log log = LogFactory.getLog(BasicDataSource.class);
  private volatile Boolean defaultAutoCommit;
  private transient Boolean defaultReadOnly;
  private volatile int defaultTransactionIsolation;
  private Integer defaultQueryTimeout;
  private volatile String defaultCatalog;
  private boolean cacheState;
  private Driver driver;
  private String driverClassName;
  private ClassLoader driverClassLoader;
  private boolean lifo;
  private int maxTotal;
  private int maxIdle;
  private int minIdle;
  private int initialSize;
  private long maxWaitMillis;
  private boolean poolPreparedStatements;
  private int maxOpenPreparedStatements;
  private boolean testOnCreate;
  private boolean testOnBorrow;
  private boolean testOnReturn;
  private long timeBetweenEvictionRunsMillis;
  private int numTestsPerEvictionRun;
  private long minEvictableIdleTimeMillis;
  private long softMinEvictableIdleTimeMillis;
  private String evictionPolicyClassName;
  private boolean testWhileIdle;
  private volatile String password;
  private String url;
  private String username;
  private volatile String validationQuery;
  private volatile int validationQueryTimeout;
  private volatile List<String> connectionInitSqls;
  private boolean accessToUnderlyingConnectionAllowed;
  private long maxConnLifetimeMillis;
  private String jmxName;
  private boolean enableAutoCommitOnReturn;
  private boolean rollbackOnReturn;
  private volatile GenericObjectPool<PoolableConnection> connectionPool;
  private Properties connectionProperties;
  private volatile DataSource dataSource;
  private volatile PrintWriter logWriter;
  private AbandonedConfig abandonedConfig;
  private boolean closed;
  private ObjectName registeredJmxName;
  
  static
  {
    DriverManager.getDrivers();
    try
    {
      if (Utils.IS_SECURITY_ENABLED)
      {
        ClassLoader loader = BasicDataSource.class.getClassLoader();
        String dbcpPackageName = BasicDataSource.class.getPackage().getName();
        loader.loadClass(dbcpPackageName + ".BasicDataSource$PaGetConnection");
        loader.loadClass(dbcpPackageName + ".DelegatingCallableStatement");
        loader.loadClass(dbcpPackageName + ".DelegatingDatabaseMetaData");
        loader.loadClass(dbcpPackageName + ".DelegatingPreparedStatement");
        loader.loadClass(dbcpPackageName + ".DelegatingResultSet");
        loader.loadClass(dbcpPackageName + ".PoolableCallableStatement");
        loader.loadClass(dbcpPackageName + ".PoolablePreparedStatement");
        loader.loadClass(dbcpPackageName + ".PoolingConnection$StatementType");
        loader.loadClass(dbcpPackageName + ".PStmtKey");
        
        String poolPackageName = PooledObject.class.getPackage().getName();
        loader.loadClass(poolPackageName + ".impl.LinkedBlockingDeque$Node");
        loader.loadClass(poolPackageName + ".impl.GenericKeyedObjectPool$ObjectDeque");
      }
    }
    catch (ClassNotFoundException cnfe)
    {
      throw new IllegalStateException("Unable to pre-load classes", cnfe);
    }
  }
  
  public Boolean getDefaultAutoCommit()
  {
    return this.defaultAutoCommit;
  }
  
  public void setDefaultAutoCommit(Boolean defaultAutoCommit)
  {
    this.defaultAutoCommit = defaultAutoCommit;
  }
  
  public Boolean getDefaultReadOnly()
  {
    return this.defaultReadOnly;
  }
  
  public void setDefaultReadOnly(Boolean defaultReadOnly)
  {
    this.defaultReadOnly = defaultReadOnly;
  }
  
  public int getDefaultTransactionIsolation()
  {
    return this.defaultTransactionIsolation;
  }
  
  public void setDefaultTransactionIsolation(int defaultTransactionIsolation)
  {
    this.defaultTransactionIsolation = defaultTransactionIsolation;
  }
  
  public Integer getDefaultQueryTimeout()
  {
    return this.defaultQueryTimeout;
  }
  
  public void setDefaultQueryTimeout(Integer defaultQueryTimeout)
  {
    this.defaultQueryTimeout = defaultQueryTimeout;
  }
  
  public String getDefaultCatalog()
  {
    return this.defaultCatalog;
  }
  
  public void setDefaultCatalog(String defaultCatalog)
  {
    if ((defaultCatalog != null) && (defaultCatalog.trim().length() > 0)) {
      this.defaultCatalog = defaultCatalog;
    } else {
      this.defaultCatalog = null;
    }
  }
  
  public boolean getCacheState()
  {
    return this.cacheState;
  }
  
  public void setCacheState(boolean cacheState)
  {
    this.cacheState = cacheState;
  }
  
  public synchronized Driver getDriver()
  {
    return this.driver;
  }
  
  public synchronized void setDriver(Driver driver)
  {
    this.driver = driver;
  }
  
  public synchronized String getDriverClassName()
  {
    return this.driverClassName;
  }
  
  public synchronized void setDriverClassName(String driverClassName)
  {
    if ((driverClassName != null) && (driverClassName.trim().length() > 0)) {
      this.driverClassName = driverClassName;
    } else {
      this.driverClassName = null;
    }
  }
  
  public synchronized ClassLoader getDriverClassLoader()
  {
    return this.driverClassLoader;
  }
  
  public synchronized void setDriverClassLoader(ClassLoader driverClassLoader)
  {
    this.driverClassLoader = driverClassLoader;
  }
  
  public synchronized boolean getLifo()
  {
    return this.lifo;
  }
  
  public synchronized void setLifo(boolean lifo)
  {
    this.lifo = lifo;
    if (this.connectionPool != null) {
      this.connectionPool.setLifo(lifo);
    }
  }
  
  public synchronized int getMaxTotal()
  {
    return this.maxTotal;
  }
  
  public synchronized void setMaxTotal(int maxTotal)
  {
    this.maxTotal = maxTotal;
    if (this.connectionPool != null) {
      this.connectionPool.setMaxTotal(maxTotal);
    }
  }
  
  public synchronized int getMaxIdle()
  {
    return this.maxIdle;
  }
  
  public synchronized void setMaxIdle(int maxIdle)
  {
    this.maxIdle = maxIdle;
    if (this.connectionPool != null) {
      this.connectionPool.setMaxIdle(maxIdle);
    }
  }
  
  public synchronized int getMinIdle()
  {
    return this.minIdle;
  }
  
  public synchronized void setMinIdle(int minIdle)
  {
    this.minIdle = minIdle;
    if (this.connectionPool != null) {
      this.connectionPool.setMinIdle(minIdle);
    }
  }
  
  public synchronized int getInitialSize()
  {
    return this.initialSize;
  }
  
  public synchronized void setInitialSize(int initialSize)
  {
    this.initialSize = initialSize;
  }
  
  public synchronized long getMaxWaitMillis()
  {
    return this.maxWaitMillis;
  }
  
  public synchronized void setMaxWaitMillis(long maxWaitMillis)
  {
    this.maxWaitMillis = maxWaitMillis;
    if (this.connectionPool != null) {
      this.connectionPool.setMaxWaitMillis(maxWaitMillis);
    }
  }
  
  public synchronized boolean isPoolPreparedStatements()
  {
    return this.poolPreparedStatements;
  }
  
  public synchronized void setPoolPreparedStatements(boolean poolingStatements)
  {
    this.poolPreparedStatements = poolingStatements;
  }
  
  public synchronized int getMaxOpenPreparedStatements()
  {
    return this.maxOpenPreparedStatements;
  }
  
  public synchronized void setMaxOpenPreparedStatements(int maxOpenStatements)
  {
    this.maxOpenPreparedStatements = maxOpenStatements;
  }
  
  public synchronized boolean getTestOnCreate()
  {
    return this.testOnCreate;
  }
  
  public synchronized void setTestOnCreate(boolean testOnCreate)
  {
    this.testOnCreate = testOnCreate;
    if (this.connectionPool != null) {
      this.connectionPool.setTestOnCreate(testOnCreate);
    }
  }
  
  public synchronized boolean getTestOnBorrow()
  {
    return this.testOnBorrow;
  }
  
  public synchronized void setTestOnBorrow(boolean testOnBorrow)
  {
    this.testOnBorrow = testOnBorrow;
    if (this.connectionPool != null) {
      this.connectionPool.setTestOnBorrow(testOnBorrow);
    }
  }
  
  public synchronized boolean getTestOnReturn()
  {
    return this.testOnReturn;
  }
  
  public synchronized void setTestOnReturn(boolean testOnReturn)
  {
    this.testOnReturn = testOnReturn;
    if (this.connectionPool != null) {
      this.connectionPool.setTestOnReturn(testOnReturn);
    }
  }
  
  public synchronized long getTimeBetweenEvictionRunsMillis()
  {
    return this.timeBetweenEvictionRunsMillis;
  }
  
  public synchronized void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis)
  {
    this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    if (this.connectionPool != null) {
      this.connectionPool.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
    }
  }
  
  public synchronized int getNumTestsPerEvictionRun()
  {
    return this.numTestsPerEvictionRun;
  }
  
  public synchronized void setNumTestsPerEvictionRun(int numTestsPerEvictionRun)
  {
    this.numTestsPerEvictionRun = numTestsPerEvictionRun;
    if (this.connectionPool != null) {
      this.connectionPool.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
    }
  }
  
  public synchronized long getMinEvictableIdleTimeMillis()
  {
    return this.minEvictableIdleTimeMillis;
  }
  
  public synchronized void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis)
  {
    this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    if (this.connectionPool != null) {
      this.connectionPool.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
    }
  }
  
  public synchronized void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis)
  {
    this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
    if (this.connectionPool != null) {
      this.connectionPool.setSoftMinEvictableIdleTimeMillis(softMinEvictableIdleTimeMillis);
    }
  }
  
  public synchronized long getSoftMinEvictableIdleTimeMillis()
  {
    return this.softMinEvictableIdleTimeMillis;
  }
  
  public synchronized String getEvictionPolicyClassName()
  {
    return this.evictionPolicyClassName;
  }
  
  public synchronized void setEvictionPolicyClassName(String evictionPolicyClassName)
  {
    if (this.connectionPool != null) {
      this.connectionPool.setEvictionPolicyClassName(evictionPolicyClassName);
    }
    this.evictionPolicyClassName = evictionPolicyClassName;
  }
  
  public synchronized boolean getTestWhileIdle()
  {
    return this.testWhileIdle;
  }
  
  public synchronized void setTestWhileIdle(boolean testWhileIdle)
  {
    this.testWhileIdle = testWhileIdle;
    if (this.connectionPool != null) {
      this.connectionPool.setTestWhileIdle(testWhileIdle);
    }
  }
  
  public synchronized int getNumActive()
  {
    if (this.connectionPool != null) {
      return this.connectionPool.getNumActive();
    }
    return 0;
  }
  
  public synchronized int getNumIdle()
  {
    if (this.connectionPool != null) {
      return this.connectionPool.getNumIdle();
    }
    return 0;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public synchronized String getUrl()
  {
    return this.url;
  }
  
  public synchronized void setUrl(String url)
  {
    this.url = url;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public String getValidationQuery()
  {
    return this.validationQuery;
  }
  
  public void setValidationQuery(String validationQuery)
  {
    if ((validationQuery != null) && (validationQuery.trim().length() > 0)) {
      this.validationQuery = validationQuery;
    } else {
      this.validationQuery = null;
    }
  }
  
  public int getValidationQueryTimeout()
  {
    return this.validationQueryTimeout;
  }
  
  public void setValidationQueryTimeout(int timeout)
  {
    this.validationQueryTimeout = timeout;
  }
  
  public List<String> getConnectionInitSqls()
  {
    List<String> result = this.connectionInitSqls;
    if (result == null) {
      return Collections.emptyList();
    }
    return result;
  }
  
  public String[] getConnectionInitSqlsAsArray()
  {
    Collection<String> result = getConnectionInitSqls();
    return (String[])result.toArray(new String[result.size()]);
  }
  
  public void setConnectionInitSqls(Collection<String> connectionInitSqls)
  {
    if ((connectionInitSqls != null) && (connectionInitSqls.size() > 0))
    {
      ArrayList<String> newVal = null;
      for (String s : connectionInitSqls) {
        if ((s != null) && (s.trim().length() > 0))
        {
          if (newVal == null) {
            newVal = new ArrayList();
          }
          newVal.add(s);
        }
      }
      this.connectionInitSqls = newVal;
    }
    else
    {
      this.connectionInitSqls = null;
    }
  }
  
  public synchronized boolean isAccessToUnderlyingConnectionAllowed()
  {
    return this.accessToUnderlyingConnectionAllowed;
  }
  
  public synchronized void setAccessToUnderlyingConnectionAllowed(boolean allow)
  {
    this.accessToUnderlyingConnectionAllowed = allow;
  }
  
  public long getMaxConnLifetimeMillis()
  {
    return this.maxConnLifetimeMillis;
  }
  
  public void setMaxConnLifetimeMillis(long maxConnLifetimeMillis)
  {
    this.maxConnLifetimeMillis = maxConnLifetimeMillis;
  }
  
  public String getJmxName()
  {
    return this.jmxName;
  }
  
  public void setJmxName(String jmxName)
  {
    this.jmxName = jmxName;
  }
  
  public boolean getEnableAutoCommitOnReturn()
  {
    return this.enableAutoCommitOnReturn;
  }
  
  public void setEnableAutoCommitOnReturn(boolean enableAutoCommitOnReturn)
  {
    this.enableAutoCommitOnReturn = enableAutoCommitOnReturn;
  }
  
  public boolean getRollbackOnReturn()
  {
    return this.rollbackOnReturn;
  }
  
  public void setRollbackOnReturn(boolean rollbackOnReturn)
  {
    this.rollbackOnReturn = rollbackOnReturn;
  }
  
  protected GenericObjectPool<PoolableConnection> getConnectionPool()
  {
    return this.connectionPool;
  }
  
  Properties getConnectionProperties()
  {
    return this.connectionProperties;
  }
  
  public Connection getConnection()
    throws SQLException
  {
    if (Utils.IS_SECURITY_ENABLED)
    {
      PrivilegedExceptionAction<Connection> action = new PaGetConnection(null);
      try
      {
        return (Connection)AccessController.doPrivileged(action);
      }
      catch (PrivilegedActionException e)
      {
        Throwable cause = e.getCause();
        if ((cause instanceof SQLException)) {
          throw ((SQLException)cause);
        }
        throw new SQLException(e);
      }
    }
    return createDataSource().getConnection();
  }
  
  public Connection getConnection(String user, String pass)
    throws SQLException
  {
    throw new UnsupportedOperationException("Not supported by BasicDataSource");
  }
  
  public int getLoginTimeout()
    throws SQLException
  {
    throw new UnsupportedOperationException("Not supported by BasicDataSource");
  }
  
  public PrintWriter getLogWriter()
    throws SQLException
  {
    return createDataSource().getLogWriter();
  }
  
  public void setLoginTimeout(int loginTimeout)
    throws SQLException
  {
    throw new UnsupportedOperationException("Not supported by BasicDataSource");
  }
  
  public void setLogWriter(PrintWriter logWriter)
    throws SQLException
  {
    createDataSource().setLogWriter(logWriter);
    this.logWriter = logWriter;
  }
  
  public boolean getRemoveAbandonedOnBorrow()
  {
    if (this.abandonedConfig != null) {
      return this.abandonedConfig.getRemoveAbandonedOnBorrow();
    }
    return false;
  }
  
  public void setRemoveAbandonedOnMaintenance(boolean removeAbandonedOnMaintenance)
  {
    if (this.abandonedConfig == null) {
      this.abandonedConfig = new AbandonedConfig();
    }
    this.abandonedConfig.setRemoveAbandonedOnMaintenance(removeAbandonedOnMaintenance);
  }
  
  public boolean getRemoveAbandonedOnMaintenance()
  {
    if (this.abandonedConfig != null) {
      return this.abandonedConfig.getRemoveAbandonedOnMaintenance();
    }
    return false;
  }
  
  public void setRemoveAbandonedOnBorrow(boolean removeAbandonedOnBorrow)
  {
    if (this.abandonedConfig == null) {
      this.abandonedConfig = new AbandonedConfig();
    }
    this.abandonedConfig.setRemoveAbandonedOnBorrow(removeAbandonedOnBorrow);
  }
  
  public int getRemoveAbandonedTimeout()
  {
    if (this.abandonedConfig != null) {
      return this.abandonedConfig.getRemoveAbandonedTimeout();
    }
    return 300;
  }
  
  public void setRemoveAbandonedTimeout(int removeAbandonedTimeout)
  {
    if (this.abandonedConfig == null) {
      this.abandonedConfig = new AbandonedConfig();
    }
    this.abandonedConfig.setRemoveAbandonedTimeout(removeAbandonedTimeout);
  }
  
  public boolean getLogAbandoned()
  {
    if (this.abandonedConfig != null) {
      return this.abandonedConfig.getLogAbandoned();
    }
    return false;
  }
  
  public void setLogAbandoned(boolean logAbandoned)
  {
    if (this.abandonedConfig == null) {
      this.abandonedConfig = new AbandonedConfig();
    }
    this.abandonedConfig.setLogAbandoned(logAbandoned);
  }
  
  public PrintWriter getAbandonedLogWriter()
  {
    if (this.abandonedConfig != null) {
      return this.abandonedConfig.getLogWriter();
    }
    return null;
  }
  
  public void setAbandonedLogWriter(PrintWriter logWriter)
  {
    if (this.abandonedConfig == null) {
      this.abandonedConfig = new AbandonedConfig();
    }
    this.abandonedConfig.setLogWriter(logWriter);
  }
  
  public boolean getAbandonedUsageTracking()
  {
    if (this.abandonedConfig != null) {
      return this.abandonedConfig.getUseUsageTracking();
    }
    return false;
  }
  
  public void setAbandonedUsageTracking(boolean usageTracking)
  {
    if (this.abandonedConfig == null) {
      this.abandonedConfig = new AbandonedConfig();
    }
    this.abandonedConfig.setUseUsageTracking(usageTracking);
  }
  
  public void addConnectionProperty(String name, String value)
  {
    this.connectionProperties.put(name, value);
  }
  
  public void removeConnectionProperty(String name)
  {
    this.connectionProperties.remove(name);
  }
  
  public void setConnectionProperties(String connectionProperties)
  {
    if (connectionProperties == null) {
      throw new NullPointerException("connectionProperties is null");
    }
    String[] entries = connectionProperties.split(";");
    Properties properties = new Properties();
    for (String entry : entries) {
      if (entry.length() > 0)
      {
        int index = entry.indexOf('=');
        if (index > 0)
        {
          String name = entry.substring(0, index);
          String value = entry.substring(index + 1);
          properties.setProperty(name, value);
        }
        else
        {
          properties.setProperty(entry, "");
        }
      }
    }
    this.connectionProperties = properties;
  }
  
  public synchronized void close()
    throws SQLException
  {
    if (this.registeredJmxName != null)
    {
      MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
      try
      {
        mbs.unregisterMBean(this.registeredJmxName);
      }
      catch (JMException e)
      {
        log.warn("Failed to unregister the JMX name: " + this.registeredJmxName, e);
      }
      finally
      {
        this.registeredJmxName = null;
      }
    }
    this.closed = true;
    GenericObjectPool<?> oldpool = this.connectionPool;
    this.connectionPool = null;
    this.dataSource = null;
    try
    {
      if (oldpool != null) {
        oldpool.close();
      }
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Cannot close connection pool", e);
    }
  }
  
  public synchronized boolean isClosed()
  {
    return this.closed;
  }
  
  public boolean isWrapperFor(Class<?> iface)
    throws SQLException
  {
    return false;
  }
  
  public <T> T unwrap(Class<T> iface)
    throws SQLException
  {
    throw new SQLException("BasicDataSource is not a wrapper.");
  }
  
  public Logger getParentLogger()
    throws SQLFeatureNotSupportedException
  {
    throw new SQLFeatureNotSupportedException();
  }
  
  protected DataSource createDataSource()
    throws SQLException
  {
    if (this.closed) {
      throw new SQLException("Data source is closed");
    }
    if (this.dataSource != null) {
      return this.dataSource;
    }
    synchronized (this)
    {
      if (this.dataSource != null) {
        return this.dataSource;
      }
      jmxRegister();
      
      ConnectionFactory driverConnectionFactory = createConnectionFactory();
      
      boolean success = false;
      PoolableConnectionFactory poolableConnectionFactory;
      try
      {
        poolableConnectionFactory = createPoolableConnectionFactory(driverConnectionFactory);
        
        poolableConnectionFactory.setPoolStatements(this.poolPreparedStatements);
        
        poolableConnectionFactory.setMaxOpenPrepatedStatements(this.maxOpenPreparedStatements);
        
        success = true;
      }
      catch (SQLException se)
      {
        throw se;
      }
      catch (RuntimeException rte)
      {
        throw rte;
      }
      catch (Exception ex)
      {
        throw new SQLException("Error creating connection factory", ex);
      }
      if (success) {
        createConnectionPool(poolableConnectionFactory);
      }
      success = false;
      try
      {
        this.dataSource = createDataSourceInstance();
        this.dataSource.setLogWriter(this.logWriter);
        success = true;
      }
      catch (SQLException se)
      {
        throw se;
      }
      catch (RuntimeException rte)
      {
        throw rte;
      }
      catch (Exception ex)
      {
        throw new SQLException("Error creating datasource", ex);
      }
      finally
      {
        if (!success) {
          closeConnectionPool();
        }
      }
      try
      {
        for (int i = 0; i < this.initialSize; i++) {
          this.connectionPool.addObject();
        }
      }
      catch (Exception e)
      {
        closeConnectionPool();
        throw new SQLException("Error preloading the connection pool", e);
      }
      startPoolMaintenance();
      
      return this.dataSource;
    }
  }
  
  protected ConnectionFactory createConnectionFactory()
    throws SQLException
  {
    Driver driverToUse = this.driver;
    if (driverToUse == null)
    {
      Class<?> driverFromCCL = null;
      if (this.driverClassName != null) {
        try
        {
          try
          {
            if (this.driverClassLoader == null) {
              driverFromCCL = Class.forName(this.driverClassName);
            } else {
              driverFromCCL = Class.forName(this.driverClassName, true, this.driverClassLoader);
            }
          }
          catch (ClassNotFoundException cnfe)
          {
            driverFromCCL = Thread.currentThread().getContextClassLoader().loadClass(this.driverClassName);
          }
        }
        catch (Exception t)
        {
          String message = "Cannot load JDBC driver class '" + this.driverClassName + "'";
          
          this.logWriter.println(message);
          t.printStackTrace(this.logWriter);
          throw new SQLException(message, t);
        }
      }
      try
      {
        if (driverFromCCL == null)
        {
          driverToUse = DriverManager.getDriver(this.url);
        }
        else
        {
          driverToUse = (Driver)driverFromCCL.newInstance();
          if (!driverToUse.acceptsURL(this.url)) {
            throw new SQLException("No suitable driver", "08001");
          }
        }
      }
      catch (Exception t)
      {
        String message = "Cannot create JDBC driver of class '" + (this.driverClassName != null ? this.driverClassName : "") + "' for connect URL '" + this.url + "'";
        
        this.logWriter.println(message);
        t.printStackTrace(this.logWriter);
        throw new SQLException(message, t);
      }
    }
    String user = this.username;
    if (user != null) {
      this.connectionProperties.put("user", user);
    } else {
      log("DBCP DataSource configured without a 'username'");
    }
    String pwd = this.password;
    if (pwd != null) {
      this.connectionProperties.put("password", pwd);
    } else {
      log("DBCP DataSource configured without a 'password'");
    }
    ConnectionFactory driverConnectionFactory = new DriverConnectionFactory(driverToUse, this.url, this.connectionProperties);
    
    return driverConnectionFactory;
  }
  
  protected void createConnectionPool(PoolableConnectionFactory factory)
  {
    GenericObjectPoolConfig config = new GenericObjectPoolConfig();
    updateJmxName(config);
    GenericObjectPool<PoolableConnection> gop;
    GenericObjectPool<PoolableConnection> gop;
    if ((this.abandonedConfig != null) && ((this.abandonedConfig.getRemoveAbandonedOnBorrow()) || (this.abandonedConfig.getRemoveAbandonedOnMaintenance()))) {
      gop = new GenericObjectPool(factory, config, this.abandonedConfig);
    } else {
      gop = new GenericObjectPool(factory, config);
    }
    gop.setMaxTotal(this.maxTotal);
    gop.setMaxIdle(this.maxIdle);
    gop.setMinIdle(this.minIdle);
    gop.setMaxWaitMillis(this.maxWaitMillis);
    gop.setTestOnCreate(this.testOnCreate);
    gop.setTestOnBorrow(this.testOnBorrow);
    gop.setTestOnReturn(this.testOnReturn);
    gop.setNumTestsPerEvictionRun(this.numTestsPerEvictionRun);
    gop.setMinEvictableIdleTimeMillis(this.minEvictableIdleTimeMillis);
    gop.setTestWhileIdle(this.testWhileIdle);
    gop.setLifo(this.lifo);
    gop.setSwallowedExceptionListener(new SwallowedExceptionLogger(log));
    factory.setPool(gop);
    this.connectionPool = gop;
  }
  
  private void closeConnectionPool()
  {
    GenericObjectPool<?> oldpool = this.connectionPool;
    this.connectionPool = null;
    try
    {
      if (oldpool != null) {
        oldpool.close();
      }
    }
    catch (Exception e) {}
  }
  
  protected void startPoolMaintenance()
  {
    if ((this.connectionPool != null) && (this.timeBetweenEvictionRunsMillis > 0L)) {
      this.connectionPool.setTimeBetweenEvictionRunsMillis(this.timeBetweenEvictionRunsMillis);
    }
  }
  
  protected DataSource createDataSourceInstance()
    throws SQLException
  {
    PoolingDataSource<PoolableConnection> pds = new PoolingDataSource(this.connectionPool);
    pds.setAccessToUnderlyingConnectionAllowed(isAccessToUnderlyingConnectionAllowed());
    return pds;
  }
  
  protected PoolableConnectionFactory createPoolableConnectionFactory(ConnectionFactory driverConnectionFactory)
    throws SQLException
  {
    PoolableConnectionFactory connectionFactory = null;
    try
    {
      connectionFactory = new PoolableConnectionFactory(driverConnectionFactory, this.registeredJmxName);
      connectionFactory.setValidationQuery(this.validationQuery);
      connectionFactory.setValidationQueryTimeout(this.validationQueryTimeout);
      connectionFactory.setConnectionInitSql(this.connectionInitSqls);
      connectionFactory.setDefaultReadOnly(this.defaultReadOnly);
      connectionFactory.setDefaultAutoCommit(this.defaultAutoCommit);
      connectionFactory.setDefaultTransactionIsolation(this.defaultTransactionIsolation);
      connectionFactory.setDefaultCatalog(this.defaultCatalog);
      connectionFactory.setCacheState(this.cacheState);
      connectionFactory.setPoolStatements(this.poolPreparedStatements);
      connectionFactory.setMaxOpenPrepatedStatements(this.maxOpenPreparedStatements);
      connectionFactory.setMaxConnLifetimeMillis(this.maxConnLifetimeMillis);
      connectionFactory.setRollbackOnReturn(getRollbackOnReturn());
      connectionFactory.setEnableAutoCommitOnReturn(getEnableAutoCommitOnReturn());
      connectionFactory.setDefaultQueryTimeout(getDefaultQueryTimeout());
      validateConnectionFactory(connectionFactory);
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Cannot create PoolableConnectionFactory (" + e.getMessage() + ")", e);
    }
    return connectionFactory;
  }
  
  protected static void validateConnectionFactory(PoolableConnectionFactory connectionFactory)
    throws Exception
  {
    PoolableConnection conn = null;
    PooledObject<PoolableConnection> p = null;
    try
    {
      p = connectionFactory.makeObject();
      conn = (PoolableConnection)p.getObject();
      connectionFactory.activateObject(p);
      connectionFactory.validateConnection(conn);
      connectionFactory.passivateObject(p);
    }
    finally
    {
      if (p != null) {
        connectionFactory.destroyObject(p);
      }
    }
  }
  
  protected void log(String message)
  {
    if (this.logWriter != null) {
      this.logWriter.println(message);
    }
  }
  
  public BasicDataSource()
  {
    this.defaultAutoCommit = null;
    
    this.defaultReadOnly = null;
    
    this.defaultTransactionIsolation = -1;
    
    this.defaultQueryTimeout = null;
    
    this.defaultCatalog = null;
    
    this.cacheState = true;
    
    this.driver = null;
    
    this.driverClassName = null;
    
    this.driverClassLoader = null;
    
    this.lifo = true;
    
    this.maxTotal = 8;
    
    this.maxIdle = 8;
    
    this.minIdle = 0;
    
    this.initialSize = 0;
    
    this.maxWaitMillis = -1L;
    
    this.poolPreparedStatements = false;
    
    this.maxOpenPreparedStatements = -1;
    
    this.testOnCreate = false;
    
    this.testOnBorrow = true;
    
    this.testOnReturn = false;
    
    this.timeBetweenEvictionRunsMillis = -1L;
    
    this.numTestsPerEvictionRun = 3;
    
    this.minEvictableIdleTimeMillis = 1800000L;
    
    this.softMinEvictableIdleTimeMillis = -1L;
    
    this.evictionPolicyClassName = "org.apache.commons.pool2.impl.DefaultEvictionPolicy";
    
    this.testWhileIdle = false;
    
    this.password = null;
    
    this.url = null;
    
    this.username = null;
    
    this.validationQuery = null;
    
    this.validationQueryTimeout = -1;
    
    this.accessToUnderlyingConnectionAllowed = false;
    
    this.maxConnLifetimeMillis = -1L;
    
    this.jmxName = null;
    
    this.enableAutoCommitOnReturn = true;
    
    this.rollbackOnReturn = true;
    
    this.connectionPool = null;
    
    this.connectionProperties = new Properties();
    
    this.dataSource = null;
    
    this.logWriter = new PrintWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8));
    
    this.registeredJmxName = null;
  }
  
  private void jmxRegister()
  {
    if (this.registeredJmxName != null) {
      return;
    }
    String requestedName = getJmxName();
    if (requestedName == null) {
      return;
    }
    ObjectName oname;
    try
    {
      oname = new ObjectName(requestedName);
    }
    catch (MalformedObjectNameException e)
    {
      log.warn("The requested JMX name [" + requestedName + "] was not valid and will be ignored.");
      
      return;
    }
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    try
    {
      mbs.registerMBean(this, oname);
    }
    catch (InstanceAlreadyExistsException|MBeanRegistrationException|NotCompliantMBeanException e)
    {
      log.warn("Failed to complete JMX registration", e);
    }
  }
  
  public ObjectName preRegister(MBeanServer server, ObjectName name)
  {
    String requestedName = getJmxName();
    if (requestedName != null) {
      try
      {
        this.registeredJmxName = new ObjectName(requestedName);
      }
      catch (MalformedObjectNameException e)
      {
        log.warn("The requested JMX name [" + requestedName + "] was not valid and will be ignored.");
      }
    }
    if (this.registeredJmxName == null) {
      this.registeredJmxName = name;
    }
    return this.registeredJmxName;
  }
  
  private void updateJmxName(GenericObjectPoolConfig config)
  {
    if (this.registeredJmxName == null) {
      return;
    }
    StringBuilder base = new StringBuilder(this.registeredJmxName.toString());
    base.append(",connectionpool=");
    config.setJmxNameBase(base.toString());
    config.setJmxNamePrefix("connections");
  }
  
  protected ObjectName getRegisteredJmxName()
  {
    return this.registeredJmxName;
  }
  
  public void postRegister(Boolean registrationDone) {}
  
  public void preDeregister()
    throws Exception
  {}
  
  public void postDeregister() {}
  
  private class PaGetConnection
    implements PrivilegedExceptionAction<Connection>
  {
    private PaGetConnection() {}
    
    public Connection run()
      throws SQLException
    {
      return BasicDataSource.this.createDataSource().getConnection();
    }
  }
}
