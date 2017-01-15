package org.jooq;

import java.util.Collection;
import java.util.Map;

public abstract interface StoreQuery<R extends Record>
  extends Query
{
  @Support
  public abstract void setRecord(R paramR);
  
  @Support
  public abstract <T> void addValue(Field<T> paramField, T paramT);
  
  @Support
  public abstract <T> void addValue(Field<T> paramField1, Field<T> paramField2);
  
  @Support
  public abstract void addValues(Map<? extends Field<?>, ?> paramMap);
  
  @Support
  public abstract void setReturning();
  
  @Support
  public abstract void setReturning(Identity<R, ? extends Number> paramIdentity);
  
  @Support
  public abstract void setReturning(Field<?>... paramVarArgs);
  
  @Support
  public abstract void setReturning(Collection<? extends Field<?>> paramCollection);
  
  @Support
  public abstract R getReturnedRecord();
  
  @Support
  public abstract Result<R> getReturnedRecords();
}
