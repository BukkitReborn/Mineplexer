package org.jooq;

public abstract interface SelectFinalStep<R extends Record>
  extends Select<R>
{
  public abstract SelectQuery<R> getQuery();
}
