package mineplex.core.disguise.disguises;

import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguiseIronGolem
  extends DisguiseGolem
{
  public DisguiseIronGolem(Entity entity)
  {
    super(EntityType.IRON_GOLEM, entity);
    
    this.DataWatcher.a(16, Byte.valueOf((byte)0));
  }
  
  public boolean bW()
  {
    return (this.DataWatcher.getByte(16) & 0x1) != 0;
  }
  
  public void setPlayerCreated(boolean flag)
  {
    byte b0 = this.DataWatcher.getByte(16);
    if (flag) {
      this.DataWatcher.watch(16, Byte.valueOf((byte)(b0 | 0x1)));
    } else {
      this.DataWatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
    }
  }
  
  protected String getHurtSound()
  {
    return "mob.irongolem.hit";
  }
}
