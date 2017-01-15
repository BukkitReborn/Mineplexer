package org.jooq;

import java.util.Collection;

public abstract interface InsertReturningStep<R extends Record>
  extends InsertFinalStep<R>
{
  @Support
  public abstract InsertResultStep<R> returning();
  
  @Support
  public abstract InsertResultStep<R> returning(Field<?>... paramVarArgs);
  
  @Support
  public abstract InsertResultStep<R> returning(Collection<? extends Field<?>> paramCollection);
}
