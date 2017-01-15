package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep9<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9>
  extends MergeValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep9<R, T1, T2, T3, T4, T5, T6, T7, T8, T9> key(Collection<? extends Field<?>> paramCollection);
}
