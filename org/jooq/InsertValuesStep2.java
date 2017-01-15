package org.jooq;

import java.util.Collection;

public abstract interface InsertValuesStep2<R extends Record, T1, T2>
  extends InsertOnDuplicateStep<R>
{
  @Support
  public abstract InsertValuesStep2<R, T1, T2> values(T1 paramT1, T2 paramT2);
  
  @Support
  public abstract InsertValuesStep2<R, T1, T2> values(Field<T1> paramField, Field<T2> paramField1);
  
  @Support
  public abstract InsertValuesStep2<R, T1, T2> values(Collection<?> paramCollection);
  
  @Support
  public abstract Insert<R> select(Select<? extends Record2<T1, T2>> paramSelect);
}
