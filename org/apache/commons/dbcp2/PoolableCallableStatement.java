package org.apache.commons.dbcp2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.pool2.KeyedObjectPool;

public class PoolableCallableStatement
  extends DelegatingCallableStatement
{
  private final KeyedObjectPool<PStmtKey, DelegatingPreparedStatement> _pool;
  private final PStmtKey _key;
  
  public PoolableCallableStatement(CallableStatement stmt, PStmtKey key, KeyedObjectPool<PStmtKey, DelegatingPreparedStatement> pool, DelegatingConnection<Connection> conn)
  {
    super(conn, stmt);
    this._pool = pool;
    this._key = key;
    if (getConnectionInternal() != null) {
      getConnectionInternal().removeTrace(this);
    }
  }
  
  public void close()
    throws SQLException
  {
    if (!isClosed()) {
      try
      {
        this._pool.returnObject(this._key, this);
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
        throw new SQLException("Cannot close CallableStatement (return to pool failed)", e);
      }
    }
  }
  
  protected void activate()
    throws SQLException
  {
    setClosedInternal(false);
    if (getConnectionInternal() != null) {
      getConnectionInternal().addTrace(this);
    }
    super.activate();
  }
  
  protected void passivate()
    throws SQLException
  {
    setClosedInternal(true);
    if (getConnectionInternal() != null) {
      getConnectionInternal().removeTrace(this);
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
    super.passivate();
  }
}
