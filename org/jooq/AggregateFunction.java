package org.jooq;

public abstract interface AggregateFunction<T>
  extends Field<T>, WindowOverStep<T>
{
  @Support({SQLDialect.CUBRID, SQLDialect.H2, SQLDialect.POSTGRES})
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
