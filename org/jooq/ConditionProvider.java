package org.jooq;

import java.util.Collection;

@Deprecated
public abstract interface ConditionProvider
{
  @Support
  public abstract void addConditions(Condition... paramVarArgs);
  
  @Support
  public abstract void addConditions(Collection<? extends Condition> paramCollection);
  
  @Support
  public abstract void addConditions(Operator paramOperator, Condition... paramVarArgs);
  
  @Support
  public abstract void addConditions(Operator paramOperator, Collection<? extends Condition> paramCollection);
}
