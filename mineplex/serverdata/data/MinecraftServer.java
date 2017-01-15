package mineplex.serverdata.data;

public class MinecraftServer
{
  private String _name;
  private String _group;
  private String _motd;
  private int _playerCount;
  private int _maxPlayerCount;
  private int _tps;
  private int _ram;
  private int _maxRam;
  private String _publicAddress;
  private int _port;
  private long _startUpDate;
  
  public String getName()
  {
    return this._name;
  }
  
  public String getGroup()
  {
    return this._group;
  }
  
  public String getMotd()
  {
    return this._motd;
  }
  
  public int getPlayerCount()
  {
    return this._playerCount;
  }
  
  public void incrementPlayerCount(int amount)
  {
    this._playerCount += amount;
  }
  
  public int getMaxPlayerCount()
  {
    return this._maxPlayerCount;
  }
  
  public int getTps()
  {
    return this._tps;
  }
  
  public int getRam()
  {
    return this._ram;
  }
  
  public int getMaxRam()
  {
    return this._maxRam;
  }
  
  public String getPublicAddress()
  {
    return this._publicAddress;
  }
  
  public int getPort()
  {
    return this._port;
  }
  
  public MinecraftServer(String name, String group, String motd, String publicAddress, int port, int playerCount, int maxPlayerCount, int tps, int ram, int maxRam, long startUpDate)
  {
    this._name = name;
    this._group = group;
    this._motd = motd;
    this._playerCount = playerCount;
    this._maxPlayerCount = maxPlayerCount;
    this._tps = tps;
    this._ram = ram;
    this._maxRam = maxRam;
    this._publicAddress = publicAddress;
    this._port = port;
    this._startUpDate = startUpDate;
  }
  
  public boolean isEmpty()
  {
    return this._playerCount == 0;
  }
  
  public double getUptime()
  {
    return System.currentTimeMillis() / 1000.0D - this._startUpDate;
  }
  
  public boolean isJoinable()
  {
    if ((this._motd != null) && ((this._motd.contains("Starting")) || (this._motd.contains("Recruiting")) || 
      (this._motd.contains("Waiting")) || (this._motd.contains("Open in")) || (this._motd.isEmpty()) || (this._motd.contains("Generating")))) {
      if (this._playerCount < this._maxPlayerCount)
      {
        int availableSlots = this._maxPlayerCount - this._playerCount;
        return availableSlots > 20;
      }
    }
    return false;
  }
  
  public void setGroup(String group)
  {
    this._group = group;
  }
  
  public void setName(String name)
  {
    this._name = name;
  }
}
