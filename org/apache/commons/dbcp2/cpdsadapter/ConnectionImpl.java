package org.apache.commons.dbcp2.cpdsadapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.commons.dbcp2.DelegatingConnection;
import org.apache.commons.dbcp2.DelegatingPreparedStatement;

class ConnectionImpl
  extends DelegatingConnection<Connection>
{
  private final boolean accessToUnderlyingConnectionAllowed;
  private final PooledConnectionImpl pooledConnection;
  
  ConnectionImpl(PooledConnectionImpl pooledConnection, Connection connection, boolean accessToUnderlyingConnectionAllowed)
  {
    super(connection);
    this.pooledConnection = pooledConnection;
    this.accessToUnderlyingConnectionAllowed = accessToUnderlyingConnectionAllowed;
  }
  
  public void close()
    throws SQLException
  {
    if (!isClosedInternal()) {
      try
      {
        passivate();
      }
      finally
      {
        setClosedInternal(true);
        this.pooledConnection.notifyListeners();
      }
    }
  }
  
  public PreparedStatement prepareStatement(String sql)
    throws SQLException
  {
    checkOpen();
    try
    {
      return new DelegatingPreparedStatement(this, this.pooledConnection.prepareStatement(sql));
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
    throws SQLException
  {
    checkOpen();
    try
    {
      return new DelegatingPreparedStatement(this, this.pooledConnection.prepareStatement(sql, resultSetType, resultSetConcurrency));
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
    throws SQLException
  {
    checkOpen();
    try
    {
      return new DelegatingPreparedStatement(this, this.pooledConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
    throws SQLException
  {
    checkOpen();
    try
    {
      return new DelegatingPreparedStatement(this, this.pooledConnection.prepareStatement(sql, autoGeneratedKeys));
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
    throws SQLException
  {
    checkOpen();
    try
    {
      return new DelegatingPreparedStatement(this, this.pooledConnection.prepareStatement(sql, columnIndexes));
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public PreparedStatement prepareStatement(String sql, String[] columnNames)
    throws SQLException
  {
    checkOpen();
    try
    {
      return new DelegatingPreparedStatement(this, this.pooledConnection.prepareStatement(sql, columnNames));
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public boolean isAccessToUnderlyingConnectionAllowed()
  {
    return this.accessToUnderlyingConnectionAllowed;
  }
  
  public Connection getDelegate()
  {
    if (isAccessToUnderlyingConnectionAllowed()) {
      return getDelegateInternal();
    }
    return null;
  }
  
  public Connection getInnermostDelegate()
  {
    if (isAccessToUnderlyingConnectionAllowed()) {
      return super.getInnermostDelegateInternal();
    }
    return null;
  }
}
