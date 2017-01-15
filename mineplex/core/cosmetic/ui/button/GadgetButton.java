package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.ui.page.GadgetPage;
import mineplex.core.gadget.types.Gadget;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class GadgetButton
  implements IButton
{
  private Gadget _gadget;
  private GadgetPage _page;
  
  public GadgetButton(Gadget gadget, GadgetPage page)
  {
    this._gadget = gadget;
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._page.purchaseGadget(player, this._gadget);
  }
}
