package mineplex.core.friend.ui;

import java.util.UUID;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.util.com.mojang.authlib.GameProfile;

public class LineTracker
{
  private String _line = null;
  private String _oldLine = null;
  private PacketPlayOutPlayerInfo _clearOldPacket;
  private PacketPlayOutPlayerInfo _addNewPacket;
  private PacketPlayOutPlayerInfo _clearNewPacket;
  
  public LineTracker(String line)
  {
    setLine(line);
  }
  
  public boolean setLine(String s)
  {
    if ((s != null) && (s.length() > 16)) {
      s = s.substring(0, 16);
    }
    if ((this._line != null) && (this._line.compareTo(s) == 0)) {
      return false;
    }
    this._oldLine = this._line;
    this._line = s;
    if (this._oldLine != null)
    {
      this._clearOldPacket = new PacketPlayOutPlayerInfo();
      this._clearOldPacket.username = this._oldLine;
      this._clearOldPacket.action = 4;
      this._clearOldPacket.ping = 0;
      this._clearOldPacket.player = new GameProfile(UUID.randomUUID(), this._oldLine);
    }
    if (this._line != null)
    {
      this._addNewPacket = new PacketPlayOutPlayerInfo();
      this._addNewPacket.username = this._line;
      this._addNewPacket.action = 0;
      this._addNewPacket.ping = 0;
      this._addNewPacket.player = new GameProfile(UUID.randomUUID(), this._line);
      
      this._clearNewPacket = new PacketPlayOutPlayerInfo();
      this._clearNewPacket.username = this._line;
      this._clearNewPacket.action = 4;
      this._clearNewPacket.ping = 0;
      this._clearNewPacket.player = new GameProfile(UUID.randomUUID(), this._line);
    }
    return true;
  }
  
  public void displayLineToPlayer(EntityPlayer entityPlayer)
  {
    if (this._oldLine != null) {
      entityPlayer.playerConnection.sendPacket(this._clearOldPacket);
    }
    if (this._line != null) {
      entityPlayer.playerConnection.sendPacket(this._addNewPacket);
    }
  }
  
  public void removeLineForPlayer(EntityPlayer entityPlayer)
  {
    if (this._line != null) {
      entityPlayer.playerConnection.sendPacket(this._clearNewPacket);
    }
  }
  
  public void clearOldLine()
  {
    this._oldLine = null;
  }
}
