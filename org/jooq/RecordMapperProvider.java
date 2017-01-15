package org.jooq;

public abstract interface RecordMapperProvider
{
  public abstract <R extends Record, E> RecordMapper<R, E> provide(RecordType<R> paramRecordType, Class<? extends E> paramClass);
}
