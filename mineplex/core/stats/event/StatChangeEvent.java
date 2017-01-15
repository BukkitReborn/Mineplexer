package mineplex.core.stats.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StatChangeEvent
  extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private String _player;
  private String _statName;
  private long _valueBefore;
  private long _valueAfter;
  
  public StatChangeEvent(String player, String statName, long valueBefore, long valueAfter)
  {
    this._player = player;
    this._statName = statName;
    this._valueBefore = valueBefore;
    this._valueAfter = valueAfter;
  }
  
  public HandlerList getHandlers()
  {
    return handlers;
  }
  
  public static HandlerList getHandlerList()
  {
    return handlers;
  }
  
  public String getPlayerName()
  {
    return this._player;
  }
  
  public String getStatName()
  {
    return this._statName;
  }
  
  public long getValueBefore()
  {
    return this._valueBefore;
  }
  
  public long getValueAfter()
  {
    return this._valueAfter;
  }
}
