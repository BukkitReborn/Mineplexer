package org.apache.commons.dbcp2;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import org.apache.commons.pool2.ObjectPool;

public class PoolableConnection
  extends DelegatingConnection<Connection>
  implements PoolableConnectionMXBean
{
  private static MBeanServer MBEAN_SERVER = null;
  
  static
  {
    try
    {
      MBEAN_SERVER = ManagementFactory.getPlatformMBeanServer();
    }
    catch (Exception ex) {}
  }
  
  private ObjectPool<PoolableConnection> _pool = null;
  private final ObjectName _jmxName;
  private PreparedStatement validationPreparedStatement = null;
  private String lastValidationSql = null;
  
  public PoolableConnection(Connection conn, ObjectPool<PoolableConnection> pool, ObjectName jmxName)
  {
    super(conn);
    this._pool = pool;
    this._jmxName = jmxName;
    if (jmxName != null) {
      try
      {
        MBEAN_SERVER.registerMBean(this, jmxName);
      }
      catch (InstanceAlreadyExistsException|MBeanRegistrationException|NotCompliantMBeanException e) {}
    }
  }
  
  protected void passivate()
    throws SQLException
  {
    super.passivate();
    setClosedInternal(true);
  }
  
  public boolean isClosed()
    throws SQLException
  {
    if (isClosedInternal()) {
      return true;
    }
    if (getDelegateInternal().isClosed())
    {
      close();
      return true;
    }
    return false;
  }
  
  public synchronized void close()
    throws SQLException
  {
    if (isClosedInternal()) {
      return;
    }
    boolean isUnderlyingConectionClosed;
    try
    {
      isUnderlyingConectionClosed = getDelegateInternal().isClosed();
    }
    catch (SQLException e)
    {
      try
      {
        this._pool.invalidateObject(this);
      }
      catch (IllegalStateException ise)
      {
        passivate();
        getInnermostDelegate().close();
      }
      catch (Exception ie) {}
      throw new SQLException("Cannot close connection (isClosed check failed)", e);
    }
    if (isUnderlyingConectionClosed) {
      try
      {
        this._pool.invalidateObject(this);
      }
      catch (IllegalStateException e)
      {
        passivate();
        getInnermostDelegate().close();
      }
      catch (Exception e)
      {
        throw new SQLException("Cannot close connection (invalidating pooled object failed)", e);
      }
    } else {
      try
      {
        this._pool.returnObject(this);
      }
      catch (IllegalStateException e)
      {
        passivate();
        getInnermostDelegate().close();
      }
      catch (SQLException e)
      {
        throw e;
      }
      catch (RuntimeException e)
      {
        throw e;
      }
      catch (Exception e)
      {
        throw new SQLException("Cannot close connection (return to pool failed)", e);
      }
    }
  }
  
  public void reallyClose()
    throws SQLException
  {
    if (this._jmxName != null) {
      try
      {
        MBEAN_SERVER.unregisterMBean(this._jmxName);
      }
      catch (MBeanRegistrationException|InstanceNotFoundException e) {}
    }
    if (this.validationPreparedStatement != null) {
      try
      {
        this.validationPreparedStatement.close();
      }
      catch (SQLException sqle) {}
    }
    super.closeInternal();
  }
  
  public String getToString()
  {
    return toString();
  }
  
  public void validate(String sql, int timeout)
    throws SQLException
  {
    if ((sql == null) || (sql.length() == 0))
    {
      if (timeout < 0) {
        timeout = 0;
      }
      if (!isValid(timeout)) {
        throw new SQLException("isValid() returned false");
      }
      return;
    }
    if (!sql.equals(this.lastValidationSql))
    {
      this.lastValidationSql = sql;
      
      this.validationPreparedStatement = getInnermostDelegateInternal().prepareStatement(sql);
    }
    if (timeout > 0) {
      this.validationPreparedStatement.setQueryTimeout(timeout);
    }
    try
    {
      ResultSet rs = this.validationPreparedStatement.executeQuery();Throwable localThrowable2 = null;
      try
      {
        if (!rs.next()) {
          throw new SQLException("validationQuery didn't return a row");
        }
      }
      catch (Throwable localThrowable1)
      {
        localThrowable2 = localThrowable1;throw localThrowable1;
      }
      finally
      {
        if (rs != null) {
          if (localThrowable2 != null) {
            try
            {
              rs.close();
            }
            catch (Throwable x2)
            {
              localThrowable2.addSuppressed(x2);
            }
          } else {
            rs.close();
          }
        }
      }
    }
    catch (SQLException sqle)
    {
      throw sqle;
    }
  }
}
