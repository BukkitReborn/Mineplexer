package org.apache.commons.pool2;

public abstract class BaseObjectPool<T>
  implements ObjectPool<T>
{
  public abstract T borrowObject()
    throws Exception;
  
  public abstract void returnObject(T paramT)
    throws Exception;
  
  public abstract void invalidateObject(T paramT)
    throws Exception;
  
  public int getNumIdle()
  {
    return -1;
  }
  
  public int getNumActive()
  {
    return -1;
  }
  
  public void clear()
    throws Exception, UnsupportedOperationException
  {
    throw new UnsupportedOperationException();
  }
  
  public void addObject()
    throws Exception, UnsupportedOperationException
  {
    throw new UnsupportedOperationException();
  }
  
  public void close()
  {
    this.closed = true;
  }
  
  public final boolean isClosed()
  {
    return this.closed;
  }
  
  protected final void assertOpen()
    throws IllegalStateException
  {
    if (isClosed()) {
      throw new IllegalStateException("Pool not open");
    }
  }
  
  private volatile boolean closed = false;
}
