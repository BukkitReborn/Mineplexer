package org.apache.commons.dbcp2.managed;

import java.sql.SQLException;
import javax.sql.DataSource;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;

public class BasicManagedDataSource
  extends BasicDataSource
{
  private TransactionRegistry transactionRegistry;
  private transient TransactionManager transactionManager;
  private String xaDataSource;
  private XADataSource xaDataSourceInstance;
  
  public synchronized XADataSource getXaDataSourceInstance()
  {
    return this.xaDataSourceInstance;
  }
  
  public synchronized void setXaDataSourceInstance(XADataSource xaDataSourceInstance)
  {
    this.xaDataSourceInstance = xaDataSourceInstance;
    this.xaDataSource = (xaDataSourceInstance == null ? null : xaDataSourceInstance.getClass().getName());
  }
  
  public TransactionManager getTransactionManager()
  {
    return this.transactionManager;
  }
  
  protected synchronized TransactionRegistry getTransactionRegistry()
  {
    return this.transactionRegistry;
  }
  
  public void setTransactionManager(TransactionManager transactionManager)
  {
    this.transactionManager = transactionManager;
  }
  
  public synchronized String getXADataSource()
  {
    return this.xaDataSource;
  }
  
  public synchronized void setXADataSource(String xaDataSource)
  {
    this.xaDataSource = xaDataSource;
  }
  
  protected ConnectionFactory createConnectionFactory()
    throws SQLException
  {
    if (this.transactionManager == null) {
      throw new SQLException("Transaction manager must be set before a connection can be created");
    }
    if (this.xaDataSource == null)
    {
      ConnectionFactory connectionFactory = super.createConnectionFactory();
      XAConnectionFactory xaConnectionFactory = new LocalXAConnectionFactory(getTransactionManager(), connectionFactory);
      this.transactionRegistry = xaConnectionFactory.getTransactionRegistry();
      return xaConnectionFactory;
    }
    if (this.xaDataSourceInstance == null)
    {
      Class<?> xaDataSourceClass = null;
      try
      {
        xaDataSourceClass = Class.forName(this.xaDataSource);
      }
      catch (Exception t)
      {
        String message = "Cannot load XA data source class '" + this.xaDataSource + "'";
        throw new SQLException(message, t);
      }
      try
      {
        this.xaDataSourceInstance = ((XADataSource)xaDataSourceClass.newInstance());
      }
      catch (Exception t)
      {
        String message = "Cannot create XA data source of class '" + this.xaDataSource + "'";
        throw new SQLException(message, t);
      }
    }
    XAConnectionFactory xaConnectionFactory = new DataSourceXAConnectionFactory(getTransactionManager(), this.xaDataSourceInstance, getUsername(), getPassword());
    this.transactionRegistry = xaConnectionFactory.getTransactionRegistry();
    return xaConnectionFactory;
  }
  
  protected DataSource createDataSourceInstance()
    throws SQLException
  {
    PoolingDataSource<PoolableConnection> pds = new ManagedDataSource(getConnectionPool(), this.transactionRegistry);
    
    pds.setAccessToUnderlyingConnectionAllowed(isAccessToUnderlyingConnectionAllowed());
    return pds;
  }
  
  protected PoolableConnectionFactory createPoolableConnectionFactory(ConnectionFactory driverConnectionFactory)
    throws SQLException
  {
    PoolableConnectionFactory connectionFactory = null;
    try
    {
      connectionFactory = new PoolableManagedConnectionFactory((XAConnectionFactory)driverConnectionFactory, getRegisteredJmxName());
      
      connectionFactory.setValidationQuery(getValidationQuery());
      connectionFactory.setValidationQueryTimeout(getValidationQueryTimeout());
      connectionFactory.setConnectionInitSql(getConnectionInitSqls());
      connectionFactory.setDefaultReadOnly(getDefaultReadOnly());
      connectionFactory.setDefaultAutoCommit(getDefaultAutoCommit());
      connectionFactory.setDefaultTransactionIsolation(getDefaultTransactionIsolation());
      connectionFactory.setDefaultCatalog(getDefaultCatalog());
      connectionFactory.setCacheState(getCacheState());
      connectionFactory.setPoolStatements(isPoolPreparedStatements());
      connectionFactory.setMaxOpenPrepatedStatements(getMaxOpenPreparedStatements());
      
      connectionFactory.setMaxConnLifetimeMillis(getMaxConnLifetimeMillis());
      connectionFactory.setRollbackOnReturn(getRollbackOnReturn());
      connectionFactory.setEnableAutoCommitOnReturn(getEnableAutoCommitOnReturn());
      connectionFactory.setDefaultQueryTimeout(getDefaultQueryTimeout());
      validateConnectionFactory(connectionFactory);
    }
    catch (RuntimeException e)
    {
      throw e;
    }
    catch (Exception e)
    {
      throw new SQLException("Cannot create PoolableConnectionFactory (" + e.getMessage() + ")", e);
    }
    return connectionFactory;
  }
}
