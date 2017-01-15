package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.ui.page.Menu;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class OpenCostumes
  implements IButton
{
  private Menu _menu;
  
  public OpenCostumes(Menu menu)
  {
    this._menu = menu;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._menu.openCostumes(player);
  }
}
