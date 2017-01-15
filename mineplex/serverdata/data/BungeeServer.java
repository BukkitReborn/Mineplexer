package mineplex.serverdata.data;

import mineplex.serverdata.Region;

public class BungeeServer
  implements Data
{
  private String _name;
  private Region _region;
  private int _playerCount;
  private String _publicAddress;
  private int _port;
  private boolean _connected;
  
  public String getName()
  {
    return this._name;
  }
  
  public Region getRegion()
  {
    return this._region;
  }
  
  public int getPlayerCount()
  {
    return this._playerCount;
  }
  
  public String getPublicAddress()
  {
    return this._publicAddress;
  }
  
  public int getPort()
  {
    return this._port;
  }
  
  public boolean isConnected()
  {
    return this._connected;
  }
  
  public BungeeServer(String name, Region region, String publicAddress, int port, int playerCount, boolean connected)
  {
    this._name = name;
    this._region = region;
    this._playerCount = playerCount;
    this._publicAddress = publicAddress;
    this._port = port;
    this._connected = connected;
  }
  
  public String getDataId()
  {
    return this._name;
  }
}
