package org.jooq;

public abstract interface Name
  extends QueryPart
{
  public abstract String[] getName();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowDefinition as(WindowSpecification paramWindowSpecification);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract <R extends Record> CommonTableExpression<R> as(Select<R> paramSelect);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract DerivedColumnList fields(String... paramVarArgs);
}
