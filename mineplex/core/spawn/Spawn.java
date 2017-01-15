package mineplex.core.spawn;

import java.util.ArrayList;
import java.util.List;
import mineplex.core.MiniPlugin;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilWorld;
import mineplex.core.spawn.command.SpawnCommand;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Spawn
  extends MiniPlugin
{
  private SpawnRepository _repository;
  private List<Location> _spawns = new ArrayList();
  
  public Spawn(JavaPlugin plugin, String serverName)
  {
    super("Spawn", plugin);
    
    this._repository = new SpawnRepository(plugin, serverName);
    for (String spawn : this._repository.retrieveSpawns()) {
      this._spawns.add(UtilWorld.strToLoc(spawn));
    }
  }
  
  public void addCommands()
  {
    addCommand(new SpawnCommand(this));
  }
  
  public Location getSpawn()
  {
    if (this._spawns.isEmpty()) {
      return UtilServer.getServer().getWorld("world").getSpawnLocation();
    }
    return (Location)this._spawns.get(UtilMath.r(this._spawns.size()));
  }
  
  public void AddSpawn(Player player)
  {
    final Location loc = player.getLocation();
    
    player.getWorld().setSpawnLocation((int)loc.getX(), (int)loc.getY(), (int)loc.getZ());
    
    this._spawns.add(loc);
    
    runAsync(new Runnable()
    {
      public void run()
      {
        Spawn.this._repository.addSpawn(UtilWorld.locToStr(loc));
      }
    });
    UtilPlayer.message(player, F.main(this._moduleName, "You added a Spawn Node."));
    
    log("Added Spawn [" + UtilWorld.locToStr(loc) + "] by [" + player.getName() + "].");
  }
  
  public void ClearSpawn(Player player)
  {
    this._spawns.clear();
    
    runAsync(new Runnable()
    {
      public void run()
      {
        Spawn.this._repository.clearSpawns();
      }
    });
    UtilPlayer.message(player, F.main(this._moduleName, "You cleared all Spawn Nodes."));
    
    log("Cleared Spawn [ALL] by [" + player.getName() + "].");
  }
  
  @EventHandler
  public void handleRespawn(PlayerRespawnEvent event)
  {
    event.setRespawnLocation(getSpawn());
  }
}
