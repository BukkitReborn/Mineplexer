package org.apache.commons.dbcp2.cpdsadapter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.StatementEventListener;
import org.apache.commons.dbcp2.DelegatingConnection;
import org.apache.commons.dbcp2.PoolablePreparedStatement;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

class PooledConnectionImpl
  implements PooledConnection, KeyedPooledObjectFactory<PStmtKeyCPDS, PoolablePreparedStatement<PStmtKeyCPDS>>
{
  private static final String CLOSED = "Attempted to use PooledConnection after closed() was called.";
  private Connection connection = null;
  private final DelegatingConnection<?> delegatingConnection;
  private Connection logicalConnection = null;
  private final Vector<ConnectionEventListener> eventListeners;
  private final Vector<StatementEventListener> statementEventListeners = new Vector();
  private boolean isClosed;
  private KeyedObjectPool<PStmtKeyCPDS, PoolablePreparedStatement<PStmtKeyCPDS>> pstmtPool = null;
  private boolean accessToUnderlyingConnectionAllowed = false;
  
  PooledConnectionImpl(Connection connection)
  {
    this.connection = connection;
    if ((connection instanceof DelegatingConnection)) {
      this.delegatingConnection = ((DelegatingConnection)connection);
    } else {
      this.delegatingConnection = new DelegatingConnection(connection);
    }
    this.eventListeners = new Vector();
    this.isClosed = false;
  }
  
  public void setStatementPool(KeyedObjectPool<PStmtKeyCPDS, PoolablePreparedStatement<PStmtKeyCPDS>> statementPool)
  {
    this.pstmtPool = statementPool;
  }
  
  public void addConnectionEventListener(ConnectionEventListener listener)
  {
    if (!this.eventListeners.contains(listener)) {
      this.eventListeners.add(listener);
    }
  }
  
  public void addStatementEventListener(StatementEventListener listener)
  {
    if (!this.statementEventListeners.contains(listener)) {
      this.statementEventListeners.add(listener);
    }
  }
  
  public void close()
    throws SQLException
  {
    assertOpen();
    this.isClosed = true;
    try
    {
      if (this.pstmtPool != null) {
        try
        {
          this.pstmtPool.close();
        }
        finally
        {
          this.pstmtPool = null;
        }
      }
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Cannot close connection (return to pool failed)", e);
    }
    finally
    {
      try
      {
        this.connection.close();
      }
      finally
      {
        this.connection = null;
      }
    }
  }
  
  private void assertOpen()
    throws SQLException
  {
    if (this.isClosed) {
      throw new SQLException("Attempted to use PooledConnection after closed() was called.");
    }
  }
  
  public Connection getConnection()
    throws SQLException
  {
    assertOpen();
    if ((this.logicalConnection != null) && (!this.logicalConnection.isClosed())) {
      throw new SQLException("PooledConnection was reused, withoutits previous Connection being closed.");
    }
    this.logicalConnection = new ConnectionImpl(this, this.connection, isAccessToUnderlyingConnectionAllowed());
    
    return this.logicalConnection;
  }
  
  public void removeConnectionEventListener(ConnectionEventListener listener)
  {
    this.eventListeners.remove(listener);
  }
  
  public void removeStatementEventListener(StatementEventListener listener)
  {
    this.statementEventListeners.remove(listener);
  }
  
  protected void finalize()
    throws Throwable
  {
    try
    {
      this.connection.close();
    }
    catch (Exception ignored) {}
    if ((this.logicalConnection != null) && (!this.logicalConnection.isClosed())) {
      throw new SQLException("PooledConnection was gc'ed, withoutits last Connection being closed.");
    }
  }
  
  void notifyListeners()
  {
    ConnectionEvent event = new ConnectionEvent(this);
    Object[] listeners = this.eventListeners.toArray();
    for (Object listener : listeners) {
      ((ConnectionEventListener)listener).connectionClosed(event);
    }
  }
  
  PreparedStatement prepareStatement(String sql)
    throws SQLException
  {
    if (this.pstmtPool == null) {
      return this.connection.prepareStatement(sql);
    }
    try
    {
      return (PreparedStatement)this.pstmtPool.borrowObject(createKey(sql));
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Borrow prepareStatement from pool failed", e);
    }
  }
  
  PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
    throws SQLException
  {
    if (this.pstmtPool == null) {
      return this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    try
    {
      return (PreparedStatement)this.pstmtPool.borrowObject(createKey(sql, resultSetType, resultSetConcurrency));
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Borrow prepareStatement from pool failed", e);
    }
  }
  
  PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
    throws SQLException
  {
    if (this.pstmtPool == null) {
      return this.connection.prepareStatement(sql, autoGeneratedKeys);
    }
    try
    {
      return (PreparedStatement)this.pstmtPool.borrowObject(createKey(sql, autoGeneratedKeys));
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Borrow prepareStatement from pool failed", e);
    }
  }
  
  PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
    throws SQLException
  {
    if (this.pstmtPool == null) {
      return this.connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    try
    {
      return (PreparedStatement)this.pstmtPool.borrowObject(createKey(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Borrow prepareStatement from pool failed", e);
    }
  }
  
  PreparedStatement prepareStatement(String sql, int[] columnIndexes)
    throws SQLException
  {
    if (this.pstmtPool == null) {
      return this.connection.prepareStatement(sql, columnIndexes);
    }
    try
    {
      return (PreparedStatement)this.pstmtPool.borrowObject(createKey(sql, columnIndexes));
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Borrow prepareStatement from pool failed", e);
    }
  }
  
  PreparedStatement prepareStatement(String sql, String[] columnNames)
    throws SQLException
  {
    if (this.pstmtPool == null) {
      return this.connection.prepareStatement(sql, columnNames);
    }
    try
    {
      return (PreparedStatement)this.pstmtPool.borrowObject(createKey(sql, columnNames));
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Borrow prepareStatement from pool failed", e);
    }
  }
  
  protected PStmtKeyCPDS createKey(String sql, int autoGeneratedKeys)
  {
    return new PStmtKeyCPDS(normalizeSQL(sql), autoGeneratedKeys);
  }
  
  protected PStmtKeyCPDS createKey(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
  {
    return new PStmtKeyCPDS(normalizeSQL(sql), resultSetType, resultSetConcurrency, resultSetHoldability);
  }
  
  protected PStmtKeyCPDS createKey(String sql, int[] columnIndexes)
  {
    return new PStmtKeyCPDS(normalizeSQL(sql), columnIndexes);
  }
  
  protected PStmtKeyCPDS createKey(String sql, String[] columnNames)
  {
    return new PStmtKeyCPDS(normalizeSQL(sql), columnNames);
  }
  
  protected PStmtKeyCPDS createKey(String sql, int resultSetType, int resultSetConcurrency)
  {
    return new PStmtKeyCPDS(normalizeSQL(sql), resultSetType, resultSetConcurrency);
  }
  
  protected PStmtKeyCPDS createKey(String sql)
  {
    return new PStmtKeyCPDS(normalizeSQL(sql));
  }
  
  protected String normalizeSQL(String sql)
  {
    return sql.trim();
  }
  
  public PooledObject<PoolablePreparedStatement<PStmtKeyCPDS>> makeObject(PStmtKeyCPDS key)
    throws Exception
  {
    if (null == key) {
      throw new IllegalArgumentException();
    }
    if ((null == key.getResultSetType()) && (null == key.getResultSetConcurrency()))
    {
      if (null == key.getAutoGeneratedKeys()) {
        return new DefaultPooledObject(new PoolablePreparedStatement(this.connection.prepareStatement(key.getSql()), key, this.pstmtPool, this.delegatingConnection));
      }
      return new DefaultPooledObject(new PoolablePreparedStatement(this.connection.prepareStatement(key.getSql(), key.getAutoGeneratedKeys().intValue()), key, this.pstmtPool, this.delegatingConnection));
    }
    return new DefaultPooledObject(new PoolablePreparedStatement(this.connection.prepareStatement(key.getSql(), key.getResultSetType().intValue(), key.getResultSetConcurrency().intValue()), key, this.pstmtPool, this.delegatingConnection));
  }
  
  public void destroyObject(PStmtKeyCPDS key, PooledObject<PoolablePreparedStatement<PStmtKeyCPDS>> p)
    throws Exception
  {
    ((PoolablePreparedStatement)p.getObject()).getInnermostDelegate().close();
  }
  
  public boolean validateObject(PStmtKeyCPDS key, PooledObject<PoolablePreparedStatement<PStmtKeyCPDS>> p)
  {
    return true;
  }
  
  public void activateObject(PStmtKeyCPDS key, PooledObject<PoolablePreparedStatement<PStmtKeyCPDS>> p)
    throws Exception
  {
    ((PoolablePreparedStatement)p.getObject()).activate();
  }
  
  public void passivateObject(PStmtKeyCPDS key, PooledObject<PoolablePreparedStatement<PStmtKeyCPDS>> p)
    throws Exception
  {
    PoolablePreparedStatement<PStmtKeyCPDS> ppss = (PoolablePreparedStatement)p.getObject();
    ppss.clearParameters();
    ppss.passivate();
  }
  
  public synchronized boolean isAccessToUnderlyingConnectionAllowed()
  {
    return this.accessToUnderlyingConnectionAllowed;
  }
  
  public synchronized void setAccessToUnderlyingConnectionAllowed(boolean allow)
  {
    this.accessToUnderlyingConnectionAllowed = allow;
  }
}
