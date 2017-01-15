package org.jooq;

import java.util.Collection;

public abstract interface MergeNotMatchedValuesStep6<R extends Record, T1, T2, T3, T4, T5, T6>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Collection<?> paramCollection);
}
