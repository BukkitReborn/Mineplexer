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
import mineplex.core.common.util.C;
import mineplex.core.donation.DonationManager;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import mineplex.core.shop.page.ShopPageBase;
import mineplex.core.stats.StatsManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AchievementPage
  extends ShopPageBase<AchievementManager, AchievementShop>
{
  private static int ACHIEVEMENT_MIDDLE_INDEX = 31;
  private AchievementCategory _category;
  private StatsManager _statsManager;
  private Player _target;
  
  public AchievementPage(AchievementManager plugin, StatsManager statsManager, AchievementCategory category, AchievementShop shop, CoreClientManager clientManager, DonationManager donationManager, Player player, Player target)
  {
    super(plugin, shop, clientManager, donationManager, category.getFriendlyName(), player);
    
    this._statsManager = statsManager;
    this._category = category;
    this._target = target;
    
    buildPage();
  }
  
  protected void buildPage()
  {
    int currentIndex = ACHIEVEMENT_MIDDLE_INDEX - getAchievements().size() / 2;
    boolean hasAllAchievements = true;
    int achievementCount = 0;
    
    ArrayList<String> masterAchievementLore = new ArrayList();
    masterAchievementLore.add(" ");
    
    List<Achievement> achievements = getAchievements();
    for (Achievement achievement : achievements)
    {
      AchievementData data = ((AchievementManager)getPlugin()).get(this._target, achievement);
      boolean singleLevel = achievement.isSingleLevel();
      boolean hasUnlocked = data.getLevel() >= achievement.getMaxLevel();
      if (!hasUnlocked) {
        hasAllAchievements = false;
      }
      Material material = hasUnlocked ? Material.EXP_BOTTLE : Material.GLASS_BOTTLE;
      String itemName = (hasUnlocked ? C.cGreen : C.cRed) + achievement.getName();
      if (!singleLevel) {
        itemName = itemName + ChatColor.WHITE + " Level " + data.getLevel() + "/" + achievement.getMaxLevel();
      }
      ArrayList<String> lore = new ArrayList();
      lore.add(" ");
      String[] arrayOfString;
      int j = (arrayOfString = achievement.getDesc()).length;
      for (int i = 0; i < j; i++)
      {
        String descLine = arrayOfString[i];
        
        lore.add(ChatColor.RESET + descLine);
      }
      if ((!hasUnlocked) && (achievement.isOngoing()))
      {
        lore.add(" ");
        lore.add(C.cYellow + (singleLevel ? "Progress: " : "Next Level: ") + C.cWhite + data.getExpRemainder() + "/" + data.getExpNextLevel());
      }
      if ((!hasUnlocked) && (singleLevel))
      {
        lore.add(" ");
        lore.add(C.cYellow + "Reward: " + C.cGreen + achievement.getGemReward() + " Gems");
      }
      if ((hasUnlocked) && (data.getLevel() == achievement.getMaxLevel()))
      {
        lore.add(" ");
        lore.add(C.cAqua + "Complete!");
      }
      addItem(currentIndex, new ShopItem(material, (byte)(hasUnlocked ? 0 : 0), itemName, (String[])lore.toArray(new String[0]), 1, false, false));
      
      masterAchievementLore.add((hasUnlocked ? C.cGreen : C.cRed) + achievement.getName());
      
      currentIndex++;
      achievementCount++;
    }
    if ((!this._category.getFriendlyName().startsWith("Global")) && (achievementCount > 0))
    {
      String itemName = ChatColor.RESET + this._category.getFriendlyName() + " Master Achievement";
      masterAchievementLore.add(" ");
      if (getPlayer().equals(this._target)) {
        if (this._category.getReward() != null) {
          masterAchievementLore.add(C.cYellow + C.Bold + "Reward: " + ChatColor.RESET + this._category.getReward());
        } else {
          masterAchievementLore.add(C.cYellow + C.Bold + "Reward: " + ChatColor.RESET + "Coming Soon...");
        }
      }
      addItem(40, new ShopItem(hasAllAchievements ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK, (byte)0, itemName, (String[])masterAchievementLore.toArray(new String[0]), 1, false, true));
    }
    addBackButton();
    addStats();
  }
  
  private void addBackButton()
  {
    addButton(4, new ShopItem(Material.BED, C.cGray + " ⇽ Go Back", new String[0], 1, false), new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        AchievementMainPage page;
        AchievementMainPage page;
        if (AchievementPage.this._category.getGameCategory() == AchievementCategory.GameCategory.ARCADE) {
          page = new ArcadeMainPage((AchievementManager)AchievementPage.this.getPlugin(), AchievementPage.this._statsManager, (AchievementShop)AchievementPage.this.getShop(), AchievementPage.this.getClientManager(), AchievementPage.this.getDonationManager(), "Arcade Games", player, AchievementPage.this._target);
        } else {
          page = new AchievementMainPage((AchievementManager)AchievementPage.this.getPlugin(), AchievementPage.this._statsManager, (AchievementShop)AchievementPage.this.getShop(), AchievementPage.this.getClientManager(), AchievementPage.this.getDonationManager(), AchievementPage.this._target.getName() + "'s Stats", player, AchievementPage.this._target);
        }
        ((AchievementShop)AchievementPage.this.getShop()).openPageForPlayer(AchievementPage.this.getPlayer(), page);
        player.playSound(player.getLocation(), Sound.CLICK, 1.0F, 1.0F);
      }
    });
  }
  
  private void addStats()
  {
    if (this._category.getStatsToDisplay().length == 0) {
      return;
    }
    Material material = Material.BOOK;
    String itemName = C.Bold + this._category.getFriendlyName() + " Stats";
    List<String> lore = new ArrayList();
    lore.add(" ");
    this._category.addStats(getClientManager(), this._statsManager, lore, getPlayer(), this._target);
    
    ItemStack item = new ItemStack(material);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ChatColor.RESET + itemName);
    meta.setLore(lore);
    item.setItemMeta(meta);
    
    setItem(22, item);
  }
  
  public List<Achievement> getAchievements()
  {
    List<Achievement> achievements = new ArrayList();
    Achievement[] arrayOfAchievement;
    int j = (arrayOfAchievement = Achievement.values()).length;
    for (int i = 0; i < j; i++)
    {
      Achievement achievement = arrayOfAchievement[i];
      if (achievement.getCategory() == this._category) {
        achievements.add(achievement);
      }
    }
    return achievements;
  }
}
