package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.SQLException;

public abstract interface ConnectionFactory
{
  public abstract Connection createConnection()
    throws SQLException;
}
