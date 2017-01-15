package mineplex.core.gadget.gadgets;

import java.util.HashSet;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguisePig;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class MorphPig
  extends MorphGadget
{
  private HashSet<Player> _double = new HashSet();
  
  public MorphPig(GadgetManager manager)
  {
    super(manager, "Pig Morph", new String[] {C.cWhite + "Oink. Oink. Oink.... Oink?", " ", C.cYellow + "Left Click" + C.cGray + " to use " + C.cGreen + "Oink", C.cYellow + "Collide" + C.cGray + " to use " + C.cGreen + "Pig Bounce", " ", C.cPurple + "Unlocked with Ultra Rank" }, -1, Material.PORK, (byte)0);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguisePig disguise = new DisguisePig(player);
    disguise.setName(player.getName(), this.Manager.getClientManager().Get(player).GetRank());
    disguise.setCustomNameVisible(true);
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    this.Manager.getDisguiseManager().undisguise(player);
  }
  
  @EventHandler
  public void Snort(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (!IsActive(player)) {
      return;
    }
    if (!UtilEvent.isAction(event, UtilEvent.ActionType.L)) {
      return;
    }
    if (!Recharge.Instance.use(player, GetName(), 400L, false, false)) {
      return;
    }
    player.getWorld().playSound(player.getLocation(), Sound.PIG_IDLE, 1.0F, (float)(0.75D + Math.random() * 0.5D));
  }
  
  @EventHandler
  public void HeroOwner(PlayerJoinEvent event)
  {
    if (this.Manager.getClientManager().Get(event.getPlayer()).GetRank().Has(Rank.ULTRA)) {
      ((Donor)this.Manager.getDonationManager().Get(event.getPlayer().getName())).AddUnknownSalesPackagesOwned(GetName());
    }
  }
  
  @EventHandler
  public void Collide(PlayerToggleFlightEvent event)
  {
    this._double.add(event.getPlayer());
    Recharge.Instance.useForce(event.getPlayer(), GetName() + " Double Jump", 200L);
  }
  
  @EventHandler
  public void Collide(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Player player : GetActive())
    {
      if ((this._double.contains(player)) && 
        (UtilEnt.isGrounded(player)) && 
        (Recharge.Instance.usable(player, GetName() + " Double Jump"))) {
        this._double.remove(player);
      }
      double range = 1.0D;
      if (this._double.contains(player)) {
        range += 0.5D;
      }
      if (player.getVehicle() == null) {
        if (Recharge.Instance.usable(player, GetName() + " Collide"))
        {
          Player[] arrayOfPlayer;
          int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
          for (int i = 0; i < j; i++)
          {
            Player other = arrayOfPlayer[i];
            if (!other.equals(player)) {
              if (other.getVehicle() == null) {
                if (Recharge.Instance.usable(other, GetName() + " Collide")) {
                  if (UtilMath.offset(player, other) <= range) {
                    if (!this.Manager.collideEvent(this, other))
                    {
                      Recharge.Instance.useForce(other, GetName() + " Collide", 200L);
                      Recharge.Instance.useForce(player, GetName() + " Collide", 200L);
                      
                      double power = 0.4D;
                      double height = 0.1D;
                      if (player.isSprinting())
                      {
                        power = 0.6D;
                        height = 0.2D;
                      }
                      if (this._double.contains(player))
                      {
                        power = 1.0D;
                        height = 0.3D;
                      }
                      UtilAction.velocity(player, UtilAlg.getTrajectory2d(other, player), power, false, 0.0D, height, 1.0D, true);
                      UtilAction.velocity(other, UtilAlg.getTrajectory2d(player, other), power, false, 0.0D, height, 1.0D, true);
                      if (this._double.contains(player)) {
                        player.getWorld().playSound(player.getLocation(), Sound.PIG_DEATH, (float)(0.8D + Math.random() * 0.4D), (float)(0.8D + Math.random() * 0.4D));
                      } else {
                        player.getWorld().playSound(player.getLocation(), Sound.PIG_IDLE, 1.0F, (float)(1.5D + Math.random() * 0.5D));
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  @EventHandler
  public void Clean(PlayerQuitEvent event)
  {
    this._double.remove(event.getPlayer());
  }
}
