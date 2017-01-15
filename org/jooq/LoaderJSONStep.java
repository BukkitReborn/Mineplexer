package org.jooq;

import java.util.Collection;

public abstract interface LoaderJSONStep<R extends TableRecord<R>>
{
  @Support
  public abstract LoaderJSONOptionsStep<R> fields(Field<?>... paramVarArgs);
  
  @Support
  public abstract LoaderJSONOptionsStep<R> fields(Collection<? extends Field<?>> paramCollection);
}
