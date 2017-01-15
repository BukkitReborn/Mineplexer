package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import org.apache.commons.pool2.ObjectPool;

public class PoolingDriver
  implements Driver
{
  static
  {
    try
    {
      DriverManager.registerDriver(new PoolingDriver());
    }
    catch (Exception e) {}
  }
  
  protected static final HashMap<String, ObjectPool<? extends Connection>> pools = new HashMap();
  private final boolean accessToUnderlyingConnectionAllowed;
  protected static final String URL_PREFIX = "jdbc:apache:commons:dbcp:";
  
  public PoolingDriver()
  {
    this(true);
  }
  
  protected PoolingDriver(boolean accessToUnderlyingConnectionAllowed)
  {
    this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
  }
  
  protected boolean isAccessToUnderlyingConnectionAllowed()
  {
    return this.accessToUnderlyingConnectionAllowed;
  }
  
  public synchronized ObjectPool<? extends Connection> getConnectionPool(String name)
    throws SQLException
  {
    ObjectPool<? extends Connection> pool = (ObjectPool)pools.get(name);
    if (null == pool) {
      throw new SQLException("Pool not registered.");
    }
    return pool;
  }
  
  public synchronized void registerPool(String name, ObjectPool<? extends Connection> pool)
  {
    pools.put(name, pool);
  }
  
  public synchronized void closePool(String name)
    throws SQLException
  {
    ObjectPool<? extends Connection> pool = (ObjectPool)pools.get(name);
    if (pool != null)
    {
      pools.remove(name);
      try
      {
        pool.close();
      }
      catch (Exception e)
      {
        throw new SQLException("Error closing pool " + name, e);
      }
    }
  }
  
  public synchronized String[] getPoolNames()
  {
    Set<String> names = pools.keySet();
    return (String[])names.toArray(new String[names.size()]);
  }
  
  public boolean acceptsURL(String url)
    throws SQLException
  {
    try
    {
      return url.startsWith("jdbc:apache:commons:dbcp:");
    }
    catch (NullPointerException e) {}
    return false;
  }
  
  public Connection connect(String url, Properties info)
    throws SQLException
  {
    if (acceptsURL(url))
    {
      ObjectPool<? extends Connection> pool = getConnectionPool(url.substring(URL_PREFIX_LEN));
      try
      {
        Connection conn = (Connection)pool.borrowObject();
        if (conn == null) {
          return null;
        }
        return new PoolGuardConnectionWrapper(pool, conn);
      }
      catch (SQLException e)
      {
        throw e;
      }
      catch (NoSuchElementException e)
      {
        throw new SQLException("Cannot get a connection, pool error: " + e.getMessage(), e);
      }
      catch (RuntimeException e)
      {
        throw e;
      }
      catch (Exception e)
      {
        throw new SQLException("Cannot get a connection, general error: " + e.getMessage(), e);
      }
    }
    return null;
  }
  
  public Logger getParentLogger()
    throws SQLFeatureNotSupportedException
  {
    throw new SQLFeatureNotSupportedException();
  }
  
  public void invalidateConnection(Connection conn)
    throws SQLException
  {
    if ((conn instanceof PoolGuardConnectionWrapper))
    {
      PoolGuardConnectionWrapper pgconn = (PoolGuardConnectionWrapper)conn;
      
      ObjectPool<Connection> pool = pgconn.pool;
      try
      {
        pool.invalidateObject(pgconn.getDelegateInternal());
      }
      catch (Exception e) {}
    }
    else
    {
      throw new SQLException("Invalid connection class");
    }
  }
  
  public int getMajorVersion()
  {
    return 1;
  }
  
  public int getMinorVersion()
  {
    return 0;
  }
  
  public boolean jdbcCompliant()
  {
    return true;
  }
  
  public DriverPropertyInfo[] getPropertyInfo(String url, Properties info)
  {
    return new DriverPropertyInfo[0];
  }
  
  protected static final int URL_PREFIX_LEN = "jdbc:apache:commons:dbcp:".length();
  protected static final int MAJOR_VERSION = 1;
  protected static final int MINOR_VERSION = 0;
  
  private class PoolGuardConnectionWrapper
    extends DelegatingConnection<Connection>
  {
    private final ObjectPool<? extends Connection> pool;
    
    PoolGuardConnectionWrapper(Connection pool)
    {
      super();
      this.pool = pool;
    }
    
    public Connection getDelegate()
    {
      if (PoolingDriver.this.isAccessToUnderlyingConnectionAllowed()) {
        return super.getDelegate();
      }
      return null;
    }
    
    public Connection getInnermostDelegate()
    {
      if (PoolingDriver.this.isAccessToUnderlyingConnectionAllowed()) {
        return super.getInnermostDelegate();
      }
      return null;
    }
  }
}
