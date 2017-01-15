package mineplex.core.disguise.disguises;

import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.EnumEntitySize;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutSpawnEntityLiving;
import org.bukkit.util.Vector;

public class DisguiseArmorStand
  extends DisguiseInsentient
{
  private Vector _headPosition = new Vector();
  
  public DisguiseArmorStand(org.bukkit.entity.Entity entity)
  {
    super(entity);
    
    this.DataWatcher.a(10, Byte.valueOf((byte)0));
    for (int i = 11; i < 17; i++) {
      this.DataWatcher.a(i, new Vector(0, 0, 0));
    }
  }
  
  public Vector getHeadPosition()
  {
    return this._headPosition.clone();
  }
  
  protected String getHurtSound()
  {
    return null;
  }
  
  public Packet GetSpawnPacket()
  {
    PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving();
    packet.a = this.Entity.getId();
    packet.b = 30;
    packet.c = EnumEntitySize.SIZE_2.a(this.Entity.locX);
    packet.d = MathHelper.floor(this.Entity.locY * 32.0D);
    packet.e = EnumEntitySize.SIZE_2.a(this.Entity.locZ);
    packet.i = ((byte)(int)(this.Entity.yaw * 256.0F / 360.0F));
    packet.j = ((byte)(int)(this.Entity.pitch * 256.0F / 360.0F));
    packet.k = ((byte)(int)(this.Entity.yaw * 256.0F / 360.0F));
    
    double var2 = 3.9D;
    double var4 = 0.0D;
    double var6 = 0.0D;
    double var8 = 0.0D;
    if (var4 < -var2) {
      var4 = -var2;
    }
    if (var6 < -var2) {
      var6 = -var2;
    }
    if (var8 < -var2) {
      var8 = -var2;
    }
    if (var4 > var2) {
      var4 = var2;
    }
    if (var6 > var2) {
      var6 = var2;
    }
    if (var8 > var2) {
      var8 = var2;
    }
    packet.f = ((int)(var4 * 8000.0D));
    packet.g = ((int)(var6 * 8000.0D));
    packet.h = ((int)(var8 * 8000.0D));
    
    packet.l = this.DataWatcher;
    packet.m = this.DataWatcher.b();
    
    return packet;
  }
  
  public void setBodyPosition(Vector vector)
  {
    this.DataWatcher.watch(12, vector);
  }
  
  public void setHasArms()
  {
    this.DataWatcher.watch(10, Integer.valueOf(this.DataWatcher.getByte(10) | 0x4));
  }
  
  public void setHeadPosition(Vector vector)
  {
    this._headPosition = vector;
    this.DataWatcher.watch(11, vector);
  }
  
  public void setLeftArmPosition(Vector vector)
  {
    this.DataWatcher.watch(13, vector);
  }
  
  public void setLeftLegPosition(Vector vector)
  {
    this.DataWatcher.watch(15, vector);
  }
  
  public void setRemoveBase()
  {
    this.DataWatcher.watch(10, Integer.valueOf(this.DataWatcher.getByte(10) | 0x8));
  }
  
  public void setRightArmPosition(Vector vector)
  {
    this.DataWatcher.watch(14, vector);
  }
  
  public void setRightLegPosition(Vector vector)
  {
    this.DataWatcher.watch(16, vector);
  }
  
  public void setSmall()
  {
    this.DataWatcher.watch(10, Integer.valueOf(this.DataWatcher.getByte(10) | 0x1));
  }
  
  public void setGravityEffected()
  {
    this.DataWatcher.watch(10, Integer.valueOf(this.DataWatcher.getByte(10) | 0x2));
  }
}
