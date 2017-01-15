package org.jooq;

public abstract interface GroupConcatSeparatorStep
  extends AggregateFunction<String>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract AggregateFunction<String> separator(String paramString);
}
