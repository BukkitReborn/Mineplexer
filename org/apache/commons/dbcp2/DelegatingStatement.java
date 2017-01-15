package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.List;

public class DelegatingStatement
  extends AbandonedTrace
  implements Statement
{
  private Statement _stmt = null;
  private DelegatingConnection<?> _conn = null;
  
  public DelegatingStatement(DelegatingConnection<?> c, Statement s)
  {
    super(c);
    this._stmt = s;
    this._conn = c;
  }
  
  public Statement getDelegate()
  {
    return this._stmt;
  }
  
  public Statement getInnermostDelegate()
  {
    Statement s = this._stmt;
    while ((s != null) && ((s instanceof DelegatingStatement)))
    {
      s = ((DelegatingStatement)s).getDelegate();
      if (this == s) {
        return null;
      }
    }
    return s;
  }
  
  public void setDelegate(Statement s)
  {
    this._stmt = s;
  }
  
  private boolean _closed = false;
  
  protected boolean isClosedInternal()
  {
    return this._closed;
  }
  
  protected void setClosedInternal(boolean closed)
  {
    this._closed = closed;
  }
  
  protected void checkOpen()
    throws SQLException
  {
    if (isClosed()) {
      throw new SQLException(getClass().getName() + " with address: \"" + toString() + "\" is closed.");
    }
  }
  
  public void close()
    throws SQLException
  {
    if (isClosed()) {
      return;
    }
    try
    {
      try
      {
        if (this._conn != null)
        {
          this._conn.removeTrace(this);
          this._conn = null;
        }
        List<AbandonedTrace> resultSets = getTrace();
        if (resultSets != null)
        {
          ResultSet[] set = (ResultSet[])resultSets.toArray(new ResultSet[resultSets.size()]);
          for (ResultSet element : set) {
            element.close();
          }
          clearTrace();
        }
        if (this._stmt != null) {
          this._stmt.close();
        }
      }
      catch (SQLException e)
      {
        handleException(e);
      }
    }
    finally
    {
      this._closed = true;
      this._stmt = null;
    }
  }
  
  protected void handleException(SQLException e)
    throws SQLException
  {
    if (this._conn != null) {
      this._conn.handleException(e);
    } else {
      throw e;
    }
  }
  
  protected void activate()
    throws SQLException
  {
    if ((this._stmt instanceof DelegatingStatement)) {
      ((DelegatingStatement)this._stmt).activate();
    }
  }
  
  protected void passivate()
    throws SQLException
  {
    if ((this._stmt instanceof DelegatingStatement)) {
      ((DelegatingStatement)this._stmt).passivate();
    }
  }
  
  public Connection getConnection()
    throws SQLException
  {
    checkOpen();
    return getConnectionInternal();
  }
  
  protected DelegatingConnection<?> getConnectionInternal()
  {
    return this._conn;
  }
  
  public ResultSet executeQuery(String sql)
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return DelegatingResultSet.wrapResultSet(this, this._stmt.executeQuery(sql));
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public ResultSet getResultSet()
    throws SQLException
  {
    checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this, this._stmt.getResultSet());
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public int executeUpdate(String sql)
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return this._stmt.executeUpdate(sql);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getMaxFieldSize()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getMaxFieldSize();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public void setMaxFieldSize(int max)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.setMaxFieldSize(max);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public int getMaxRows()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getMaxRows();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public void setMaxRows(int max)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.setMaxRows(max);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setEscapeProcessing(boolean enable)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.setEscapeProcessing(enable);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public int getQueryTimeout()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getQueryTimeout();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public void setQueryTimeout(int seconds)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.setQueryTimeout(seconds);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void cancel()
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.cancel();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public SQLWarning getWarnings()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getWarnings();
    }
    catch (SQLException e)
    {
      handleException(e);throw new AssertionError();
    }
  }
  
  public void clearWarnings()
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.clearWarnings();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void setCursorName(String name)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.setCursorName(name);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public boolean execute(String sql)
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return this._stmt.execute(sql);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public int getUpdateCount()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getUpdateCount();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public boolean getMoreResults()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getMoreResults();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public void setFetchDirection(int direction)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.setFetchDirection(direction);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public int getFetchDirection()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getFetchDirection();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public void setFetchSize(int rows)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.setFetchSize(rows);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public int getFetchSize()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getFetchSize();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getResultSetConcurrency()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getResultSetConcurrency();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int getResultSetType()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getResultSetType();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public void addBatch(String sql)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.addBatch(sql);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public void clearBatch()
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.clearBatch();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public int[] executeBatch()
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return this._stmt.executeBatch();
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public String toString()
  {
    return this._stmt == null ? "NULL" : this._stmt.toString();
  }
  
  public boolean getMoreResults(int current)
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getMoreResults(current);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public ResultSet getGeneratedKeys()
    throws SQLException
  {
    checkOpen();
    try
    {
      return DelegatingResultSet.wrapResultSet(this, this._stmt.getGeneratedKeys());
    }
    catch (SQLException e)
    {
      handleException(e);
      throw new AssertionError();
    }
  }
  
  public int executeUpdate(String sql, int autoGeneratedKeys)
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return this._stmt.executeUpdate(sql, autoGeneratedKeys);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int executeUpdate(String sql, int[] columnIndexes)
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return this._stmt.executeUpdate(sql, columnIndexes);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public int executeUpdate(String sql, String[] columnNames)
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return this._stmt.executeUpdate(sql, columnNames);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public boolean execute(String sql, int autoGeneratedKeys)
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return this._stmt.execute(sql, autoGeneratedKeys);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean execute(String sql, int[] columnIndexes)
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return this._stmt.execute(sql, columnIndexes);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public boolean execute(String sql, String[] columnNames)
    throws SQLException
  {
    checkOpen();
    if (this._conn != null) {
      this._conn.setLastUsed();
    }
    try
    {
      return this._stmt.execute(sql, columnNames);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public int getResultSetHoldability()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.getResultSetHoldability();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return 0;
  }
  
  public boolean isClosed()
    throws SQLException
  {
    return this._closed;
  }
  
  public boolean isWrapperFor(Class<?> iface)
    throws SQLException
  {
    if (iface.isAssignableFrom(getClass())) {
      return true;
    }
    if (iface.isAssignableFrom(this._stmt.getClass())) {
      return true;
    }
    return this._stmt.isWrapperFor(iface);
  }
  
  public <T> T unwrap(Class<T> iface)
    throws SQLException
  {
    if (iface.isAssignableFrom(getClass())) {
      return (T)iface.cast(this);
    }
    if (iface.isAssignableFrom(this._stmt.getClass())) {
      return (T)iface.cast(this._stmt);
    }
    return (T)this._stmt.unwrap(iface);
  }
  
  public void setPoolable(boolean poolable)
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.setPoolable(poolable);
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public boolean isPoolable()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.isPoolable();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  public void closeOnCompletion()
    throws SQLException
  {
    checkOpen();
    try
    {
      this._stmt.closeOnCompletion();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
  }
  
  public boolean isCloseOnCompletion()
    throws SQLException
  {
    checkOpen();
    try
    {
      return this._stmt.isCloseOnCompletion();
    }
    catch (SQLException e)
    {
      handleException(e);
    }
    return false;
  }
  
  protected void finalize()
    throws Throwable
  {
    close();
    super.finalize();
  }
}
