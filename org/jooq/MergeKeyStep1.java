package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep1<R extends Record, T1>
  extends MergeValuesStep1<R, T1>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep1<R, T1> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep1<R, T1> key(Collection<? extends Field<?>> paramCollection);
}
