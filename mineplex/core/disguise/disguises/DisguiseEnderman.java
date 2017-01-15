package mineplex.core.disguise.disguises;

import java.util.Arrays;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.MobEffect;
import net.minecraft.server.v1_7_R4.MobEffectList;
import net.minecraft.server.v1_7_R4.PotionBrewer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.spigotmc.ProtocolData.ByteShort;

public class DisguiseEnderman
  extends DisguiseMonster
{
  public DisguiseEnderman(Entity entity)
  {
    super(EntityType.ENDERMAN, entity);
    
    this.DataWatcher.a(16, new ProtocolData.ByteShort((short)0));
    this.DataWatcher.a(17, new Byte((byte)0));
    this.DataWatcher.a(18, new Byte((byte)0));
    
    int i = PotionBrewer.a(Arrays.asList(new MobEffect[] { new MobEffect(MobEffectList.FIRE_RESISTANCE.id, 777) }));
    this.DataWatcher.watch(8, Byte.valueOf((byte)(PotionBrewer.b(Arrays.asList(tmp99_96)) ? 1 : 0)));
    this.DataWatcher.watch(7, Integer.valueOf(i));
  }
  
  public void UpdateDataWatcher()
  {
    super.UpdateDataWatcher();
    
    this.DataWatcher.watch(0, Byte.valueOf((byte)(this.DataWatcher.getByte(0) & 0xFFFFFFFE)));
    this.DataWatcher.watch(16, new ProtocolData.ByteShort(this.DataWatcher.getShort(16)));
  }
  
  public void SetCarriedId(int i)
  {
    this.DataWatcher.watch(16, new ProtocolData.ByteShort((short)(i & 0xFF)));
  }
  
  public int GetCarriedId()
  {
    return this.DataWatcher.getByte(16);
  }
  
  public void SetCarriedData(int i)
  {
    this.DataWatcher.watch(17, Byte.valueOf((byte)(i & 0xFF)));
  }
  
  public int GetCarriedData()
  {
    return this.DataWatcher.getByte(17);
  }
  
  public boolean bX()
  {
    return this.DataWatcher.getByte(18) > 0;
  }
  
  public void a(boolean flag)
  {
    this.DataWatcher.watch(18, Byte.valueOf((byte)(flag ? 1 : 0)));
  }
  
  protected String getHurtSound()
  {
    return "mob.endermen.hit";
  }
}
