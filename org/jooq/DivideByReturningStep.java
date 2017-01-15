package org.jooq;

import java.util.Collection;

public abstract interface DivideByReturningStep
{
  @Support
  public abstract Table<Record> returning(Field<?>... paramVarArgs);
  
  @Support
  public abstract Table<Record> returning(Collection<? extends Field<?>> paramCollection);
}
