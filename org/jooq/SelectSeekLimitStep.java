package org.jooq;

public abstract interface SelectSeekLimitStep<R extends Record>
  extends SelectForUpdateStep<R>
{
  @Support
  public abstract SelectForUpdateStep<R> limit(int paramInt);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract SelectForUpdateStep<R> limit(Param<Integer> paramParam);
}
