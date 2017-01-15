package mineplex.core.cosmetic.ui.page;

import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.cosmetic.ui.button.CloseButton;
import mineplex.core.cosmetic.ui.button.SelectTagButton;
import mineplex.core.donation.DonationManager;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.pet.Pet;
import mineplex.core.pet.PetClient;
import mineplex.core.pet.PetExtra;
import mineplex.core.pet.PetManager;
import mineplex.core.pet.repository.PetRepository;
import mineplex.core.pet.repository.token.PetChangeToken;
import mineplex.core.pet.repository.token.PetToken;
import mineplex.core.shop.page.ConfirmationPage;
import mineplex.core.shop.page.ShopPageBase;
import net.minecraft.server.v1_7_R4.IInventory;
import net.minecraft.server.v1_7_R4.ItemStack;
import net.minecraft.server.v1_7_R4.Items;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class PetTagPage
  extends ShopPageBase<CosmeticManager, CosmeticShop>
{
  private String _tagName = "Pet Tag";
  private Pet _pet;
  private boolean _petPurchase;
  
  public PetTagPage(CosmeticManager plugin, CosmeticShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player, Pet pet, boolean petPurchase)
  {
    super(plugin, shop, clientManager, donationManager, name, player, 3);
    
    this._pet = pet;
    this._petPurchase = petPurchase;
    
    buildPage();
    
    getPlayer().setLevel(5);
  }
  
  protected void buildPage()
  {
    this.inventory.setItem(0, new ItemStack(Items.NAME_TAG));
    
    getButtonMap().put(Integer.valueOf(0), new CloseButton());
    getButtonMap().put(Integer.valueOf(1), new CloseButton());
    getButtonMap().put(Integer.valueOf(2), new SelectTagButton(this));
  }
  
  public void playerClosed()
  {
    super.playerClosed();
    
    getPlayer().setLevel(0);
  }
  
  public void SelectTag()
  {
    this._tagName = ChatColor.stripColor(this._tagName);
    this._tagName = this._tagName.replaceAll("[^A-Za-z0-9]", "");
    if (this._tagName.length() > 16)
    {
      UtilPlayer.message(getPlayer(), F.main(((CosmeticManager)getPlugin()).getName(), ChatColor.RED + "Pet name cannot be longer than 16 characters."));
      playDenySound(getPlayer());
      
      ((CosmeticShop)getShop()).openPageForPlayer(getPlayer(), new PetPage((CosmeticManager)getPlugin(), (CosmeticShop)getShop(), getClientManager(), getDonationManager(), "Pets", getPlayer()));
      return;
    }
    PetExtra tag = new PetExtra("Rename " + this._pet.GetName() + " to " + this._tagName, Material.NAME_TAG, 100);
    
    this._pet.setDisplayName(C.cGreen + "Purchase " + this._tagName);
    
    ((CosmeticShop)getShop()).openPageForPlayer(getPlayer(), new ConfirmationPage(getPlugin(), getShop(), getClientManager(), getDonationManager(), new Runnable()
    {
      public void run()
      {
        PetChangeToken token = new PetChangeToken();
        token.Name = PetTagPage.this.getPlayer().getName();
        token.PetType = PetTagPage.this._pet.GetPetType().toString();
        token.PetName = PetTagPage.this._tagName;
        
        PetToken petToken = new PetToken();
        petToken.PetType = token.PetType;
        if (PetTagPage.this._petPurchase)
        {
          ((CosmeticManager)PetTagPage.this.getPlugin()).getPetManager().GetRepository().AddPet(token);
          ((CosmeticManager)PetTagPage.this.getPlugin()).getPetManager().addPetOwnerToQueue(PetTagPage.this.getPlayer().getName(), PetTagPage.this._pet.GetPetType());
        }
        else
        {
          ((CosmeticManager)PetTagPage.this.getPlugin()).getPetManager().GetRepository().UpdatePet(token);
          ((CosmeticManager)PetTagPage.this.getPlugin()).getPetManager().addRenamePetToQueue(PetTagPage.this.getPlayer().getName(), token.PetName);
        }
        ((PetClient)((CosmeticManager)PetTagPage.this.getPlugin()).getPetManager().Get(PetTagPage.this.getPlayer())).GetPets().put(PetTagPage.this._pet.GetPetType(), token.PetName);
        
        ((CosmeticManager)PetTagPage.this.getPlugin()).getInventoryManager().addItemToInventory(null, PetTagPage.this.getPlayer(), "Pet", PetTagPage.this._pet.GetPetType().toString(), 1);
        ((CosmeticShop)PetTagPage.this.getShop()).openPageForPlayer(PetTagPage.this.getPlayer(), new Menu((CosmeticManager)PetTagPage.this.getPlugin(), (CosmeticShop)PetTagPage.this.getShop(), PetTagPage.this.getClientManager(), PetTagPage.this.getDonationManager(), PetTagPage.this.getPlayer()));
      }
    }, null, this._petPurchase ? this._pet : tag, CurrencyType.Coins, 
    
      getPlayer()));
  }
  
  public void SetTagName(String tagName)
  {
    this._tagName = tagName;
  }
}
