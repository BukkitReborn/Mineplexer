package org.jooq;

import java.util.Collection;

public abstract interface SelectWindowStep<R extends Record>
  extends SelectOrderByStep<R>
{
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract SelectOrderByStep<R> window(WindowDefinition... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract SelectOrderByStep<R> window(Collection<? extends WindowDefinition> paramCollection);
}
