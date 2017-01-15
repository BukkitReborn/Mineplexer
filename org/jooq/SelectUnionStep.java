package org.jooq;

public abstract interface SelectUnionStep<R extends Record>
  extends SelectFinalStep<R>
{
  @Support
  public abstract SelectOrderByStep<R> union(Select<? extends R> paramSelect);
  
  @Support
  public abstract SelectOrderByStep<R> unionAll(Select<? extends R> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract SelectOrderByStep<R> except(Select<? extends R> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract SelectOrderByStep<R> intersect(Select<? extends R> paramSelect);
}
