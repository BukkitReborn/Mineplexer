package org.jooq;

public abstract interface InsertOnDuplicateStep<R extends Record>
  extends InsertReturningStep<R>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract InsertOnDuplicateSetStep<R> onDuplicateKeyUpdate();
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL})
  public abstract InsertFinalStep<R> onDuplicateKeyIgnore();
}
