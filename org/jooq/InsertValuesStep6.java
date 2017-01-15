package org.jooq;

import java.util.Collection;

public abstract interface InsertValuesStep6<R extends Record, T1, T2, T3, T4, T5, T6>
  extends InsertOnDuplicateStep<R>
{
  @Support
  public abstract InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> values(T1 paramT1, T2 paramT2, T3 paramT3, T4 paramT4, T5 paramT5, T6 paramT6);
  
  @Support
  public abstract InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> values(Field<T1> paramField, Field<T2> paramField1, Field<T3> paramField2, Field<T4> paramField3, Field<T5> paramField4, Field<T6> paramField5);
  
  @Support
  public abstract InsertValuesStep6<R, T1, T2, T3, T4, T5, T6> values(Collection<?> paramCollection);
  
  @Support
  public abstract Insert<R> select(Select<? extends Record6<T1, T2, T3, T4, T5, T6>> paramSelect);
}
