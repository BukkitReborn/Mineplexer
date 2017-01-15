package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;

public abstract interface SelectOnStep<R extends Record>
{
  @Support
  public abstract SelectOnConditionStep<R> on(Condition... paramVarArgs);
  
  @Support
  public abstract SelectOnConditionStep<R> on(Field<Boolean> paramField);
  
  @Support
  public abstract SelectOnConditionStep<R> on(String paramString);
  
  @Support
  public abstract SelectOnConditionStep<R> on(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectOnConditionStep<R> on(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectJoinStep<R> onKey()
    throws DataAccessException;
  
  @Support
  public abstract SelectJoinStep<R> onKey(TableField<?, ?>... paramVarArgs)
    throws DataAccessException;
  
  @Support
  public abstract SelectJoinStep<R> onKey(ForeignKey<?, ?> paramForeignKey);
  
  @Support
  public abstract SelectJoinStep<R> using(Field<?>... paramVarArgs);
  
  @Support
  public abstract SelectJoinStep<R> using(Collection<? extends Field<?>> paramCollection);
}
