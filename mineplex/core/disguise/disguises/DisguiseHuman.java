package mineplex.core.disguise.disguises;

import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.entity.Entity;
import org.spigotmc.ProtocolData.DualByte;
import org.spigotmc.ProtocolData.HiddenByte;

public abstract class DisguiseHuman
  extends DisguiseLiving
{
  public DisguiseHuman(Entity entity)
  {
    super(entity);
    
    this.DataWatcher.a(10, new ProtocolData.HiddenByte((byte)0));
    this.DataWatcher.a(16, new ProtocolData.DualByte((byte)0, (byte)0));
    this.DataWatcher.a(17, Float.valueOf(0.0F));
    this.DataWatcher.a(18, Integer.valueOf(0));
  }
}
