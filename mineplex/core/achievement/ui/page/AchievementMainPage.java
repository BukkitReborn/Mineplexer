package mineplex.core.achievement.ui.page;

import java.util.ArrayList;
import java.util.List;
import mineplex.core.account.CoreClientManager;
import mineplex.core.achievement.Achievement;
import mineplex.core.achievement.AchievementCategory;
import mineplex.core.achievement.AchievementCategory.GameCategory;
import mineplex.core.achievement.AchievementData;
import mineplex.core.achievement.AchievementManager;
import mineplex.core.achievement.ui.AchievementShop;
import mineplex.core.achievement.ui.button.ArcadeButton;
import mineplex.core.achievement.ui.button.CategoryButton;
import mineplex.core.common.util.C;
import mineplex.core.donation.DonationManager;
import mineplex.core.itemstack.ItemLayout;
import mineplex.core.shop.item.ShopItem;
import mineplex.core.shop.page.ShopPageBase;
import mineplex.core.stats.StatsManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AchievementMainPage
  extends ShopPageBase<AchievementManager, AchievementShop>
{
  protected Player _target;
  protected StatsManager _statsManager;
  
  public AchievementMainPage(AchievementManager plugin, StatsManager statsManager, AchievementShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player, Player target)
  {
    super(plugin, shop, clientManager, donationManager, name, player, 36);
    
    this._target = target;
    this._statsManager = statsManager;
    
    buildPage();
  }
  
  protected void buildPage()
  {
    ArrayList<Integer> pageLayout = new ItemLayout(new String[] {
      "XXXXOXXXX", 
      "OXOXOXOXO", 
      "OXOXOXOXO", 
      "XXOXOXOXX" }).getItemSlots();
    int listSlot = 0;
    AchievementCategory[] arrayOfAchievementCategory;
    int j = (arrayOfAchievementCategory = AchievementCategory.values()).length;
    for (int i = 0; i < j; i++)
    {
      AchievementCategory category = arrayOfAchievementCategory[i];
      if (category.getGameCategory() != AchievementCategory.GameCategory.ARCADE)
      {
        CategoryButton button = new CategoryButton((AchievementShop)getShop(), (AchievementManager)getPlugin(), this._statsManager, category, getDonationManager(), 
          getClientManager(), this._target);
        
        ArrayList<String> lore = new ArrayList();
        lore.add(" ");
        category.addStats(getClientManager(), this._statsManager, lore, category == AchievementCategory.GLOBAL ? 5 : 2, 
          getPlayer(), this._target);
        lore.add(" ");
        addAchievements(category, lore, 9);
        lore.add(" ");
        lore.add(ChatColor.RESET + "Click for more details!");
        
        ShopItem shopItem = new ShopItem(category.getIcon(), category.getIconData(), C.Bold + category.getFriendlyName(), 
          (String[])lore.toArray(new String[0]), 1, false, false);
        addButton(((Integer)pageLayout.get(listSlot++)).intValue(), shopItem, button);
      }
    }
    addArcadeButton(((Integer)pageLayout.get(listSlot++)).intValue());
  }
  
  protected void addArcadeButton(int slot)
  {
    ArcadeButton button = new ArcadeButton((AchievementShop)getShop(), (AchievementManager)getPlugin(), this._statsManager, getDonationManager(), getClientManager(), this._target);
    ShopItem shopItem = new ShopItem(Material.BOW, (byte)0, C.Bold + "Arcade Games", new String[] { " ", ChatColor.RESET + "Click for more!" }, 1, false, false);
    
    addButton(slot, shopItem, button);
  }
  
  protected void addAchievements(AchievementCategory category, List<String> lore, int max)
  {
    int achievementCount = 0;
    for (int i = 0; (i < Achievement.values().length) && (achievementCount < max); i++)
    {
      Achievement achievement = Achievement.values()[i];
      if (achievement.getCategory() == category) {
        if (achievement.getMaxLevel() <= 1)
        {
          AchievementData data = ((AchievementManager)getPlugin()).get(this._target, achievement);
          boolean finished = data.getLevel() >= achievement.getMaxLevel();
          
          lore.add((finished ? C.cGreen : C.cRed) + achievement.getName());
          
          achievementCount++;
        }
      }
    }
  }
}
