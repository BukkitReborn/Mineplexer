package mineplex.core.achievement;

import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.achievement.command.StatsCommand;
import mineplex.core.achievement.ui.AchievementShop;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilGear;
import mineplex.core.common.util.UtilInv;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.donation.DonationManager;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.stats.PlayerStats;
import mineplex.core.stats.StatsManager;
import mineplex.core.stats.event.StatChangeEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

public class AchievementManager
  extends MiniPlugin
{
  private StatsManager _statsManager;
  private AchievementShop _shop;
  private int _interfaceSlot = 7;
  private boolean _giveInterfaceItem = false;
  private NautHashMap<String, NautHashMap<Achievement, AchievementLog>> _log = new NautHashMap();
  private boolean _shopEnabled = true;
  public static String _mineplexName;
  
  public AchievementManager(StatsManager statsManager, CoreClientManager clientManager, DonationManager donationManager, String mineplexName)
  {
    super("Achievement Manager", statsManager.getPlugin());
    this._statsManager = statsManager;
    this._shop = new AchievementShop(this, this._statsManager, clientManager, donationManager, "Achievement");
    
    _mineplexName = mineplexName;
  }
  
  public AchievementData get(Player player, Achievement type)
  {
    return get(player.getName(), type);
  }
  
  public AchievementData get(String playerName, Achievement type)
  {
    int exp = 0;
    String[] arrayOfString;
    int j = (arrayOfString = type.getStats()).length;
    for (int i = 0; i < j; i++)
    {
      String stat = arrayOfString[i];
      exp = (int)(exp + ((PlayerStats)this._statsManager.Get(playerName)).getStat(stat));
    }
    return type.getLevelData(exp);
  }
  
  @EventHandler
  public void informLevelUp(StatChangeEvent event)
  {
    Player player = UtilPlayer.searchExact(event.getPlayerName());
    if (player == null) {
      return;
    }
    Achievement[] arrayOfAchievement;
    int j = (arrayOfAchievement = Achievement.values()).length;
    for (int i = 0; i < j; i++)
    {
      Achievement type = arrayOfAchievement[i];
      String[] arrayOfString;
      int m = (arrayOfString = type.getStats()).length;
      for (int k = 0; k < m; k++)
      {
        String stat = arrayOfString[k];
        if (stat.equalsIgnoreCase(event.getStatName()))
        {
          if (!this._log.containsKey(player.getName())) {
            this._log.put(player.getName(), new NautHashMap());
          }
          if (type.getLevelData(event.getValueAfter()).getLevel() > type.getLevelData(event.getValueBefore()).getLevel())
          {
            if (!((NautHashMap)this._log.get(player.getName())).containsKey(type))
            {
              ((NautHashMap)this._log.get(player.getName())).put(type, new AchievementLog(event.getValueAfter() - event.getValueBefore(), true));
            }
            else
            {
              AchievementLog log = (AchievementLog)((NautHashMap)this._log.get(player.getName())).get(type);
              log.Amount += event.getValueAfter() - event.getValueBefore();
              log.LevelUp = true;
            }
          }
          else if (!((NautHashMap)this._log.get(player.getName())).containsKey(type)) {
            if (!((NautHashMap)this._log.get(player.getName())).containsKey(type))
            {
              ((NautHashMap)this._log.get(player.getName())).put(type, new AchievementLog(event.getValueAfter() - event.getValueBefore(), false));
            }
            else
            {
              AchievementLog log = (AchievementLog)((NautHashMap)this._log.get(player.getName())).get(type);
              log.Amount += event.getValueAfter() - event.getValueBefore();
            }
          }
        }
      }
    }
  }
  
  public void addCommands()
  {
    addCommand(new StatsCommand(this));
  }
  
  public void openShop(Player player)
  {
    this._shop.attemptShopOpen(player);
  }
  
  public void openShop(Player player, Player target)
  {
    this._shop.attemptShopOpen(player, target);
  }
  
  @EventHandler
  public void playerQuit(PlayerQuitEvent event)
  {
    this._log.remove(event.getPlayer().getName());
  }
  
  @EventHandler
  public void playerJoin(PlayerJoinEvent event)
  {
    if (this._giveInterfaceItem) {
      giveInterfaceItem(event.getPlayer());
    }
  }
  
  public void clearLog(Player player)
  {
    this._log.remove(player.getName());
  }
  
  public NautHashMap<Achievement, AchievementLog> getLog(Player player)
  {
    return (NautHashMap)this._log.remove(player.getName());
  }
  
  public void setGiveInterfaceItem(boolean giveInterfaceItem)
  {
    this._giveInterfaceItem = giveInterfaceItem;
  }
  
  public void giveInterfaceItem(Player player)
  {
    if (!UtilGear.isMat(player.getInventory().getItem(this._interfaceSlot), Material.SKULL_ITEM))
    {
      ItemStack item = ItemStackFactory.Instance.CreateStack(Material.SKULL_ITEM, (byte)3, 1, ChatColor.RESET + C.cGreen + "/stats");
      SkullMeta meta = (SkullMeta)item.getItemMeta();
      meta.setOwner(player.getName());
      item.setItemMeta(meta);
      
      player.getInventory().setItem(this._interfaceSlot, item);
      
      UtilInv.Update(player);
    }
  }
  
  @EventHandler
  public void openShop(PlayerInteractEvent event)
  {
    if (!this._shopEnabled) {
      return;
    }
    if ((event.hasItem()) && (event.getItem().getType() == Material.SKULL_ITEM))
    {
      event.setCancelled(true);
      openShop(event.getPlayer());
    }
  }
  
  public boolean hasCategory(Player player, Achievement[] required)
  {
    if ((required == null) || (required.length == 0)) {
      return false;
    }
    Achievement[] arrayOfAchievement;
    int j = (arrayOfAchievement = required).length;
    for (int i = 0; i < j; i++)
    {
      Achievement cur = arrayOfAchievement[i];
      if (get(player, cur).getLevel() < cur.getMaxLevel()) {
        return false;
      }
    }
    return true;
  }
  
  public int getMineplexLevelNumber(Player sender, Rank rank)
  {
    int level = get(sender, Achievement.GLOBAL_MINEPLEX_LEVEL).getLevel();
    if (sender.getName().equalsIgnoreCase("B2_mp")) {
      return 101;
    }
    if (rank.Has(Rank.MODERATOR)) {
      level = Math.max(level, 5);
    }
    if (rank.Has(Rank.SNR_MODERATOR)) {
      level = Math.max(level, 15);
    }
    if (rank.Has(Rank.JNR_DEV)) {
      level = Math.max(level, 25);
    }
    if (rank.Has(Rank.ADMIN)) {
      level = Math.max(level, 30 + get(sender, Achievement.GLOBAL_GEM_HUNTER).getLevel());
    }
    if (rank.Has(Rank.OWNER)) {
      level = Math.max(level, 50 + get(sender, Achievement.GLOBAL_GEM_HUNTER).getLevel());
    }
    if (sender.getName().equalsIgnoreCase("Phinary")) {
      level = -level;
    }
    return level;
  }
  
  public String getMineplexLevel(Player sender, Rank rank)
  {
    return Achievement.getExperienceString(getMineplexLevelNumber(sender, rank)) + " " + ChatColor.RESET;
  }
  
  public void setShopEnabled(boolean var)
  {
    this._shopEnabled = var;
  }
}
