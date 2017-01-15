package org.jooq;

import java.sql.PreparedStatement;
import java.util.Collection;
import org.jooq.exception.DataAccessException;

public abstract interface BindContext
  extends Context<BindContext>
{
  public abstract PreparedStatement statement();
  
  @Deprecated
  public abstract BindContext bind(QueryPart paramQueryPart)
    throws DataAccessException;
  
  @Deprecated
  public abstract BindContext bind(Collection<? extends QueryPart> paramCollection)
    throws DataAccessException;
  
  @Deprecated
  public abstract BindContext bind(QueryPart[] paramArrayOfQueryPart)
    throws DataAccessException;
  
  @Deprecated
  public abstract BindContext bindValue(Object paramObject, Class<?> paramClass)
    throws DataAccessException;
  
  @Deprecated
  public abstract BindContext bindValues(Object... paramVarArgs)
    throws DataAccessException;
  
  public abstract BindContext bindValue(Object paramObject, Field<?> paramField)
    throws DataAccessException;
}
