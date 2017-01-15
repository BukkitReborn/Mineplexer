package mineplex.core.scoreboard;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.donation.DonationManager;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ScoreboardManager
  extends MiniPlugin
{
  private CoreClientManager _clientManager;
  private DonationManager _donationManager;
  private HashMap<Player, PlayerScoreboard> _playerScoreboards = new HashMap();
  private HashMap<String, ScoreboardData> _scoreboards = new HashMap();
  private String _title;
  private int _shineIndex;
  private boolean _shineDirection = true;
  public static String _mineplexName;
  
  public ScoreboardManager(JavaPlugin plugin, CoreClientManager clientManager, DonationManager donationManager, String mineplexName)
  {
    super("Scoreboard Manager", plugin);
    
    this._clientManager = clientManager;
    this._donationManager = donationManager;
    this._title = ("   " + mineplexName.toUpperCase() + "   ");
    _mineplexName = mineplexName;
  }
  
  public CoreClientManager getClients()
  {
    return this._clientManager;
  }
  
  public DonationManager getDonation()
  {
    return this._donationManager;
  }
  
  @EventHandler
  public void playerJoin(PlayerJoinEvent event)
  {
    this._playerScoreboards.put(event.getPlayer(), new PlayerScoreboard(this, event.getPlayer()));
  }
  
  @EventHandler
  public void playerQuit(PlayerQuitEvent event)
  {
    this._playerScoreboards.remove(event.getPlayer());
  }
  
  public void draw()
  {
    Iterator<Player> playerIterator = this._playerScoreboards.keySet().iterator();
    while (playerIterator.hasNext())
    {
      Player player = (Player)playerIterator.next();
      if (!player.isOnline()) {
        playerIterator.remove();
      } else {
        ((PlayerScoreboard)this._playerScoreboards.get(player)).draw(this, player);
      }
    }
  }
  
  public ScoreboardData getData(String scoreboardName, boolean create)
  {
    if (!create) {
      return (ScoreboardData)this._scoreboards.get(scoreboardName);
    }
    if (!this._scoreboards.containsKey(scoreboardName)) {
      this._scoreboards.put(scoreboardName, new ScoreboardData());
    }
    return (ScoreboardData)this._scoreboards.get(scoreboardName);
  }
  
  @EventHandler
  public void updateTitle(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FASTER) {
      return;
    }
    String out;
    String out;
    if (this._shineDirection) {
      out = C.cGold + C.Bold;
    } else {
      out = C.cWhite + C.Bold;
    }
    char c;
    for (int i = 0; i < this._title.length(); i++)
    {
      c = this._title.charAt(i);
      if (this._shineDirection)
      {
        if (i == this._shineIndex) {
          out = out + C.cYellow + C.Bold;
        }
        if (i == this._shineIndex + 1) {
          out = out + C.cWhite + C.Bold;
        }
      }
      else
      {
        if (i == this._shineIndex) {
          out = out + C.cYellow + C.Bold;
        }
        if (i == this._shineIndex + 1) {
          out = out + C.cGold + C.Bold;
        }
      }
      out = out + c;
    }
    for (PlayerScoreboard ps : this._playerScoreboards.values()) {
      ps.setTitle(out);
    }
    this._shineIndex += 1;
    if (this._shineIndex == this._title.length() * 2)
    {
      this._shineIndex = 0;
      this._shineDirection = (!this._shineDirection);
    }
  }
}
