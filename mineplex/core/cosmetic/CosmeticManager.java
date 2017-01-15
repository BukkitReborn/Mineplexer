package mineplex.core.cosmetic;

import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilGear;
import mineplex.core.common.util.UtilInv;
import mineplex.core.common.util.UtilServer;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.donation.DonationManager;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.mount.MountManager;
import mineplex.core.pet.PetManager;
import mineplex.core.treasure.TreasureManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class CosmeticManager
  extends MiniPlugin
{
  private InventoryManager _inventoryManager;
  private GadgetManager _gadgetManager;
  private MountManager _mountManager;
  private PetManager _petManager;
  private TreasureManager _treasureManager;
  private CosmeticShop _shop;
  private boolean _showInterface = true;
  private int _interfaceSlot = 4;
  
  public CosmeticManager(JavaPlugin plugin, CoreClientManager clientManager, DonationManager donationManager, InventoryManager inventoryManager, GadgetManager gadgetManager, MountManager mountManager, PetManager petManager, TreasureManager treasureManager)
  {
    super("Cosmetic Manager", plugin);
    
    this._inventoryManager = inventoryManager;
    this._gadgetManager = gadgetManager;
    this._mountManager = mountManager;
    this._petManager = petManager;
    this._treasureManager = treasureManager;
    
    this._shop = new CosmeticShop(this, clientManager, donationManager, this._moduleName);
  }
  
  public void showInterface(boolean showInterface)
  {
    boolean changed = this._showInterface == showInterface;
    
    this._showInterface = showInterface;
    if (changed) {
      for (Player player : Bukkit.getOnlinePlayers()) {
        if (this._showInterface) {
          player.getInventory().setItem(this._interfaceSlot, ItemStackFactory.Instance.CreateStack(Material.CHEST, (byte)0, 1, ChatColor.RESET + C.cGreen + "Cosmetic Menu"));
        } else {
          player.getInventory().setItem(this._interfaceSlot, null);
        }
      }
    }
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    if (!this._showInterface) {
      return;
    }
    giveInterfaceItem(event.getPlayer());
  }
  
  public void giveInterfaceItem(Player player)
  {
    if (!UtilGear.isMat(player.getInventory().getItem(this._interfaceSlot), Material.CHEST))
    {
      player.getInventory().setItem(this._interfaceSlot, ItemStackFactory.Instance.CreateStack(Material.CHEST, (byte)0, 1, ChatColor.RESET + C.cGreen + "Cosmetic Menu"));
      
      this._gadgetManager.redisplayActiveItem(player);
      
      UtilInv.Update(player);
    }
  }
  
  @EventHandler
  public void orderThatChest(final PlayerDropItemEvent event)
  {
    if (!this._showInterface) {
      return;
    }
    if (event.getItemDrop().getItemStack().getType() == Material.CHEST) {
      Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), new Runnable()
      {
        public void run()
        {
          if (event.getPlayer().isOnline())
          {
            event.getPlayer().getInventory().remove(Material.CHEST);
            event.getPlayer().getInventory().setItem(CosmeticManager.this._interfaceSlot, ItemStackFactory.Instance.CreateStack(Material.CHEST, (byte)0, 1, ChatColor.RESET + C.cGreen + "Inventory Menu"));
            event.getPlayer().updateInventory();
          }
        }
      });
    }
  }
  
  @EventHandler
  public void openShop(PlayerInteractEvent event)
  {
    if (!this._showInterface) {
      return;
    }
    if ((event.hasItem()) && (event.getItem().getType() == Material.CHEST))
    {
      event.setCancelled(true);
      
      this._shop.attemptShopOpen(event.getPlayer());
    }
  }
  
  public GadgetManager getGadgetManager()
  {
    return this._gadgetManager;
  }
  
  public MountManager getMountManager()
  {
    return this._mountManager;
  }
  
  public PetManager getPetManager()
  {
    return this._petManager;
  }
  
  public InventoryManager getInventoryManager()
  {
    return this._inventoryManager;
  }
  
  public void setInterfaceSlot(int i)
  {
    this._interfaceSlot = i;
    
    this._gadgetManager.setActiveItemSlot(i - 1);
  }
  
  public void setActive(boolean showInterface)
  {
    this._showInterface = showInterface;
    if (!showInterface)
    {
      Player[] arrayOfPlayer;
      int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
      for (int i = 0; i < j; i++)
      {
        Player player = arrayOfPlayer[i];
        if (player.getOpenInventory().getTopInventory().getHolder() != player) {
          player.closeInventory();
        }
      }
    }
  }
  
  public void disableItemsForGame()
  {
    this._gadgetManager.DisableAll();
    this._mountManager.DisableAll();
    this._petManager.DisableAll();
  }
  
  public void setHideParticles(boolean b)
  {
    this._gadgetManager.setHideParticles(b);
  }
  
  public boolean isShowingInterface()
  {
    return this._showInterface;
  }
  
  public TreasureManager getTreasureManager()
  {
    return this._treasureManager;
  }
}
