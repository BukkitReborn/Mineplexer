package mineplex.core.gadget.gadgets;

import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.projectile.IThrown;
import mineplex.core.projectile.ProjectileManager;
import mineplex.core.projectile.ProjectileUser;
import mineplex.core.updater.UpdateType;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class ItemFleshHook
  extends ItemGadget
  implements IThrown
{
  public ItemFleshHook(GadgetManager manager)
  {
    super(manager, "Flesh Hook", new String[] {C.cWhite + "Make new friends by throwing a hook", C.cWhite + "into their face and pulling them", C.cWhite + "towards you!" }, -1, Material.getMaterial(131), (byte)0, 2000L, new Ammo("Flesh Hook", "50 Flesh Hooks", Material.getMaterial(131), (byte)0, new String[] { C.cWhite + "50 Flesh Hooks for you to use!" }, 1000, 50));
  }
  
  public void ActivateCustom(Player player)
  {
    Item item = player.getWorld().dropItem(player.getEyeLocation().add(player.getLocation().getDirection()), ItemStackFactory.Instance.CreateStack(131));
    UtilAction.velocity(item, player.getLocation().getDirection(), 
      1.6D, false, 0.0D, 0.2D, 10.0D, false);
    
    this.Manager.getProjectileManager().AddThrow(item, player, this, -1L, true, true, true, 
      Sound.FIRE_IGNITE, 1.4F, 0.8F, UtilParticle.ParticleType.CRIT, null, 0, UpdateType.TICK, 0.5F);
    
    UtilPlayer.message(player, F.main("Skill", "You used " + F.skill(GetName()) + "."));
    
    item.getWorld().playSound(item.getLocation(), Sound.IRONGOLEM_THROW, 2.0F, 0.8F);
  }
  
  public void Collide(LivingEntity target, Block block, ProjectileUser data)
  {
    data.GetThrown().remove();
    if (!(data.GetThrower() instanceof Player)) {
      return;
    }
    Player player = (Player)data.GetThrower();
    if (target == null) {
      return;
    }
    if (((target instanceof Player)) && 
      (this.Manager.collideEvent(this, (Player)target))) {
      return;
    }
    UtilAction.velocity(target, 
      UtilAlg.getTrajectory(target.getLocation(), player.getLocation()), 
      3.0D, false, 0.0D, 0.8D, 1.5D, true);
    
    target.playEffect(EntityEffect.HURT);
    
    UtilPlayer.message(target, F.main("Skill", F.name(player.getName()) + " hit you with " + F.skill(GetName()) + "."));
  }
  
  public void Idle(ProjectileUser data)
  {
    data.GetThrown().remove();
  }
  
  public void Expire(ProjectileUser data)
  {
    data.GetThrown().remove();
  }
}
