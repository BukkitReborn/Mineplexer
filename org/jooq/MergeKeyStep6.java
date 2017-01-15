package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep6<R extends Record, T1, T2, T3, T4, T5, T6>
  extends MergeValuesStep6<R, T1, T2, T3, T4, T5, T6>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep6<R, T1, T2, T3, T4, T5, T6> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep6<R, T1, T2, T3, T4, T5, T6> key(Collection<? extends Field<?>> paramCollection);
}
