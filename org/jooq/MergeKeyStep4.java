package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep4<R extends Record, T1, T2, T3, T4>
  extends MergeValuesStep4<R, T1, T2, T3, T4>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep4<R, T1, T2, T3, T4> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep4<R, T1, T2, T3, T4> key(Collection<? extends Field<?>> paramCollection);
}
