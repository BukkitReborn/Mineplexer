package org.jooq;

import java.util.List;
import org.jooq.exception.DataAccessException;

public abstract interface Routine<T>
  extends QueryPart
{
  public abstract Schema getSchema();
  
  public abstract String getName();
  
  public abstract Package getPackage();
  
  public abstract List<Parameter<?>> getOutParameters();
  
  public abstract List<Parameter<?>> getInParameters();
  
  public abstract T getReturnValue();
  
  public abstract List<Result<Record>> getResults();
  
  public abstract List<Parameter<?>> getParameters();
  
  public abstract int execute(Configuration paramConfiguration)
    throws DataAccessException;
  
  public abstract int execute()
    throws DataAccessException;
}
