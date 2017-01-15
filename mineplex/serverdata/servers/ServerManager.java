package mineplex.serverdata.servers;

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mineplex.serverdata.Region;
import mineplex.serverdata.redis.RedisConfig;
import mineplex.serverdata.redis.RedisServerRepository;

public class ServerManager
{
  public static final String SERVER_STATUS_LABEL = "ServerStatus";
  private static RedisConfig _config;
  private static Map<Region, ServerRepository> repositories = new HashMap();
  
  private static ServerRepository getServerRepository(ConnectionData writeConn, ConnectionData readConn, Region region)
  {
    if (repositories.containsKey(region)) {
      return (ServerRepository)repositories.get(region);
    }
    ServerRepository repository = new RedisServerRepository(writeConn, readConn, region);
    repositories.put(region, repository);
    return repository;
  }
  
  public static ServerRepository getServerRepository(Region region)
  {
    return getServerRepository(getMasterConnection(), getSlaveConnection(), region);
  }
  
  public static ConnectionData getMasterConnection()
  {
    return getConnection(true);
  }
  
  public static ConnectionData getSlaveConnection()
  {
    return getConnection(false);
  }
  
  public static ConnectionData getConnection(boolean writeable, String name)
  {
    return getConfig().getConnection(writeable, name);
  }
  
  public static ConnectionData getConnection(boolean writeable)
  {
    return getConnection(writeable, "DefaultConnection");
  }
  
  public static RedisConfig getConfig()
  {
    if (_config == null) {
      try
      {
        File configFile = new File("redis-config.dat");
        if (configFile.exists())
        {
          List<ConnectionData> connections = new ArrayList();
          List<String> lines = Files.readAllLines(configFile.toPath(), Charset.defaultCharset());
          for (String line : lines)
          {
            ConnectionData connection = deserializeConnection(line);
            connections.add(connection);
          }
          _config = new RedisConfig(connections);
        }
        else
        {
          log("redis-config.dat not found at " + configFile.toPath().toString());
          _config = new RedisConfig();
        }
      }
      catch (Exception exception)
      {
        exception.printStackTrace();
        log("---Unable To Parse Redis Configuration File---");
      }
    }
    return _config;
  }
  
  private static ConnectionData deserializeConnection(String line)
  {
    String[] args = line.split(" ");
    if (args.length >= 2)
    {
      String ip = args[0];
      int port = Integer.parseInt(args[1]);
      String typeName = args.length >= 3 ? args[2].toUpperCase() : "MASTER";
      ConnectionData.ConnectionType type = ConnectionData.ConnectionType.valueOf(typeName);
      String name = args.length >= 4 ? args[3] : "DefaultConnection";
      
      return new ConnectionData(ip, port, type, name);
    }
    return null;
  }
  
  private static void log(String message)
  {
    System.out.println(String.format("[ServerManager] %s", new Object[] { message }));
  }
}
