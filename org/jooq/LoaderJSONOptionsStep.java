package org.jooq;

public abstract interface LoaderJSONOptionsStep<R extends TableRecord<R>>
  extends LoaderLoadStep<R>
{
  @Support
  public abstract LoaderJSONOptionsStep<R> ignoreRows(int paramInt);
}
