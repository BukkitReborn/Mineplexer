package mineplex.core.shop.page;

import java.util.List;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilInv;
import mineplex.core.donation.DonationManager;
import mineplex.core.shop.ShopBase;
import mineplex.core.shop.item.IButton;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IInventory;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class ShopPageBase<PluginType extends MiniPlugin, ShopType extends ShopBase<PluginType>>
  extends CraftInventoryCustom
  implements Listener
{
  private PluginType _plugin;
  private CoreClientManager _clientManager;
  private DonationManager _donationManager;
  private ShopType _shop;
  private Player _player;
  private CoreClient _client;
  private CurrencyType _currencyType;
  private NautHashMap<Integer, IButton> _buttonMap;
  private boolean _showCurrency = false;
  private int _currencySlot = 4;
  
  public ShopPageBase(PluginType plugin, ShopType shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player)
  {
    this(plugin, shop, clientManager, donationManager, name, player, 54);
  }
  
  public ShopPageBase(PluginType plugin, ShopType shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player, int slots)
  {
    super(null, slots, name);
    
    this._plugin = plugin;
    this._clientManager = clientManager;
    this._donationManager = donationManager;
    this._shop = shop;
    this._player = player;
    this._buttonMap = new NautHashMap();
    
    this._client = this._clientManager.Get(player);
    if (shop.getAvailableCurrencyTypes().size() > 0) {
      this._currencyType = ((CurrencyType)shop.getAvailableCurrencyTypes().get(0));
    }
  }
  
  protected void changeCurrency(Player player)
  {
    playAcceptSound(player);
    
    int currentIndex = this._shop.getAvailableCurrencyTypes().indexOf(this._currencyType);
    if (currentIndex + 1 < this._shop.getAvailableCurrencyTypes().size()) {
      this._currencyType = ((CurrencyType)this._shop.getAvailableCurrencyTypes().get(currentIndex + 1));
    } else {
      this._currencyType = ((CurrencyType)this._shop.getAvailableCurrencyTypes().get(0));
    }
  }
  
  protected abstract void buildPage();
  
  protected void addItem(int slot, org.bukkit.inventory.ItemStack item)
  {
    if (slot > this.inventory.getSize() - 1) {
      this._player.getInventory().setItem(getPlayerSlot(slot), item);
    } else {
      setItem(slot, item);
    }
  }
  
  protected void addItemFakeCount(int slot, org.bukkit.inventory.ItemStack item, int fakeCount)
  {
    net.minecraft.server.v1_7_R4.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
    nmsStack.count = fakeCount;
    if (slot > this.inventory.getSize() - 1) {
      ((CraftPlayer)this._player).getHandle().inventory.setItem(getPlayerSlot(slot), nmsStack);
    } else {
      getInventory().setItem(slot, nmsStack);
    }
  }
  
  protected int getPlayerSlot(int slot)
  {
    return slot >= this.inventory.getSize() + 27 ? slot - (this.inventory.getSize() + 27) : slot - (this.inventory.getSize() - 9);
  }
  
  protected void addButton(int slot, org.bukkit.inventory.ItemStack item, IButton button)
  {
    addItem(slot, item);
    
    this._buttonMap.put(Integer.valueOf(slot), button);
  }
  
  protected void addButtonFakeCount(int slot, org.bukkit.inventory.ItemStack item, IButton button, int fakeItemCount)
  {
    addItemFakeCount(slot, item, fakeItemCount);
    
    this._buttonMap.put(Integer.valueOf(slot), button);
  }
  
  protected void addGlow(int slot)
  {
    UtilInv.addDullEnchantment(getItem(slot));
  }
  
  protected void removeButton(int slot)
  {
    getInventory().setItem(slot, null);
    this._buttonMap.remove(Integer.valueOf(slot));
  }
  
  public void playerClicked(InventoryClickEvent event)
  {
    if (this._buttonMap.containsKey(Integer.valueOf(event.getRawSlot()))) {
      ((IButton)this._buttonMap.get(Integer.valueOf(event.getRawSlot()))).onClick(this._player, event.getClick());
    } else if (event.getRawSlot() != 64537) {
      if ((event.getInventory().getTitle() == this.inventory.getInventoryName()) && ((this.inventory.getSize() <= event.getSlot()) || (this.inventory.getItem(event.getSlot()) != null))) {
        playDenySound(this._player);
      } else if ((event.getInventory() == this._player.getInventory()) && (this._player.getInventory().getItem(event.getSlot()) != null)) {
        playDenySound(this._player);
      }
    }
  }
  
  public void playerOpened() {}
  
  public void playerClosed()
  {
    this.inventory.onClose((CraftPlayer)this._player);
  }
  
  public void playAcceptSound(Player player)
  {
    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.6F);
  }
  
  public void playRemoveSound(Player player)
  {
    player.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 0.6F);
  }
  
  public void playDenySound(Player player)
  {
    player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1.0F, 0.6F);
  }
  
  public void dispose()
  {
    this._player = null;
    this._client = null;
    this._shop = null;
    this._plugin = null;
  }
  
  public void refresh()
  {
    clearPage();
    buildPage();
  }
  
  public void clearPage()
  {
    clear();
    this._buttonMap.clear();
  }
  
  public void setItem(int column, int row, org.bukkit.inventory.ItemStack itemStack)
  {
    setItem(column + row * 9, itemStack);
  }
  
  public ShopType getShop()
  {
    return this._shop;
  }
  
  public PluginType getPlugin()
  {
    return this._plugin;
  }
  
  public CoreClientManager getClientManager()
  {
    return this._clientManager;
  }
  
  public DonationManager getDonationManager()
  {
    return this._donationManager;
  }
  
  protected Player getPlayer()
  {
    return this._player;
  }
  
  protected CoreClient getClient()
  {
    return this._client;
  }
  
  protected CurrencyType getCurrencyType()
  {
    return this._currencyType;
  }
  
  protected void setCurrencyType(CurrencyType type)
  {
    this._currencyType = type;
  }
  
  protected NautHashMap<Integer, IButton> getButtonMap()
  {
    return this._buttonMap;
  }
  
  protected boolean shouldShowCurrency()
  {
    return this._showCurrency;
  }
  
  protected int getCurrencySlot()
  {
    return this._currencySlot;
  }
}
