package org.jooq.types;

import java.io.Serializable;

public abstract interface Interval
  extends Serializable
{
  public abstract Interval neg();
  
  public abstract Interval abs();
  
  public abstract int getSign();
  
  public abstract double doubleValue();
  
  public abstract float floatValue();
  
  public abstract long longValue();
  
  public abstract int intValue();
  
  public abstract byte byteValue();
  
  public abstract short shortValue();
}
