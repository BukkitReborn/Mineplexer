package mineplex.core.loot;

import java.util.ArrayList;
import mineplex.core.common.util.UtilMath;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.ItemMeta.Spigot;

public class ChestLoot
{
  private ArrayList<RandomItem> _randomItems = new ArrayList();
  private int _totalLoot;
  private boolean _unbreakableLoot;
  
  public ChestLoot()
  {
    this(false);
  }
  
  public ChestLoot(boolean unbreakableLoot)
  {
    this._unbreakableLoot = unbreakableLoot;
  }
  
  public void cloneLoot(ChestLoot loot)
  {
    this._totalLoot += loot._totalLoot;
    this._randomItems.addAll(loot._randomItems);
  }
  
  public ItemStack getLoot()
  {
    int no = UtilMath.r(this._totalLoot);
    for (RandomItem item : this._randomItems)
    {
      no -= item.getAmount();
      if (no < 0)
      {
        ItemStack itemstack = item.getItemStack();
        if ((this._unbreakableLoot) && (itemstack.getType().getMaxDurability() > 16))
        {
          ItemMeta meta = itemstack.getItemMeta();
          meta.spigot().setUnbreakable(true);
          itemstack.setItemMeta(meta);
        }
        return itemstack;
      }
    }
    return null;
  }
  
  public void addLoot(ItemStack item, int amount)
  {
    addLoot(item, amount, item.getAmount(), item.getAmount());
  }
  
  public void addLoot(ItemStack item, int amount, int minStackSize, int maxStackSize)
  {
    addLoot(new RandomItem(item, amount, minStackSize, maxStackSize));
  }
  
  public void addLoot(Material material, int amount)
  {
    addLoot(material, amount, 1, 1);
  }
  
  public void addLoot(Material material, int amount, int minStackSize, int maxStackSize)
  {
    addLoot(new ItemStack(material), amount, minStackSize, maxStackSize);
  }
  
  public void addLoot(RandomItem item)
  {
    this._totalLoot += item.getAmount();
    this._randomItems.add(item);
  }
}
