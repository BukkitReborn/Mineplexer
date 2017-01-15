package mineplex.core.message;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PrivateMessageEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private boolean _cancelled = false;
  private Player _sender;
  private Player _recipient;
  private String _msg;
  
  public PrivateMessageEvent(Player sender, Player recipient, String msg)
  {
    this._sender = sender;
    this._recipient = recipient;
    this._msg = msg;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public void setCancelled(boolean cancel)
  {
    this._cancelled = cancel;
  }
  
  public boolean isCancelled()
  {
    return this._cancelled;
  }
  
  public Player getSender()
  {
    return this._sender;
  }
  
  public Player getRecipient()
  {
    return this._recipient;
  }
  
  public String getMessage()
  {
    return this._msg;
  }
}
