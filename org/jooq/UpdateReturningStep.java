package org.jooq;

import java.util.Collection;

public abstract interface UpdateReturningStep<R extends Record>
{
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract UpdateResultStep<R> returning();
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract UpdateResultStep<R> returning(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.FIREBIRD, SQLDialect.POSTGRES})
  public abstract UpdateResultStep<R> returning(Collection<? extends Field<?>> paramCollection);
}
