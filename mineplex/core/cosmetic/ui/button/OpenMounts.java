package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.cosmetic.ui.page.Menu;
import mineplex.core.cosmetic.ui.page.MountPage;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class OpenMounts
  implements IButton
{
  private Menu _page;
  
  public OpenMounts(Menu page)
  {
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    ((CosmeticShop)this._page.getShop()).openPageForPlayer(player, new MountPage((CosmeticManager)this._page.getPlugin(), (CosmeticShop)this._page.getShop(), this._page.getClientManager(), this._page.getDonationManager(), "Mounts", player));
  }
}
