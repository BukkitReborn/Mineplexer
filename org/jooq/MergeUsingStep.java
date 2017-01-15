package org.jooq;

public abstract interface MergeUsingStep<R extends Record>
  extends MergeKeyStepN<R>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnStep<R> using(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnStep<R> usingDual();
}
