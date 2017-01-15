package org.jooq;

public abstract interface SelectStartWithStep<R extends Record>
  extends SelectGroupByStep<R>
{
  @Support({SQLDialect.CUBRID})
  public abstract SelectGroupByStep<R> startWith(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectGroupByStep<R> startWith(Field<Boolean> paramField);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectGroupByStep<R> startWith(String paramString);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectGroupByStep<R> startWith(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID})
  public abstract SelectGroupByStep<R> startWith(String paramString, QueryPart... paramVarArgs);
}
