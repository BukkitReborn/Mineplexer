package org.jooq;

public abstract interface SelectForUpdateStep<R extends Record>
  extends SelectOptionStep<R>
{
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectForUpdateOfStep<R> forUpdate();
  
  @Support({SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract SelectOptionStep<R> forShare();
}
