package mineplex.core.packethandler;

import net.minecraft.server.v1_7_R4.PacketListener;
import net.minecraft.server.v1_7_R4.PacketPlayInListener;
import net.minecraft.server.v1_7_R4.PacketPlayInUseEntity;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.entity.Player;

public class PacketPlayUseEntity
  extends PacketPlayInUseEntity
{
  private static PacketHandler _packetHandler;
  
  public void handle(PacketListener packetlistener)
  {
    Player player = ((PlayerConnection)packetlistener).getPlayer();
    
    PacketVerifier verifier = _packetHandler.getPacketVerifier(player);
    
    PacketInfo packetInfo = new PacketInfo(player, this, verifier);
    for (IPacketHandler handler : _packetHandler.getPacketHandlers()) {
      handler.handle(packetInfo);
    }
    if (!packetInfo.isCancelled()) {
      a((PacketPlayInListener)packetlistener);
    }
  }
}
