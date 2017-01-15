package org.apache.commons.pool2.impl;

import java.util.Set;

public abstract interface GenericObjectPoolMXBean
{
  public abstract boolean getBlockWhenExhausted();
  
  public abstract boolean getLifo();
  
  public abstract int getMaxIdle();
  
  public abstract int getMaxTotal();
  
  public abstract long getMaxWaitMillis();
  
  public abstract long getMinEvictableIdleTimeMillis();
  
  public abstract int getMinIdle();
  
  public abstract int getNumActive();
  
  public abstract int getNumIdle();
  
  public abstract int getNumTestsPerEvictionRun();
  
  public abstract boolean getTestOnCreate();
  
  public abstract boolean getTestOnBorrow();
  
  public abstract boolean getTestOnReturn();
  
  public abstract boolean getTestWhileIdle();
  
  public abstract long getTimeBetweenEvictionRunsMillis();
  
  public abstract boolean isClosed();
  
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
  
  public abstract boolean isAbandonedConfig();
  
  public abstract boolean getLogAbandoned();
  
  public abstract boolean getRemoveAbandonedOnBorrow();
  
  public abstract boolean getRemoveAbandonedOnMaintenance();
  
  public abstract int getRemoveAbandonedTimeout();
  
  public abstract String getFactoryType();
  
  public abstract Set<DefaultPooledObjectInfo> listAllObjects();
}
