package mineplex.core.achievement.ui.button;

import mineplex.core.account.CoreClientManager;
import mineplex.core.achievement.AchievementCategory;
import mineplex.core.achievement.AchievementManager;
import mineplex.core.achievement.ui.AchievementShop;
import mineplex.core.achievement.ui.page.AchievementPage;
import mineplex.core.donation.DonationManager;
import mineplex.core.shop.item.IButton;
import mineplex.core.stats.StatsManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class CategoryButton
  implements IButton
{
  private AchievementShop _shop;
  private AchievementCategory _category;
  private AchievementManager _achievementManager;
  private StatsManager _statsManager;
  private DonationManager _donationManager;
  private CoreClientManager _clientManager;
  private Player _target;
  
  public CategoryButton(AchievementShop shop, AchievementManager achievementManager, StatsManager statsManager, AchievementCategory category, DonationManager donationManager, CoreClientManager clientManager, Player target)
  {
    this._category = category;
    this._shop = shop;
    this._achievementManager = achievementManager;
    this._statsManager = statsManager;
    this._donationManager = donationManager;
    this._clientManager = clientManager;
    this._target = target;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._shop.openPageForPlayer(player, new AchievementPage(this._achievementManager, this._statsManager, this._category, this._shop, this._clientManager, this._donationManager, player, this._target));
    player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
  }
}
