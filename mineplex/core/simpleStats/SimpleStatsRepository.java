package mineplex.core.simpleStats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.database.DBPool;

public class SimpleStatsRepository
{
  private static Object _connectionLock = new Object();
  private static String CREATE_STATS_TABLE = "CREATE TABLE IF NOT EXISTS simpleStats (id INT NOT NULL AUTO_INCREMENT, statName VARCHAR(64), statValue VARCHAR(64), PRIMARY KEY (id));";
  private static String RETRIEVE_STATS_RECORDS = "SELECT simpleStats.statName, simpleStats.statValue FROM simpleStats;";
  private static String STORE_STATS_RECORD = "INSERT INTO simpleStats (statName,statValue) VALUES(?,?);";
  private static String RETRIEVE_STAT_RECORD = "SELECT simpleStats.statName, simpleStats.statValue FROM simpleStats WHERE statName = '?';";
  private Connection _connection = null;
  
  /* Error */
  public void initialize()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: ldc 45
    //   4: invokestatic 47	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   7: pop
    //   8: aload_0
    //   9: invokestatic 53	mineplex/core/database/DBPool:getUrl	()Ljava/lang/String;
    //   12: invokestatic 59	mineplex/core/database/DBPool:getUser	()Ljava/lang/String;
    //   15: invokestatic 62	mineplex/core/database/DBPool:getPass	()Ljava/lang/String;
    //   18: invokestatic 65	java/sql/DriverManager:getConnection	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/sql/Connection;
    //   21: putfield 40	mineplex/core/simpleStats/SimpleStatsRepository:_connection	Ljava/sql/Connection;
    //   24: aload_0
    //   25: getfield 40	mineplex/core/simpleStats/SimpleStatsRepository:_connection	Ljava/sql/Connection;
    //   28: getstatic 24	mineplex/core/simpleStats/SimpleStatsRepository:CREATE_STATS_TABLE	Ljava/lang/String;
    //   31: invokeinterface 71 2 0
    //   36: astore_1
    //   37: aload_1
    //   38: invokeinterface 77 1 0
    //   43: pop
    //   44: goto +54 -> 98
    //   47: astore_2
    //   48: aload_2
    //   49: invokevirtual 83	java/lang/Exception:printStackTrace	()V
    //   52: aload_1
    //   53: ifnull +65 -> 118
    //   56: aload_1
    //   57: invokeinterface 88 1 0
    //   62: goto +56 -> 118
    //   65: astore 4
    //   67: aload 4
    //   69: invokevirtual 91	java/sql/SQLException:printStackTrace	()V
    //   72: goto +46 -> 118
    //   75: astore_3
    //   76: aload_1
    //   77: ifnull +19 -> 96
    //   80: aload_1
    //   81: invokeinterface 88 1 0
    //   86: goto +10 -> 96
    //   89: astore 4
    //   91: aload 4
    //   93: invokevirtual 91	java/sql/SQLException:printStackTrace	()V
    //   96: aload_3
    //   97: athrow
    //   98: aload_1
    //   99: ifnull +19 -> 118
    //   102: aload_1
    //   103: invokeinterface 88 1 0
    //   108: goto +10 -> 118
    //   111: astore 4
    //   113: aload 4
    //   115: invokevirtual 91	java/sql/SQLException:printStackTrace	()V
    //   118: return
    // Line number table:
    //   Java source line #25	-> byte code offset #0
    //   Java source line #29	-> byte code offset #2
    //   Java source line #31	-> byte code offset #8
    //   Java source line #34	-> byte code offset #24
    //   Java source line #35	-> byte code offset #37
    //   Java source line #36	-> byte code offset #44
    //   Java source line #37	-> byte code offset #47
    //   Java source line #39	-> byte code offset #48
    //   Java source line #43	-> byte code offset #52
    //   Java source line #47	-> byte code offset #56
    //   Java source line #48	-> byte code offset #62
    //   Java source line #49	-> byte code offset #65
    //   Java source line #51	-> byte code offset #67
    //   Java source line #42	-> byte code offset #75
    //   Java source line #43	-> byte code offset #76
    //   Java source line #47	-> byte code offset #80
    //   Java source line #48	-> byte code offset #86
    //   Java source line #49	-> byte code offset #89
    //   Java source line #51	-> byte code offset #91
    //   Java source line #54	-> byte code offset #96
    //   Java source line #43	-> byte code offset #98
    //   Java source line #47	-> byte code offset #102
    //   Java source line #48	-> byte code offset #108
    //   Java source line #49	-> byte code offset #111
    //   Java source line #51	-> byte code offset #113
    //   Java source line #55	-> byte code offset #118
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	119	0	this	SimpleStatsRepository
    //   1	102	1	preparedStatement	PreparedStatement
    //   47	2	2	exception	Exception
    //   75	22	3	localObject	Object
    //   65	3	4	e	SQLException
    //   89	3	4	e	SQLException
    //   111	3	4	e	SQLException
    // Exception table:
    //   from	to	target	type
    //   2	44	47	java/lang/Exception
    //   56	62	65	java/sql/SQLException
    //   2	52	75	finally
    //   80	86	89	java/sql/SQLException
    //   102	108	111	java/sql/SQLException
  }
  
  public NautHashMap<String, String> retrieveStatRecords()
  {
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    NautHashMap<String, String> statRecords = new NautHashMap();
    try
    {
      synchronized (_connectionLock)
      {
        if (this._connection.isClosed()) {
          this._connection = DriverManager.getConnection(DBPool.getUrl(), DBPool.getUser(), DBPool.getPass());
        }
        preparedStatement = this._connection.prepareStatement(RETRIEVE_STATS_RECORDS);
        
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          statRecords.put(resultSet.getString(1), resultSet.getString(2));
        }
      }
    }
    catch (Exception exception)
    {
      exception = exception;
      
      exception.printStackTrace();
      if (preparedStatement != null) {
        try
        {
          preparedStatement.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
      if (resultSet == null) {
        return statRecords;
      }
      try
      {
        resultSet.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    finally
    {
      localObject1 = 
      
        finally;
      if (preparedStatement != null) {
        try
        {
          preparedStatement.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
      if (resultSet != null) {
        try
        {
          resultSet.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
      throw ((Throwable)localObject1);
    }
    if (preparedStatement != null) {
      try
      {
        preparedStatement.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    if (resultSet != null) {
      try
      {
        resultSet.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    return statRecords;
  }
  
  public void storeStatValue(String statName, String statValue)
  {
    PreparedStatement preparedStatement = null;
    try
    {
      synchronized (_connectionLock)
      {
        if (this._connection.isClosed()) {
          this._connection = DriverManager.getConnection(DBPool.getUrl(), DBPool.getUser(), DBPool.getPass());
        }
        preparedStatement = this._connection.prepareStatement(STORE_STATS_RECORD);
        preparedStatement.setString(1, statName);
        preparedStatement.setString(2, statValue);
        
        preparedStatement.executeUpdate();
      }
    }
    catch (Exception exception)
    {
      exception = exception;
      
      exception.printStackTrace();
      if (preparedStatement == null) {
        return;
      }
      try
      {
        preparedStatement.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    finally
    {
      localObject1 = 
      
        finally;
      if (preparedStatement != null) {
        try
        {
          preparedStatement.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
      throw ((Throwable)localObject1);
    }
    if (preparedStatement != null) {
      try
      {
        preparedStatement.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public NautHashMap<String, String> retrieveStat(String statName)
  {
    ResultSet resultSet = null;
    PreparedStatement preparedStatement = null;
    NautHashMap<String, String> statRecords = new NautHashMap();
    try
    {
      synchronized (_connectionLock)
      {
        if (this._connection.isClosed()) {
          this._connection = DriverManager.getConnection(DBPool.getUrl(), DBPool.getUser(), DBPool.getPass());
        }
        preparedStatement = this._connection.prepareStatement(RETRIEVE_STAT_RECORD);
        preparedStatement.setString(1, statName);
        
        resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          statRecords.put(resultSet.getString(1), resultSet.getString(2));
        }
      }
    }
    catch (Exception exception)
    {
      exception = exception;
      
      exception.printStackTrace();
      if (preparedStatement != null) {
        try
        {
          preparedStatement.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
      if (resultSet == null) {
        break label253;
      }
      try
      {
        resultSet.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    finally
    {
      localObject1 = 
      
        finally;
      if (preparedStatement != null) {
        try
        {
          preparedStatement.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
      if (resultSet != null) {
        try
        {
          resultSet.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
      throw ((Throwable)localObject1);
    }
    if (preparedStatement != null) {
      try
      {
        preparedStatement.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    if (resultSet != null) {
      try
      {
        resultSet.close();
      }
      catch (SQLException e)
      {
        e.printStackTrace();
      }
    }
    label253:
    return statRecords;
  }
}
