package mineplex.serverdata.commands;

public abstract class ServerCommand
{
  private String[] _targetServers;
  
  public ServerCommand(String... targetServers)
  {
    this._targetServers = targetServers;
  }
  
  public void setTargetServers(String... targetServers)
  {
    this._targetServers = targetServers;
  }
  
  public String[] getTargetServers()
  {
    if (this._targetServers == null) {
      this._targetServers = new String[0];
    }
    return this._targetServers;
  }
  
  public void run() {}
  
  public boolean isTargetServer(String serverName)
  {
    if (getTargetServers().length == 0) {
      return true;
    }
    String[] arrayOfString;
    int j = (arrayOfString = getTargetServers()).length;
    for (int i = 0; i < j; i++)
    {
      String targetServer = arrayOfString[i];
      if (targetServer.equalsIgnoreCase(serverName)) {
        return true;
      }
    }
    return false;
  }
  
  public void publish()
  {
    ServerCommandManager.getInstance().publishCommand(this);
  }
}
