package org.jooq;

import java.util.Collection;

public abstract interface SelectForUpdateOfStep<R extends Record>
  extends SelectForUpdateWaitStep<R>
{
  @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract SelectForUpdateWaitStep<R> of(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB})
  public abstract SelectForUpdateWaitStep<R> of(Collection<? extends Field<?>> paramCollection);
  
  @Support({SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract SelectForUpdateWaitStep<R> of(Table<?>... paramVarArgs);
}
