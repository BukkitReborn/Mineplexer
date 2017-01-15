package org.apache.commons.pool2;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public final class PoolUtils
{
  static class TimerHolder
  {
    static final Timer MIN_IDLE_TIMER = new Timer(true);
  }
  
  public static void checkRethrow(Throwable t)
  {
    if ((t instanceof ThreadDeath)) {
      throw ((ThreadDeath)t);
    }
    if ((t instanceof VirtualMachineError)) {
      throw ((VirtualMachineError)t);
    }
  }
  
  public static <T> TimerTask checkMinIdle(ObjectPool<T> pool, int minIdle, long period)
    throws IllegalArgumentException
  {
    if (pool == null) {
      throw new IllegalArgumentException("keyedPool must not be null.");
    }
    if (minIdle < 0) {
      throw new IllegalArgumentException("minIdle must be non-negative.");
    }
    TimerTask task = new ObjectPoolMinIdleTimerTask(pool, minIdle);
    getMinIdleTimer().schedule(task, 0L, period);
    return task;
  }
  
  public static <K, V> TimerTask checkMinIdle(KeyedObjectPool<K, V> keyedPool, K key, int minIdle, long period)
    throws IllegalArgumentException
  {
    if (keyedPool == null) {
      throw new IllegalArgumentException("keyedPool must not be null.");
    }
    if (key == null) {
      throw new IllegalArgumentException("key must not be null.");
    }
    if (minIdle < 0) {
      throw new IllegalArgumentException("minIdle must be non-negative.");
    }
    TimerTask task = new KeyedObjectPoolMinIdleTimerTask(keyedPool, key, minIdle);
    
    getMinIdleTimer().schedule(task, 0L, period);
    return task;
  }
  
  public static <K, V> Map<K, TimerTask> checkMinIdle(KeyedObjectPool<K, V> keyedPool, Collection<K> keys, int minIdle, long period)
    throws IllegalArgumentException
  {
    if (keys == null) {
      throw new IllegalArgumentException("keys must not be null.");
    }
    Map<K, TimerTask> tasks = new HashMap(keys.size());
    Iterator<K> iter = keys.iterator();
    while (iter.hasNext())
    {
      K key = iter.next();
      TimerTask task = checkMinIdle(keyedPool, key, minIdle, period);
      tasks.put(key, task);
    }
    return tasks;
  }
  
  public static <T> void prefill(ObjectPool<T> pool, int count)
    throws Exception, IllegalArgumentException
  {
    if (pool == null) {
      throw new IllegalArgumentException("pool must not be null.");
    }
    for (int i = 0; i < count; i++) {
      pool.addObject();
    }
  }
  
  public static <K, V> void prefill(KeyedObjectPool<K, V> keyedPool, K key, int count)
    throws Exception, IllegalArgumentException
  {
    if (keyedPool == null) {
      throw new IllegalArgumentException("keyedPool must not be null.");
    }
    if (key == null) {
      throw new IllegalArgumentException("key must not be null.");
    }
    for (int i = 0; i < count; i++) {
      keyedPool.addObject(key);
    }
  }
  
  public static <K, V> void prefill(KeyedObjectPool<K, V> keyedPool, Collection<K> keys, int count)
    throws Exception, IllegalArgumentException
  {
    if (keys == null) {
      throw new IllegalArgumentException("keys must not be null.");
    }
    Iterator<K> iter = keys.iterator();
    while (iter.hasNext()) {
      prefill(keyedPool, iter.next(), count);
    }
  }
  
  public static <T> ObjectPool<T> synchronizedPool(ObjectPool<T> pool)
  {
    if (pool == null) {
      throw new IllegalArgumentException("pool must not be null.");
    }
    return new SynchronizedObjectPool(pool);
  }
  
  public static <K, V> KeyedObjectPool<K, V> synchronizedPool(KeyedObjectPool<K, V> keyedPool)
  {
    return new SynchronizedKeyedObjectPool(keyedPool);
  }
  
  public static <T> PooledObjectFactory<T> synchronizedPooledFactory(PooledObjectFactory<T> factory)
  {
    return new SynchronizedPooledObjectFactory(factory);
  }
  
  public static <K, V> KeyedPooledObjectFactory<K, V> synchronizedKeyedPooledFactory(KeyedPooledObjectFactory<K, V> keyedFactory)
  {
    return new SynchronizedKeyedPooledObjectFactory(keyedFactory);
  }
  
  public static <T> ObjectPool<T> erodingPool(ObjectPool<T> pool)
  {
    return erodingPool(pool, 1.0F);
  }
  
  public static <T> ObjectPool<T> erodingPool(ObjectPool<T> pool, float factor)
  {
    if (pool == null) {
      throw new IllegalArgumentException("pool must not be null.");
    }
    if (factor <= 0.0F) {
      throw new IllegalArgumentException("factor must be positive.");
    }
    return new ErodingObjectPool(pool, factor);
  }
  
  public static <K, V> KeyedObjectPool<K, V> erodingPool(KeyedObjectPool<K, V> keyedPool)
  {
    return erodingPool(keyedPool, 1.0F);
  }
  
  public static <K, V> KeyedObjectPool<K, V> erodingPool(KeyedObjectPool<K, V> keyedPool, float factor)
  {
    return erodingPool(keyedPool, factor, false);
  }
  
  public static <K, V> KeyedObjectPool<K, V> erodingPool(KeyedObjectPool<K, V> keyedPool, float factor, boolean perKey)
  {
    if (keyedPool == null) {
      throw new IllegalArgumentException("keyedPool must not be null.");
    }
    if (factor <= 0.0F) {
      throw new IllegalArgumentException("factor must be positive.");
    }
    if (perKey) {
      return new ErodingPerKeyKeyedObjectPool(keyedPool, factor);
    }
    return new ErodingKeyedObjectPool(keyedPool, factor);
  }
  
  private static Timer getMinIdleTimer()
  {
    return TimerHolder.MIN_IDLE_TIMER;
  }
  
  private static class ObjectPoolMinIdleTimerTask<T>
    extends TimerTask
  {
    private final int minIdle;
    private final ObjectPool<T> pool;
    
    ObjectPoolMinIdleTimerTask(ObjectPool<T> pool, int minIdle)
      throws IllegalArgumentException
    {
      if (pool == null) {
        throw new IllegalArgumentException("pool must not be null.");
      }
      this.pool = pool;
      this.minIdle = minIdle;
    }
    
    public void run()
    {
      boolean success = false;
      try
      {
        if (this.pool.getNumIdle() < this.minIdle) {
          this.pool.addObject();
        }
        success = true;
      }
      catch (Exception e)
      {
        cancel();
      }
      finally
      {
        if (!success) {
          cancel();
        }
      }
    }
    
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append("ObjectPoolMinIdleTimerTask");
      sb.append("{minIdle=").append(this.minIdle);
      sb.append(", pool=").append(this.pool);
      sb.append('}');
      return sb.toString();
    }
  }
  
  private static class KeyedObjectPoolMinIdleTimerTask<K, V>
    extends TimerTask
  {
    private final int minIdle;
    private final K key;
    private final KeyedObjectPool<K, V> keyedPool;
    
    KeyedObjectPoolMinIdleTimerTask(KeyedObjectPool<K, V> keyedPool, K key, int minIdle)
      throws IllegalArgumentException
    {
      if (keyedPool == null) {
        throw new IllegalArgumentException("keyedPool must not be null.");
      }
      this.keyedPool = keyedPool;
      this.key = key;
      this.minIdle = minIdle;
    }
    
    public void run()
    {
      boolean success = false;
      try
      {
        if (this.keyedPool.getNumIdle(this.key) < this.minIdle) {
          this.keyedPool.addObject(this.key);
        }
        success = true;
      }
      catch (Exception e)
      {
        cancel();
      }
      finally
      {
        if (!success) {
          cancel();
        }
      }
    }
    
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append("KeyedObjectPoolMinIdleTimerTask");
      sb.append("{minIdle=").append(this.minIdle);
      sb.append(", key=").append(this.key);
      sb.append(", keyedPool=").append(this.keyedPool);
      sb.append('}');
      return sb.toString();
    }
  }
  
  private static class SynchronizedObjectPool<T>
    implements ObjectPool<T>
  {
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final ObjectPool<T> pool;
    
    SynchronizedObjectPool(ObjectPool<T> pool)
      throws IllegalArgumentException
    {
      if (pool == null) {
        throw new IllegalArgumentException("pool must not be null.");
      }
      this.pool = pool;
    }
    
    public T borrowObject()
      throws Exception, NoSuchElementException, IllegalStateException
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        return (T)this.pool.borrowObject();
      }
      finally
      {
        writeLock.unlock();
      }
    }
    
    public void returnObject(T obj)
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.pool.returnObject(obj);
      }
      catch (Exception e) {}finally
      {
        writeLock.unlock();
      }
    }
    
    public void invalidateObject(T obj)
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.pool.invalidateObject(obj);
      }
      catch (Exception e) {}finally
      {
        writeLock.unlock();
      }
    }
    
    public void addObject()
      throws Exception, IllegalStateException, UnsupportedOperationException
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.pool.addObject();
      }
      finally
      {
        writeLock.unlock();
      }
    }
    
    public int getNumIdle()
    {
      ReentrantReadWriteLock.ReadLock readLock = this.readWriteLock.readLock();
      readLock.lock();
      try
      {
        return this.pool.getNumIdle();
      }
      finally
      {
        readLock.unlock();
      }
    }
    
    public int getNumActive()
    {
      ReentrantReadWriteLock.ReadLock readLock = this.readWriteLock.readLock();
      readLock.lock();
      try
      {
        return this.pool.getNumActive();
      }
      finally
      {
        readLock.unlock();
      }
    }
    
    public void clear()
      throws Exception, UnsupportedOperationException
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.pool.clear();
      }
      finally
      {
        writeLock.unlock();
      }
    }
    
    public void close()
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.pool.close();
      }
      catch (Exception e) {}finally
      {
        writeLock.unlock();
      }
    }
    
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append("SynchronizedObjectPool");
      sb.append("{pool=").append(this.pool);
      sb.append('}');
      return sb.toString();
    }
  }
  
  private static class SynchronizedKeyedObjectPool<K, V>
    implements KeyedObjectPool<K, V>
  {
    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final KeyedObjectPool<K, V> keyedPool;
    
    SynchronizedKeyedObjectPool(KeyedObjectPool<K, V> keyedPool)
      throws IllegalArgumentException
    {
      if (keyedPool == null) {
        throw new IllegalArgumentException("keyedPool must not be null.");
      }
      this.keyedPool = keyedPool;
    }
    
    public V borrowObject(K key)
      throws Exception, NoSuchElementException, IllegalStateException
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        return (V)this.keyedPool.borrowObject(key);
      }
      finally
      {
        writeLock.unlock();
      }
    }
    
    public void returnObject(K key, V obj)
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.keyedPool.returnObject(key, obj);
      }
      catch (Exception e) {}finally
      {
        writeLock.unlock();
      }
    }
    
    public void invalidateObject(K key, V obj)
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.keyedPool.invalidateObject(key, obj);
      }
      catch (Exception e) {}finally
      {
        writeLock.unlock();
      }
    }
    
    public void addObject(K key)
      throws Exception, IllegalStateException, UnsupportedOperationException
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.keyedPool.addObject(key);
      }
      finally
      {
        writeLock.unlock();
      }
    }
    
    public int getNumIdle(K key)
    {
      ReentrantReadWriteLock.ReadLock readLock = this.readWriteLock.readLock();
      readLock.lock();
      try
      {
        return this.keyedPool.getNumIdle(key);
      }
      finally
      {
        readLock.unlock();
      }
    }
    
    public int getNumActive(K key)
    {
      ReentrantReadWriteLock.ReadLock readLock = this.readWriteLock.readLock();
      readLock.lock();
      try
      {
        return this.keyedPool.getNumActive(key);
      }
      finally
      {
        readLock.unlock();
      }
    }
    
    public int getNumIdle()
    {
      ReentrantReadWriteLock.ReadLock readLock = this.readWriteLock.readLock();
      readLock.lock();
      try
      {
        return this.keyedPool.getNumIdle();
      }
      finally
      {
        readLock.unlock();
      }
    }
    
    public int getNumActive()
    {
      ReentrantReadWriteLock.ReadLock readLock = this.readWriteLock.readLock();
      readLock.lock();
      try
      {
        return this.keyedPool.getNumActive();
      }
      finally
      {
        readLock.unlock();
      }
    }
    
    public void clear()
      throws Exception, UnsupportedOperationException
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.keyedPool.clear();
      }
      finally
      {
        writeLock.unlock();
      }
    }
    
    public void clear(K key)
      throws Exception, UnsupportedOperationException
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.keyedPool.clear(key);
      }
      finally
      {
        writeLock.unlock();
      }
    }
    
    public void close()
    {
      ReentrantReadWriteLock.WriteLock writeLock = this.readWriteLock.writeLock();
      writeLock.lock();
      try
      {
        this.keyedPool.close();
      }
      catch (Exception e) {}finally
      {
        writeLock.unlock();
      }
    }
    
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append("SynchronizedKeyedObjectPool");
      sb.append("{keyedPool=").append(this.keyedPool);
      sb.append('}');
      return sb.toString();
    }
  }
  
  private static class SynchronizedPooledObjectFactory<T>
    implements PooledObjectFactory<T>
  {
    private final ReentrantReadWriteLock.WriteLock writeLock = new ReentrantReadWriteLock().writeLock();
    private final PooledObjectFactory<T> factory;
    
    SynchronizedPooledObjectFactory(PooledObjectFactory<T> factory)
      throws IllegalArgumentException
    {
      if (factory == null) {
        throw new IllegalArgumentException("factory must not be null.");
      }
      this.factory = factory;
    }
    
    public PooledObject<T> makeObject()
      throws Exception
    {
      this.writeLock.lock();
      try
      {
        return this.factory.makeObject();
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public void destroyObject(PooledObject<T> p)
      throws Exception
    {
      this.writeLock.lock();
      try
      {
        this.factory.destroyObject(p);
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public boolean validateObject(PooledObject<T> p)
    {
      this.writeLock.lock();
      try
      {
        return this.factory.validateObject(p);
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public void activateObject(PooledObject<T> p)
      throws Exception
    {
      this.writeLock.lock();
      try
      {
        this.factory.activateObject(p);
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public void passivateObject(PooledObject<T> p)
      throws Exception
    {
      this.writeLock.lock();
      try
      {
        this.factory.passivateObject(p);
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append("SynchronizedPoolableObjectFactory");
      sb.append("{factory=").append(this.factory);
      sb.append('}');
      return sb.toString();
    }
  }
  
  private static class SynchronizedKeyedPooledObjectFactory<K, V>
    implements KeyedPooledObjectFactory<K, V>
  {
    private final ReentrantReadWriteLock.WriteLock writeLock = new ReentrantReadWriteLock().writeLock();
    private final KeyedPooledObjectFactory<K, V> keyedFactory;
    
    SynchronizedKeyedPooledObjectFactory(KeyedPooledObjectFactory<K, V> keyedFactory)
      throws IllegalArgumentException
    {
      if (keyedFactory == null) {
        throw new IllegalArgumentException("keyedFactory must not be null.");
      }
      this.keyedFactory = keyedFactory;
    }
    
    public PooledObject<V> makeObject(K key)
      throws Exception
    {
      this.writeLock.lock();
      try
      {
        return this.keyedFactory.makeObject(key);
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public void destroyObject(K key, PooledObject<V> p)
      throws Exception
    {
      this.writeLock.lock();
      try
      {
        this.keyedFactory.destroyObject(key, p);
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public boolean validateObject(K key, PooledObject<V> p)
    {
      this.writeLock.lock();
      try
      {
        return this.keyedFactory.validateObject(key, p);
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public void activateObject(K key, PooledObject<V> p)
      throws Exception
    {
      this.writeLock.lock();
      try
      {
        this.keyedFactory.activateObject(key, p);
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public void passivateObject(K key, PooledObject<V> p)
      throws Exception
    {
      this.writeLock.lock();
      try
      {
        this.keyedFactory.passivateObject(key, p);
      }
      finally
      {
        this.writeLock.unlock();
      }
    }
    
    public String toString()
    {
      StringBuilder sb = new StringBuilder();
      sb.append("SynchronizedKeyedPoolableObjectFactory");
      sb.append("{keyedFactory=").append(this.keyedFactory);
      sb.append('}');
      return sb.toString();
    }
  }
  
  private static class ErodingFactor
  {
    private final float factor;
    private volatile transient long nextShrink;
    private volatile transient int idleHighWaterMark;
    
    public ErodingFactor(float factor)
    {
      this.factor = factor;
      this.nextShrink = (System.currentTimeMillis() + (900000.0F * factor));
      
      this.idleHighWaterMark = 1;
    }
    
    public void update(long now, int numIdle)
    {
      int idle = Math.max(0, numIdle);
      this.idleHighWaterMark = Math.max(idle, this.idleHighWaterMark);
      float maxInterval = 15.0F;
      float minutes = 15.0F + -14.0F / this.idleHighWaterMark * idle;
      
      this.nextShrink = (now + (minutes * 60000.0F * this.factor));
    }
    
    public long getNextShrink()
    {
      return this.nextShrink;
    }
    
    public String toString()
    {
      return "ErodingFactor{factor=" + this.factor + ", idleHighWaterMark=" + this.idleHighWaterMark + '}';
    }
  }
  
  private static class ErodingObjectPool<T>
    implements ObjectPool<T>
  {
    private final ObjectPool<T> pool;
    private final PoolUtils.ErodingFactor factor;
    
    public ErodingObjectPool(ObjectPool<T> pool, float factor)
    {
      this.pool = pool;
      this.factor = new PoolUtils.ErodingFactor(factor);
    }
    
    public T borrowObject()
      throws Exception, NoSuchElementException, IllegalStateException
    {
      return (T)this.pool.borrowObject();
    }
    
    public void returnObject(T obj)
    {
      boolean discard = false;
      long now = System.currentTimeMillis();
      synchronized (this.pool)
      {
        if (this.factor.getNextShrink() < now)
        {
          int numIdle = this.pool.getNumIdle();
          if (numIdle > 0) {
            discard = true;
          }
          this.factor.update(now, numIdle);
        }
      }
      try
      {
        if (discard) {
          this.pool.invalidateObject(obj);
        } else {
          this.pool.returnObject(obj);
        }
      }
      catch (Exception e) {}
    }
    
    public void invalidateObject(T obj)
    {
      try
      {
        this.pool.invalidateObject(obj);
      }
      catch (Exception e) {}
    }
    
    public void addObject()
      throws Exception, IllegalStateException, UnsupportedOperationException
    {
      this.pool.addObject();
    }
    
    public int getNumIdle()
    {
      return this.pool.getNumIdle();
    }
    
    public int getNumActive()
    {
      return this.pool.getNumActive();
    }
    
    public void clear()
      throws Exception, UnsupportedOperationException
    {
      this.pool.clear();
    }
    
    public void close()
    {
      try
      {
        this.pool.close();
      }
      catch (Exception e) {}
    }
    
    public String toString()
    {
      return "ErodingObjectPool{factor=" + this.factor + ", pool=" + this.pool + '}';
    }
  }
  
  private static class ErodingKeyedObjectPool<K, V>
    implements KeyedObjectPool<K, V>
  {
    private final KeyedObjectPool<K, V> keyedPool;
    private final PoolUtils.ErodingFactor erodingFactor;
    
    public ErodingKeyedObjectPool(KeyedObjectPool<K, V> keyedPool, float factor)
    {
      this(keyedPool, new PoolUtils.ErodingFactor(factor));
    }
    
    protected ErodingKeyedObjectPool(KeyedObjectPool<K, V> keyedPool, PoolUtils.ErodingFactor erodingFactor)
    {
      if (keyedPool == null) {
        throw new IllegalArgumentException("keyedPool must not be null.");
      }
      this.keyedPool = keyedPool;
      this.erodingFactor = erodingFactor;
    }
    
    public V borrowObject(K key)
      throws Exception, NoSuchElementException, IllegalStateException
    {
      return (V)this.keyedPool.borrowObject(key);
    }
    
    public void returnObject(K key, V obj)
      throws Exception
    {
      boolean discard = false;
      long now = System.currentTimeMillis();
      PoolUtils.ErodingFactor factor = getErodingFactor(key);
      synchronized (this.keyedPool)
      {
        if (factor.getNextShrink() < now)
        {
          int numIdle = getNumIdle(key);
          if (numIdle > 0) {
            discard = true;
          }
          factor.update(now, numIdle);
        }
      }
      try
      {
        if (discard) {
          this.keyedPool.invalidateObject(key, obj);
        } else {
          this.keyedPool.returnObject(key, obj);
        }
      }
      catch (Exception e) {}
    }
    
    protected PoolUtils.ErodingFactor getErodingFactor(K key)
    {
      return this.erodingFactor;
    }
    
    public void invalidateObject(K key, V obj)
    {
      try
      {
        this.keyedPool.invalidateObject(key, obj);
      }
      catch (Exception e) {}
    }
    
    public void addObject(K key)
      throws Exception, IllegalStateException, UnsupportedOperationException
    {
      this.keyedPool.addObject(key);
    }
    
    public int getNumIdle()
    {
      return this.keyedPool.getNumIdle();
    }
    
    public int getNumIdle(K key)
    {
      return this.keyedPool.getNumIdle(key);
    }
    
    public int getNumActive()
    {
      return this.keyedPool.getNumActive();
    }
    
    public int getNumActive(K key)
    {
      return this.keyedPool.getNumActive(key);
    }
    
    public void clear()
      throws Exception, UnsupportedOperationException
    {
      this.keyedPool.clear();
    }
    
    public void clear(K key)
      throws Exception, UnsupportedOperationException
    {
      this.keyedPool.clear(key);
    }
    
    public void close()
    {
      try
      {
        this.keyedPool.close();
      }
      catch (Exception e) {}
    }
    
    protected KeyedObjectPool<K, V> getKeyedPool()
    {
      return this.keyedPool;
    }
    
    public String toString()
    {
      return "ErodingKeyedObjectPool{factor=" + this.erodingFactor + ", keyedPool=" + this.keyedPool + '}';
    }
  }
  
  private static class ErodingPerKeyKeyedObjectPool<K, V>
    extends PoolUtils.ErodingKeyedObjectPool<K, V>
  {
    private final float factor;
    private final Map<K, PoolUtils.ErodingFactor> factors = Collections.synchronizedMap(new HashMap());
    
    public ErodingPerKeyKeyedObjectPool(KeyedObjectPool<K, V> keyedPool, float factor)
    {
      super(null);
      this.factor = factor;
    }
    
    protected PoolUtils.ErodingFactor getErodingFactor(K key)
    {
      PoolUtils.ErodingFactor eFactor = (PoolUtils.ErodingFactor)this.factors.get(key);
      if (eFactor == null)
      {
        eFactor = new PoolUtils.ErodingFactor(this.factor);
        this.factors.put(key, eFactor);
      }
      return eFactor;
    }
    
    public String toString()
    {
      return "ErodingPerKeyKeyedObjectPool{factor=" + this.factor + ", keyedPool=" + getKeyedPool() + '}';
    }
  }
}
