package org.apache.commons.pool2.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PoolUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.PooledObjectState;
import org.apache.commons.pool2.UsageTracking;

public class GenericObjectPool<T>
  extends BaseGenericObjectPool<T>
  implements ObjectPool<T>, GenericObjectPoolMXBean, UsageTracking<T>
{
  public GenericObjectPool(PooledObjectFactory<T> factory)
  {
    this(factory, new GenericObjectPoolConfig());
  }
  
  public GenericObjectPool(PooledObjectFactory<T> factory, GenericObjectPoolConfig config)
  {
    super(config, "org.apache.commons.pool2:type=GenericObjectPool,name=", config.getJmxNamePrefix());
    if (factory == null)
    {
      jmxUnregister();
      throw new IllegalArgumentException("factory may not be null");
    }
    this.factory = factory;
    
    setConfig(config);
    
    startEvictor(getTimeBetweenEvictionRunsMillis());
  }
  
  public GenericObjectPool(PooledObjectFactory<T> factory, GenericObjectPoolConfig config, AbandonedConfig abandonedConfig)
  {
    this(factory, config);
    setAbandonedConfig(abandonedConfig);
  }
  
  public int getMaxIdle()
  {
    return this.maxIdle;
  }
  
  public void setMaxIdle(int maxIdle)
  {
    this.maxIdle = maxIdle;
  }
  
  public void setMinIdle(int minIdle)
  {
    this.minIdle = minIdle;
  }
  
  public int getMinIdle()
  {
    int maxIdleSave = getMaxIdle();
    if (this.minIdle > maxIdleSave) {
      return maxIdleSave;
    }
    return this.minIdle;
  }
  
  public boolean isAbandonedConfig()
  {
    return this.abandonedConfig != null;
  }
  
  public boolean getLogAbandoned()
  {
    AbandonedConfig ac = this.abandonedConfig;
    return (ac != null) && (ac.getLogAbandoned());
  }
  
  public boolean getRemoveAbandonedOnBorrow()
  {
    AbandonedConfig ac = this.abandonedConfig;
    return (ac != null) && (ac.getRemoveAbandonedOnBorrow());
  }
  
  public boolean getRemoveAbandonedOnMaintenance()
  {
    AbandonedConfig ac = this.abandonedConfig;
    return (ac != null) && (ac.getRemoveAbandonedOnMaintenance());
  }
  
  public int getRemoveAbandonedTimeout()
  {
    AbandonedConfig ac = this.abandonedConfig;
    return ac != null ? ac.getRemoveAbandonedTimeout() : Integer.MAX_VALUE;
  }
  
  public void setConfig(GenericObjectPoolConfig conf)
  {
    setLifo(conf.getLifo());
    setMaxIdle(conf.getMaxIdle());
    setMinIdle(conf.getMinIdle());
    setMaxTotal(conf.getMaxTotal());
    setMaxWaitMillis(conf.getMaxWaitMillis());
    setBlockWhenExhausted(conf.getBlockWhenExhausted());
    setTestOnCreate(conf.getTestOnCreate());
    setTestOnBorrow(conf.getTestOnBorrow());
    setTestOnReturn(conf.getTestOnReturn());
    setTestWhileIdle(conf.getTestWhileIdle());
    setNumTestsPerEvictionRun(conf.getNumTestsPerEvictionRun());
    setMinEvictableIdleTimeMillis(conf.getMinEvictableIdleTimeMillis());
    setTimeBetweenEvictionRunsMillis(conf.getTimeBetweenEvictionRunsMillis());
    
    setSoftMinEvictableIdleTimeMillis(conf.getSoftMinEvictableIdleTimeMillis());
    
    setEvictionPolicyClassName(conf.getEvictionPolicyClassName());
  }
  
  public void setAbandonedConfig(AbandonedConfig abandonedConfig)
    throws IllegalArgumentException
  {
    if (abandonedConfig == null)
    {
      this.abandonedConfig = null;
    }
    else
    {
      this.abandonedConfig = new AbandonedConfig();
      this.abandonedConfig.setLogAbandoned(abandonedConfig.getLogAbandoned());
      this.abandonedConfig.setLogWriter(abandonedConfig.getLogWriter());
      this.abandonedConfig.setRemoveAbandonedOnBorrow(abandonedConfig.getRemoveAbandonedOnBorrow());
      this.abandonedConfig.setRemoveAbandonedOnMaintenance(abandonedConfig.getRemoveAbandonedOnMaintenance());
      this.abandonedConfig.setRemoveAbandonedTimeout(abandonedConfig.getRemoveAbandonedTimeout());
      this.abandonedConfig.setUseUsageTracking(abandonedConfig.getUseUsageTracking());
    }
  }
  
  public PooledObjectFactory<T> getFactory()
  {
    return this.factory;
  }
  
  public T borrowObject()
    throws Exception
  {
    return (T)borrowObject(getMaxWaitMillis());
  }
  
  public T borrowObject(long borrowMaxWaitMillis)
    throws Exception
  {
    assertOpen();
    
    AbandonedConfig ac = this.abandonedConfig;
    if ((ac != null) && (ac.getRemoveAbandonedOnBorrow()) && (getNumIdle() < 2) && (getNumActive() > getMaxTotal() - 3)) {
      removeAbandoned(ac);
    }
    PooledObject<T> p = null;
    
    boolean blockWhenExhausted = getBlockWhenExhausted();
    
    long waitTime = 0L;
    while (p == null)
    {
      boolean create = false;
      if (blockWhenExhausted)
      {
        p = (PooledObject)this.idleObjects.pollFirst();
        if (p == null)
        {
          create = true;
          p = create();
        }
        if (p == null) {
          if (borrowMaxWaitMillis < 0L)
          {
            p = (PooledObject)this.idleObjects.takeFirst();
          }
          else
          {
            waitTime = System.currentTimeMillis();
            p = (PooledObject)this.idleObjects.pollFirst(borrowMaxWaitMillis, TimeUnit.MILLISECONDS);
            
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
        p = (PooledObject)this.idleObjects.pollFirst();
        if (p == null)
        {
          create = true;
          p = create();
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
          this.factory.activateObject(p);
        }
        catch (Exception e)
        {
          try
          {
            destroy(p);
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
            validate = this.factory.validateObject(p);
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
              destroy(p);
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
    updateStatsBorrow(p, waitTime);
    
    return (T)p.getObject();
  }
  
  public void returnObject(T obj)
  {
    PooledObject<T> p = (PooledObject)this.allObjects.get(obj);
    if (!isAbandonedConfig())
    {
      if (p == null) {
        throw new IllegalStateException("Returned object not currently part of this pool");
      }
    }
    else
    {
      if (p == null) {
        return;
      }
      synchronized (p)
      {
        PooledObjectState state = p.getState();
        if (state != PooledObjectState.ALLOCATED) {
          throw new IllegalStateException("Object has already been retured to this pool or is invalid");
        }
        p.markReturning();
      }
    }
    long activeTime = p.getActiveTimeMillis();
    if ((getTestOnReturn()) && 
      (!this.factory.validateObject(p)))
    {
      try
      {
        destroy(p);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
      try
      {
        ensureIdle(1, false);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
      updateStatsReturn(activeTime);
      return;
    }
    try
    {
      this.factory.passivateObject(p);
    }
    catch (Exception e1)
    {
      swallowException(e1);
      try
      {
        destroy(p);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
      try
      {
        ensureIdle(1, false);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
      updateStatsReturn(activeTime);
      return;
    }
    if (!p.deallocate()) {
      throw new IllegalStateException("Object has already been retured to this pool or is invalid");
    }
    int maxIdleSave = getMaxIdle();
    if ((isClosed()) || ((maxIdleSave > -1) && (maxIdleSave <= this.idleObjects.size()))) {
      try
      {
        destroy(p);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
    } else if (getLifo()) {
      this.idleObjects.addFirst(p);
    } else {
      this.idleObjects.addLast(p);
    }
    updateStatsReturn(activeTime);
  }
  
  public void invalidateObject(T obj)
    throws Exception
  {
    PooledObject<T> p = (PooledObject)this.allObjects.get(obj);
    if (p == null)
    {
      if (isAbandonedConfig()) {
        return;
      }
      throw new IllegalStateException("Invalidated object not currently part of this pool");
    }
    synchronized (p)
    {
      if (p.getState() != PooledObjectState.INVALID) {
        destroy(p);
      }
    }
    ensureIdle(1, false);
  }
  
  public void clear()
  {
    PooledObject<T> p = (PooledObject)this.idleObjects.poll();
    while (p != null)
    {
      try
      {
        destroy(p);
      }
      catch (Exception e)
      {
        swallowException(e);
      }
      p = (PooledObject)this.idleObjects.poll();
    }
  }
  
  public int getNumActive()
  {
    return this.allObjects.size() - this.idleObjects.size();
  }
  
  public int getNumIdle()
  {
    return this.idleObjects.size();
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
      
      this.idleObjects.interuptTakeWaiters();
    }
  }
  
  public void evict()
    throws Exception
  {
    assertOpen();
    if (this.idleObjects.size() > 0)
    {
      PooledObject<T> underTest = null;
      EvictionPolicy<T> evictionPolicy = getEvictionPolicy();
      synchronized (this.evictionLock)
      {
        EvictionConfig evictionConfig = new EvictionConfig(getMinEvictableIdleTimeMillis(), getSoftMinEvictableIdleTimeMillis(), getMinIdle());
        
        boolean testWhileIdle = getTestWhileIdle();
        
        int i = 0;
        for (int m = getNumTests(); i < m; i++)
        {
          if ((this.evictionIterator == null) || (!this.evictionIterator.hasNext())) {
            if (getLifo()) {
              this.evictionIterator = this.idleObjects.descendingIterator();
            } else {
              this.evictionIterator = this.idleObjects.iterator();
            }
          }
          if (!this.evictionIterator.hasNext()) {
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
          else if (evictionPolicy.evict(evictionConfig, underTest, this.idleObjects.size()))
          {
            destroy(underTest);
            this.destroyedByEvictorCount.incrementAndGet();
          }
          else
          {
            if (testWhileIdle)
            {
              boolean active = false;
              try
              {
                this.factory.activateObject(underTest);
                active = true;
              }
              catch (Exception e)
              {
                destroy(underTest);
                this.destroyedByEvictorCount.incrementAndGet();
              }
              if (active) {
                if (!this.factory.validateObject(underTest))
                {
                  destroy(underTest);
                  this.destroyedByEvictorCount.incrementAndGet();
                }
                else
                {
                  try
                  {
                    this.factory.passivateObject(underTest);
                  }
                  catch (Exception e)
                  {
                    destroy(underTest);
                    this.destroyedByEvictorCount.incrementAndGet();
                  }
                }
              }
            }
            if (underTest.endEvictionTest(this.idleObjects)) {}
          }
        }
      }
    }
    AbandonedConfig ac = this.abandonedConfig;
    if ((ac != null) && (ac.getRemoveAbandonedOnMaintenance())) {
      removeAbandoned(ac);
    }
  }
  
  private PooledObject<T> create()
    throws Exception
  {
    int localMaxTotal = getMaxTotal();
    long newCreateCount = this.createCount.incrementAndGet();
    if (((localMaxTotal > -1) && (newCreateCount > localMaxTotal)) || (newCreateCount > 2147483647L))
    {
      this.createCount.decrementAndGet();
      return null;
    }
    PooledObject<T> p;
    try
    {
      p = this.factory.makeObject();
    }
    catch (Exception e)
    {
      this.createCount.decrementAndGet();
      throw e;
    }
    AbandonedConfig ac = this.abandonedConfig;
    if ((ac != null) && (ac.getLogAbandoned())) {
      p.setLogAbandoned(true);
    }
    this.createdCount.incrementAndGet();
    this.allObjects.put(p.getObject(), p);
    return p;
  }
  
  private void destroy(PooledObject<T> toDestory)
    throws Exception
  {
    toDestory.invalidate();
    this.idleObjects.remove(toDestory);
    this.allObjects.remove(toDestory.getObject());
    try
    {
      this.factory.destroyObject(toDestory);
    }
    finally
    {
      this.destroyedCount.incrementAndGet();
      this.createCount.decrementAndGet();
    }
  }
  
  void ensureMinIdle()
    throws Exception
  {
    ensureIdle(getMinIdle(), true);
  }
  
  private void ensureIdle(int idleCount, boolean always)
    throws Exception
  {
    if ((idleCount < 1) || (isClosed()) || ((!always) && (!this.idleObjects.hasTakeWaiters()))) {
      return;
    }
    while (this.idleObjects.size() < idleCount)
    {
      PooledObject<T> p = create();
      if (p == null) {
        break;
      }
      if (getLifo()) {
        this.idleObjects.addFirst(p);
      } else {
        this.idleObjects.addLast(p);
      }
    }
  }
  
  public void addObject()
    throws Exception
  {
    assertOpen();
    if (this.factory == null) {
      throw new IllegalStateException("Cannot add objects without a factory.");
    }
    PooledObject<T> p = create();
    addIdleObject(p);
  }
  
  private void addIdleObject(PooledObject<T> p)
    throws Exception
  {
    if (p != null)
    {
      this.factory.passivateObject(p);
      if (getLifo()) {
        this.idleObjects.addFirst(p);
      } else {
        this.idleObjects.addLast(p);
      }
    }
  }
  
  private int getNumTests()
  {
    int numTestsPerEvictionRun = getNumTestsPerEvictionRun();
    if (numTestsPerEvictionRun >= 0) {
      return Math.min(numTestsPerEvictionRun, this.idleObjects.size());
    }
    return (int)Math.ceil(this.idleObjects.size() / Math.abs(numTestsPerEvictionRun));
  }
  
  private void removeAbandoned(AbandonedConfig ac)
  {
    long now = System.currentTimeMillis();
    long timeout = now - ac.getRemoveAbandonedTimeout() * 1000L;
    
    ArrayList<PooledObject<T>> remove = new ArrayList();
    Iterator<PooledObject<T>> it = this.allObjects.values().iterator();
    while (it.hasNext())
    {
      PooledObject<T> pooledObject = (PooledObject)it.next();
      synchronized (pooledObject)
      {
        if ((pooledObject.getState() == PooledObjectState.ALLOCATED) && (pooledObject.getLastUsedTime() <= timeout))
        {
          pooledObject.markAbandoned();
          remove.add(pooledObject);
        }
      }
    }
    Iterator<PooledObject<T>> itr = remove.iterator();
    while (itr.hasNext())
    {
      PooledObject<T> pooledObject = (PooledObject)itr.next();
      if (ac.getLogAbandoned()) {
        pooledObject.printStackTrace(ac.getLogWriter());
      }
      try
      {
        invalidateObject(pooledObject.getObject());
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void use(T pooledObject)
  {
    AbandonedConfig ac = this.abandonedConfig;
    if ((ac != null) && (ac.getUseUsageTracking()))
    {
      PooledObject<T> wrapper = (PooledObject)this.allObjects.get(pooledObject);
      wrapper.use();
    }
  }
  
  private volatile String factoryType = null;
  
  public int getNumWaiters()
  {
    if (getBlockWhenExhausted()) {
      return this.idleObjects.getTakeQueueLength();
    }
    return 0;
  }
  
  public String getFactoryType()
  {
    if (this.factoryType == null)
    {
      StringBuilder result = new StringBuilder();
      result.append(this.factory.getClass().getName());
      result.append('<');
      Class<?> pooledObjectType = PoolImplUtils.getFactoryType(this.factory.getClass());
      
      result.append(pooledObjectType.getName());
      result.append('>');
      this.factoryType = result.toString();
    }
    return this.factoryType;
  }
  
  public Set<DefaultPooledObjectInfo> listAllObjects()
  {
    Set<DefaultPooledObjectInfo> result = new HashSet(this.allObjects.size());
    for (PooledObject<T> p : this.allObjects.values()) {
      result.add(new DefaultPooledObjectInfo(p));
    }
    return result;
  }
  
  private volatile int maxIdle = 8;
  private volatile int minIdle = 0;
  private final PooledObjectFactory<T> factory;
  private final Map<T, PooledObject<T>> allObjects = new ConcurrentHashMap();
  private final AtomicLong createCount = new AtomicLong(0L);
  private final LinkedBlockingDeque<PooledObject<T>> idleObjects = new LinkedBlockingDeque();
  private static final String ONAME_BASE = "org.apache.commons.pool2:type=GenericObjectPool,name=";
  private volatile AbandonedConfig abandonedConfig = null;
}
