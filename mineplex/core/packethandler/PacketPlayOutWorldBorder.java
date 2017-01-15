package mineplex.core.packethandler;

import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketDataSerializer;
import net.minecraft.server.v1_7_R4.PacketListener;

public class PacketPlayOutWorldBorder
  extends Packet
{
  public int worldBorderType;
  public double centerX;
  public double centerZ;
  public double newRadius;
  public double oldRadius;
  public long speed;
  public int warningBlocks;
  public int warningTime;
  
  public void a(PacketDataSerializer paramPacketDataSerializer) {}
  
  public void b(PacketDataSerializer paramPacketDataSerializer)
  {
    paramPacketDataSerializer.b(this.worldBorderType);
    switch (this.worldBorderType)
    {
    case 0: 
      paramPacketDataSerializer.writeDouble(this.newRadius * 2.0D);
      break;
    case 1: 
      paramPacketDataSerializer.writeDouble(this.oldRadius * 2.0D);
      paramPacketDataSerializer.writeDouble(this.newRadius * 2.0D);
      paramPacketDataSerializer.b((int)this.speed);
      break;
    case 2: 
      paramPacketDataSerializer.writeDouble(this.centerX);
      paramPacketDataSerializer.writeDouble(this.centerZ);
      break;
    case 3: 
      paramPacketDataSerializer.writeDouble(this.centerX);
      paramPacketDataSerializer.writeDouble(this.centerZ);
      paramPacketDataSerializer.writeDouble(this.oldRadius * 2.0D);
      paramPacketDataSerializer.writeDouble(this.newRadius * 2.0D);
      paramPacketDataSerializer.b((int)this.speed);
      
      paramPacketDataSerializer.b(29999984);
      paramPacketDataSerializer.b(this.warningTime);
      paramPacketDataSerializer.b(this.warningBlocks);
      break;
    case 4: 
      paramPacketDataSerializer.b(this.warningTime);
      break;
    case 5: 
      paramPacketDataSerializer.b(this.warningBlocks);
    }
  }
  
  public void handle(PacketListener arg0) {}
}
