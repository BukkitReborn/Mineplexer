package mineplex.core.disguise.disguises;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguisePig
  extends DisguiseAnimal
{
  public DisguisePig(Entity entity)
  {
    super(EntityType.PIG, entity);
  }
  
  public String getHurtSound()
  {
    return "mob.pig.say";
  }
}
