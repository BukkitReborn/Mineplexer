package mineplex.serverdata.servers;

public class ConnectionData
{
  private ConnectionType _type;
  private String _name;
  private String _host;
  private int _port;
  
  public static enum ConnectionType
  {
    MASTER,  SLAVE;
  }
  
  public ConnectionType getType()
  {
    return this._type;
  }
  
  public String getName()
  {
    return this._name;
  }
  
  public String getHost()
  {
    return this._host;
  }
  
  public int getPort()
  {
    return this._port;
  }
  
  public ConnectionData(String host, int port, ConnectionType type, String name)
  {
    this._host = host;
    this._port = port;
    this._type = type;
    this._name = name;
  }
  
  public boolean nameMatches(String name)
  {
    return (name == null) || (name.equalsIgnoreCase(this._name));
  }
}
