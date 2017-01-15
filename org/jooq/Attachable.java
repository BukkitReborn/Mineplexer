package org.jooq;

import java.io.Serializable;

public abstract interface Attachable
  extends Serializable
{
  public abstract void attach(Configuration paramConfiguration);
  
  public abstract void detach();
}
