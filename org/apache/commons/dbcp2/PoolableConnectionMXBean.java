package org.apache.commons.dbcp2;

import java.sql.SQLException;

public abstract interface PoolableConnectionMXBean
{
  public abstract boolean isClosed()
    throws SQLException;
  
  public abstract String getToString();
  
  public abstract boolean getAutoCommit()
    throws SQLException;
  
  public abstract void setAutoCommit(boolean paramBoolean)
    throws SQLException;
  
  public abstract boolean getCacheState();
  
  public abstract void setCacheState(boolean paramBoolean);
  
  public abstract String getCatalog()
    throws SQLException;
  
  public abstract void setCatalog(String paramString)
    throws SQLException;
  
  public abstract int getHoldability()
    throws SQLException;
  
  public abstract void setHoldability(int paramInt)
    throws SQLException;
  
  public abstract boolean isReadOnly()
    throws SQLException;
  
  public abstract void setReadOnly(boolean paramBoolean)
    throws SQLException;
  
  public abstract String getSchema()
    throws SQLException;
  
  public abstract void setSchema(String paramString)
    throws SQLException;
  
  public abstract int getTransactionIsolation()
    throws SQLException;
  
  public abstract void setTransactionIsolation(int paramInt)
    throws SQLException;
  
  public abstract void clearCachedState();
  
  public abstract void clearWarnings()
    throws SQLException;
  
  public abstract void close()
    throws SQLException;
  
  public abstract void reallyClose()
    throws SQLException;
}
