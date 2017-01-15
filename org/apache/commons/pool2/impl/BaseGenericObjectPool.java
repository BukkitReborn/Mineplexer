package org.apache.commons.pool2.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicLong;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.SwallowedExceptionListener;

public abstract class BaseGenericObjectPool<T>
{
  public static final int MEAN_TIMING_STATS_CACHE_SIZE = 100;
  private volatile int maxTotal = -1;
  private volatile boolean blockWhenExhausted = true;
  private volatile long maxWaitMillis = -1L;
  private volatile boolean lifo = true;
  private volatile boolean testOnCreate = false;
  private volatile boolean testOnBorrow = false;
  private volatile boolean testOnReturn = false;
  private volatile boolean testWhileIdle = false;
  private volatile long timeBetweenEvictionRunsMillis = -1L;
  private volatile int numTestsPerEvictionRun = 3;
  private volatile long minEvictableIdleTimeMillis = 1800000L;
  private volatile long softMinEvictableIdleTimeMillis = -1L;
  private volatile EvictionPolicy<T> evictionPolicy;
  final Object closeLock = new Object();
  volatile boolean closed = false;
  final Object evictionLock = new Object();
  private BaseGenericObjectPool<T>.Evictor evictor = null;
  Iterator<PooledObject<T>> evictionIterator = null;
  private final ClassLoader factoryClassLoader;
  private final ObjectName oname;
  private final String creationStackTrace;
  private final AtomicLong borrowedCount = new AtomicLong(0L);
  private final AtomicLong returnedCount = new AtomicLong(0L);
  final AtomicLong createdCount = new AtomicLong(0L);
  final AtomicLong destroyedCount = new AtomicLong(0L);
  final AtomicLong destroyedByEvictorCount = new AtomicLong(0L);
  final AtomicLong destroyedByBorrowValidationCount = new AtomicLong(0L);
  private final LinkedList<Long> activeTimes = new LinkedList();
  private final LinkedList<Long> idleTimes = new LinkedList();
  private final LinkedList<Long> waitTimes = new LinkedList();
  private final Object maxBorrowWaitTimeMillisLock = new Object();
  private volatile long maxBorrowWaitTimeMillis = 0L;
  private SwallowedExceptionListener swallowedExceptionListener = null;
  
  public BaseGenericObjectPool(BaseObjectPoolConfig config, String jmxNameBase, String jmxNamePrefix)
  {
    if (config.getJmxEnabled()) {
      this.oname = jmxRegister(config, jmxNameBase, jmxNamePrefix);
    } else {
      this.oname = null;
    }
    this.creationStackTrace = getStackTrace(new Exception());
    
    this.factoryClassLoader = Thread.currentThread().getContextClassLoader();
    
    initStats();
  }
  
  public final int getMaxTotal()
  {
    return this.maxTotal;
  }
  
  public final void setMaxTotal(int maxTotal)
  {
    this.maxTotal = maxTotal;
  }
  
  public final boolean getBlockWhenExhausted()
  {
    return this.blockWhenExhausted;
  }
  
  public final void setBlockWhenExhausted(boolean blockWhenExhausted)
  {
    this.blockWhenExhausted = blockWhenExhausted;
  }
  
  public final long getMaxWaitMillis()
  {
    return this.maxWaitMillis;
  }
  
  public final void setMaxWaitMillis(long maxWaitMillis)
  {
    this.maxWaitMillis = maxWaitMillis;
  }
  
  public final boolean getLifo()
  {
    return this.lifo;
  }
  
  public final void setLifo(boolean lifo)
  {
    this.lifo = lifo;
  }
  
  public final boolean getTestOnCreate()
  {
    return this.testOnCreate;
  }
  
  public final void setTestOnCreate(boolean testOnCreate)
  {
    this.testOnCreate = testOnCreate;
  }
  
  public final boolean getTestOnBorrow()
  {
    return this.testOnBorrow;
  }
  
  public final void setTestOnBorrow(boolean testOnBorrow)
  {
    this.testOnBorrow = testOnBorrow;
  }
  
  public final boolean getTestOnReturn()
  {
    return this.testOnReturn;
  }
  
  public final void setTestOnReturn(boolean testOnReturn)
  {
    this.testOnReturn = testOnReturn;
  }
  
  public final boolean getTestWhileIdle()
  {
    return this.testWhileIdle;
  }
  
  public final void setTestWhileIdle(boolean testWhileIdle)
  {
    this.testWhileIdle = testWhileIdle;
  }
  
  public final long getTimeBetweenEvictionRunsMillis()
  {
    return this.timeBetweenEvictionRunsMillis;
  }
  
  public final void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis)
  {
    this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    startEvictor(timeBetweenEvictionRunsMillis);
  }
  
  public final int getNumTestsPerEvictionRun()
  {
    return this.numTestsPerEvictionRun;
  }
  
  public final void setNumTestsPerEvictionRun(int numTestsPerEvictionRun)
  {
    this.numTestsPerEvictionRun = numTestsPerEvictionRun;
  }
  
  public final long getMinEvictableIdleTimeMillis()
  {
    return this.minEvictableIdleTimeMillis;
  }
  
  public final void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis)
  {
    this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
  }
  
  public final long getSoftMinEvictableIdleTimeMillis()
  {
    return this.softMinEvictableIdleTimeMillis;
  }
  
  public final void setSoftMinEvictableIdleTimeMillis(long softMinEvictableIdleTimeMillis)
  {
    this.softMinEvictableIdleTimeMillis = softMinEvictableIdleTimeMillis;
  }
  
  public final String getEvictionPolicyClassName()
  {
    return this.evictionPolicy.getClass().getName();
  }
  
  public final void setEvictionPolicyClassName(String evictionPolicyClassName)
  {
    try
    {
      Class<?> clazz = Class.forName(evictionPolicyClassName);
      Object policy = clazz.newInstance();
      if ((policy instanceof EvictionPolicy))
      {
        EvictionPolicy<T> evicPolicy = (EvictionPolicy)policy;
        this.evictionPolicy = evicPolicy;
      }
    }
    catch (ClassNotFoundException e)
    {
      throw new IllegalArgumentException("Unable to create EvictionPolicy instance of type " + evictionPolicyClassName, e);
    }
    catch (InstantiationException e)
    {
      throw new IllegalArgumentException("Unable to create EvictionPolicy instance of type " + evictionPolicyClassName, e);
    }
    catch (IllegalAccessException e)
    {
      throw new IllegalArgumentException("Unable to create EvictionPolicy instance of type " + evictionPolicyClassName, e);
    }
  }
  
  public abstract void close();
  
  public final boolean isClosed()
  {
    return this.closed;
  }
  
  public abstract void evict()
    throws Exception;
  
  final EvictionPolicy<T> getEvictionPolicy()
  {
    return this.evictionPolicy;
  }
  
  final void assertOpen()
    throws IllegalStateException
  {
    if (isClosed()) {
      throw new IllegalStateException("Pool not open");
    }
  }
  
  final void startEvictor(long delay)
  {
    synchronized (this.evictionLock)
    {
      if (null != this.evictor)
      {
        EvictionTimer.cancel(this.evictor);
        this.evictor = null;
        this.evictionIterator = null;
      }
      if (delay > 0L)
      {
        this.evictor = new Evictor();
        EvictionTimer.schedule(this.evictor, delay, delay);
      }
    }
  }
  
  abstract void ensureMinIdle()
    throws Exception;
  
  public final ObjectName getJmxName()
  {
    return this.oname;
  }
  
  public final String getCreationStackTrace()
  {
    return this.creationStackTrace;
  }
  
  public final long getBorrowedCount()
  {
    return this.borrowedCount.get();
  }
  
  public final long getReturnedCount()
  {
    return this.returnedCount.get();
  }
  
  public final long getCreatedCount()
  {
    return this.createdCount.get();
  }
  
  public final long getDestroyedCount()
  {
    return this.destroyedCount.get();
  }
  
  public final long getDestroyedByEvictorCount()
  {
    return this.destroyedByEvictorCount.get();
  }
  
  public final long getDestroyedByBorrowValidationCount()
  {
    return this.destroyedByBorrowValidationCount.get();
  }
  
  public final long getMeanActiveTimeMillis()
  {
    return getMeanFromStatsCache(this.activeTimes);
  }
  
  public final long getMeanIdleTimeMillis()
  {
    return getMeanFromStatsCache(this.idleTimes);
  }
  
  public final long getMeanBorrowWaitTimeMillis()
  {
    return getMeanFromStatsCache(this.waitTimes);
  }
  
  public final long getMaxBorrowWaitTimeMillis()
  {
    return this.maxBorrowWaitTimeMillis;
  }
  
  public abstract int getNumIdle();
  
  public final SwallowedExceptionListener getSwallowedExceptionListener()
  {
    return this.swallowedExceptionListener;
  }
  
  public final void setSwallowedExceptionListener(SwallowedExceptionListener swallowedExceptionListener)
  {
    this.swallowedExceptionListener = swallowedExceptionListener;
  }
  
  final void swallowException(Exception e)
  {
    SwallowedExceptionListener listener = getSwallowedExceptionListener();
    if (listener == null) {
      return;
    }
    try
    {
      listener.onSwallowException(e);
    }
    catch (OutOfMemoryError oome)
    {
      throw oome;
    }
    catch (VirtualMachineError vme)
    {
      throw vme;
    }
    catch (Throwable t) {}
  }
  
  final void updateStatsBorrow(PooledObject<T> p, long waitTime)
  {
    this.borrowedCount.incrementAndGet();
    synchronized (this.idleTimes)
    {
      this.idleTimes.add(Long.valueOf(p.getIdleTimeMillis()));
      this.idleTimes.poll();
    }
    synchronized (this.waitTimes)
    {
      this.waitTimes.add(Long.valueOf(waitTime));
      this.waitTimes.poll();
    }
    synchronized (this.maxBorrowWaitTimeMillisLock)
    {
      if (waitTime > this.maxBorrowWaitTimeMillis) {
        this.maxBorrowWaitTimeMillis = waitTime;
      }
    }
  }
  
  final void updateStatsReturn(long activeTime)
  {
    this.returnedCount.incrementAndGet();
    synchronized (this.activeTimes)
    {
      this.activeTimes.add(Long.valueOf(activeTime));
      this.activeTimes.poll();
    }
  }
  
  final void jmxUnregister()
  {
    if (this.oname != null) {
      try
      {
        ManagementFactory.getPlatformMBeanServer().unregisterMBean(this.oname);
      }
      catch (MBeanRegistrationException e)
      {
        swallowException(e);
      }
      catch (InstanceNotFoundException e)
      {
        swallowException(e);
      }
    }
  }
  
  private ObjectName jmxRegister(BaseObjectPoolConfig config, String jmxNameBase, String jmxNamePrefix)
  {
    ObjectName objectName = null;
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    int i = 1;
    boolean registered = false;
    String base = config.getJmxNameBase();
    if (base == null) {
      base = jmxNameBase;
    }
    while (!registered) {
      try
      {
        ObjectName objName;
        ObjectName objName;
        if (i == 1) {
          objName = new ObjectName(base + jmxNamePrefix);
        } else {
          objName = new ObjectName(base + jmxNamePrefix + i);
        }
        mbs.registerMBean(this, objName);
        objectName = objName;
        registered = true;
      }
      catch (MalformedObjectNameException e)
      {
        if (("pool".equals(jmxNamePrefix)) && (jmxNameBase.equals(base)))
        {
          registered = true;
        }
        else
        {
          jmxNamePrefix = "pool";
          
          base = jmxNameBase;
        }
      }
      catch (InstanceAlreadyExistsException e)
      {
        i++;
      }
      catch (MBeanRegistrationException e)
      {
        registered = true;
      }
      catch (NotCompliantMBeanException e)
      {
        registered = true;
      }
    }
    return objectName;
  }
  
  private String getStackTrace(Exception e)
  {
    Writer w = new StringWriter();
    PrintWriter pw = new PrintWriter(w);
    e.printStackTrace(pw);
    return w.toString();
  }
  
  private long getMeanFromStatsCache(LinkedList<Long> cache)
  {
    List<Long> times = new ArrayList(100);
    synchronized (cache)
    {
      times.addAll(cache);
    }
    double result = 0.0D;
    int counter = 0;
    Iterator<Long> iter = times.iterator();
    while (iter.hasNext())
    {
      Long time = (Long)iter.next();
      if (time != null)
      {
        counter++;
        result = result * ((counter - 1) / counter) + time.longValue() / counter;
      }
    }
    return result;
  }
  
  private void initStats()
  {
    for (int i = 0; i < 100; i++)
    {
      this.activeTimes.add(null);
      this.idleTimes.add(null);
      this.waitTimes.add(null);
    }
  }
  
  class Evictor
    extends TimerTask
  {
    Evictor() {}
    
    public void run()
    {
      ClassLoader savedClassLoader = Thread.currentThread().getContextClassLoader();
      try
      {
        Thread.currentThread().setContextClassLoader(BaseGenericObjectPool.this.factoryClassLoader);
        try
        {
          BaseGenericObjectPool.this.evict();
        }
        catch (Exception e)
        {
          BaseGenericObjectPool.this.swallowException(e);
        }
        catch (OutOfMemoryError oome)
        {
          oome.printStackTrace(System.err);
        }
        try
        {
          BaseGenericObjectPool.this.ensureMinIdle();
        }
        catch (Exception e)
        {
          BaseGenericObjectPool.this.swallowException(e);
        }
      }
      finally
      {
        Thread.currentThread().setContextClassLoader(savedClassLoader);
      }
    }
  }
}
