package mineplex.core.hologram;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class HologramManager
  implements Listener
{
  private ArrayList<Hologram> _activeHolograms = new ArrayList();
  
  public HologramManager(JavaPlugin arcadeManager)
  {
    Bukkit.getPluginManager().registerEvents(this, arcadeManager);
  }
  
  void addHologram(Hologram hologram)
  {
    this._activeHolograms.add(hologram);
  }
  
  void removeHologram(Hologram hologram)
  {
    this._activeHolograms.remove(hologram);
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onTick(UpdateEvent event)
  {
    if ((event.getType() != UpdateType.TICK) || (this._activeHolograms.isEmpty())) {
      return;
    }
    List<World> worlds = Bukkit.getWorlds();
    Iterator<Hologram> itel = this._activeHolograms.iterator();
    while (itel.hasNext())
    {
      Hologram hologram = (Hologram)itel.next();
      if (!worlds.contains(hologram.getLocation().getWorld()))
      {
        itel.remove();
        hologram.stop();
      }
      else
      {
        if (hologram.getEntityFollowing() != null)
        {
          Entity following = hologram.getEntityFollowing();
          if ((hologram.isRemoveOnEntityDeath()) && (!following.isValid()))
          {
            itel.remove();
            hologram.stop();
            continue;
          }
          if (!hologram.relativeToEntity.equals(following.getLocation().subtract(hologram.getLocation()).toVector()))
          {
            Vector vec = hologram.relativeToEntity.clone();
            hologram.setLocation(following.getLocation().add(hologram.relativeToEntity));
            hologram.relativeToEntity = vec;
            continue;
          }
        }
        ArrayList<Player> canSee = hologram.getNearbyPlayers();
        Iterator<Player> itel2 = hologram.getPlayersTracking().iterator();
        while (itel2.hasNext())
        {
          Player player = (Player)itel2.next();
          if (!canSee.contains(player))
          {
            itel2.remove();
            if (player.getWorld() == hologram.getLocation().getWorld()) {
              ((CraftPlayer)player).getHandle().playerConnection.sendPacket(hologram.getDestroyPacket(player));
            }
          }
        }
        for (Player player : canSee) {
          if (!hologram.getPlayersTracking().contains(player))
          {
            hologram.getPlayersTracking().add(player);
            Packet[] arrayOfPacket;
            int j = (arrayOfPacket = hologram.getSpawnPackets(player)).length;
            for (int i = 0; i < j; i++)
            {
              Packet packet = arrayOfPacket[i];
              
              ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
            }
          }
        }
      }
    }
  }
}
