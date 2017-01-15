package org.jooq;

import java.util.List;

public abstract interface Catalog
  extends QueryPart
{
  public abstract String getName();
  
  public abstract List<Schema> getSchemas();
  
  public abstract Schema getSchema(String paramString);
}
