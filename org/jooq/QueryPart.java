package org.jooq;

import java.io.Serializable;

public abstract interface QueryPart
  extends Serializable
{
  public abstract String toString();
  
  public abstract boolean equals(Object paramObject);
  
  public abstract int hashCode();
}
