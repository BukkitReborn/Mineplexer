package mineplex.bungee.playerTracker;

import java.io.File;
import java.io.PrintStream;
import mineplex.serverdata.Region;
import mineplex.serverdata.data.DataRepository;
import mineplex.serverdata.data.PlayerStatus;
import mineplex.serverdata.redis.RedisDataRepository;
import mineplex.serverdata.servers.ServerManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.event.EventHandler;

public class PlayerTracker
  implements Listener
{
  private static final int DEFAULT_STATUS_TIMEOUT = 28800;
  private DataRepository<PlayerStatus> _repository;
  private Plugin _plugin;
  
  public PlayerTracker(Plugin plugin)
  {
    this._plugin = plugin;
    
    this._plugin.getProxy().getPluginManager().registerListener(this._plugin, this);
    
    Region region = !new File("eu.dat").exists() ? Region.US : Region.EU;
    this._repository = new RedisDataRepository(ServerManager.getMasterConnection(), ServerManager.getSlaveConnection(), 
      region, PlayerStatus.class, "playerStatus");
    
    System.out.println("Initialized PlayerTracker.");
  }
  
  @EventHandler
  public void playerConnect(final ServerConnectedEvent event)
  {
    this._plugin.getProxy().getScheduler().runAsync(this._plugin, new Runnable()
    {
      public void run()
      {
        PlayerStatus snapshot = new PlayerStatus(event.getPlayer().getName(), event.getServer().getInfo().getName());
        PlayerTracker.this._repository.addElement(snapshot, 28800);
      }
    });
  }
  
  @EventHandler
  public void playerDisconnect(final PlayerDisconnectEvent event)
  {
    this._plugin.getProxy().getScheduler().runAsync(this._plugin, new Runnable()
    {
      public void run()
      {
        PlayerTracker.this._repository.removeElement(event.getPlayer().getName());
      }
    });
  }
}
