package org.jooq;

import java.util.Map;

public abstract interface UpdateSetStep<R extends Record>
{
  @Support
  public abstract <T> UpdateSetMoreStep<R> set(Field<T> paramField, T paramT);
  
  @Support
  public abstract <T> UpdateSetMoreStep<R> set(Field<T> paramField1, Field<T> paramField2);
  
  @Support
  public abstract <T> UpdateSetMoreStep<R> set(Field<T> paramField, Select<? extends Record1<T>> paramSelect);
  
  @Support
  public abstract UpdateSetMoreStep<R> set(Map<? extends Field<?>, ?> paramMap);
  
  @Support
  public abstract UpdateSetMoreStep<R> set(Record paramRecord);
}
