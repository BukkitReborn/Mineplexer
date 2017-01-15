package mineplex.serverdata.data;

import java.util.HashMap;
import java.util.Map;
import mineplex.serverdata.Region;

public class DedicatedServer
{
  public static final int DEFAULT_CPU = 32;
  public static final int DEFAULT_RAM = 14000;
  private String _name;
  private String _publicAddress;
  private String _privateAddress;
  private Region _region;
  private int _availableCpu;
  private int _availableRam;
  private int _maxCpu;
  private int _maxRam;
  private Map<String, Integer> _serverCounts;
  
  public String getName()
  {
    return this._name;
  }
  
  public String getPublicAddress()
  {
    return this._publicAddress;
  }
  
  public String getPrivateAddress()
  {
    return this._privateAddress;
  }
  
  public Region getRegion()
  {
    return this._region;
  }
  
  public boolean isUsRegion()
  {
    return this._region == Region.US;
  }
  
  public int getAvailableCpu()
  {
    return this._availableCpu;
  }
  
  public int getAvailableRam()
  {
    return this._availableRam;
  }
  
  public int getMaxCpu()
  {
    return this._maxCpu;
  }
  
  public int getMaxRam()
  {
    return this._maxRam;
  }
  
  public DedicatedServer(Map<String, String> data)
  {
    this._name = ((String)data.get("name"));
    this._publicAddress = ((String)data.get("publicAddress"));
    this._privateAddress = ((String)data.get("privateAddress"));
    this._region = Region.valueOf(((String)data.get("region")).toUpperCase());
    this._availableCpu = Integer.valueOf((String)data.get("cpu")).intValue();
    this._availableRam = Integer.valueOf((String)data.get("ram")).intValue();
    this._maxCpu = Integer.valueOf((String)data.get("cpu")).intValue();
    this._maxRam = Integer.valueOf((String)data.get("ram")).intValue();
    this._serverCounts = new HashMap();
  }
  
  public void setServerCount(ServerGroup serverGroup, int serverCount)
  {
    if (this._serverCounts.containsKey(serverGroup.getName()))
    {
      int currentAmount = ((Integer)this._serverCounts.get(serverGroup.getName())).intValue();
      this._availableCpu += serverGroup.getRequiredCpu() * currentAmount;
      this._availableRam += serverGroup.getRequiredRam() * currentAmount;
    }
    this._serverCounts.put(serverGroup.getName(), Integer.valueOf(serverCount));
    this._availableCpu -= serverGroup.getRequiredCpu() * serverCount;
    this._availableRam -= serverGroup.getRequiredRam() * serverCount;
  }
  
  public int getServerCount(ServerGroup serverGroup)
  {
    String groupName = serverGroup.getName();
    return this._serverCounts.containsKey(groupName) ? ((Integer)this._serverCounts.get(groupName)).intValue() : 0;
  }
  
  public void incrementServerCount(ServerGroup serverGroup)
  {
    setServerCount(serverGroup, getServerCount(serverGroup) + 1);
  }
}
