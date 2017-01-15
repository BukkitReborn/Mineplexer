package mineplex.core.achievement.ui.page;

import java.util.ArrayList;
import mineplex.core.account.CoreClientManager;
import mineplex.core.achievement.AchievementCategory;
import mineplex.core.achievement.AchievementCategory.GameCategory;
import mineplex.core.achievement.AchievementManager;
import mineplex.core.achievement.ui.AchievementShop;
import mineplex.core.achievement.ui.button.CategoryButton;
import mineplex.core.common.util.C;
import mineplex.core.donation.DonationManager;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import mineplex.core.stats.StatsManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ArcadeMainPage
  extends AchievementMainPage
{
  public ArcadeMainPage(AchievementManager plugin, StatsManager statsManager, AchievementShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player, Player target)
  {
    super(plugin, statsManager, shop, clientManager, donationManager, name, player, target);
  }
  
  protected void buildPage()
  {
    int slot = 9;
    AchievementCategory[] arrayOfAchievementCategory;
    int j = (arrayOfAchievementCategory = AchievementCategory.values()).length;
    for (int i = 0; i < j; i++)
    {
      AchievementCategory category = arrayOfAchievementCategory[i];
      if (category.getGameCategory() == AchievementCategory.GameCategory.ARCADE)
      {
        CategoryButton button = new CategoryButton((AchievementShop)getShop(), (AchievementManager)getPlugin(), this._statsManager, category, getDonationManager(), getClientManager(), this._target);
        
        ArrayList<String> lore = new ArrayList();
        lore.add(" ");
        category.addStats(getClientManager(), this._statsManager, lore, 2, getPlayer(), this._target);
        lore.add(" ");
        addAchievements(category, lore, 9);
        lore.add(" ");
        lore.add(ChatColor.RESET + "Click for more details!");
        
        ShopItem shopItem = new ShopItem(category.getIcon(), category.getIconData(), C.Bold + category.getFriendlyName(), (String[])lore.toArray(new String[0]), 1, false, false);
        addButton(slot, shopItem, button);
        
        slot += ((slot + 1) % 9 == 0 ? 1 : 2);
      }
    }
    addBackButton();
  }
  
  private void addBackButton()
  {
    addButton(4, new ShopItem(Material.BED, C.cGray + " ⇽ Go Back", new String[0], 1, false), new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ((AchievementShop)ArcadeMainPage.this.getShop()).openPageForPlayer(ArcadeMainPage.this.getPlayer(), new AchievementMainPage((AchievementManager)ArcadeMainPage.this.getPlugin(), ArcadeMainPage.this._statsManager, (AchievementShop)ArcadeMainPage.this.getShop(), ArcadeMainPage.this.getClientManager(), ArcadeMainPage.this.getDonationManager(), ArcadeMainPage.this._target.getName() + "'s Stats", player, ArcadeMainPage.this._target));
        player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
      }
    });
  }
}
