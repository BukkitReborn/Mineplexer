package org.jooq;

public abstract interface WindowBeforeOverStep<T>
  extends WindowOverStep<T>, Field<T>
{
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<T> over();
}
