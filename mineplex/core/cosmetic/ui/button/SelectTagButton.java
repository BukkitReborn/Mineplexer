package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.ui.page.PetTagPage;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class SelectTagButton
  implements IButton
{
  private PetTagPage _page;
  
  public SelectTagButton(PetTagPage page)
  {
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._page.SelectTag();
  }
}
