package org.jooq;

import java.util.List;

public abstract interface Loader<R extends TableRecord<R>>
{
  public abstract List<LoaderError> errors();
  
  public abstract int processed();
  
  public abstract int ignored();
  
  public abstract int stored();
}
