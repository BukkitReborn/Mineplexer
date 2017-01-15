package org.jooq;

public abstract interface CaseConditionStep<T>
  extends Field<T>
{
  @Support
  public abstract CaseConditionStep<T> when(Condition paramCondition, T paramT);
  
  @Support
  public abstract CaseConditionStep<T> when(Condition paramCondition, Field<T> paramField);
  
  @Support
  public abstract CaseConditionStep<T> when(Condition paramCondition, Select<? extends Record1<T>> paramSelect);
  
  @Support
  public abstract Field<T> otherwise(T paramT);
  
  @Support
  public abstract Field<T> otherwise(Field<T> paramField);
  
  @Support
  public abstract Field<T> otherwise(Select<? extends Record1<T>> paramSelect);
}
