package org.jooq;

public abstract interface Package
  extends QueryPart
{
  public abstract Schema getSchema();
  
  public abstract String getName();
}
