package org.apache.commons.dbcp2.managed;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbcp2.ConnectionFactory;

public abstract interface XAConnectionFactory
  extends ConnectionFactory
{
  public abstract TransactionRegistry getTransactionRegistry();
  
  public abstract Connection createConnection()
    throws SQLException;
}
