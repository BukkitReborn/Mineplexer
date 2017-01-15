package org.apache.commons.pool2.proxy;

import org.apache.commons.pool2.UsageTracking;

abstract interface ProxySource<T>
{
  public abstract T createProxy(T paramT, UsageTracking<T> paramUsageTracking);
  
  public abstract T resolveProxy(T paramT);
}
