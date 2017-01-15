package org.jooq;

public abstract interface SelectOptionStep<R extends Record>
  extends SelectUnionStep<R>
{
  @Support
  public abstract SelectUnionStep<R> option(String paramString);
}
