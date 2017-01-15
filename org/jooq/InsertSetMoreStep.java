package org.jooq;

import java.util.Map;

public abstract interface InsertSetMoreStep<R extends Record>
  extends InsertOnDuplicateStep<R>
{
  @Support
  public abstract <T> InsertSetMoreStep<R> set(Field<T> paramField, T paramT);
  
  @Support
  public abstract <T> InsertSetMoreStep<R> set(Field<T> paramField1, Field<T> paramField2);
  
  @Support
  public abstract <T> InsertSetMoreStep<R> set(Field<T> paramField, Select<? extends Record1<T>> paramSelect);
  
  @Support
  public abstract InsertSetMoreStep<R> set(Map<? extends Field<?>, ?> paramMap);
  
  @Support
  public abstract InsertSetMoreStep<R> set(Record paramRecord);
  
  @Support
  public abstract InsertSetStep<R> newRecord();
}
