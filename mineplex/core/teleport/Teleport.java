package mineplex.core.teleport;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import mineplex.core.MiniPlugin;
import mineplex.core.account.event.ClientUnloadEvent;
import mineplex.core.common.jsonchat.ChildJsonMessage;
import mineplex.core.common.jsonchat.ClickEvent;
import mineplex.core.common.jsonchat.HoverEvent;
import mineplex.core.common.jsonchat.JsonMessage;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilWorld;
import mineplex.core.teleport.command.LocateCommand;
import mineplex.core.teleport.command.TeleportCommand;
import mineplex.core.teleport.event.MineplexTeleportEvent;
import mineplex.core.teleport.redis.RedisLocate;
import mineplex.core.teleport.redis.RedisLocateCallback;
import mineplex.core.teleport.redis.RedisLocateHandler;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import mineplex.serverdata.commands.ServerCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class Teleport
  extends MiniPlugin
{
  private LinkedList<Teleporter> teleportList = new LinkedList();
  private NautHashMap<String, LinkedList<Location>> _tpHistory = new NautHashMap();
  private NautHashMap<UUID, BukkitRunnable> _failedRedisLocates = new NautHashMap();
  private String _serverName;
  
  public Teleport(JavaPlugin plugin)
  {
    super("Teleport", plugin);
    
    this._serverName = getPlugin().getConfig().getString("serverstatus.name");
    
    RedisLocateHandler locateHandler = new RedisLocateHandler(this);
    
    ServerCommandManager.getInstance().registerCommandType("RedisLocate", RedisLocate.class, locateHandler);
    ServerCommandManager.getInstance().registerCommandType("RedisLocateCallback", RedisLocateCallback.class, locateHandler);
  }
  
  public void addCommands()
  {
    addCommand(new TeleportCommand(this));
    addCommand(new LocateCommand(this));
  }
  
  public void handleLocateCallback(RedisLocateCallback callback)
  {
    BukkitRunnable runnable = (BukkitRunnable)this._failedRedisLocates.remove(callback.getUUID());
    if (runnable != null) {
      runnable.cancel();
    }
    Player player = Bukkit.getPlayerExact(callback.getReceivingPlayer());
    if (player != null)
    {
      ChildJsonMessage message = new JsonMessage("").extra(C.mHead + "Locate" + "> " + C.mBody + "Located [" + C.mElem + 
        callback.getLocatedPlayer() + C.mBody + "] at ");
      
      message.add(C.cBlue + callback.getServer()).click(ClickEvent.RUN_COMMAND, 
        "/server " + callback.getServer());
      
      message.hover(HoverEvent.SHOW_TEXT, "Teleport to " + callback.getServer());
      
      message.sendToPlayer(player);
    }
  }
  
  public void locatePlayer(final Player player, final String target)
  {
    Player targetPlayer = Bukkit.getPlayerExact(target);
    if (targetPlayer != null)
    {
      UtilPlayer.message(player, F.main("Locate", C.mBody + " [" + C.mElem + target + C.mBody + "] is in the same server!"));
      return;
    }
    RedisLocate locate = new RedisLocate(this._serverName, player.getName(), target);
    final UUID uuid = locate.getUUID();
    
    BukkitRunnable runnable = new BukkitRunnable()
    {
      public void run()
      {
        Teleport.this._failedRedisLocates.remove(uuid);
        UtilPlayer.message(player, F.main("Locate", C.mBody + "Failed to locate [" + C.mElem + target + C.mBody + "]."));
      }
    };
    this._failedRedisLocates.put(uuid, runnable);
    runnable.runTaskLater(this._plugin, 40L);
    
    locate.publish();
  }
  
  @EventHandler
  public void UnloadHistory(ClientUnloadEvent event)
  {
    this._tpHistory.remove(event.GetName());
  }
  
  @EventHandler
  public void update(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    if (this.teleportList.isEmpty()) {
      return;
    }
    ((Teleporter)this.teleportList.removeFirst()).doTeleport();
  }
  
  public void playerToPlayer(Player caller, String from, String to)
  {
    LinkedList<Player> listA = new LinkedList();
    if (from.equals("%ALL%"))
    {
      Player[] arrayOfPlayer;
      int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
      for (int i = 0; i < j; i++)
      {
        Player cur = arrayOfPlayer[i];
        listA.add(cur);
      }
    }
    else
    {
      listA = UtilPlayer.matchOnline(caller, from, true);
    }
    Player pB = UtilPlayer.searchOnline(caller, to, true);
    if ((listA.isEmpty()) || (pB == null)) {
      return;
    }
    if (listA.size() == 1)
    {
      Player pA = (Player)listA.getFirst();
      
      String mA = null;
      mB = null;
      if (pA.equals(caller))
      {
        mA = F.main("Teleport", "You teleported to " + F.elem(pB.getName()) + ".");
      }
      else if (pB.equals(caller))
      {
        mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to themself.");
        mB = F.main("Teleport", "You teleported " + F.elem(pA.getName()) + " to yourself.");
      }
      else
      {
        mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to " + F.elem(pB.getName()) + ".");
        mB = F.main("Teleport", "You teleported " + F.elem(pA.getName()) + " to " + F.elem(pB.getName()) + ".");
      }
      Add(pA, pB.getLocation(), mA, true, caller, (String)mB, 
        pA.getName() + " teleported to " + pB.getName() + " via " + caller.getName());
      return;
    }
    boolean first = true;
    for (Object mB = listA.iterator(); ((Iterator)mB).hasNext();)
    {
      Player pA = (Player)((Iterator)mB).next();
      
      String mA = null;
      String mB = null;
      if (pA.equals(caller))
      {
        mA = F.main("Teleport", "You teleported to " + F.elem(pB.getName()) + ".");
      }
      else if (pB.equals(caller))
      {
        mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to themself.");
        mB = F.main("Teleport", "You teleported " + F.elem(new StringBuilder(String.valueOf(listA.size())).append(" Players").toString()) + " to yourself.");
      }
      else
      {
        mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to " + F.elem(pB.getName()) + ".");
        mB = F.main("Teleport", "You teleported " + F.elem(new StringBuilder(String.valueOf(listA.size())).append(" Players").toString()) + " to " + F.elem(pB.getName()) + ".");
      }
      if (first) {
        Add(pA, pB.getLocation(), mA, true, caller, mB, pA.getName() + " teleported to " + pB.getName() + " via " + caller.getName());
      } else {
        Add(pA, pB.getLocation(), mA, true, caller, null, pA.getName() + " teleported to " + pB.getName() + " via " + caller.getName());
      }
      first = false;
    }
  }
  
  public void playerToLoc(Player caller, String target, String sX, String sY, String sZ)
  {
    playerToLoc(caller, target, caller.getWorld().getName(), sX, sY, sZ);
  }
  
  public void playerToLoc(Player caller, String target, String world, String sX, String sY, String sZ)
  {
    Player player = UtilPlayer.searchOnline(caller, target, true);
    if (player == null) {
      return;
    }
    try
    {
      int x = sX.matches(".*[0-9]") ? Integer.parseInt(sX.replace("~", "")) : 0;
      int y = sY.matches(".*[0-9]") ? Integer.parseInt(sY.replace("~", "")) : 0;
      int z = sZ.matches(".*[0-9]") ? Integer.parseInt(sZ.replace("~", "")) : 0;
      
      Location pLoc = player.getLocation();
      if (sX.startsWith("~")) {
        x += pLoc.getBlockX();
      }
      if (sY.startsWith("~")) {
        y += pLoc.getBlockY();
      }
      if (sZ.startsWith("~")) {
        z += pLoc.getBlockZ();
      }
      Location loc = new Location(Bukkit.getWorld(world), x, y, z);
      
      String mA = null;
      if (caller == player) {
        mA = F.main("Teleport", "You teleported to " + UtilWorld.locToStrClean(loc) + ".");
      } else {
        mA = F.main("Teleport", F.elem(caller.getName()) + " teleported you to " + UtilWorld.locToStrClean(loc) + ".");
      }
      Add(player, loc, mA, true, caller, null, player.getName() + " teleported to " + UtilWorld.locToStrClean(loc) + " via " + caller.getName());
    }
    catch (Exception e)
    {
      UtilPlayer.message(caller, F.main("Teleport", "Invalid Location [" + sX + "," + sY + "," + sZ + "]."));
      return;
    }
  }
  
  public void Add(Player pA, Location loc, String mA, boolean record, Player pB, String mB, String log)
  {
    this.teleportList.addLast(new Teleporter(this, pA, pB, mA, mB, loc, record, log));
  }
  
  public void TP(Player player, Location getLocation)
  {
    TP(player, getLocation, true);
  }
  
  public void TP(Player player, Location loc, boolean dettach)
  {
    MineplexTeleportEvent event = new MineplexTeleportEvent(player, loc);
    UtilServer.getServer().getPluginManager().callEvent(event);
    if (event.isCancelled()) {
      return;
    }
    if (dettach)
    {
      player.eject();
      player.leaveVehicle();
    }
    player.setFallDistance(0.0F);
    player.setVelocity(new Vector(0, 0, 0));
    
    player.teleport(loc);
  }
  
  public LinkedList<Location> GetTPHistory(Player player)
  {
    return (LinkedList)this._tpHistory.get(player.getName());
  }
}
