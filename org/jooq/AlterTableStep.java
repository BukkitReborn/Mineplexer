package org.jooq;

public abstract interface AlterTableStep
{
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract <T> AlterTableAlterStep<T> alter(Field<T> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract AlterTableAlterStep<Object> alter(String paramString);
  
  @Support
  public abstract <T> AlterTableFinalStep add(Field<T> paramField, DataType<T> paramDataType);
  
  @Support
  public abstract AlterTableFinalStep add(String paramString, DataType<?> paramDataType);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract AlterTableDropStep drop(Field<?> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract AlterTableDropStep drop(String paramString);
}
