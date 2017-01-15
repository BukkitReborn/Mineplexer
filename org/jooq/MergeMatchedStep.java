package org.jooq;

public abstract interface MergeMatchedStep<R extends Record>
  extends MergeNotMatchedStep<R>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeMatchedSetStep<R> whenMatchedThenUpdate();
}
