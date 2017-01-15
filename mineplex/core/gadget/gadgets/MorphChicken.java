package mineplex.core.gadget.gadgets;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseChicken;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class MorphChicken
  extends MorphGadget
{
  public MorphChicken(GadgetManager manager)
  {
    super(manager, "Chicken Morph", new String[] {C.cWhite + "Soar through the air like a fat Chicken!", " ", C.cYellow + "Left Click" + C.cGray + " to use " + C.cGreen + "Egg Shot", C.cYellow + "Double Jump" + C.cGray + " to use " + C.cGreen + "Flap" }, 20000, Material.FEATHER, (byte)0);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguiseChicken disguise = new DisguiseChicken(player);
    disguise.setName(player.getName(), this.Manager.getClientManager().Get(player).GetRank());
    disguise.setCustomNameVisible(true);
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    this.Manager.getDisguiseManager().undisguise(player);
    
    player.setAllowFlight(false);
    player.setFlying(false);
  }
  
  @EventHandler
  public void Egg(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (!IsActive(player)) {
      return;
    }
    if (!UtilEvent.isAction(event, UtilEvent.ActionType.L)) {
      return;
    }
    if (!Recharge.Instance.use(player, GetName(), 100L, false, false)) {
      return;
    }
    Vector offset = player.getLocation().getDirection();
    if (offset.getY() < 0.0D) {
      offset.setY(0);
    }
    Egg egg = (Egg)player.getWorld().spawn(player.getLocation().add(0.0D, 0.5D, 0.0D).add(offset), Egg.class);
    egg.setVelocity(player.getLocation().getDirection().add(new Vector(0.0D, 0.2D, 0.0D)));
    egg.setShooter(player);
    
    player.getWorld().playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 0.5F, 1.0F);
  }
  
  @EventHandler
  public void Flap(PlayerToggleFlightEvent event)
  {
    Player player = event.getPlayer();
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }
    if (!IsActive(player)) {
      return;
    }
    event.setCancelled(true);
    player.setFlying(false);
    
    player.setAllowFlight(false);
    
    double power = 0.4D + 0.5D * player.getExp();
    
    UtilAction.velocity(player, player.getLocation().getDirection(), power, true, power, 0.0D, 10.0D, true);
    
    player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, (float)(0.3D + player.getExp()), (float)(Math.random() / 2.0D + 1.0D));
    
    Recharge.Instance.use(player, GetName(), 80L, false, false);
    
    player.setExp(Math.max(0.0F, player.getExp() - 0.11111111F));
  }
  
  @EventHandler
  public void FlapUpdate(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Player player : GetActive()) {
      if (player.getGameMode() != GameMode.CREATIVE) {
        if ((UtilEnt.isGrounded(player)) || (UtilBlock.solid(player.getLocation().getBlock().getRelative(BlockFace.DOWN))))
        {
          player.setExp(0.999F);
          player.setAllowFlight(true);
        }
        else if ((Recharge.Instance.usable(player, GetName())) && (player.getExp() > 0.0F))
        {
          player.setAllowFlight(true);
        }
      }
    }
  }
  
  @EventHandler
  public void EggHit(EntityDamageByEntityEvent event)
  {
    if ((event.getDamager() instanceof Egg)) {
      event.getEntity().setVelocity(new Vector(0, 0, 0));
    }
  }
}
