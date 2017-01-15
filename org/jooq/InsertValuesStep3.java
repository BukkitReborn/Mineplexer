package org.jooq;

import java.util.Collection;

public abstract interface InsertValuesStep3<R extends Record, T1, T2, T3>
  extends InsertOnDuplicateStep<R>
{
  @Support
  public abstract InsertValuesStep3<R, T1, T2, T3> values(T1 paramT1, T2 paramT2, T3 paramT3);
  
  @Support
  public abstract InsertValuesStep3<R, T1, T2, T3> values(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2);
  
  @Support
  public abstract InsertValuesStep3<R, T1, T2, T3> values(Collection<?> paramCollection);
  
  @Support
  public abstract Insert<R> select(Select<? extends Record3<T1, T2, T3>> paramSelect);
}
