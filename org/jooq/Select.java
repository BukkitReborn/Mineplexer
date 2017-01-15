package org.jooq;

import java.util.List;
import org.jooq.exception.DataAccessException;

public abstract interface Select<R extends Record>
  extends ResultQuery<R>, TableLike<R>, FieldLike
{
  @Support
  public abstract Select<R> union(Select<? extends R> paramSelect);
  
  @Support
  public abstract Select<R> unionAll(Select<? extends R> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Select<R> except(Select<? extends R> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE})
  public abstract Select<R> intersect(Select<? extends R> paramSelect);
  
  public abstract List<Field<?>> getSelect();
  
  @Deprecated
  public abstract int fetchCount()
    throws DataAccessException;
}
