package mineplex.core.disguise.disguises;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.server.v1_7_R4.DataWatcher;
import net.minecraft.server.v1_7_R4.EntityLiving;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityEquipment;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public abstract class DisguiseLiving
  extends DisguiseBase
{
  private static Random _random = new Random();
  private boolean _invisible;
  private ItemStack[] _equipment = new ItemStack[5];
  
  public DisguiseLiving(org.bukkit.entity.Entity entity)
  {
    super(entity);
    
    this.DataWatcher.a(6, Float.valueOf(1.0F));
    this.DataWatcher.a(7, Integer.valueOf(0));
    this.DataWatcher.a(8, Byte.valueOf((byte)0));
    this.DataWatcher.a(9, Byte.valueOf((byte)0));
  }
  
  public ItemStack[] getEquipment()
  {
    return this._equipment;
  }
  
  public void setEquipment(ItemStack[] equipment)
  {
    this._equipment = equipment;
  }
  
  public void setHelmet(ItemStack item)
  {
    this._equipment[3] = item;
  }
  
  public void setChestplate(ItemStack item)
  {
    this._equipment[2] = item;
  }
  
  public void setLeggings(ItemStack item)
  {
    this._equipment[1] = item;
  }
  
  public void setBoots(ItemStack item)
  {
    this._equipment[0] = item;
  }
  
  public void setHeldItem(ItemStack item)
  {
    this._equipment[4] = item;
  }
  
  public ArrayList<Packet> getEquipmentPackets()
  {
    ArrayList<Packet> packets = new ArrayList();
    for (int nmsSlot = 0; nmsSlot < 5; nmsSlot++)
    {
      int armorSlot = nmsSlot - 1;
      if (armorSlot < 0) {
        armorSlot = 4;
      }
      ItemStack itemstack = this._equipment[armorSlot];
      if ((itemstack != null) && (itemstack.getType() != Material.AIR))
      {
        ItemStack item = null;
        if ((this.Entity instanceof EntityLiving)) {
          item = CraftItemStack.asBukkitCopy(((EntityLiving)this.Entity).getEquipment()[nmsSlot]);
        }
        if ((item == null) || (item.getType() == Material.AIR))
        {
          PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment();
          
          packet.a = GetEntityId();
          packet.b = nmsSlot;
          packet.c = CraftItemStack.asNMSCopy(itemstack);
          
          packets.add(packet);
        }
      }
    }
    return packets;
  }
  
  public void UpdateDataWatcher()
  {
    super.UpdateDataWatcher();
    byte b0 = this.DataWatcher.getByte(0);
    if (this._invisible) {
      this.DataWatcher.watch(0, Byte.valueOf((byte)(b0 | 0x20)));
    } else {
      this.DataWatcher.watch(0, Byte.valueOf((byte)(b0 & 0xFFFFFFDF)));
    }
    if ((this.Entity instanceof EntityLiving))
    {
      this.DataWatcher.watch(6, Float.valueOf(this.Entity.getDataWatcher().getFloat(6)));
      this.DataWatcher.watch(7, Integer.valueOf(this.Entity.getDataWatcher().getInt(7)));
      this.DataWatcher.watch(8, Byte.valueOf(this.Entity.getDataWatcher().getByte(8)));
      this.DataWatcher.watch(9, Byte.valueOf(this.Entity.getDataWatcher().getByte(9)));
    }
  }
  
  public boolean isInvisible()
  {
    return this._invisible;
  }
  
  public void setInvisible(boolean invisible)
  {
    this._invisible = invisible;
  }
  
  protected String getHurtSound()
  {
    return "damage.hit";
  }
  
  protected float getVolume()
  {
    return 1.0F;
  }
  
  protected float getPitch()
  {
    return (_random.nextFloat() - _random.nextFloat()) * 0.2F + 1.0F;
  }
  
  public void setHealth(float health)
  {
    this.DataWatcher.watch(6, Float.valueOf(health));
  }
  
  public float getHealth()
  {
    return this.DataWatcher.getFloat(6);
  }
}
