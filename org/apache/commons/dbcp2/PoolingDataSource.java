package org.apache.commons.dbcp2;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class PoolingDataSource<C extends Connection>
  implements DataSource
{
  private static final Log log = LogFactory.getLog(PoolingDataSource.class);
  private boolean accessToUnderlyingConnectionAllowed = false;
  
  public PoolingDataSource(ObjectPool<C> pool)
  {
    if (null == pool) {
      throw new NullPointerException("Pool must not be null.");
    }
    this._pool = pool;
    if ((this._pool instanceof GenericObjectPool))
    {
      PoolableConnectionFactory pcf = (PoolableConnectionFactory)((GenericObjectPool)this._pool).getFactory();
      if (pcf == null) {
        throw new NullPointerException("PoolableConnectionFactory must not be null.");
      }
      if (pcf.getPool() != this._pool)
      {
        log.warn(Utils.getMessage("poolingDataSource.factoryConfig"));
        
        ObjectPool<PoolableConnection> p = this._pool;
        pcf.setPool(p);
      }
    }
  }
  
  public boolean isAccessToUnderlyingConnectionAllowed()
  {
    return this.accessToUnderlyingConnectionAllowed;
  }
  
  public void setAccessToUnderlyingConnectionAllowed(boolean allow)
  {
    this.accessToUnderlyingConnectionAllowed = allow;
  }
  
  public boolean isWrapperFor(Class<?> iface)
    throws SQLException
  {
    return false;
  }
  
  public <T> T unwrap(Class<T> iface)
    throws SQLException
  {
    throw new SQLException("PoolingDataSource is not a wrapper.");
  }
  
  public Logger getParentLogger()
    throws SQLFeatureNotSupportedException
  {
    throw new SQLFeatureNotSupportedException();
  }
  
  public Connection getConnection()
    throws SQLException
  {
    try
    {
      C conn = (Connection)this._pool.borrowObject();
      if (conn == null) {
        return null;
      }
      return new PoolGuardConnectionWrapper(conn);
    }
    catch (SQLException e)
    {
      throw e;
    }
    catch (NoSuchElementException e)
    {
      throw new SQLException("Cannot get a connection, pool error " + e.getMessage(), e);
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Cannot get a connection, general error", e);
    }
  }
  
  public Connection getConnection(String uname, String passwd)
    throws SQLException
  {
    throw new UnsupportedOperationException();
  }
  
  public PrintWriter getLogWriter()
  {
    return this._logWriter;
  }
  
  public int getLoginTimeout()
  {
    throw new UnsupportedOperationException("Login timeout is not supported.");
  }
  
  public void setLoginTimeout(int seconds)
  {
    throw new UnsupportedOperationException("Login timeout is not supported.");
  }
  
  public void setLogWriter(PrintWriter out)
  {
    this._logWriter = out;
  }
  
  private PrintWriter _logWriter = null;
  private final ObjectPool<C> _pool;
  
  protected ObjectPool<C> getPool()
  {
    return this._pool;
  }
  
  private class PoolGuardConnectionWrapper<D extends Connection>
    extends DelegatingConnection<D>
  {
    PoolGuardConnectionWrapper()
    {
      super();
    }
    
    public D getDelegate()
    {
      if (PoolingDataSource.this.isAccessToUnderlyingConnectionAllowed()) {
        return super.getDelegate();
      }
      return null;
    }
    
    public Connection getInnermostDelegate()
    {
      if (PoolingDataSource.this.isAccessToUnderlyingConnectionAllowed()) {
        return super.getInnermostDelegate();
      }
      return null;
    }
    
    public void close()
      throws SQLException
    {
      if (getDelegateInternal() != null)
      {
        super.close();
        super.setDelegate(null);
      }
    }
    
    public boolean isClosed()
      throws SQLException
    {
      if (getDelegateInternal() == null) {
        return true;
      }
      return super.isClosed();
    }
  }
}
