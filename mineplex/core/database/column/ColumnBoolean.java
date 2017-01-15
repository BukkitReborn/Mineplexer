package mineplex.core.database.column;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnBoolean
  extends Column<Boolean>
{
  public ColumnBoolean(String name)
  {
    super(name);
  }
  
  public ColumnBoolean(String name, boolean value)
  {
    super(name, Boolean.valueOf(value));
  }
  
  public String getCreateString()
  {
    return this.Name + " BOOLEAN";
  }
  
  public Boolean getValue(ResultSet resultSet)
    throws SQLException
  {
    return Boolean.valueOf(resultSet.getBoolean(this.Name));
  }
  
  public void setValue(PreparedStatement preparedStatement, int columnNumber)
    throws SQLException
  {
    preparedStatement.setBoolean(columnNumber, ((Boolean)this.Value).booleanValue());
  }
  
  public ColumnBoolean clone()
  {
    return new ColumnBoolean(this.Name, ((Boolean)this.Value).booleanValue());
  }
}
