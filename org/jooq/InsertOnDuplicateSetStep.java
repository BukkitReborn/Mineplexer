package org.jooq;

import java.util.Map;

public abstract interface InsertOnDuplicateSetStep<R extends Record>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract <T> InsertOnDuplicateSetMoreStep<R> set(Field<T> paramField, T paramT);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract <T> InsertOnDuplicateSetMoreStep<R> set(Field<T> paramField1, Field<T> paramField2);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract <T> InsertOnDuplicateSetMoreStep<R> set(Field<T> paramField, Select<? extends Record1<T>> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract InsertOnDuplicateSetMoreStep<R> set(Map<? extends Field<?>, ?> paramMap);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract InsertOnDuplicateSetMoreStep<R> set(Record paramRecord);
}
