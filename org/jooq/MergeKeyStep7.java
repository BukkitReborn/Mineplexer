package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep7<R extends Record, T1, T2, T3, T4, T5, T6, T7>
  extends MergeValuesStep7<R, T1, T2, T3, T4, T5, T6, T7>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep7<R, T1, T2, T3, T4, T5, T6, T7> key(Collection<? extends Field<?>> paramCollection);
}
