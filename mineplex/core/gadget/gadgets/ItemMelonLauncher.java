package mineplex.core.gadget.gadgets;

import java.util.ArrayList;
import java.util.Iterator;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.projectile.IThrown;
import mineplex.core.projectile.ProjectileManager;
import mineplex.core.projectile.ProjectileUser;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Effect;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class ItemMelonLauncher
  extends ItemGadget
  implements IThrown
{
  private ArrayList<Item> _melon = new ArrayList();
  
  public ItemMelonLauncher(GadgetManager manager)
  {
    super(manager, "Melon Launcher", new String[] {C.cWhite + "Deliciously fun!", C.cWhite + "Eat the melon slices for a", C.cWhite + "temporary speed boost!" }, -1, Material.MELON_BLOCK, (byte)0, 1000L, new Ammo("Melon Launcher", "100 Melons", Material.MELON_BLOCK, (byte)0, new String[] { C.cWhite + "100 Melons for you to launch!" }, 500, 100));
  }
  
  public void ActivateCustom(Player player)
  {
    Item item = player.getWorld().dropItem(player.getEyeLocation().add(player.getLocation().getDirection()), ItemStackFactory.Instance.CreateStack(Material.MELON_BLOCK));
    UtilAction.velocity(item, player.getLocation().getDirection(), 
      1.0D, false, 0.0D, 0.2D, 10.0D, false);
    
    this.Manager.getProjectileManager().AddThrow(item, player, this, -1L, true, true, true, 
      null, 1.0F, 1.0F, null, null, 0, UpdateType.TICK, 0.5F);
    
    UtilPlayer.message(player, F.main("Skill", "You used " + F.skill(GetName()) + "."));
    
    item.getWorld().playSound(item.getLocation(), Sound.EXPLODE, 0.5F, 0.5F);
  }
  
  public void Collide(LivingEntity target, Block block, ProjectileUser data)
  {
    if (target != null)
    {
      UtilAction.velocity(target, 
        UtilAlg.getTrajectory2d(data.GetThrown().getLocation(), target.getLocation()), 
        1.4D, false, 0.0D, 0.8D, 1.5D, true);
      
      target.playEffect(EntityEffect.HURT);
    }
    smash(data.GetThrown());
  }
  
  public void Idle(ProjectileUser data)
  {
    smash(data.GetThrown());
  }
  
  public void Expire(ProjectileUser data)
  {
    smash(data.GetThrown());
  }
  
  public void smash(Entity ent)
  {
    ent.getWorld().playEffect(ent.getLocation(), Effect.STEP_SOUND, Material.MELON_BLOCK);
    for (int i = 0; i < 10; i++)
    {
      Item item = ent.getWorld().dropItem(ent.getLocation(), ItemStackFactory.Instance.CreateStack(Material.MELON));
      item.setVelocity(new Vector(UtilMath.rr(0.5D, true), UtilMath.rr(0.5D, false), UtilMath.rr(0.5D, true)));
      item.setPickupDelay(30);
      
      this._melon.add(item);
    }
    ent.remove();
  }
  
  @EventHandler
  public void pickupMelon(PlayerPickupItemEvent event)
  {
    if (!this._melon.remove(event.getItem())) {
      return;
    }
    event.getItem().remove();
    
    event.setCancelled(true);
    
    event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.EAT, 1.0F, 1.0F);
    if (!event.getPlayer().hasPotionEffect(PotionEffectType.SPEED)) {
      event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 80, 1), true);
    }
  }
  
  @EventHandler
  public void cleanupMelon(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOW) {
      return;
    }
    for (Iterator<Item> melonIterator = this._melon.iterator(); melonIterator.hasNext();)
    {
      Item melon = (Item)melonIterator.next();
      if ((melon.isDead()) || (!melon.isValid()) || (melon.getTicksLived() > 400))
      {
        melonIterator.remove();
        melon.remove();
      }
    }
    while (this._melon.size() > 60)
    {
      Item item = (Item)this._melon.remove(0);
      item.remove();
    }
  }
}
