package mineplex.core.database.column;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnInt
  extends Column<Integer>
{
  public ColumnInt(String name)
  {
    super(name);
    this.Value = Integer.valueOf(0);
  }
  
  public ColumnInt(String name, int value)
  {
    super(name, Integer.valueOf(value));
  }
  
  public String getCreateString()
  {
    return this.Name + " INT";
  }
  
  public Integer getValue(ResultSet resultSet)
    throws SQLException
  {
    return Integer.valueOf(resultSet.getInt(this.Name));
  }
  
  public void setValue(PreparedStatement preparedStatement, int columnNumber)
    throws SQLException
  {
    preparedStatement.setInt(columnNumber, ((Integer)this.Value).intValue());
  }
  
  public ColumnInt clone()
  {
    return new ColumnInt(this.Name, ((Integer)this.Value).intValue());
  }
}
