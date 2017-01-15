package mineplex.core.leaderboard;

import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LeaderboardManager
  extends MiniPlugin
{
  private static LeaderboardManager _instance;
  private StatEventsRepository _statEvents;
  private CoreClientManager _clientManager;
  private String _serverGroup;
  
  public LeaderboardManager(JavaPlugin plugin, CoreClientManager clientManager)
  {
    super("Leaderboard Manager", plugin);
    
    _instance = this;
    this._clientManager = clientManager;
    this._statEvents = new StatEventsRepository(plugin);
    this._serverGroup = this._plugin.getConfig().getString("serverstatus.group");
  }
  
  public boolean attemptStatEvent(Player player, String stat, int gamemode, int value)
  {
    StatType type = StatType.getType(stat);
    
    return type == null ? false : onStatEvent(player, type, gamemode, value);
  }
  
  public boolean onStatEvent(Player player, StatType type, int gamemode, int value)
  {
    return true;
  }
  
  public void addCommands()
  {
    addCommand(new SetTournamentCommand(this));
  }
  
  public static LeaderboardManager getInstance()
  {
    return _instance;
  }
}
