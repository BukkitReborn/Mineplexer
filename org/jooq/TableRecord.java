package org.jooq;

import org.jooq.exception.DataAccessException;

public abstract interface TableRecord<R extends TableRecord<R>>
  extends Record
{
  public abstract Table<R> getTable();
  
  public abstract R original();
  
  public abstract int insert()
    throws DataAccessException;
  
  public abstract int insert(Field<?>... paramVarArgs)
    throws DataAccessException;
  
  public abstract <O extends UpdatableRecord<O>> O fetchParent(ForeignKey<R, O> paramForeignKey)
    throws DataAccessException;
}
