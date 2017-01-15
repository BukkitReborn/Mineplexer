package org.jooq;

public abstract interface SelectConnectByConditionStep<R extends Record>
  extends SelectStartWithStep<R>
{
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> and(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> and(Field<Boolean> paramField);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> and(String paramString);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> and(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> and(String paramString, QueryPart... paramVarArgs);
}
