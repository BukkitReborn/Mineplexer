package org.apache.commons.pool2;

public abstract interface KeyedPooledObjectFactory<K, V>
{
  public abstract PooledObject<V> makeObject(K paramK)
    throws Exception;
  
  public abstract void destroyObject(K paramK, PooledObject<V> paramPooledObject)
    throws Exception;
  
  public abstract boolean validateObject(K paramK, PooledObject<V> paramPooledObject);
  
  public abstract void activateObject(K paramK, PooledObject<V> paramPooledObject)
    throws Exception;
  
  public abstract void passivateObject(K paramK, PooledObject<V> paramPooledObject)
    throws Exception;
}
