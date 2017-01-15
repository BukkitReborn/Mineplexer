package mineplex.core.treasure.gui;

import mineplex.core.common.CurrencyType;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.page.ConfirmationPage;
import mineplex.core.treasure.ChestPackage;
import mineplex.core.treasure.TreasureManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class BuyChestButton
  implements IButton
{
  private InventoryManager _inventoryManager;
  private TreasurePage _page;
  private String _chestName;
  private Material _chestMat;
  private int _chestCost;
  
  public BuyChestButton(Player player, InventoryManager inventoryManager, TreasurePage page, String chestName, Material chestMat, int chestCost)
  {
    this._inventoryManager = inventoryManager;
    
    this._page = page;
    
    this._chestName = chestName;
    this._chestMat = chestMat;
    this._chestCost = chestCost;
  }
  
  public void onClick(final Player player, ClickType clickType)
  {
    ((TreasureShop)this._page.getShop()).openPageForPlayer(player, new ConfirmationPage(
      (TreasureManager)this._page.getPlugin(), (TreasureShop)this._page.getShop(), this._page.getClientManager(), this._page.getDonationManager(), new Runnable()
      {
        public void run()
        {
          BuyChestButton.this._inventoryManager.addItemToInventory(player, "Item", BuyChestButton.this._chestName, 1);
          BuyChestButton.this._page.refresh();
        }
      }, this._page, new ChestPackage(this._chestName, this._chestMat, this._chestCost), CurrencyType.Coins, player));
  }
}
