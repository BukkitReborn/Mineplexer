package mineplex.core.shop.item;

import java.util.ArrayList;
import java.util.List;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilInv;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopItem
  extends org.bukkit.inventory.ItemStack
{
  protected String _name;
  private String _deliveryName;
  protected String[] _lore;
  private int _deliveryAmount;
  private boolean _locked;
  private boolean _displayItem;
  
  public ShopItem(org.bukkit.inventory.ItemStack itemStack, String name, String deliveryName, int deliveryAmount, boolean locked, boolean displayItem)
  {
    super(itemStack);
    
    this._name = name;
    this._deliveryName = deliveryName;
    this._displayItem = displayItem;
    this._deliveryAmount = deliveryAmount;
    if (itemStack.getItemMeta().hasLore()) {
      this._lore = ((String[])itemStack.getItemMeta().getLore().toArray(new String[0]));
    } else {
      this._lore = new String[0];
    }
    UpdateVisual(true);
  }
  
  public net.minecraft.server.v1_7_R4.ItemStack getHandle()
  {
    return CraftItemStack.asNMSCopy(this);
  }
  
  public ShopItem(Material type, String name, int deliveryAmount, boolean locked)
  {
    this(type, name, null, deliveryAmount, locked);
  }
  
  public ShopItem(int type, String name, int deliveryAmount, boolean locked)
  {
    this(type, name, null, deliveryAmount, locked);
  }
  
  public ShopItem(Material type, String name, String[] lore, int deliveryAmount, boolean locked)
  {
    this(type, name, lore, deliveryAmount, locked, false);
  }
  
  public ShopItem(int type, String name, String[] lore, int deliveryAmount, boolean locked)
  {
    this(type, name, lore, deliveryAmount, locked, false);
  }
  
  public ShopItem(Material type, String name, String[] lore, int deliveryAmount, boolean locked, boolean displayItem)
  {
    this(type, (byte)0, name, null, lore, deliveryAmount, locked, displayItem);
  }
  
  public ShopItem(int type, String name, String[] lore, int deliveryAmount, boolean locked, boolean displayItem)
  {
    this(type, (byte)0, name, null, lore, deliveryAmount, locked, displayItem);
  }
  
  public ShopItem(Material type, byte data, String name, String[] lore, int deliveryAmount, boolean locked, boolean displayItem)
  {
    this(type, data, name, null, lore, deliveryAmount, locked, displayItem);
  }
  
  public ShopItem(int type, byte data, String name, String[] lore, int deliveryAmount, boolean locked, boolean displayItem)
  {
    this(type, data, name, null, lore, deliveryAmount, locked, displayItem);
  }
  
  public ShopItem(Material type, byte data, String name, String deliveryName, String[] lore, int deliveryAmount, boolean locked, boolean displayItem)
  {
    this(type.getId(), data, name, deliveryName, lore, deliveryAmount, locked, displayItem);
  }
  
  public ShopItem(int type, byte data, String name, String deliveryName, String[] lore, int deliveryAmount, boolean locked, boolean displayItem)
  {
    super(type, Math.max(deliveryAmount, 1), data, null);
    
    this._name = name;
    this._deliveryName = deliveryName;
    this._lore = lore;
    this._displayItem = displayItem;
    this._deliveryAmount = deliveryAmount;
    this._locked = locked;
    
    UpdateVisual(false);
    
    setAmount(Math.max(deliveryAmount, 1));
  }
  
  public boolean IsLocked()
  {
    return this._locked;
  }
  
  public void SetDeliverySettings()
  {
    setAmount(this._deliveryAmount);
    if (this._deliveryName != null)
    {
      ItemMeta meta = getItemMeta();
      meta.setDisplayName(this._deliveryName);
      setItemMeta(meta);
    }
  }
  
  public ShopItem clone()
  {
    return new ShopItem(super.clone(), this._name, this._deliveryName, this._deliveryAmount, this._locked, this._displayItem);
  }
  
  public boolean equals(Object obj)
  {
    return super.equals(obj);
  }
  
  protected void UpdateVisual(boolean clone)
  {
    ItemMeta meta = getItemMeta();
    if (!clone) {
      meta.setDisplayName(((this._locked) && (!this._displayItem) ? C.cRed : C.cGreen) + C.Bold + this._name);
    }
    ArrayList<String> lore = new ArrayList();
    if (this._lore != null)
    {
      String[] arrayOfString;
      int j = (arrayOfString = this._lore).length;
      for (int i = 0; i < j; i++)
      {
        String line = arrayOfString[i];
        if ((line != null) && (!line.isEmpty())) {
          lore.add(line);
        }
      }
    }
    meta.setLore(lore);
    
    setItemMeta(meta);
  }
  
  public boolean IsDisplay()
  {
    return this._displayItem;
  }
  
  public void addGlow()
  {
    UtilInv.addDullEnchantment(this);
  }
  
  public void SetLocked(boolean owns)
  {
    this._locked = owns;
    UpdateVisual(false);
  }
  
  public String GetName()
  {
    return this._name;
  }
  
  public void SetName(String name)
  {
    this._name = name;
  }
  
  public void SetLore(String[] string)
  {
    this._lore = string;
    
    ArrayList<String> lore = new ArrayList();
    if (this._lore != null)
    {
      String[] arrayOfString;
      int j = (arrayOfString = this._lore).length;
      for (int i = 0; i < j; i++)
      {
        String line = arrayOfString[i];
        if ((line != null) && (!line.isEmpty())) {
          lore.add(line);
        }
      }
    }
    ItemMeta meta = getItemMeta();
    meta.setLore(lore);
    setItemMeta(meta);
  }
}
