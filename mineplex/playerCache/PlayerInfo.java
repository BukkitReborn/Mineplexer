package mineplex.playerCache;

import java.util.UUID;
import mineplex.serverdata.data.Data;

public class PlayerInfo
  implements Data
{
  private int _id;
  private UUID _uuid;
  private String _name;
  private boolean _online;
  private long _lastUniqueLogin;
  private long _loginTime;
  private int _sessionId;
  private int _version;
  
  public PlayerInfo(int id, UUID uuid, String name, int version)
  {
    this._id = id;
    this._uuid = uuid;
    this._name = name;
    this._version = version;
  }
  
  public String getDataId()
  {
    return this._uuid.toString();
  }
  
  public int getId()
  {
    return this._id;
  }
  
  public UUID getUUID()
  {
    return this._uuid;
  }
  
  public String getName()
  {
    return this._name;
  }
  
  public boolean getOnline()
  {
    return this._online;
  }
  
  public long getLastUniqueLogin()
  {
    return this._lastUniqueLogin;
  }
  
  public long getLoginTime()
  {
    return this._loginTime;
  }
  
  public int getSessionId()
  {
    return this._sessionId;
  }
  
  public int getVersion()
  {
    return this._version;
  }
  
  public void setSessionId(int sessionId)
  {
    this._sessionId = sessionId;
  }
  
  public void setName(String name)
  {
    this._name = name;
  }
  
  public void setVersion(int version)
  {
    this._version = version;
  }
}
