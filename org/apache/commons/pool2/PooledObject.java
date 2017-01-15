package org.apache.commons.pool2;

import java.io.PrintWriter;
import java.util.Deque;

public abstract interface PooledObject<T>
  extends Comparable<PooledObject<T>>
{
  public abstract T getObject();
  
  public abstract long getCreateTime();
  
  public abstract long getActiveTimeMillis();
  
  public abstract long getIdleTimeMillis();
  
  public abstract long getLastBorrowTime();
  
  public abstract long getLastReturnTime();
  
  public abstract long getLastUsedTime();
  
  public abstract int compareTo(PooledObject<T> paramPooledObject);
  
  public abstract boolean equals(Object paramObject);
  
  public abstract int hashCode();
  
  public abstract String toString();
  
  public abstract boolean startEvictionTest();
  
  public abstract boolean endEvictionTest(Deque<PooledObject<T>> paramDeque);
  
  public abstract boolean allocate();
  
  public abstract boolean deallocate();
  
  public abstract void invalidate();
  
  public abstract void setLogAbandoned(boolean paramBoolean);
  
  public abstract void use();
  
  public abstract void printStackTrace(PrintWriter paramPrintWriter);
  
  public abstract PooledObjectState getState();
  
  public abstract void markAbandoned();
  
  public abstract void markReturning();
}
