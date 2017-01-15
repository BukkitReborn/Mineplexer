package mineplex.core.party;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.jsonchat.ChildJsonMessage;
import mineplex.core.common.jsonchat.ClickEvent;
import mineplex.core.common.jsonchat.JsonMessage;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.party.redis.RedisPartyData;
import mineplex.serverdata.Region;
import mineplex.serverdata.commands.ServerTransfer;
import mineplex.serverdata.commands.TransferCommand;
import mineplex.serverdata.data.ServerGroup;
import mineplex.serverdata.servers.ServerManager;
import mineplex.serverdata.servers.ServerRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class Party
{
  private PartyManager _manager;
  private boolean _isHub;
  private String _creator;
  private String _previousServer;
  private ArrayList<String> _players = new ArrayList();
  private NautHashMap<String, Long> _invitee = new NautHashMap();
  private Scoreboard _scoreboard;
  private Objective _scoreboardObj;
  private ArrayList<String> _scoreboardLast = new ArrayList();
  private long _partyOfflineTimer = -1L;
  private long _informNewLeaderTimer = -1L;
  
  public Party(PartyManager manager, RedisPartyData partyData)
  {
    this(manager);
    
    this._creator = partyData.getLeader();
    this._previousServer = partyData.getPreviousServer();
  }
  
  public Party(PartyManager manager)
  {
    this._manager = manager;
    Region region = manager.getPlugin().getConfig().getBoolean("serverstatus.us") ? Region.US : Region.EU;
    String groupName = manager.getPlugin().getConfig().getString("serverstatus.group");
    
    ServerGroup serverGroup = ServerManager.getServerRepository(region).getServerGroup(groupName);
    if (serverGroup == null) {
      return;
    }
    this._isHub = (!serverGroup.getArcadeGroup());
    if (this._isHub)
    {
      this._scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
      this._scoreboardObj = this._scoreboard.registerNewObjective("Party", "dummy");
      this._scoreboardObj.setDisplaySlot(DisplaySlot.SIDEBAR);
      
      this._scoreboard.registerNewTeam(ChatColor.GREEN + "Members");
      Rank[] arrayOfRank;
      int j = (arrayOfRank = Rank.values()).length;
      for (int i = 0; i < j; i++)
      {
        Rank rank = arrayOfRank[i];
        if (rank != Rank.ALL) {
          this._scoreboard.registerNewTeam(rank.Name).setPrefix(rank.GetTag(true, false) + ChatColor.RESET + " ");
        } else {
          this._scoreboard.registerNewTeam(rank.Name).setPrefix("");
        }
      }
      this._scoreboard.registerNewTeam("Party").setPrefix(ChatColor.LIGHT_PURPLE + C.Bold + "Party" + ChatColor.RESET + " ");
      for (Player player : Bukkit.getOnlinePlayers()) {
        this._scoreboard.getTeam(this._manager.GetClients().Get(player).GetRank().Name).addPlayer(player);
      }
    }
  }
  
  public void JoinParty(Player player)
  {
    if (this._players.isEmpty())
    {
      this._players.add(player.getName());
      
      UtilPlayer.message(player, F.main("Party", "You created a new Party."));
      
      this._creator = player.getName();
    }
    else
    {
      this._players.add(player.getName());
      this._invitee.remove(player.getName());
      
      Announce(F.elem(player.getName()) + " has joined the party!");
    }
    if (this._isHub) {
      this._scoreboard.getTeam("Party").addPlayer(player);
    }
  }
  
  public void InviteParty(Player player, boolean inviteeInParty)
  {
    this._invitee.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
    if (this._players.contains(player.getName()))
    {
      UtilPlayer.message(player, F.main("Party", F.name(player.getName()) + " is already in the Party."));
      player.playSound(player.getLocation(), Sound.NOTE_BASS, 1.0F, 1.5F);
    }
    Announce(F.name(player.getName()) + " has been invited to your Party.");
    
    UtilPlayer.message(player, F.main("Party", F.name(GetLeader()) + " invited you to their Party."));
    if (inviteeInParty)
    {
      ChildJsonMessage message = new JsonMessage("").extra(C.mHead + "Party> " + C.mBody + "Type ");
      
      message.add(F.link("/party leave")).click(ClickEvent.RUN_COMMAND, "/party leave");
      
      message.add(C.mBody + " then ");
      
      message.add(F.link("/party " + GetLeader())).click(ClickEvent.RUN_COMMAND, "/party " + GetLeader());
      
      message.add(C.mBody + " to join.");
      
      message.sendToPlayer(player);
    }
    else
    {
      ChildJsonMessage message = new JsonMessage("").extra(C.mHead + "Party> " + C.mBody + "Type ");
      
      message.add(F.link("/party " + GetLeader())).click(ClickEvent.RUN_COMMAND, "/party " + GetLeader());
      
      message.add(C.mBody + " to join.");
      
      message.sendToPlayer(player);
    }
    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.5F);
  }
  
  public void LeaveParty(Player player)
  {
    Announce(F.name(player.getName()) + " has left the Party.");
    
    boolean leader = player.equals(GetLeader());
    
    this._players.remove(player.getName());
    if (this._isHub) {
      this._scoreboard.getTeam(this._manager.GetClients().Get(player).GetRank().Name).addPlayer(player);
    }
    if ((leader) && (this._players.size() > 0)) {
      Announce("Party Leadership passed on to " + F.name(GetLeader()) + ".");
    }
  }
  
  public void KickParty(String player)
  {
    Announce(F.name(player) + " was kicked from the Party.");
    
    this._players.remove(player);
  }
  
  public void PlayerJoin(Player player)
  {
    if (this._isHub) {
      if (this._players.contains(player.getName())) {
        this._scoreboard.getTeam("Party").addPlayer(player);
      } else if (this._manager.GetClients().Get(player) != null) {
        this._scoreboard.getTeam(this._manager.GetClients().Get(player).GetRank().Name).addPlayer(player);
      }
    }
    if (this._creator.equals(player.getName()))
    {
      this._players.remove(player.getName());
      this._players.add(0, player.getName());
      if (this._informNewLeaderTimer < System.currentTimeMillis()) {
        Announce("Party Leadership returned to " + F.name(GetLeader()) + ".");
      }
      if (this._previousServer != null)
      {
        for (String playerName : this._players)
        {
          Player p = Bukkit.getPlayerExact(playerName);
          if (p == null)
          {
            TransferCommand transferCommand = new TransferCommand(
              new ServerTransfer(playerName, this._manager.getServerName()));
            
            transferCommand.setTargetServers(new String[] { this._previousServer });
            
            transferCommand.publish();
          }
        }
        this._previousServer = null;
      }
    }
  }
  
  public void PlayerQuit(Player player)
  {
    if (player.getName().equals(GetLeader()))
    {
      this._players.remove(player.getName());
      this._players.add(player.getName());
      if (this._informNewLeaderTimer < System.currentTimeMillis()) {
        Announce("Party Leadership passed on to " + F.name(GetLeader()) + ".");
      }
    }
  }
  
  public void Announce(String message)
  {
    for (String name : this._players)
    {
      Player player = UtilPlayer.searchExact(name);
      if ((player != null) && (player.isOnline()))
      {
        UtilPlayer.message(player, F.main("Party", message));
        player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.5F);
      }
    }
  }
  
  public void ExpireInvitees()
  {
    Iterator<String> inviteeIterator = this._invitee.keySet().iterator();
    while (inviteeIterator.hasNext())
    {
      String name = (String)inviteeIterator.next();
      if (UtilTime.elapsed(((Long)this._invitee.get(name)).longValue(), 60000L))
      {
        Announce(F.name(name) + " did not respond to the Party invite.");
        inviteeIterator.remove();
      }
    }
  }
  
  public String GetLeader()
  {
    if (this._players.isEmpty()) {
      return this._creator;
    }
    return (String)this._players.get(0);
  }
  
  public Collection<String> GetPlayers()
  {
    return this._players;
  }
  
  public Collection<Player> GetPlayersOnline()
  {
    ArrayList<Player> players = new ArrayList();
    for (String name : this._players)
    {
      Player player = UtilPlayer.searchExact(name);
      if (player != null) {
        players.add(player);
      }
    }
    return players;
  }
  
  public Collection<String> GetInvitees()
  {
    return this._invitee.keySet();
  }
  
  public void UpdateScoreboard()
  {
    if (this._isHub)
    {
      this._scoreboardObj.setDisplayName(GetLeader() + "'s Party");
      for (String pastLine : this._scoreboardLast) {
        this._scoreboard.resetScores(pastLine);
      }
      this._scoreboardLast.clear();
      
      int i = 16;
      String name;
      for (int j = 0; j < this._players.size(); j++)
      {
        name = (String)this._players.get(j);
        Player player = UtilPlayer.searchExact(name);
        
        ChatColor col = ChatColor.GREEN;
        if (player == null) {
          col = ChatColor.RED;
        }
        String line = col + name;
        if (line.length() > 16) {
          line = line.substring(0, 16);
        }
        this._scoreboardObj.getScore(line).setScore(i);
        
        this._scoreboardLast.add(line);
        
        i--;
      }
      for (String name : this._invitee.keySet())
      {
        int time = 1 + (int)((60000L - (System.currentTimeMillis() - ((Long)this._invitee.get(name)).longValue())) / 1000L);
        
        String line = time + " " + ChatColor.GRAY + name;
        if (line.length() > 16) {
          line = line.substring(0, 16);
        }
        this._scoreboardObj.getScore(line).setScore(i);
        
        this._scoreboardLast.add(line);
        
        i--;
      }
      for (String name : this._players)
      {
        Player player = UtilPlayer.searchExact(name);
        if (player != null) {
          if (!player.getScoreboard().equals(this._scoreboard)) {
            player.setScoreboard(this._scoreboard);
          }
        }
      }
    }
  }
  
  public boolean IsDead()
  {
    if (this._players.size() == 0) {
      return true;
    }
    if ((this._players.size() == 1) && (this._invitee.size() == 0)) {
      return true;
    }
    int online = 0;
    for (String name : this._players)
    {
      Player player = UtilPlayer.searchExact(name);
      if (player != null) {
        online++;
      }
    }
    if (online <= 1)
    {
      if (this._partyOfflineTimer == -1L) {
        this._partyOfflineTimer = System.currentTimeMillis();
      } else if (UtilTime.elapsed(this._partyOfflineTimer, online == 0 ? 5000 : 120000)) {
        return true;
      }
    }
    else if (this._partyOfflineTimer > 0L) {
      this._partyOfflineTimer = -1L;
    }
    return false;
  }
  
  public void resetWaitingTime()
  {
    this._partyOfflineTimer = -1L;
  }
  
  public void switchedServer()
  {
    this._informNewLeaderTimer = (System.currentTimeMillis() + 5000L);
  }
}
