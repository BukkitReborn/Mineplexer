package org.jooq;

public abstract interface MergeOnConditionStep<R extends Record>
  extends MergeMatchedStep<R>
{
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> and(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> and(Field<Boolean> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> and(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> and(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> and(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> andNot(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> andNot(Field<Boolean> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> andExists(Select<?> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> andNotExists(Select<?> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> or(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> or(Field<Boolean> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> or(String paramString);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> or(String paramString, Object... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> or(String paramString, QueryPart... paramVarArgs);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> orNot(Condition paramCondition);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> orNot(Field<Boolean> paramField);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> orExists(Select<?> paramSelect);
  
  @Support({SQLDialect.CUBRID, SQLDialect.HSQLDB})
  public abstract MergeOnConditionStep<R> orNotExists(Select<?> paramSelect);
}
