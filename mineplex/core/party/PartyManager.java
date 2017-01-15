package mineplex.core.party;

import java.util.Collection;
import java.util.Iterator;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.party.commands.PartyCommand;
import mineplex.core.party.redis.RedisPartyData;
import mineplex.core.party.redis.RedisPartyHandler;
import mineplex.core.portal.Portal;
import mineplex.core.portal.ServerTransferEvent;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import mineplex.serverdata.commands.ServerCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PartyManager
  extends MiniPlugin
{
  private CoreClientManager _clientManager;
  private PreferencesManager _preferenceManager;
  private Portal _portal;
  private String _serverName;
  public NautHashMap<String, Party> _partyLeaderMap = new NautHashMap();
  
  public PartyManager(JavaPlugin plugin, Portal portal, CoreClientManager clientManager, PreferencesManager preferenceManager)
  {
    super("Party Manager", plugin);
    
    this._portal = portal;
    this._clientManager = clientManager;
    this._preferenceManager = preferenceManager;
    this._serverName = getPlugin().getConfig().getString("serverstatus.name");
    
    ServerCommandManager.getInstance().registerCommandType("RedisPartyData", RedisPartyData.class, 
      new RedisPartyHandler(this));
  }
  
  public void addCommands()
  {
    addCommand(new PartyCommand(this));
  }
  
  public CoreClientManager GetClients()
  {
    return this._clientManager;
  }
  
  public PreferencesManager getPreferenceManager()
  {
    return this._preferenceManager;
  }
  
  public Party CreateParty(Player player)
  {
    Party party = new Party(this);
    party.JoinParty(player);
    this._partyLeaderMap.put(player.getName(), party);
    
    return party;
  }
  
  public String getServerName()
  {
    return this._serverName;
  }
  
  public void addParty(Party party)
  {
    if (this._partyLeaderMap.containsKey(party.GetLeader())) {
      ((Party)this._partyLeaderMap.get(party.GetLeader())).resetWaitingTime();
    } else {
      this._partyLeaderMap.put(party.GetLeader(), party);
    }
  }
  
  @EventHandler
  public void serverTransfer(ServerTransferEvent event)
  {
    Party party = (Party)this._partyLeaderMap.get(event.getPlayer().getName());
    if (party != null)
    {
      party.switchedServer();
      
      RedisPartyData data = new RedisPartyData(party, this._serverName);
      data.setTargetServers(new String[] { event.getServer() });
      data.publish();
      for (Player player : party.GetPlayersOnline()) {
        if (player != event.getPlayer()) {
          this._portal.sendPlayerToServer(player, event.getServer(), false);
        }
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void PlayerJoin(PlayerJoinEvent event)
  {
    try
    {
      for (Party party : this._partyLeaderMap.values()) {
        party.PlayerJoin(event.getPlayer());
      }
    }
    catch (Exception ex)
    {
      throw ex;
    }
  }
  
  @EventHandler
  public void PlayerQuit(PlayerQuitEvent event)
  {
    for (Party party : this._partyLeaderMap.values()) {
      party.PlayerQuit(event.getPlayer());
    }
  }
  
  @EventHandler
  public void Update(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    ExpireParties();
    for (Party party : this._partyLeaderMap.values())
    {
      party.ExpireInvitees();
      party.UpdateScoreboard();
    }
  }
  
  public void ExpireParties()
  {
    Iterator<Party> partyIterator = this._partyLeaderMap.values().iterator();
    while (partyIterator.hasNext())
    {
      Party party = (Party)partyIterator.next();
      if (party.IsDead())
      {
        party.Announce("Your Party has been closed.");
        partyIterator.remove();
      }
    }
  }
  
  public Party GetParty(Player player)
  {
    for (Party party : this._partyLeaderMap.values()) {
      if (party.GetPlayers().contains(player.getName())) {
        return party;
      }
    }
    return null;
  }
}
