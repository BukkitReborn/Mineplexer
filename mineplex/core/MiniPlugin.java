package mineplex.core;

import java.io.PrintStream;
import mineplex.core.command.CommandCenter;
import mineplex.core.command.ICommand;
import mineplex.core.common.util.F;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilTime;
import mineplex.core.common.util.UtilTime.TimeUnit;
import org.bukkit.Server;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class MiniPlugin
  implements Listener
{
  protected String _moduleName = "Default";
  protected JavaPlugin _plugin;
  protected NautHashMap<String, ICommand> _commands;
  
  public MiniPlugin(String moduleName, JavaPlugin plugin)
  {
    this._moduleName = moduleName;
    this._plugin = plugin;
    this._commands = new NautHashMap();
    onEnable();
    registerEvents(this);
  }
  
  public PluginManager getPluginManager()
  {
    return this._plugin.getServer().getPluginManager();
  }
  
  public BukkitScheduler getScheduler()
  {
    return this._plugin.getServer().getScheduler();
  }
  
  public JavaPlugin getPlugin()
  {
    return this._plugin;
  }
  
  public void registerEvents(Listener listener)
  {
    this._plugin.getServer().getPluginManager().registerEvents(listener, this._plugin);
  }
  
  public void registerSelf()
  {
    registerEvents(this);
  }
  
  public void deregisterSelf()
  {
    HandlerList.unregisterAll(this);
  }
  
  public final void onEnable()
  {
    long epoch = System.currentTimeMillis();
    log("Initializing...");
    enable();
    addCommands();
    log("Enabled in " + UtilTime.convertString(System.currentTimeMillis() - epoch, 1, UtilTime.TimeUnit.FIT) + ".");
  }
  
  public final void onDisable()
  {
    disable();
    log("Disabled.");
  }
  
  public void enable() {}
  
  public void disable() {}
  
  public void addCommands() {}
  
  public final String getName()
  {
    return this._moduleName;
  }
  
  public final void addCommand(ICommand command)
  {
    CommandCenter.Instance.AddCommand(command);
  }
  
  public final void removeCommand(ICommand command)
  {
    CommandCenter.Instance.RemoveCommand(command);
  }
  
  public void log(String message)
  {
    System.out.println(F.main(this._moduleName, message));
  }
  
  public void runAsync(Runnable runnable)
  {
    this._plugin.getServer().getScheduler().runTaskAsynchronously(this._plugin, runnable);
  }
  
  public void runSync(Runnable runnable)
  {
    this._plugin.getServer().getScheduler().runTask(this._plugin, runnable);
  }
  
  public void runSyncLater(Runnable runnable, long delay)
  {
    this._plugin.getServer().getScheduler().runTaskLater(this._plugin, runnable, delay);
  }
}
