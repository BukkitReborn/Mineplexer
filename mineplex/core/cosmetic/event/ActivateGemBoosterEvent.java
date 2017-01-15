package mineplex.core.cosmetic.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ActivateGemBoosterEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player _player;
  private boolean _cancelled = false;
  
  public ActivateGemBoosterEvent(Player player)
  {
    this._player = player;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public Player getPlayer()
  {
    return this._player;
  }
  
  public void setCancelled(boolean cancel)
  {
    this._cancelled = cancel;
  }
  
  public boolean isCancelled()
  {
    return this._cancelled;
  }
}
