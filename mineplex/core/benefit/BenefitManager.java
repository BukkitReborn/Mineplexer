package mineplex.core.benefit;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import mineplex.core.MiniDbClientPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.benefit.benefits.BenefitBase;
import mineplex.core.common.util.Callback;
import mineplex.core.inventory.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class BenefitManager
  extends MiniDbClientPlugin<BenefitData>
{
  private BenefitManagerRepository _repository;
  private List<BenefitBase> _benefits = new ArrayList();
  
  public BenefitManager(JavaPlugin plugin, CoreClientManager clientManager, InventoryManager inventoryManager)
  {
    super("Benefit Manager", plugin, clientManager);
    this._repository = new BenefitManagerRepository(plugin);
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void giveBenefit(final PlayerJoinEvent event)
  {
    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
    {
      public void run()
      {
        if (((BenefitData)BenefitManager.this.Get(event.getPlayer())).Loaded) {
          for (final BenefitBase benefit : BenefitManager.this._benefits) {
            if (!((BenefitData)BenefitManager.this.Get(event.getPlayer())).Benefits.contains(benefit.getName())) {
              benefit.recordBenefit(event.getPlayer(), new Callback()
              {
                public void run(Boolean success)
                {
                  if (success.booleanValue()) {
                    benefit.rewardPlayer(this.val$event.getPlayer());
                  } else {
                    System.out.println("Benefit reward failed for " + this.val$event.getPlayer().getName());
                  }
                }
              });
            }
          }
        }
      }
    }, 100L);
  }
  
  protected BenefitData AddPlayer(String player)
  {
    return new BenefitData();
  }
  
  public void processLoginResultSet(String playerName, int accountId, ResultSet resultSet)
    throws SQLException
  {
    Set(playerName, this._repository.retrievePlayerBenefitData(resultSet));
  }
  
  public String getQuery(int accountId, String uuid, String name)
  {
    return "SELECT benefit FROM rankBenefits WHERE rankBenefits.accountId = '" + accountId + "';";
  }
}
