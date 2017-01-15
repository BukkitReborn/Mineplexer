package org.apache.commons.dbcp2;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DataSourceConnectionFactory
  implements ConnectionFactory
{
  private final String _uname;
  private final String _passwd;
  private final DataSource _source;
  
  public DataSourceConnectionFactory(DataSource source)
  {
    this(source, null, null);
  }
  
  public DataSourceConnectionFactory(DataSource source, String uname, String passwd)
  {
    this._source = source;
    this._uname = uname;
    this._passwd = passwd;
  }
  
  public Connection createConnection()
    throws SQLException
  {
    if ((null == this._uname) && (null == this._passwd)) {
      return this._source.getConnection();
    }
    return this._source.getConnection(this._uname, this._passwd);
  }
}
