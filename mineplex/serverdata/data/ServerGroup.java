package mineplex.serverdata.data;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import mineplex.serverdata.Region;

public class ServerGroup
{
  private HashMap<String, String> _dataMap = null;
  private String _name;
  private String _host;
  private String _prefix;
  private int _minPlayers;
  private int _maxPlayers;
  private int _requiredRam;
  private int _requiredCpu;
  private int _requiredTotalServers;
  private int _requiredJoinableServers;
  private boolean _arcadeGroup;
  private String _worldZip;
  private String _plugin;
  private String _configPath;
  private int _portSection;
  private boolean _pvp;
  private boolean _tournament;
  private boolean _tournamentPoints;
  private boolean _teamRejoin;
  private boolean _teamAutoJoin;
  private boolean _teamForceBalance;
  private boolean _gameAutoStart;
  private boolean _gameTimeout;
  private boolean _rewardGems;
  private boolean _rewardItems;
  private boolean _rewardStats;
  private boolean _rewardAchievements;
  private boolean _hotbarInventory;
  private boolean _hotbarHubClock;
  private boolean _playerKickIdle;
  private boolean _generateFreeVersions;
  private String _games;
  private String _serverType;
  private boolean _addNoCheat;
  private boolean _addWorldEdit;
  private boolean _whitelist;
  private boolean _staffOnly;
  private String _resourcePack = "";
  private Region _region;
  private Set<MinecraftServer> _servers;
  
  public ServerGroup(Map<String, String> data, Collection<MinecraftServer> serverStatuses)
  {
    this._name = ((String)data.get("name"));
    this._prefix = ((String)data.get("prefix"));
    this._requiredRam = Integer.valueOf((String)data.get("ram")).intValue();
    this._requiredCpu = Integer.valueOf((String)data.get("cpu")).intValue();
    this._requiredTotalServers = Integer.valueOf((String)data.get("totalServers")).intValue();
    this._requiredJoinableServers = Integer.valueOf((String)data.get("joinableServers")).intValue();
    this._portSection = Integer.valueOf((String)data.get("portSection")).intValue();
    this._arcadeGroup = Boolean.valueOf((String)data.get("arcadeGroup")).booleanValue();
    this._worldZip = ((String)data.get("worldZip"));
    this._plugin = ((String)data.get("plugin"));
    this._configPath = ((String)data.get("configPath"));
    this._minPlayers = Integer.valueOf((String)data.get("minPlayers")).intValue();
    this._maxPlayers = Integer.valueOf((String)data.get("maxPlayers")).intValue();
    this._pvp = Boolean.valueOf((String)data.get("pvp")).booleanValue();
    this._tournament = Boolean.valueOf((String)data.get("tournament")).booleanValue();
    this._tournamentPoints = Boolean.valueOf((String)data.get("tournamentPoints")).booleanValue();
    this._generateFreeVersions = Boolean.valueOf((String)data.get("generateFreeVersions")).booleanValue();
    this._games = ((String)data.get("games"));
    this._serverType = ((String)data.get("serverType"));
    this._addNoCheat = Boolean.valueOf((String)data.get("addNoCheat")).booleanValue();
    this._addWorldEdit = Boolean.valueOf((String)data.get("addWorldEdit")).booleanValue();
    this._teamRejoin = Boolean.valueOf((String)data.get("teamRejoin")).booleanValue();
    this._teamAutoJoin = Boolean.valueOf((String)data.get("teamAutoJoin")).booleanValue();
    this._teamForceBalance = Boolean.valueOf((String)data.get("teamForceBalance")).booleanValue();
    this._gameAutoStart = Boolean.valueOf((String)data.get("gameAutoStart")).booleanValue();
    this._gameTimeout = Boolean.valueOf((String)data.get("gameTimeout")).booleanValue();
    this._rewardGems = Boolean.valueOf((String)data.get("rewardGems")).booleanValue();
    this._rewardItems = Boolean.valueOf((String)data.get("rewardItems")).booleanValue();
    this._rewardStats = Boolean.valueOf((String)data.get("rewardStats")).booleanValue();
    this._rewardAchievements = Boolean.valueOf((String)data.get("rewardAchievements")).booleanValue();
    this._hotbarInventory = Boolean.valueOf((String)data.get("hotbarInventory")).booleanValue();
    this._hotbarHubClock = Boolean.valueOf((String)data.get("hotbarHubClock")).booleanValue();
    this._playerKickIdle = Boolean.valueOf((String)data.get("playerKickIdle")).booleanValue();
    this._staffOnly = Boolean.valueOf((String)data.get("staffOnly")).booleanValue();
    this._whitelist = Boolean.valueOf((String)data.get("whitelist")).booleanValue();
    this._resourcePack = (data.containsKey("resourcePack") ? (String)data.get("resourcePack") : "");
    this._host = ((String)data.get("host"));
    this._region = (data.containsKey("region") ? Region.valueOf((String)data.get("region")) : Region.ALL);
    if (serverStatuses != null) {
      parseServers(serverStatuses);
    }
  }
  
  public ServerGroup(String name, String prefix, String host, int ram, int cpu, int totalServers, int joinable, int portSection, boolean arcade, String worldZip, String plugin, String configPath, int minPlayers, int maxPlayers, boolean pvp, boolean tournament, boolean tournamentPoints, String games, String serverType, boolean noCheat, boolean worldEdit, boolean teamRejoin, boolean teamAutoJoin, boolean teamForceBalance, boolean gameAutoStart, boolean gameTimeout, boolean rewardGems, boolean rewardItems, boolean rewardStats, boolean rewardAchievements, boolean hotbarInventory, boolean hotbarHubClock, boolean playerKickIdle, boolean staffOnly, boolean whitelist, String resourcePack, Region region)
  {
    this._name = name;
    this._prefix = prefix;
    this._host = host;
    this._requiredRam = ram;
    this._requiredCpu = cpu;
    this._requiredTotalServers = totalServers;
    this._requiredJoinableServers = joinable;
    this._portSection = portSection;
    this._arcadeGroup = arcade;
    this._worldZip = worldZip;
    this._plugin = plugin;
    this._configPath = configPath;
    this._minPlayers = minPlayers;
    this._maxPlayers = maxPlayers;
    this._pvp = pvp;
    this._tournament = tournament;
    this._tournamentPoints = tournamentPoints;
    this._games = games;
    this._serverType = serverType;
    this._addNoCheat = noCheat;
    this._addWorldEdit = worldEdit;
    this._teamRejoin = teamRejoin;
    this._teamAutoJoin = teamAutoJoin;
    this._teamForceBalance = teamForceBalance;
    this._gameAutoStart = gameAutoStart;
    this._gameTimeout = gameTimeout;
    this._rewardGems = rewardGems;
    this._rewardItems = rewardItems;
    this._rewardStats = rewardStats;
    this._rewardAchievements = rewardAchievements;
    this._hotbarInventory = hotbarInventory;
    this._hotbarHubClock = hotbarHubClock;
    this._playerKickIdle = playerKickIdle;
    this._staffOnly = staffOnly;
    this._whitelist = whitelist;
    this._resourcePack = resourcePack;
    this._region = region;
  }
  
  public String getName()
  {
    return this._name;
  }
  
  public String getHost()
  {
    return this._host;
  }
  
  public String getPrefix()
  {
    return this._prefix;
  }
  
  public int getMinPlayers()
  {
    return this._minPlayers;
  }
  
  public int getMaxPlayers()
  {
    return this._maxPlayers;
  }
  
  public int getRequiredRam()
  {
    return this._requiredRam;
  }
  
  public int getRequiredCpu()
  {
    return this._requiredCpu;
  }
  
  public int getRequiredTotalServers()
  {
    return this._requiredTotalServers;
  }
  
  public int getRequiredJoinableServers()
  {
    return this._requiredJoinableServers;
  }
  
  public int getPortSection()
  {
    return this._portSection;
  }
  
  public boolean getArcadeGroup()
  {
    return this._arcadeGroup;
  }
  
  public String getWorldZip()
  {
    return this._worldZip;
  }
  
  public String getPlugin()
  {
    return this._plugin;
  }
  
  public String getConfigPath()
  {
    return this._configPath;
  }
  
  public boolean getPvp()
  {
    return this._pvp;
  }
  
  public boolean getTournament()
  {
    return this._tournament;
  }
  
  public boolean getTournamentPoints()
  {
    return this._tournamentPoints;
  }
  
  public boolean getTeamRejoin()
  {
    return this._teamRejoin;
  }
  
  public boolean getTeamAutoJoin()
  {
    return this._teamAutoJoin;
  }
  
  public boolean getTeamForceBalance()
  {
    return this._teamForceBalance;
  }
  
  public boolean getGameAutoStart()
  {
    return this._gameAutoStart;
  }
  
  public boolean getGameTimeout()
  {
    return this._gameTimeout;
  }
  
  public boolean getRewardGems()
  {
    return this._rewardGems;
  }
  
  public boolean getRewardItems()
  {
    return this._rewardItems;
  }
  
  public boolean getRewardStats()
  {
    return this._rewardStats;
  }
  
  public boolean getRewardAchievements()
  {
    return this._rewardAchievements;
  }
  
  public boolean getHotbarInventory()
  {
    return this._hotbarInventory;
  }
  
  public boolean getHotbarHubClock()
  {
    return this._hotbarHubClock;
  }
  
  public boolean getPlayerKickIdle()
  {
    return this._playerKickIdle;
  }
  
  public boolean getGenerateFreeVersions()
  {
    return this._generateFreeVersions;
  }
  
  public String getGames()
  {
    return this._games;
  }
  
  public String getServerType()
  {
    return this._serverType;
  }
  
  public boolean getAddNoCheat()
  {
    return this._addNoCheat;
  }
  
  public boolean getAddWorldEdit()
  {
    return this._addWorldEdit;
  }
  
  public boolean getWhitelist()
  {
    return this._whitelist;
  }
  
  public boolean getStaffOnly()
  {
    return this._staffOnly;
  }
  
  public String getResourcePack()
  {
    return this._resourcePack;
  }
  
  public Region getRegion()
  {
    return this._region;
  }
  
  public Set<MinecraftServer> getServers()
  {
    return this._servers;
  }
  
  public int getServerCount()
  {
    return this._servers.size();
  }
  
  public int getJoinableCount()
  {
    int joinable = 0;
    for (MinecraftServer server : this._servers) {
      if (server.isJoinable()) {
        joinable++;
      }
    }
    return joinable;
  }
  
  public int getPlayerCount()
  {
    int playerCount = 0;
    for (MinecraftServer server : this._servers) {
      playerCount += server.getPlayerCount();
    }
    return playerCount;
  }
  
  public int getMaxPlayerCount()
  {
    int maxPlayerCount = 0;
    for (MinecraftServer server : this._servers) {
      maxPlayerCount += server.getMaxPlayerCount();
    }
    return maxPlayerCount;
  }
  
  public Collection<MinecraftServer> getEmptyServers()
  {
    Collection<MinecraftServer> emptyServers = new HashSet();
    for (MinecraftServer server : this._servers) {
      if ((server.isEmpty()) && (server.getUptime() >= 150.0D)) {
        emptyServers.add(server);
      }
    }
    return emptyServers;
  }
  
  private void parseServers(Collection<MinecraftServer> servers)
  {
    this._servers = new HashSet();
    for (MinecraftServer server : servers) {
      if (this._name.equalsIgnoreCase(server.getGroup())) {
        this._servers.add(server);
      }
    }
  }
  
  public int generateUniqueId(int startId)
  {
    int id = startId;
    for (;;)
    {
      boolean uniqueId = true;
      for (MinecraftServer server : this._servers)
      {
        String serverName = server.getName();
        try
        {
          int serverNum = Integer.parseInt(serverName.split("-")[1]);
          if (serverNum == id) {
            uniqueId = false;
          }
        }
        catch (Exception exception)
        {
          exception.printStackTrace();
        }
      }
      if (uniqueId) {
        return id;
      }
      id++;
    }
  }
  
  public HashMap<String, String> getDataMap()
  {
    if (this._dataMap == null)
    {
      this._dataMap = new HashMap();
      
      this._dataMap.put("name", this._name);
      this._dataMap.put("prefix", this._prefix);
      this._dataMap.put("ram", this._requiredRam);
      this._dataMap.put("cpu", this._requiredCpu);
      this._dataMap.put("totalServers", this._requiredTotalServers);
      this._dataMap.put("joinableServers", this._requiredJoinableServers);
      this._dataMap.put("portSection", this._portSection);
      this._dataMap.put("arcadeGroup", this._arcadeGroup);
      this._dataMap.put("worldZip", this._worldZip);
      this._dataMap.put("plugin", this._plugin);
      this._dataMap.put("configPath", this._configPath);
      this._dataMap.put("minPlayers", this._minPlayers);
      this._dataMap.put("maxPlayers", this._maxPlayers);
      this._dataMap.put("pvp", this._pvp);
      this._dataMap.put("tournament", this._tournament);
      this._dataMap.put("tournamentPoints", this._tournamentPoints);
      this._dataMap.put("games", this._games);
      this._dataMap.put("serverType", this._serverType);
      this._dataMap.put("addNoCheat", this._addNoCheat);
      this._dataMap.put("teamRejoin", this._teamRejoin);
      this._dataMap.put("teamAutoJoin", this._teamAutoJoin);
      this._dataMap.put("teamForceBalance", this._teamForceBalance);
      this._dataMap.put("gameAutoStart", this._gameAutoStart);
      this._dataMap.put("gameTimeout", this._gameTimeout);
      this._dataMap.put("rewardGems", this._rewardGems);
      this._dataMap.put("rewardItems", this._rewardItems);
      this._dataMap.put("rewardStats", this._rewardStats);
      this._dataMap.put("rewardAchievements", this._rewardAchievements);
      this._dataMap.put("hotbarInventory", this._hotbarInventory);
      this._dataMap.put("hotbarHubClock", this._hotbarHubClock);
      this._dataMap.put("playerKickIdle", this._playerKickIdle);
      this._dataMap.put("staffOnly", this._staffOnly);
      this._dataMap.put("whitelist", this._whitelist);
      this._dataMap.put("resourcePack", this._resourcePack);
      this._dataMap.put("host", this._host);
      this._dataMap.put("region", this._region.name());
    }
    return this._dataMap;
  }
}
