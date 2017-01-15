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

public class ParticleGreen
  extends ParticleGadget
{
  public ParticleGreen(GadgetManager manager)
  {
    super(manager, "Green Ring", new String[] {C.cWhite + "With these sparkles, you", C.cWhite + "can now sparkle while you", C.cWhite + "sparkle with CaptainSparklez." }, -2, Material.EMERALD, (byte)0);
  }
  
  @EventHandler
  public void playParticle(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Player player : GetActive()) {
      if (shouldDisplay(player))
      {
        float x = (float)(Math.sin(player.getTicksLived() / 7.0D) * 1.0D);
        float z = (float)(Math.cos(player.getTicksLived() / 7.0D) * 1.0D);
        float y = (float)(Math.cos(player.getTicksLived() / 17.0D) * 1.0D + 1.0D);
        
        UtilParticle.PlayParticle(UtilParticle.ParticleType.HAPPY_VILLAGER, player.getLocation().add(x, y, z), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
          UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
      }
    }
  }
}
