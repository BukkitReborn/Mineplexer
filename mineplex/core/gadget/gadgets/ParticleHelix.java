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
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;

public class ParticleHelix
  extends ParticleGadget
{
  public ParticleHelix(GadgetManager manager)
  {
    super(manager, "Blood Helix", new String[] {C.cWhite + "Ancient legend says this magic", C.cWhite + "empowers the blood of its user,", C.cWhite + "giving them godly powers." }, -2, Material.REDSTONE, (byte)0);
  }
  
  @EventHandler
  public void playParticle(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Player player : GetActive()) {
      if (shouldDisplay(player)) {
        if (this.Manager.isMoving(player))
        {
          UtilParticle.PlayParticle(UtilParticle.ParticleType.RED_DUST, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.2F, 0.2F, 0.2F, 0.0F, 4, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
        else
        {
          for (int height = 0; height <= 20; height++) {
            for (int i = 0; i < 2; i++)
            {
              double lead = i * 3.141592653589793D;
              
              double heightLead = height * 0.3141592653589793D;
              
              float x = (float)(Math.sin(player.getTicksLived() / 20.0D + lead + heightLead) * 1.2000000476837158D);
              float z = (float)(Math.cos(player.getTicksLived() / 20.0D + lead + heightLead) * 1.2000000476837158D);
              
              float y = 0.15F * height;
              
              UtilParticle.PlayParticle(UtilParticle.ParticleType.RED_DUST, player.getLocation().add(x * (1.0D - height / 22.0D), y, z * (1.0D - height / 22.0D)), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
                UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
            }
          }
          player.getWorld().playSound(player.getLocation(), Sound.LAVA, 0.3F, 1.0F);
        }
      }
    }
  }
}
