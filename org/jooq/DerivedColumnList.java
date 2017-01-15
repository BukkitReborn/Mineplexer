package org.jooq;

public abstract interface DerivedColumnList
  extends QueryPart
{
  public abstract <R extends Record> CommonTableExpression<R> as(Select<R> paramSelect);
}
