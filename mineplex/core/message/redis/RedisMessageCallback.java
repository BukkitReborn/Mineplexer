package mineplex.core.message.redis;

import java.util.UUID;
import mineplex.serverdata.commands.ServerCommand;

public class RedisMessageCallback
  extends ServerCommand
{
  private String _message;
  private String _setLastMessage;
  private String _target;
  private boolean _staffMessage;
  private UUID _uuid;
  
  public RedisMessageCallback(RedisMessage globalMessage, boolean staffMessage, String receivedPlayer, String message)
  {
    super(new String[0]);
    
    this._target = globalMessage.getSender();
    this._message = message;
    this._setLastMessage = receivedPlayer;
    this._uuid = globalMessage.getUUID();
    this._staffMessage = staffMessage;
    if (globalMessage.getSendingServer() != null) {
      setTargetServers(new String[] { globalMessage.getSendingServer() });
    }
  }
  
  public boolean isStaffMessage()
  {
    return this._staffMessage;
  }
  
  public String getLastReplied()
  {
    return this._setLastMessage;
  }
  
  public String getMessage()
  {
    return this._message;
  }
  
  public String getTarget()
  {
    return this._target;
  }
  
  public UUID getUUID()
  {
    return this._uuid;
  }
  
  public void run() {}
}
