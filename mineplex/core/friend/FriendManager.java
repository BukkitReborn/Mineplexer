package mineplex.core.friend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import mineplex.core.MiniDbClientPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.jsonchat.ChildJsonMessage;
import mineplex.core.common.jsonchat.JsonMessage;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.friend.command.AddFriend;
import mineplex.core.friend.command.DeleteFriend;
import mineplex.core.friend.command.FriendsDisplay;
import mineplex.core.friend.data.FriendData;
import mineplex.core.friend.data.FriendRepository;
import mineplex.core.friend.data.FriendStatus;
import mineplex.core.portal.Portal;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.preferences.UserPreferences;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class FriendManager
  extends MiniDbClientPlugin<FriendData>
{
  private static FriendSorter _friendSorter = new FriendSorter();
  private PreferencesManager _preferenceManager;
  private FriendRepository _repository;
  private Portal _portal;
  
  public FriendManager(JavaPlugin plugin, CoreClientManager clientManager, PreferencesManager preferences, Portal portal)
  {
    super("Friends", plugin, clientManager);
    
    this._preferenceManager = preferences;
    this._repository = new FriendRepository(plugin);
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
  
  public void addCommands()
  {
    addCommand(new AddFriend(this));
    addCommand(new DeleteFriend(this));
    addCommand(new FriendsDisplay(this));
  }
  
  protected FriendData AddPlayer(String player)
  {
    return new FriendData();
  }
  
  @EventHandler
  public void updateFriends(UpdateEvent event)
  {
    if ((event.getType() != UpdateType.SLOW) || (Bukkit.getOnlinePlayers().size() == 0)) {
      return;
    }
    final Player[] onlinePlayers = UtilServer.getPlayers();
    
    Bukkit.getServer().getScheduler().runTaskAsynchronously(this._plugin, new Runnable()
    {
      public void run()
      {
        final NautHashMap<String, FriendData> newData = FriendManager.this._repository.getFriendsForAll(onlinePlayers);
        
        Bukkit.getServer().getScheduler().runTask(FriendManager.this._plugin, new Runnable()
        {
          public void run()
          {
            for (Player player : ) {
              if (newData.containsKey(player.getUniqueId().toString())) {
                ((FriendData)FriendManager.this.Get(player)).setFriends(((FriendData)newData.get(player.getUniqueId().toString())).getFriends());
              } else {
                ((FriendData)FriendManager.this.Get(player)).getFriends().clear();
              }
            }
          }
        });
      }
    });
  }
  
  public void addFriend(final Player caller, final String name)
  {
    if (caller.getName().equalsIgnoreCase(name))
    {
      caller.sendMessage(F.main(getName(), ChatColor.GRAY + "You cannot add yourself as a friend"));
      return;
    }
    boolean update = false;
    for (FriendStatus status : ((FriendData)Get(caller)).getFriends()) {
      if (status.Name.equalsIgnoreCase(name))
      {
        if ((status.Status == FriendStatusType.Pending) || (status.Status == FriendStatusType.Blocked))
        {
          update = true;
          break;
        }
        if (status.Status == FriendStatusType.Denied)
        {
          caller.sendMessage(F.main(getName(), ChatColor.GREEN + name + ChatColor.GRAY + 
            " has denied your friend request."));
          return;
        }
        if (status.Status == FriendStatusType.Accepted)
        {
          caller.sendMessage(F.main(getName(), "You are already friends with " + ChatColor.GREEN + name));
          return;
        }
        if (status.Status == FriendStatusType.Sent)
        {
          caller.sendMessage(F.main(getName(), ChatColor.GREEN + name + ChatColor.GRAY + 
            " has yet to respond to your friend request."));
          return;
        }
      }
    }
    final boolean updateFinal = update;
    
    Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        if (updateFinal)
        {
          FriendManager.this._repository.updateFriend(caller.getName(), name, "Accepted");
          FriendManager.this._repository.updateFriend(name, caller.getName(), "Accepted");
          
          Bukkit.getServer().getScheduler().runTask(FriendManager.this._plugin, new Runnable()
          {
            public void run()
            {
              Iterator<FriendStatus> statusIterator = ((FriendData)FriendManager.this.Get(this.val$caller)).getFriends().iterator();
              while (statusIterator.hasNext())
              {
                FriendStatus status = (FriendStatus)statusIterator.next();
                if (status.Name.equalsIgnoreCase(this.val$name))
                {
                  status.Status = FriendStatusType.Accepted;
                  break;
                }
              }
            }
          });
        }
        else
        {
          FriendManager.this._repository.addFriend(caller, name);
          
          Bukkit.getServer().getScheduler().runTask(FriendManager.this._plugin, new Runnable()
          {
            public void run()
            {
              Iterator<FriendStatus> statusIterator = ((FriendData)FriendManager.this.Get(this.val$caller)).getFriends().iterator();
              while (statusIterator.hasNext())
              {
                FriendStatus status = (FriendStatus)statusIterator.next();
                if (status.Name.equalsIgnoreCase(this.val$name))
                {
                  status.Status = FriendStatusType.Sent;
                  break;
                }
              }
            }
          });
        }
        Bukkit.getServer().getScheduler().runTask(FriendManager.this._plugin, new Runnable()
        {
          public void run()
          {
            if (this.val$updateFinal) {
              this.val$caller.sendMessage(F.main(FriendManager.this.getName(), "You and " + ChatColor.GREEN + this.val$name + ChatColor.GRAY + 
                " are now friends!"));
            } else {
              this.val$caller.sendMessage(F.main(FriendManager.this.getName(), "Added " + ChatColor.GREEN + this.val$name + ChatColor.GRAY + 
                " to your friends list!"));
            }
          }
        });
      }
    });
  }
  
  public void removeFriend(final Player caller, final String name)
  {
    Bukkit.getServer().getScheduler().runTaskAsynchronously(getPlugin(), new Runnable()
    {
      public void run()
      {
        FriendManager.this._repository.removeFriend(caller.getName(), name);
        FriendManager.this._repository.removeFriend(name, caller.getName());
        
        Bukkit.getServer().getScheduler().runTask(FriendManager.this._plugin, new Runnable()
        {
          public void run()
          {
            Iterator<FriendStatus> statusIterator = ((FriendData)FriendManager.this.Get(this.val$caller)).getFriends().iterator();
            while (statusIterator.hasNext())
            {
              FriendStatus status = (FriendStatus)statusIterator.next();
              if (status.Name.equalsIgnoreCase(this.val$name))
              {
                status.Status = FriendStatusType.Blocked;
                break;
              }
            }
            this.val$caller.sendMessage(F.main(FriendManager.this.getName(), "Deleted " + ChatColor.GREEN + this.val$name + ChatColor.GRAY + 
              " from your friends list!"));
          }
        });
      }
    });
  }
  
  public void showFriends(Player caller)
  {
    boolean isStaff = this.ClientManager.Get(caller).GetRank().Has(Rank.HELPER);
    boolean gotAFriend = false;
    List<FriendStatus> friendStatuses = ((FriendData)Get(caller)).getFriends();
    Collections.sort(friendStatuses, _friendSorter);
    
    caller.sendMessage(C.cAqua + C.Strike + "======================[" + ChatColor.RESET + C.cWhite + C.Bold + "Friends" + 
      ChatColor.RESET + C.cAqua + C.Strike + "]======================");
    
    ArrayList<ChildJsonMessage> sentLines = new ArrayList();
    ArrayList<ChildJsonMessage> pendingLines = new ArrayList();
    ArrayList<ChildJsonMessage> onlineLines = new ArrayList();
    ArrayList<ChildJsonMessage> offlineLines = new ArrayList();
    for (FriendStatus friend : friendStatuses) {
      if ((friend.Status != FriendStatusType.Blocked) && (friend.Status != FriendStatusType.Denied)) {
        if ((((UserPreferences)this._preferenceManager.Get(caller)).PendingFriendRequests) || (friend.Status != FriendStatusType.Pending))
        {
          gotAFriend = true;
          
          ChildJsonMessage message = new JsonMessage("").color("white").extra("").color("white");
          if (friend.Status == FriendStatusType.Accepted)
          {
            if (friend.Online)
            {
              if ((friend.ServerName.contains("Staff")) || (friend.ServerName.contains("CUST")))
              {
                if ((isStaff) && (friend.ServerName.contains("Staff"))) {
                  message.add("Teleport").color("green").bold().click("run_command", "/server " + friend.ServerName).hover("show_text", "Teleport to " + friend.Name + "'s server.");
                } else {
                  message.add("No Teleport").color("yellow").bold();
                }
              }
              else {
                message.add("Teleport").color("green").bold().click("run_command", "/server " + friend.ServerName).hover("show_text", "Teleport to " + friend.Name + "'s server.");
              }
              message.add(" - ").color("white");
              message.add("Delete").color("red").bold().click("run_command", "/unfriend " + friend.Name)
                .hover("show_text", "Remove " + friend.Name + " from your friends list.");
              message.add(" - ").color("white");
              message.add(friend.Name).color(friend.Online ? "green" : "gray");
              message.add(" - ").color("white");
              if ((friend.ServerName.contains("Staff")) || (friend.ServerName.contains("CUST")))
              {
                if ((isStaff) && (friend.ServerName.contains("Staff"))) {
                  message.add(friend.ServerName).color("dark_green");
                } else {
                  message.add("Private Staff Server").color("dark_green");
                }
              }
              else {
                message.add(friend.ServerName).color("dark_green");
              }
              onlineLines.add(message);
            }
            else
            {
              message.add("Delete").color("red").bold().click("run_command", "/unfriend " + friend.Name).hover("show_text", "Remove " + friend.Name + " from your friends list.");
              message.add(" - ").color("white");
              message.add(friend.Name).color(friend.Online ? "green" : "gray");
              message.add(" - ").color("white");
              message.add("Offline for ").color("gray").add(UtilTime.MakeStr(friend.LastSeenOnline)).color("gray");
              
              offlineLines.add(message);
            }
          }
          else if (friend.Status == FriendStatusType.Pending)
          {
            message.add("Accept").color("green").bold().click("run_command", "/friend " + friend.Name).hover("show_text", "Accept " + friend.Name + "'s friend request.");
            message.add(" - ").color("white");
            message.add("Deny").color("red").bold().click("run_command", "/unfriend " + friend.Name)
              .hover("show_text", "Deny " + friend.Name + "'s friend request.");
            message.add(" - ").color("white");
            message.add(friend.Name + " Requested Friendship").color("gray");
            
            pendingLines.add(message);
          }
          else if (friend.Status == FriendStatusType.Sent)
          {
            message.add("Cancel").color("red").bold().click("run_command", "/unfriend " + friend.Name).hover("show_text", "Cancel friend request to " + friend.Name);
            message.add(" - ").color("white");
            message.add(friend.Name + " Friendship Request").color("gray");
            
            sentLines.add(message);
          }
        }
      }
    }
    for (JsonMessage msg : sentLines) {
      msg.sendToPlayer(caller);
    }
    for (JsonMessage msg : offlineLines) {
      msg.sendToPlayer(caller);
    }
    for (JsonMessage msg : pendingLines) {
      msg.sendToPlayer(caller);
    }
    for (JsonMessage msg : onlineLines) {
      msg.sendToPlayer(caller);
    }
    if (!gotAFriend)
    {
      caller.sendMessage(" ");
      caller.sendMessage("Welcome to your Friends List!");
      caller.sendMessage(" ");
      caller.sendMessage("To add friends, type " + C.cGreen + "/friend <Player Name>");
      caller.sendMessage(" ");
      caller.sendMessage("Type " + C.cGreen + "/friend" + ChatColor.RESET + " at any time to interact with your friends!");
      caller.sendMessage(" ");
    }
    ChildJsonMessage message = new JsonMessage("").extra(C.cAqua + C.Strike + "======================");
    
    message.add(C.cDAqua + "Toggle GUI").click("run_command", "/friendsdisplay");
    
    message.hover("show_text", C.cAqua + "Toggle friends to display in a inventory");
    
    message.add(C.cAqua + C.Strike + "======================");
    
    message.sendToPlayer(caller);
  }
  
  public boolean isFriends(Player player, String friend)
  {
    FriendData friendData = (FriendData)Get(player);
    for (FriendStatus friendStatus : friendData.getFriends()) {
      if (friendStatus.Name.equalsIgnoreCase(friend)) {
        return true;
      }
    }
    return false;
  }
  
  public void processLoginResultSet(String playerName, int accountId, ResultSet resultSet)
    throws SQLException
  {
    Set(playerName, this._repository.loadClientInformation(resultSet));
  }
  
  public String getQuery(int accountId, String uuid, String name)
  {
    return 
      "SELECT tA.Name, status, tA.lastLogin, now() FROM accountFriend INNER Join accounts AS fA ON fA.uuid = uuidSource INNER JOIN accounts AS tA ON tA.uuid = uuidTarget WHERE uuidSource = '" + uuid + "';";
  }
}
