package mineplex.core.packethandler;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IPacketVerifier;
import net.minecraft.server.v1_7_R4.NetworkManager;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketVerifier
  implements IPacketVerifier
{
  private static Field _destroyId;
  private Player _owner;
  private List<IPacketHandler> _packetHandlers = new ArrayList();
  
  public PacketVerifier(Player owner)
  {
    this._owner = owner;
    if (_destroyId == null) {
      try
      {
        _destroyId = PacketPlayOutEntityDestroy.class.getDeclaredField("a");
        _destroyId.setAccessible(true);
      }
      catch (Exception exception)
      {
        System.out.println("Field exception in CustomTagFix : ");
        exception.printStackTrace();
      }
    }
  }
  
  public boolean verify(Packet o)
  {
    PacketInfo packetInfo = new PacketInfo(this._owner, o, this);
    for (IPacketHandler handler : this._packetHandlers) {
      handler.handle(packetInfo);
    }
    return !packetInfo.isCancelled();
  }
  
  public void bypassProcess(Packet packet)
  {
    ((CraftPlayer)this._owner).getHandle().playerConnection.networkManager.handle(packet, new GenericFutureListener[0]);
  }
  
  public void Deactivate()
  {
    this._owner = null;
  }
  
  public void process(Packet packet)
  {
    ((CraftPlayer)this._owner).getHandle().playerConnection.sendPacket(packet);
  }
  
  public void clearHandlers()
  {
    this._packetHandlers.clear();
  }
  
  public void addPacketHandler(IPacketHandler packetHandler)
  {
    this._packetHandlers.add(packetHandler);
  }
  
  public void removePacketHandler(IPacketHandler packetHandler)
  {
    this._packetHandlers.remove(packetHandler);
  }
}
