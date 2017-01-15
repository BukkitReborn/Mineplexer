package mineplex.core.database.column;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColumnDouble
  extends Column<Double>
{
  public ColumnDouble(String name)
  {
    super(name);
    this.Value = Double.valueOf(0.0D);
  }
  
  public ColumnDouble(String name, Double value)
  {
    super(name, value);
  }
  
  public String getCreateString()
  {
    return this.Name + " DOUBLE";
  }
  
  public Double getValue(ResultSet resultSet)
    throws SQLException
  {
    return Double.valueOf(resultSet.getDouble(this.Name));
  }
  
  public void setValue(PreparedStatement preparedStatement, int columnNumber)
    throws SQLException
  {
    preparedStatement.setDouble(columnNumber, ((Double)this.Value).doubleValue());
  }
  
  public ColumnDouble clone()
  {
    return new ColumnDouble(this.Name, (Double)this.Value);
  }
}
