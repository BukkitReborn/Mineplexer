package org.apache.commons.dbcp2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.pool2.KeyedObjectPool;

public class PoolablePreparedStatement<K>
  extends DelegatingPreparedStatement
{
  private final KeyedObjectPool<K, PoolablePreparedStatement<K>> _pool;
  private final K _key;
  private volatile boolean batchAdded = false;
  
  public PoolablePreparedStatement(PreparedStatement stmt, K key, KeyedObjectPool<K, PoolablePreparedStatement<K>> pool, DelegatingConnection<?> conn)
  {
    super(conn, stmt);
    this._pool = pool;
    this._key = key;
    if (getConnectionInternal() != null) {
      getConnectionInternal().removeTrace(this);
    }
  }
  
  public void addBatch()
    throws SQLException
  {
    super.addBatch();
    this.batchAdded = true;
  }
  
  public void clearBatch()
    throws SQLException
  {
    this.batchAdded = false;
    super.clearBatch();
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
        throw new SQLException("Cannot close preparedstatement (return to pool failed)", e);
      }
    }
  }
  
  public void activate()
    throws SQLException
  {
    setClosedInternal(false);
    if (getConnectionInternal() != null) {
      getConnectionInternal().addTrace(this);
    }
    super.activate();
  }
  
  public void passivate()
    throws SQLException
  {
    if (this.batchAdded) {
      clearBatch();
    }
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
