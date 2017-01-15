package mineplex.core.message.redis;

import java.util.UUID;
import mineplex.serverdata.commands.ServerCommand;

public class RedisMessage
  extends ServerCommand
{
  private String _message;
  private String _sender;
  private String _sendingServer;
  private String _target;
  private String _rank;
  private UUID _uuid = UUID.randomUUID();
  
  public RedisMessage(String sendingServer, String sender, String targetServer, String target, String message, String rank)
  {
    super(new String[0]);
    
    this._sender = sender;
    this._target = target;
    this._message = message;
    this._sendingServer = sendingServer;
    this._rank = rank;
    if (targetServer != null) {
      setTargetServers(new String[] { targetServer });
    }
  }
  
  public UUID getUUID()
  {
    return this._uuid;
  }
  
  public String getRank()
  {
    return this._rank;
  }
  
  public boolean isStaffMessage()
  {
    return getTargetServers().length == 0;
  }
  
  public String getMessage()
  {
    return this._message;
  }
  
  public String getSender()
  {
    return this._sender;
  }
  
  public String getSendingServer()
  {
    return this._sendingServer;
  }
  
  public String getTarget()
  {
    return this._target;
  }
  
  public void run() {}
}
