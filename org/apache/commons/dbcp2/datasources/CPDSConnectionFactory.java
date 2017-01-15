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
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class CPDSConnectionFactory
  implements PooledObjectFactory<PooledConnectionAndInfo>, ConnectionEventListener, PooledConnectionManager
{
  private static final String NO_KEY_MESSAGE = "close() was called on a Connection, but I have no record of the underlying PooledConnection.";
  private final ConnectionPoolDataSource _cpds;
  private final String _validationQuery;
  private final int _validationQueryTimeout;
  private final boolean _rollbackAfterValidation;
  private ObjectPool<PooledConnectionAndInfo> _pool;
  private final String _username;
  private String _password = null;
  private long maxConnLifetimeMillis = -1L;
  private final Set<PooledConnection> validatingSet = Collections.newSetFromMap(new ConcurrentHashMap());
  private final Map<PooledConnection, PooledConnectionAndInfo> pcMap = new ConcurrentHashMap();
  
  public CPDSConnectionFactory(ConnectionPoolDataSource cpds, String validationQuery, int validationQueryTimeout, boolean rollbackAfterValidation, String username, String password)
  {
    this._cpds = cpds;
    this._validationQuery = validationQuery;
    this._validationQueryTimeout = validationQueryTimeout;
    this._username = username;
    this._password = password;
    this._rollbackAfterValidation = rollbackAfterValidation;
  }
  
  public ObjectPool<PooledConnectionAndInfo> getPool()
  {
    return this._pool;
  }
  
  public void setPool(ObjectPool<PooledConnectionAndInfo> pool)
  {
    this._pool = pool;
  }
  
  public synchronized PooledObject<PooledConnectionAndInfo> makeObject()
  {
    PooledConnectionAndInfo pci;
    try
    {
      PooledConnection pc = null;
      if (this._username == null) {
        pc = this._cpds.getPooledConnection();
      } else {
        pc = this._cpds.getPooledConnection(this._username, this._password);
      }
      if (pc == null) {
        throw new IllegalStateException("Connection pool data source returned null from getPooledConnection");
      }
      pc.addConnectionEventListener(this);
      pci = new PooledConnectionAndInfo(pc, this._username, this._password);
      this.pcMap.put(pc, pci);
    }
    catch (SQLException e)
    {
      throw new RuntimeException(e.getMessage());
    }
    return new DefaultPooledObject(pci);
  }
  
  public void destroyObject(PooledObject<PooledConnectionAndInfo> p)
    throws Exception
  {
    doDestroyObject((PooledConnectionAndInfo)p.getObject());
  }
  
  private void doDestroyObject(PooledConnectionAndInfo pci)
    throws Exception
  {
    PooledConnection pc = pci.getPooledConnection();
    pc.removeConnectionEventListener(this);
    this.pcMap.remove(pc);
    pc.close();
  }
  
  public boolean validateObject(PooledObject<PooledConnectionAndInfo> p)
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
  
  public void passivateObject(PooledObject<PooledConnectionAndInfo> p)
    throws Exception
  {
    validateLifetime(p);
  }
  
  public void activateObject(PooledObject<PooledConnectionAndInfo> p)
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
        this._pool.returnObject(pci);
      }
      catch (Exception e)
      {
        System.err.println("CLOSING DOWN CONNECTION AS IT COULD NOT BE RETURNED TO THE POOL");
        
        pc.removeConnectionEventListener(this);
        try
        {
          doDestroyObject(pci);
        }
        catch (Exception e2)
        {
          System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + pci);
          
          e2.printStackTrace();
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
    
    PooledConnectionAndInfo pci = (PooledConnectionAndInfo)this.pcMap.get(pc);
    if (pci == null) {
      throw new IllegalStateException("close() was called on a Connection, but I have no record of the underlying PooledConnection.");
    }
    try
    {
      this._pool.invalidateObject(pci);
    }
    catch (Exception e)
    {
      System.err.println("EXCEPTION WHILE DESTROYING OBJECT " + pci);
      e.printStackTrace();
    }
  }
  
  public void invalidate(PooledConnection pc)
    throws SQLException
  {
    PooledConnectionAndInfo pci = (PooledConnectionAndInfo)this.pcMap.get(pc);
    if (pci == null) {
      throw new IllegalStateException("close() was called on a Connection, but I have no record of the underlying PooledConnection.");
    }
    try
    {
      this._pool.invalidateObject(pci);
      this._pool.close();
    }
    catch (Exception ex)
    {
      throw new SQLException("Error invalidating connection", ex);
    }
  }
  
  public synchronized void setPassword(String password)
  {
    this._password = password;
  }
  
  public void setMaxConnLifetimeMillis(long maxConnLifetimeMillis)
  {
    this.maxConnLifetimeMillis = maxConnLifetimeMillis;
  }
  
  public void closePool(String username)
    throws SQLException
  {
    synchronized (this)
    {
      if ((username == null) || (!username.equals(this._username))) {
        return;
      }
    }
    try
    {
      this._pool.close();
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
