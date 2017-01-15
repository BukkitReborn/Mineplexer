package org.jooq;

public abstract interface TruncateCascadeStep<R extends Record>
  extends TruncateFinalStep<R>
{
  @Support({SQLDialect.POSTGRES})
  public abstract TruncateFinalStep<R> cascade();
  
  @Support({SQLDialect.POSTGRES})
  public abstract TruncateFinalStep<R> restrict();
}
