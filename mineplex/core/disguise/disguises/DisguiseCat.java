package mineplex.core.disguise.disguises;

import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguiseCat
  extends DisguiseTameableAnimal
{
  public DisguiseCat(Entity entity)
  {
    super(EntityType.OCELOT, entity);
    
    this.DataWatcher.a(18, Byte.valueOf((byte)0));
  }
  
  public int getCatType()
  {
    return this.DataWatcher.getByte(18);
  }
  
  public void setCatType(int i)
  {
    this.DataWatcher.watch(18, Byte.valueOf((byte)i));
  }
  
  protected String getHurtSound()
  {
    return "mob.cat.hitt";
  }
}
