package mineplex.core.mount;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.Navigation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftCreature;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HorseMount
  extends Mount<Horse>
{
  protected Horse.Color _color;
  protected Horse.Style _style;
  protected Horse.Variant _variant;
  protected double _jump;
  protected Material _armor;
  
  public HorseMount(MountManager manager, String name, String[] desc, Material displayMaterial, byte displayData, int cost, Horse.Color color, Horse.Style style, Horse.Variant variant, double jump, Material armor)
  {
    super(manager, name, displayMaterial, displayData, desc, cost);
    this.KnownPackage = false;
    
    this._color = color;
    this._style = style;
    this._variant = variant;
    this._jump = jump;
    this._armor = armor;
  }
  
  @EventHandler
  public void UpdateHorse(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SEC) {
      return;
    }
    Iterator<Player> activeIterator = this._active.keySet().iterator();
    while (activeIterator.hasNext())
    {
      Player player = (Player)activeIterator.next();
      Horse horse = (Horse)this._active.get(player);
      if (!horse.isValid())
      {
        horse.remove();
        activeIterator.remove();
      }
      else
      {
        EntityCreature ec = ((CraftCreature)horse).getHandle();
        Navigation nav = ec.getNavigation();
        
        Location target = player.getLocation().add(UtilAlg.getTrajectory(player, horse).multiply(2));
        if (UtilMath.offset(horse.getLocation(), target) > 12.0D)
        {
          target = horse.getLocation();
          target.add(UtilAlg.getTrajectory(horse, player).multiply(12));
          nav.a(target.getX(), target.getY(), target.getZ(), 1.399999976158142D);
        }
        else if (UtilMath.offset(horse, player) > 4.0D)
        {
          nav.a(target.getX(), target.getY(), target.getZ(), 1.399999976158142D);
        }
      }
    }
  }
  
  public void EnableCustom(Player player)
  {
    player.leaveVehicle();
    player.eject();
    
    this.Manager.DeregisterAll(player);
    
    Horse horse = (Horse)player.getWorld().spawn(player.getLocation(), Horse.class);
    horse.setAdult();
    horse.setAgeLock(true);
    horse.setColor(this._color);
    horse.setStyle(this._style);
    horse.setVariant(this._variant);
    horse.setOwner(player);
    horse.setMaxDomestication(1);
    horse.setJumpStrength(this._jump);
    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
    if (horse.getVariant() == Horse.Variant.MULE) {
      horse.setCarryingChest(true);
    }
    if (this._armor != null) {
      horse.getInventory().setArmor(new ItemStack(this._armor));
    }
    horse.setCustomName(player.getName() + "'s " + GetName());
    
    UtilPlayer.message(player, F.main("Mount", "You spawned " + F.elem(GetName()) + "."));
    
    this._active.put(player, horse);
  }
  
  public void Disable(Player player)
  {
    Horse horse = (Horse)this._active.remove(player);
    if (horse != null)
    {
      horse.remove();
      
      UtilPlayer.message(player, F.main("Mount", "You despawned " + F.elem(GetName()) + "."));
      
      this.Manager.removeActive(player);
    }
  }
}
