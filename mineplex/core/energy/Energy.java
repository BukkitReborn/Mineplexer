package mineplex.core.energy;

import java.util.HashMap;
import mineplex.core.MiniClientPlugin;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.energy.event.EnergyEvent;
import mineplex.core.energy.event.EnergyEvent.EnergyChangeReason;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Energy
  extends MiniClientPlugin<ClientEnergy>
{
  private double _baseEnergy = 180.0D;
  private boolean _enabled = true;
  
  public Energy(JavaPlugin plugin)
  {
    super("Energy", plugin);
  }
  
  @EventHandler
  public void Update(UpdateEvent event)
  {
    if (!this._enabled) {
      return;
    }
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player cur = arrayOfPlayer[i];
      UpdateEnergy(cur);
    }
  }
  
  private void UpdateEnergy(Player cur)
  {
    if (cur.isDead()) {
      return;
    }
    double energy = 0.4D;
    
    EnergyEvent energyEvent = new EnergyEvent(cur, energy, EnergyEvent.EnergyChangeReason.Recharge);
    this._plugin.getServer().getPluginManager().callEvent(energyEvent);
    if (energyEvent.isCancelled()) {
      return;
    }
    ModifyEnergy(cur, energyEvent.GetTotalAmount());
  }
  
  public void ModifyEnergy(Player player, double energy)
  {
    if (!this._enabled) {
      return;
    }
    ClientEnergy client = (ClientEnergy)Get(player);
    if (energy > 0.0D) {
      client.Energy = Math.min(GetMax(player), client.Energy + energy);
    } else {
      client.Energy = Math.max(0.0D, client.Energy + energy);
    }
    if (energy < 0.0D) {
      client.LastEnergy = System.currentTimeMillis();
    }
    player.setExp(Math.min(0.999F, (float)client.Energy / (float)GetMax(player)));
  }
  
  public double GetMax(Player player)
  {
    return this._baseEnergy + ((ClientEnergy)Get(player)).EnergyBonus();
  }
  
  public double GetCurrent(Player player)
  {
    return ((ClientEnergy)Get(player)).Energy;
  }
  
  @EventHandler
  public void HandleRespawn(PlayerRespawnEvent event)
  {
    ((ClientEnergy)Get(event.getPlayer())).Energy = 0.0D;
  }
  
  @EventHandler
  public void HandleJoin(PlayerJoinEvent event)
  {
    ((ClientEnergy)Get(event.getPlayer())).Energy = 0.0D;
  }
  
  public boolean Use(Player player, String ability, double amount, boolean use, boolean inform)
  {
    ClientEnergy client = (ClientEnergy)Get(player);
    if (client.Energy < amount)
    {
      if (inform) {
        UtilPlayer.message(player, F.main(this._moduleName, "You are too exhausted to use " + F.skill(ability) + "."));
      }
      return false;
    }
    if (!use) {
      return true;
    }
    ModifyEnergy(player, -amount);
    
    return true;
  }
  
  @EventHandler
  public void handleExp(PlayerExpChangeEvent event)
  {
    if (!this._enabled) {
      return;
    }
    event.setAmount(0);
  }
  
  protected ClientEnergy AddPlayer(String player)
  {
    return new ClientEnergy();
  }
  
  public void AddEnergyMaxMod(Player player, String reason, int amount)
  {
    ((ClientEnergy)Get(player)).MaxEnergyMods.put(reason, Integer.valueOf(amount));
  }
  
  public void RemoveEnergyMaxMod(Player player, String reason)
  {
    ((ClientEnergy)Get(player)).MaxEnergyMods.remove(reason);
  }
  
  public void setEnabled(boolean b)
  {
    this._enabled = b;
  }
}
