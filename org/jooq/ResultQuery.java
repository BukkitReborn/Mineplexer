package org.jooq;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.MappingException;

public abstract interface ResultQuery<R extends Record>
  extends Query, Iterable<R>
{
  public abstract Result<R> getResult();
  
  public abstract Result<R> fetch()
    throws DataAccessException;
  
  public abstract ResultSet fetchResultSet()
    throws DataAccessException;
  
  public abstract Iterator<R> iterator()
    throws DataAccessException;
  
  public abstract Cursor<R> fetchLazy()
    throws DataAccessException;
  
  @Deprecated
  public abstract Cursor<R> fetchLazy(int paramInt)
    throws DataAccessException;
  
  public abstract List<Result<Record>> fetchMany()
    throws DataAccessException;
  
  public abstract <T> List<T> fetch(Field<T> paramField)
    throws DataAccessException;
  
  public abstract <T> List<T> fetch(Field<?> paramField, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <T, U> List<U> fetch(Field<T> paramField, Converter<? super T, U> paramConverter)
    throws DataAccessException;
  
  public abstract List<?> fetch(int paramInt)
    throws DataAccessException;
  
  public abstract <T> List<T> fetch(int paramInt, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <U> List<U> fetch(int paramInt, Converter<?, U> paramConverter)
    throws DataAccessException;
  
  public abstract List<?> fetch(String paramString)
    throws DataAccessException;
  
  public abstract <T> List<T> fetch(String paramString, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <U> List<U> fetch(String paramString, Converter<?, U> paramConverter)
    throws DataAccessException;
  
  public abstract <T> T fetchOne(Field<T> paramField)
    throws DataAccessException, InvalidResultException;
  
  public abstract <T> T fetchOne(Field<?> paramField, Class<? extends T> paramClass)
    throws DataAccessException, InvalidResultException;
  
  public abstract <T, U> U fetchOne(Field<T> paramField, Converter<? super T, U> paramConverter)
    throws DataAccessException, InvalidResultException;
  
  public abstract Object fetchOne(int paramInt)
    throws DataAccessException, InvalidResultException;
  
  public abstract <T> T fetchOne(int paramInt, Class<? extends T> paramClass)
    throws DataAccessException, InvalidResultException;
  
  public abstract <U> U fetchOne(int paramInt, Converter<?, U> paramConverter)
    throws DataAccessException, InvalidResultException;
  
  public abstract Object fetchOne(String paramString)
    throws DataAccessException, InvalidResultException;
  
  public abstract <T> T fetchOne(String paramString, Class<? extends T> paramClass)
    throws DataAccessException, InvalidResultException;
  
  public abstract <U> U fetchOne(String paramString, Converter<?, U> paramConverter)
    throws DataAccessException, InvalidResultException;
  
  public abstract R fetchOne()
    throws DataAccessException, InvalidResultException;
  
  public abstract Map<String, Object> fetchOneMap()
    throws DataAccessException, InvalidResultException;
  
  public abstract Object[] fetchOneArray()
    throws DataAccessException, InvalidResultException;
  
  public abstract <E> E fetchOneInto(Class<? extends E> paramClass)
    throws DataAccessException, MappingException, InvalidResultException;
  
  public abstract <Z extends Record> Z fetchOneInto(Table<Z> paramTable)
    throws DataAccessException, InvalidResultException;
  
  public abstract <T> T fetchAny(Field<T> paramField)
    throws DataAccessException;
  
  public abstract <T> T fetchAny(Field<?> paramField, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <T, U> U fetchAny(Field<T> paramField, Converter<? super T, U> paramConverter)
    throws DataAccessException;
  
  public abstract Object fetchAny(int paramInt)
    throws DataAccessException;
  
  public abstract <T> T fetchAny(int paramInt, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <U> U fetchAny(int paramInt, Converter<?, U> paramConverter)
    throws DataAccessException;
  
  public abstract Object fetchAny(String paramString)
    throws DataAccessException;
  
  public abstract <T> T fetchAny(String paramString, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <U> U fetchAny(String paramString, Converter<?, U> paramConverter)
    throws DataAccessException;
  
  public abstract R fetchAny()
    throws DataAccessException;
  
  public abstract Map<String, Object> fetchAnyMap()
    throws DataAccessException;
  
  public abstract Object[] fetchAnyArray()
    throws DataAccessException;
  
  public abstract <E> E fetchAnyInto(Class<? extends E> paramClass)
    throws DataAccessException, MappingException;
  
  public abstract <Z extends Record> Z fetchAnyInto(Table<Z> paramTable)
    throws DataAccessException;
  
  public abstract List<Map<String, Object>> fetchMaps()
    throws DataAccessException;
  
  public abstract <K> Map<K, R> fetchMap(Field<K> paramField)
    throws DataAccessException;
  
  public abstract <K, V> Map<K, V> fetchMap(Field<K> paramField, Field<V> paramField1)
    throws DataAccessException;
  
  public abstract Map<Record, R> fetchMap(Field<?>[] paramArrayOfField)
    throws DataAccessException;
  
  public abstract <E> Map<List<?>, E> fetchMap(Field<?>[] paramArrayOfField, Class<? extends E> paramClass)
    throws DataAccessException, MappingException;
  
  public abstract <E> Map<List<?>, E> fetchMap(Field<?>[] paramArrayOfField, RecordMapper<? super R, E> paramRecordMapper)
    throws DataAccessException, MappingException;
  
  public abstract <S extends Record> Map<S, R> fetchMap(Table<S> paramTable)
    throws DataAccessException;
  
  public abstract <E, S extends Record> Map<S, E> fetchMap(Table<S> paramTable, Class<? extends E> paramClass)
    throws DataAccessException, MappingException;
  
  public abstract <E, S extends Record> Map<S, E> fetchMap(Table<S> paramTable, RecordMapper<? super R, E> paramRecordMapper)
    throws DataAccessException, MappingException;
  
  public abstract <K, E> Map<K, E> fetchMap(Field<K> paramField, Class<? extends E> paramClass)
    throws DataAccessException;
  
  public abstract <K, E> Map<K, E> fetchMap(Field<K> paramField, RecordMapper<? super R, E> paramRecordMapper)
    throws DataAccessException;
  
  public abstract <K> Map<K, Result<R>> fetchGroups(Field<K> paramField)
    throws DataAccessException;
  
  public abstract <K, V> Map<K, List<V>> fetchGroups(Field<K> paramField, Field<V> paramField1)
    throws DataAccessException;
  
  public abstract Map<Record, Result<R>> fetchGroups(Field<?>[] paramArrayOfField)
    throws DataAccessException;
  
  public abstract <E> Map<Record, List<E>> fetchGroups(Field<?>[] paramArrayOfField, Class<? extends E> paramClass)
    throws MappingException;
  
  public abstract <E> Map<Record, List<E>> fetchGroups(Field<?>[] paramArrayOfField, RecordMapper<? super R, E> paramRecordMapper)
    throws MappingException;
  
  public abstract <S extends Record> Map<S, Result<R>> fetchGroups(Table<S> paramTable)
    throws DataAccessException;
  
  public abstract <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> paramTable, Class<? extends E> paramClass)
    throws DataAccessException, MappingException;
  
  public abstract <E, S extends Record> Map<S, List<E>> fetchGroups(Table<S> paramTable, RecordMapper<? super R, E> paramRecordMapper)
    throws DataAccessException, MappingException;
  
  public abstract <K, E> Map<K, List<E>> fetchGroups(Field<K> paramField, Class<? extends E> paramClass)
    throws DataAccessException, MappingException;
  
  public abstract <K, E> Map<K, List<E>> fetchGroups(Field<K> paramField, RecordMapper<? super R, E> paramRecordMapper)
    throws DataAccessException, MappingException;
  
  public abstract Object[][] fetchArrays()
    throws DataAccessException;
  
  public abstract Object[] fetchArray(int paramInt)
    throws DataAccessException;
  
  public abstract <T> T[] fetchArray(int paramInt, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <U> U[] fetchArray(int paramInt, Converter<?, U> paramConverter)
    throws DataAccessException;
  
  public abstract Object[] fetchArray(String paramString)
    throws DataAccessException;
  
  public abstract <T> T[] fetchArray(String paramString, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <U> U[] fetchArray(String paramString, Converter<?, U> paramConverter)
    throws DataAccessException;
  
  public abstract <T> T[] fetchArray(Field<T> paramField)
    throws DataAccessException;
  
  public abstract <T> T[] fetchArray(Field<?> paramField, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <T, U> U[] fetchArray(Field<T> paramField, Converter<? super T, U> paramConverter)
    throws DataAccessException;
  
  public abstract Set<?> fetchSet(int paramInt)
    throws DataAccessException;
  
  public abstract <T> Set<T> fetchSet(int paramInt, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <U> Set<U> fetchSet(int paramInt, Converter<?, U> paramConverter)
    throws DataAccessException;
  
  public abstract Set<?> fetchSet(String paramString)
    throws DataAccessException;
  
  public abstract <T> Set<T> fetchSet(String paramString, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <U> Set<U> fetchSet(String paramString, Converter<?, U> paramConverter)
    throws DataAccessException;
  
  public abstract <T> Set<T> fetchSet(Field<T> paramField)
    throws DataAccessException;
  
  public abstract <T> Set<T> fetchSet(Field<?> paramField, Class<? extends T> paramClass)
    throws DataAccessException;
  
  public abstract <T, U> Set<U> fetchSet(Field<T> paramField, Converter<? super T, U> paramConverter)
    throws DataAccessException;
  
  public abstract <E> List<E> fetchInto(Class<? extends E> paramClass)
    throws DataAccessException, MappingException;
  
  public abstract <Z extends Record> Result<Z> fetchInto(Table<Z> paramTable)
    throws DataAccessException;
  
  public abstract <H extends RecordHandler<? super R>> H fetchInto(H paramH)
    throws DataAccessException;
  
  public abstract <E> List<E> fetch(RecordMapper<? super R, E> paramRecordMapper)
    throws DataAccessException;
  
  @Deprecated
  public abstract FutureResult<R> fetchLater()
    throws DataAccessException;
  
  @Deprecated
  public abstract FutureResult<R> fetchLater(ExecutorService paramExecutorService)
    throws DataAccessException;
  
  public abstract Class<? extends R> getRecordType();
  
  public abstract ResultQuery<R> bind(String paramString, Object paramObject)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract ResultQuery<R> bind(int paramInt, Object paramObject)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract ResultQuery<R> queryTimeout(int paramInt);
  
  public abstract ResultQuery<R> keepStatement(boolean paramBoolean);
  
  public abstract ResultQuery<R> maxRows(int paramInt);
  
  public abstract ResultQuery<R> fetchSize(int paramInt);
  
  public abstract ResultQuery<R> resultSetConcurrency(int paramInt);
  
  public abstract ResultQuery<R> resultSetType(int paramInt);
  
  public abstract ResultQuery<R> resultSetHoldability(int paramInt);
  
  public abstract ResultQuery<R> intern(Field<?>... paramVarArgs);
  
  public abstract ResultQuery<R> intern(int... paramVarArgs);
  
  public abstract ResultQuery<R> intern(String... paramVarArgs);
}
