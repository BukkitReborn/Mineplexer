package org.jooq;

public abstract interface LoaderOptionsStep<R extends TableRecord<R>>
  extends LoaderSourceStep<R>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract LoaderOptionsStep<R> onDuplicateKeyUpdate();
  
  @Support
  public abstract LoaderOptionsStep<R> onDuplicateKeyIgnore();
  
  @Support
  public abstract LoaderOptionsStep<R> onDuplicateKeyError();
  
  @Support
  public abstract LoaderOptionsStep<R> onErrorIgnore();
  
  @Support
  public abstract LoaderOptionsStep<R> onErrorAbort();
  
  @Support
  public abstract LoaderOptionsStep<R> commitEach();
  
  @Support
  public abstract LoaderOptionsStep<R> commitAfter(int paramInt);
  
  @Support
  public abstract LoaderOptionsStep<R> commitAll();
  
  @Support
  public abstract LoaderOptionsStep<R> commitNone();
}
