package org.jooq;

import java.util.Collection;

public abstract interface SelectFromStep<R extends Record>
  extends SelectWhereStep<R>
{
  @Support
  public abstract SelectJoinStep<R> from(TableLike<?> paramTableLike);
  
  @Support
  public abstract SelectJoinStep<R> from(TableLike<?>... paramVarArgs);
  
  @Support
  public abstract SelectJoinStep<R> from(Collection<? extends TableLike<?>> paramCollection);
  
  @Support
  public abstract SelectJoinStep<R> from(String paramString);
  
  @Support
  public abstract SelectJoinStep<R> from(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract SelectJoinStep<R> from(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract SelectFromStep<R> hint(String paramString);
}
