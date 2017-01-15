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

public class ParticleFireRings
  extends ParticleGadget
{
  public ParticleFireRings(GadgetManager manager)
  {
    super(manager, "Flame Rings", new String[] {C.cWhite + "Forged from the burning ashes", C.cWhite + "of 1000 Blazes by the infamous", C.cWhite + "Flame King of the Nether realm." }, -2, Material.BLAZE_POWDER, (byte)0);
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
          UtilParticle.PlayParticle(UtilParticle.ParticleType.FLAME, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.2F, 0.2F, 0.2F, 0.0F, 1, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
        else
        {
          for (int i = 0; i < 1; i++)
          {
            double lead = i * 3.141592653589793D;
            
            float x = (float)(Math.sin(player.getTicksLived() / 5.0D + lead) * 1.0D);
            float z = (float)(Math.cos(player.getTicksLived() / 5.0D + lead) * 1.0D);
            
            float y = (float)(Math.sin(player.getTicksLived() / 5.0D + lead) + 1.0D);
            
            UtilParticle.PlayParticle(UtilParticle.ParticleType.FLAME, player.getLocation().add(x, y, z), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
              UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
          }
          for (int i = 0; i < 1; i++)
          {
            double lead = i * 3.141592653589793D;
            
            float x = (float)-(Math.sin(player.getTicksLived() / 5.0D + lead) * 1.0D);
            float z = (float)(Math.cos(player.getTicksLived() / 5.0D + lead) * 1.0D);
            
            float y = (float)(Math.sin(player.getTicksLived() / 5.0D + lead) + 1.0D);
            
            UtilParticle.PlayParticle(UtilParticle.ParticleType.FLAME, player.getLocation().add(x, y, z), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
              UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
          }
          player.getWorld().playSound(player.getLocation(), Sound.FIRE, 0.2F, 1.0F);
        }
      }
    }
  }
}
