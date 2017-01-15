package mineplex.core.disguise.disguises;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public abstract class DisguiseAnimal
  extends DisguiseAgeable
{
  public DisguiseAnimal(EntityType disguiseType, Entity entity)
  {
    super(disguiseType, entity);
  }
}
