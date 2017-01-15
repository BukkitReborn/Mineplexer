package org.apache.commons.dbcp2.datasources;

import java.sql.SQLException;
import javax.sql.PooledConnection;

abstract interface PooledConnectionManager
{
  public abstract void invalidate(PooledConnection paramPooledConnection)
    throws SQLException;
  
  public abstract void setPassword(String paramString);
  
  public abstract void closePool(String paramString)
    throws SQLException;
}
