package mineplex.core.teleport.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MineplexTeleportEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player _entity;
  private Location _loc;
  private boolean _cancelled = false;
  
  public MineplexTeleportEvent(Player entity, Location loc)
  {
    this._entity = entity;
    this._loc = loc;
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
    return this._entity;
  }
  
  public Location getLocation()
  {
    return this._loc;
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
