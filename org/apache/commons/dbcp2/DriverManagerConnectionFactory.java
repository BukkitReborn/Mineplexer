package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DriverManagerConnectionFactory
  implements ConnectionFactory
{
  static
  {
    DriverManager.getDrivers();
  }
  
  public DriverManagerConnectionFactory(String connectUri, Properties props)
  {
    this._connectUri = connectUri;
    this._props = props;
  }
  
  public DriverManagerConnectionFactory(String connectUri, String uname, String passwd)
  {
    this._connectUri = connectUri;
    this._uname = uname;
    this._passwd = passwd;
  }
  
  public Connection createConnection()
    throws SQLException
  {
    if (null == this._props)
    {
      if ((this._uname == null) && (this._passwd == null)) {
        return DriverManager.getConnection(this._connectUri);
      }
      return DriverManager.getConnection(this._connectUri, this._uname, this._passwd);
    }
    return DriverManager.getConnection(this._connectUri, this._props);
  }
  
  private String _connectUri = null;
  private String _uname = null;
  private String _passwd = null;
  private Properties _props = null;
}
