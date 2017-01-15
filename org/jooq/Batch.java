package org.jooq;

import java.io.Serializable;
import org.jooq.exception.DataAccessException;

public abstract interface Batch
  extends Serializable
{
  public abstract int[] execute()
    throws DataAccessException;
  
  public abstract int size();
}
