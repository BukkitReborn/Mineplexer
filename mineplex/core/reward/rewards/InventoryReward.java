package mineplex.core.reward.rewards;

import java.util.Random;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.reward.Reward;
import mineplex.core.reward.RewardData;
import mineplex.core.reward.RewardRarity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryReward
  extends Reward
{
  private Random _random;
  private InventoryManager _inventoryManager;
  private ItemStack _itemStack;
  private String _name;
  private String _packageName;
  private int _minAmount;
  private int _maxAmount;
  
  public InventoryReward(InventoryManager inventoryManager, String name, String packageName, int minAmount, int maxAmount, ItemStack itemStack, RewardRarity rarity, int weight)
  {
    this(RANDOM, inventoryManager, name, packageName, minAmount, maxAmount, itemStack, rarity, weight);
  }
  
  public InventoryReward(Random random, InventoryManager inventoryManager, String name, String packageName, int minAmount, int maxAmount, ItemStack itemStack, RewardRarity rarity, int weight)
  {
    super(rarity, weight);
    
    this._random = random;
    this._name = name;
    this._packageName = packageName;
    this._minAmount = minAmount;
    this._maxAmount = maxAmount;
    this._itemStack = itemStack;
    this._inventoryManager = inventoryManager;
  }
  
  public RewardData giveRewardCustom(Player player)
  {
    int amountToGive;
    int amountToGive;
    if (this._minAmount != this._maxAmount) {
      amountToGive = this._random.nextInt(this._maxAmount - this._minAmount) + this._minAmount;
    } else {
      amountToGive = this._minAmount;
    }
    this._inventoryManager.addItemToInventory(player, "Item", this._packageName, amountToGive);
    
    return new RewardData(getRarity().getColor() + amountToGive + " " + this._name, this._itemStack);
  }
  
  public boolean canGiveReward(Player player)
  {
    return true;
  }
  
  protected String getPackageName()
  {
    return this._packageName;
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof InventoryReward)) {
      return ((InventoryReward)obj).getPackageName().equals(this._packageName);
    }
    return false;
  }
}
