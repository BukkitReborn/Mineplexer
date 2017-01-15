package mineplex.core.cosmetic.ui.button;

import mineplex.core.common.CurrencyType;
import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.cosmetic.ui.page.MountPage;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.mount.Mount;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.page.ConfirmationPage;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class MountButton
  implements IButton
{
  private Mount<?> _mount;
  private MountPage _page;
  
  public MountButton(Mount<?> mount, MountPage page)
  {
    this._mount = mount;
    this._page = page;
  }
  
  public void onClick(final Player player, ClickType clickType)
  {
    ((CosmeticShop)this._page.getShop()).openPageForPlayer(player, new ConfirmationPage(this._page.getPlugin(), this._page.getShop(), this._page.getClientManager(), this._page.getDonationManager(), new Runnable()
    {
      public void run()
      {
        ((CosmeticManager)MountButton.this._page.getPlugin()).getInventoryManager().addItemToInventory(null, player, "Mount", MountButton.this._mount.GetName(), 1);
        MountButton.this._page.refresh();
      }
    }, this._page, this._mount, CurrencyType.Coins, player));
  }
}
