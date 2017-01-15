package mineplex.core.gadget.event;

import mineplex.core.gadget.types.Gadget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GadgetActivateEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player _player;
  private Gadget _gadget;
  private boolean _cancelled = false;
  
  public GadgetActivateEvent(Player player, Gadget gadget)
  {
    this._player = player;
    this._gadget = gadget;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public Gadget getGadget()
  {
    return this._gadget;
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
