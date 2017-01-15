package org.apache.commons.dbcp2;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.ClientInfoStatus;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public class DelegatingConnection<C extends Connection>
  extends AbandonedTrace
  implements Connection
{
  private static final Map<String, ClientInfoStatus> EMPTY_FAILED_PROPERTIES = ;
  private volatile C _conn = null;
  private volatile boolean _closed = false;
  private boolean _cacheState = true;
  private Boolean _autoCommitCached = null;
  private Boolean _readOnlyCached = null;
  private Integer defaultQueryTimeout = null;
  
  public DelegatingConnection(C c)
  {
    this._conn = c;
  }
  
  public String toString()
  {
    String s = null;
    
    Connection c = getInnermostDelegateInternal();
    if (c != null) {
      try
      {
        if (c.isClosed())
        {
          s = "connection is closed";
        }
        else
        {
          StringBuffer sb = new StringBuffer();
          sb.append(hashCode());
          DatabaseMetaData meta = c.getMetaData();
          if (meta != null)
          {
            sb.append(", URL=");
            sb.append(meta.getURL());
            sb.append(", UserName=");
            sb.append(meta.getUserName());
            sb.append(", ");
            sb.append(meta.getDriverName());
            s = sb.toString();
          }
        }
      }
      catch (SQLException ex) {}
    }
    if (s == null) {
      s = super.toString();
    }
    return s;
  }
  
  public C getDelegate()
  {
    return getDelegateInternal();
  }
  
  protected final C getDelegateInternal()
  {
    return this._conn;
  }
  
  public boolean innermostDelegateEquals(Connection c)
  {
    Connection innerCon = getInnermostDelegateInternal();
    if (innerCon == null) {
      return c == null;
    }
    return innerCon.equals(c);
  }
  
  public Connection getInnermostDelegate()
  {
    return getInnermostDelegateInternal();
  }
  
  public final Connection getInnermostDelegateInternal()
  {
    Connection c = this._conn;
    while ((c != null) && ((c instanceof DelegatingConnection)))
    {
      c = ((DelegatingConnection)c).getDelegateInternal();
      if (this == c) {
        return null;
      }
    }
    return c;
  }
  
  public void setDelegate(C c)
  {
    this._conn = c;
  }
  
  public void close()
    throws SQLException
  {
    if (!this._closed) {
      closeInternal();
    }
  }
  
  protected boolean isClosedInternal()
  {
    return this._closed;
  }
  
  protected void setClosedInternal(boolean closed)
  {
    this._closed = closed;
  }
  
  protected final void closeInternal()
    throws SQLException
  {
    try
    {
      passivate();
    }
    finally
    {
      try
      {
        this._conn.close();
      }
      finally
      {
        this._closed = true;
      }
    }
  }
  
  protected void handleException(SQLException e)
    throws SQLException
  {
    throw e;
  }
  
  private void initializeStatement(DelegatingStatement ds)
    throws SQLException
  {
    if ((this.defaultQueryTimeout != null) && (this.defaultQueryTimeout.intValue() != ds.getQueryTimeout())) {
      ds.setQueryTimeout(this.defaultQueryTimeout.intValue());
    }
  }
  
  public Statement createStatement()
    throws SQLException
  {
    checkOpen();
    try
    {
      DelegatingStatement ds = new DelegatingStatement(this, this._conn.createStatement());
      
      initializeStatement(ds);
      return ds;
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Statement createStatement(int resultSetType, int resultSetConcurrency)
    throws SQLException
  {
    checkOpen();
    try
    {
      DelegatingStatement ds = new DelegatingStatement(this, this._conn.createStatement(resultSetType, resultSetConcurrency));
      
      initializeStatement(ds);
      return ds;
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public PreparedStatement prepareStatement(String sql)
    throws SQLException
  {
    checkOpen();
    try
    {
      DelegatingPreparedStatement dps = new DelegatingPreparedStatement(this, this._conn.prepareStatement(sql));
      
      initializeStatement(dps);
      return dps;
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
      DelegatingPreparedStatement dps = new DelegatingPreparedStatement(this, this._conn.prepareStatement(sql, resultSetType, resultSetConcurrency));
      
      initializeStatement(dps);
      return dps;
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public CallableStatement prepareCall(String sql)
    throws SQLException
  {
    checkOpen();
    try
    {
      DelegatingCallableStatement dcs = new DelegatingCallableStatement(this, this._conn.prepareCall(sql));
      
      initializeStatement(dcs);
      return dcs;
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
    throws SQLException
  {
    checkOpen();
    try
    {
      DelegatingCallableStatement dcs = new DelegatingCallableStatement(this, this._conn.prepareCall(sql, resultSetType, resultSetConcurrency));
      
      initializeStatement(dcs);
      return dcs;
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void clearWarnings()
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.clearWarnings();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void commit()
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.commit();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public boolean getCacheState()
  {
    return this._cacheState;
  }
  
  public boolean getAutoCommit()
    throws SQLException
  {
    checkOpen();
    if ((this._cacheState) && (this._autoCommitCached != null)) {
      return this._autoCommitCached.booleanValue();
    }
    try
    {
      this._autoCommitCached = Boolean.valueOf(this._conn.getAutoCommit());
      return this._autoCommitCached.booleanValue();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public String getCatalog()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.getCatalog();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public DatabaseMetaData getMetaData()
    throws SQLException
  {
    checkOpen();
    try
    {
      return new DelegatingDatabaseMetaData(this, this._conn.getMetaData());
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public int getTransactionIsolation()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.getTransactionIsolation();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return -1;
  }
  
  public Map<String, Class<?>> getTypeMap()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.getTypeMap();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public SQLWarning getWarnings()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.getWarnings();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public boolean isReadOnly()
    throws SQLException
  {
    checkOpen();
    if ((this._cacheState) && (this._readOnlyCached != null)) {
      return this._readOnlyCached.booleanValue();
    }
    try
    {
      this._readOnlyCached = Boolean.valueOf(this._conn.isReadOnly());
      return this._readOnlyCached.booleanValue();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public String nativeSQL(String sql)
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.nativeSQL(sql);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void rollback()
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.rollback();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public Integer getDefaultQueryTimeout()
  {
    return this.defaultQueryTimeout;
  }
  
  public void setDefaultQueryTimeout(Integer defaultQueryTimeout)
  {
    this.defaultQueryTimeout = defaultQueryTimeout;
  }
  
  public void setCacheState(boolean cacheState)
  {
    this._cacheState = cacheState;
  }
  
  public void clearCachedState()
  {
    this._autoCommitCached = null;
    this._readOnlyCached = null;
    if ((this._conn instanceof DelegatingConnection)) {
      ((DelegatingConnection)this._conn).clearCachedState();
    }
  }
  
  public void setAutoCommit(boolean autoCommit)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.setAutoCommit(autoCommit);
      if (this._cacheState) {
        this._autoCommitCached = Boolean.valueOf(autoCommit);
      }
    }
    catch (SQLException e)
    {
      this._autoCommitCached = null;
      handleException(e);
    }
  }
  
  public void setCatalog(String catalog)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.setCatalog(catalog);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setReadOnly(boolean readOnly)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.setReadOnly(readOnly);
      if (this._cacheState) {
        this._readOnlyCached = Boolean.valueOf(readOnly);
      }
    }
    catch (SQLException e)
    {
      this._readOnlyCached = null;
      handleException(e);
    }
  }
  
  public void setTransactionIsolation(int level)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.setTransactionIsolation(level);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setTypeMap(Map<String, Class<?>> map)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.setTypeMap(map);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public boolean isClosed()
    throws SQLException
  {
    return (this._closed) || (this._conn.isClosed());
  }
  
  protected void checkOpen()
    throws SQLException
  {
    if (this._closed)
    {
      if (null != this._conn)
      {
        String label = "";
        try
        {
          label = this._conn.toString();
        }
        catch (Exception ex) {}
        throw new SQLException("Connection " + label + " is closed.");
      }
      throw new SQLException("Connection is null.");
    }
  }
  
  protected void activate()
  {
    this._closed = false;
    setLastUsed();
    if ((this._conn instanceof DelegatingConnection)) {
      ((DelegatingConnection)this._conn).activate();
    }
  }
  
  protected void passivate()
    throws SQLException
  {
    List<AbandonedTrace> traces = getTrace();
    if ((traces != null) && (traces.size() > 0))
    {
      Iterator<AbandonedTrace> traceIter = traces.iterator();
      while (traceIter.hasNext())
      {
        Object trace = traceIter.next();
        if ((trace instanceof Statement)) {
          ((Statement)trace).close();
        } else if ((trace instanceof ResultSet)) {
          ((ResultSet)trace).close();
        }
      }
      clearTrace();
    }
    setLastUsed(0L);
  }
  
  public int getHoldability()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.getHoldability();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public void setHoldability(int holdability)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.setHoldability(holdability);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public Savepoint setSavepoint()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.setSavepoint();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Savepoint setSavepoint(String name)
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.setSavepoint(name);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void rollback(Savepoint savepoint)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.rollback(savepoint);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void releaseSavepoint(Savepoint savepoint)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.releaseSavepoint(savepoint);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
    throws SQLException
  {
    checkOpen();
    try
    {
      DelegatingStatement ds = new DelegatingStatement(this, this._conn.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability));
      
      initializeStatement(ds);
      return ds;
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
      DelegatingPreparedStatement dps = new DelegatingPreparedStatement(this, this._conn.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
      
      initializeStatement(dps);
      return dps;
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability)
    throws SQLException
  {
    checkOpen();
    try
    {
      DelegatingCallableStatement dcs = new DelegatingCallableStatement(this, this._conn.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
      
      initializeStatement(dcs);
      return dcs;
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
      DelegatingPreparedStatement dps = new DelegatingPreparedStatement(this, this._conn.prepareStatement(sql, autoGeneratedKeys));
      
      initializeStatement(dps);
      return dps;
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
      DelegatingPreparedStatement dps = new DelegatingPreparedStatement(this, this._conn.prepareStatement(sql, columnIndexes));
      
      initializeStatement(dps);
      return dps;
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
      DelegatingPreparedStatement dps = new DelegatingPreparedStatement(this, this._conn.prepareStatement(sql, columnNames));
      
      initializeStatement(dps);
      return dps;
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public boolean isWrapperFor(Class<?> iface)
    throws SQLException
  {
    if (iface.isAssignableFrom(getClass())) {
      return true;
    }
    if (iface.isAssignableFrom(this._conn.getClass())) {
      return true;
    }
    return this._conn.isWrapperFor(iface);
  }
  
  public <T> T unwrap(Class<T> iface)
    throws SQLException
  {
    if (iface.isAssignableFrom(getClass())) {
      return (T)iface.cast(this);
    }
    if (iface.isAssignableFrom(this._conn.getClass())) {
      return (T)iface.cast(this._conn);
    }
    return (T)this._conn.unwrap(iface);
  }
  
  public Array createArrayOf(String typeName, Object[] elements)
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.createArrayOf(typeName, elements);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Blob createBlob()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.createBlob();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Clob createClob()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.createClob();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public NClob createNClob()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.createNClob();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public SQLXML createSQLXML()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.createSQLXML();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public Struct createStruct(String typeName, Object[] attributes)
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.createStruct(typeName, attributes);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public boolean isValid(int timeout)
    throws SQLException
  {
    if (isClosed()) {
      return false;
    }
    try
    {
      return this._conn.isValid(timeout);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public void setClientInfo(String name, String value)
    throws SQLClientInfoException
  {
    try
    {
      checkOpen();
      this._conn.setClientInfo(name, value);
    }
    catch (SQLClientInfoException e)
    {
      throw e;
    }
    catch (SQLException e)
    {
      throw new SQLClientInfoException("Connection is closed.", EMPTY_FAILED_PROPERTIES, e);
    }
  }
  
  public void setClientInfo(Properties properties)
    throws SQLClientInfoException
  {
    try
    {
      checkOpen();
      this._conn.setClientInfo(properties);
    }
    catch (SQLClientInfoException e)
    {
      throw e;
    }
    catch (SQLException e)
    {
      throw new SQLClientInfoException("Connection is closed.", EMPTY_FAILED_PROPERTIES, e);
    }
  }
  
  public Properties getClientInfo()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.getClientInfo();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public String getClientInfo(String name)
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.getClientInfo(name);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void setSchema(String schema)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.setSchema(schema);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public String getSchema()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.getSchema();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return null;
  }
  
  public void abort(Executor executor)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.abort(executor);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setNetworkTimeout(Executor executor, int milliseconds)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._conn.setNetworkTimeout(executor, milliseconds);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public int getNetworkTimeout()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._conn.getNetworkTimeout();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
}
