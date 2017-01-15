package org.jooq;

public abstract interface CreateIndexStep
{
  @Support
  public abstract CreateIndexFinalStep on(Table<?> paramTable, Field<?>... paramVarArgs);
  
  @Support
  public abstract CreateIndexFinalStep on(String paramString, String... paramVarArgs);
}
