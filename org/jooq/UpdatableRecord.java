package org.jooq;

import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataChangedException;
import org.jooq.exception.InvalidResultException;

public abstract interface UpdatableRecord<R extends UpdatableRecord<R>>
  extends TableRecord<R>
{
  public abstract Record key();
  
  public abstract int store()
    throws DataAccessException, DataChangedException;
  
  public abstract int store(Field<?>... paramVarArgs)
    throws DataAccessException, DataChangedException;
  
  public abstract int insert()
    throws DataAccessException;
  
  public abstract int insert(Field<?>... paramVarArgs)
    throws DataAccessException;
  
  public abstract int update()
    throws DataAccessException, DataChangedException;
  
  public abstract int update(Field<?>... paramVarArgs)
    throws DataAccessException, DataChangedException;
  
  public abstract int delete()
    throws DataAccessException, DataChangedException;
  
  public abstract void refresh()
    throws DataAccessException;
  
  public abstract void refresh(Field<?>... paramVarArgs)
    throws DataAccessException;
  
  public abstract R copy();
  
  public abstract <O extends TableRecord<O>> O fetchChild(ForeignKey<O, R> paramForeignKey)
    throws InvalidResultException, DataAccessException;
  
  public abstract <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> paramForeignKey)
    throws DataAccessException;
}
