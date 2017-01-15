package org.jooq;

public abstract interface MergeMatchedWhereStep<R extends Record>
  extends MergeNotMatchedStep<R>
{
  @Support({SQLDialect.CUBRID})
  public abstract MergeMatchedDeleteStep<R> where(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID})
  public abstract MergeMatchedDeleteStep<R> where(Field<Boolean> paramField);
}
