package org.jooq;

import java.util.Collection;

public abstract interface MergeNotMatchedValuesStepN<R extends Record>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Collection<?> paramCollection);
}
