package mineplex.core;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.packethandler.IPacketHandler;
import mineplex.core.packethandler.PacketHandler;
import mineplex.core.packethandler.PacketInfo;
import mineplex.core.packethandler.PacketVerifier;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Replay
  extends MiniPlugin
  implements IPacketHandler
{
  private NautHashMap<PacketInfo, Long> _packetList = new NautHashMap();
  private long _startTime = 0L;
  private long _replayTime = 0L;
  private boolean _replay = false;
  private long _speed = 20L;
  
  public Replay(JavaPlugin plugin, PacketHandler packetHandler)
  {
    super("Replay", plugin);
  }
  
  @EventHandler
  public void interact(PlayerInteractEvent event)
  {
    if (event.getItem().getType() == Material.WEB)
    {
      event.getPlayer().setItemInHand(new ItemStack(Material.STICK, 1));
      this._replay = true;
      this._replayTime = System.currentTimeMillis();
    }
  }
  
  @EventHandler
  public void replay(UpdateEvent event)
  {
    if ((event.getType() != UpdateType.TICK) || (!this._replay)) {
      return;
    }
    Iterator<Map.Entry<PacketInfo, Long>> entryIterator = this._packetList.entrySet().iterator();
    while (entryIterator.hasNext())
    {
      Map.Entry<PacketInfo, Long> entry = (Map.Entry)entryIterator.next();
      if (System.currentTimeMillis() + this._speed - this._replayTime > ((Long)entry.getValue()).longValue())
      {
        ((PacketInfo)entry.getKey()).getVerifier().bypassProcess(((PacketInfo)entry.getKey()).getPacket());
        entryIterator.remove();
      }
    }
  }
  
  public void handle(PacketInfo packetInfo)
  {
    if (this._replay)
    {
      packetInfo.setCancelled(true);
      return;
    }
    if (this._startTime == 0L) {
      this._startTime = System.currentTimeMillis();
    }
    this._packetList.put(packetInfo, Long.valueOf(System.currentTimeMillis() - this._startTime));
    if (packetInfo.isCancelled()) {}
  }
}
