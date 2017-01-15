package org.apache.commons.dbcp2.datasources;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;
import org.apache.commons.dbcp2.Utils;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class KeyedCPDSConnectionFactory
  implements KeyedPooledObjectFactory<UserPassKey, PooledConnectionAndInfo>, ConnectionEventListener, PooledConnectionManager
{
  private static final String NO_KEY_MESSAGE = "close() was called on a Connection, but I have no record of the underlying PooledConnection.";
  private final ConnectionPoolDataSource _cpds;
  private final String _validationQuery;
  private final int _validationQueryTimeout;
  private final boolean _rollbackAfterValidation;
  private KeyedObjectPool<UserPassKey, PooledConnectionAndInfo> _pool;
  private long maxConnLifetimeMillis = -1L;
  private final Set<PooledConnection> validatingSet = Collections.newSetFromMap(new ConcurrentHashMap());
  private final Map<PooledConnection, PooledConnectionAndInfo> pcMap = new ConcurrentHashMap();
  
  public KeyedCPDSConnectionFactory(ConnectionPoolDataSource cpds, String validationQuery, int validationQueryTimeout, boolean rollbackAfterValidation)
  {
    this._cpds = cpds;
    this._validationQuery = validationQuery;
    this._validationQueryTimeout = validationQueryTimeout;
    this._rollbackAfterValidation = rollbackAfterValidation;
  }
  
  public void setPool(KeyedObjectPool<UserPassKey, PooledConnectionAndInfo> pool)
  {
    this._pool = pool;
  }
  
  public KeyedObjectPool<UserPassKey, PooledConnectionAndInfo> getPool()
  {
    return this._pool;
  }
  
  public synchronized PooledObject<PooledConnectionAndInfo> makeObject(UserPassKey upkey)
    throws Exception
  {
    PooledConnectionAndInfo pci = null;
    
    PooledConnection pc = null;
    String username = upkey.getUsername();
    String password = upkey.getPassword();
    if (username == null) {
      pc = this._cpds.getPooledConnection();
    } else {
      pc = this._cpds.getPooledConnection(username, password);
    }
    if (pc == null) {
      throw new IllegalStateException("Connection pool data source returned null from getPooledConnection");
    }
    pc.addConnectionEventListener(this);
    pci = new PooledConnectionAndInfo(pc, username, password);
    this.pcMap.put(pc, pci);
    
    return new DefaultPooledObject(pci);
  }
  
  public void destroyObject(UserPassKey key, PooledObject<PooledConnectionAndInfo> p)
    throws Exception
  {
    PooledConnection pc = ((PooledConnectionAndInfo)p.getObject()).getPooledConnection();
    pc.removeConnectionEventListener(this);
    this.pcMap.remove(pc);
    pc.close();
  }
  
  public boolean validateObject(UserPassKey key, PooledObject<PooledConnectionAndInfo> p)
  {
    try
    {
      validateLifetime(p);
    }
    catch (Exception e)
    {
      return false;
    }
    boolean valid = false;
    PooledConnection pconn = ((PooledConnectionAndInfo)p.getObject()).getPooledConnection();
    if (null == this._validationQuery)
    {
      int timeout = this._validationQueryTimeout;
      if (timeout < 0) {
        timeout = 0;
      }
      try
      {
        valid = pconn.getConnection().isValid(timeout);
      }
      catch (SQLException e)
      {
        valid = false;
      }
    }
    else
    {
      Connection conn = null;
      Statement stmt = null;
      ResultSet rset = null;
      
      this.validatingSet.add(pconn);
      try
      {
        conn = pconn.getConnection();
        stmt = conn.createStatement();
        rset = stmt.executeQuery(this._validationQuery);
        if (rset.next()) {
          valid = true;
        } else {
          valid = false;
        }
        if (this._rollbackAfterValidation) {
          conn.rollback();
        }
      }
      catch (Exception e)
      {
        valid = false;
      }
      finally
      {
        Utils.closeQuietly(rset);
        Utils.closeQuietly(stmt);
        Utils.closeQuietly(conn);
        this.validatingSet.remove(pconn);
      }
    }
    return valid;
  }
  
  public void passivateObject(UserPassKey key, PooledObject<PooledConnectionAndInfo> p)
    throws Exception
  {
    validateLifetime(p);
  }
  
  public void activateObject(UserPassKey key, PooledObject<PooledConnectionAndInfo> p)
    throws Exception
  {
    validateLifetime(p);
  }
  
  public void connectionClosed(ConnectionEvent event)
  {
    PooledConnection pc = (PooledConnection)event.getSource();
    if (!this.validatingSet.contains(pc))
    {
      PooledConnectionAndInfo pci = (PooledConnectionAndInfo)this.pcMap.get(pc);
      if (pci == null) {
        throw new IllegalStateException("close() was called on a Connection, but I have no record of the underlying PooledConnection.");
      }
      try
      {
        this._pool.returnObject(pci.getUserPassKey(), pci);
      }
      catch (Exception e)
      {
        System.err.println("CLOSING DOWN CONNECTION AS IT COULD NOT BE RETURNED TO THE POOL");
        
        pc.removeConnectionEventListener(this);
        try
        {
          this._pool.invalidateObject(pci.getUserPassKey(), pci);
        }
        catch (Exception e3)
        {
          System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + pci);
          
          e3.printStackTrace();
        }
      }
    }
  }
  
  public void connectionErrorOccurred(ConnectionEvent event)
  {
    PooledConnection pc = (PooledConnection)event.getSource();
    if (null != event.getSQLException()) {
      System.err.println("CLOSING DOWN CONNECTION DUE TO INTERNAL ERROR (" + event.getSQLException() + ")");
    }
    pc.removeConnectionEventListener(this);
    
    PooledConnectionAndInfo info = (PooledConnectionAndInfo)this.pcMap.get(pc);
    if (info == null) {
      throw new IllegalStateException("close() was called on a Connection, but I have no record of the underlying PooledConnection.");
    }
    try
    {
      this._pool.invalidateObject(info.getUserPassKey(), info);
    }
    catch (Exception e)
    {
      System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + info);
      e.printStackTrace();
    }
  }
  
  public void invalidate(PooledConnection pc)
    throws SQLException
  {
    PooledConnectionAndInfo info = (PooledConnectionAndInfo)this.pcMap.get(pc);
    if (info == null) {
      throw new IllegalStateException("close() was called on a Connection, but I have no record of the underlying PooledConnection.");
    }
    UserPassKey key = info.getUserPassKey();
    try
    {
      this._pool.invalidateObject(key, info);
      this._pool.clear(key);
    }
    catch (Exception ex)
    {
      throw new SQLException("Error invalidating connection", ex);
    }
  }
  
  public void setPassword(String password) {}
  
  public void setMaxConnLifetimeMillis(long maxConnLifetimeMillis)
  {
    this.maxConnLifetimeMillis = maxConnLifetimeMillis;
  }
  
  public void closePool(String username)
    throws SQLException
  {
    try
    {
      this._pool.clear(new UserPassKey(username, null));
    }
    catch (Exception ex)
    {
      throw new SQLException("Error closing connection pool", ex);
    }
  }
  
  private void validateLifetime(PooledObject<PooledConnectionAndInfo> p)
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
}
