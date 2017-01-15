package mineplex.core.gadget.event;

import java.util.List;
import mineplex.core.gadget.types.Gadget;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GadgetBlockEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Gadget _gadget;
  private List<Block> _blocks;
  private boolean _cancelled = false;
  
  public GadgetBlockEvent(Gadget gadget, List<Block> blocks)
  {
    this._gadget = gadget;
    this._blocks = blocks;
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
  
  public List<Block> getBlocks()
  {
    return this._blocks;
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
