package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.ui.page.GadgetPage;
import mineplex.core.gadget.types.Gadget;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class DeactivateGadgetButton
  implements IButton
{
  private Gadget _gadget;
  private GadgetPage _page;
  
  public DeactivateGadgetButton(Gadget gadget, GadgetPage page)
  {
    this._gadget = gadget;
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    if (clickType.isLeftClick()) {
      this._page.deactivateGadget(player, this._gadget);
    } else if (clickType.isRightClick()) {
      this._page.handleRightClick(player, this._gadget);
    }
  }
}
