package mineplex.core.gadget.gadgets;

import java.util.HashSet;
import java.util.Iterator;
import mineplex.core.blood.BloodEvent;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ParticleGadget;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class ParticleBlizzard
  extends ParticleGadget
{
  private HashSet<Arrow> _arrows = new HashSet();
  
  public ParticleBlizzard(GadgetManager manager)
  {
    super(manager, "Frost Lord", new String[] {C.cWhite + "You are a mighty frost lord.", C.cWhite + "Your double jumps and arrows", C.cWhite + "are enchanted with snow powers.", " ", C.cPurple + "No longer available" }, -1, Material.SNOW_BALL, (byte)0);
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
          UtilParticle.PlayParticle(UtilParticle.ParticleType.SNOW_SHOVEL, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.2F, 0.2F, 0.2F, 0.0F, 4, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
        else
        {
          player.getWorld().playSound(player.getLocation(), Sound.AMBIENCE_RAIN, 0.015F, 0.2F);
          
          double scale = player.getTicksLived() % 50 / 50.0D;
          for (int i = 0; i < 8; i++)
          {
            double r = (1.0D - scale) * 3.141592653589793D * 2.0D;
            
            double x = Math.sin(r + i * 0.7853981633974483D) * (r % 12.566370614359172D) * 0.4D;
            double z = Math.cos(r + i * 0.7853981633974483D) * (r % 12.566370614359172D) * 0.4D;
            
            UtilParticle.PlayParticle(UtilParticle.ParticleType.SNOW_SHOVEL, player.getLocation().add(x, scale * 3.0D, z), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
              UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
            if ((scale > 0.95D) && (Recharge.Instance.use(player, GetName(), 1000L, false, false)))
            {
              UtilParticle.PlayParticle(UtilParticle.ParticleType.SNOW_SHOVEL, player.getLocation().add(0.0D, scale * 3.5D, 0.0D), 0.0F, 0.0F, 0.0F, 0.2F, 60, 
                UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
              player.getWorld().playSound(player.getLocation(), Sound.STEP_SNOW, 1.0F, 1.5F);
            }
          }
        }
      }
    }
  }
  
  @EventHandler
  public void jump(PlayerToggleFlightEvent event)
  {
    if (!shouldDisplay(event.getPlayer())) {
      return;
    }
    if ((!event.getPlayer().isFlying()) && 
      (IsActive(event.getPlayer()))) {
      UtilParticle.PlayParticle(UtilParticle.ParticleType.SNOW_SHOVEL, event.getPlayer().getLocation(), 0.0F, 0.0F, 0.0F, 0.6F, 100, 
        UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    }
  }
  
  @EventHandler
  public void arrow(ProjectileLaunchEvent event)
  {
    if (this.Manager.hideParticles()) {
      return;
    }
    if ((event.getEntity() instanceof Arrow)) {
      if (event.getEntity().getShooter() != null) {
        if (GetActive().contains(event.getEntity().getShooter())) {
          this._arrows.add((Arrow)event.getEntity());
        }
      }
    }
  }
  
  @EventHandler
  public void arrow(ProjectileHitEvent event)
  {
    if (!this._arrows.remove(event.getEntity())) {
      return;
    }
    UtilParticle.PlayParticle(UtilParticle.ParticleType.SNOW_SHOVEL, event.getEntity().getLocation(), 0.0F, 0.0F, 0.0F, 0.4F, 12, 
      UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
  }
  
  @EventHandler
  public void arrowClean(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Iterator<Arrow> arrowIterator = this._arrows.iterator(); arrowIterator.hasNext();)
    {
      Arrow arrow = (Arrow)arrowIterator.next();
      if ((arrow.isDead()) || (!arrow.isValid()) || (arrow.isOnGround())) {
        arrowIterator.remove();
      } else {
        UtilParticle.PlayParticle(UtilParticle.ParticleType.SNOW_SHOVEL, arrow.getLocation(), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
          UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
      }
    }
  }
  
  @EventHandler
  public void death(BloodEvent event)
  {
    if (event.getPlayer() == null) {
      return;
    }
    if (!IsActive(event.getPlayer())) {
      return;
    }
    if (!shouldDisplay(event.getPlayer())) {
      return;
    }
    event.setItem(Material.SNOW_BALL, (byte)0);
  }
}
