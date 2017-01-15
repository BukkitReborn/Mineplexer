package org.apache.commons.pool2.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.pool2.KeyedObjectPool;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectState;

public class GenericKeyedObjectPool<K, T>
  extends BaseGenericObjectPool<T>
  implements KeyedObjectPool<K, T>, GenericKeyedObjectPoolMXBean<K>
{
  public GenericKeyedObjectPool(KeyedPooledObjectFactory<K, T> factory)
  {
    this(factory, new GenericKeyedObjectPoolConfig());
  }
  
  public GenericKeyedObjectPool(KeyedPooledObjectFactory<K, T> factory, GenericKeyedObjectPoolConfig config)
  {
    super(config, "org.apache.commons.pool2:type=GenericKeyedObjectPool,name=", config.getJmxNamePrefix());
    if (factory == null)
    {
      jmxUnregister();
      throw new IllegalArgumentException("factory may not be null");
    }
    this.factory = factory;
    
    setConfig(config);
    
    startEvictor(getMinEvictableIdleTimeMillis());
  }
  
  public int getMaxTotalPerKey()
  {
    return this.maxTotalPerKey;
  }
  
  public void setMaxTotalPerKey(int maxTotalPerKey)
  {
    this.maxTotalPerKey = maxTotalPerKey;
  }
  
  public int getMaxIdlePerKey()
  {
    return this.maxIdlePerKey;
  }
  
  public void setMaxIdlePerKey(int maxIdlePerKey)
  {
    this.maxIdlePerKey = maxIdlePerKey;
  }
  
  public void setMinIdlePerKey(int minIdlePerKey)
  {
    this.minIdlePerKey = minIdlePerKey;
  }
  
  public int getMinIdlePerKey()
  {
    int maxIdlePerKeySave = getMaxIdlePerKey();
    if (this.minIdlePerKey > maxIdlePerKeySave) {
      return maxIdlePerKeySave;
    }
    return this.minIdlePerKey;
  }
  
  public void setConfig(GenericKeyedObjectPoolConfig conf)
  {
    setLifo(conf.getLifo());
    setMaxIdlePerKey(conf.getMaxIdlePerKey());
    setMaxTotalPerKey(conf.getMaxTotalPerKey());
    setMaxTotal(conf.getMaxTotal());
    setMinIdlePerKey(conf.getMinIdlePerKey());
    setMaxWaitMillis(conf.getMaxWaitMillis());
    setBlockWhenExhausted(conf.getBlockWhenExhausted());
    setTestOnCreate(conf.getTestOnCreate());
    setTestOnBorrow(conf.getTestOnBorrow());
    setTestOnReturn(conf.getTestOnReturn());
    setTestWhileIdle(conf.getTestWhileIdle());
    setNumTestsPerEvictionRun(conf.getNumTestsPerEvictionRun());
    setMinEvictableIdleTimeMillis(conf.getMinEvictableIdleTimeMillis());
    setSoftMinEvictableIdleTimeMillis(conf.getSoftMinEvictableIdleTimeMillis());
    
    setTimeBetweenEvictionRunsMillis(conf.getTimeBetweenEvictionRunsMillis());
    
    setEvictionPolicyClassName(conf.getEvictionPolicyClassName());
  }
  
  public KeyedPooledObjectFactory<K, T> getFactory()
  {
    return this.factory;
  }
  
  public T borrowObject(K key)
    throws Exception
  {
    return (T)borrowObject(key, getMaxWaitMillis());
  }
  
  public T borrowObject(K key, long borrowMaxWaitMillis)
    throws Exception
  {
    assertOpen();
    
    PooledObject<T> p = null;
    
    boolean blockWhenExhausted = getBlockWhenExhausted();
    
    long waitTime = 0L;
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = register(key);
    try
    {
      while (p == null)
      {
        boolean create = false;
        if (blockWhenExhausted)
        {
          p = (PooledObject)objectDeque.getIdleObjects().pollFirst();
          if (p == null)
          {
            create = true;
            p = create(key);
          }
          if (p == null) {
            if (borrowMaxWaitMillis < 0L)
            {
              p = (PooledObject)objectDeque.getIdleObjects().takeFirst();
            }
            else
            {
              waitTime = System.currentTimeMillis();
              p = (PooledObject)objectDeque.getIdleObjects().pollFirst(borrowMaxWaitMillis, TimeUnit.MILLISECONDS);
              
              waitTime = System.currentTimeMillis() - waitTime;
            }
          }
          if (p == null) {
            throw new NoSuchElementException("Timeout waiting for idle object");
          }
          if (!p.allocate()) {
            p = null;
          }
        }
        else
        {
          p = (PooledObject)objectDeque.getIdleObjects().pollFirst();
          if (p == null)
          {
            create = true;
            p = create(key);
          }
          if (p == null) {
            throw new NoSuchElementException("Pool exhausted");
          }
          if (!p.allocate()) {
            p = null;
          }
        }
        if (p != null)
        {
          try
          {
            this.factory.activateObject(key, p);
          }
          catch (Exception e)
          {
            try
            {
              destroy(key, p, true);
            }
            catch (Exception e1) {}
            p = null;
            if (create)
            {
              NoSuchElementException nsee = new NoSuchElementException("Unable to activate object");
              
              nsee.initCause(e);
              throw nsee;
            }
          }
          if ((p != null) && ((getTestOnBorrow()) || ((create) && (getTestOnCreate()))))
          {
            boolean validate = false;
            Throwable validationThrowable = null;
            try
            {
              validate = this.factory.validateObject(key, p);
            }
            catch (Throwable t)
            {
              PoolUtils.checkRethrow(t);
              validationThrowable = t;
            }
            if (!validate)
            {
              try
              {
                destroy(key, p, true);
                this.destroyedByBorrowValidationCount.incrementAndGet();
              }
              catch (Exception e) {}
              p = null;
              if (create)
              {
                NoSuchElementException nsee = new NoSuchElementException("Unable to validate object");
                
                nsee.initCause(validationThrowable);
                throw nsee;
              }
            }
          }
        }
      }
    }
    finally
    {
      deregister(key);
    }
    updateStatsBorrow(p, waitTime);
    
    return (T)p.getObject();
  }
  
  public void returnObject(K key, T obj)
  {
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (ObjectDeque)this.poolMap.get(key);
    
    PooledObject<T> p = (PooledObject)objectDeque.getAllObjects().get(obj);
    if (p == null) {
      throw new IllegalStateException("Returned object not currently part of this pool");
    }
    long activeTime = p.getActiveTimeMillis();
    if ((getTestOnReturn()) && 
      (!this.factory.validateObject(key, p)))
    {
      try
      {
        destroy(key, p, true);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
      if (objectDeque.idleObjects.hasTakeWaiters()) {
        try
        {
          addObject(key);
        }
        catch (Exception e)
        {
          swallowException(e);
        }
      }
      updateStatsReturn(activeTime);
      return;
    }
    try
    {
      this.factory.passivateObject(key, p);
    }
    catch (Exception e1)
    {
      swallowException(e1);
      try
      {
        destroy(key, p, true);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
      if (objectDeque.idleObjects.hasTakeWaiters()) {
        try
        {
          addObject(key);
        }
        catch (Exception e)
        {
          swallowException(e);
        }
      }
      updateStatsReturn(activeTime);
      return;
    }
    if (!p.deallocate()) {
      throw new IllegalStateException("Object has already been retured to this pool");
    }
    int maxIdle = getMaxIdlePerKey();
    LinkedBlockingDeque<PooledObject<T>> idleObjects = objectDeque.getIdleObjects();
    if ((isClosed()) || ((maxIdle > -1) && (maxIdle <= idleObjects.size()))) {
      try
      {
        destroy(key, p, true);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
    } else if (getLifo()) {
      idleObjects.addFirst(p);
    } else {
      idleObjects.addLast(p);
    }
    if (hasBorrowWaiters()) {
      reuseCapacity();
    }
    updateStatsReturn(activeTime);
  }
  
  public void invalidateObject(K key, T obj)
    throws Exception
  {
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (ObjectDeque)this.poolMap.get(key);
    
    PooledObject<T> p = (PooledObject)objectDeque.getAllObjects().get(obj);
    if (p == null) {
      throw new IllegalStateException("Object not currently part of this pool");
    }
    synchronized (p)
    {
      if (p.getState() != PooledObjectState.INVALID) {
        destroy(key, p, true);
      }
    }
    if (objectDeque.idleObjects.hasTakeWaiters()) {
      addObject(key);
    }
  }
  
  public void clear()
  {
    Iterator<K> iter = this.poolMap.keySet().iterator();
    while (iter.hasNext()) {
      clear(iter.next());
    }
  }
  
  public void clear(K key)
  {
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = register(key);
    try
    {
      LinkedBlockingDeque<PooledObject<T>> idleObjects = objectDeque.getIdleObjects();
      
      PooledObject<T> p = (PooledObject)idleObjects.poll();
      while (p != null)
      {
        try
        {
          destroy(key, p, true);
        }
        catch (Exception e)
        {
          swallowException(e);
        }
        p = (PooledObject)idleObjects.poll();
      }
    }
    finally
    {
      deregister(key);
    }
  }
  
  public int getNumActive()
  {
    return this.numTotal.get() - getNumIdle();
  }
  
  public int getNumIdle()
  {
    Iterator<GenericKeyedObjectPool<K, T>.ObjectDeque<T>> iter = this.poolMap.values().iterator();
    int result = 0;
    while (iter.hasNext()) {
      result += ((ObjectDeque)iter.next()).getIdleObjects().size();
    }
    return result;
  }
  
  public int getNumActive(K key)
  {
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (ObjectDeque)this.poolMap.get(key);
    if (objectDeque != null) {
      return objectDeque.getAllObjects().size() - objectDeque.getIdleObjects().size();
    }
    return 0;
  }
  
  public int getNumIdle(K key)
  {
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (ObjectDeque)this.poolMap.get(key);
    return objectDeque != null ? objectDeque.getIdleObjects().size() : 0;
  }
  
  public void close()
  {
    if (isClosed()) {
      return;
    }
    synchronized (this.closeLock)
    {
      if (isClosed()) {
        return;
      }
      startEvictor(-1L);
      
      this.closed = true;
      
      clear();
      
      jmxUnregister();
      
      Iterator<GenericKeyedObjectPool<K, T>.ObjectDeque<T>> iter = this.poolMap.values().iterator();
      while (iter.hasNext()) {
        ((ObjectDeque)iter.next()).getIdleObjects().interuptTakeWaiters();
      }
      clear();
    }
  }
  
  public void clearOldest()
  {
    Map<PooledObject<T>, K> map = new TreeMap();
    for (Iterator i$ = this.poolMap.keySet().iterator(); i$.hasNext();)
    {
      k = i$.next();
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> queue = (ObjectDeque)this.poolMap.get(k);
      if (queue != null)
      {
        LinkedBlockingDeque<PooledObject<T>> idleObjects = queue.getIdleObjects();
        for (PooledObject<T> p : idleObjects) {
          map.put(p, k);
        }
      }
    }
    K k;
    int itemsToRemove = (int)(map.size() * 0.15D) + 1;
    Iterator<Map.Entry<PooledObject<T>, K>> iter = map.entrySet().iterator();
    while ((iter.hasNext()) && (itemsToRemove > 0))
    {
      Map.Entry<PooledObject<T>, K> entry = (Map.Entry)iter.next();
      
      K key = entry.getValue();
      PooledObject<T> p = (PooledObject)entry.getKey();
      
      boolean destroyed = true;
      try
      {
        destroyed = destroy(key, p, false);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
      if (destroyed) {
        itemsToRemove--;
      }
    }
  }
  
  private void reuseCapacity()
  {
    int maxTotalPerKeySave = getMaxTotalPerKey();
    
    int maxQueueLength = 0;
    LinkedBlockingDeque<PooledObject<T>> mostLoaded = null;
    K loadedKey = null;
    for (K k : this.poolMap.keySet())
    {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> deque = (ObjectDeque)this.poolMap.get(k);
      if (deque != null)
      {
        LinkedBlockingDeque<PooledObject<T>> pool = deque.getIdleObjects();
        int queueLength = pool.getTakeQueueLength();
        if ((getNumActive(k) < maxTotalPerKeySave) && (queueLength > maxQueueLength))
        {
          maxQueueLength = queueLength;
          mostLoaded = pool;
          loadedKey = k;
        }
      }
    }
    if (mostLoaded != null)
    {
      register(loadedKey);
      try
      {
        PooledObject<T> p = create(loadedKey);
        if (p != null) {
          addIdleObject(loadedKey, p);
        }
      }
      catch (Exception e)
      {
        swallowException(e);
      }
      finally
      {
        deregister(loadedKey);
      }
    }
  }
  
  private boolean hasBorrowWaiters()
  {
    for (K k : this.poolMap.keySet())
    {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> deque = (ObjectDeque)this.poolMap.get(k);
      if (deque != null)
      {
        LinkedBlockingDeque<PooledObject<T>> pool = deque.getIdleObjects();
        if (pool.hasTakeWaiters()) {
          return true;
        }
      }
    }
    return false;
  }
  
  public void evict()
    throws Exception
  {
    assertOpen();
    if (getNumIdle() == 0) {
      return;
    }
    PooledObject<T> underTest = null;
    EvictionPolicy<T> evictionPolicy = getEvictionPolicy();
    synchronized (this.evictionLock)
    {
      EvictionConfig evictionConfig = new EvictionConfig(getMinEvictableIdleTimeMillis(), getSoftMinEvictableIdleTimeMillis(), getMinIdlePerKey());
      
      boolean testWhileIdle = getTestWhileIdle();
      
      LinkedBlockingDeque<PooledObject<T>> idleObjects = null;
      
      int i = 0;
      for (int m = getNumTests(); i < m; i++)
      {
        if ((this.evictionIterator == null) || (!this.evictionIterator.hasNext()))
        {
          if ((this.evictionKeyIterator == null) || (!this.evictionKeyIterator.hasNext()))
          {
            List<K> keyCopy = new ArrayList();
            Lock readLock = this.keyLock.readLock();
            readLock.lock();
            try
            {
              keyCopy.addAll(this.poolKeyList);
            }
            finally
            {
              readLock.unlock();
            }
            this.evictionKeyIterator = keyCopy.iterator();
          }
          while (this.evictionKeyIterator.hasNext())
          {
            this.evictionKey = this.evictionKeyIterator.next();
            GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (ObjectDeque)this.poolMap.get(this.evictionKey);
            if (objectDeque != null)
            {
              idleObjects = objectDeque.getIdleObjects();
              if (getLifo()) {
                this.evictionIterator = idleObjects.descendingIterator();
              } else {
                this.evictionIterator = idleObjects.iterator();
              }
              if (this.evictionIterator.hasNext()) {
                break;
              }
              this.evictionIterator = null;
            }
          }
        }
        if (this.evictionIterator == null) {
          return;
        }
        try
        {
          underTest = (PooledObject)this.evictionIterator.next();
        }
        catch (NoSuchElementException nsee)
        {
          i--;
          this.evictionIterator = null;
          continue;
        }
        if (!underTest.startEvictionTest())
        {
          i--;
        }
        else if (evictionPolicy.evict(evictionConfig, underTest, ((ObjectDeque)this.poolMap.get(this.evictionKey)).getIdleObjects().size()))
        {
          destroy(this.evictionKey, underTest, true);
          this.destroyedByEvictorCount.incrementAndGet();
        }
        else
        {
          if (testWhileIdle)
          {
            boolean active = false;
            try
            {
              this.factory.activateObject(this.evictionKey, underTest);
              active = true;
            }
            catch (Exception e)
            {
              destroy(this.evictionKey, underTest, true);
              this.destroyedByEvictorCount.incrementAndGet();
            }
            if (active) {
              if (!this.factory.validateObject(this.evictionKey, underTest))
              {
                destroy(this.evictionKey, underTest, true);
                this.destroyedByEvictorCount.incrementAndGet();
              }
              else
              {
                try
                {
                  this.factory.passivateObject(this.evictionKey, underTest);
                }
                catch (Exception e)
                {
                  destroy(this.evictionKey, underTest, true);
                  this.destroyedByEvictorCount.incrementAndGet();
                }
              }
            }
          }
          if (underTest.endEvictionTest(idleObjects)) {}
        }
      }
    }
  }
  
  private PooledObject<T> create(K key)
    throws Exception
  {
    int maxTotalPerKeySave = getMaxTotalPerKey();
    int maxTotal = getMaxTotal();
    
    boolean loop = true;
    while (loop)
    {
      int newNumTotal = this.numTotal.incrementAndGet();
      if ((maxTotal > -1) && (newNumTotal > maxTotal))
      {
        this.numTotal.decrementAndGet();
        if (getNumIdle() == 0) {
          return null;
        }
        clearOldest();
      }
      else
      {
        loop = false;
      }
    }
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (ObjectDeque)this.poolMap.get(key);
    long newCreateCount = objectDeque.getCreateCount().incrementAndGet();
    if (((maxTotalPerKeySave > -1) && (newCreateCount > maxTotalPerKeySave)) || (newCreateCount > 2147483647L))
    {
      this.numTotal.decrementAndGet();
      objectDeque.getCreateCount().decrementAndGet();
      return null;
    }
    PooledObject<T> p = null;
    try
    {
      p = this.factory.makeObject(key);
    }
    catch (Exception e)
    {
      this.numTotal.decrementAndGet();
      objectDeque.getCreateCount().decrementAndGet();
      throw e;
    }
    this.createdCount.incrementAndGet();
    objectDeque.getAllObjects().put(p.getObject(), p);
    return p;
  }
  
  private boolean destroy(K key, PooledObject<T> toDestroy, boolean always)
    throws Exception
  {
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = register(key);
    try
    {
      boolean isIdle = objectDeque.getIdleObjects().remove(toDestroy);
      boolean bool1;
      if ((isIdle) || (always))
      {
        objectDeque.getAllObjects().remove(toDestroy.getObject());
        toDestroy.invalidate();
        try
        {
          this.factory.destroyObject(key, toDestroy);
        }
        finally
        {
          objectDeque.getCreateCount().decrementAndGet();
          this.destroyedCount.incrementAndGet();
          this.numTotal.decrementAndGet();
        }
        return true;
      }
      return false;
    }
    finally
    {
      deregister(key);
    }
  }
  
  private GenericKeyedObjectPool<K, T>.ObjectDeque<T> register(K k)
  {
    Lock lock = this.keyLock.readLock();
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = null;
    try
    {
      lock.lock();
      objectDeque = (ObjectDeque)this.poolMap.get(k);
      if (objectDeque == null)
      {
        lock.unlock();
        lock = this.keyLock.writeLock();
        lock.lock();
        objectDeque = (ObjectDeque)this.poolMap.get(k);
        if (objectDeque == null)
        {
          objectDeque = new ObjectDeque(null);
          objectDeque.getNumInterested().incrementAndGet();
          
          this.poolMap.put(k, objectDeque);
          this.poolKeyList.add(k);
        }
        else
        {
          objectDeque.getNumInterested().incrementAndGet();
        }
      }
      else
      {
        objectDeque.getNumInterested().incrementAndGet();
      }
    }
    finally
    {
      lock.unlock();
    }
    return objectDeque;
  }
  
  private void deregister(K k)
  {
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (ObjectDeque)this.poolMap.get(k);
    long numInterested = objectDeque.getNumInterested().decrementAndGet();
    if ((numInterested == 0L) && (objectDeque.getCreateCount().get() == 0))
    {
      Lock writeLock = this.keyLock.writeLock();
      writeLock.lock();
      try
      {
        if ((objectDeque.getCreateCount().get() == 0) && (objectDeque.getNumInterested().get() == 0L))
        {
          this.poolMap.remove(k);
          this.poolKeyList.remove(k);
        }
      }
      finally
      {
        writeLock.unlock();
      }
    }
  }
  
  void ensureMinIdle()
    throws Exception
  {
    int minIdlePerKeySave = getMinIdlePerKey();
    if (minIdlePerKeySave < 1) {
      return;
    }
    for (K k : this.poolMap.keySet()) {
      ensureMinIdle(k);
    }
  }
  
  private void ensureMinIdle(K key)
    throws Exception
  {
    GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque = (ObjectDeque)this.poolMap.get(key);
    
    int deficit = calculateDeficit(objectDeque);
    for (int i = 0; (i < deficit) && (calculateDeficit(objectDeque) > 0); i++) {
      addObject(key);
    }
  }
  
  public void addObject(K key)
    throws Exception
  {
    assertOpen();
    register(key);
    try
    {
      PooledObject<T> p = create(key);
      addIdleObject(key, p);
    }
    finally
    {
      deregister(key);
    }
  }
  
  private void addIdleObject(K key, PooledObject<T> p)
    throws Exception
  {
    if (p != null)
    {
      this.factory.passivateObject(key, p);
      LinkedBlockingDeque<PooledObject<T>> idleObjects = ((ObjectDeque)this.poolMap.get(key)).getIdleObjects();
      if (getLifo()) {
        idleObjects.addFirst(p);
      } else {
        idleObjects.addLast(p);
      }
    }
  }
  
  public void preparePool(K key)
    throws Exception
  {
    int minIdlePerKeySave = getMinIdlePerKey();
    if (minIdlePerKeySave < 1) {
      return;
    }
    ensureMinIdle(key);
  }
  
  private int getNumTests()
  {
    int totalIdle = getNumIdle();
    int numTests = getNumTestsPerEvictionRun();
    if (numTests >= 0) {
      return Math.min(numTests, totalIdle);
    }
    return (int)Math.ceil(totalIdle / Math.abs(numTests));
  }
  
  private int calculateDeficit(GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDeque)
  {
    if (objectDeque == null) {
      return getMinIdlePerKey();
    }
    int maxTotal = getMaxTotal();
    int maxTotalPerKeySave = getMaxTotalPerKey();
    
    int objectDefecit = 0;
    
    objectDefecit = getMinIdlePerKey() - objectDeque.getIdleObjects().size();
    if (maxTotalPerKeySave > 0)
    {
      int growLimit = Math.max(0, maxTotalPerKeySave - objectDeque.getIdleObjects().size());
      
      objectDefecit = Math.min(objectDefecit, growLimit);
    }
    if (maxTotal > 0)
    {
      int growLimit = Math.max(0, maxTotal - getNumActive() - getNumIdle());
      objectDefecit = Math.min(objectDefecit, growLimit);
    }
    return objectDefecit;
  }
  
  public Map<String, Integer> getNumActivePerKey()
  {
    HashMap<String, Integer> result = new HashMap();
    
    Iterator<Map.Entry<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>>> iter = this.poolMap.entrySet().iterator();
    while (iter.hasNext())
    {
      Map.Entry<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>> entry = (Map.Entry)iter.next();
      if (entry != null)
      {
        K key = entry.getKey();
        GenericKeyedObjectPool<K, T>.ObjectDeque<T> objectDequeue = (ObjectDeque)entry.getValue();
        if ((key != null) && (objectDequeue != null)) {
          result.put(key.toString(), Integer.valueOf(objectDequeue.getAllObjects().size() - objectDequeue.getIdleObjects().size()));
        }
      }
    }
    return result;
  }
  
  public int getNumWaiters()
  {
    int result = 0;
    if (getBlockWhenExhausted())
    {
      Iterator<GenericKeyedObjectPool<K, T>.ObjectDeque<T>> iter = this.poolMap.values().iterator();
      while (iter.hasNext()) {
        result += ((ObjectDeque)iter.next()).getIdleObjects().getTakeQueueLength();
      }
    }
    return result;
  }
  
  public Map<String, Integer> getNumWaitersByKey()
  {
    Map<String, Integer> result = new HashMap();
    for (K key : this.poolMap.keySet())
    {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> queue = (ObjectDeque)this.poolMap.get(key);
      if (queue != null) {
        if (getBlockWhenExhausted()) {
          result.put(key.toString(), Integer.valueOf(queue.getIdleObjects().getTakeQueueLength()));
        } else {
          result.put(key.toString(), Integer.valueOf(0));
        }
      }
    }
    return result;
  }
  
  public Map<String, List<DefaultPooledObjectInfo>> listAllObjects()
  {
    Map<String, List<DefaultPooledObjectInfo>> result = new HashMap();
    for (K key : this.poolMap.keySet())
    {
      GenericKeyedObjectPool<K, T>.ObjectDeque<T> queue = (ObjectDeque)this.poolMap.get(key);
      if (queue != null)
      {
        list = new ArrayList();
        
        result.put(key.toString(), list);
        for (PooledObject<T> p : queue.getAllObjects().values()) {
          list.add(new DefaultPooledObjectInfo(p));
        }
      }
    }
    List<DefaultPooledObjectInfo> list;
    return result;
  }
  
  private class ObjectDeque<S>
  {
    private final LinkedBlockingDeque<PooledObject<S>> idleObjects = new LinkedBlockingDeque();
    private final AtomicInteger createCount = new AtomicInteger(0);
    private final Map<S, PooledObject<S>> allObjects = new ConcurrentHashMap();
    private final AtomicLong numInterested = new AtomicLong(0L);
    
    private ObjectDeque() {}
    
    public LinkedBlockingDeque<PooledObject<S>> getIdleObjects()
    {
      return this.idleObjects;
    }
    
    public AtomicInteger getCreateCount()
    {
      return this.createCount;
    }
    
    public AtomicLong getNumInterested()
    {
      return this.numInterested;
    }
    
    public Map<S, PooledObject<S>> getAllObjects()
    {
      return this.allObjects;
    }
  }
  
  private volatile int maxIdlePerKey = 8;
  private volatile int minIdlePerKey = 0;
  private volatile int maxTotalPerKey = 8;
  private final KeyedPooledObjectFactory<K, T> factory;
  private final Map<K, GenericKeyedObjectPool<K, T>.ObjectDeque<T>> poolMap = new ConcurrentHashMap();
  private final List<K> poolKeyList = new ArrayList();
  private final ReadWriteLock keyLock = new ReentrantReadWriteLock(true);
  private final AtomicInteger numTotal = new AtomicInteger(0);
  private Iterator<K> evictionKeyIterator = null;
  private K evictionKey = null;
  private static final String ONAME_BASE = "org.apache.commons.pool2:type=GenericKeyedObjectPool,name=";
}
