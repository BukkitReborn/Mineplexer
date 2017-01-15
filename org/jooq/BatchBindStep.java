package org.jooq;

public abstract interface BatchBindStep
  extends Batch
{
  public abstract BatchBindStep bind(Object... paramVarArgs);
  
  public abstract BatchBindStep bind(Object[][] paramArrayOfObject);
}
