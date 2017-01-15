package mineplex.core.benefit.benefits;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.benefit.BenefitManager;
import mineplex.core.benefit.BenefitManagerRepository;
import mineplex.core.common.util.Callback;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class BenefitBase
{
  private BenefitManager _plugin;
  private String _name;
  private BenefitManagerRepository _repository;
  
  protected BenefitBase(BenefitManager plugin, String name, BenefitManagerRepository repository)
  {
    this._plugin = plugin;
    this._name = name;
    this._repository = repository;
  }
  
  public JavaPlugin getPlugin()
  {
    return this._plugin.getPlugin();
  }
  
  public BenefitManagerRepository getRepository()
  {
    return this._repository;
  }
  
  public abstract void rewardPlayer(Player paramPlayer);
  
  public void recordBenefit(final Player player, final Callback<Boolean> callback)
  {
    Bukkit.getServer().getScheduler().runTaskAsynchronously(this._plugin.getPlugin(), new Runnable()
    {
      public void run()
      {
        boolean success = BenefitBase.this._repository.addBenefit(BenefitBase.this._plugin.getClientManager().Get(player).getAccountId(), BenefitBase.this._name);
        callback.run(Boolean.valueOf(success));
      }
    });
  }
  
  public String getName()
  {
    return this._name;
  }
}
