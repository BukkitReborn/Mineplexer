package org.jooq;

import java.util.Collection;

public abstract interface UpdateFromStep<R extends Record>
  extends UpdateWhereStep<R>
{
  @Support({SQLDialect.POSTGRES})
  public abstract UpdateWhereStep<R> from(TableLike<?> paramTableLike);
  
  @Support({SQLDialect.POSTGRES})
  public abstract UpdateWhereStep<R> from(TableLike<?>... paramVarArgs);
  
  @Support({SQLDialect.POSTGRES})
  public abstract UpdateWhereStep<R> from(Collection<? extends TableLike<?>> paramCollection);
  
  @Support({SQLDialect.POSTGRES})
  public abstract UpdateWhereStep<R> from(String paramString);
  
  @Support({SQLDialect.POSTGRES})
  public abstract UpdateWhereStep<R> from(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.POSTGRES})
  public abstract UpdateWhereStep<R> from(String paramString, QueryPart... paramVarArgs);
}
