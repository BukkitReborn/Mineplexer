package org.apache.commons.pool2;

public abstract class BasePooledObjectFactory<T>
  implements PooledObjectFactory<T>
{
  public abstract T create()
    throws Exception;
  
  public abstract PooledObject<T> wrap(T paramT);
  
  public PooledObject<T> makeObject()
    throws Exception
  {
    return wrap(create());
  }
  
  public void destroyObject(PooledObject<T> p)
    throws Exception
  {}
  
  public boolean validateObject(PooledObject<T> p)
  {
    return true;
  }
  
  public void activateObject(PooledObject<T> p)
    throws Exception
  {}
  
  public void passivateObject(PooledObject<T> p)
    throws Exception
  {}
}
