package mineplex.bungee.motd;

import mineplex.serverdata.data.Data;

public class GlobalMotd
  implements Data
{
  private String _name;
  private String _headline;
  private String _motd;
  
  public String getHeadline()
  {
    return this._headline;
  }
  
  public String getMotd()
  {
    return this._motd;
  }
  
  public GlobalMotd(String name, String headline, String motd)
  {
    this._name = name;
    this._headline = headline;
    this._motd = motd;
  }
  
  public String getDataId()
  {
    return this._name;
  }
}
