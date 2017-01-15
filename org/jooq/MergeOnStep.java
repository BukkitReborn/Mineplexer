package org.jooq;

public abstract interface MergeOnStep<R extends Record>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> on(Condition... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> on(Field<Boolean> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> on(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> on(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> on(String paramString, QueryPart... paramVarArgs);
}
