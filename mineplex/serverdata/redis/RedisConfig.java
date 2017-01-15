package mineplex.serverdata.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import mineplex.serverdata.servers.ConnectionData;
import mineplex.serverdata.servers.ConnectionData.ConnectionType;

public class RedisConfig
{
  private static final String DEFAULT_IP = "10.3.203.80";
  private static final int DEFAULT_PORT = 6379;
  private static Random random = new Random();
  private List<ConnectionData> _connections;
  
  public RedisConfig(List<ConnectionData> connections)
  {
    this._connections = connections;
  }
  
  public RedisConfig()
  {
    this._connections = new ArrayList();
    this._connections.add(new ConnectionData("10.3.203.80", 6379, ConnectionData.ConnectionType.MASTER, "DefaultConnection"));
  }
  
  public ConnectionData getConnection()
  {
    return getConnection(true, null);
  }
  
  public ConnectionData getConnection(boolean writeable, String name)
  {
    List<ConnectionData> connections = getConnections(writeable, name);
    if (connections.size() > 0)
    {
      int index = random.nextInt(connections.size());
      return (ConnectionData)connections.get(index);
    }
    return null;
  }
  
  public List<ConnectionData> getConnections(boolean writeable, String name)
  {
    List<ConnectionData> connections = new ArrayList();
    ConnectionData.ConnectionType type = writeable ? ConnectionData.ConnectionType.MASTER : ConnectionData.ConnectionType.SLAVE;
    for (ConnectionData connection : this._connections) {
      if ((connection.getType() == type) && (connection.nameMatches(name))) {
        connections.add(connection);
      }
    }
    return connections;
  }
}
