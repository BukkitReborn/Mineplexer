package org.jooq;

import java.util.Collection;
import org.jooq.exception.DataAccessException;

public abstract interface TableOnStep
{
  @Support
  public abstract TableOnConditionStep on(Condition... paramVarArgs);
  
  @Support
  public abstract TableOnConditionStep on(Field<Boolean> paramField);
  
  @Support
  public abstract TableOnConditionStep on(String paramString);
  
  @Support
  public abstract TableOnConditionStep on(String paramString, Object... paramVarArgs);
  
  @Support
  public abstract TableOnConditionStep on(String paramString, QueryPart... paramVarArgs);
  
  @Support
  public abstract Table<Record> using(Field<?>... paramVarArgs);
  
  @Support
  public abstract Table<Record> using(Collection<? extends Field<?>> paramCollection);
  
  @Support
  public abstract TableOnConditionStep onKey()
    throws DataAccessException;
  
  @Support
  public abstract TableOnConditionStep onKey(TableField<?, ?>... paramVarArgs)
    throws DataAccessException;
  
  @Support
  public abstract TableOnConditionStep onKey(ForeignKey<?, ?> paramForeignKey);
}
