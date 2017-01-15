package org.apache.commons.pool2;

import java.util.NoSuchElementException;

public abstract interface KeyedObjectPool<K, V>
{
  public abstract V borrowObject(K paramK)
    throws Exception, NoSuchElementException, IllegalStateException;
  
  public abstract void returnObject(K paramK, V paramV)
    throws Exception;
  
  public abstract void invalidateObject(K paramK, V paramV)
    throws Exception;
  
  public abstract void addObject(K paramK)
    throws Exception, IllegalStateException, UnsupportedOperationException;
  
  public abstract int getNumIdle(K paramK);
  
  public abstract int getNumActive(K paramK);
  
  public abstract int getNumIdle();
  
  public abstract int getNumActive();
  
  public abstract void clear()
    throws Exception, UnsupportedOperationException;
  
  public abstract void clear(K paramK)
    throws Exception, UnsupportedOperationException;
  
  public abstract void close();
}
