package mineplex.core.stats;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Set;
import mineplex.core.MiniDbClientPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilServer;
import mineplex.core.stats.command.GiveStatCommand;
import mineplex.core.stats.command.TimeCommand;
import mineplex.core.stats.event.StatChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class StatsManager
  extends MiniDbClientPlugin<PlayerStats>
{
  private static Object _statSync = new Object();
  private StatsRepository _repository;
  private NautHashMap<String, Integer> _stats = new NautHashMap();
  private NautHashMap<Player, NautHashMap<String, Long>> _statUploadQueue = new NautHashMap();
  private Runnable _saveRunnable;
  
  public StatsManager(JavaPlugin plugin, CoreClientManager clientManager)
  {
    super("Stats Manager", plugin, clientManager);
    
    this._repository = new StatsRepository(plugin);
    if (this._saveRunnable == null)
    {
      this._saveRunnable = new Runnable()
      {
        public void run()
        {
          StatsManager.this.saveStats();
        }
      };
      plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this._saveRunnable, 20L, 20L);
    }
    for (Stat stat : this._repository.retrieveStats()) {
      this._stats.put(stat.Name, Integer.valueOf(stat.Id));
    }
    clientManager.addStoredProcedureLoginProcessor(new SecondaryStatHandler(this, this._repository));
  }
  
  public void incrementStat(final Player player, final String statName, final long value)
  {
    long newValue = ((PlayerStats)Get(player)).addStat(statName, value);
    
    UtilServer.getServer().getPluginManager().callEvent(new StatChangeEvent(player.getName(), statName, newValue - value, newValue));
    if (!this._stats.containsKey(statName)) {
      Bukkit.getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
      {
        public void run()
        {
          synchronized (StatsManager._statSync)
          {
            if (StatsManager.this._stats.containsKey(statName))
            {
              StatsManager.this.addToQueue(statName, player, value);
              return;
            }
            StatsManager.this._repository.addStat(statName);
            
            StatsManager.this._stats.clear();
            for (Stat stat : StatsManager.this._repository.retrieveStats()) {
              StatsManager.this._stats.put(stat.Name, Integer.valueOf(stat.Id));
            }
            StatsManager.this.addToQueue(statName, player, value);
          }
        }
      });
    } else {
      addToQueue(statName, player, value);
    }
  }
  
  private void addToQueue(String statName, Player player, long value)
  {
    synchronized (_statSync)
    {
      if (!this._statUploadQueue.containsKey(player)) {
        this._statUploadQueue.put(player, new NautHashMap());
      }
      if (!((NautHashMap)this._statUploadQueue.get(player)).containsKey(statName)) {
        ((NautHashMap)this._statUploadQueue.get(player)).put(statName, Long.valueOf(0L));
      }
      ((NautHashMap)this._statUploadQueue.get(player)).put(statName, Long.valueOf(((Long)((NautHashMap)this._statUploadQueue.get(player)).get(statName)).longValue() + value));
    }
  }
  
  protected void saveStats()
  {
    if (this._statUploadQueue.isEmpty()) {
      return;
    }
    try
    {
      NautHashMap<Integer, NautHashMap<Integer, Long>> uploadQueue = new NautHashMap();
      synchronized (_statSync)
      {
        for (Iterator<Player> statIterator = this._statUploadQueue.keySet().iterator(); statIterator.hasNext();)
        {
          Player player = (Player)statIterator.next();
          if (!player.isOnline())
          {
            int uploadKey = this.ClientManager.getCachedClientAccountId(player.getUniqueId());
            
            uploadQueue.put(Integer.valueOf(uploadKey), new NautHashMap());
            for (String statName : ((NautHashMap)this._statUploadQueue.get(player)).keySet())
            {
              int statId = ((Integer)this._stats.get(statName)).intValue();
              ((NautHashMap)uploadQueue.get(Integer.valueOf(uploadKey))).put(Integer.valueOf(statId), (Long)((NautHashMap)this._statUploadQueue.get(player)).get(statName));
              System.out.println(player.getName() + " saving stat : " + statName + " adding " + ((NautHashMap)this._statUploadQueue.get(player)).get(statName));
            }
            statIterator.remove();
          }
        }
      }
      this._repository.saveStats(uploadQueue);
    }
    catch (Exception exception)
    {
      exception.printStackTrace();
    }
  }
  
  public boolean incrementStat(int accountId, String statName, long value)
  {
    if (this._stats.containsKey(statName)) {
      return false;
    }
    final NautHashMap<Integer, NautHashMap<Integer, Long>> uploadQueue = new NautHashMap();
    uploadQueue.put(Integer.valueOf(accountId), new NautHashMap());
    ((NautHashMap)uploadQueue.get(Integer.valueOf(accountId))).put((Integer)this._stats.get(statName), Long.valueOf(value));
    
    runAsync(new Runnable()
    {
      public void run()
      {
        StatsManager.this._repository.saveStats(uploadQueue);
      }
    });
    return true;
  }
  
  public int getStatId(String statName)
  {
    return ((Integer)this._stats.get(statName)).intValue();
  }
  
  public void replacePlayerHack(String playerName, PlayerStats playerStats)
  {
    Set(playerName, playerStats);
  }
  
  protected PlayerStats AddPlayer(String player)
  {
    return new PlayerStats();
  }
  
  public PlayerStats getOfflinePlayerStats(String playerName)
    throws SQLException
  {
    return this._repository.loadOfflinePlayerStats(playerName);
  }
  
  public void addCommands()
  {
    addCommand(new TimeCommand(this));
    addCommand(new GiveStatCommand(this));
  }
  
  public void processLoginResultSet(String playerName, int accountId, ResultSet resultSet)
    throws SQLException
  {
    Set(playerName, this._repository.loadClientInformation(resultSet));
  }
  
  public String getQuery(int accountId, String uuid, String name)
  {
    return "SELECT stats.name, value FROM accountStats INNER JOIN stats ON stats.id = accountStats.statId WHERE accountStats.accountId = '" + accountId + "';";
  }
}
