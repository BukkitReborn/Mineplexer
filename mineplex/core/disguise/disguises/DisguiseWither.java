package mineplex.core.disguise.disguises;

import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DisguiseWither
  extends DisguiseMonster
{
  public DisguiseWither(Entity entity)
  {
    super(EntityType.WITHER, entity);
    
    this.DataWatcher.a(17, new Integer(0));
    this.DataWatcher.a(18, new Integer(0));
    this.DataWatcher.a(19, new Integer(0));
    this.DataWatcher.a(20, new Integer(0));
  }
  
  public int ca()
  {
    return this.DataWatcher.getInt(20);
  }
  
  public void s(int i)
  {
    this.DataWatcher.watch(20, Integer.valueOf(i));
  }
  
  public int t(int i)
  {
    return this.DataWatcher.getInt(17 + i);
  }
  
  public void b(int i, int j)
  {
    this.DataWatcher.watch(17 + i, Integer.valueOf(j));
  }
}
