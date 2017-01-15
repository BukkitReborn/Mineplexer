package org.jooq;

import java.util.Collection;

public abstract interface WindowSpecificationPartitionByStep
  extends WindowSpecificationOrderByStep
{
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowSpecificationOrderByStep partitionBy(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowSpecificationOrderByStep partitionBy(Collection<? extends Field<?>> paramCollection);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowSpecificationOrderByStep partitionByOne();
}
