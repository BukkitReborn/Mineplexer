package org.jooq;

public abstract interface MergeMatchedDeleteStep<R extends Record>
  extends MergeNotMatchedStep<R>
{
  @Support({SQLDialect.CUBRID})
  public abstract MergeNotMatchedStep<R> deleteWhere(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID})
  public abstract MergeNotMatchedStep<R> deleteWhere(Field<Boolean> paramField);
}
