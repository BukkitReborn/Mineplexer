package mineplex.core.gadget.gadgets;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ParticleGadget;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;

public class ParticleFoot
  extends ParticleGadget
{
  private boolean _foot = false;
  private HashMap<Location, Long> _steps = new HashMap();
  
  public ParticleFoot(GadgetManager manager)
  {
    super(manager, "Shadow Walk", new String[] {C.cWhite + "In a world where footprints", C.cWhite + "do not exist, leaving your", C.cWhite + "shadow behind is the next", C.cWhite + "best thing." }, -2, Material.LEATHER_BOOTS, (byte)0);
  }
  
  @EventHandler
  public void playParticle(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FASTEST) {
      return;
    }
    this._foot = (!this._foot);
    
    cleanSteps();
    for (Player player : GetActive()) {
      if (shouldDisplay(player)) {
        if (this.Manager.isMoving(player)) {
          if (UtilEnt.isGrounded(player))
          {
            Vector dir = player.getLocation().getDirection();
            dir.setY(0);
            dir.normalize();
            Vector offset;
            Vector offset;
            if (this._foot) {
              offset = new Vector(dir.getZ() * -1.0D, 0.1D, dir.getX());
            } else {
              offset = new Vector(dir.getZ(), 0.1D, dir.getX() * -1.0D);
            }
            Location loc = player.getLocation().add(offset.multiply(0.2D));
            if (!nearStep(loc)) {
              if (UtilBlock.solid(loc.getBlock().getRelative(BlockFace.DOWN)))
              {
                this._steps.put(loc, Long.valueOf(System.currentTimeMillis()));
                
                UtilParticle.PlayParticle(UtilParticle.ParticleType.FOOTSTEP, loc, 0.0F, 0.0F, 0.0F, 0.0F, 1, 
                  UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
                
                UtilParticle.PlayParticle(UtilParticle.ParticleType.LARGE_SMOKE, loc.clone().add(0.0D, 0.1D, 0.0D), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
                  UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
              }
            }
          }
        }
      }
    }
  }
  
  public void cleanSteps()
  {
    if (this._steps.isEmpty()) {
      return;
    }
    Iterator<Map.Entry<Location, Long>> stepIterator = this._steps.entrySet().iterator();
    while (stepIterator.hasNext())
    {
      Map.Entry<Location, Long> entry = (Map.Entry)stepIterator.next();
      if (UtilTime.elapsed(((Long)entry.getValue()).longValue(), 10000L)) {
        stepIterator.remove();
      }
    }
  }
  
  public boolean nearStep(Location loc)
  {
    for (Location other : this._steps.keySet()) {
      if (UtilMath.offset(loc, other) < 0.3D) {
        return true;
      }
    }
    return false;
  }
}
