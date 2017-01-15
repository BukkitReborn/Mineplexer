package org.jooq;

import org.jooq.exception.DataAccessException;

public abstract interface LoaderError
{
  public abstract DataAccessException exception();
  
  public abstract int rowIndex();
  
  public abstract String[] row();
  
  public abstract Query query();
}
