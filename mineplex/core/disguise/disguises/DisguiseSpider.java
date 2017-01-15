package mineplex.core.disguise.disguises;

import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguiseSpider
  extends DisguiseMonster
{
  public DisguiseSpider(Entity entity)
  {
    super(EntityType.SPIDER, entity);
    
    this.DataWatcher.a(16, new Byte((byte)0));
  }
  
  public boolean bT()
  {
    return (this.DataWatcher.getByte(16) & 0x1) != 0;
  }
  
  public void a(boolean flag)
  {
    byte b0 = this.DataWatcher.getByte(16);
    if (flag) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    }
    this.DataWatcher.watch(16, Byte.valueOf(b0));
  }
  
  protected String getHurtSound()
  {
    return "mob.spider.say";
  }
}
