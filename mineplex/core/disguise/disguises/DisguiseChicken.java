package mineplex.core.disguise.disguises;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguiseChicken
  extends DisguiseAnimal
{
  public DisguiseChicken(Entity entity)
  {
    super(EntityType.CHICKEN, entity);
  }
  
  public String getHurtSound()
  {
    return "mob.chicken.hurt";
  }
}
