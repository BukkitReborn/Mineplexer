package org.apache.commons.pool2.impl;

import java.util.List;
import java.util.Map;

public abstract interface GenericKeyedObjectPoolMXBean<K>
{
  public abstract boolean getBlockWhenExhausted();
  
  public abstract boolean getLifo();
  
  public abstract int getMaxIdlePerKey();
  
  public abstract int getMaxTotal();
  
  public abstract int getMaxTotalPerKey();
  
  public abstract long getMaxWaitMillis();
  
  public abstract long getMinEvictableIdleTimeMillis();
  
  public abstract int getMinIdlePerKey();
  
  public abstract int getNumActive();
  
  public abstract int getNumIdle();
  
  public abstract int getNumTestsPerEvictionRun();
  
  public abstract boolean getTestOnCreate();
  
  public abstract boolean getTestOnBorrow();
  
  public abstract boolean getTestOnReturn();
  
  public abstract boolean getTestWhileIdle();
  
  public abstract long getTimeBetweenEvictionRunsMillis();
  
  public abstract boolean isClosed();
  
  public abstract Map<String, Integer> getNumActivePerKey();
  
  public abstract long getBorrowedCount();
  
  public abstract long getReturnedCount();
  
  public abstract long getCreatedCount();
  
  public abstract long getDestroyedCount();
  
  public abstract long getDestroyedByEvictorCount();
  
  public abstract long getDestroyedByBorrowValidationCount();
  
  public abstract long getMeanActiveTimeMillis();
  
  public abstract long getMeanIdleTimeMillis();
  
  public abstract long getMeanBorrowWaitTimeMillis();
  
  public abstract long getMaxBorrowWaitTimeMillis();
  
  public abstract String getCreationStackTrace();
  
  public abstract int getNumWaiters();
  
  public abstract Map<String, Integer> getNumWaitersByKey();
  
  public abstract Map<String, List<DefaultPooledObjectInfo>> listAllObjects();
}
