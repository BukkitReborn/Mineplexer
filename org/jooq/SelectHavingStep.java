package org.jooq;

import java.util.Collection;

public abstract interface SelectHavingStep<R extends Record>
  extends SelectWindowStep<R>
{
  @Support
  public abstract SelectHavingConditionStep<R> having(Condition... paramVarArgs);
  
  @Support
  public abstract SelectHavingConditionStep<R> having(Collection<? extends Condition> paramCollection);
  
  @Support
  public abstract SelectHavingConditionStep<R> having(Field<Boolean> paramField);
  
  @Support
  public abstract SelectHavingConditionStep<R> having(String paramString);
  
  @Support
  public abstract SelectHavingConditionStep<R> having(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectHavingConditionStep<R> having(String paramString, QueryPart... paramVarArgs);
}
