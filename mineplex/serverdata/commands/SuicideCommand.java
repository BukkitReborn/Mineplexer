package mineplex.serverdata.commands;

import mineplex.serverdata.Region;

public class SuicideCommand
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
  
  public SuicideCommand(String server, Region region)
  {
    super(new String[0]);
    
    this._server = server;
    this._region = region;
  }
  
  public void run() {}
}
