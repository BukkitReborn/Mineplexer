package mineplex.core.updater;

import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Updater
  implements Runnable
{
  private JavaPlugin _plugin;
  
  public Updater(JavaPlugin plugin)
  {
    this._plugin = plugin;
    this._plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this._plugin, this, 0L, 1L);
  }
  
  public void run()
  {
    UpdateType[] arrayOfUpdateType;
    int j = (arrayOfUpdateType = UpdateType.values()).length;
    for (int i = 0; i < j; i++)
    {
      UpdateType updateType = arrayOfUpdateType[i];
      if (updateType.Elapsed()) {
        this._plugin.getServer().getPluginManager().callEvent(new UpdateEvent(updateType));
      }
    }
  }
}
