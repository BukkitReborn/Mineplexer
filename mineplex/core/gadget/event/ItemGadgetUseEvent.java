package mineplex.core.gadget.event;

import mineplex.core.gadget.types.ItemGadget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ItemGadgetUseEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player _player;
  private ItemGadget _gadget;
  private int _count;
  private boolean _cancelled = false;
  
  public ItemGadgetUseEvent(Player player, ItemGadget gadget, int count)
  {
    this._player = player;
    this._gadget = gadget;
    this._count = count;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public int getCount()
  {
    return this._count;
  }
  
  public ItemGadget getGadget()
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
