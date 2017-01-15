package org.jooq;

@Deprecated
public abstract interface Template
{
  public abstract QueryPart transform(Object... paramVarArgs);
}
