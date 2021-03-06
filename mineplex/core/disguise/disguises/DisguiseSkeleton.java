package mineplex.core.disguise.disguises;

import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Skeleton.SkeletonType;

public class DisguiseSkeleton
  extends DisguiseMonster
{
  public DisguiseSkeleton(Entity entity)
  {
    super(EntityType.SKELETON, entity);
    
    this.DataWatcher.a(13, Byte.valueOf((byte)0));
  }
  
  public void SetSkeletonType(Skeleton.SkeletonType skeletonType)
  {
    this.DataWatcher.watch(13, Byte.valueOf((byte)skeletonType.getId()));
  }
  
  public int GetSkeletonType()
  {
    return this.DataWatcher.getByte(13);
  }
  
  protected String getHurtSound()
  {
    return "mob.skeleton.hurt";
  }
}
