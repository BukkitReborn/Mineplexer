package org.jooq;

public abstract interface SortField<T>
  extends QueryPart
{
  public abstract String getName();
  
  public abstract SortOrder getOrder();
  
  @Support
  public abstract SortField<T> nullsFirst();
  
  @Support
  public abstract SortField<T> nullsLast();
}
