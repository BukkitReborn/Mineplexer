package mineplex.core.pet;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import mineplex.core.MiniClientPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.account.event.ClientWebResponseEvent;
import mineplex.core.blockrestore.BlockRestore;
import mineplex.core.common.Rank;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.pet.repository.PetRepository;
import mineplex.core.pet.repository.token.ClientPetTokenWrapper;
import mineplex.core.pet.types.CustomWither;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import net.minecraft.server.v1_7_R4.EntityCreature;
import net.minecraft.server.v1_7_R4.EntityWither;
import net.minecraft.server.v1_7_R4.Navigation;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftCreature;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PetManager
  extends MiniClientPlugin<PetClient>
{
  private static Object _petOwnerSynch = new Object();
  private static Object _petRenameSynch = new Object();
  private DisguiseManager _disguiseManager;
  private mineplex.core.creature.Creature _creatureModule;
  private PetRepository _repository;
  private PetFactory _petFactory;
  private BlockRestore _blockRestore;
  private NautHashMap<String, org.bukkit.entity.Creature> _activePetOwners;
  private NautHashMap<String, Integer> _failedAttempts;
  private NautHashMap<String, EntityType> _petOwnerQueue = new NautHashMap();
  private NautHashMap<String, String> _petRenameQueue = new NautHashMap();
  private DonationManager _donationManager;
  private CoreClientManager _clientManager;
  
  public PetManager(JavaPlugin plugin, CoreClientManager clientManager, DonationManager donationManager, DisguiseManager disguiseManager, mineplex.core.creature.Creature creatureModule, BlockRestore restore, String webAddress)
  {
    super("Pet Manager", plugin);
    
    this._creatureModule = creatureModule;
    this._disguiseManager = disguiseManager;
    this._repository = new PetRepository(webAddress);
    this._petFactory = new PetFactory(this._repository);
    this._blockRestore = restore;
    this._donationManager = donationManager;
    this._clientManager = clientManager;
    
    this._activePetOwners = new NautHashMap();
    this._failedAttempts = new NautHashMap();
  }
  
  public void addPetOwnerToQueue(String playerName, EntityType entityType)
  {
    synchronized (_petOwnerSynch)
    {
      this._petOwnerQueue.put(playerName, entityType);
    }
  }
  
  public void addRenamePetToQueue(String playerName, String petName)
  {
    synchronized (_petRenameSynch)
    {
      this._petRenameQueue.put(playerName, petName);
    }
  }
  
  @EventHandler
  public void processQueues(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    synchronized (_petOwnerSynch)
    {
      for (String playerName : this._petOwnerQueue.keySet())
      {
        Player player = Bukkit.getPlayerExact(playerName);
        if ((player != null) && (player.isOnline())) {
          AddPetOwner(player, (EntityType)this._petOwnerQueue.get(playerName), player.getLocation());
        }
      }
      this._petOwnerQueue.clear();
    }
    synchronized (this._petRenameQueue)
    {
      for (String playerName : this._petRenameQueue.keySet())
      {
        Player player = Bukkit.getPlayerExact(playerName);
        if ((player != null) && (player.isOnline()))
        {
          getActivePet(playerName).setCustomNameVisible(true);
          getActivePet(playerName).setCustomName((String)this._petRenameQueue.get(playerName));
        }
      }
      this._petRenameQueue.clear();
    }
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent event)
  {
    Player p = event.getPlayer();
    Rank rank = this._clientManager.Get(p).GetRank();
    if ((rank == Rank.LEGEND) || (rank == Rank.ADMIN) || (rank == Rank.DEVELOPER) || (rank == Rank.OWNER)) {
      ((Donor)this._donationManager.Get(p.getName())).AddUnknownSalesPackagesOwned("Widder");
    }
  }
  
  public void AddPetOwner(Player player, EntityType entityType, Location location)
  {
    if (this._activePetOwners.containsKey(player.getName())) {
      if (((org.bukkit.entity.Creature)this._activePetOwners.get(player.getName())).getType() != entityType) {
        RemovePet(player, true);
      } else {
        return;
      }
    }
    org.bukkit.entity.Creature pet;
    if (entityType == EntityType.WITHER)
    {
      this._creatureModule.SetForce(true);
      EntityWither wither = new CustomWither(((CraftWorld)location.getWorld()).getHandle());
      wither.Silent = true;
      wither.setLocation(location.getX(), location.getY(), location.getZ(), 0.0F, 0.0F);
      ((CraftWorld)location.getWorld()).getHandle().addEntity(wither, CreatureSpawnEvent.SpawnReason.CUSTOM);
      org.bukkit.entity.Creature pet = (org.bukkit.entity.Creature)wither.getBukkitEntity();
      this._creatureModule.SetForce(false);
      
      Entity silverfish = this._creatureModule.SpawnEntity(location, EntityType.SILVERFISH);
      UtilEnt.Vegetate(silverfish, true);
      ((LivingEntity)silverfish).addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0));
      pet.setPassenger(silverfish);
    }
    else
    {
      pet = (org.bukkit.entity.Creature)this._creatureModule.SpawnEntity(location, entityType);
    }
    if ((((PetClient)Get(player)).GetPets().get(entityType) != null) && (((String)((PetClient)Get(player)).GetPets().get(entityType)).length() > 0)) {
      pet.setCustomName((String)((PetClient)Get(player)).GetPets().get(entityType));
    }
    if ((pet instanceof Zombie))
    {
      ((Zombie)pet).setBaby(true);
      pet.getEquipment().setHelmet(new ItemStack(Material.PUMPKIN));
      pet.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999999, 0));
      UtilEnt.silence(pet, true);
    }
    else if ((pet instanceof Villager))
    {
      ((Villager)pet).setBaby();
      ((Villager)pet).setAgeLock(true);
    }
    this._activePetOwners.put(player.getName(), pet);
    this._failedAttempts.put(player.getName(), Integer.valueOf(0));
    if ((pet instanceof Ageable))
    {
      ((Ageable)pet).setBaby();
      ((Ageable)pet).setAgeLock(true);
    }
    UtilEnt.Vegetate(pet);
  }
  
  public org.bukkit.entity.Creature GetPet(Player player)
  {
    return (org.bukkit.entity.Creature)this._activePetOwners.get(player.getName());
  }
  
  public void RemovePet(Player player, boolean removeOwner)
  {
    if (this._activePetOwners.containsKey(player.getName()))
    {
      org.bukkit.entity.Creature pet = (org.bukkit.entity.Creature)this._activePetOwners.get(player.getName());
      pet.remove();
      if (removeOwner) {
        this._activePetOwners.remove(player.getName());
      }
    }
  }
  
  @EventHandler
  public void preventWolfBone(PlayerInteractEntityEvent event)
  {
    if (event.getPlayer().getItemInHand().getType() == Material.BONE)
    {
      event.setCancelled(true);
      event.getPlayer().updateInventory();
    }
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    RemovePet(event.getPlayer(), true);
  }
  
  @EventHandler
  public void onEntityTarget(EntityTargetEvent event)
  {
    if (((event.getEntity() instanceof org.bukkit.entity.Creature)) && (this._activePetOwners.containsValue((org.bukkit.entity.Creature)event.getEntity()))) {
      event.setCancelled(true);
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void onEntityDamage(EntityDamageEvent event)
  {
    if (((event.getEntity() instanceof org.bukkit.entity.Creature)) && (this._activePetOwners.containsValue((org.bukkit.entity.Creature)event.getEntity())))
    {
      if (event.getCause() == EntityDamageEvent.DamageCause.VOID)
      {
        String playerName = null;
        for (Map.Entry<String, org.bukkit.entity.Creature> entry : this._activePetOwners.entrySet()) {
          if (entry.getValue() == event.getEntity()) {
            playerName = (String)entry.getKey();
          }
        }
        if (playerName != null)
        {
          Player player = Bukkit.getPlayerExact(playerName);
          if ((player != null) && (player.isOnline())) {
            RemovePet(player, true);
          }
        }
      }
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onUpdate(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    Iterator<String> ownerIterator = this._activePetOwners.keySet().iterator();
    while (ownerIterator.hasNext())
    {
      String playerName = (String)ownerIterator.next();
      Player owner = Bukkit.getPlayer(playerName);
      
      org.bukkit.entity.Creature pet = (org.bukkit.entity.Creature)this._activePetOwners.get(playerName);
      Location petSpot = pet.getLocation();
      Location ownerSpot = owner.getLocation();
      int xDiff = Math.abs(petSpot.getBlockX() - ownerSpot.getBlockX());
      int yDiff = Math.abs(petSpot.getBlockY() - ownerSpot.getBlockY());
      int zDiff = Math.abs(petSpot.getBlockZ() - ownerSpot.getBlockZ());
      if (xDiff + yDiff + zDiff > 4)
      {
        EntityCreature ec = ((CraftCreature)pet).getHandle();
        Navigation nav = ec.getNavigation();
        
        int xIndex = -1;
        int zIndex = -1;
        Block targetBlock = ownerSpot.getBlock().getRelative(xIndex, -1, zIndex);
        while ((targetBlock.isEmpty()) || (targetBlock.isLiquid()))
        {
          if (xIndex < 2)
          {
            xIndex++;
          }
          else if (zIndex < 2)
          {
            xIndex = -1;
            zIndex++;
          }
          else
          {
            return;
          }
          targetBlock = ownerSpot.getBlock().getRelative(xIndex, -1, zIndex);
        }
        float speed = 0.9F;
        if ((pet instanceof Villager)) {
          speed = 0.6F;
        }
        if (((Integer)this._failedAttempts.get(playerName)).intValue() > 4)
        {
          pet.teleport(owner);
          this._failedAttempts.put(playerName, Integer.valueOf(0));
        }
        else if (!nav.a(targetBlock.getX(), targetBlock.getY() + 1, targetBlock.getZ(), speed))
        {
          if (pet.getFallDistance() == 0.0F) {
            this._failedAttempts.put(playerName, Integer.valueOf(((Integer)this._failedAttempts.get(playerName)).intValue() + 1));
          }
        }
        else
        {
          this._failedAttempts.put(playerName, Integer.valueOf(0));
        }
      }
    }
  }
  
  @EventHandler
  public void OnClientWebResponse(ClientWebResponseEvent event)
  {
    ClientPetTokenWrapper token = (ClientPetTokenWrapper)new Gson().fromJson(event.GetResponse(), ClientPetTokenWrapper.class);
    
    ((PetClient)Get(token.Name)).Load(token.DonorToken);
  }
  
  protected PetClient AddPlayer(String player)
  {
    return new PetClient();
  }
  
  public PetFactory GetFactory()
  {
    return this._petFactory;
  }
  
  public PetRepository GetRepository()
  {
    return this._repository;
  }
  
  public boolean hasActivePet(String name)
  {
    return this._activePetOwners.containsKey(name);
  }
  
  public org.bukkit.entity.Creature getActivePet(String name)
  {
    return (org.bukkit.entity.Creature)this._activePetOwners.get(name);
  }
  
  public void DisableAll()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      RemovePet(player, true);
    }
  }
  
  public Collection<org.bukkit.entity.Creature> getPets()
  {
    return this._activePetOwners.values();
  }
}
