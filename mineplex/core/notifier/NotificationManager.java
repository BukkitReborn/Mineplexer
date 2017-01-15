package mineplex.core.notifier;

import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class NotificationManager
  extends MiniPlugin
{
  private boolean _enabled = true;
  private CoreClientManager _clientManager;
  private String _summerLine = C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█" + C.cGold + "█" + C.cYellow + "█";
  private String _website;
  
  public NotificationManager(JavaPlugin plugin, CoreClientManager client, String website)
  {
    super("Notification Manager", plugin);
    this._clientManager = client;
    
    this._website = website;
  }
  
  @EventHandler
  public void notify(UpdateEvent event)
  {
    if (!this._enabled) {}
  }
  
  private void sale()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      Rank rank = this._clientManager.Get(player).GetRank();
      if (!rank.Has(Rank.LEGEND))
      {
        if (rank == Rank.ALL) {
          UtilPlayer.message(player, C.cWhite + "Summer Sale!  Purchase " + C.cAqua + C.Bold + "Ultra RANK" + C.cWhite + " for $15");
        } else if (rank == Rank.ULTRA) {
          UtilPlayer.message(player, C.cWhite + "Summer Sale!  Upgrade to " + C.cPurple + C.Bold + "HERO RANK" + C.cWhite + " for $15!");
        } else if (rank == Rank.HERO) {
          UtilPlayer.message(player, C.cWhite + "Summer Sale! Upgrade to " + C.cGreen + C.Bold + "LEGEND RANK" + C.cWhite + " for $15!");
        }
        UtilPlayer.message(player, C.cWhite + " Visit " + F.link(this._website) + " for 50% Off Ranks!");
      }
    }
  }
  
  private void hugeSale()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      Rank rank = this._clientManager.Get(player).GetRank();
      if (!rank.Has(Rank.LEGEND))
      {
        UtilPlayer.message(player, this._summerLine);
        UtilPlayer.message(player, " ");
        UtilPlayer.message(player, "          " + C.cGreen + C.Bold + "75% OFF" + C.cYellow + C.Bold + "  SUMMER SUPER SALE  " + C.cGreen + C.Bold + "75% OFF");
        UtilPlayer.message(player, " ");
        if (rank == Rank.ALL)
        {
          UtilPlayer.message(player, C.cWhite + " " + player.getName() + ", you can get 75% Off " + C.cAqua + C.Bold + "All Lifetime Ranks" + C.cWhite + "!");
          UtilPlayer.message(player, C.cWhite + " This is our biggest sale ever, available " + C.cRed + C.Line + "this weekend only" + C.cWhite + "!");
        }
        else if (rank == Rank.ULTRA)
        {
          UtilPlayer.message(player, C.cWhite + " Hello " + player.getName() + ", upgrade to " + C.cPurple + C.Bold + "HERO RANK" + C.cWhite + " for only $7.50!");
          UtilPlayer.message(player, C.cWhite + " This is our biggest sale ever, available " + C.cRed + C.Line + "this weekend only" + C.cWhite + "!");
        }
        else if (rank == Rank.HERO)
        {
          UtilPlayer.message(player, C.cWhite + " Hello " + player.getName() + ", upgrade to " + C.cGreen + C.Bold + "LEGEND RANK" + C.cWhite + " for only $7.50!");
          UtilPlayer.message(player, C.cWhite + " This is our biggest sale ever, available " + C.cRed + C.Line + "this weekend only" + C.cWhite + "!");
        }
        UtilPlayer.message(player, " ");
        UtilPlayer.message(player, "                         " + C.cGreen + this._website);
        UtilPlayer.message(player, " ");
        UtilPlayer.message(player, this._summerLine);
      }
    }
  }
}
