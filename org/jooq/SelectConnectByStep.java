package org.jooq;

public abstract interface SelectConnectByStep<R extends Record>
  extends SelectGroupByStep<R>
{
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectBy(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectBy(Field<Boolean> paramField);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectBy(String paramString);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectBy(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectBy(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectByNoCycle(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectByNoCycle(Field<Boolean> paramField);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectByNoCycle(String paramString);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectByNoCycle(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectConnectByConditionStep<R> connectByNoCycle(String paramString, QueryPart... paramVarArgs);
}
