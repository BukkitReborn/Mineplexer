package mineplex.core.reward;

import java.util.Random;
import org.bukkit.entity.Player;

public abstract class Reward
{
  protected static final Random RANDOM = new Random();
  private RewardRarity _rarity;
  private int _weight;
  
  public Reward(RewardRarity rarity, int weight)
  {
    this._rarity = rarity;
    this._weight = weight;
  }
  
  public final RewardData giveReward(String type, Player player)
  {
    return giveRewardCustom(player);
  }
  
  protected abstract RewardData giveRewardCustom(Player paramPlayer);
  
  public abstract boolean canGiveReward(Player paramPlayer);
  
  public RewardRarity getRarity()
  {
    return this._rarity;
  }
  
  public int getWeight()
  {
    return this._weight;
  }
}
