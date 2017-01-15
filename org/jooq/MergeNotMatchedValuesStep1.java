package org.jooq;

import java.util.Collection;

public abstract interface MergeNotMatchedValuesStep1<R extends Record, T1>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(T1 paramT1);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Field<T1> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Collection<?> paramCollection);
}
