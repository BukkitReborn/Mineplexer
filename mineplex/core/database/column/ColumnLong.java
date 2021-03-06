package mineplex.core.database.column;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnLong
  extends Column<Long>
{
  public ColumnLong(String name)
  {
    super(name);
    this.Value = Long.valueOf(0L);
  }
  
  public ColumnLong(String name, Long value)
  {
    super(name, value);
  }
  
  public String getCreateString()
  {
    return this.Name + " LONG";
  }
  
  public Long getValue(ResultSet resultSet)
    throws SQLException
  {
    return Long.valueOf(resultSet.getLong(this.Name));
  }
  
  public void setValue(PreparedStatement preparedStatement, int columnNumber)
    throws SQLException
  {
    preparedStatement.setLong(columnNumber, ((Long)this.Value).longValue());
  }
  
  public ColumnLong clone()
  {
    return new ColumnLong(this.Name, (Long)this.Value);
  }
}
