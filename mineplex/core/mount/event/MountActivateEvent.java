package mineplex.core.mount.event;

import mineplex.core.mount.Mount;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MountActivateEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player _player;
  private Mount<?> _mount;
  private boolean _cancelled = false;
  
  public MountActivateEvent(Player player, Mount<?> mount)
  {
    this._player = player;
    this._mount = mount;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public Mount getMount()
  {
    return this._mount;
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
