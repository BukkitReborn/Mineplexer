package mineplex.core.ignore;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mineplex.core.MiniDbClientPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.jsonchat.ChildJsonMessage;
import mineplex.core.common.jsonchat.JsonMessage;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.ignore.command.Ignore;
import mineplex.core.ignore.command.Unignore;
import mineplex.core.ignore.data.IgnoreData;
import mineplex.core.ignore.data.IgnoreRepository;
import mineplex.core.portal.Portal;
import mineplex.core.preferences.PreferencesManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class IgnoreManager
  extends MiniDbClientPlugin<IgnoreData>
{
  private PreferencesManager _preferenceManager;
  private IgnoreRepository _repository;
  private Portal _portal;
  
  public IgnoreManager(JavaPlugin plugin, CoreClientManager clientManager, PreferencesManager preferences, Portal portal)
  {
    super("Ignore", plugin, clientManager);
    
    this._preferenceManager = preferences;
    this._repository = new IgnoreRepository(plugin);
    this._portal = portal;
  }
  
  public PreferencesManager getPreferenceManager()
  {
    return this._preferenceManager;
  }
  
  public Portal getPortal()
  {
    return this._portal;
  }
  
  public boolean isIgnoring(Player caller, Player target)
  {
    return isIgnoring(caller, target.getName());
  }
  
  public boolean isIgnoring(Player caller, String target)
  {
    IgnoreData data = (IgnoreData)Get(caller);
    for (String ignored : data.getIgnored()) {
      if (ignored.equalsIgnoreCase(target)) {
        return true;
      }
    }
    return false;
  }
  
  public void addCommands()
  {
    addCommand(new Ignore(this));
    addCommand(new Unignore(this));
  }
  
  protected IgnoreData AddPlayer(String player)
  {
    return new IgnoreData();
  }
  
  @EventHandler
  public void onChat(AsyncPlayerChatEvent event)
  {
    if (this.ClientManager.Get(event.getPlayer()).GetRank().Has(Rank.HELPER)) {
      return;
    }
    Iterator<Player> itel = event.getRecipients().iterator();
    while (itel.hasNext())
    {
      Player player = (Player)itel.next();
      
      IgnoreData info = (IgnoreData)Get(player);
      for (String ignored : info.getIgnored()) {
        if (ignored.equalsIgnoreCase(event.getPlayer().getName()))
        {
          itel.remove();
          
          break;
        }
      }
    }
  }
  
  public void addIgnore(final Player caller, final String name)
  {
    if (caller.getName().equalsIgnoreCase(name))
    {
      caller.sendMessage(F.main(getName(), ChatColor.GRAY + "You cannot ignore yourself"));
      return;
    }
    for (String status : ((IgnoreData)Get(caller)).getIgnored()) {
      if (status.equalsIgnoreCase(name))
      {
        caller.sendMessage(F.main(getName(), ChatColor.GREEN + name + ChatColor.GRAY + " has already been ignored."));
        return;
      }
    }
    IgnoreData ignoreData = (IgnoreData)Get(caller);
    if (ignoreData != null) {
      ignoreData.getIgnored().add(name);
    }
    Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        IgnoreManager.this._repository.addIgnore(caller, name);
        
        Bukkit.getServer().getScheduler().runTask(IgnoreManager.this._plugin, new Runnable()
        {
          public void run()
          {
            this.val$caller.sendMessage(F.main(IgnoreManager.this.getName(), "Now ignoring " + ChatColor.GREEN + this.val$name));
          }
        });
      }
    });
  }
  
  public void removeIgnore(final Player caller, final String name)
  {
    IgnoreData ignoreData = (IgnoreData)Get(caller);
    if (ignoreData != null)
    {
      Iterator<String> itel = ignoreData.getIgnored().iterator();
      while (itel.hasNext())
      {
        String ignored = (String)itel.next();
        if (ignored.equalsIgnoreCase(name))
        {
          itel.remove();
          break;
        }
      }
    }
    caller.sendMessage(F.main(getName(), "No longer ignoring " + ChatColor.GREEN + name + ChatColor.GRAY + "!"));
    
    Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        IgnoreManager.this._repository.removeIgnore(caller.getName(), name);
      }
    });
  }
  
  public void showIgnores(Player caller)
  {
    List<String> ignoredPlayers = ((IgnoreData)Get(caller)).getIgnored();
    
    caller.sendMessage(C.cAqua + C.Strike + "=====================[" + ChatColor.RESET + C.cWhite + C.Bold + "Ignoring" + 
      ChatColor.RESET + C.cAqua + C.Strike + "]======================");
    
    ArrayList<ChildJsonMessage> sentLines = new ArrayList();
    for (String ignored : ignoredPlayers)
    {
      ChildJsonMessage message = new JsonMessage("").color("white").extra("").color("white");
      
      message.add("Ignoring " + ignored).color("gray");
      
      message.add(" - ").color("white");
      
      message.add("Unignore").color("red").bold().click("run_command", "/unignore " + ignored)
        .hover("show_text", "Stop ignoring " + ignored);
      
      sentLines.add(message);
    }
    for (JsonMessage msg : sentLines) {
      msg.sendToPlayer(caller);
    }
    if (sentLines.isEmpty())
    {
      caller.sendMessage(" ");
      caller.sendMessage("Welcome to your Ignore List!");
      caller.sendMessage(" ");
      caller.sendMessage("To ignore people, type " + C.cGreen + "/ignore <Player Name>");
      caller.sendMessage(" ");
      caller.sendMessage("Type " + C.cGreen + "/ignore" + ChatColor.RESET + " at any time to view the ignored!");
      caller.sendMessage(" ");
    }
    ChildJsonMessage message = new JsonMessage("").extra(C.cAqua + C.Strike + 
      "=====================================================");
    
    message.sendToPlayer(caller);
  }
  
  public void processLoginResultSet(String playerName, int accountId, ResultSet resultSet)
    throws SQLException
  {
    Set(playerName, this._repository.loadClientInformation(resultSet));
  }
  
  public String getQuery(int accountId, String uuid, String name)
  {
    return 
      "SELECT tA.Name FROM accountIgnore INNER Join accounts AS fA ON fA.uuid = uuidIgnorer INNER JOIN accounts AS tA ON tA.uuid = uuidIgnored LEFT JOIN playerMap ON tA.name = playerName WHERE uuidIgnorer = '" + uuid + "';";
  }
}
