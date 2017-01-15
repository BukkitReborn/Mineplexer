package org.jooq;

public abstract interface EnumType
{
  public abstract String getLiteral();
  
  public abstract Schema getSchema();
  
  public abstract String getName();
}
