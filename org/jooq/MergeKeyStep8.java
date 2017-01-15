package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep8<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8>
  extends MergeValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep8<R, T1, T2, T3, T4, T5, T6, T7, T8> key(Collection<? extends Field<?>> paramCollection);
}
