package org.apache.commons.pool2;

public abstract interface PooledObjectFactory<T>
{
  public abstract PooledObject<T> makeObject()
    throws Exception;
  
  public abstract void destroyObject(PooledObject<T> paramPooledObject)
    throws Exception;
  
  public abstract boolean validateObject(PooledObject<T> paramPooledObject);
  
  public abstract void activateObject(PooledObject<T> paramPooledObject)
    throws Exception;
  
  public abstract void passivateObject(PooledObject<T> paramPooledObject)
    throws Exception;
}
