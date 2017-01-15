package org.jooq;

import java.util.List;
import java.util.Map;
import org.jooq.conf.ParamType;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.DataTypeException;

public abstract interface Query
  extends QueryPart, Attachable
{
  public abstract int execute()
    throws DataAccessException;
  
  public abstract boolean isExecutable();
  
  public abstract String getSQL();
  
  @Deprecated
  public abstract String getSQL(boolean paramBoolean);
  
  public abstract String getSQL(ParamType paramParamType);
  
  public abstract List<Object> getBindValues();
  
  public abstract Map<String, Param<?>> getParams();
  
  public abstract Param<?> getParam(String paramString);
  
  public abstract Query bind(String paramString, Object paramObject)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract Query bind(int paramInt, Object paramObject)
    throws IllegalArgumentException, DataTypeException;
  
  public abstract Query queryTimeout(int paramInt);
  
  public abstract Query keepStatement(boolean paramBoolean);
  
  public abstract void close()
    throws DataAccessException;
  
  public abstract void cancel()
    throws DataAccessException;
}
