package mineplex.core.cosmetic.ui.button;

import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.cosmetic.ui.page.Menu;
import mineplex.core.cosmetic.ui.page.PetPage;
import mineplex.core.pet.Pet;
import mineplex.core.pet.PetManager;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class ActivatePetButton
  implements IButton
{
  private Pet _pet;
  private PetPage _page;
  
  public ActivatePetButton(Pet pet, PetPage page)
  {
    this._pet = pet;
    this._page = page;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._page.playAcceptSound(player);
    ((CosmeticManager)this._page.getPlugin()).getPetManager().AddPetOwner(player, this._pet.GetPetType(), player.getLocation());
    ((CosmeticShop)this._page.getShop()).openPageForPlayer(player, new Menu((CosmeticManager)this._page.getPlugin(), (CosmeticShop)this._page.getShop(), this._page.getClientManager(), this._page.getDonationManager(), player));
  }
}
