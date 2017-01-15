package mineplex.core.database.column;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Column<Type>
{
  public String Name;
  public Type Value;
  
  public Column(String name)
  {
    this.Name = name;
  }
  
  public Column(String name, Type value)
  {
    this.Name = name;
    this.Value = value;
  }
  
  public abstract String getCreateString();
  
  public abstract Type getValue(ResultSet paramResultSet)
    throws SQLException;
  
  public abstract void setValue(PreparedStatement paramPreparedStatement, int paramInt)
    throws SQLException;
  
  public abstract Column<Type> clone();
}
