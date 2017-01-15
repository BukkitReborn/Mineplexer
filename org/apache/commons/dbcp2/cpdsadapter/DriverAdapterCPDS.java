package org.apache.commons.dbcp2.cpdsadapter;

import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.Reference;
import javax.naming.Referenceable;
import javax.naming.StringRefAddr;
import javax.naming.spi.ObjectFactory;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import org.apache.commons.dbcp2.PoolablePreparedStatement;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

public class DriverAdapterCPDS
  implements ConnectionPoolDataSource, Referenceable, Serializable, ObjectFactory
{
  private static final long serialVersionUID = -4820523787212147844L;
  private static final String GET_CONNECTION_CALLED = "A PooledConnection was already requested from this source, further initialization is not allowed.";
  private String description;
  private String password;
  private String url;
  private String user;
  private String driver;
  private int loginTimeout;
  private transient PrintWriter logWriter = null;
  private boolean poolPreparedStatements;
  private int maxIdle = 10;
  private long _timeBetweenEvictionRunsMillis = -1L;
  private int _numTestsPerEvictionRun = -1;
  private int _minEvictableIdleTimeMillis = -1;
  private int _maxPreparedStatements = -1;
  private volatile boolean getConnectionCalled = false;
  private Properties connectionProperties = null;
  
  static
  {
    DriverManager.getDrivers();
  }
  
  private boolean accessToUnderlyingConnectionAllowed = false;
  
  public PooledConnection getPooledConnection()
    throws SQLException
  {
    return getPooledConnection(getUser(), getPassword());
  }
  
  public PooledConnection getPooledConnection(String username, String pass)
    throws SQLException
  {
    this.getConnectionCalled = true;
    PooledConnectionImpl pci = null;
    try
    {
      if (this.connectionProperties != null)
      {
        this.connectionProperties.put("user", username);
        this.connectionProperties.put("password", pass);
        pci = new PooledConnectionImpl(DriverManager.getConnection(getUrl(), this.connectionProperties));
      }
      else
      {
        pci = new PooledConnectionImpl(DriverManager.getConnection(getUrl(), username, pass));
      }
      pci.setAccessToUnderlyingConnectionAllowed(isAccessToUnderlyingConnectionAllowed());
    }
    catch (ClassCircularityError e)
    {
      if (this.connectionProperties != null) {
        pci = new PooledConnectionImpl(DriverManager.getConnection(getUrl(), this.connectionProperties));
      } else {
        pci = new PooledConnectionImpl(DriverManager.getConnection(getUrl(), username, pass));
      }
      pci.setAccessToUnderlyingConnectionAllowed(isAccessToUnderlyingConnectionAllowed());
    }
    KeyedObjectPool<PStmtKeyCPDS, PoolablePreparedStatement<PStmtKeyCPDS>> stmtPool = null;
    if (isPoolPreparedStatements())
    {
      GenericKeyedObjectPoolConfig config = new GenericKeyedObjectPoolConfig();
      config.setMaxTotalPerKey(Integer.MAX_VALUE);
      config.setBlockWhenExhausted(false);
      config.setMaxWaitMillis(0L);
      config.setMaxIdlePerKey(getMaxIdle());
      if (getMaxPreparedStatements() <= 0)
      {
        config.setTimeBetweenEvictionRunsMillis(getTimeBetweenEvictionRunsMillis());
        config.setNumTestsPerEvictionRun(getNumTestsPerEvictionRun());
        config.setMinEvictableIdleTimeMillis(getMinEvictableIdleTimeMillis());
      }
      else
      {
        config.setMaxTotal(getMaxPreparedStatements());
        config.setTimeBetweenEvictionRunsMillis(-1L);
        config.setNumTestsPerEvictionRun(0);
        config.setMinEvictableIdleTimeMillis(0L);
      }
      stmtPool = new GenericKeyedObjectPool(pci, config);
      pci.setStatementPool(stmtPool);
    }
    return pci;
  }
  
  public Logger getParentLogger()
    throws SQLFeatureNotSupportedException
  {
    throw new SQLFeatureNotSupportedException();
  }
  
  public Reference getReference()
    throws NamingException
  {
    String factory = getClass().getName();
    
    Reference ref = new Reference(getClass().getName(), factory, null);
    
    ref.add(new StringRefAddr("description", getDescription()));
    ref.add(new StringRefAddr("driver", getDriver()));
    ref.add(new StringRefAddr("loginTimeout", String.valueOf(getLoginTimeout())));
    
    ref.add(new StringRefAddr("password", getPassword()));
    ref.add(new StringRefAddr("user", getUser()));
    ref.add(new StringRefAddr("url", getUrl()));
    
    ref.add(new StringRefAddr("poolPreparedStatements", String.valueOf(isPoolPreparedStatements())));
    
    ref.add(new StringRefAddr("maxIdle", String.valueOf(getMaxIdle())));
    
    ref.add(new StringRefAddr("timeBetweenEvictionRunsMillis", String.valueOf(getTimeBetweenEvictionRunsMillis())));
    
    ref.add(new StringRefAddr("numTestsPerEvictionRun", String.valueOf(getNumTestsPerEvictionRun())));
    
    ref.add(new StringRefAddr("minEvictableIdleTimeMillis", String.valueOf(getMinEvictableIdleTimeMillis())));
    
    ref.add(new StringRefAddr("maxPreparedStatements", String.valueOf(getMaxPreparedStatements())));
    
    return ref;
  }
  
  public Object getObjectInstance(Object refObj, Name name, Context context, Hashtable<?, ?> env)
    throws Exception
  {
    DriverAdapterCPDS cpds = null;
    if ((refObj instanceof Reference))
    {
      Reference ref = (Reference)refObj;
      if (ref.getClassName().equals(getClass().getName()))
      {
        RefAddr ra = ref.get("description");
        if ((ra != null) && (ra.getContent() != null)) {
          setDescription(ra.getContent().toString());
        }
        ra = ref.get("driver");
        if ((ra != null) && (ra.getContent() != null)) {
          setDriver(ra.getContent().toString());
        }
        ra = ref.get("url");
        if ((ra != null) && (ra.getContent() != null)) {
          setUrl(ra.getContent().toString());
        }
        ra = ref.get("user");
        if ((ra != null) && (ra.getContent() != null)) {
          setUser(ra.getContent().toString());
        }
        ra = ref.get("password");
        if ((ra != null) && (ra.getContent() != null)) {
          setPassword(ra.getContent().toString());
        }
        ra = ref.get("poolPreparedStatements");
        if ((ra != null) && (ra.getContent() != null)) {
          setPoolPreparedStatements(Boolean.valueOf(ra.getContent().toString()).booleanValue());
        }
        ra = ref.get("maxIdle");
        if ((ra != null) && (ra.getContent() != null)) {
          setMaxIdle(Integer.parseInt(ra.getContent().toString()));
        }
        ra = ref.get("timeBetweenEvictionRunsMillis");
        if ((ra != null) && (ra.getContent() != null)) {
          setTimeBetweenEvictionRunsMillis(Integer.parseInt(ra.getContent().toString()));
        }
        ra = ref.get("numTestsPerEvictionRun");
        if ((ra != null) && (ra.getContent() != null)) {
          setNumTestsPerEvictionRun(Integer.parseInt(ra.getContent().toString()));
        }
        ra = ref.get("minEvictableIdleTimeMillis");
        if ((ra != null) && (ra.getContent() != null)) {
          setMinEvictableIdleTimeMillis(Integer.parseInt(ra.getContent().toString()));
        }
        ra = ref.get("maxPreparedStatements");
        if ((ra != null) && (ra.getContent() != null)) {
          setMaxPreparedStatements(Integer.parseInt(ra.getContent().toString()));
        }
        ra = ref.get("accessToUnderlyingConnectionAllowed");
        if ((ra != null) && (ra.getContent() != null)) {
          setAccessToUnderlyingConnectionAllowed(Boolean.valueOf(ra.getContent().toString()).booleanValue());
        }
        cpds = this;
      }
    }
    return cpds;
  }
  
  private void assertInitializationAllowed()
    throws IllegalStateException
  {
    if (this.getConnectionCalled) {
      throw new IllegalStateException("A PooledConnection was already requested from this source, further initialization is not allowed.");
    }
  }
  
  public Properties getConnectionProperties()
  {
    return this.connectionProperties;
  }
  
  public void setConnectionProperties(Properties props)
  {
    assertInitializationAllowed();
    this.connectionProperties = props;
    if (this.connectionProperties.containsKey("user")) {
      setUser(this.connectionProperties.getProperty("user"));
    }
    if (this.connectionProperties.containsKey("password")) {
      setPassword(this.connectionProperties.getProperty("password"));
    }
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String v)
  {
    this.description = v;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String v)
  {
    assertInitializationAllowed();
    this.password = v;
    if (this.connectionProperties != null) {
      this.connectionProperties.setProperty("password", v);
    }
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String v)
  {
    assertInitializationAllowed();
    this.url = v;
  }
  
  public String getUser()
  {
    return this.user;
  }
  
  public void setUser(String v)
  {
    assertInitializationAllowed();
    this.user = v;
    if (this.connectionProperties != null) {
      this.connectionProperties.setProperty("user", v);
    }
  }
  
  public String getDriver()
  {
    return this.driver;
  }
  
  public void setDriver(String v)
    throws ClassNotFoundException
  {
    assertInitializationAllowed();
    this.driver = v;
    
    Class.forName(v);
  }
  
  public int getLoginTimeout()
  {
    return this.loginTimeout;
  }
  
  public PrintWriter getLogWriter()
  {
    return this.logWriter;
  }
  
  public void setLoginTimeout(int seconds)
  {
    this.loginTimeout = seconds;
  }
  
  public void setLogWriter(PrintWriter out)
  {
    this.logWriter = out;
  }
  
  public boolean isPoolPreparedStatements()
  {
    return this.poolPreparedStatements;
  }
  
  public void setPoolPreparedStatements(boolean v)
  {
    assertInitializationAllowed();
    this.poolPreparedStatements = v;
  }
  
  public int getMaxIdle()
  {
    return this.maxIdle;
  }
  
  public void setMaxIdle(int maxIdle)
  {
    assertInitializationAllowed();
    this.maxIdle = maxIdle;
  }
  
  public long getTimeBetweenEvictionRunsMillis()
  {
    return this._timeBetweenEvictionRunsMillis;
  }
  
  public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis)
  {
    assertInitializationAllowed();
    this._timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
  }
  
  public int getNumTestsPerEvictionRun()
  {
    return this._numTestsPerEvictionRun;
  }
  
  public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun)
  {
    assertInitializationAllowed();
    this._numTestsPerEvictionRun = numTestsPerEvictionRun;
  }
  
  public int getMinEvictableIdleTimeMillis()
  {
    return this._minEvictableIdleTimeMillis;
  }
  
  public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis)
  {
    assertInitializationAllowed();
    this._minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
  }
  
  public synchronized boolean isAccessToUnderlyingConnectionAllowed()
  {
    return this.accessToUnderlyingConnectionAllowed;
  }
  
  public synchronized void setAccessToUnderlyingConnectionAllowed(boolean allow)
  {
    this.accessToUnderlyingConnectionAllowed = allow;
  }
  
  public int getMaxPreparedStatements()
  {
    return this._maxPreparedStatements;
  }
  
  public void setMaxPreparedStatements(int maxPreparedStatements)
  {
    this._maxPreparedStatements = maxPreparedStatements;
  }
}
