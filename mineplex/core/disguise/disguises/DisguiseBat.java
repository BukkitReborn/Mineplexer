package mineplex.core.disguise.disguises;

import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguiseBat
  extends DisguiseAnimal
{
  public DisguiseBat(Entity entity)
  {
    super(EntityType.BAT, entity);
    
    this.DataWatcher.a(16, new Byte((byte)0));
  }
  
  public boolean isSitting()
  {
    return (this.DataWatcher.getByte(16) & 0x1) != 0;
  }
  
  public void setSitting(boolean paramBoolean)
  {
    int i = this.DataWatcher.getByte(16);
    if (paramBoolean) {
      this.DataWatcher.watch(16, Byte.valueOf((byte)(i | 0x1)));
    } else {
      this.DataWatcher.watch(16, Byte.valueOf((byte)(i & 0xFFFFFFFE)));
    }
  }
  
  public String getHurtSound()
  {
    return "mob.bat.hurt";
  }
}
