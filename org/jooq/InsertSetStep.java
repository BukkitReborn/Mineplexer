package org.jooq;

import java.util.Collection;
import java.util.Map;

public abstract interface InsertSetStep<R extends Record>
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
  public abstract InsertValuesStepN<R> values(Object... paramVarArgs);
  
  @Support
  public abstract InsertValuesStepN<R> values(Field<?>... paramVarArgs);
  
  @Support
  public abstract InsertValuesStepN<R> values(Collection<?> paramCollection);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract InsertReturningStep<R> defaultValues();
  
  @Support
  public abstract Insert<R> select(Select<?> paramSelect);
}
