package mineplex.core.database;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import mineplex.serverdata.servers.ConnectionData;
import org.apache.commons.dbcp2.BasicDataSource;

public final class DBPool
{
  public static final DataSource ACCOUNT = getDs("Account");
  public static final DataSource QUEUE = getDs("Queue");
  public static final DataSource MINEPLEX = getDs("Mineplex");
  public static final DataSource STATS_MINEPLEX = getDs("Stats_Mineplex");
  
  private static String getLine(Integer lineNo)
  {
    try
    {
      File configFile = new File("mysql-config.dat");
      if (configFile.exists())
      {
        List<ConnectionData> connections = new ArrayList();
        List<String> lines = Files.readAllLines(configFile.toPath(), Charset.defaultCharset());
        return (String)lines.get(lineNo.intValue());
      }
    }
    catch (Exception ex)
    {
      return "Failed to parse";
    }
    return "sht";
  }
  
  public static String getUrl()
  {
    return "jdbc:mysql://" + getLine(Integer.valueOf(0));
  }
  
  public static String getUser()
  {
    return getLine(Integer.valueOf(1));
  }
  
  public static String getPass()
  {
    return getLine(Integer.valueOf(2));
  }
  
  private static DataSource getDs(String part)
  {
    if (part == "Account") {
      return openDataSource(getUrl() + "/Account", getUser(), getPass());
    }
    if (part == "Queue") {
      return openDataSource(getUrl() + "/Queue", getUser(), getPass());
    }
    if (part == "Mineplex") {
      return openDataSource(getUrl() + "/Mineplex", getUser(), getPass());
    }
    if (part == "Stats_Mineplex") {
      return openDataSource(getUrl() + "/Mineplex", getUser(), getPass());
    }
    return openDataSource(getUrl() + "/Account", getUser(), getPass());
  }
  
  private static DataSource openDataSource(String url, String username, String password)
  {
    BasicDataSource source = new BasicDataSource();
    source.addConnectionProperty("autoReconnect", "true");
    source.addConnectionProperty("allowMultiQueries", "true");
    source.setDefaultTransactionIsolation(2);
    source.setDriverClassName("com.mysql.jdbc.Driver");
    source.setUrl(url);
    source.setUsername(username);
    source.setPassword(password);
    source.setMaxTotal(4);
    source.setMaxIdle(4);
    source.setTimeBetweenEvictionRunsMillis(180000L);
    source.setSoftMinEvictableIdleTimeMillis(180000L);
    
    return source;
  }
}
