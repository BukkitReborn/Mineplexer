package mineplex.core.disguise.disguises;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguiseCow
  extends DisguiseAnimal
{
  public DisguiseCow(Entity entity)
  {
    super(EntityType.COW, entity);
  }
  
  public String getHurtSound()
  {
    return "mob.cow.hurt";
  }
}
