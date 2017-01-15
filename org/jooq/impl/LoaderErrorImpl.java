package org.jooq.impl;

import org.jooq.LoaderError;
import org.jooq.Query;
import org.jooq.exception.DataAccessException;

class LoaderErrorImpl
  implements LoaderError
{
  private final DataAccessException exception;
  private final int rowIndex;
  private final String[] row;
  private final Query query;
  
  LoaderErrorImpl(DataAccessException exception, String[] row, int rowIndex, Query query)
  {
    this.exception = exception;
    this.row = row;
    this.rowIndex = rowIndex;
    this.query = query;
  }
  
  public DataAccessException exception()
  {
    return this.exception;
  }
  
  public int rowIndex()
  {
    return this.rowIndex;
  }
  
  public String[] row()
  {
    return this.row;
  }
  
  public Query query()
  {
    return this.query;
  }
}
