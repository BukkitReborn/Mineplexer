package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStepN<R extends Record>
  extends MergeValuesStepN<R>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStepN<R> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStepN<R> key(Collection<? extends Field<?>> paramCollection);
}
