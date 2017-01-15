package mineplex.core.account.event;

import java.util.UUID;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ClientWebPunishResponseEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private String _response;
  private UUID _uuid;
  
  public ClientWebPunishResponseEvent(String response, UUID uuid)
  {
    this._response = response;
    this._uuid = uuid;
  }
  
  public String GetResponse()
  {
    return this._response;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public UUID getUniqueId()
  {
    return this._uuid;
  }
}
