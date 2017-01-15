package org.apache.commons.pool2.impl;

public class EvictionConfig
{
  private final long idleEvictTime;
  private final long idleSoftEvictTime;
  private final int minIdle;
  
  public EvictionConfig(long poolIdleEvictTime, long poolIdleSoftEvictTime, int minIdle)
  {
    if (poolIdleEvictTime > 0L) {
      this.idleEvictTime = poolIdleEvictTime;
    } else {
      this.idleEvictTime = Long.MAX_VALUE;
    }
    if (poolIdleSoftEvictTime > 0L) {
      this.idleSoftEvictTime = poolIdleSoftEvictTime;
    } else {
      this.idleSoftEvictTime = Long.MAX_VALUE;
    }
    this.minIdle = minIdle;
  }
  
  public long getIdleEvictTime()
  {
    return this.idleEvictTime;
  }
  
  public long getIdleSoftEvictTime()
  {
    return this.idleSoftEvictTime;
  }
  
  public int getMinIdle()
  {
    return this.minIdle;
  }
}
