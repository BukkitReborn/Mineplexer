package org.jooq;

import java.util.Collection;

public abstract interface MergeNotMatchedValuesStep10<R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6, T7 paramT7, T8 paramT8, T9 paramT9, T10 paramT10);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5, Field<T7> paramField6, Field<T8> paramField7, Field<T9> paramField8, Field<T10> paramField9);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeNotMatchedWhereStep<R> values(Collection<?> paramCollection);
}
