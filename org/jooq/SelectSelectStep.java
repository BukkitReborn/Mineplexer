package org.jooq;

import java.util.Collection;

public abstract interface SelectSelectStep<R extends Record>
  extends SelectDistinctOnStep<R>
{
  @Support
  public abstract SelectSelectStep<Record> select(Field<?>... paramVarArgs);
  
  @Support
  public abstract SelectSelectStep<Record> select(Collection<? extends Field<?>> paramCollection);
}
