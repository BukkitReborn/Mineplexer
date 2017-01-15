package org.jooq;

public abstract interface AlterTableDropStep
  extends AlterTableFinalStep
{
  @Support
  public abstract AlterTableFinalStep cascade();
  
  @Support
  public abstract AlterTableFinalStep restrict();
}
