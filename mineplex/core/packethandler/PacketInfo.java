package mineplex.core.packethandler;

import net.minecraft.server.v1_7_R4.Packet;
import org.bukkit.entity.Player;

public class PacketInfo
{
  private Player _player;
  private Packet _packet;
  private PacketVerifier _verifier;
  private boolean _cancelled = false;
  
  public PacketInfo(Player player, Packet packet, PacketVerifier verifier)
  {
    this._player = player;
    this._packet = packet;
    this._verifier = verifier;
  }
  
  public Packet getPacket()
  {
    return this._packet;
  }
  
  public Player getPlayer()
  {
    return this._player;
  }
  
  public PacketVerifier getVerifier()
  {
    return this._verifier;
  }
  
  public void setCancelled(boolean cancel)
  {
    this._cancelled = cancel;
  }
  
  public boolean isCancelled()
  {
    return this._cancelled;
  }
}
