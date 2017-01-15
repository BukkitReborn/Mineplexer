package mineplex.core.teleport.redis;

import java.util.UUID;
import mineplex.serverdata.commands.ServerCommand;

public class RedisLocate
  extends ServerCommand
{
  private String _sender;
  private String _sendingServer;
  private String _target;
  private UUID _uuid = UUID.randomUUID();
  
  public RedisLocate(String sendingServer, String sender, String target)
  {
    super(new String[0]);
    
    this._sender = sender;
    this._target = target;
    this._sendingServer = sendingServer;
  }
  
  public String getSender()
  {
    return this._sender;
  }
  
  public String getServer()
  {
    return this._sendingServer;
  }
  
  public String getTarget()
  {
    return this._target;
  }
  
  public UUID getUUID()
  {
    return this._uuid;
  }
}
