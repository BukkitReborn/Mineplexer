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

public class ParticleRain
  extends ParticleGadget
{
  public ParticleRain(GadgetManager manager)
  {
    super(manager, "Rain Cloud", new String[] {C.cWhite + "Your very own rain cloud!", C.cWhite + "Now you never have to worry", C.cWhite + "about not being wet. Woo..." }, -2, Material.INK_SACK, (byte)4);
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
          UtilParticle.PlayParticle(UtilParticle.ParticleType.SPLASH, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.2F, 0.2F, 0.2F, 0.0F, 4, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
        else
        {
          UtilParticle.PlayParticle(UtilParticle.ParticleType.EXPLODE, player.getLocation().add(0.0D, 3.5D, 0.0D), 0.6F, 0.0F, 0.6F, 0.0F, 8, 
            UtilParticle.ViewDist.NORMAL, new Player[] { player });
          Player[] arrayOfPlayer;
          int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
          for (int i = 0; i < j; i++)
          {
            Player other = arrayOfPlayer[i];
            if (!player.equals(other)) {
              UtilParticle.PlayParticle(UtilParticle.ParticleType.CLOUD, player.getLocation().add(0.0D, 3.5D, 0.0D), 0.6F, 0.1F, 0.6F, 0.0F, 8, 
                UtilParticle.ViewDist.NORMAL, new Player[] { other });
            }
          }
          UtilParticle.PlayParticle(UtilParticle.ParticleType.DRIP_WATER, player.getLocation().add(0.0D, 3.5D, 0.0D), 0.4F, 0.1F, 0.4F, 0.0F, 2, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
          
          player.getWorld().playSound(player.getLocation(), Sound.AMBIENCE_RAIN, 0.1F, 1.0F);
        }
      }
    }
  }
}
