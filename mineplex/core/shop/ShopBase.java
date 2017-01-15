package mineplex.core.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.donation.DonationManager;
import mineplex.core.npc.event.NpcDamageByEntityEvent;
import mineplex.core.npc.event.NpcInteractEntityEvent;
import mineplex.core.shop.page.ShopPageBase;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public abstract class ShopBase<PluginType extends MiniPlugin>
  implements Listener
{
  private NautHashMap<String, Long> _errorThrottling;
  private NautHashMap<String, Long> _purchaseBlock;
  private List<CurrencyType> _availableCurrencyTypes;
  private PluginType _plugin;
  private CoreClientManager _clientManager;
  private DonationManager _donationManager;
  private String _name;
  private NautHashMap<String, ShopPageBase<PluginType, ? extends ShopBase<PluginType>>> _playerPageMap;
  private HashSet<String> _openedShop = new HashSet();
  
  public ShopBase(PluginType plugin, CoreClientManager clientManager, DonationManager donationManager, String name, CurrencyType... currencyTypes)
  {
    this._plugin = plugin;
    this._clientManager = clientManager;
    this._donationManager = donationManager;
    this._name = name;
    this._playerPageMap = new NautHashMap();
    this._errorThrottling = new NautHashMap();
    this._purchaseBlock = new NautHashMap();
    this._availableCurrencyTypes = new ArrayList();
    this._availableCurrencyTypes.addAll(Arrays.asList(currencyTypes));
    this._plugin.registerEvents(this);
  }
  
  public List<CurrencyType> getAvailableCurrencyTypes()
  {
    return this._availableCurrencyTypes;
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onPlayerDamageEntity(NpcDamageByEntityEvent event)
  {
    if (((event.getDamager() instanceof Player)) && (attemptShopOpen((Player)event.getDamager(), event.getNpc()))) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onPlayerInteractEntity(NpcInteractEntityEvent event)
  {
    if (attemptShopOpen(event.getPlayer(), event.getNpc())) {
      event.setCancelled(true);
    }
  }
  
  private boolean attemptShopOpen(Player player, LivingEntity entity)
  {
    if ((!this._openedShop.contains(player.getName())) && (entity.isCustomNameVisible()) && (entity.getCustomName() != null) && (ChatColor.stripColor(entity.getCustomName()).equalsIgnoreCase(ChatColor.stripColor(this._name))))
    {
      if (!canOpenShop(player)) {
        return false;
      }
      this._openedShop.add(player.getName());
      openShopForPlayer(player);
      if (!this._playerPageMap.containsKey(player.getName())) {
        this._playerPageMap.put(player.getName(), buildPagesFor(player));
      }
      openPageForPlayer(player, getOpeningPageForPlayer(player));
      return true;
    }
    return false;
  }
  
  public boolean attemptShopOpen(Player player)
  {
    if (!this._openedShop.contains(player.getName()))
    {
      if (!canOpenShop(player)) {
        return false;
      }
      this._openedShop.add(player.getName());
      openShopForPlayer(player);
      if (!this._playerPageMap.containsKey(player.getName())) {
        this._playerPageMap.put(player.getName(), buildPagesFor(player));
      }
      openPageForPlayer(player, getOpeningPageForPlayer(player));
      return true;
    }
    return false;
  }
  
  protected ShopPageBase<PluginType, ? extends ShopBase<PluginType>> getOpeningPageForPlayer(Player player)
  {
    return (ShopPageBase)this._playerPageMap.get(player.getName());
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event)
  {
    if ((this._playerPageMap.containsKey(event.getWhoClicked().getName())) && (((ShopPageBase)this._playerPageMap.get(event.getWhoClicked().getName())).getName().equalsIgnoreCase(event.getInventory().getName())))
    {
      ((ShopPageBase)this._playerPageMap.get(event.getWhoClicked().getName())).playerClicked(event);
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent event)
  {
    if ((this._playerPageMap.containsKey(event.getPlayer().getName())) && (((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).getTitle() != null) && (((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).getTitle().equalsIgnoreCase(event.getInventory().getTitle())))
    {
      ((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).playerClosed();
      ((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).dispose();
      this._playerPageMap.remove(event.getPlayer().getName());
      closeShopForPlayer((Player)event.getPlayer());
      this._openedShop.remove(event.getPlayer().getName());
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onInventoryOpen(InventoryOpenEvent event)
  {
    if (!event.isCancelled()) {
      return;
    }
    if ((this._playerPageMap.containsKey(event.getPlayer().getName())) && (((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).getTitle() != null) && (((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).getTitle().equalsIgnoreCase(event.getInventory().getTitle())))
    {
      ((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).playerClosed();
      ((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).dispose();
      this._playerPageMap.remove(event.getPlayer().getName());
      closeShopForPlayer((Player)event.getPlayer());
      this._openedShop.remove(event.getPlayer().getName());
    }
  }
  
  protected boolean canOpenShop(Player player)
  {
    return true;
  }
  
  protected void openShopForPlayer(Player player) {}
  
  protected void closeShopForPlayer(Player player) {}
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    if (this._playerPageMap.containsKey(event.getPlayer().getName()))
    {
      ((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).playerClosed();
      ((ShopPageBase)this._playerPageMap.get(event.getPlayer().getName())).dispose();
      event.getPlayer().closeInventory();
      closeShopForPlayer(event.getPlayer());
      this._playerPageMap.remove(event.getPlayer().getName());
      this._openedShop.remove(event.getPlayer().getName());
    }
  }
  
  public void openPageForPlayer(Player player, ShopPageBase<PluginType, ? extends ShopBase<PluginType>> page)
  {
    if (this._playerPageMap.containsKey(player.getName())) {
      ((ShopPageBase)this._playerPageMap.get(player.getName())).playerClosed();
    }
    setCurrentPageForPlayer(player, page);
    EntityPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
    if (nmsPlayer.activeContainer != nmsPlayer.defaultContainer)
    {
      CraftEventFactory.handleInventoryCloseEvent(nmsPlayer);
      nmsPlayer.m();
    }
    player.openInventory(page);
  }
  
  public void setCurrentPageForPlayer(Player player, ShopPageBase<PluginType, ? extends ShopBase<PluginType>> page)
  {
    this._playerPageMap.put(player.getName(), page);
  }
  
  public void addPlayerProcessError(Player player)
  {
    if ((this._errorThrottling.containsKey(player.getName())) && (System.currentTimeMillis() - ((Long)this._errorThrottling.get(player.getName())).longValue() <= 5000L)) {
      this._purchaseBlock.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
    }
    this._errorThrottling.put(player.getName(), Long.valueOf(System.currentTimeMillis()));
  }
  
  public boolean canPlayerAttemptPurchase(Player player)
  {
    return (!this._purchaseBlock.containsKey(player.getName())) || (System.currentTimeMillis() - ((Long)this._purchaseBlock.get(player.getName())).longValue() > 10000L);
  }
  
  public NautHashMap<String, ShopPageBase<PluginType, ? extends ShopBase<PluginType>>> getPageMap()
  {
    return this._playerPageMap;
  }
  
  protected abstract ShopPageBase<PluginType, ? extends ShopBase<PluginType>> buildPagesFor(Player paramPlayer);
  
  public boolean isPlayerInShop(Player player)
  {
    return this._playerPageMap.containsKey(player.getName());
  }
  
  protected PluginType getPlugin()
  {
    return this._plugin;
  }
  
  protected CoreClientManager getClientManager()
  {
    return this._clientManager;
  }
  
  protected DonationManager getDonationManager()
  {
    return this._donationManager;
  }
  
  protected String getName()
  {
    return this._name;
  }
  
  protected NautHashMap<String, ShopPageBase<PluginType, ? extends ShopBase<PluginType>>> getPlayerPageMap()
  {
    return this._playerPageMap;
  }
  
  protected HashSet<String> getOpenedShop()
  {
    return this._openedShop;
  }
}
