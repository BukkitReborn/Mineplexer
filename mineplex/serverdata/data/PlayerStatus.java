package mineplex.serverdata.data;

public class PlayerStatus
  implements Data
{
  private String _name;
  private String _server;
  
  public String getName()
  {
    return this._name;
  }
  
  public String getServer()
  {
    return this._server;
  }
  
  public PlayerStatus(String name, String server)
  {
    this._name = name;
    this._server = server;
  }
  
  public String getDataId()
  {
    return this._name;
  }
}
