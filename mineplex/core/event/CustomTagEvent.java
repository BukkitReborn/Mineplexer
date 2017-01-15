package mineplex.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CustomTagEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private Player _player;
  private int _entityId;
  private String _customName;
  
  public CustomTagEvent(Player player, int entityId, String customName)
  {
    this._player = player;
    this._entityId = entityId;
    this._customName = customName;
  }
  
  public Player getPlayer()
  {
    return this._player;
  }
  
  public String getCustomName()
  {
    return this._customName;
  }
  
  public void setCustomName(String customName)
  {
    this._customName = customName;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public int getEntityId()
  {
    return this._entityId;
  }
}
