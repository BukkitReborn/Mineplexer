package org.jooq;

public abstract interface CreateTableAsStep<R extends Record>
{
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract CreateTableFinalStep as(Select<? extends R> paramSelect);
  
  @Support
  public abstract <T> CreateTableColumnStep column(Field<T> paramField, DataType<T> paramDataType);
  
  @Support
  public abstract CreateTableColumnStep column(String paramString, DataType<?> paramDataType);
}
