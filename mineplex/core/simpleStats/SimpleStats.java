package mineplex.core.simpleStats;

import mineplex.core.MiniPlugin;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class SimpleStats
  extends MiniPlugin
{
  private static Object _transferLock = new Object();
  private SimpleStatsRepository _repository = new SimpleStatsRepository();
  private NautHashMap<String, String> _entries = new NautHashMap();
  
  public SimpleStats(JavaPlugin plugin)
  {
    super("SimpleStats", plugin);
    
    this._repository.initialize();
  }
  
  @EventHandler
  public void storeStatsUpdate(UpdateEvent updateEvent)
  {
    if (updateEvent.getType() != UpdateType.SLOW) {
      return;
    }
    Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        synchronized (SimpleStats._transferLock)
        {
          SimpleStats.this._entries = SimpleStats.this._repository.retrieveStatRecords();
        }
      }
    });
  }
  
  public void store(String statName, String statValue)
  {
    final String statNameFinal = statName;
    final String statValueFinal = statValue;
    
    Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        synchronized (SimpleStats._transferLock)
        {
          SimpleStats.this._repository.storeStatValue(statNameFinal, statValueFinal);
        }
      }
    });
  }
  
  public NautHashMap<String, String> getStat(String statName)
  {
    final String statNameFinal = statName;
    
    Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        synchronized (SimpleStats._transferLock)
        {
          SimpleStats.this._entries = SimpleStats.this._repository.retrieveStat(statNameFinal);
        }
      }
    });
    return this._entries;
  }
}
