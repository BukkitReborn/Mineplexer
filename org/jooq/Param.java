package org.jooq;

import org.jooq.exception.DataTypeException;

public abstract interface Param<T>
  extends Field<T>
{
  public abstract String getName();
  
  public abstract String getParamName();
  
  public abstract T getValue();
  
  public abstract void setValue(T paramT);
  
  public abstract void setConverted(Object paramObject)
    throws DataTypeException;
  
  public abstract void setInline(boolean paramBoolean);
  
  public abstract boolean isInline();
}
