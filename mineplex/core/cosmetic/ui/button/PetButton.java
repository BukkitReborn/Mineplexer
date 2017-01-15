package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.ui.page.PetPage;
import mineplex.core.pet.Pet;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class PetButton
  implements IButton
{
  private Pet _pet;
  private PetPage _page;
  
  public PetButton(Pet pet, PetPage page)
  {
    this._pet = pet;
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._page.purchasePet(player, this._pet);
  }
}
