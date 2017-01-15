package org.jooq;

import java.util.Collection;

public abstract interface InsertValuesStep4<R extends Record, T1, T2, T3, T4>
  extends InsertOnDuplicateStep<R>
{
  @Support
  public abstract InsertValuesStep4<R, T1, T2, T3, T4> values(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4);
  
  @Support
  public abstract InsertValuesStep4<R, T1, T2, T3, T4> values(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3);
  
  @Support
  public abstract InsertValuesStep4<R, T1, T2, T3, T4> values(Collection<?> paramCollection);
  
  @Support
  public abstract Insert<R> select(Select<? extends Record4<T1, T2, T3, T4>> paramSelect);
}
