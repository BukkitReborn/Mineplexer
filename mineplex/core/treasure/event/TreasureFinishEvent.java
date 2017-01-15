package mineplex.core.treasure.event;

import mineplex.core.treasure.Treasure;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TreasureFinishEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private final Player _player;
  private final Treasure _treasure;
  
  public TreasureFinishEvent(Player player, Treasure treasure)
  {
    this._player = player;
    this._treasure = treasure;
  }
  
  public Player getPlayer()
  {
    return this._player;
  }
  
  public Treasure getTreasure()
  {
    return this._treasure;
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
