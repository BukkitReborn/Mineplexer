package mineplex.core.disguise.disguises;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public abstract class DisguiseMonster
  extends DisguiseCreature
{
  public DisguiseMonster(EntityType disguiseType, Entity entity)
  {
    super(disguiseType, entity);
  }
}
