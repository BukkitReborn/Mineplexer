package mineplex.core.disguise.disguises;

import mineplex.core.common.DummyEntity;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;

public abstract class DisguiseBase
{
  protected net.minecraft.server.v1_7_R4.Entity Entity;
  protected DataWatcher DataWatcher;
  private DisguiseBase _soundDisguise;
  public boolean Global = true;
  
  public DisguiseBase(org.bukkit.entity.Entity entity)
  {
    if (entity != null) {
      setEntity(entity);
    }
    this.DataWatcher = new DataWatcher(new DummyEntity(null));
    
    this.DataWatcher.a(0, Byte.valueOf((byte)0));
    this.DataWatcher.a(1, Short.valueOf((short)300));
    
    this._soundDisguise = this;
  }
  
  public void setEntity(org.bukkit.entity.Entity entity)
  {
    this.Entity = ((CraftEntity)entity).getHandle();
  }
  
  public void UpdateDataWatcher()
  {
    this.DataWatcher.watch(0, Byte.valueOf(this.Entity.getDataWatcher().getByte(0)));
    this.DataWatcher.watch(1, Short.valueOf(this.Entity.getDataWatcher().getShort(1)));
  }
  
  public abstract Packet GetSpawnPacket();
  
  public Packet GetMetaDataPacket()
  {
    UpdateDataWatcher();
    return new PacketPlayOutEntityMetadata(this.Entity.getId(), this.DataWatcher, true);
  }
  
  public void setSoundDisguise(DisguiseBase soundDisguise)
  {
    this._soundDisguise = soundDisguise;
    if (this._soundDisguise == null) {
      this._soundDisguise = this;
    }
  }
  
  public void playHurtSound()
  {
    this.Entity.world.makeSound(this.Entity, this._soundDisguise.getHurtSound(), this._soundDisguise.getVolume(), this._soundDisguise.getPitch());
  }
  
  public void playHurtSound(Location location)
  {
    this.Entity.world.makeSound(location.getX(), location.getY(), location.getZ(), this._soundDisguise.getHurtSound(), this._soundDisguise.getVolume(), this._soundDisguise.getPitch());
  }
  
  public net.minecraft.server.v1_7_R4.Entity GetEntity()
  {
    return this.Entity;
  }
  
  public int GetEntityId()
  {
    return this.Entity.getId();
  }
  
  protected abstract String getHurtSound();
  
  protected abstract float getVolume();
  
  protected abstract float getPitch();
}
