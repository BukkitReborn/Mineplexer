package org.jooq;

public abstract interface WindowOverStep<T>
{
  @Support({SQLDialect.CUBRID, SQLDialect.DERBY, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.POSTGRES})
  public abstract WindowPartitionByStep<T> over();
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> over(Name paramName);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> over(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> over(WindowSpecification paramWindowSpecification);
  
  @Support({SQLDialect.CUBRID, SQLDialect.POSTGRES})
  public abstract WindowFinalStep<T> over(WindowDefinition paramWindowDefinition);
}
