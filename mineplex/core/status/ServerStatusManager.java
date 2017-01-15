package mineplex.core.status;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.Callback;
import mineplex.core.monitor.LagMeter;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import mineplex.serverdata.Region;
import mineplex.serverdata.Utility;
import mineplex.serverdata.commands.ServerCommandManager;
import mineplex.serverdata.commands.SuicideCommand;
import mineplex.serverdata.data.MinecraftServer;
import mineplex.serverdata.data.ServerGroup;
import mineplex.serverdata.servers.ServerManager;
import mineplex.serverdata.servers.ServerRepository;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ServerStatusManager
  extends MiniPlugin
{
  public final int DEFAULT_SERVER_TIMEOUT = 30;
  private ServerRepository _repository;
  private CoreClientManager _clientManager;
  private LagMeter _lagMeter;
  private String _name;
  private Region _region;
  private boolean _enabled = true;
  private long _startUpDate;
  private String _ip;
  
  public ServerStatusManager(JavaPlugin plugin, CoreClientManager clientManager, LagMeter lagMeter)
  {
    super("Server Status Manager", plugin);
    
    this._startUpDate = Utility.currentTimeSeconds();
    this._clientManager = clientManager;
    this._lagMeter = lagMeter;
    if (new File("IgnoreUpdates.dat").exists()) {
      this._enabled = false;
    }
    setupConfigValues();
    
    this._name = plugin.getConfig().getString("serverstatus.name");
    
    this._region = (plugin.getConfig().getBoolean("serverstatus.us") ? Region.US : Region.EU);
    
    ServerCommandManager.getInstance().initializeServer(this._name);
    ServerCommandManager.getInstance().registerCommandType("SuicideCommand", SuicideCommand.class, new SuicideHandler(this, this._name, this._region));
    
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
    this._ip = ip;
    
    this._repository = ServerManager.getServerRepository(this._region);
    saveServerStatus();
  }
  
  private void setupConfigValues()
  {
    try
    {
      getPlugin().getConfig().addDefault("serverstatus.connectionurl", "db.mineplex.com:3306");
      getPlugin().getConfig().set("serverstatus.connectionurl", getPlugin().getConfig().getString("serverstatus.connectionurl"));
      
      getPlugin().getConfig().addDefault("serverstatus.username", "MilitaryPolice");
      getPlugin().getConfig().set("serverstatus.username", getPlugin().getConfig().getString("serverstatus.username"));
      
      getPlugin().getConfig().addDefault("serverstatus.password", "CUPr6Wuw2Rus$qap");
      getPlugin().getConfig().set("serverstatus.password", getPlugin().getConfig().getString("serverstatus.password"));
      
      getPlugin().getConfig().addDefault("serverstatus.us", Boolean.valueOf(true));
      getPlugin().getConfig().set("serverstatus.us", Boolean.valueOf(getPlugin().getConfig().getBoolean("serverstatus.us")));
      
      getPlugin().getConfig().addDefault("serverstatus.name", "TEST-1");
      getPlugin().getConfig().set("serverstatus.name", getPlugin().getConfig().getString("serverstatus.name"));
      
      getPlugin().getConfig().addDefault("serverstatus.group", "Testing");
      getPlugin().getConfig().set("serverstatus.group", getPlugin().getConfig().getString("serverstatus.group"));
      
      getPlugin().saveConfig();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void retrieveServerStatuses(final Callback<Collection<MinecraftServer>> callback)
  {
    if (!this._enabled) {
      return;
    }
    getPlugin().getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        if (callback != null) {
          callback.run(ServerStatusManager.this._repository.getServerStatuses());
        }
      }
    });
  }
  
  @EventHandler
  public void saveServerStatus(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FASTER) {
      return;
    }
    if (!this._enabled) {
      return;
    }
    saveServerStatus();
  }
  
  private void saveServerStatus()
  {
    final MinecraftServer serverSnapshot = generateServerSnapshot();
    getPlugin().getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        MinecraftServer server = ServerStatusManager.this._repository.getServerStatus(serverSnapshot.getName());
        int timeout = 30;
        if ((server != null) && (!server.getPublicAddress().equalsIgnoreCase(serverSnapshot.getPublicAddress()))) {
          timeout = -30;
        }
        ServerStatusManager.this._repository.updataServerStatus(serverSnapshot, timeout);
      }
    });
  }
  
  private MinecraftServer generateServerSnapshot()
  {
    ServerListPingEvent event = new ServerListPingEvent(null, getPlugin().getServer().getMotd(), getPlugin().getServer().getOnlinePlayers().size(), getPlugin().getServer().getMaxPlayers());
    getPluginManager().callEvent(event);
    
    String motd = this._enabled ? event.getMotd() : "Restarting";
    int playerCount = this._clientManager.getPlayerCountIncludingConnecting();
    int maxPlayerCount = event.getMaxPlayers();
    int tps = (int)this._lagMeter.getTicksPerSecond();
    String address = Bukkit.getServer().getIp().isEmpty() ? this._ip : Bukkit.getServer().getIp();
    int port = this._plugin.getServer().getPort();
    String group = this._plugin.getConfig().getString("serverstatus.group");
    int ram = (int)((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().freeMemory()) / 1048576L);
    int maxRam = (int)(Runtime.getRuntime().maxMemory() / 1048576L);
    
    return new MinecraftServer(this._name, group, motd, address, port, playerCount, 
      maxPlayerCount, tps, ram, maxRam, this._startUpDate);
  }
  
  public String getCurrentServerName()
  {
    return this._name;
  }
  
  public void retrieveServerGroups(final Callback<Collection<ServerGroup>> callback)
  {
    if (!this._enabled) {
      return;
    }
    getPlugin().getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        if (callback != null) {
          callback.run(ServerStatusManager.this._repository.getServerGroups(null));
        }
      }
    });
  }
  
  public Region getRegion()
  {
    return this._region;
  }
  
  public void disableStatus()
  {
    this._enabled = false;
    saveServerStatus();
  }
}
