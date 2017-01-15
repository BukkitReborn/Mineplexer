package mineplex.core.pet;

import java.util.Collection;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.pet.repository.PetRepository;
import mineplex.core.pet.types.Elf;
import mineplex.core.pet.types.Pumpkin;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class PetFactory
{
  private PetRepository _repository;
  private NautHashMap<EntityType, Pet> _pets;
  private NautHashMap<Material, PetExtra> _petExtras;
  
  public PetFactory(PetRepository repository)
  {
    this._repository = repository;
    this._pets = new NautHashMap();
    this._petExtras = new NautHashMap();
    
    CreatePets();
    CreatePetExtras();
  }
  
  private void CreatePets()
  {
    this._pets.put(EntityType.ZOMBIE, new Pumpkin());
    this._pets.put(EntityType.VILLAGER, new Elf());
    this._pets.put(EntityType.PIG, new Pet("Pig", EntityType.PIG, 5000));
    this._pets.put(EntityType.SHEEP, new Pet("Sheep", EntityType.SHEEP, 3000));
    this._pets.put(EntityType.COW, new Pet("Cow", EntityType.COW, 2000));
    this._pets.put(EntityType.CHICKEN, new Pet("Chicken", EntityType.CHICKEN, 7000));
    this._pets.put(EntityType.WOLF, new Pet("Dog", EntityType.WOLF, 8000));
    this._pets.put(EntityType.OCELOT, new Pet("Cat", EntityType.OCELOT, 6000));
    this._pets.put(EntityType.MUSHROOM_COW, new Pet("Mooshroom", EntityType.MUSHROOM_COW, 5000));
    this._pets.put(EntityType.WITHER, new Pet("Widder", EntityType.WITHER, -1));
  }
  
  private void CreatePetExtras()
  {
    this._petExtras.put(Material.SIGN, new PetExtra("Name Tag", Material.NAME_TAG, 100));
  }
  
  public Collection<Pet> GetPets()
  {
    return this._pets.values();
  }
  
  public Collection<PetExtra> GetPetExtras()
  {
    return this._petExtras.values();
  }
  
  public Collection<PetExtra> GetPetExtraBySalesId(int salesId)
  {
    return this._petExtras.values();
  }
}
