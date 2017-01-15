package org.jooq;

public abstract interface TruncateIdentityStep<R extends Record>
  extends TruncateCascadeStep<R>
{
  @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract TruncateCascadeStep<R> restartIdentity();
  
  @Support({SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract TruncateCascadeStep<R> continueIdentity();
}
