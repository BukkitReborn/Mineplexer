package mineplex.core.reward.rewards;

import java.util.Random;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.Callback;
import mineplex.core.donation.DonationManager;
import mineplex.core.reward.Reward;
import mineplex.core.reward.RewardData;
import mineplex.core.reward.RewardRarity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CoinReward
  extends Reward
{
  private DonationManager _donationManager;
  private Random _random;
  private int _minCoinCount;
  private int _maxCoinCount;
  
  public CoinReward(DonationManager donationManager, int minCoinCount, int maxCoinCount, int weight, RewardRarity rarity)
  {
    this(donationManager, minCoinCount, maxCoinCount, weight, rarity, RANDOM);
  }
  
  public CoinReward(DonationManager donationManager, int minCoinCount, int maxCoinCount, int weight, RewardRarity rarity, Random random)
  {
    super(rarity, weight);
    this._donationManager = donationManager;
    this._minCoinCount = minCoinCount;
    this._maxCoinCount = maxCoinCount;
    
    this._random = random;
  }
  
  public RewardData giveRewardCustom(Player player)
  {
    int gemsToReward = this._random.nextInt(this._maxCoinCount - this._minCoinCount) + this._minCoinCount;
    
    this._donationManager.RewardCoins(new Callback()
    {
      public void run(Boolean data) {}
    }, "Treasure Chest", player.getName(), this._donationManager.getClientManager().Get(player).getAccountId(), gemsToReward);
    
    return new RewardData(getRarity().getColor() + gemsToReward + " Shards", new ItemStack(175));
  }
  
  public boolean canGiveReward(Player player)
  {
    return true;
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof CoinReward)) {
      return true;
    }
    return false;
  }
}
