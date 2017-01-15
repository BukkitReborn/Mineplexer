package mineplex.core.gadget.gadgets;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ParticleGadget;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class ParticleLegend
  extends ParticleGadget
{
  public ParticleLegend(GadgetManager manager)
  {
    super(manager, "Legendary Aura", new String[] {C.cWhite + "These mystic particle attach to", C.cWhite + "only the most legendary of players!", " ", C.cPurple + "Unlocked with Legend Rank" }, -2, Material.ENDER_PORTAL, (byte)0);
  }
  
  @EventHandler
  public void playParticle(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Player player : GetActive()) {
      if (shouldDisplay(player)) {
        player.getWorld().playEffect(player.getLocation().add(0.0D, 1.0D, 0.0D), Effect.ENDER_SIGNAL, 0);
      }
    }
  }
  
  @EventHandler
  public void legendOwner(PlayerJoinEvent event)
  {
    if (this.Manager.getClientManager().Get(event.getPlayer()).GetRank().Has(Rank.LEGEND)) {
      ((Donor)this.Manager.getDonationManager().Get(event.getPlayer().getName())).AddUnknownSalesPackagesOwned(GetName());
    }
  }
}
