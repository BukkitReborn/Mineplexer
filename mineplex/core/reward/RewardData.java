package mineplex.core.reward;

import org.bukkit.inventory.ItemStack;

public class RewardData
{
  private final String _friendlyName;
  private final ItemStack _displayItem;
  
  public RewardData(String friendlyName, ItemStack displayItem)
  {
    this._friendlyName = friendlyName;
    this._displayItem = displayItem;
  }
  
  public String getFriendlyName()
  {
    return this._friendlyName;
  }
  
  public ItemStack getDisplayItem()
  {
    return this._displayItem;
  }
}
