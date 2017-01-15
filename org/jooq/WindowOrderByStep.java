package org.jooq;

import java.util.Collection;

public abstract interface WindowOrderByStep<T>
  extends WindowFinalStep<T>
{
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowRowsStep<T> orderBy(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowRowsStep<T> orderBy(SortField<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowRowsStep<T> orderBy(Collection<? extends SortField<?>> paramCollection);
}
