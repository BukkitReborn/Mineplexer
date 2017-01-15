package mineplex.core.mount;

import java.util.HashMap;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import org.bukkit.Material;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class DragonMount
  extends Mount<DragonData>
{
  public DragonMount(MountManager manager, String name, String[] desc, Material displayMaterial, byte displayData, int cost)
  {
    super(manager, name, displayMaterial, displayData, desc, cost);
    
    this.KnownPackage = false;
  }
  
  public void EnableCustom(Player player)
  {
    player.leaveVehicle();
    player.eject();
    
    this.Manager.DeregisterAll(player);
    
    UtilPlayer.message(player, F.main("Mount", "You spawned " + F.elem(GetName()) + "."));
    
    DragonData dragonData = new DragonData(this, player);
    
    dragonData.Dragon.setMaxHealth(1.0D);
    dragonData.Dragon.setHealth(1.0D);
    this._active.put(player, dragonData);
  }
  
  public void Disable(Player player)
  {
    DragonData data = (DragonData)this._active.remove(player);
    if (data != null)
    {
      data.Dragon.remove();
      data.Chicken.remove();
      
      UtilPlayer.message(player, F.main("Mount", "You despawned " + F.elem(GetName()) + "."));
      
      this.Manager.removeActive(player);
    }
  }
}
