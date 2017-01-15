package org.jooq;

import java.util.Collection;

public abstract interface GroupConcatOrderByStep
  extends GroupConcatSeparatorStep
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract GroupConcatSeparatorStep orderBy(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract GroupConcatSeparatorStep orderBy(SortField<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.POSTGRES})
  public abstract GroupConcatSeparatorStep orderBy(Collection<? extends SortField<?>> paramCollection);
}
