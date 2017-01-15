package mineplex.core.gadget.gadgets;

import java.util.HashSet;
import java.util.Iterator;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftFallingSand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemFootball
  extends ItemGadget
{
  private HashSet<Bat> _active = new HashSet();
  
  public ItemFootball(GadgetManager manager)
  {
    super(manager, "Football", new String[] {C.cWhite + "An amazing souvenier from the", C.cWhite + "Mineplex World Cup in 2053!" }, -1, Material.CLAY_BALL, (byte)3, 1000L, new Ammo("Melon Launcher", "10 Footballs", Material.CLAY_BALL, (byte)0, new String[] { C.cWhite + "10 Footballs to play with" }, 1000, 10));
  }
  
  public void ActivateCustom(Player player)
  {
    FallingBlock ball = player.getWorld().spawnFallingBlock(player.getLocation().add(0.0D, 1.0D, 0.0D), Material.SKULL, (byte)3);
    
    Bat bat = (Bat)player.getWorld().spawn(player.getLocation(), Bat.class);
    UtilEnt.Vegetate(bat);
    UtilEnt.ghost(bat, true, true);
    UtilEnt.silence(bat, true);
    
    bat.setPassenger(ball);
    
    this._active.add(bat);
    
    UtilPlayer.message(player, F.main("Skill", "You used " + F.skill(GetName()) + "."));
  }
  
  @EventHandler
  public void Collide(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    int j;
    int i;
    for (Iterator localIterator = this._active.iterator(); localIterator.hasNext(); i < j)
    {
      Bat ball = (Bat)localIterator.next();
      if (ball.getPassenger() != null)
      {
        ((CraftFallingSand)ball.getPassenger()).getHandle().ticksLived = 1;
        ball.getPassenger().setTicksLived(1);
      }
      Player[] arrayOfPlayer;
      j = (arrayOfPlayer = UtilServer.getPlayers()).length;i = 0; continue;Player other = arrayOfPlayer[i];
      if (UtilMath.offset(ball, other) <= 1.5D) {
        if (Recharge.Instance.use(other, GetName() + " Bump", 200L, false, false))
        {
          double power = 0.4D;
          if (other.isSprinting()) {
            power = 0.7D;
          }
          UtilAction.velocity(ball, UtilAlg.getTrajectory2d(other, ball), power, false, 0.0D, 0.0D, 0.0D, false);
          
          other.getWorld().playSound(other.getLocation(), Sound.ITEM_PICKUP, 0.2F, 0.2F);
        }
      }
      i++;
    }
  }
  
  @EventHandler
  public void Snort(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (!UtilEvent.isAction(event, UtilEvent.ActionType.L)) {
      return;
    }
    for (Bat ball : this._active) {
      if (UtilMath.offset(ball, player) <= 2.0D)
      {
        if (!Recharge.Instance.use(player, GetName() + " Kick", 1000L, false, false)) {
          return;
        }
        Recharge.Instance.useForce(player, GetName() + " Bump", 1000L);
        
        UtilAction.velocity(ball, UtilAlg.getTrajectory2d(player, ball), 2.0D, false, 0.0D, 0.0D, 0.0D, false);
        
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_PICKUP, 1.0F, 0.1F);
      }
    }
  }
}
