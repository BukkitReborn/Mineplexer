package org.apache.commons.pool2;

public abstract interface UsageTracking<T>
{
  public abstract void use(T paramT);
}
