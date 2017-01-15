package org.apache.commons.dbcp2.datasources;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.sql.ConnectionPoolDataSource;
import org.apache.commons.dbcp2.SwallowedExceptionLogger;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

public class PerUserPoolDataSource
  extends InstanceKeyDataSource
{
  private static final long serialVersionUID = 7872747993848065028L;
  private static final Log log = LogFactory.getLog(PerUserPoolDataSource.class);
  private Map<String, Boolean> perUserBlockWhenExhausted = null;
  private Map<String, String> perUserEvictionPolicyClassName = null;
  private Map<String, Boolean> perUserLifo = null;
  private Map<String, Integer> perUserMaxIdle = null;
  private Map<String, Integer> perUserMaxTotal = null;
  private Map<String, Long> perUserMaxWaitMillis = null;
  private Map<String, Long> perUserMinEvictableIdleTimeMillis = null;
  private Map<String, Integer> perUserMinIdle = null;
  private Map<String, Integer> perUserNumTestsPerEvictionRun = null;
  private Map<String, Long> perUserSoftMinEvictableIdleTimeMillis = null;
  private Map<String, Boolean> perUserTestOnCreate = null;
  private Map<String, Boolean> perUserTestOnBorrow = null;
  private Map<String, Boolean> perUserTestOnReturn = null;
  private Map<String, Boolean> perUserTestWhileIdle = null;
  private Map<String, Long> perUserTimeBetweenEvictionRunsMillis = null;
  private Map<String, Boolean> perUserDefaultAutoCommit = null;
  private Map<String, Integer> perUserDefaultTransactionIsolation = null;
  private Map<String, Boolean> perUserDefaultReadOnly = null;
  private transient Map<PoolKey, PooledConnectionManager> managers = new HashMap();
  
  public void close()
  {
    for (PooledConnectionManager manager : this.managers.values()) {
      try
      {
        ((CPDSConnectionFactory)manager).getPool().close();
      }
      catch (Exception closePoolException) {}
    }
    InstanceKeyDataSourceFactory.removeInstance(getInstanceKey());
  }
  
  public boolean getPerUserBlockWhenExhausted(String key)
  {
    Boolean value = null;
    if (this.perUserBlockWhenExhausted != null) {
      value = (Boolean)this.perUserBlockWhenExhausted.get(key);
    }
    if (value == null) {
      return getDefaultBlockWhenExhausted();
    }
    return value.booleanValue();
  }
  
  public void setPerUserBlockWhenExhausted(String username, Boolean value)
  {
    assertInitializationAllowed();
    if (this.perUserBlockWhenExhausted == null) {
      this.perUserBlockWhenExhausted = new HashMap();
    }
    this.perUserBlockWhenExhausted.put(username, value);
  }
  
  void setPerUserBlockWhenExhausted(Map<String, Boolean> userDefaultBlockWhenExhausted)
  {
    assertInitializationAllowed();
    if (this.perUserBlockWhenExhausted == null) {
      this.perUserBlockWhenExhausted = new HashMap();
    } else {
      this.perUserBlockWhenExhausted.clear();
    }
    this.perUserBlockWhenExhausted.putAll(userDefaultBlockWhenExhausted);
  }
  
  public String getPerUserEvictionPolicyClassName(String key)
  {
    String value = null;
    if (this.perUserEvictionPolicyClassName != null) {
      value = (String)this.perUserEvictionPolicyClassName.get(key);
    }
    if (value == null) {
      return getDefaultEvictionPolicyClassName();
    }
    return value;
  }
  
  public void setPerUserEvictionPolicyClassName(String username, String value)
  {
    assertInitializationAllowed();
    if (this.perUserEvictionPolicyClassName == null) {
      this.perUserEvictionPolicyClassName = new HashMap();
    }
    this.perUserEvictionPolicyClassName.put(username, value);
  }
  
  void setPerUserEvictionPolicyClassName(Map<String, String> userDefaultEvictionPolicyClassName)
  {
    assertInitializationAllowed();
    if (this.perUserEvictionPolicyClassName == null) {
      this.perUserEvictionPolicyClassName = new HashMap();
    } else {
      this.perUserEvictionPolicyClassName.clear();
    }
    this.perUserEvictionPolicyClassName.putAll(userDefaultEvictionPolicyClassName);
  }
  
  public boolean getPerUserLifo(String key)
  {
    Boolean value = null;
    if (this.perUserLifo != null) {
      value = (Boolean)this.perUserLifo.get(key);
    }
    if (value == null) {
      return getDefaultLifo();
    }
    return value.booleanValue();
  }
  
  public void setPerUserLifo(String username, Boolean value)
  {
    assertInitializationAllowed();
    if (this.perUserLifo == null) {
      this.perUserLifo = new HashMap();
    }
    this.perUserLifo.put(username, value);
  }
  
  void setPerUserLifo(Map<String, Boolean> userDefaultLifo)
  {
    assertInitializationAllowed();
    if (this.perUserLifo == null) {
      this.perUserLifo = new HashMap();
    } else {
      this.perUserLifo.clear();
    }
    this.perUserLifo.putAll(userDefaultLifo);
  }
  
  public int getPerUserMaxIdle(String key)
  {
    Integer value = null;
    if (this.perUserMaxIdle != null) {
      value = (Integer)this.perUserMaxIdle.get(key);
    }
    if (value == null) {
      return getDefaultMaxIdle();
    }
    return value.intValue();
  }
  
  public void setPerUserMaxIdle(String username, Integer value)
  {
    assertInitializationAllowed();
    if (this.perUserMaxIdle == null) {
      this.perUserMaxIdle = new HashMap();
    }
    this.perUserMaxIdle.put(username, value);
  }
  
  void setPerUserMaxIdle(Map<String, Integer> userDefaultMaxIdle)
  {
    assertInitializationAllowed();
    if (this.perUserMaxIdle == null) {
      this.perUserMaxIdle = new HashMap();
    } else {
      this.perUserMaxIdle.clear();
    }
    this.perUserMaxIdle.putAll(userDefaultMaxIdle);
  }
  
  public int getPerUserMaxTotal(String key)
  {
    Integer value = null;
    if (this.perUserMaxTotal != null) {
      value = (Integer)this.perUserMaxTotal.get(key);
    }
    if (value == null) {
      return getDefaultMaxTotal();
    }
    return value.intValue();
  }
  
  public void setPerUserMaxTotal(String username, Integer value)
  {
    assertInitializationAllowed();
    if (this.perUserMaxTotal == null) {
      this.perUserMaxTotal = new HashMap();
    }
    this.perUserMaxTotal.put(username, value);
  }
  
  void setPerUserMaxTotal(Map<String, Integer> userDefaultMaxTotal)
  {
    assertInitializationAllowed();
    if (this.perUserMaxTotal == null) {
      this.perUserMaxTotal = new HashMap();
    } else {
      this.perUserMaxTotal.clear();
    }
    this.perUserMaxTotal.putAll(userDefaultMaxTotal);
  }
  
  public long getPerUserMaxWaitMillis(String key)
  {
    Long value = null;
    if (this.perUserMaxWaitMillis != null) {
      value = (Long)this.perUserMaxWaitMillis.get(key);
    }
    if (value == null) {
      return getDefaultMaxWaitMillis();
    }
    return value.longValue();
  }
  
  public void setPerUserMaxWaitMillis(String username, Long value)
  {
    assertInitializationAllowed();
    if (this.perUserMaxWaitMillis == null) {
      this.perUserMaxWaitMillis = new HashMap();
    }
    this.perUserMaxWaitMillis.put(username, value);
  }
  
  void setPerUserMaxWaitMillis(Map<String, Long> userDefaultMaxWaitMillis)
  {
    assertInitializationAllowed();
    if (this.perUserMaxWaitMillis == null) {
      this.perUserMaxWaitMillis = new HashMap();
    } else {
      this.perUserMaxWaitMillis.clear();
    }
    this.perUserMaxWaitMillis.putAll(userDefaultMaxWaitMillis);
  }
  
  public long getPerUserMinEvictableIdleTimeMillis(String key)
  {
    Long value = null;
    if (this.perUserMinEvictableIdleTimeMillis != null) {
      value = (Long)this.perUserMinEvictableIdleTimeMillis.get(key);
    }
    if (value == null) {
      return getDefaultMinEvictableIdleTimeMillis();
    }
    return value.longValue();
  }
  
  public void setPerUserMinEvictableIdleTimeMillis(String username, Long value)
  {
    assertInitializationAllowed();
    if (this.perUserMinEvictableIdleTimeMillis == null) {
      this.perUserMinEvictableIdleTimeMillis = new HashMap();
    }
    this.perUserMinEvictableIdleTimeMillis.put(username, value);
  }
  
  void setPerUserMinEvictableIdleTimeMillis(Map<String, Long> userDefaultMinEvictableIdleTimeMillis)
  {
    assertInitializationAllowed();
    if (this.perUserMinEvictableIdleTimeMillis == null) {
      this.perUserMinEvictableIdleTimeMillis = new HashMap();
    } else {
      this.perUserMinEvictableIdleTimeMillis.clear();
    }
    this.perUserMinEvictableIdleTimeMillis.putAll(userDefaultMinEvictableIdleTimeMillis);
  }
  
  public int getPerUserMinIdle(String key)
  {
    Integer value = null;
    if (this.perUserMinIdle != null) {
      value = (Integer)this.perUserMinIdle.get(key);
    }
    if (value == null) {
      return getDefaultMinIdle();
    }
    return value.intValue();
  }
  
  public void setPerUserMinIdle(String username, Integer value)
  {
    assertInitializationAllowed();
    if (this.perUserMinIdle == null) {
      this.perUserMinIdle = new HashMap();
    }
    this.perUserMinIdle.put(username, value);
  }
  
  void setPerUserMinIdle(Map<String, Integer> userDefaultMinIdle)
  {
    assertInitializationAllowed();
    if (this.perUserMinIdle == null) {
      this.perUserMinIdle = new HashMap();
    } else {
      this.perUserMinIdle.clear();
    }
    this.perUserMinIdle.putAll(userDefaultMinIdle);
  }
  
  public int getPerUserNumTestsPerEvictionRun(String key)
  {
    Integer value = null;
    if (this.perUserNumTestsPerEvictionRun != null) {
      value = (Integer)this.perUserNumTestsPerEvictionRun.get(key);
    }
    if (value == null) {
      return getDefaultNumTestsPerEvictionRun();
    }
    return value.intValue();
  }
  
  public void setPerUserNumTestsPerEvictionRun(String username, Integer value)
  {
    assertInitializationAllowed();
    if (this.perUserNumTestsPerEvictionRun == null) {
      this.perUserNumTestsPerEvictionRun = new HashMap();
    }
    this.perUserNumTestsPerEvictionRun.put(username, value);
  }
  
  void setPerUserNumTestsPerEvictionRun(Map<String, Integer> userDefaultNumTestsPerEvictionRun)
  {
    assertInitializationAllowed();
    if (this.perUserNumTestsPerEvictionRun == null) {
      this.perUserNumTestsPerEvictionRun = new HashMap();
    } else {
      this.perUserNumTestsPerEvictionRun.clear();
    }
    this.perUserNumTestsPerEvictionRun.putAll(userDefaultNumTestsPerEvictionRun);
  }
  
  public long getPerUserSoftMinEvictableIdleTimeMillis(String key)
  {
    Long value = null;
    if (this.perUserSoftMinEvictableIdleTimeMillis != null) {
      value = (Long)this.perUserSoftMinEvictableIdleTimeMillis.get(key);
    }
    if (value == null) {
      return getDefaultSoftMinEvictableIdleTimeMillis();
    }
    return value.longValue();
  }
  
  public void setPerUserSoftMinEvictableIdleTimeMillis(String username, Long value)
  {
    assertInitializationAllowed();
    if (this.perUserSoftMinEvictableIdleTimeMillis == null) {
      this.perUserSoftMinEvictableIdleTimeMillis = new HashMap();
    }
    this.perUserSoftMinEvictableIdleTimeMillis.put(username, value);
  }
  
  void setPerUserSoftMinEvictableIdleTimeMillis(Map<String, Long> userDefaultSoftMinEvictableIdleTimeMillis)
  {
    assertInitializationAllowed();
    if (this.perUserSoftMinEvictableIdleTimeMillis == null) {
      this.perUserSoftMinEvictableIdleTimeMillis = new HashMap();
    } else {
      this.perUserSoftMinEvictableIdleTimeMillis.clear();
    }
    this.perUserSoftMinEvictableIdleTimeMillis.putAll(userDefaultSoftMinEvictableIdleTimeMillis);
  }
  
  public boolean getPerUserTestOnCreate(String key)
  {
    Boolean value = null;
    if (this.perUserTestOnCreate != null) {
      value = (Boolean)this.perUserTestOnCreate.get(key);
    }
    if (value == null) {
      return getDefaultTestOnCreate();
    }
    return value.booleanValue();
  }
  
  public void setPerUserTestOnCreate(String username, Boolean value)
  {
    assertInitializationAllowed();
    if (this.perUserTestOnCreate == null) {
      this.perUserTestOnCreate = new HashMap();
    }
    this.perUserTestOnCreate.put(username, value);
  }
  
  void setPerUserTestOnCreate(Map<String, Boolean> userDefaultTestOnCreate)
  {
    assertInitializationAllowed();
    if (this.perUserTestOnCreate == null) {
      this.perUserTestOnCreate = new HashMap();
    } else {
      this.perUserTestOnCreate.clear();
    }
    this.perUserTestOnCreate.putAll(userDefaultTestOnCreate);
  }
  
  public boolean getPerUserTestOnBorrow(String key)
  {
    Boolean value = null;
    if (this.perUserTestOnBorrow != null) {
      value = (Boolean)this.perUserTestOnBorrow.get(key);
    }
    if (value == null) {
      return getDefaultTestOnBorrow();
    }
    return value.booleanValue();
  }
  
  public void setPerUserTestOnBorrow(String username, Boolean value)
  {
    assertInitializationAllowed();
    if (this.perUserTestOnBorrow == null) {
      this.perUserTestOnBorrow = new HashMap();
    }
    this.perUserTestOnBorrow.put(username, value);
  }
  
  void setPerUserTestOnBorrow(Map<String, Boolean> userDefaultTestOnBorrow)
  {
    assertInitializationAllowed();
    if (this.perUserTestOnBorrow == null) {
      this.perUserTestOnBorrow = new HashMap();
    } else {
      this.perUserTestOnBorrow.clear();
    }
    this.perUserTestOnBorrow.putAll(userDefaultTestOnBorrow);
  }
  
  public boolean getPerUserTestOnReturn(String key)
  {
    Boolean value = null;
    if (this.perUserTestOnReturn != null) {
      value = (Boolean)this.perUserTestOnReturn.get(key);
    }
    if (value == null) {
      return getDefaultTestOnReturn();
    }
    return value.booleanValue();
  }
  
  public void setPerUserTestOnReturn(String username, Boolean value)
  {
    assertInitializationAllowed();
    if (this.perUserTestOnReturn == null) {
      this.perUserTestOnReturn = new HashMap();
    }
    this.perUserTestOnReturn.put(username, value);
  }
  
  void setPerUserTestOnReturn(Map<String, Boolean> userDefaultTestOnReturn)
  {
    assertInitializationAllowed();
    if (this.perUserTestOnReturn == null) {
      this.perUserTestOnReturn = new HashMap();
    } else {
      this.perUserTestOnReturn.clear();
    }
    this.perUserTestOnReturn.putAll(userDefaultTestOnReturn);
  }
  
  public boolean getPerUserTestWhileIdle(String key)
  {
    Boolean value = null;
    if (this.perUserTestWhileIdle != null) {
      value = (Boolean)this.perUserTestWhileIdle.get(key);
    }
    if (value == null) {
      return getDefaultTestWhileIdle();
    }
    return value.booleanValue();
  }
  
  public void setPerUserTestWhileIdle(String username, Boolean value)
  {
    assertInitializationAllowed();
    if (this.perUserTestWhileIdle == null) {
      this.perUserTestWhileIdle = new HashMap();
    }
    this.perUserTestWhileIdle.put(username, value);
  }
  
  void setPerUserTestWhileIdle(Map<String, Boolean> userDefaultTestWhileIdle)
  {
    assertInitializationAllowed();
    if (this.perUserTestWhileIdle == null) {
      this.perUserTestWhileIdle = new HashMap();
    } else {
      this.perUserTestWhileIdle.clear();
    }
    this.perUserTestWhileIdle.putAll(userDefaultTestWhileIdle);
  }
  
  public long getPerUserTimeBetweenEvictionRunsMillis(String key)
  {
    Long value = null;
    if (this.perUserTimeBetweenEvictionRunsMillis != null) {
      value = (Long)this.perUserTimeBetweenEvictionRunsMillis.get(key);
    }
    if (value == null) {
      return getDefaultTimeBetweenEvictionRunsMillis();
    }
    return value.longValue();
  }
  
  public void setPerUserTimeBetweenEvictionRunsMillis(String username, Long value)
  {
    assertInitializationAllowed();
    if (this.perUserTimeBetweenEvictionRunsMillis == null) {
      this.perUserTimeBetweenEvictionRunsMillis = new HashMap();
    }
    this.perUserTimeBetweenEvictionRunsMillis.put(username, value);
  }
  
  void setPerUserTimeBetweenEvictionRunsMillis(Map<String, Long> userDefaultTimeBetweenEvictionRunsMillis)
  {
    assertInitializationAllowed();
    if (this.perUserTimeBetweenEvictionRunsMillis == null) {
      this.perUserTimeBetweenEvictionRunsMillis = new HashMap();
    } else {
      this.perUserTimeBetweenEvictionRunsMillis.clear();
    }
    this.perUserTimeBetweenEvictionRunsMillis.putAll(userDefaultTimeBetweenEvictionRunsMillis);
  }
  
  public Boolean getPerUserDefaultAutoCommit(String key)
  {
    Boolean value = null;
    if (this.perUserDefaultAutoCommit != null) {
      value = (Boolean)this.perUserDefaultAutoCommit.get(key);
    }
    return value;
  }
  
  public void setPerUserDefaultAutoCommit(String username, Boolean value)
  {
    assertInitializationAllowed();
    if (this.perUserDefaultAutoCommit == null) {
      this.perUserDefaultAutoCommit = new HashMap();
    }
    this.perUserDefaultAutoCommit.put(username, value);
  }
  
  void setPerUserDefaultAutoCommit(Map<String, Boolean> userDefaultAutoCommit)
  {
    assertInitializationAllowed();
    if (this.perUserDefaultAutoCommit == null) {
      this.perUserDefaultAutoCommit = new HashMap();
    } else {
      this.perUserDefaultAutoCommit.clear();
    }
    this.perUserDefaultAutoCommit.putAll(userDefaultAutoCommit);
  }
  
  public Boolean getPerUserDefaultReadOnly(String key)
  {
    Boolean value = null;
    if (this.perUserDefaultReadOnly != null) {
      value = (Boolean)this.perUserDefaultReadOnly.get(key);
    }
    return value;
  }
  
  public void setPerUserDefaultReadOnly(String username, Boolean value)
  {
    assertInitializationAllowed();
    if (this.perUserDefaultReadOnly == null) {
      this.perUserDefaultReadOnly = new HashMap();
    }
    this.perUserDefaultReadOnly.put(username, value);
  }
  
  void setPerUserDefaultReadOnly(Map<String, Boolean> userDefaultReadOnly)
  {
    assertInitializationAllowed();
    if (this.perUserDefaultReadOnly == null) {
      this.perUserDefaultReadOnly = new HashMap();
    } else {
      this.perUserDefaultReadOnly.clear();
    }
    this.perUserDefaultReadOnly.putAll(userDefaultReadOnly);
  }
  
  public Integer getPerUserDefaultTransactionIsolation(String key)
  {
    Integer value = null;
    if (this.perUserDefaultTransactionIsolation != null) {
      value = (Integer)this.perUserDefaultTransactionIsolation.get(key);
    }
    return value;
  }
  
  public void setPerUserDefaultTransactionIsolation(String username, Integer value)
  {
    assertInitializationAllowed();
    if (this.perUserDefaultTransactionIsolation == null) {
      this.perUserDefaultTransactionIsolation = new HashMap();
    }
    this.perUserDefaultTransactionIsolation.put(username, value);
  }
  
  void setPerUserDefaultTransactionIsolation(Map<String, Integer> userDefaultTransactionIsolation)
  {
    assertInitializationAllowed();
    if (this.perUserDefaultTransactionIsolation == null) {
      this.perUserDefaultTransactionIsolation = new HashMap();
    } else {
      this.perUserDefaultTransactionIsolation.clear();
    }
    this.perUserDefaultTransactionIsolation.putAll(userDefaultTransactionIsolation);
  }
  
  public int getNumActive()
  {
    return getNumActive(null);
  }
  
  public int getNumActive(String username)
  {
    ObjectPool<PooledConnectionAndInfo> pool = getPool(getPoolKey(username));
    
    return pool == null ? 0 : pool.getNumActive();
  }
  
  public int getNumIdle()
  {
    return getNumIdle(null);
  }
  
  public int getNumIdle(String username)
  {
    ObjectPool<PooledConnectionAndInfo> pool = getPool(getPoolKey(username));
    
    return pool == null ? 0 : pool.getNumIdle();
  }
  
  protected PooledConnectionAndInfo getPooledConnectionAndInfo(String username, String password)
    throws SQLException
  {
    PoolKey key = getPoolKey(username);
    PooledConnectionManager manager;
    ObjectPool<PooledConnectionAndInfo> pool;
    synchronized (this)
    {
      manager = (PooledConnectionManager)this.managers.get(key);
      if (manager == null) {
        try
        {
          registerPool(username, password);
          manager = (PooledConnectionManager)this.managers.get(key);
        }
        catch (NamingException e)
        {
          throw new SQLException("RegisterPool failed", e);
        }
      }
      pool = ((CPDSConnectionFactory)manager).getPool();
    }
    PooledConnectionAndInfo info = null;
    try
    {
      info = (PooledConnectionAndInfo)pool.borrowObject();
    }
    catch (NoSuchElementException ex)
    {
      throw new SQLException("Could not retrieve connection info from pool", ex);
    }
    catch (Exception e)
    {
      try
      {
        testCPDS(username, password);
      }
      catch (Exception ex)
      {
        throw new SQLException("Could not retrieve connection info from pool", ex);
      }
      manager.closePool(username);
      synchronized (this)
      {
        this.managers.remove(key);
      }
      try
      {
        registerPool(username, password);
        pool = getPool(key);
      }
      catch (NamingException ne)
      {
        throw new SQLException("RegisterPool failed", ne);
      }
      try
      {
        info = (PooledConnectionAndInfo)pool.borrowObject();
      }
      catch (Exception ex)
      {
        throw new SQLException("Could not retrieve connection info from pool", ex);
      }
    }
    return info;
  }
  
  protected void setupDefaults(Connection con, String username)
    throws SQLException
  {
    Boolean defaultAutoCommit = isDefaultAutoCommit();
    if (username != null)
    {
      Boolean userMax = getPerUserDefaultAutoCommit(username);
      if (userMax != null) {
        defaultAutoCommit = userMax;
      }
    }
    Boolean defaultReadOnly = isDefaultReadOnly();
    if (username != null)
    {
      Boolean userMax = getPerUserDefaultReadOnly(username);
      if (userMax != null) {
        defaultReadOnly = userMax;
      }
    }
    int defaultTransactionIsolation = getDefaultTransactionIsolation();
    if (username != null)
    {
      Integer userMax = getPerUserDefaultTransactionIsolation(username);
      if (userMax != null) {
        defaultTransactionIsolation = userMax.intValue();
      }
    }
    if ((defaultAutoCommit != null) && (con.getAutoCommit() != defaultAutoCommit.booleanValue())) {
      con.setAutoCommit(defaultAutoCommit.booleanValue());
    }
    if (defaultTransactionIsolation != -1) {
      con.setTransactionIsolation(defaultTransactionIsolation);
    }
    if ((defaultReadOnly != null) && (con.isReadOnly() != defaultReadOnly.booleanValue())) {
      con.setReadOnly(defaultReadOnly.booleanValue());
    }
  }
  
  protected PooledConnectionManager getConnectionManager(UserPassKey upkey)
  {
    return (PooledConnectionManager)this.managers.get(getPoolKey(upkey.getUsername()));
  }
  
  public Reference getReference()
    throws NamingException
  {
    Reference ref = new Reference(getClass().getName(), PerUserPoolDataSourceFactory.class.getName(), null);
    
    ref.add(new StringRefAddr("instanceKey", getInstanceKey()));
    return ref;
  }
  
  private PoolKey getPoolKey(String username)
  {
    return new PoolKey(getDataSourceName(), username);
  }
  
  private synchronized void registerPool(String username, String password)
    throws NamingException, SQLException
  {
    ConnectionPoolDataSource cpds = testCPDS(username, password);
    
    CPDSConnectionFactory factory = new CPDSConnectionFactory(cpds, getValidationQuery(), getValidationQueryTimeout(), isRollbackAfterValidation(), username, password);
    
    factory.setMaxConnLifetimeMillis(getMaxConnLifetimeMillis());
    
    GenericObjectPool<PooledConnectionAndInfo> pool = new GenericObjectPool(factory);
    
    factory.setPool(pool);
    pool.setBlockWhenExhausted(getPerUserBlockWhenExhausted(username));
    pool.setEvictionPolicyClassName(getPerUserEvictionPolicyClassName(username));
    
    pool.setLifo(getPerUserLifo(username));
    pool.setMaxIdle(getPerUserMaxIdle(username));
    pool.setMaxTotal(getPerUserMaxTotal(username));
    pool.setMaxWaitMillis(getPerUserMaxWaitMillis(username));
    pool.setMinEvictableIdleTimeMillis(getPerUserMinEvictableIdleTimeMillis(username));
    
    pool.setMinIdle(getPerUserMinIdle(username));
    pool.setNumTestsPerEvictionRun(getPerUserNumTestsPerEvictionRun(username));
    
    pool.setSoftMinEvictableIdleTimeMillis(getPerUserSoftMinEvictableIdleTimeMillis(username));
    
    pool.setTestOnCreate(getPerUserTestOnCreate(username));
    pool.setTestOnBorrow(getPerUserTestOnBorrow(username));
    pool.setTestOnReturn(getPerUserTestOnReturn(username));
    pool.setTestWhileIdle(getPerUserTestWhileIdle(username));
    pool.setTimeBetweenEvictionRunsMillis(getPerUserTimeBetweenEvictionRunsMillis(username));
    
    pool.setSwallowedExceptionListener(new SwallowedExceptionLogger(log));
    
    Object old = this.managers.put(getPoolKey(username), factory);
    if (old != null) {
      throw new IllegalStateException("Pool already contains an entry for this user/password: " + username);
    }
  }
  
  private void readObject(ObjectInputStream in)
    throws IOException, ClassNotFoundException
  {
    try
    {
      in.defaultReadObject();
      PerUserPoolDataSource oldDS = (PerUserPoolDataSource)new PerUserPoolDataSourceFactory().getObjectInstance(getReference(), null, null, null);
      
      this.managers = oldDS.managers;
    }
    catch (NamingException e)
    {
      throw new IOException("NamingException: " + e);
    }
  }
  
  private ObjectPool<PooledConnectionAndInfo> getPool(PoolKey key)
  {
    CPDSConnectionFactory mgr = (CPDSConnectionFactory)this.managers.get(key);
    return mgr == null ? null : mgr.getPool();
  }
}
