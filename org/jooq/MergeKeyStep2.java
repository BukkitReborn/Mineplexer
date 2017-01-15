package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep2<R extends Record, T1, T2>
  extends MergeValuesStep2<R, T1, T2>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep2<R, T1, T2> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep2<R, T1, T2> key(Collection<? extends Field<?>> paramCollection);
}
