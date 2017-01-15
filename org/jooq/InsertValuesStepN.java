package org.jooq;

import java.util.Collection;

public abstract interface InsertValuesStepN<R extends Record>
  extends InsertOnDuplicateStep<R>
{
  @Support
  public abstract InsertValuesStepN<R> values(Object... paramVarArgs);
  
  @Support
  public abstract InsertValuesStepN<R> values(Field<?>... paramVarArgs);
  
  @Support
  public abstract InsertValuesStepN<R> values(Collection<?> paramCollection);
  
  @Support
  public abstract Insert<R> select(Select<?> paramSelect);
}
