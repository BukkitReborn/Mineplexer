package mineplex.core.cosmetic.ui.button;

import mineplex.core.mount.Mount;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.page.ShopPageBase;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class DeactivateMountButton
  implements IButton
{
  private Mount<?> _mount;
  private ShopPageBase<?, ?> _page;
  
  public DeactivateMountButton(Mount<?> mount, ShopPageBase<?, ?> page)
  {
    this._mount = mount;
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._page.playAcceptSound(player);
    this._mount.Disable(player);
    this._page.refresh();
  }
}
