package org.jooq;

import java.util.Map;

public abstract interface MergeMatchedSetStep<R extends Record>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract <T> MergeMatchedSetMoreStep<R> set(Field<T> paramField, T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract <T> MergeMatchedSetMoreStep<R> set(Field<T> paramField1, Field<T> paramField2);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract <T> MergeMatchedSetMoreStep<R> set(Field<T> paramField, Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeMatchedSetMoreStep<R> set(Map<? extends Field<?>, ?> paramMap);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeMatchedSetMoreStep<R> set(Record paramRecord);
}
