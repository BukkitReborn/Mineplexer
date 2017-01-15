package mineplex.core.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface ResultSetCallable
{
  public abstract void processResultSet(ResultSet paramResultSet)
    throws SQLException;
}
