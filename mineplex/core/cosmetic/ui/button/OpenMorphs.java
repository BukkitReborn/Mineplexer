package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.cosmetic.ui.page.Menu;
import mineplex.core.cosmetic.ui.page.MorphPage;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class OpenMorphs
  implements IButton
{
  private Menu _page;
  
  public OpenMorphs(Menu page)
  {
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    ((CosmeticShop)this._page.getShop()).openPageForPlayer(player, new MorphPage((CosmeticManager)this._page.getPlugin(), (CosmeticShop)this._page.getShop(), this._page.getClientManager(), this._page.getDonationManager(), "Morphs", player));
  }
}
