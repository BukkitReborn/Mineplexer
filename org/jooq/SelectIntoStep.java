package org.jooq;

public abstract interface SelectIntoStep<R extends Record>
  extends SelectFromStep<R>
{
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract SelectIntoStep<Record> into(Table<?> paramTable);
}
