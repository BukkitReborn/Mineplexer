package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.ui.page.Menu;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class OpenMusic
  implements IButton
{
  private Menu _menu;
  
  public OpenMusic(Menu menu)
  {
    this._menu = menu;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._menu.openMusic(player);
  }
}
