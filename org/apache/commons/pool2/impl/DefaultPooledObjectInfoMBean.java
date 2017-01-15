package org.apache.commons.pool2.impl;

public abstract interface DefaultPooledObjectInfoMBean
{
  public abstract long getCreateTime();
  
  public abstract String getCreateTimeFormatted();
  
  public abstract long getLastBorrowTime();
  
  public abstract String getLastBorrowTimeFormatted();
  
  public abstract String getLastBorrowTrace();
  
  public abstract long getLastReturnTime();
  
  public abstract String getLastReturnTimeFormatted();
  
  public abstract String getPooledObjectType();
  
  public abstract String getPooledObjectToString();
  
  public abstract long getBorrowedCount();
}
