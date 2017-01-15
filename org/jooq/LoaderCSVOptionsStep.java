package org.jooq;

public abstract interface LoaderCSVOptionsStep<R extends TableRecord<R>>
  extends LoaderLoadStep<R>
{
  @Support
  public abstract LoaderCSVOptionsStep<R> ignoreRows(int paramInt);
  
  @Support
  public abstract LoaderCSVOptionsStep<R> quote(char paramChar);
  
  @Support
  public abstract LoaderCSVOptionsStep<R> separator(char paramChar);
  
  @Support
  public abstract LoaderCSVOptionsStep<R> nullString(String paramString);
}
