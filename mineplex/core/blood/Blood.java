package mineplex.core.blood;

import java.util.HashMap;
import java.util.HashSet;
import mineplex.core.MiniPlugin;
import mineplex.core.common.util.UtilServer;
import mineplex.core.itemstack.ItemBuilder;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class Blood
  extends MiniPlugin
{
  private HashMap<Item, Integer> _blood = new HashMap();
  
  public Blood(JavaPlugin plugin)
  {
    super("Blood", plugin);
  }
  
  @EventHandler
  public void Death(PlayerDeathEvent event)
  {
    Effects(event.getEntity(), event.getEntity().getEyeLocation(), 10, 0.5D, Sound.HURT_FLESH, 1.0F, 1.0F, Material.INK_SACK, (byte)1, true);
  }
  
  public void Effects(Player player, Location loc, int particles, double velMult, Sound sound, float soundVol, float soundPitch, Material type, byte data, boolean bloodStep)
  {
    Effects(player, loc, particles, velMult, sound, soundVol, soundPitch, type, data, 10, bloodStep);
  }
  
  public void Effects(Player player, Location loc, int particles, double velMult, Sound sound, float soundVol, float soundPitch, Material type, byte data, int ticks, boolean bloodStep)
  {
    BloodEvent event = new BloodEvent(player, loc, particles, velMult, sound, soundVol, soundPitch, type, data, ticks, bloodStep);
    UtilServer.getServer().getPluginManager().callEvent(event);
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void display(BloodEvent event)
  {
    for (int i = 0; i < event.getParticles(); i++)
    {
      Item item = event.getLocation().getWorld().dropItem(event.getLocation(), new ItemBuilder(event
        .getMaterial(), 1, (short)event.getMaterialData()).setTitle(System.nanoTime()).build());
      
      item.setVelocity(new Vector((Math.random() - 0.5D) * event.getVelocityMult(), Math.random() * event.getVelocityMult(), (Math.random() - 0.5D) * event.getVelocityMult()));
      
      item.setPickupDelay(999999);
      
      this._blood.put(item, Integer.valueOf(event.getTicks()));
    }
    if (event.getBloodStep()) {
      event.getLocation().getWorld().playEffect(event.getLocation(), Effect.STEP_SOUND, 55);
    }
    event.getLocation().getWorld().playSound(event.getLocation(), event.getSound(), event.getSoundVolume(), event.getSoundPitch());
  }
  
  @EventHandler
  public void Update(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    HashSet<Item> expire = new HashSet();
    for (Item cur : this._blood.keySet()) {
      if ((cur.getTicksLived() > ((Integer)this._blood.get(cur)).intValue()) || (!cur.isValid())) {
        expire.add(cur);
      }
    }
    for (Item cur : expire)
    {
      cur.remove();
      this._blood.remove(cur);
    }
  }
  
  @EventHandler
  public void Pickup(PlayerPickupItemEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (this._blood.containsKey(event.getItem())) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void HopperPickup(InventoryPickupItemEvent event)
  {
    if (event.isCancelled()) {
      return;
    }
    if (this._blood.containsKey(event.getItem())) {
      event.setCancelled(true);
    }
  }
}
