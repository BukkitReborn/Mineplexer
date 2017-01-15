package mineplex.core.mount.types;

import java.util.HashMap;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.mount.Mount;
import mineplex.core.mount.MountManager;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class MountSlime
  extends Mount<Slime>
{
  public MountSlime(MountManager manager)
  {
    super(manager, "Slime Mount", Material.SLIME_BALL, (byte)0, new String[] {ChatColor.RESET + "Bounce around on your very", ChatColor.RESET + "own personal slime friend!" }, 15000);
    
    this.KnownPackage = false;
  }
  
  public void EnableCustom(Player player)
  {
    player.leaveVehicle();
    player.eject();
    
    this.Manager.DeregisterAll(player);
    
    Slime mount = (Slime)player.getWorld().spawn(player.getLocation(), Slime.class);
    mount.setSize(2);
    
    mount.setCustomName(player.getName() + "'s " + GetName());
    
    UtilPlayer.message(player, F.main("Mount", "You spawned " + F.elem(GetName()) + "."));
    
    this._active.put(player, mount);
  }
  
  public void Disable(Player player)
  {
    Slime mount = (Slime)this._active.remove(player);
    if (mount != null)
    {
      mount.remove();
      
      UtilPlayer.message(player, F.main("Mount", "You despawned " + F.elem(GetName()) + "."));
      
      this.Manager.removeActive(player);
    }
  }
  
  @EventHandler
  public void interactMount(PlayerInteractEntityEvent event)
  {
    if (event.getRightClicked() == null) {
      return;
    }
    if (!GetActive().containsKey(event.getPlayer())) {
      return;
    }
    if (!((Slime)GetActive().get(event.getPlayer())).equals(event.getRightClicked()))
    {
      UtilPlayer.message(event.getPlayer(), F.main("Mount", "This is not your Mount!"));
      return;
    }
    event.getPlayer().leaveVehicle();
    event.getPlayer().eject();
    
    event.getRightClicked().setPassenger(event.getPlayer());
  }
  
  @EventHandler
  public void target(EntityTargetEvent event)
  {
    if (!GetActive().containsKey(event.getTarget())) {
      return;
    }
    if (!((Slime)GetActive().get(event.getTarget())).equals(event.getEntity())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void updateBounce(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Slime slime : GetActive().values()) {
      if (slime.getPassenger() != null) {
        if (UtilEnt.isGrounded(slime)) {
          if ((slime.getPassenger() instanceof Player))
          {
            Player player = (Player)slime.getPassenger();
            if (Recharge.Instance.use(player, GetName(), 200L, false, false))
            {
              UtilAction.velocity(slime, slime.getPassenger().getLocation().getDirection(), 1.0D, true, 0.0D, 0.4D, 1.0D, true);
              
              slime.getWorld().playSound(slime.getLocation(), Sound.SLIME_WALK, 1.0F, 0.75F);
            }
          }
        }
      }
    }
    for (Slime slime : GetActive().values()) {
      if (slime.getPassenger() != null) {
        if ((slime.getPassenger() instanceof Player))
        {
          Player player = (Player)slime.getPassenger();
          if (Recharge.Instance.usable(player, GetName() + " Collide")) {
            for (Slime other : GetActive().values()) {
              if (!other.equals(slime)) {
                if (other.getPassenger() != null) {
                  if ((other.getPassenger() instanceof Player))
                  {
                    Player otherPlayer = (Player)other.getPassenger();
                    if (Recharge.Instance.usable(otherPlayer, GetName() + " Collide")) {
                      if (UtilMath.offset(slime, other) <= 2.0D)
                      {
                        Recharge.Instance.useForce(player, GetName() + " Collide", 500L);
                        Recharge.Instance.useForce(otherPlayer, GetName() + " Collide", 500L);
                        
                        UtilAction.velocity(slime, UtilAlg.getTrajectory(other, slime), 1.2D, false, 0.0D, 0.8D, 10.0D, true);
                        UtilAction.velocity(other, UtilAlg.getTrajectory(slime, other), 1.2D, false, 0.0D, 0.8D, 10.0D, true);
                        
                        slime.getWorld().playSound(slime.getLocation(), Sound.SLIME_ATTACK, 1.0F, 0.5F);
                        slime.getWorld().playSound(slime.getLocation(), Sound.SLIME_WALK, 1.0F, 0.5F);
                        other.getWorld().playSound(other.getLocation(), Sound.SLIME_WALK, 1.0F, 0.5F);
                        
                        slime.playEffect(EntityEffect.HURT);
                        other.playEffect(EntityEffect.HURT);
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
