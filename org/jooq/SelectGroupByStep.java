package org.jooq;

import java.util.Collection;

public abstract interface SelectGroupByStep<R extends Record>
  extends SelectHavingStep<R>
{
  @Support
  public abstract SelectHavingStep<R> groupBy(GroupField... paramVarArgs);
  
  @Support
  public abstract SelectHavingStep<R> groupBy(Collection<? extends GroupField> paramCollection);
}
