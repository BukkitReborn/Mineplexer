package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;

public abstract interface ForeignKey<R extends Record, O extends Record>
  extends Key<R>
{
  public abstract UniqueKey<O> getKey();
  
  public abstract O fetchParent(R paramR)
    throws DataAccessException;
  
  public abstract Result<O> fetchParents(R... paramVarArgs)
    throws DataAccessException;
  
  public abstract Result<O> fetchParents(Collection<? extends R> paramCollection)
    throws DataAccessException;
  
  public abstract Result<R> fetchChildren(O paramO)
    throws DataAccessException;
  
  public abstract Result<R> fetchChildren(O... paramVarArgs)
    throws DataAccessException;
  
  public abstract Result<R> fetchChildren(Collection<? extends O> paramCollection)
    throws DataAccessException;
}
