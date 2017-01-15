package org.jooq;

import java.util.List;
import org.jooq.exception.DataAccessException;

public abstract interface Meta
{
  @Support
  public abstract List<Catalog> getCatalogs()
    throws DataAccessException;
  
  @Support
  public abstract List<Schema> getSchemas()
    throws DataAccessException;
  
  @Support
  public abstract List<Table<?>> getTables()
    throws DataAccessException;
  
  @Support
  public abstract List<UniqueKey<?>> getPrimaryKeys()
    throws DataAccessException;
}
