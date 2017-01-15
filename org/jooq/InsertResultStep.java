package org.jooq;

import org.jooq.exception.DataAccessException;

public abstract interface InsertResultStep<R extends Record>
  extends Insert<R>
{
  @Support
  public abstract Result<R> fetch()
    throws DataAccessException;
  
  @Support
  public abstract R fetchOne()
    throws DataAccessException;
}
