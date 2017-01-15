package org.jooq;

import java.util.Collection;

public abstract interface WindowSpecificationOrderByStep
  extends WindowSpecificationRowsStep
{
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowSpecificationRowsStep orderBy(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowSpecificationRowsStep orderBy(SortField<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowSpecificationRowsStep orderBy(Collection<? extends SortField<?>> paramCollection);
}
