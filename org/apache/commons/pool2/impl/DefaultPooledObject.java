package org.apache.commons.pool2.impl;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Deque;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectState;
import org.apache.commons.pool2.TrackedUse;

public class DefaultPooledObject<T>
  implements PooledObject<T>
{
  private final T object;
  private PooledObjectState state = PooledObjectState.IDLE;
  private final long createTime = System.currentTimeMillis();
  private volatile long lastBorrowTime = this.createTime;
  private volatile long lastUseTime = this.createTime;
  private volatile long lastReturnTime = this.createTime;
  private volatile boolean logAbandoned = false;
  private volatile Exception borrowedBy = null;
  private volatile Exception usedBy = null;
  private volatile long borrowedCount = 0L;
  
  public DefaultPooledObject(T object)
  {
    this.object = object;
  }
  
  public T getObject()
  {
    return (T)this.object;
  }
  
  public long getCreateTime()
  {
    return this.createTime;
  }
  
  public long getActiveTimeMillis()
  {
    long rTime = this.lastReturnTime;
    long bTime = this.lastBorrowTime;
    if (rTime > bTime) {
      return rTime - bTime;
    }
    return System.currentTimeMillis() - bTime;
  }
  
  public long getIdleTimeMillis()
  {
    return System.currentTimeMillis() - this.lastReturnTime;
  }
  
  public long getLastBorrowTime()
  {
    return this.lastBorrowTime;
  }
  
  public long getLastReturnTime()
  {
    return this.lastReturnTime;
  }
  
  public long getBorrowedCount()
  {
    return this.borrowedCount;
  }
  
  public long getLastUsedTime()
  {
    if ((this.object instanceof TrackedUse)) {
      return Math.max(((TrackedUse)this.object).getLastUsed(), this.lastUseTime);
    }
    return this.lastUseTime;
  }
  
  public int compareTo(PooledObject<T> other)
  {
    long lastActiveDiff = getLastReturnTime() - other.getLastReturnTime();
    if (lastActiveDiff == 0L) {
      return System.identityHashCode(this) - System.identityHashCode(other);
    }
    return (int)Math.min(Math.max(lastActiveDiff, -2147483648L), 2147483647L);
  }
  
  public String toString()
  {
    StringBuilder result = new StringBuilder();
    result.append("Object: ");
    result.append(this.object.toString());
    result.append(", State: ");
    synchronized (this)
    {
      result.append(this.state.toString());
    }
    return result.toString();
  }
  
  public synchronized boolean startEvictionTest()
  {
    if (this.state == PooledObjectState.IDLE)
    {
      this.state = PooledObjectState.EVICTION;
      return true;
    }
    return false;
  }
  
  public synchronized boolean endEvictionTest(Deque<PooledObject<T>> idleQueue)
  {
    if (this.state == PooledObjectState.EVICTION)
    {
      this.state = PooledObjectState.IDLE;
      return true;
    }
    if (this.state == PooledObjectState.EVICTION_RETURN_TO_HEAD)
    {
      this.state = PooledObjectState.IDLE;
      if (idleQueue.offerFirst(this)) {}
    }
    return false;
  }
  
  public synchronized boolean allocate()
  {
    if (this.state == PooledObjectState.IDLE)
    {
      this.state = PooledObjectState.ALLOCATED;
      this.lastBorrowTime = System.currentTimeMillis();
      this.lastUseTime = this.lastBorrowTime;
      this.borrowedCount += 1L;
      if (this.logAbandoned) {
        this.borrowedBy = new AbandonedObjectCreatedException();
      }
      return true;
    }
    if (this.state == PooledObjectState.EVICTION)
    {
      this.state = PooledObjectState.EVICTION_RETURN_TO_HEAD;
      return false;
    }
    return false;
  }
  
  public synchronized boolean deallocate()
  {
    if ((this.state == PooledObjectState.ALLOCATED) || (this.state == PooledObjectState.RETURNING))
    {
      this.state = PooledObjectState.IDLE;
      this.lastReturnTime = System.currentTimeMillis();
      if (this.borrowedBy != null) {
        this.borrowedBy = null;
      }
      return true;
    }
    return false;
  }
  
  public synchronized void invalidate()
  {
    this.state = PooledObjectState.INVALID;
  }
  
  public void use()
  {
    this.lastUseTime = System.currentTimeMillis();
    this.usedBy = new Exception("The last code to use this object was:");
  }
  
  public void printStackTrace(PrintWriter writer)
  {
    Exception borrowedByCopy = this.borrowedBy;
    if (borrowedByCopy != null) {
      borrowedByCopy.printStackTrace(writer);
    }
    Exception usedByCopy = this.usedBy;
    if (usedByCopy != null) {
      usedByCopy.printStackTrace(writer);
    }
  }
  
  public synchronized PooledObjectState getState()
  {
    return this.state;
  }
  
  public synchronized void markAbandoned()
  {
    this.state = PooledObjectState.ABANDONED;
  }
  
  public synchronized void markReturning()
  {
    this.state = PooledObjectState.RETURNING;
  }
  
  public void setLogAbandoned(boolean logAbandoned)
  {
    this.logAbandoned = logAbandoned;
  }
  
  static class AbandonedObjectCreatedException
    extends Exception
  {
    private static final long serialVersionUID = 7398692158058772916L;
    private static final SimpleDateFormat format = new SimpleDateFormat("'Pooled object created' yyyy-MM-dd HH:mm:ss Z 'by the following code has not been returned to the pool:'");
    private final long _createdTime;
    
    public AbandonedObjectCreatedException()
    {
      this._createdTime = System.currentTimeMillis();
    }
    
    public String getMessage()
    {
      String msg;
      synchronized (format)
      {
        msg = format.format(new Date(this._createdTime));
      }
      return msg;
    }
  }
}
