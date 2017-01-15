package mineplex.core.treasure;

import java.util.List;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilTextMiddle;
import mineplex.core.donation.DonationManager;
import mineplex.core.gadget.event.GadgetBlockEvent;
import mineplex.core.hologram.Hologram;
import mineplex.core.hologram.HologramManager;
import mineplex.core.inventory.ClientInventory;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.reward.Reward;
import mineplex.core.treasure.event.TreasureFinishEvent;
import mineplex.core.treasure.event.TreasureStartEvent;
import mineplex.core.treasure.gui.TreasureShop;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

public class TreasureLocation
  implements Listener
{
  private TreasureManager _treasureManager;
  private InventoryManager _inventoryManager;
  private Hologram _hologram;
  private HologramManager _hologramManager;
  private Treasure _currentTreasure;
  private Block _chestBlock;
  private byte _chestBlockData;
  private Block[] _chestSpawns;
  private TreasureShop _shop;
  private Location _resetLocation;
  
  public TreasureLocation(TreasureManager treasureManager, InventoryManager inventoryManager, CoreClientManager clientManager, DonationManager donationManager, Block chestBlock, Block[] chestSpawns, Location resetLocation, HologramManager hologramManager)
  {
    this._treasureManager = treasureManager;
    this._resetLocation = resetLocation;
    this._inventoryManager = inventoryManager;
    this._chestBlock = chestBlock;
    this._chestBlockData = this._chestBlock.getData();
    this._chestSpawns = chestSpawns;
    this._hologramManager = hologramManager;
    this._currentTreasure = null;
    this._hologram = new Hologram(this._hologramManager, chestBlock.getLocation().add(0.5D, 2.5D, 0.5D), new String[] { C.cGreen + C.Bold + "Open Treasure" });
    setHoloChestVisible(true);
    this._shop = new TreasureShop(treasureManager, this._inventoryManager, clientManager, donationManager, this);
  }
  
  @EventHandler
  public void onInteract(PlayerInteractEvent event)
  {
    if ((event.getAction() == Action.RIGHT_CLICK_BLOCK) && (event.getClickedBlock().equals(this._chestBlock)))
    {
      openShop(event.getPlayer());
      event.setCancelled(true);
    }
  }
  
  public void attemptOpenTreasure(Player player, TreasureType treasureType)
  {
    if (isTreasureInProgress())
    {
      player.sendMessage(F.main("Treasure", "Please wait for the current chest to be opened"));
      return;
    }
    if (!chargeAccount(player, treasureType))
    {
      player.sendMessage(F.main("Treasure", "You dont have any chests to open!"));
      return;
    }
    TreasureStartEvent event = new TreasureStartEvent(player);
    Bukkit.getPluginManager().callEvent(event);
    if (event.isCancelled()) {
      return;
    }
    setHoloChestVisible(false);
    if (treasureType == TreasureType.ANCIENT) {
      Bukkit.broadcastMessage(F.main("Treasure", F.name(player.getName()) + " is opening an " + treasureType.getName()));
    }
    if (treasureType == TreasureType.MYTHICAL) {
      Bukkit.broadcastMessage(F.main("Treasure", F.name(player.getName()) + " is opening a " + treasureType.getName()));
    }
    Reward[] rewards = this._treasureManager.getRewards(player, treasureType.getRewardType());
    Treasure treasure = new Treasure(player, rewards, this._chestBlock, this._chestSpawns, treasureType, this._treasureManager.getBlockRestore(), this._hologramManager);
    this._currentTreasure = treasure;
    
    UtilTextMiddle.display(treasureType.getName(), "Choose 4 Chests To Open", 20, 180, 20, new Player[] { player });
    UtilPlayer.message(player, F.main("Treasure", "Choose 4 Chests To Open"));
    
    Location teleportLocation = treasure.getCenterBlock().getLocation().add(0.5D, 0.0D, 0.5D);
    teleportLocation.setPitch(player.getLocation().getPitch());
    teleportLocation.setYaw(player.getLocation().getYaw());
    for (Entity entity : player.getNearbyEntities(3.0D, 3.0D, 3.0D)) {
      UtilAction.velocity(entity, UtilAlg.getTrajectory(entity.getLocation(), treasure.getCenterBlock().getLocation()).multiply(-1), 1.5D, true, 0.8D, 0.0D, 1.0D, true);
    }
    player.teleport(teleportLocation);
  }
  
  private boolean chargeAccount(Player player, TreasureType treasureType)
  {
    int itemCount = ((ClientInventory)this._inventoryManager.Get(player)).getItemCount(treasureType.getItemName());
    if (itemCount > 0)
    {
      this._inventoryManager.addItemToInventory(player, "Item", treasureType.getItemName(), -1);
      return true;
    }
    return false;
  }
  
  private void setHoloChestVisible(boolean visible)
  {
    if (visible)
    {
      this._hologram.start();
      this._chestBlock.setType(Material.CHEST);
      this._chestBlock.setData(this._chestBlockData);
    }
    else
    {
      this._hologram.stop();
      this._chestBlock.setType(Material.AIR);
    }
  }
  
  public void cleanup()
  {
    if (this._currentTreasure != null)
    {
      this._currentTreasure.cleanup();
      this._currentTreasure = null;
    }
  }
  
  @EventHandler
  public void update(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    if (isTreasureInProgress())
    {
      Treasure treasure = this._currentTreasure;
      
      treasure.update();
      if ((!treasure.getPlayer().isOnline()) || ((treasure.isFinished()) && (treasure.getFinishedTickCount() >= 80)))
      {
        treasure.cleanup();
        
        TreasureFinishEvent finishEvent = new TreasureFinishEvent(treasure.getPlayer(), treasure);
        Bukkit.getPluginManager().callEvent(finishEvent);
      }
    }
  }
  
  @EventHandler
  public void onTreasureFinish(TreasureFinishEvent event)
  {
    if (event.getTreasure().equals(this._currentTreasure))
    {
      Player player = this._currentTreasure.getPlayer();
      player.teleport(this._resetLocation);
      this._currentTreasure = null;
      setHoloChestVisible(true);
    }
  }
  
  @EventHandler(priority=EventPriority.HIGH)
  public void interact(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (isTreasureInProgress()) {
      if (this._currentTreasure.getPlayer().equals(player))
      {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
          this._currentTreasure.openChest(event.getClickedBlock());
        }
        event.setCancelled(true);
      }
      else if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
      {
        ChestData chestData = this._currentTreasure.getChestData(event.getClickedBlock());
        if (chestData != null) {
          event.setCancelled(true);
        }
      }
    }
  }
  
  @EventHandler
  public void inventoryOpen(InventoryOpenEvent event)
  {
    if ((event.getInventory().getTitle() != null) && (event.getInventory().getTitle().contains("Punish"))) {
      return;
    }
    if ((isTreasureInProgress()) && (event.getPlayer().equals(this._currentTreasure.getPlayer()))) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void cancelMove(PlayerMoveEvent event)
  {
    Player player = event.getPlayer();
    if (isTreasureInProgress()) {
      if (this._currentTreasure.getPlayer().equals(player))
      {
        Treasure treasure = this._currentTreasure;
        Location centerLocation = treasure.getCenterBlock().getLocation().add(0.5D, 0.5D, 0.5D);
        if (event.getTo().distanceSquared(centerLocation) > 9.0D)
        {
          Location newTo = event.getFrom();
          newTo.setPitch(event.getTo().getPitch());
          newTo.setYaw(event.getTo().getYaw());
          event.setTo(newTo);
        }
      }
      else
      {
        Location fromLocation = event.getFrom();
        Location toLocation = event.getTo();
        Location centerLocation = this._currentTreasure.getCenterBlock().getLocation().add(0.5D, 1.5D, 0.5D);
        double toDistanceFromCenter = centerLocation.distanceSquared(toLocation);
        if (toDistanceFromCenter <= 16.0D)
        {
          double fromDistanceFromCenter = centerLocation.distanceSquared(fromLocation);
          if (toDistanceFromCenter < fromDistanceFromCenter)
          {
            Location spawnLocation = new Location(player.getWorld(), 0.0D, 64.0D, 0.0D);
            UtilAction.velocity(player, UtilAlg.getTrajectory(player.getLocation(), spawnLocation).multiply(-1), 1.5D, true, 0.8D, 0.0D, 1.0D, true);
          }
        }
      }
    }
  }
  
  @EventHandler
  public void cancelVelocity(PlayerVelocityEvent event)
  {
    Player player = event.getPlayer();
    if ((isTreasureInProgress()) && (this._currentTreasure.getPlayer().equals(player))) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void preventGadgetBlockEvent(GadgetBlockEvent event)
  {
    List<Block> blocks = event.getBlocks();
    
    int x = this._chestBlock.getX();
    int y = this._chestBlock.getY();
    int z = this._chestBlock.getZ();
    for (Block block : blocks)
    {
      int dx = Math.abs(x - block.getX());
      int dy = Math.abs(y - block.getY());
      int dz = Math.abs(z - block.getZ());
      if ((dx <= 4) && (dz <= 4) && (dy <= 4))
      {
        event.setCancelled(true);
        return;
      }
    }
  }
  
  @EventHandler
  public void quit(PlayerQuitEvent event)
  {
    if ((isTreasureInProgress()) && (this._currentTreasure.getPlayer().equals(event.getPlayer()))) {
      reset();
    }
  }
  
  public boolean isTreasureInProgress()
  {
    return this._currentTreasure != null;
  }
  
  public void reset()
  {
    cleanup();
    this._chestBlock.setType(Material.CHEST);
    this._chestBlock.setData(this._chestBlockData);
    this._hologram.start();
  }
  
  public Treasure getCurrentTreasure()
  {
    return this._currentTreasure;
  }
  
  public void openShop(Player player)
  {
    this._shop.attemptShopOpen(player);
  }
}
