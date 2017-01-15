package org.apache.commons.dbcp2;

public abstract interface BasicDataSourceMXBean
{
  public abstract boolean getAbandonedUsageTracking();
  
  public abstract Boolean getDefaultAutoCommit();
  
  public abstract Boolean getDefaultReadOnly();
  
  public abstract int getDefaultTransactionIsolation();
  
  public abstract String getDefaultCatalog();
  
  public abstract boolean getCacheState();
  
  public abstract String getDriverClassName();
  
  public abstract boolean getLifo();
  
  public abstract int getMaxTotal();
  
  public abstract int getMaxIdle();
  
  public abstract int getMinIdle();
  
  public abstract int getInitialSize();
  
  public abstract long getMaxWaitMillis();
  
  public abstract boolean isPoolPreparedStatements();
  
  public abstract int getMaxOpenPreparedStatements();
  
  public abstract boolean getTestOnCreate();
  
  public abstract boolean getTestOnBorrow();
  
  public abstract long getTimeBetweenEvictionRunsMillis();
  
  public abstract int getNumTestsPerEvictionRun();
  
  public abstract long getMinEvictableIdleTimeMillis();
  
  public abstract long getSoftMinEvictableIdleTimeMillis();
  
  public abstract boolean getTestWhileIdle();
  
  public abstract int getNumActive();
  
  public abstract int getNumIdle();
  
  public abstract String getPassword();
  
  public abstract String getUrl();
  
  public abstract String getUsername();
  
  public abstract String getValidationQuery();
  
  public abstract int getValidationQueryTimeout();
  
  public abstract String[] getConnectionInitSqlsAsArray();
  
  public abstract boolean isAccessToUnderlyingConnectionAllowed();
  
  public abstract long getMaxConnLifetimeMillis();
  
  public abstract boolean getRemoveAbandonedOnBorrow();
  
  public abstract boolean getRemoveAbandonedOnMaintenance();
  
  public abstract int getRemoveAbandonedTimeout();
  
  public abstract boolean getLogAbandoned();
  
  public abstract boolean isClosed();
}
