package mineplex.core.mount.types;

import java.util.HashMap;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseBase;
import mineplex.core.disguise.disguises.DisguiseSheep;
import mineplex.core.mount.HorseMount;
import mineplex.core.mount.MountManager;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Horse.Style;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

public class MountSheep
  extends HorseMount
{
  public MountSheep(MountManager manager)
  {
    super(manager, "Techno Sheep", new String[] {ChatColor.RESET + "Muley muley!" }, Material.WOOL, (byte)14, 3000, Horse.Color.BLACK, Horse.Style.BLACK_DOTS, Horse.Variant.MULE, 1.0D, null);
  }
  
  public void EnableCustom(Player player)
  {
    player.leaveVehicle();
    player.eject();
    
    this.Manager.DeregisterAll(player);
    
    Horse horse = (Horse)player.getWorld().spawn(player.getLocation(), Horse.class);
    
    horse.setOwner(player);
    horse.setMaxDomestication(1);
    horse.getInventory().setSaddle(new ItemStack(Material.SADDLE));
    
    DisguiseSheep disguise = new DisguiseSheep(horse);
    disguise.setName(player.getName(), this.Manager.getClientManager().Get(player).GetRank());
    
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
    
    UtilPlayer.message(player, F.main("Mount", "You spawned " + F.elem(GetName()) + "."));
    
    this._active.put(player, horse);
  }
  
  @EventHandler
  public void updateColor(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Horse horse : GetActive().values())
    {
      DisguiseBase base = this.Manager.getDisguiseManager().getDisguise(horse);
      if ((base != null) && ((base instanceof DisguiseSheep)))
      {
        DisguiseSheep sheep = (DisguiseSheep)base;
        if (horse.getTicksLived() % 4 == 0) {
          sheep.setColor(DyeColor.RED);
        } else if (horse.getTicksLived() % 4 == 1) {
          sheep.setColor(DyeColor.YELLOW);
        } else if (horse.getTicksLived() % 4 == 2) {
          sheep.setColor(DyeColor.GREEN);
        } else if (horse.getTicksLived() % 4 == 3) {
          sheep.setColor(DyeColor.BLUE);
        }
      }
    }
  }
}
