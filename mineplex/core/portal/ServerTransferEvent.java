package mineplex.core.portal;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ServerTransferEvent
  extends Event
{
  private static final HandlerList _handlers = new HandlerList();
  private Player _player;
  private String _server;
  
  public ServerTransferEvent(Player player, String server)
  {
    this._player = player;
    this._server = server;
  }
  
  public HandlerList getHandlers()
  {
    return _handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return _handlers;
  }
  
  public Player getPlayer()
  {
    return this._player;
  }
  
  public String getServer()
  {
    return this._server;
  }
}
