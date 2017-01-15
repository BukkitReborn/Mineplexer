package org.jooq;

import java.util.Collection;

public abstract interface MergeNotMatchedValuesStep3<R extends Record, T1, T2, T3>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Collection<?> paramCollection);
}
