package mineplex.core.gadget.gadgets;

import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ParticleGadget;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class ParticleEnchant
  extends ParticleGadget
{
  public ParticleEnchant(GadgetManager manager)
  {
    super(manager, "Enchanted", new String[] {C.cWhite + "The wisdom of the universe", C.cWhite + "suddenly finds you extremely", C.cWhite + "attractive, and wants to", C.cWhite + "'enchant' you." }, -2, Material.BOOK, (byte)0);
  }
  
  @EventHandler
  public void playParticle(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Player player : GetActive()) {
      if (shouldDisplay(player)) {
        if (this.Manager.isMoving(player)) {
          UtilParticle.PlayParticle(UtilParticle.ParticleType.ENCHANTMENT_TABLE, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.2F, 0.2F, 0.2F, 0.0F, 4, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        } else {
          UtilParticle.PlayParticle(UtilParticle.ParticleType.ENCHANTMENT_TABLE, player.getLocation().add(0.0D, 1.4D, 0.0D), 0.0F, 0.0F, 0.0F, 1.0F, 4, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
      }
    }
  }
}
