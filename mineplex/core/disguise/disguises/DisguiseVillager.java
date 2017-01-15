package mineplex.core.disguise.disguises;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguiseVillager
  extends DisguiseAgeable
{
  public DisguiseVillager(Entity entity)
  {
    super(EntityType.VILLAGER, entity);
  }
  
  protected String getHurtSound()
  {
    return "mob.villager.hit";
  }
}
