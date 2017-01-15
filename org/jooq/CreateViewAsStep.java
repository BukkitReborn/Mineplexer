package org.jooq;

public abstract interface CreateViewAsStep<R extends Record>
{
  @Support
  public abstract CreateViewFinalStep as(Select<? extends R> paramSelect);
}
