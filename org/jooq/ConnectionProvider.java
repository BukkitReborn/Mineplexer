package org.jooq;

import java.sql.Connection;
import org.jooq.exception.DataAccessException;

public abstract interface ConnectionProvider
{
  public abstract Connection acquire()
    throws DataAccessException;
  
  public abstract void release(Connection paramConnection)
    throws DataAccessException;
}
