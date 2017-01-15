package org.apache.commons.dbcp2.managed;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.ConnectionEvent;
import javax.sql.ConnectionEventListener;
import javax.sql.PooledConnection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.TransactionManager;
import javax.transaction.xa.XAResource;

public class DataSourceXAConnectionFactory
  implements XAConnectionFactory
{
  private final TransactionRegistry transactionRegistry;
  private final XADataSource xaDataSource;
  private String username;
  private String password;
  
  public DataSourceXAConnectionFactory(TransactionManager transactionManager, XADataSource xaDataSource)
  {
    this(transactionManager, xaDataSource, null, null);
  }
  
  public DataSourceXAConnectionFactory(TransactionManager transactionManager, XADataSource xaDataSource, String username, String password)
  {
    if (transactionManager == null) {
      throw new NullPointerException("transactionManager is null");
    }
    if (xaDataSource == null) {
      throw new NullPointerException("xaDataSource is null");
    }
    this.transactionRegistry = new TransactionRegistry(transactionManager);
    this.xaDataSource = xaDataSource;
    this.username = username;
    this.password = password;
  }
  
  public String getUsername()
  {
    return this.username;
  }
  
  public void setUsername(String username)
  {
    this.username = username;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public TransactionRegistry getTransactionRegistry()
  {
    return this.transactionRegistry;
  }
  
  public Connection createConnection()
    throws SQLException
  {
    XAConnection xaConnection;
    XAConnection xaConnection;
    if (this.username == null) {
      xaConnection = this.xaDataSource.getXAConnection();
    } else {
      xaConnection = this.xaDataSource.getXAConnection(this.username, this.password);
    }
    Connection connection = xaConnection.getConnection();
    XAResource xaResource = xaConnection.getXAResource();
    
    this.transactionRegistry.registerConnection(connection, xaResource);
    
    xaConnection.addConnectionEventListener(new ConnectionEventListener()
    {
      public void connectionClosed(ConnectionEvent event)
      {
        PooledConnection pc = (PooledConnection)event.getSource();
        pc.removeConnectionEventListener(this);
        try
        {
          pc.close();
        }
        catch (SQLException e)
        {
          System.err.println("Failed to close XAConnection");
          e.printStackTrace();
        }
      }
      
      public void connectionErrorOccurred(ConnectionEvent event)
      {
        connectionClosed(event);
      }
    });
    return connection;
  }
}
