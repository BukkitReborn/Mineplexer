package mineplex.bungee.playerCount;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import mineplex.bungee.status.InternetStatus;
import mineplex.serverdata.Region;
import mineplex.serverdata.data.BungeeServer;
import mineplex.serverdata.data.DataRepository;
import mineplex.serverdata.redis.RedisDataRepository;
import mineplex.serverdata.servers.ServerManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.ServerPing.Players;
import net.md_5.bungee.api.config.ConfigurationAdapter;
import net.md_5.bungee.api.config.ListenerInfo;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.event.EventHandler;

public class PlayerCount
  implements Listener, Runnable
{
  private DataRepository<BungeeServer> _repository;
  private DataRepository<BungeeServer> _secondRepository;
  private UUID _uuid;
  private Region _region;
  private ListenerInfo _listenerInfo;
  private Plugin _plugin;
  private int _totalPlayers = -1;
  
  public PlayerCount(Plugin plugin)
  {
    this._uuid = UUID.randomUUID();
    this._region = (!new File("eu.dat").exists() ? Region.US : Region.EU);
    this._plugin = plugin;
    
    this._plugin.getProxy().getScheduler().schedule(this._plugin, this, 500L, 500L, TimeUnit.MILLISECONDS);
    this._plugin.getProxy().getPluginManager().registerListener(this._plugin, this);
    
    this._listenerInfo = ((ListenerInfo)this._plugin.getProxy().getConfigurationAdapter().getListeners().iterator().next());
    
    this._repository = new RedisDataRepository(ServerManager.getMasterConnection(), ServerManager.getSlaveConnection(), 
      Region.ALL, BungeeServer.class, "bungeeServers");
    if (this._region == Region.US) {
      this._secondRepository = new RedisDataRepository(ServerManager.getMasterConnection(), ServerManager.getSlaveConnection(), 
        Region.ALL, BungeeServer.class, "bungeeServers");
    } else {
      this._secondRepository = new RedisDataRepository(ServerManager.getMasterConnection(), ServerManager.getSlaveConnection(), 
        Region.ALL, BungeeServer.class, "bungeeServers");
    }
  }
  
  public void run()
  {
    BungeeServer snapshot = generateSnapshot();
    this._repository.addElement(snapshot, 15);
    
    this._totalPlayers = fetchPlayerCount();
  }
  
  private int fetchPlayerCount()
  {
    int totalPlayers = 0;
    for (BungeeServer server : this._repository.getElements()) {
      totalPlayers += server.getPlayerCount();
    }
    for (BungeeServer server : this._secondRepository.getElements()) {
      totalPlayers += server.getPlayerCount();
    }
    return totalPlayers;
  }
  
  private BungeeServer generateSnapshot()
  {
    String name = this._uuid.toString();
    URL whatismyip = null;
    try
    {
      whatismyip = new URL("http://checkip.amazonaws.com/");
    }
    catch (MalformedURLException e)
    {
      e.printStackTrace();
    }
    BufferedReader in = null;
    try
    {
      in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
    }
    catch (IOException e1)
    {
      e1.printStackTrace();
    }
    String ip = null;
    try
    {
      ip = in.readLine();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    String host = ip;
    int port = this._listenerInfo.getHost().getPort();
    boolean connected = InternetStatus.isConnected();
    int playerCount = this._plugin.getProxy().getOnlineCount();
    return new BungeeServer(name, this._region, host, port, playerCount, connected);
  }
  
  @EventHandler
  public void ServerPing(ProxyPingEvent event)
  {
    ServerPing serverPing = event.getResponse();
    
    event.setResponse(new ServerPing(serverPing.getVersion(), new ServerPing.Players(this._totalPlayers + 1, this._totalPlayers, null), serverPing.getDescription(), serverPing.getFaviconObject()));
  }
  
  public int getTotalPlayers()
  {
    return this._totalPlayers;
  }
}
