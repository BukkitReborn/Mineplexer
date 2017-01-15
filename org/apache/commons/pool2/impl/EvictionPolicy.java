package org.apache.commons.pool2.impl;

import org.apache.commons.pool2.PooledObject;

public abstract interface EvictionPolicy<T>
{
  public abstract boolean evict(EvictionConfig paramEvictionConfig, PooledObject<T> paramPooledObject, int paramInt);
}
