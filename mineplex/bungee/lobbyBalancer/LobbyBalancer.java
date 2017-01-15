package mineplex.bungee.lobbyBalancer;

import java.io.File;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import mineplex.serverdata.Region;
import mineplex.serverdata.data.MinecraftServer;
import mineplex.serverdata.servers.ServerManager;
import mineplex.serverdata.servers.ServerRepository;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.event.EventHandler;

public class LobbyBalancer
  implements Listener, Runnable
{
  private Plugin _plugin;
  private ServerRepository _repository;
  private List<MinecraftServer> _sortedLobbies = new ArrayList();
  private static Object _serverLock = new Object();
  private int _lobbyIndex = 0;
  
  public LobbyBalancer(Plugin plugin)
  {
    this._plugin = plugin;
    
    Region region = !new File("eu.dat").exists() ? Region.US : Region.EU;
    this._repository = ServerManager.getServerRepository(region);
    
    loadLobbyServers();
    
    this._plugin.getProxy().getPluginManager().registerListener(this._plugin, this);
    this._plugin.getProxy().getScheduler().schedule(this._plugin, this, 250L, 250L, TimeUnit.MILLISECONDS);
  }
  
  @EventHandler
  public void playerConnect(ServerConnectEvent event)
  {
    if (!event.getTarget().getName().equalsIgnoreCase("Lobby")) {
      return;
    }
    synchronized (_serverLock)
    {
      if ((this._lobbyIndex >= this._sortedLobbies.size()) || (((MinecraftServer)this._sortedLobbies.get(this._lobbyIndex)).getPlayerCount() >= ((MinecraftServer)this._sortedLobbies.get(this._lobbyIndex)).getMaxPlayerCount())) {
        this._lobbyIndex = 0;
      }
      event.setTarget(this._plugin.getProxy().getServerInfo(((MinecraftServer)this._sortedLobbies.get(this._lobbyIndex)).getName()));
      ((MinecraftServer)this._sortedLobbies.get(this._lobbyIndex)).incrementPlayerCount(1);
      System.out.println("Sending " + event.getPlayer().getName() + " to " + ((MinecraftServer)this._sortedLobbies.get(this._lobbyIndex)).getName() + "(" + ((MinecraftServer)this._sortedLobbies.get(this._lobbyIndex)).getPublicAddress() + ")");
      this._lobbyIndex += 1;
    }
  }
  
  public void run()
  {
    loadLobbyServers();
  }
  
  public void loadLobbyServers()
  {
    Collection<MinecraftServer> servers = this._repository.getServerStatuses();
    synchronized (_serverLock)
    {
      long startTime = System.currentTimeMillis();
      this._sortedLobbies.clear();
      for (MinecraftServer server : servers) {
        if (server.getName() != null)
        {
          InetSocketAddress socketAddress = new InetSocketAddress(server.getPublicAddress(), server.getPort());
          this._plugin.getProxy().getServers().put(server.getName(), this._plugin.getProxy().constructServerInfo(server.getName(), socketAddress, "LobbyBalancer", false));
          if (server.getName().toUpperCase().contains("LOBBY")) {
            if ((server.getMotd() == null) || (!server.getMotd().contains("Restarting"))) {
              this._sortedLobbies.add(server);
            }
          }
        }
      }
      Collections.sort(this._sortedLobbies, new LobbySorter());
      
      long timeSpentInLock = System.currentTimeMillis() - startTime;
      if (timeSpentInLock > 50L) {
        System.out.println("[==] TIMING [==] Locked loading servers for " + timeSpentInLock + "ms");
      }
    }
  }
}
