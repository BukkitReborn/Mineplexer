package org.jooq;

public abstract interface Case
{
  @Support
  public abstract <V> CaseValueStep<V> value(V paramV);
  
  @Support
  public abstract <V> CaseValueStep<V> value(Field<V> paramField);
  
  @Support
  public abstract <T> CaseConditionStep<T> when(Condition paramCondition, T paramT);
  
  @Support
  public abstract <T> CaseConditionStep<T> when(Condition paramCondition, Field<T> paramField);
  
  @Support
  public abstract <T> CaseConditionStep<T> when(Condition paramCondition, Select<? extends Record1<T>> paramSelect);
}
