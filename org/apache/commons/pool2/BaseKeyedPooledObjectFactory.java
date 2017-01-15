package org.apache.commons.pool2;

public abstract class BaseKeyedPooledObjectFactory<K, V>
  implements KeyedPooledObjectFactory<K, V>
{
  public abstract V create(K paramK)
    throws Exception;
  
  public abstract PooledObject<V> wrap(V paramV);
  
  public PooledObject<V> makeObject(K key)
    throws Exception
  {
    return wrap(create(key));
  }
  
  public void destroyObject(K key, PooledObject<V> p)
    throws Exception
  {}
  
  public boolean validateObject(K key, PooledObject<V> p)
  {
    return true;
  }
  
  public void activateObject(K key, PooledObject<V> p)
    throws Exception
  {}
  
  public void passivateObject(K key, PooledObject<V> p)
    throws Exception
  {}
}
