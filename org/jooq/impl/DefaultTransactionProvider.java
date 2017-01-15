package org.jooq.impl;

import java.sql.Connection;
import java.sql.Savepoint;
import java.util.Map;
import java.util.Stack;
import org.jooq.Configuration;
import org.jooq.ConnectionProvider;
import org.jooq.TransactionContext;
import org.jooq.TransactionProvider;
import org.jooq.exception.DataAccessException;

public class DefaultTransactionProvider
  implements TransactionProvider
{
  private final ConnectionProvider provider;
  private Connection connection;
  
  public DefaultTransactionProvider(ConnectionProvider provider)
  {
    this.provider = provider;
  }
  
  private final Stack<Savepoint> savepoints(Configuration configuration)
  {
    Stack<Savepoint> savepoints = (Stack)configuration.data("org.jooq.configuration.default-transaction-provider-savepoints");
    if (savepoints == null)
    {
      savepoints = new Stack();
      configuration.data("org.jooq.configuration.default-transaction-provider-savepoints", savepoints);
    }
    return savepoints;
  }
  
  private final boolean autoCommit(Configuration configuration)
  {
    Boolean autoCommit = (Boolean)configuration.data("org.jooq.configuration.default-transaction-provider-autocommit");
    if (autoCommit == null)
    {
      autoCommit = Boolean.valueOf(connection(configuration).getAutoCommit());
      configuration.data("org.jooq.configuration.default-transaction-provider-autocommit", autoCommit);
    }
    return autoCommit.booleanValue();
  }
  
  private final DefaultConnectionProvider connection(Configuration configuration)
  {
    DefaultConnectionProvider connectionWrapper = (DefaultConnectionProvider)configuration.data("org.jooq.configuration.default-transaction-provider-connection-provider");
    if (connectionWrapper == null)
    {
      connectionWrapper = new DefaultConnectionProvider(this.connection);
      configuration.data("org.jooq.configuration.default-transaction-provider-connection-provider", connectionWrapper);
    }
    return connectionWrapper;
  }
  
  public final void begin(TransactionContext ctx)
  {
    Stack<Savepoint> savepoints = savepoints(ctx.configuration());
    if (savepoints.isEmpty()) {
      brace(ctx.configuration(), true);
    }
    savepoints.push(connection(ctx.configuration()).setSavepoint());
  }
  
  public final void commit(TransactionContext ctx)
  {
    Stack<Savepoint> savepoints = savepoints(ctx.configuration());
    Savepoint savepoint = (Savepoint)savepoints.pop();
    if (savepoint != null) {
      try
      {
        connection(ctx.configuration()).releaseSavepoint(savepoint);
      }
      catch (DataAccessException localDataAccessException) {}
    }
    if (savepoints.isEmpty())
    {
      connection(ctx.configuration()).commit();
      brace(ctx.configuration(), false);
    }
  }
  
  public final void rollback(TransactionContext ctx)
  {
    Stack<Savepoint> savepoints = savepoints(ctx.configuration());
    Savepoint savepoint = null;
    if (!savepoints.isEmpty()) {
      savepoint = (Savepoint)savepoints.pop();
    }
    try
    {
      if (savepoint == null) {
        connection(ctx.configuration()).rollback();
      } else {
        connection(ctx.configuration()).rollback(savepoint);
      }
    }
    finally
    {
      if (savepoints.isEmpty()) {
        brace(ctx.configuration(), false);
      }
    }
  }
  
  private void brace(Configuration configuration, boolean start)
  {
    if (start) {
      this.connection = this.provider.acquire();
    }
    try
    {
      boolean autoCommit = autoCommit(configuration);
      if (autoCommit == true) {
        connection(configuration).setAutoCommit(!start);
      }
    }
    finally
    {
      if (!start)
      {
        this.provider.release(this.connection);
        
        this.connection = null;
        configuration.data().remove("org.jooq.configuration.default-transaction-provider-connection-provider");
      }
    }
  }
}
