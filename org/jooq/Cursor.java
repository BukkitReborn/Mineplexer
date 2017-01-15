package org.jooq;

import java.sql.ResultSet;
import java.util.List;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.MappingException;

public abstract interface Cursor<R extends Record>
  extends Iterable<R>
{
  public abstract RecordType<R> recordType();
  
  public abstract Row fieldsRow();
  
  public abstract <T> Field<T> field(Field<T> paramField);
  
  public abstract Field<?> field(String paramString);
  
  public abstract Field<?> field(int paramInt);
  
  public abstract Field<?>[] fields();
  
  public abstract boolean hasNext()
    throws DataAccessException;
  
  public abstract Result<R> fetch()
    throws DataAccessException;
  
  public abstract Result<R> fetch(int paramInt)
    throws DataAccessException;
  
  public abstract R fetchOne()
    throws DataAccessException;
  
  public abstract <H extends RecordHandler<? super R>> H fetchOneInto(H paramH)
    throws DataAccessException;
  
  public abstract <H extends RecordHandler<? super R>> H fetchInto(H paramH)
    throws DataAccessException;
  
  public abstract <E> E fetchOne(RecordMapper<? super R, E> paramRecordMapper)
    throws DataAccessException;
  
  public abstract <E> List<E> fetch(RecordMapper<? super R, E> paramRecordMapper)
    throws DataAccessException;
  
  public abstract <E> E fetchOneInto(Class<? extends E> paramClass)
    throws DataAccessException, MappingException;
  
  public abstract <E> List<E> fetchInto(Class<? extends E> paramClass)
    throws DataAccessException, MappingException;
  
  public abstract <Z extends Record> Z fetchOneInto(Table<Z> paramTable)
    throws DataAccessException, MappingException;
  
  public abstract <Z extends Record> Result<Z> fetchInto(Table<Z> paramTable)
    throws DataAccessException, MappingException;
  
  public abstract void close()
    throws DataAccessException;
  
  public abstract boolean isClosed();
  
  public abstract ResultSet resultSet();
}
