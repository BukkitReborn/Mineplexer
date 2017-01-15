package mineplex.core.teleport.redis;

import java.util.UUID;
import mineplex.serverdata.commands.ServerCommand;

public class RedisLocateCallback
  extends ServerCommand
{
  private String _locatedPlayer;
  private String _server;
  private String _receivingPlayer;
  private UUID _uuid;
  
  public RedisLocateCallback(RedisLocate command, String server, String targetName)
  {
    super(new String[0]);
    
    this._uuid = command.getUUID();
    this._receivingPlayer = command.getSender();
    this._locatedPlayer = targetName;
    this._server = server;
    
    setTargetServers(new String[] { command.getServer() });
  }
  
  public String getLocatedPlayer()
  {
    return this._locatedPlayer;
  }
  
  public String getServer()
  {
    return this._server;
  }
  
  public String getReceivingPlayer()
  {
    return this._receivingPlayer;
  }
  
  public UUID getUUID()
  {
    return this._uuid;
  }
}
