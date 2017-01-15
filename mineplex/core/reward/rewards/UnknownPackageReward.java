package mineplex.core.reward.rewards;

import java.io.PrintStream;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.reward.Reward;
import mineplex.core.reward.RewardData;
import mineplex.core.reward.RewardRarity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class UnknownPackageReward
  extends Reward
{
  private DonationManager _donationManager;
  private ItemStack _itemStack;
  private String _name;
  private String _packageName;
  
  public UnknownPackageReward(DonationManager donationManager, String name, String packageName, ItemStack itemStack, RewardRarity rarity, int weight)
  {
    super(rarity, weight);
    this._donationManager = donationManager;
    this._name = name;
    this._packageName = packageName;
    this._itemStack = itemStack;
  }
  
  protected RewardData giveRewardCustom(Player player)
  {
    this._donationManager.PurchaseUnknownSalesPackage(null, player.getName(), this._donationManager.getClientManager().Get(player).getAccountId(), this._packageName, true, 0, true);
    
    return new RewardData(getRarity().getColor() + this._name, this._itemStack);
  }
  
  public boolean canGiveReward(Player player)
  {
    if (this._donationManager.Get(player.getName()) == null)
    {
      System.out.println("Could not give reward " + this._packageName + " to Offline Player: " + player.getName());
      return false;
    }
    return !((Donor)this._donationManager.Get(player.getName())).OwnsUnknownPackage(this._packageName);
  }
  
  protected String getPackageName()
  {
    return this._packageName;
  }
  
  protected String getFriendlyName()
  {
    return this._name;
  }
  
  public boolean equals(Object obj)
  {
    if ((obj instanceof UnknownPackageReward)) {
      return ((UnknownPackageReward)obj).getPackageName().equals(this._packageName);
    }
    return false;
  }
}
