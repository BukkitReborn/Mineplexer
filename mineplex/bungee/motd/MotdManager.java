package mineplex.bungee.motd;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import mineplex.serverdata.Region;
import mineplex.serverdata.data.DataRepository;
import mineplex.serverdata.redis.RedisDataRepository;
import mineplex.serverdata.servers.ServerManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.api.scheduler.TaskScheduler;
import net.md_5.bungee.event.EventHandler;

public class MotdManager
  implements Listener, Runnable
{
  private Plugin _plugin;
  private DataRepository<GlobalMotd> _repository;
  private Random _random = new Random();
  private String _firstLine = "                §b§l§m   §8§l§m[ §r §9§lMineplex§r §f§lGames§r §8§l§m ]§b§l§m   §r";
  private String _motdLines;
  
  public MotdManager(Plugin plugin)
  {
    this._plugin = plugin;
    
    this._plugin.getProxy().getScheduler().schedule(this._plugin, this, 5L, 30L, TimeUnit.SECONDS);
    this._plugin.getProxy().getPluginManager().registerListener(this._plugin, this);
    
    this._repository = new RedisDataRepository(ServerManager.getMasterConnection(), ServerManager.getSlaveConnection(), 
      Region.ALL, GlobalMotd.class, "globalMotd");
    run();
    if (new File("updateMOTD.dat").exists())
    {
      File motdFile = new File("updateMOTD.dat");
      
      List<String> lines = new ArrayList();
      try
      {
        updateMainMotd(ChatColor.translateAlternateColorCodes('&', (String)Files.readAllLines(motdFile.toPath(), Charset.defaultCharset()).get(0)), ChatColor.translateAlternateColorCodes('&', (String)Files.readAllLines(motdFile.toPath(), Charset.defaultCharset()).get(1)));
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      System.out.println("Updated Bungee MOTD");
    }
  }
  
  @EventHandler
  public void serverPing(ProxyPingEvent event)
  {
    ServerPing serverPing = event.getResponse();
    
    String motd = this._firstLine + "\n" + this._motdLines;
    
    event.setResponse(new ServerPing(serverPing.getVersion(), serverPing.getPlayers(), motd, serverPing.getFaviconObject()));
  }
  
  public void run()
  {
    GlobalMotd motd = (GlobalMotd)this._repository.getElement("MainMotd");
    if (motd != null)
    {
      this._motdLines = motd.getMotd();
      this._firstLine = motd.getHeadline();
    }
  }
  
  public void updateMainMotd(String headline, String motdLine)
  {
    this._repository.addElement(new GlobalMotd("MainMotd", headline, motdLine));
  }
  
  public String getMotdLines()
  {
    return this._motdLines;
  }
}
