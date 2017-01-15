package mineplex.core.database.column;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ColumnTimestamp
  extends Column<Timestamp>
{
  public ColumnTimestamp(String name)
  {
    super(name);
  }
  
  public ColumnTimestamp(String name, Timestamp value)
  {
    super(name, value);
  }
  
  public String getCreateString()
  {
    return this.Name + " TIMESTAMP";
  }
  
  public Timestamp getValue(ResultSet resultSet)
    throws SQLException
  {
    return resultSet.getTimestamp(this.Name);
  }
  
  public void setValue(PreparedStatement preparedStatement, int columnNumber)
    throws SQLException
  {
    preparedStatement.setTimestamp(columnNumber, (Timestamp)this.Value);
  }
  
  public ColumnTimestamp clone()
  {
    return new ColumnTimestamp(this.Name, (Timestamp)this.Value);
  }
}
