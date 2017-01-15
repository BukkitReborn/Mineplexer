package org.jooq;

import java.io.IOException;

public abstract interface LoaderLoadStep<R extends TableRecord<R>>
{
  @Support
  public abstract Loader<R> execute()
    throws IOException;
}
