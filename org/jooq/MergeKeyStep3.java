package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep3<R extends Record, T1, T2, T3>
  extends MergeValuesStep3<R, T1, T2, T3>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep3<R, T1, T2, T3> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep3<R, T1, T2, T3> key(Collection<? extends Field<?>> paramCollection);
}
