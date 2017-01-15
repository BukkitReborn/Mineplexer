package mineplex.core.disguise.disguises;

import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.spigotmc.ProtocolData.IntByte;

public abstract class DisguiseAgeable
  extends DisguiseCreature
{
  public DisguiseAgeable(EntityType disguiseType, Entity entity)
  {
    super(disguiseType, entity);
    
    this.DataWatcher.a(12, new ProtocolData.IntByte(0, (byte)0));
  }
  
  public void UpdateDataWatcher()
  {
    super.UpdateDataWatcher();
    
    this.DataWatcher.watch(12, this.DataWatcher.getIntByte(12));
  }
  
  public boolean isBaby()
  {
    return this.DataWatcher.getIntByte(12).value < 0;
  }
  
  public void setBaby()
  {
    this.DataWatcher.watch(12, new ProtocolData.IntByte(41536, (byte)-1));
  }
}
