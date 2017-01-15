package mineplex.core.mount.types;

import java.util.HashMap;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import mineplex.core.mount.HorseMount;
import mineplex.core.mount.MountManager;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.event.EventHandler;

public class MountFrost
  extends HorseMount
{
  public MountFrost(MountManager manager)
  {
    super(manager, "Glacial Steed", new String[] {C.cWhite + "Born in the North Pole,", C.cWhite + "it leaves a trail of frost", C.cWhite + "as it moves!" }, Material.SNOW_BALL, (byte)0, 15000, Horse.Color.WHITE, Horse.Style.WHITE, Horse.Variant.HORSE, 1.0D, null);
  }
  
  @EventHandler
  public void Trail(UpdateEvent event)
  {
    if (event.getType() == UpdateType.TICK) {
      for (Horse horse : GetActive().values()) {
        UtilParticle.PlayParticle(UtilParticle.ParticleType.SNOW_SHOVEL, horse.getLocation().add(0.0D, 1.0D, 0.0D), 
          0.25F, 0.25F, 0.25F, 0.1F, 4, 
          UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
      }
    }
  }
}
