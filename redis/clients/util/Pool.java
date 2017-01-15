package redis.clients.util;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;

public abstract class Pool<T>
{
  protected GenericObjectPool<T> internalPool;
  
  public Pool() {}
  
  public Pool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory)
  {
    initPool(poolConfig, factory);
  }
  
  public void initPool(GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory)
  {
    if (this.internalPool != null) {
      try
      {
        closeInternalPool();
      }
      catch (Exception e) {}
    }
    this.internalPool = new GenericObjectPool(factory, poolConfig);
  }
  
  public T getResource()
  {
    try
    {
      return (T)this.internalPool.borrowObject();
    }
    catch (Exception e)
    {
      throw new JedisConnectionException("Could not get a resource from the pool", e);
    }
  }
  
  public void returnResourceObject(T resource)
  {
    if (resource == null) {
      return;
    }
    try
    {
      this.internalPool.returnObject(resource);
    }
    catch (Exception e)
    {
      throw new JedisException("Could not return the resource to the pool", e);
    }
  }
  
  public void returnBrokenResource(T resource)
  {
    if (resource != null) {
      returnBrokenResourceObject(resource);
    }
  }
  
  public void returnResource(T resource)
  {
    if (resource != null) {
      returnResourceObject(resource);
    }
  }
  
  public void destroy()
  {
    closeInternalPool();
  }
  
  protected void returnBrokenResourceObject(T resource)
  {
    try
    {
      this.internalPool.invalidateObject(resource);
    }
    catch (Exception e)
    {
      throw new JedisException("Could not return the resource to the pool", e);
    }
  }
  
  protected void closeInternalPool()
  {
    try
    {
      this.internalPool.close();
    }
    catch (Exception e)
    {
      throw new JedisException("Could not destroy the pool", e);
    }
  }
}
