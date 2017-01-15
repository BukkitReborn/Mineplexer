package org.jooq;

import java.util.Collection;

public abstract interface LoaderCSVStep<R extends TableRecord<R>>
{
  @Support
  public abstract LoaderCSVOptionsStep<R> fields(Field<?>... paramVarArgs);
  
  @Support
  public abstract LoaderCSVOptionsStep<R> fields(Collection<? extends Field<?>> paramCollection);
}
