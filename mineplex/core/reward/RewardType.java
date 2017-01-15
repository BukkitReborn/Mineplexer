package mineplex.core.reward;

public enum RewardType
{
  GameLoot(
    1.0E-6D, 1.0E-5D, 1.0E-4D, 3.0D),  OldChest(0.0D, 0.05D, 0.4D, 5.0D),  AncientChest(15.0D, 30.0D, 45.0D, 50.0D),  MythicalChest(25.0D, 50.0D, 75.0D, 100.0D);
  
  private double _mythicalChance;
  private double _legendaryChance;
  private double _rareChance;
  private double _uncommonChance;
  
  private RewardType(double mythical, double legend, double rare, double uncommon)
  {
    this._mythicalChance = (mythical / 100.0D);
    this._legendaryChance = (this._mythicalChance + legend / 100.0D);
    this._rareChance = (this._legendaryChance + rare / 100.0D);
    this._uncommonChance = (this._rareChance + uncommon / 100.0D);
  }
  
  public RewardRarity generateRarity(boolean requiresUncommon)
  {
    double rand = Math.random();
    
    RewardRarity rarity = RewardRarity.COMMON;
    if (rand <= this._mythicalChance) {
      rarity = RewardRarity.MYTHICAL;
    } else if (rand <= this._legendaryChance) {
      rarity = RewardRarity.LEGENDARY;
    } else if (rand <= this._rareChance) {
      rarity = RewardRarity.RARE;
    } else if ((rand <= this._uncommonChance) || (requiresUncommon)) {
      rarity = RewardRarity.UNCOMMON;
    }
    return rarity;
  }
}
