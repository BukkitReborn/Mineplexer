package mineplex.core.memory;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mineplex.core.MiniPlugin;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import net.minecraft.server.v1_7_R4.CraftingManager;
import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class MemoryFix
  extends MiniPlugin
{
  private static Field _intHashMap;
  
  public MemoryFix(JavaPlugin plugin)
  {
    super("Memory Fix", plugin);
  }
  
  @EventHandler
  public void fixInventoryLeaks(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOW) {
      return;
    }
    Iterator localIterator2;
    for (Iterator localIterator1 = Bukkit.getWorlds().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      World world = (World)localIterator1.next();
      
      localIterator2 = ((CraftWorld)world).getHandle().tileEntityList.iterator(); continue;Object tileEntity = localIterator2.next();
      if ((tileEntity instanceof IInventory))
      {
        Iterator<HumanEntity> entityIterator = ((IInventory)tileEntity).getViewers().iterator();
        while (entityIterator.hasNext())
        {
          HumanEntity entity = (HumanEntity)entityIterator.next();
          if (((entity instanceof CraftPlayer)) && (!((CraftPlayer)entity).isOnline())) {
            entityIterator.remove();
          }
        }
      }
    }
    CraftingManager.getInstance().lastCraftView = null;
    CraftingManager.getInstance().lastRecipe = null;
  }
  
  @EventHandler
  public void fixEntityTrackerLeak(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SLOW) {}
  }
}
