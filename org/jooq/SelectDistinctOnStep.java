package org.jooq;

import java.util.Collection;

public abstract interface SelectDistinctOnStep<R extends Record>
  extends SelectIntoStep<R>
{
  @Support({SQLDialect.POSTGRES})
  public abstract SelectIntoStep<R> on(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.POSTGRES})
  public abstract SelectIntoStep<R> on(Collection<? extends Field<?>> paramCollection);
  
  @Support({SQLDialect.POSTGRES})
  public abstract SelectIntoStep<R> distinctOn(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.POSTGRES})
  public abstract SelectIntoStep<R> distinctOn(Collection<? extends Field<?>> paramCollection);
}
