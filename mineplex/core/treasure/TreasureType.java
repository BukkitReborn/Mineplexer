package mineplex.core.treasure;

import mineplex.core.common.util.C;
import mineplex.core.reward.RewardType;
import org.bukkit.Material;

public enum TreasureType
{
  OLD(C.cYellow + "Old Chest", "Old Chest", RewardType.OldChest, Material.CHEST, TreasureStyle.OLD),  ANCIENT(C.cGold + "Ancient Chest", "Ancient Chest", RewardType.AncientChest, Material.TRAPPED_CHEST, TreasureStyle.ANCIENT),  MYTHICAL(C.cRed + "Mythical Chest", "Mythical Chest", RewardType.MythicalChest, Material.ENDER_CHEST, TreasureStyle.MYTHICAL);
  
  private final String _name;
  private final RewardType _rewardType;
  private final Material _material;
  private final TreasureStyle _treasureStyle;
  private final String _itemName;
  
  private TreasureType(String name, String itemName, RewardType rewardType, Material material, TreasureStyle treasureStyle)
  {
    this._name = name;
    this._itemName = itemName;
    this._rewardType = rewardType;
    this._material = material;
    this._treasureStyle = treasureStyle;
  }
  
  public RewardType getRewardType()
  {
    return this._rewardType;
  }
  
  public String getName()
  {
    return this._name;
  }
  
  public Material getMaterial()
  {
    return this._material;
  }
  
  public TreasureStyle getStyle()
  {
    return this._treasureStyle;
  }
  
  public String getItemName()
  {
    return this._itemName;
  }
}
