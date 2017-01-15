package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.page.PetPage;
import mineplex.core.pet.Pet;
import mineplex.core.pet.PetManager;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class RenamePetButton
  implements IButton
{
  private PetPage _page;
  
  public RenamePetButton(PetPage page)
  {
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._page.playAcceptSound(player);
    Creature currentPet = ((CosmeticManager)this._page.getPlugin()).getPetManager().getActivePet(player.getName());
    this._page.renamePet(player, new Pet(currentPet.getCustomName(), currentPet.getType(), 1), false);
  }
}
