package org.jooq;

import java.util.Collection;

public abstract interface MergeValuesStep1<R extends Record, T1>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract Merge<R> values(T1 paramT1);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract Merge<R> values(Field<T1> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract Merge<R> values(Collection<?> paramCollection);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract Merge<R> select(Select<? extends Record1<T1>> paramSelect);
}
