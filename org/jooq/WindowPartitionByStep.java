package org.jooq;

public abstract interface WindowPartitionByStep<T>
  extends WindowOrderByStep<T>
{
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowOrderByStep<T> partitionBy(Field<?>... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowOrderByStep<T> partitionByOne();
}
