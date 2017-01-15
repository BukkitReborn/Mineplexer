package org.jooq.impl;

import java.sql.Connection;
import java.sql.Savepoint;
import org.jooq.ConnectionProvider;
import org.jooq.exception.DataAccessException;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.jdbc.JDBCUtils;

public class DefaultConnectionProvider
  implements ConnectionProvider
{
  private static final JooqLogger log = JooqLogger.getLogger(DefaultConnectionProvider.class);
  private Connection connection;
  private boolean finalize;
  
  public DefaultConnectionProvider(Connection connection)
  {
    this(connection, false);
  }
  
  DefaultConnectionProvider(Connection connection, boolean finalize)
  {
    this.connection = connection;
    this.finalize = finalize;
  }
  
  public final Connection acquire()
  {
    return this.connection;
  }
  
  public final void release(Connection released) {}
  
  protected void finalize()
    throws Throwable
  {
    if (this.finalize) {
      JDBCUtils.safeClose(this.connection);
    }
    super.finalize();
  }
  
  public final void setConnection(Connection connection)
  {
    this.connection = connection;
  }
  
  public final void commit()
    throws DataAccessException
  {
    try
    {
      log.debug("commit");
      this.connection.commit();
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot commit transaction", e);
    }
  }
  
  public final void rollback()
    throws DataAccessException
  {
    try
    {
      log.debug("rollback");
      this.connection.rollback();
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot rollback transaction", e);
    }
  }
  
  public final void rollback(Savepoint savepoint)
    throws DataAccessException
  {
    try
    {
      log.debug("rollback to savepoint");
      this.connection.rollback(savepoint);
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot rollback transaction", e);
    }
  }
  
  public final Savepoint setSavepoint()
    throws DataAccessException
  {
    try
    {
      log.debug("set savepoint");
      return this.connection.setSavepoint();
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot set savepoint", e);
    }
  }
  
  public final Savepoint setSavepoint(String name)
    throws DataAccessException
  {
    try
    {
      log.debug("set savepoint", name);
      return this.connection.setSavepoint(name);
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot set savepoint", e);
    }
  }
  
  public final void releaseSavepoint(Savepoint savepoint)
    throws DataAccessException
  {
    try
    {
      log.debug("release savepoint");
      this.connection.releaseSavepoint(savepoint);
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot release savepoint", e);
    }
  }
  
  public final void setAutoCommit(boolean autoCommit)
    throws DataAccessException
  {
    try
    {
      log.debug("setting auto commit", Boolean.valueOf(autoCommit));
      this.connection.setAutoCommit(autoCommit);
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot set autoCommit", e);
    }
  }
  
  public final boolean getAutoCommit()
    throws DataAccessException
  {
    try
    {
      return this.connection.getAutoCommit();
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot get autoCommit", e);
    }
  }
  
  public final void setHoldability(int holdability)
    throws DataAccessException
  {
    try
    {
      log.debug("setting holdability", Integer.valueOf(holdability));
      this.connection.setHoldability(holdability);
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot set holdability", e);
    }
  }
  
  public final int getHoldability()
    throws DataAccessException
  {
    try
    {
      return this.connection.getHoldability();
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot get holdability", e);
    }
  }
  
  public final void setTransactionIsolation(int level)
    throws DataAccessException
  {
    try
    {
      log.debug("setting tx isolation", Integer.valueOf(level));
      this.connection.setTransactionIsolation(level);
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot set transactionIsolation", e);
    }
  }
  
  public final int getTransactionIsolation()
    throws DataAccessException
  {
    try
    {
      return this.connection.getTransactionIsolation();
    }
    catch (Exception e)
    {
      throw new DataAccessException("Cannot get transactionIsolation", e);
    }
  }
}
