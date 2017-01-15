package mineplex.core;

import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TablistFix
  extends MiniPlugin
{
  public TablistFix(JavaPlugin plugin)
  {
    super("Tablist Fix", plugin);
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onJoin(PlayerJoinEvent event)
  {
    final Player player = event.getPlayer();
    runSyncLater(new Runnable()
    {
      public void run()
      {
        if (!player.isOnline()) {
          return;
        }
        PacketPlayOutPlayerInfo packet = PacketPlayOutPlayerInfo.updateDisplayName(((CraftPlayer)player).getHandle());
        if (UtilPlayer.is1_8(player)) {
          UtilPlayer.sendPacket(player, new Packet[] { packet });
        }
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
        for (int i = 0; i < j; i++)
        {
          Player other = arrayOfPlayer[i];
          if ((!other.equals(player)) && (other.canSee(player)) && (UtilPlayer.is1_8(other))) {
            UtilPlayer.sendPacket(other, new Packet[] { packet });
          }
        }
      }
    }, 20L);
  }
}
