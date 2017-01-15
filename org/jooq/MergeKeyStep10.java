package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep10<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
  extends MergeValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep10<R, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> key(Collection<? extends Field<?>> paramCollection);
}
