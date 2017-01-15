package mineplex.core.loot;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RandomItem
{
  private int _amount;
  private ItemStack _item;
  private int _min;
  private int _max;
  
  public RandomItem(ItemStack item, int amount)
  {
    this(item, amount, item.getAmount(), item.getAmount());
  }
  
  public RandomItem(ItemStack item, int amount, int minStackSize, int maxStackSize)
  {
    this._amount = amount;
    this._item = item;
    this._min = minStackSize;
    this._max = maxStackSize;
  }
  
  public RandomItem(Material material, int amount)
  {
    this(material, amount, 1, 1);
  }
  
  public RandomItem(Material material, int amount, int minStackSize, int maxStackSize)
  {
    this._amount = amount;
    this._item = new ItemStack(material);
    this._min = minStackSize;
    this._max = maxStackSize;
  }
  
  public int getAmount()
  {
    return this._amount;
  }
  
  public ItemStack getItemStack()
  {
    this._item.setAmount(new Random().nextInt(Math.max(1, this._max - this._min + 1)) + this._min);
    
    return this._item;
  }
}
