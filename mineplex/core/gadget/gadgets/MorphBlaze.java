package mineplex.core.gadget.gadgets;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseBlaze;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class MorphBlaze
  extends MorphGadget
{
  public MorphBlaze(GadgetManager manager)
  {
    super(manager, "Blaze Morph", new String[] {C.cWhite + "Transforms the wearer into a fiery Blaze!", " ", C.cYellow + "Crouch" + C.cGray + " to use " + C.cGreen + "Firefly", " ", C.cPurple + "Unlocked with Hero Rank" }, -1, Material.BLAZE_POWDER, (byte)0);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguiseBlaze disguise = new DisguiseBlaze(player);
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
  public void Trail(UpdateEvent event)
  {
    if (event.getType() == UpdateType.TICK) {
      for (Player player : GetActive()) {
        if (player.isSneaking())
        {
          player.leaveVehicle();
          player.eject();
          
          player.getWorld().playSound(player.getLocation(), Sound.FIZZ, 0.2F, (float)Math.random());
          UtilParticle.PlayParticle(UtilParticle.ParticleType.FLAME, player.getLocation().add(0.0D, 1.0D, 0.0D), 
            0.25F, 0.25F, 0.25F, 0.0F, 3, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
          UtilParticle.PlayParticle(UtilParticle.ParticleType.LARGE_SMOKE, player.getLocation().add(0.0D, 1.0D, 0.0D), 
            0.1F, 0.1F, 0.1F, 0.0F, 1, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
          UtilAction.velocity(player, 0.8D, 0.1D, 1.0D, true);
        }
      }
    }
  }
  
  @EventHandler
  public void HeroOwner(PlayerJoinEvent event)
  {
    if (this.Manager.getClientManager().Get(event.getPlayer()).GetRank().Has(Rank.HERO)) {
      ((Donor)this.Manager.getDonationManager().Get(event.getPlayer().getName())).AddUnknownSalesPackagesOwned(GetName());
    }
  }
}
