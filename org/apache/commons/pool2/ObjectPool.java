package org.apache.commons.pool2;

import java.util.NoSuchElementException;

public abstract interface ObjectPool<T>
{
  public abstract T borrowObject()
    throws Exception, NoSuchElementException, IllegalStateException;
  
  public abstract void returnObject(T paramT)
    throws Exception;
  
  public abstract void invalidateObject(T paramT)
    throws Exception;
  
  public abstract void addObject()
    throws Exception, IllegalStateException, UnsupportedOperationException;
  
  public abstract int getNumIdle();
  
  public abstract int getNumActive();
  
  public abstract void clear()
    throws Exception, UnsupportedOperationException;
  
  public abstract void close();
}
