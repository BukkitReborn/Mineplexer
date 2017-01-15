package org.jooq;

public abstract interface Sequence<T extends Number>
  extends QueryPart
{
  public abstract String getName();
  
  public abstract Schema getSchema();
  
  public abstract DataType<T> getDataType();
  
  @Support({SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.POSTGRES})
  public abstract Field<T> currval();
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract Field<T> nextval();
}
