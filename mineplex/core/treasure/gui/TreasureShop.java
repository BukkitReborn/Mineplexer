package mineplex.core.treasure.gui;

import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.donation.DonationManager;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.shop.ShopBase;
import mineplex.core.shop.page.ShopPageBase;
import mineplex.core.treasure.TreasureLocation;
import mineplex.core.treasure.TreasureManager;
import org.bukkit.entity.Player;

public class TreasureShop
  extends ShopBase<TreasureManager>
{
  private TreasureLocation _treasureLocation;
  private InventoryManager _inventoryManager;
  
  public TreasureShop(TreasureManager plugin, InventoryManager inventoryManager, CoreClientManager clientManager, DonationManager donationManager, TreasureLocation treasureLocation)
  {
    super(plugin, clientManager, donationManager, "Treasure Shop", new CurrencyType[0]);
    this._treasureLocation = treasureLocation;
    this._inventoryManager = inventoryManager;
  }
  
  protected ShopPageBase<TreasureManager, ? extends ShopBase<TreasureManager>> buildPagesFor(Player player)
  {
    return new TreasurePage((TreasureManager)getPlugin(), this, this._treasureLocation, getClientManager(), getDonationManager(), this._inventoryManager, player);
  }
}
