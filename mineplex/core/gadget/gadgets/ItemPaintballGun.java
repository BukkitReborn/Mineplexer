package mineplex.core.gadget.gadgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import mineplex.core.blockrestore.BlockRestore;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.event.GadgetBlockEvent;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

public class ItemPaintballGun
  extends ItemGadget
{
  private HashSet<Projectile> _balls = new HashSet();
  
  public ItemPaintballGun(GadgetManager manager)
  {
    super(manager, "Paintball Gun", new String[] {C.cWhite + "PEW PEW PEW PEW!" }, -1, Material.GOLD_BARDING, (byte)0, 200L, new Ammo("Paintball Gun", "100 Paintballs", Material.GOLD_BARDING, (byte)0, new String[] { C.cWhite + "100 Paintballs for you to shoot!" }, 500, 100));
  }
  
  public void ActivateCustom(Player player)
  {
    Projectile proj = player.launchProjectile(EnderPearl.class);
    proj.setVelocity(proj.getVelocity().multiply(2));
    this._balls.add(proj);
    
    player.getWorld().playSound(player.getLocation(), Sound.CHICKEN_EGG_POP, 1.5F, 1.2F);
  }
  
  @EventHandler
  public void Paint(ProjectileHitEvent event)
  {
    if (!this._balls.remove(event.getEntity())) {
      return;
    }
    Location loc = event.getEntity().getLocation().add(event.getEntity().getVelocity());
    loc.getWorld().playEffect(loc, Effect.STEP_SOUND, 49);
    
    byte color = 2;
    double r = Math.random();
    if (r > 0.8D) {
      color = 4;
    } else if (r > 0.6D) {
      color = 5;
    } else if (r > 0.4D) {
      color = 9;
    } else if (r > 0.2D) {
      color = 14;
    }
    for (Block block : UtilBlock.getInRadius(loc, 3.0D).keySet())
    {
      if (block.getType() == Material.PORTAL) {
        return;
      }
      if (block.getType() == Material.CACTUS) {
        return;
      }
      if (block.getType() == Material.SUGAR_CANE_BLOCK) {
        return;
      }
    }
    List<Block> blocks = new ArrayList();
    blocks.addAll(UtilBlock.getInRadius(loc, 1.5D).keySet());
    
    GadgetBlockEvent gadgetEvent = new GadgetBlockEvent(this, blocks);
    Bukkit.getServer().getPluginManager().callEvent(gadgetEvent);
    if (gadgetEvent.isCancelled()) {
      return;
    }
    for (Block block : gadgetEvent.getBlocks()) {
      if (UtilBlock.solid(block)) {
        if (block.getType() == Material.CARPET) {
          this.Manager.getBlockRestore().Add(block, 171, color, 4000L);
        } else {
          this.Manager.getBlockRestore().Add(block, 35, color, 4000L);
        }
      }
    }
  }
  
  @EventHandler
  public void Teleport(PlayerTeleportEvent event)
  {
    if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void cleanupBalls(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOW) {
      return;
    }
    for (Iterator<Projectile> ballIterator = this._balls.iterator(); ballIterator.hasNext();)
    {
      Projectile ball = (Projectile)ballIterator.next();
      if ((ball.isDead()) || (!ball.isValid())) {
        ballIterator.remove();
      }
    }
  }
}
