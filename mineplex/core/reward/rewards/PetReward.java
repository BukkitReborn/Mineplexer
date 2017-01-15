package mineplex.core.reward.rewards;

import mineplex.core.common.util.NautHashMap;
import mineplex.core.donation.DonationManager;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.pet.PetClient;
import mineplex.core.pet.PetManager;
import mineplex.core.pet.repository.PetRepository;
import mineplex.core.pet.repository.token.PetChangeToken;
import mineplex.core.pet.repository.token.PetToken;
import mineplex.core.reward.RewardData;
import mineplex.core.reward.RewardRarity;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PetReward
  extends UnknownPackageReward
{
  private InventoryManager _inventoryManager;
  private PetManager _petManager;
  private EntityType _petEntity;
  
  public PetReward(PetManager petManager, InventoryManager inventoryManager, DonationManager donationManager, String name, String packageName, EntityType petEntity, RewardRarity rarity, int weight)
  {
    super(donationManager, name, packageName, new ItemStack(Material.MONSTER_EGG, 1, petEntity.getTypeId()), rarity, weight);
    
    this._petManager = petManager;
    this._inventoryManager = inventoryManager;
    this._petEntity = petEntity;
  }
  
  protected RewardData giveRewardCustom(Player player)
  {
    PetChangeToken token = new PetChangeToken();
    token.Name = player.getName();
    token.PetType = this._petEntity.toString();
    token.PetName = getPackageName();
    
    PetToken petToken = new PetToken();
    petToken.PetType = token.PetType;
    
    this._petManager.GetRepository().AddPet(token);
    
    ((PetClient)this._petManager.Get(player)).GetPets().put(this._petEntity, token.PetName);
    
    this._inventoryManager.addItemToInventory(player, "Pet", this._petEntity.toString(), 1);
    
    return super.giveRewardCustom(player);
  }
}
