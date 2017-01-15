package mineplex.core.disguise.disguises;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguisePigZombie
  extends DisguiseZombie
{
  public DisguisePigZombie(Entity entity)
  {
    super(EntityType.PIG_ZOMBIE, entity);
  }
  
  protected String getHurtSound()
  {
    return "mob.zombiepig.zpighurt";
  }
}
