package mineplex.core.achievement;

import java.util.List;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilTime;
import mineplex.core.common.util.UtilTime.TimeUnit;
import mineplex.core.stats.PlayerStats;
import mineplex.core.stats.StatsManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum AchievementCategory
{
  GLOBAL("Global", null, new StatDisplay[] { StatDisplay.GEMS_EARNED, 0, new StatDisplay("Games Played", new String[] { "GamesPlayed" }), StatDisplay.TIME_IN_GAME }, Material.EMERALD, 0, GameCategory.GLOBAL, "None"),  BRIDGES("The Bridges", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.IRON_PICKAXE, 0, GameCategory.SURVIVAL, "Destructor Kit"),  SURVIVAL_GAMES("Survival Games", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.DIAMOND_SWORD, 0, GameCategory.SURVIVAL, "Horseman Kit"),  SKYWARS("Skywars", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.FEATHER, 5, GameCategory.SURVIVAL, "Destructor Kit"),  UHC("Ultra Hardcore", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.GOLDEN_APPLE, 0, GameCategory.SURVIVAL, "None"),  WIZARDS("Wizards", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.BLAZE_ROD, 0, GameCategory.SURVIVAL, "Witch Doctor Kit"),  CASTLE_SIEGE("Castle Siege", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, new StatDisplay("Kills as Defenders"), new StatDisplay("Deaths as Defenders"), new StatDisplay("Kills as Undead"), new StatDisplay("Deaths as Undead"), StatDisplay.GEMS_EARNED }, Material.DIAMOND_CHESTPLATE, 0, GameCategory.CLASSICS, null),  BLOCK_HUNT("Block Hunt", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.GRASS, 0, GameCategory.CLASSICS, "Infestor Kit"),  SMASH_MOBS("Super Smash Mobs", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.SKULL_ITEM, 4, GameCategory.CLASSICS, "Sheep Kit"),  MINE_STRIKE("MineStrike", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.TNT, 0, GameCategory.CLASSICS, "None"),  DRAW_MY_THING("Draw My Thing", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.GEMS_EARNED }, Material.BOOK_AND_QUILL, 0, GameCategory.CLASSICS, "Extra Tools Kit"),  CHAMPIONS("Champions", new String[] { "Champions Domination", "Champions TDM" }, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.BEACON, 0, GameCategory.CHAMPIONS, "Extra Class Skills"),  MASTER_BUILDERS("Master Builders", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.GEMS_EARNED }, Material.WOOD, 0, GameCategory.CLASSICS, "None"),  DRAGONS("Dragons", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.GEMS_EARNED }, Material.ENDER_STONE, 0, GameCategory.ARCADE, null),  DRAGON_ESCAPE("Dragon Escape", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.GEMS_EARNED }, Material.DRAGON_EGG, 0, GameCategory.ARCADE, "Digger Kit"),  SHEEP_QUEST("Sheep Quest", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.WOOL, 0, GameCategory.ARCADE, null),  SNEAKY_ASSASSINS("Sneaky Assassins", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.INK_SACK, 0, GameCategory.ARCADE, "Briber Kit"),  ONE_IN_THE_QUIVER("One in the Quiver", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.BOW, 0, GameCategory.ARCADE, "Slam Shooter Kit"),  SUPER_PAINTBALL("Super Paintball", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.ENDER_PEARL, 0, GameCategory.ARCADE, null),  TURF_WARS("Turf Wars", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.HARD_CLAY, 14, GameCategory.ARCADE, null),  RUNNER("Runner", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.LEATHER_BOOTS, 0, GameCategory.ARCADE, null),  SPLEEF("Super Spleef", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.IRON_SPADE, 0, GameCategory.ARCADE, null),  DEATH_TAG("Death Tag", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.SKULL_ITEM, 0, GameCategory.ARCADE, null),  SNAKE("Snake", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.WOOL, 4, GameCategory.ARCADE, "Reversal Snake Kit"),  BACON_BRAWL("Bacon Brawl", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.PORK, 0, GameCategory.ARCADE, null),  MICRO_BATTLE("Micro Battle", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.LAVA, 0, GameCategory.ARCADE, null),  BOMB_LOBBERS("Bomb Lobbers", null, new StatDisplay[] { StatDisplay.WINS, StatDisplay.GAMES_PLAYED, StatDisplay.KILLS, StatDisplay.DEATHS, StatDisplay.GEMS_EARNED }, Material.FIREBALL, 0, GameCategory.ARCADE, "Waller Kit");
  
  private String _name;
  private String[] _statsToPull;
  private StatDisplay[] _statDisplays;
  private Material _icon;
  private GameCategory _gameCategory;
  private byte _iconData;
  private String _kitReward;
  
  private AchievementCategory(String name, String[] statsToPull, StatDisplay[] statDisplays, Material icon, int iconData, GameCategory gameCategory, String kitReward)
  {
    this._name = name;
    this._statsToPull = new String[] { statsToPull != null ? statsToPull : name };
    this._statDisplays = statDisplays;
    this._icon = icon;
    this._iconData = ((byte)iconData);
    this._gameCategory = gameCategory;
    this._kitReward = kitReward;
  }
  
  public String getFriendlyName()
  {
    return this._name;
  }
  
  public String getReward()
  {
    return this._kitReward;
  }
  
  public Material getIcon()
  {
    return this._icon;
  }
  
  public String[] getStatsToPull()
  {
    return this._statsToPull;
  }
  
  public StatDisplay[] getStatsToDisplay()
  {
    return this._statDisplays;
  }
  
  public byte getIconData()
  {
    return this._iconData;
  }
  
  public GameCategory getGameCategory()
  {
    return this._gameCategory;
  }
  
  public void addStats(CoreClientManager clientManager, StatsManager statsManager, List<String> lore, Player player, Player target)
  {
    addStats(clientManager, statsManager, lore, Integer.MAX_VALUE, player, target);
  }
  
  public void addStats(CoreClientManager clientManager, StatsManager statsManager, List<String> lore, int max, Player player, Player target)
  {
    PlayerStats stats = (PlayerStats)statsManager.Get(target);
    for (int i = 0; (i < this._statDisplays.length) && (i < max); i++) {
      if (this._statDisplays[i] == null)
      {
        lore.add(" ");
      }
      else
      {
        String displayName = this._statDisplays[i].getDisplayName();
        if ((clientManager.Get(player).GetRank().Has(Rank.MODERATOR)) || (player.equals(target)) || ((!displayName.contains("Losses")) && (!displayName.contains("Kills")) && (!displayName.contains("Deaths")) && (!displayName.equals("Time In Game")) && (!displayName.equals("Games Played"))))
        {
          int statNumber = 0;
          String[] arrayOfString1;
          int j = (arrayOfString1 = this._statsToPull).length;
          for (int i = 0; i < j; i++)
          {
            String statToPull = arrayOfString1[i];
            String[] arrayOfString2;
            int m = (arrayOfString2 = this._statDisplays[i].getStats()).length;
            for (int k = 0; k < m; k++)
            {
              String statName = arrayOfString2[k];
              statNumber = (int)(statNumber + stats.getStat(statToPull + "." + statName));
            }
          }
          String statString = C.cWhite + statNumber;
          if (displayName.equalsIgnoreCase("Time In Game")) {
            statString = C.cWhite + UtilTime.convertString(statNumber * 1000L, 0, UtilTime.TimeUnit.FIT);
          }
          lore.add(C.cYellow + displayName + ": " + statString);
        }
      }
    }
  }
  
  public static enum GameCategory
  {
    GLOBAL,  SURVIVAL,  CLASSICS,  CHAMPIONS,  ARCADE;
  }
}
