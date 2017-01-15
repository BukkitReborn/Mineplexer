package org.jooq;

import java.util.Collection;

public abstract interface InsertValuesStep1<R extends Record, T1>
  extends InsertOnDuplicateStep<R>
{
  @Support
  public abstract InsertValuesStep1<R, T1> values(T1 paramT1);
  
  @Support
  public abstract InsertValuesStep1<R, T1> values(Field<T1> paramField);
  
  @Support
  public abstract InsertValuesStep1<R, T1> values(Collection<?> paramCollection);
  
  @Support
  public abstract Insert<R> select(Select<? extends Record1<T1>> paramSelect);
}
