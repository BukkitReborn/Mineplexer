package org.jooq;

public abstract interface DropTableStep
  extends DropTableFinalStep
{
  @Support
  public abstract DropTableFinalStep cascade();
  
  @Support
  public abstract DropTableFinalStep restrict();
}
