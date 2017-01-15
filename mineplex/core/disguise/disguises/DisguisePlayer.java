package mineplex.core.disguise.disguises;

import java.util.UUID;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_7_R4.PacketPlayOutPlayerInfo;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DisguisePlayer
  extends DisguiseHuman
{
  private GameProfile _profile;
  private boolean _sneaking;
  private BlockFace _sleeping;
  
  public DisguisePlayer(org.bukkit.entity.Entity entity)
  {
    super(entity);
  }
  
  public DisguisePlayer(org.bukkit.entity.Entity entity, GameProfile profile)
  {
    this(entity);
    
    setProfile(profile);
  }
  
  public void setProfile(GameProfile profile)
  {
    GameProfile newProfile = new GameProfile(UUID.randomUUID(), profile.getName());
    
    newProfile.getProperties().putAll(profile.getProperties());
    
    this._profile = newProfile;
  }
  
  public BlockFace getSleepingDirection()
  {
    return this._sleeping;
  }
  
  public void setSleeping(BlockFace sleeping)
  {
    this._sleeping = sleeping;
  }
  
  public void setSneaking(boolean sneaking)
  {
    this._sneaking = sneaking;
  }
  
  public Packet getOldInfoPacket(boolean add)
  {
    PacketPlayOutPlayerInfo playerInfo = new PacketPlayOutPlayerInfo();
    if ((this.Entity instanceof Player))
    {
      playerInfo.username = this.Entity.getName();
      playerInfo.action = (add ? 0 : 4);
      playerInfo.ping = 90;
      playerInfo.player = ((CraftPlayer)this.Entity).getProfile();
      playerInfo.gamemode = 0;
    }
    return playerInfo;
  }
  
  public Packet getNewInfoPacket(boolean add)
  {
    PacketPlayOutPlayerInfo newDisguiseInfo = new PacketPlayOutPlayerInfo();
    newDisguiseInfo.username = this._profile.getName();
    newDisguiseInfo.action = (add ? 0 : 4);
    newDisguiseInfo.ping = 90;
    newDisguiseInfo.player = this._profile;
    newDisguiseInfo.gamemode = 0;
    
    return newDisguiseInfo;
  }
  
  public void UpdateDataWatcher()
  {
    super.UpdateDataWatcher();
    
    byte b0 = this.DataWatcher.getByte(0);
    if (this._sneaking) {
      this.DataWatcher.watch(0, Byte.valueOf((byte)(b0 | 0x2)));
    } else {
      this.DataWatcher.watch(0, Byte.valueOf((byte)(b0 & 0xFFFFFFFD)));
    }
  }
  
  public PacketPlayOutNamedEntitySpawn spawnBeforePlayer(Location spawnLocation)
  {
    Location loc = spawnLocation.add(spawnLocation.getDirection().normalize().multiply(30));
    loc.setY(Math.max(loc.getY(), 0.0D));
    
    PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
    packet.a = this.Entity.getId();
    packet.b = this._profile;
    packet.c = MathHelper.floor(loc.getX() * 32.0D);
    packet.d = MathHelper.floor(loc.getY() * 32.0D);
    packet.e = MathHelper.floor(loc.getZ() * 32.0D);
    packet.f = ((byte)(int)(loc.getYaw() * 256.0F / 360.0F));
    packet.g = ((byte)(int)(loc.getPitch() * 256.0F / 360.0F));
    packet.i = this.DataWatcher;
    
    return packet;
  }
  
  public PacketPlayOutNamedEntitySpawn GetSpawnPacket()
  {
    PacketPlayOutNamedEntitySpawn packet = new PacketPlayOutNamedEntitySpawn();
    packet.a = this.Entity.getId();
    packet.b = this._profile;
    packet.c = MathHelper.floor(this.Entity.locX * 32.0D);
    packet.d = MathHelper.floor(this.Entity.locY * 32.0D);
    packet.e = MathHelper.floor(this.Entity.locZ * 32.0D);
    packet.f = ((byte)(int)(this.Entity.yaw * 256.0F / 360.0F));
    packet.g = ((byte)(int)(this.Entity.pitch * 256.0F / 360.0F));
    packet.i = this.DataWatcher;
    
    return packet;
  }
  
  public String getName()
  {
    return this._profile.getName();
  }
}
