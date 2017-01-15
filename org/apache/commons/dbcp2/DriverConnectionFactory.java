package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class DriverConnectionFactory
  implements ConnectionFactory
{
  private final Driver _driver;
  private final String _connectUri;
  private final Properties _props;
  
  public DriverConnectionFactory(Driver driver, String connectUri, Properties props)
  {
    this._driver = driver;
    this._connectUri = connectUri;
    this._props = props;
  }
  
  public Connection createConnection()
    throws SQLException
  {
    return this._driver.connect(this._connectUri, this._props);
  }
  
  public String toString()
  {
    return getClass().getName() + " [" + String.valueOf(this._driver) + ";" + String.valueOf(this._connectUri) + ";" + String.valueOf(this._props) + "]";
  }
}
