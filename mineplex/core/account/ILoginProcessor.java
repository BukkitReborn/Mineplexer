package mineplex.core.account;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract interface ILoginProcessor
{
  public abstract String getName();
  
  public abstract void processLoginResultSet(String paramString, int paramInt, ResultSet paramResultSet)
    throws SQLException;
  
  public abstract String getQuery(int paramInt, String paramString1, String paramString2);
}
