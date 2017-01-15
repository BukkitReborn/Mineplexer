package mineplex.serverdata.commands;

import mineplex.serverdata.Region;

public class RestartCommand
  extends ServerCommand
{
  private String _server;
  private Region _region;
  
  public String getServerName()
  {
    return this._server;
  }
  
  public Region getRegion()
  {
    return this._region;
  }
  
  public RestartCommand(String server, Region region)
  {
    super(new String[0]);
    
    this._server = server;
    this._region = region;
  }
  
  public void run() {}
}
