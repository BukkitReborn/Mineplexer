package mineplex.core.treasure.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TreasureStartEvent
  extends Event
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private Player _player;
  private boolean _cancelled = false;
  
  public TreasureStartEvent(Player player)
  {
    this._player = player;
  }
  
  public Player getPlayer()
  {
    return this._player;
  }
  
  public boolean isCancelled()
  {
    return this._cancelled;
  }
  
  public void setCancelled(boolean cancelled)
  {
    this._cancelled = cancelled;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
}
