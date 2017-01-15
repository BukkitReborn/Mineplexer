package org.apache.commons.pool2.impl;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.pool2.BaseObjectPool;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;

public class SoftReferenceObjectPool<T>
  extends BaseObjectPool<T>
{
  private final PooledObjectFactory<T> factory;
  private final ReferenceQueue<T> refQueue = new ReferenceQueue();
  private int numActive = 0;
  private long destroyCount = 0L;
  private long createCount = 0L;
  private final LinkedBlockingDeque<PooledSoftReference<T>> idleReferences = new LinkedBlockingDeque();
  private final ArrayList<PooledSoftReference<T>> allReferences = new ArrayList();
  
  public SoftReferenceObjectPool(PooledObjectFactory<T> factory)
  {
    this.factory = factory;
  }
  
  public synchronized T borrowObject()
    throws Exception
  {
    assertOpen();
    T obj = null;
    boolean newlyCreated = false;
    PooledSoftReference<T> ref = null;
    while (null == obj)
    {
      if (this.idleReferences.isEmpty())
      {
        if (null == this.factory) {
          throw new NoSuchElementException();
        }
        newlyCreated = true;
        obj = this.factory.makeObject().getObject();
        this.createCount += 1L;
        
        ref = new PooledSoftReference(new SoftReference(obj));
        this.allReferences.add(ref);
      }
      else
      {
        ref = (PooledSoftReference)this.idleReferences.pollFirst();
        obj = ref.getObject();
        
        ref.getReference().clear();
        ref.setReference(new SoftReference(obj));
      }
      if ((null != this.factory) && (null != obj)) {
        try
        {
          this.factory.activateObject(ref);
          if (!this.factory.validateObject(ref)) {
            throw new Exception("ValidateObject failed");
          }
        }
        catch (Throwable t)
        {
          PoolUtils.checkRethrow(t);
          try
          {
            destroy(ref);
          }
          catch (Throwable t2)
          {
            PoolUtils.checkRethrow(t2);
          }
          finally
          {
            obj = null;
          }
          if (newlyCreated) {
            throw new NoSuchElementException("Could not create a validated object, cause: " + t.getMessage());
          }
        }
      }
    }
    this.numActive += 1;
    ref.allocate();
    return obj;
  }
  
  public synchronized void returnObject(T obj)
    throws Exception
  {
    boolean success = !isClosed();
    PooledSoftReference<T> ref = findReference(obj);
    if (ref == null) {
      throw new IllegalStateException("Returned object not currently part of this pool");
    }
    if (this.factory != null) {
      if (!this.factory.validateObject(ref)) {
        success = false;
      } else {
        try
        {
          this.factory.passivateObject(ref);
        }
        catch (Exception e)
        {
          success = false;
        }
      }
    }
    boolean shouldDestroy = !success;
    this.numActive -= 1;
    if (success)
    {
      ref.deallocate();
      this.idleReferences.add(ref);
    }
    notifyAll();
    if ((shouldDestroy) && (this.factory != null)) {
      try
      {
        destroy(ref);
      }
      catch (Exception e) {}
    }
  }
  
  public synchronized void invalidateObject(T obj)
    throws Exception
  {
    PooledSoftReference<T> ref = findReference(obj);
    if (ref == null) {
      throw new IllegalStateException("Object to invalidate is not currently part of this pool");
    }
    if (this.factory != null) {
      destroy(ref);
    }
    this.numActive -= 1;
    notifyAll();
  }
  
  public synchronized void addObject()
    throws Exception
  {
    assertOpen();
    if (this.factory == null) {
      throw new IllegalStateException("Cannot add objects without a factory.");
    }
    T obj = this.factory.makeObject().getObject();
    this.createCount += 1L;
    
    PooledSoftReference<T> ref = new PooledSoftReference(new SoftReference(obj, this.refQueue));
    
    this.allReferences.add(ref);
    
    boolean success = true;
    if (!this.factory.validateObject(ref)) {
      success = false;
    } else {
      this.factory.passivateObject(ref);
    }
    boolean shouldDestroy = !success;
    if (success)
    {
      this.idleReferences.add(ref);
      notifyAll();
    }
    if (shouldDestroy) {
      try
      {
        destroy(ref);
      }
      catch (Exception e) {}
    }
  }
  
  public synchronized int getNumIdle()
  {
    pruneClearedReferences();
    return this.idleReferences.size();
  }
  
  public synchronized int getNumActive()
  {
    return this.numActive;
  }
  
  public synchronized void clear()
  {
    if (null != this.factory)
    {
      Iterator<PooledSoftReference<T>> iter = this.idleReferences.iterator();
      while (iter.hasNext()) {
        try
        {
          PooledSoftReference<T> ref = (PooledSoftReference)iter.next();
          if (null != ref.getObject()) {
            this.factory.destroyObject(ref);
          }
        }
        catch (Exception e) {}
      }
    }
    this.idleReferences.clear();
    pruneClearedReferences();
  }
  
  public void close()
  {
    super.close();
    clear();
  }
  
  public synchronized PooledObjectFactory<T> getFactory()
  {
    return this.factory;
  }
  
  private void pruneClearedReferences()
  {
    removeClearedReferences(this.idleReferences.iterator());
    removeClearedReferences(this.allReferences.iterator());
    while (this.refQueue.poll() != null) {}
  }
  
  private PooledSoftReference<T> findReference(T obj)
  {
    Iterator<PooledSoftReference<T>> iterator = this.allReferences.iterator();
    while (iterator.hasNext())
    {
      PooledSoftReference<T> reference = (PooledSoftReference)iterator.next();
      if ((reference.getObject() != null) && (reference.getObject().equals(obj))) {
        return reference;
      }
    }
    return null;
  }
  
  private void destroy(PooledSoftReference<T> toDestroy)
    throws Exception
  {
    toDestroy.invalidate();
    this.idleReferences.remove(toDestroy);
    this.allReferences.remove(toDestroy);
    try
    {
      this.factory.destroyObject(toDestroy);
    }
    finally
    {
      this.destroyCount += 1L;
      toDestroy.getReference().clear();
    }
  }
  
  private void removeClearedReferences(Iterator<PooledSoftReference<T>> iterator)
  {
    while (iterator.hasNext())
    {
      PooledSoftReference<T> ref = (PooledSoftReference)iterator.next();
      if ((ref.getReference() == null) || (ref.getReference().isEnqueued())) {
        iterator.remove();
      }
    }
  }
}
