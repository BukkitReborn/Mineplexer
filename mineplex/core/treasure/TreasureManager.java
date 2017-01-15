package mineplex.core.treasure;

import java.util.ArrayList;
import java.util.List;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.blockrestore.BlockRestore;
import mineplex.core.donation.DonationManager;
import mineplex.core.hologram.HologramManager;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.pet.PetManager;
import mineplex.core.reward.Reward;
import mineplex.core.reward.RewardManager;
import mineplex.core.reward.RewardType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class TreasureManager
  extends MiniPlugin
{
  private RewardManager _rewardManager;
  private InventoryManager _inventoryManager;
  private BlockRestore _blockRestore;
  private HologramManager _hologramManager;
  private List<TreasureLocation> _treasureLocations;
  public static String _website;
  
  public TreasureManager(JavaPlugin plugin, CoreClientManager clientManager, DonationManager donationManager, InventoryManager inventoryManager, PetManager petManager, BlockRestore blockRestore, HologramManager hologramManager, String website)
  {
    super("Treasure", plugin);
    
    this._inventoryManager = inventoryManager;
    this._blockRestore = blockRestore;
    this._hologramManager = hologramManager;
    this._rewardManager = new RewardManager(clientManager, donationManager, inventoryManager, petManager, 
      100, 250, 
      500, 1000, 
      4000, 6000, 
      12000, 32000, 
      true);
    
    _website = website;
    
    World world = (World)Bukkit.getWorlds().get(0);
    
    this._treasureLocations = new ArrayList();
    
    Block chestBlock = world.getBlockAt(-19, 72, -19);
    Block chestLoc1 = world.getBlockAt(-16, 72, -20);
    Block chestLoc2 = world.getBlockAt(-18, 72, -22);
    Block chestLoc3 = world.getBlockAt(-20, 72, -22);
    Block chestLoc4 = world.getBlockAt(-22, 72, -20);
    Block chestLoc5 = world.getBlockAt(-22, 72, -18);
    Block chestLoc6 = world.getBlockAt(-20, 72, -16);
    Block chestLoc7 = world.getBlockAt(-18, 72, -16);
    Block chestLoc8 = world.getBlockAt(-16, 72, -18);
    Location resetLocation = new Location(world, -23.5D, 72.0D, -23.5D);
    this._treasureLocations.add(new TreasureLocation(this, this._inventoryManager, clientManager, donationManager, chestBlock, new Block[] { chestLoc1, chestLoc2, chestLoc3, chestLoc4, chestLoc5, chestLoc6, chestLoc7, chestLoc8 }, resetLocation, this._hologramManager));
    
    Block chestBlock = world.getBlockAt(19, 72, 19);
    Block chestLoc1 = world.getBlockAt(16, 72, 20);
    Block chestLoc2 = world.getBlockAt(18, 72, 22);
    Block chestLoc3 = world.getBlockAt(20, 72, 22);
    Block chestLoc4 = world.getBlockAt(22, 72, 20);
    Block chestLoc5 = world.getBlockAt(22, 72, 18);
    Block chestLoc6 = world.getBlockAt(20, 72, 16);
    Block chestLoc7 = world.getBlockAt(18, 72, 16);
    Block chestLoc8 = world.getBlockAt(16, 72, 18);
    Location resetLocation = new Location(world, 23.5D, 72.0D, 23.5D);
    this._treasureLocations.add(new TreasureLocation(this, this._inventoryManager, clientManager, donationManager, chestBlock, new Block[] { chestLoc1, chestLoc2, chestLoc3, chestLoc4, chestLoc5, chestLoc6, chestLoc7, chestLoc8 }, resetLocation, this._hologramManager));
    
    Block chestBlock = world.getBlockAt(19, 72, -19);
    Block chestLoc1 = world.getBlockAt(16, 72, -20);
    Block chestLoc2 = world.getBlockAt(18, 72, -22);
    Block chestLoc3 = world.getBlockAt(20, 72, -22);
    Block chestLoc4 = world.getBlockAt(22, 72, -20);
    Block chestLoc5 = world.getBlockAt(22, 72, -18);
    Block chestLoc6 = world.getBlockAt(20, 72, -16);
    Block chestLoc7 = world.getBlockAt(18, 72, -16);
    Block chestLoc8 = world.getBlockAt(16, 72, -18);
    Location resetLocation = new Location(world, 23.5D, 72.0D, -23.5D);
    this._treasureLocations.add(new TreasureLocation(this, this._inventoryManager, clientManager, donationManager, chestBlock, new Block[] { chestLoc1, chestLoc2, chestLoc3, chestLoc4, chestLoc5, chestLoc6, chestLoc7, chestLoc8 }, resetLocation, this._hologramManager));
    
    Block chestBlock = world.getBlockAt(-19, 72, 19);
    Block chestLoc1 = world.getBlockAt(-16, 72, 20);
    Block chestLoc2 = world.getBlockAt(-18, 72, 22);
    Block chestLoc3 = world.getBlockAt(-20, 72, 22);
    Block chestLoc4 = world.getBlockAt(-22, 72, 20);
    Block chestLoc5 = world.getBlockAt(-22, 72, 18);
    Block chestLoc6 = world.getBlockAt(-20, 72, 16);
    Block chestLoc7 = world.getBlockAt(-18, 72, 16);
    Block chestLoc8 = world.getBlockAt(-16, 72, 18);
    Location resetLocation = new Location(world, -23.5D, 72.0D, 23.5D);
    this._treasureLocations.add(new TreasureLocation(this, this._inventoryManager, clientManager, donationManager, chestBlock, new Block[] { chestLoc1, chestLoc2, chestLoc3, chestLoc4, chestLoc5, chestLoc6, chestLoc7, chestLoc8 }, resetLocation, this._hologramManager));
    for (TreasureLocation treasureLocation : this._treasureLocations) {
      this._plugin.getServer().getPluginManager().registerEvents(treasureLocation, this._plugin);
    }
  }
  
  public void disable()
  {
    for (TreasureLocation treasureLocation : this._treasureLocations) {
      treasureLocation.cleanup();
    }
  }
  
  public Reward[] getRewards(Player player, RewardType rewardType)
  {
    return this._rewardManager.getRewards(player, rewardType);
  }
  
  public boolean isOpening(Player player)
  {
    for (TreasureLocation treasureLocation : this._treasureLocations)
    {
      Treasure treasure = treasureLocation.getCurrentTreasure();
      if (treasure != null) {
        if (treasure.getPlayer().equals(player)) {
          return true;
        }
      }
    }
    return false;
  }
  
  public BlockRestore getBlockRestore()
  {
    return this._blockRestore;
  }
}
