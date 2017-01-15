package mineplex.core.explosion;

import java.util.Collection;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ExplosionEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player _owner;
  private Collection<Block> _blocks;
  
  public ExplosionEvent(Collection<Block> blocks)
  {
    this(blocks, null);
  }
  
  public ExplosionEvent(Collection<Block> blocks, Player owner)
  {
    this._blocks = blocks;
    this._owner = owner;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public Collection<Block> GetBlocks()
  {
    return this._blocks;
  }
  
  public Player getOwner()
  {
    return this._owner;
  }
  
  public void setOwner(Player owner)
  {
    this._owner = owner;
  }
}
