package org.jooq;

import java.util.Collection;

public abstract interface MergeKeyStep5<R extends Record, T1, T2, T3, T4, T5>
  extends MergeValuesStep5<R, T1, T2, T3, T4, T5>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep5<R, T1, T2, T3, T4, T5> key(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract MergeValuesStep5<R, T1, T2, T3, T4, T5> key(Collection<? extends Field<?>> paramCollection);
}
