package org.jooq;

import java.util.Collection;

public abstract interface MergeValuesStep4<R extends Record, T1, T2, T3, T4>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract Merge<R> values(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract Merge<R> values(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract Merge<R> values(Collection<?> paramCollection);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract Merge<R> select(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
}
