package mineplex.core.cosmetic.ui.button;

import mineplex.core.pet.PetManager;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.page.ShopPageBase;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class DeactivatePetButton
  implements IButton
{
  private ShopPageBase<?, ?> _page;
  private PetManager _petManager;
  
  public DeactivatePetButton(ShopPageBase<?, ?> page, PetManager petManager)
  {
    this._page = page;
    this._petManager = petManager;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._page.playAcceptSound(player);
    this._petManager.RemovePet(player, true);
    this._page.refresh();
  }
}
