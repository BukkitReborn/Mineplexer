package org.jooq;

import java.util.Collection;

public abstract interface DeleteQuery<R extends Record>
  extends ConditionProvider, Delete<R>
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
