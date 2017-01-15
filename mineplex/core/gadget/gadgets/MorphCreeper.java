package mineplex.core.gadget.gadgets;

import java.util.HashMap;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseBase;
import mineplex.core.disguise.disguises.DisguiseCreeper;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MorphCreeper
  extends MorphGadget
{
  private HashMap<Player, Long> _active = new HashMap();
  
  public MorphCreeper(GadgetManager manager)
  {
    super(manager, "Creeper Morph", new String[] {C.cWhite + "Transforms the wearer into a creepy Creeper!", " ", C.cYellow + "Crouch" + C.cGray + " to use " + C.cGreen + "Detonate", " ", C.cPurple + "Unlocked with Hero Rank" }, -1, Material.SKULL_ITEM, (byte)4);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguiseCreeper disguise = new DisguiseCreeper(player);
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
  public void Trigger(UpdateEvent event)
  {
    if (event.getType() == UpdateType.TICK) {
      for (Player player : GetActive()) {
        if (player.isSneaking())
        {
          player.leaveVehicle();
          player.eject();
          if (!this._active.containsKey(player)) {
            this._active.put(player, Long.valueOf(System.currentTimeMillis()));
          }
          double elapsed = (System.currentTimeMillis() - ((Long)this._active.get(player)).longValue()) / 1000.0D;
          
          player.setExp(Math.min(0.99F, (float)(elapsed / 1.5D)));
          
          player.getWorld().playSound(player.getLocation(), Sound.CREEPER_HISS, (float)(0.5D + elapsed / 3.0D), (float)(0.5D + elapsed));
          
          IncreaseSize(player);
        }
        else if (this._active.containsKey(player))
        {
          DecreaseSize(player);
          
          player.setExp(0.0F);
          
          double elapsed = (System.currentTimeMillis() - ((Long)this._active.remove(player)).longValue()) / 1000.0D;
          if (elapsed >= 1.5D)
          {
            UtilParticle.PlayParticle(UtilParticle.ParticleType.HUGE_EXPLOSION, player.getLocation(), 0.0F, 0.5F, 0.0F, 0.0F, 1, 
              UtilParticle.ViewDist.MAX, UtilServer.getPlayers());
            player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1.0F, 0.8F);
            
            player.playEffect(EntityEffect.HURT);
            
            HashMap<Player, Double> players = UtilPlayer.getInRadius(player.getLocation(), 8.0D);
            for (Player other : players.keySet()) {
              if (!other.equals(player)) {
                if (!this.Manager.collideEvent(this, other))
                {
                  double mult = ((Double)players.get(other)).doubleValue();
                  
                  UtilAction.velocity(other, UtilAlg.getTrajectory(player.getLocation(), other.getLocation()), 1.0D + 1.5D * mult, false, 0.0D, 0.5D + 1.0D * mult, 3.0D, true);
                }
              }
            }
          }
        }
      }
    }
  }
  
  public DisguiseCreeper GetDisguise(Player player)
  {
    DisguiseBase disguise = this.Manager.getDisguiseManager().getDisguise(player);
    if (disguise == null) {
      return null;
    }
    if (!(disguise instanceof DisguiseCreeper)) {
      return null;
    }
    return (DisguiseCreeper)disguise;
  }
  
  public int GetSize(Player player)
  {
    DisguiseCreeper creeper = GetDisguise(player);
    if (creeper == null) {
      return 0;
    }
    return creeper.bV();
  }
  
  public void DecreaseSize(Player player)
  {
    DisguiseCreeper creeper = GetDisguise(player);
    if (creeper == null) {
      return;
    }
    creeper.a(-1);
    
    this.Manager.getDisguiseManager().updateDisguise(creeper);
  }
  
  public void IncreaseSize(Player player)
  {
    DisguiseCreeper creeper = GetDisguise(player);
    if (creeper == null) {
      return;
    }
    creeper.a(1);
    
    this.Manager.getDisguiseManager().updateDisguise(creeper);
  }
  
  @EventHandler
  public void HeroOwner(PlayerJoinEvent event)
  {
    if (this.Manager.getClientManager().Get(event.getPlayer()).GetRank().Has(Rank.HERO)) {
      ((Donor)this.Manager.getDonationManager().Get(event.getPlayer().getName())).AddUnknownSalesPackagesOwned(GetName());
    }
  }
  
  @EventHandler
  public void Clean(PlayerQuitEvent event)
  {
    this._active.remove(event.getPlayer());
  }
}
