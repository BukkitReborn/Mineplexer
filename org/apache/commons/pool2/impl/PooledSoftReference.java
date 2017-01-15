package org.apache.commons.pool2.impl;

import java.lang.ref.SoftReference;
import org.apache.commons.pool2.PooledObjectState;

public class PooledSoftReference<T>
  extends DefaultPooledObject<T>
{
  private volatile SoftReference<T> reference;
  
  public PooledSoftReference(SoftReference<T> reference)
  {
    super(null);
    this.reference = reference;
  }
  
  public T getObject()
  {
    return (T)this.reference.get();
  }
  
  public String toString()
  {
    StringBuilder result = new StringBuilder();
    result.append("Referenced Object: ");
    result.append(getObject().toString());
    result.append(", State: ");
    synchronized (this)
    {
      result.append(getState().toString());
    }
    return result.toString();
  }
  
  public synchronized SoftReference<T> getReference()
  {
    return this.reference;
  }
  
  public synchronized void setReference(SoftReference<T> reference)
  {
    this.reference = reference;
  }
}
