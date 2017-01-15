package org.jooq;

public abstract interface SelectForUpdateWaitStep<R extends Record>
  extends SelectOptionStep<R>
{
  @Support({SQLDialect.POSTGRES})
  public abstract SelectOptionStep<R> noWait();
}
