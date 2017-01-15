package mineplex.core.reward.rewards;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.account.repository.AccountRepository;
import mineplex.core.common.Rank;
import mineplex.core.reward.Reward;
import mineplex.core.reward.RewardData;
import mineplex.core.reward.RewardRarity;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RankReward
  extends Reward
{
  private CoreClientManager _clientManager;
  
  public RankReward(CoreClientManager clientManager, int weight, RewardRarity rarity)
  {
    super(rarity, weight);
    
    this._clientManager = clientManager;
  }
  
  public RewardData giveRewardCustom(Player player)
  {
    Rank rank = null;
    if (this._clientManager.Get(player).GetRank() == Rank.ALL) {
      rank = Rank.ULTRA;
    } else if (this._clientManager.Get(player).GetRank() == Rank.ULTRA) {
      rank = Rank.HERO;
    } else if (this._clientManager.Get(player).GetRank() == Rank.HERO) {
      rank = Rank.LEGEND;
    }
    if (rank == null) {
      return new RewardData(getRarity().getColor() + "Rank Upgrade Error", new ItemStack(Material.PAPER));
    }
    this._clientManager.Get(player).SetRank(rank);
    this._clientManager.getRepository().saveRank(null, player.getName(), player.getUniqueId(), rank, true);
    
    return new RewardData(getRarity().getColor() + rank.Name + " Rank", new ItemStack(Material.NETHER_STAR));
  }
  
  public boolean canGiveReward(Player player)
  {
    return !this._clientManager.Get(player).GetRank().Has(Rank.LEGEND);
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof RankReward)) {
      return true;
    }
    return false;
  }
}
