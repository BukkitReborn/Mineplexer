package mineplex.core.database.column;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnByte
  extends Column<Byte>
{
  public ColumnByte(String name)
  {
    super(name);
    this.Value = Byte.valueOf((byte)0);
  }
  
  public ColumnByte(String name, Byte value)
  {
    super(name, value);
  }
  
  public String getCreateString()
  {
    return this.Name + " TINYINT";
  }
  
  public Byte getValue(ResultSet resultSet)
    throws SQLException
  {
    return Byte.valueOf(resultSet.getByte(this.Name));
  }
  
  public void setValue(PreparedStatement preparedStatement, int columnNumber)
    throws SQLException
  {
    preparedStatement.setLong(columnNumber, ((Byte)this.Value).byteValue());
  }
  
  public ColumnByte clone()
  {
    return new ColumnByte(this.Name, (Byte)this.Value);
  }
}
