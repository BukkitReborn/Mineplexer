package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep13<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
  extends MergeValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep13<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> key(Collection<? extends Field<?>> paramCollection);
}
