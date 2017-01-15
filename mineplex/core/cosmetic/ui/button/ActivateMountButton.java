package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.cosmetic.ui.page.Menu;
import mineplex.core.cosmetic.ui.page.MountPage;
import mineplex.core.mount.Mount;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ActivateMountButton
  implements IButton
{
  private Mount<?> _mount;
  private MountPage _page;
  
  public ActivateMountButton(Mount<?> mount, MountPage page)
  {
    this._mount = mount;
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._page.playAcceptSound(player);
    this._mount.Enable(player);
    ((CosmeticShop)this._page.getShop()).openPageForPlayer(player, new Menu((CosmeticManager)this._page.getPlugin(), (CosmeticShop)this._page.getShop(), this._page.getClientManager(), this._page.getDonationManager(), player));
  }
}
