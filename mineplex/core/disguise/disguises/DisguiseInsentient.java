package mineplex.core.disguise.disguises;

import mineplex.core.common.Rank;
import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;

public abstract class DisguiseInsentient
  extends DisguiseLiving
{
  private boolean _showArmor;
  
  public DisguiseInsentient(Entity entity)
  {
    super(entity);
    
    this.DataWatcher.a(3, Byte.valueOf((byte)0));
    this.DataWatcher.a(2, "");
    if (!(this instanceof DisguiseArmorStand))
    {
      this.DataWatcher.a(11, Byte.valueOf((byte)0));
      this.DataWatcher.a(10, "");
    }
  }
  
  public void setName(String name)
  {
    setName(name, null);
  }
  
  public void setName(String name, Rank rank)
  {
    if (rank != null) {
      if (rank.Has(Rank.ULTRA)) {
        name = rank.GetTag(true, true) + " " + ChatColor.RESET + name;
      }
    }
    this.DataWatcher.watch(10, name);
    this.DataWatcher.watch(2, name);
  }
  
  public boolean hasCustomName()
  {
    return this.DataWatcher.getString(10).length() > 0;
  }
  
  public void setCustomNameVisible(boolean visible)
  {
    this.DataWatcher.watch(11, Byte.valueOf((byte)(visible ? 1 : 0)));
    this.DataWatcher.watch(3, Byte.valueOf((byte)(visible ? 1 : 0)));
  }
  
  public boolean getCustomNameVisible()
  {
    return this.DataWatcher.getByte(11) == 1;
  }
  
  public boolean armorVisible()
  {
    return this._showArmor;
  }
  
  public void showArmor()
  {
    this._showArmor = true;
  }
  
  public void hideArmor()
  {
    this._showArmor = false;
  }
}
