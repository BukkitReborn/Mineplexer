package org.jooq;

public abstract interface MergeNotMatchedWhereStep<R extends Record>
  extends MergeFinalStep<R>
{
  @Support({SQLDialect.CUBRID})
  public abstract MergeFinalStep<R> where(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID})
  public abstract MergeFinalStep<R> where(Field<Boolean> paramField);
}
