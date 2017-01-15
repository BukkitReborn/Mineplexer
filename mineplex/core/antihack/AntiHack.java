package mineplex.core.antihack;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.antihack.types.Fly;
import mineplex.core.antihack.types.Idle;
import mineplex.core.antihack.types.Reach;
import mineplex.core.antihack.types.Speed;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.portal.Portal;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.preferences.UserPreferences;
import mineplex.core.punish.Punish;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiHack
  extends MiniPlugin
{
  public static AntiHack Instance;
  private boolean _enabled = true;
  private boolean _strict = false;
  private boolean _kick = true;
  public Punish Punish;
  public Portal Portal;
  private PreferencesManager _preferences;
  private CoreClientManager _clientManager;
  private HashMap<Player, HashMap<String, ArrayList<Long>>> _offense = new HashMap();
  private HashMap<Player, Long> _ignore = new HashMap();
  private HashSet<Player> _velocityEvent = new HashSet();
  private HashMap<Player, Long> _lastMoveEvent = new HashMap();
  private HashSet<Player> _hubAttempted = new HashSet();
  public int FloatHackTicks = 10;
  public int HoverHackTicks = 4;
  public int RiseHackTicks = 6;
  public int SpeedHackTicks = 6;
  public int IdleTime = 20000;
  public int KeepOffensesFor = 30000;
  public int FlightTriggerCancel = 2000;
  public ArrayList<Detector> _movementDetectors;
  public ArrayList<Detector> _combatDetectors;
  private AntiHackRepository _repository;
  public static String _mineplexName;
  
  protected AntiHack(JavaPlugin plugin, Punish punish, Portal portal, PreferencesManager preferences, CoreClientManager clientManager, String mineplexName)
  {
    super("AntiHack", plugin);
    this.Punish = punish;
    this.Portal = portal;
    this._preferences = preferences;
    this._clientManager = clientManager;
    this._repository = new AntiHackRepository(plugin.getConfig().getString("serverstatus.name"));
    this._repository.initialize();
    this._movementDetectors = new ArrayList();
    this._combatDetectors = new ArrayList();
    this._movementDetectors.add(new Fly(this));
    this._movementDetectors.add(new Idle(this));
    this._movementDetectors.add(new Speed(this));
    this._combatDetectors.add(new Reach(this));
    
    _mineplexName = mineplexName;
  }
  
  public static void Initialize(JavaPlugin plugin, Punish punish, Portal portal, PreferencesManager preferences, CoreClientManager clientManager, String mineplexName)
  {
    Instance = new AntiHack(plugin, punish, portal, preferences, clientManager, mineplexName);
  }
  
  @EventHandler
  public void playerMove(PlayerMoveEvent event)
  {
    if (!this._enabled) {
      return;
    }
    this._lastMoveEvent.put(event.getPlayer(), Long.valueOf(System.currentTimeMillis()));
  }
  
  @EventHandler
  public void playerTeleport(PlayerTeleportEvent event)
  {
    if (!this._enabled) {
      return;
    }
    setIgnore(event.getPlayer(), 2000L);
  }
  
  @EventHandler
  public void playerVelocity(PlayerVelocityEvent event)
  {
    if (!this._enabled) {
      return;
    }
    this._velocityEvent.add(event.getPlayer());
  }
  
  @EventHandler
  public void playerQuit(PlayerQuitEvent event)
  {
    if (!this._enabled) {
      return;
    }
    resetAll(event.getPlayer());
  }
  
  @EventHandler
  public void startIgnore(PlayerMoveEvent event)
  {
    if (!this._enabled) {
      return;
    }
    Player player = event.getPlayer();
    if (this._velocityEvent.remove(player)) {
      setIgnore(player, 2000L);
    }
    long timeBetweenPackets;
    if ((this._lastMoveEvent.containsKey(player)) && ((timeBetweenPackets = System.currentTimeMillis() - ((Long)this._lastMoveEvent.get(player)).longValue()) > 500L)) {
      setIgnore(player, Math.min(4000L, timeBetweenPackets));
    }
  }
  
  public void setIgnore(Player player, long time)
  {
    for (Detector detector : this._movementDetectors) {
      detector.Reset(player);
    }
    if ((this._ignore.containsKey(player)) && (((Long)this._ignore.get(player)).longValue() > System.currentTimeMillis() + time)) {
      return;
    }
    this._ignore.put(player, Long.valueOf(System.currentTimeMillis() + time));
  }
  
  public boolean isValid(Player player, boolean groundValid)
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player other = arrayOfPlayer[i];
      if ((!player.equals(other)) && (other.getGameMode() == GameMode.SURVIVAL) && (!UtilPlayer.isSpectator(player)) && (other.getVehicle() == null) && (UtilMath.offset(player, other) < 2.0D)) {
        return true;
      }
    }
    if ((player.isFlying()) || (player.isInsideVehicle()) || (player.getGameMode() != GameMode.SURVIVAL) || (UtilPlayer.isSpectator(player))) {
      return true;
    }
    if ((groundValid) && ((UtilEnt.onBlock(player)) || (player.getLocation().getBlock().getType() != Material.AIR))) {
      return true;
    }
    if ((this._ignore.containsKey(player)) && (System.currentTimeMillis() < ((Long)this._ignore.get(player)).longValue())) {
      return true;
    }
    return false;
  }
  
  public void addSuspicion(Player player, String type)
  {
    if (!this._enabled) {
      return;
    }
    System.out.println(C.cRed + C.Bold + player.getName() + " suspected for " + type + ".");
    if (!this._offense.containsKey(player)) {
      this._offense.put(player, new HashMap());
    }
    if (!((HashMap)this._offense.get(player)).containsKey(type)) {
      ((HashMap)this._offense.get(player)).put(type, new ArrayList());
    }
    ((ArrayList)((HashMap)this._offense.get(player)).get(type)).add(Long.valueOf(System.currentTimeMillis()));
    int total = 0;
    for (String curType : ((HashMap)this._offense.get(player)).keySet())
    {
      offenseIterator = ((ArrayList)((HashMap)this._offense.get(player)).get(curType)).iterator();
      while (offenseIterator.hasNext()) {
        if (UtilTime.elapsed(((Long)offenseIterator.next()).longValue(), this.KeepOffensesFor)) {
          offenseIterator.remove();
        }
      }
      total += ((ArrayList)((HashMap)this._offense.get(player)).get(curType)).size();
    }
    Player[] arrayOfPlayer;
    Iterator<Long> offenseIterator = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (Iterator<Long> localIterator1 = 0; localIterator1 < offenseIterator; localIterator1++)
    {
      Player admin = arrayOfPlayer[localIterator1];
      if ((this._clientManager.Get(admin).GetRank().Has(Rank.MODERATOR)) && (((UserPreferences)this._preferences.Get(admin)).ShowMacReports)) {
        UtilPlayer.message(admin, "#" + total + ": " + C.cRed + C.Bold + player.getName() + " suspected for " + type + ".");
      }
    }
    System.out.println("[Offense] #" + total + ": " + player.getName() + " received suspicion for " + type + ".");
  }
  
  @EventHandler
  public void generateReports(UpdateEvent event)
  {
    if (!this._enabled) {
      return;
    }
    if (event.getType() != UpdateType.SEC) {
      return;
    }
    Iterator<Map.Entry<Player, HashMap<String, ArrayList<Long>>>> playerIterator = this._offense.entrySet().iterator();
    while (playerIterator.hasNext())
    {
      Map.Entry<Player, HashMap<String, ArrayList<Long>>> entry = (Map.Entry)playerIterator.next();
      Player player = (Player)entry.getKey();
      String out = "";
      int total = 0;
      for (String type : ((HashMap)entry.getValue()).keySet())
      {
        Iterator<Long> offenseIterator = ((ArrayList)((HashMap)entry.getValue()).get(type)).iterator();
        while (offenseIterator.hasNext())
        {
          long time = ((Long)offenseIterator.next()).longValue();
          if (UtilTime.elapsed(time, this.KeepOffensesFor)) {
            offenseIterator.remove();
          }
        }
        int count = ((ArrayList)((HashMap)entry.getValue()).get(type)).size();
        total += count;
        out = out + count + " " + type + ", ";
      }
      if (out.length() > 0) {
        out = out.substring(0, out.length() - 2);
      }
      String severity = "Low";
      if (total > (this._strict ? 6 : 18)) {
        severity = "Extreme";
      } else if (total > (this._strict ? 4 : 12)) {
        severity = "High";
      } else if (total > (this._strict ? 2 : 6)) {
        severity = "Medium";
      }
      sendReport(player, out, severity);
      if (severity.equalsIgnoreCase("Extreme"))
      {
        playerIterator.remove();
        resetAll(player, false);
      }
    }
  }
  
  public void sendReport(Player player, String report, String severity)
  {
    if (severity.equals("Extreme"))
    {
      boolean handled = false;
      Player[] arrayOfPlayer;
      int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
      for (int i = 0; i < j; i++)
      {
        Player staff = arrayOfPlayer[i];
        if (this._clientManager.Get(staff).GetRank().Has(Rank.MODERATOR))
        {
          UtilPlayer.message(staff, C.cAqua + C.Scramble + "A" + ChatColor.RESET + C.cRed + C.Bold + " MAC > " + ChatColor.RESET + C.cYellow + report);
          UtilPlayer.message(staff, C.cAqua + C.Scramble + "A" + ChatColor.RESET + C.cRed + C.Bold + " MAC > " + ChatColor.RESET + C.cGold + player.getName() + C.cYellow + " has extreme violation. Please investigate.");
          handled = true;
        }
      }
      if ((!handled) && (this._clientManager.Get(player).GetRank() != Rank.YOUTUBE) && (this._clientManager.Get(player).GetRank() != Rank.TWITCH))
      {
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 2.0F, 0.5F);
        if ((this._kick) || (this._hubAttempted.remove(player)))
        {
          player.kickPlayer(C.cGold + "Mineplex Anti-Cheat\n" + C.cWhite + "You were kicked for suspicious movement.\n" + C.cWhite + "Cheating may result in a " + C.cRed + "Permanent Ban" + C.cWhite + ".\n" + C.cWhite + "If you were not cheating, you will not be banned.");
        }
        else
        {
          this._hubAttempted.add(player);
          UtilPlayer.message(player, C.cGold + C.Strike + "---------------------------------------------");
          UtilPlayer.message(player, "");
          UtilPlayer.message(player, C.cGold + _mineplexName + " Anti-Cheat");
          UtilPlayer.message(player, "");
          UtilPlayer.message(player, "You were kicked from the game for suspicious movement.");
          UtilPlayer.message(player, "Cheating may result in a " + C.cRed + "Permanent Ban" + C.cWhite + ".");
          UtilPlayer.message(player, "If you were not cheating, you will not be banned.");
          UtilPlayer.message(player, "");
          UtilPlayer.message(player, C.cGold + C.Strike + "---------------------------------------------");
          this.Portal.sendPlayerToServer(player, "Lobby");
        }
        UtilServer.broadcast(F.main("MAC", player.getName() + " was kicked for suspicious movement."));
      }
      ServerListPingEvent event = new ServerListPingEvent(null, Bukkit.getServer().getMotd(), Bukkit.getServer().getOnlinePlayers().size(), Bukkit.getServer().getMaxPlayers());
      getPluginManager().callEvent(event);
      String motd = event.getMotd();
      String game = "N/A";
      String map = "N/A";
      String[] args = motd.split("\\|");
      if (args.length > 0) {
        motd = args[0];
      }
      if (args.length > 2) {
        game = args[2];
      }
      if (args.length > 3) {
        map = args[3];
      }
      this._repository.saveOffense(player, motd, game, map, report);
    }
  }
  
  private void reset()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      resetAll(player);
    }
  }
  
  private void resetAll(Player player)
  {
    resetAll(player, true);
  }
  
  private void resetAll(Player player, boolean removeOffenses)
  {
    this._ignore.remove(player);
    this._velocityEvent.remove(player);
    this._lastMoveEvent.remove(player);
    if (removeOffenses) {
      this._offense.remove(player);
    }
    for (Detector detector2 : this._movementDetectors) {
      detector2.Reset(player);
    }
    for (Detector detector2 : this._combatDetectors) {
      detector2.Reset(player);
    }
  }
  
  @EventHandler
  public void cleanupPlayers(UpdateEvent event)
  {
    if (!this._enabled) {
      return;
    }
    if (event.getType() != UpdateType.SLOW) {
      return;
    }
    label197:
    for (Iterator<Map.Entry<Player, Long>> playerIterator = this._ignore.entrySet().iterator(); playerIterator.hasNext(); ???.hasNext())
    {
      Player player = (Player)((Map.Entry)playerIterator.next()).getKey();
      if ((player.isOnline()) && (!player.isDead()) && (player.isValid())) {
        break label197;
      }
      playerIterator.remove();
      
      this._velocityEvent.remove(player);
      this._lastMoveEvent.remove(player);
      
      this._offense.remove(player);
      for (Detector detector : this._movementDetectors) {
        detector.Reset(player);
      }
      ??? = this._combatDetectors.iterator(); continue;Detector detector = (Detector)???.next();
      detector.Reset(player);
    }
    for (Iterator<Player> playerIterator = this._hubAttempted.iterator(); playerIterator.hasNext();)
    {
      Player player1 = (Player)playerIterator.next();
      if ((!player1.isOnline()) || (!player1.isValid())) {
        playerIterator.remove();
      }
    }
  }
  
  public void setEnabled(boolean b)
  {
    this._enabled = b;
    System.out.println("MAC Enabled: " + b);
  }
  
  public boolean isEnabled()
  {
    return this._enabled;
  }
  
  public void setStrict(boolean strict)
  {
    this._strict = strict;
    reset();
    System.out.println("MAC Strict: " + strict);
  }
  
  public boolean isStrict()
  {
    return this._strict;
  }
  
  public void setKick(boolean kick)
  {
    this._kick = kick;
    System.out.println("MAC Kick: " + kick);
  }
}
