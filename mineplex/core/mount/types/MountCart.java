package mineplex.core.mount.types;

import java.util.Collection;
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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;

public class MountCart
  extends Mount<Minecart>
{
  public MountCart(MountManager manager)
  {
    super(manager, "Minecart", Material.MINECART, (byte)0, new String[] {ChatColor.RESET + "Cruise around town in your", ChatColor.RESET + "new Minecart VX Turbo!" }, 15000);
    
    this.KnownPackage = false;
  }
  
  public void EnableCustom(Player player)
  {
    player.leaveVehicle();
    player.eject();
    
    this.Manager.DeregisterAll(player);
    
    Minecart mount = (Minecart)player.getWorld().spawn(player.getLocation().add(0.0D, 2.0D, 0.0D), Minecart.class);
    
    UtilPlayer.message(player, F.main("Mount", "You spawned " + F.elem(GetName()) + "."));
    
    this._active.put(player, mount);
  }
  
  public void Disable(Player player)
  {
    Minecart mount = (Minecart)this._active.remove(player);
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
    if (!((Minecart)GetActive().get(event.getPlayer())).equals(event.getRightClicked()))
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
    if (!((Minecart)GetActive().get(event.getTarget())).equals(event.getEntity())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void updateBounce(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Minecart cart : GetActive().values()) {
      if (cart.getPassenger() != null) {
        if (UtilEnt.isGrounded(cart)) {
          if ((cart.getPassenger() instanceof Player))
          {
            UtilAction.velocity(cart, cart.getPassenger().getLocation().getDirection(), 1.4D, true, 0.0D, 0.0D, 1.0D, false);
            if (Math.random() > 0.8D) {
              cart.getWorld().playSound(cart.getLocation(), Sound.MINECART_BASE, 0.05F, 2.0F);
            }
          }
        }
      }
    }
    for (Minecart cart : GetActive().values()) {
      if (cart.getPassenger() != null) {
        if ((cart.getPassenger() instanceof Player))
        {
          Player player = (Player)cart.getPassenger();
          if (Recharge.Instance.usable(player, GetName() + " Collide")) {
            for (Minecart other : GetActive().values()) {
              if (!other.equals(cart)) {
                if (other.getPassenger() != null) {
                  if ((other.getPassenger() instanceof Player))
                  {
                    Player otherPlayer = (Player)other.getPassenger();
                    if (Recharge.Instance.usable(otherPlayer, GetName() + " Collide")) {
                      if (UtilMath.offset(cart, other) <= 2.0D)
                      {
                        Recharge.Instance.useForce(player, GetName() + " Collide", 500L);
                        Recharge.Instance.useForce(otherPlayer, GetName() + " Collide", 500L);
                        
                        UtilAction.velocity(cart, UtilAlg.getTrajectory(other, cart), 1.2D, false, 0.0D, 0.8D, 10.0D, true);
                        UtilAction.velocity(other, UtilAlg.getTrajectory(cart, other), 1.2D, false, 0.0D, 0.8D, 10.0D, true);
                        
                        cart.getWorld().playSound(cart.getLocation(), Sound.IRONGOLEM_HIT, 1.0F, 0.5F);
                        other.getWorld().playSound(other.getLocation(), Sound.IRONGOLEM_HIT, 1.0F, 0.5F);
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
  
  @EventHandler
  public void cancelBreak(VehicleDamageEvent event)
  {
    if (GetActive().values().contains(event.getVehicle())) {
      event.setCancelled(true);
    }
  }
}
