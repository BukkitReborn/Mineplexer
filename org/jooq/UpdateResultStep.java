package org.jooq;

import org.jooq.exception.DataAccessException;

public abstract interface UpdateResultStep<R extends Record>
  extends Insert<R>
{
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract Result<R> fetch()
    throws DataAccessException;
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract R fetchOne()
    throws DataAccessException;
}
