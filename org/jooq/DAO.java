package org.jooq;

import java.util.Collection;
import java.util.List;
import org.jooq.conf.Settings;
import org.jooq.exception.DataAccessException;

public abstract interface DAO<R extends TableRecord<R>, P, T>
{
  public abstract Configuration configuration();
  
  public abstract Settings settings();
  
  public abstract SQLDialect dialect();
  
  public abstract SQLDialect family();
  
  public abstract RecordMapper<R, P> mapper();
  
  public abstract void insert(P paramP)
    throws DataAccessException;
  
  public abstract void insert(P... paramVarArgs)
    throws DataAccessException;
  
  public abstract void insert(Collection<P> paramCollection)
    throws DataAccessException;
  
  public abstract void update(P paramP)
    throws DataAccessException;
  
  public abstract void update(P... paramVarArgs)
    throws DataAccessException;
  
  public abstract void update(Collection<P> paramCollection)
    throws DataAccessException;
  
  public abstract void delete(P... paramVarArgs)
    throws DataAccessException;
  
  public abstract void delete(Collection<P> paramCollection)
    throws DataAccessException;
  
  public abstract void deleteById(T... paramVarArgs)
    throws DataAccessException;
  
  public abstract void deleteById(Collection<T> paramCollection)
    throws DataAccessException;
  
  public abstract boolean exists(P paramP)
    throws DataAccessException;
  
  public abstract boolean existsById(T paramT)
    throws DataAccessException;
  
  public abstract long count()
    throws DataAccessException;
  
  public abstract List<P> findAll()
    throws DataAccessException;
  
  public abstract P findById(T paramT)
    throws DataAccessException;
  
  public abstract <Z> List<P> fetch(Field<Z> paramField, Z... paramVarArgs)
    throws DataAccessException;
  
  public abstract <Z> P fetchOne(Field<Z> paramField, Z paramZ)
    throws DataAccessException;
  
  public abstract Table<R> getTable();
  
  public abstract Class<P> getType();
}
