package org.jooq.impl;

import org.jooq.CaseValueStep;
import org.jooq.CaseWhenStep;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Select;

class CaseValueStepImpl<V>
  implements CaseValueStep<V>
{
  private final Field<V> value;
  
  CaseValueStepImpl(Field<V> value)
  {
    this.value = value;
  }
  
  public final <T> CaseWhenStep<V, T> when(V compareValue, T result)
  {
    return when(Utils.field(compareValue), Utils.field(result));
  }
  
  public final <T> CaseWhenStep<V, T> when(V compareValue, Field<T> result)
  {
    return when(Utils.field(compareValue), result);
  }
  
  public final <T> CaseWhenStep<V, T> when(V compareValue, Select<? extends Record1<T>> result)
  {
    return when(Utils.field(compareValue), DSL.field(result));
  }
  
  public final <T> CaseWhenStep<V, T> when(Field<V> compareValue, T result)
  {
    return when(compareValue, Utils.field(result));
  }
  
  public final <T> CaseWhenStep<V, T> when(Field<V> compareValue, Field<T> result)
  {
    return new CaseWhenStepImpl(this.value, compareValue, result);
  }
  
  public final <T> CaseWhenStep<V, T> when(Field<V> compareValue, Select<? extends Record1<T>> result)
  {
    return when(compareValue, DSL.field(result));
  }
}
