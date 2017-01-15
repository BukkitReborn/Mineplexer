package mineplex.core.gadget.gadgets;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseBat;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.projectile.IThrown;
import mineplex.core.projectile.ProjectileManager;
import mineplex.core.projectile.ProjectileUser;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MorphBat
  extends MorphGadget
  implements IThrown
{
  public MorphBat(GadgetManager manager)
  {
    super(manager, "Bat Morph", new String[] {C.cWhite + "Flap around and annoy people by", C.cWhite + "screeching loudly into their ears!", " ", C.cYellow + "Left Click" + C.cGray + " to use " + C.cGreen + "Screech", C.cYellow + "Double Jump" + C.cGray + " to use " + C.cGreen + "Flap", C.cYellow + "Tap Sneak" + C.cGray + " to use " + C.cGreen + "Poop" }, 40000, Material.SKULL_ITEM, (byte)1);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguiseBat disguise = new DisguiseBat(player);
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
  public void Screech(PlayerInteractEvent event)
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
    player.getWorld().playSound(player.getLocation(), Sound.BAT_HURT, 1.0F, 1.0F);
  }
  
  @EventHandler
  public void Poop(PlayerToggleSneakEvent event)
  {
    Player player = event.getPlayer();
    if (player.isSneaking()) {
      return;
    }
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }
    if (!IsActive(player)) {
      return;
    }
    if (!Recharge.Instance.use(player, "Poop", 4000L, true, false)) {
      return;
    }
    Item item = player.getWorld().dropItem(player.getEyeLocation().add(player.getLocation().getDirection()), ItemStackFactory.Instance.CreateStack(Material.MELON_SEEDS));
    UtilAction.velocity(item, player.getLocation().getDirection(), 
      0.01D, true, -0.3D, 0.0D, 10.0D, false);
    
    this.Manager.getProjectileManager().AddThrow(item, player, this, -1L, true, true, true, 
      null, 1.0F, 1.0F, null, null, 0, UpdateType.TICK, 0.5F);
    
    UtilPlayer.message(player, F.main("Skill", "You used " + F.skill("Poop") + "."));
    
    player.getWorld().playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.0F, 0.1F);
  }
  
  public void Collide(LivingEntity target, Block block, ProjectileUser data)
  {
    if (target != null)
    {
      target.playEffect(EntityEffect.HURT);
      
      target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 80, 1), true);
      
      UtilPlayer.message(target, F.main("Skill", F.name(UtilEnt.getName(data.GetThrower())) + " hit you with " + F.skill("Bat Poop") + "."));
      
      UtilPlayer.message(data.GetThrower(), F.main("Skill", "You hit " + F.name(UtilEnt.getName(target)) + " with " + F.skill("Bat Poop") + "."));
    }
    data.GetThrown().remove();
  }
  
  public void Idle(ProjectileUser data)
  {
    data.GetThrown().remove();
  }
  
  public void Expire(ProjectileUser data)
  {
    data.GetThrown().remove();
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
    
    UtilAction.velocity(player, player.getLocation().getDirection(), 0.8D, false, 0.0D, 0.5D, 0.8D, true);
    
    player.getWorld().playSound(player.getLocation(), Sound.BAT_TAKEOFF, (float)(0.3D + player.getExp()), (float)(Math.random() / 2.0D + 0.5D));
    
    Recharge.Instance.use(player, GetName(), 40L, false, false);
  }
  
  @EventHandler
  public void FlapUpdate(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Player player : GetActive()) {
      if (player.getGameMode() != GameMode.CREATIVE) {
        if ((UtilEnt.isGrounded(player)) || (UtilBlock.solid(player.getLocation().getBlock().getRelative(BlockFace.DOWN)))) {
          player.setAllowFlight(true);
        } else if (Recharge.Instance.usable(player, GetName())) {
          player.setAllowFlight(true);
        }
      }
    }
  }
}
