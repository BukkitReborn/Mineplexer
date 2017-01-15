package mineplex.core.gadget;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.blockrestore.BlockRestore;
import mineplex.core.common.Rank;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.event.GadgetCollideEntityEvent;
import mineplex.core.gadget.gadgets.ItemBatGun;
import mineplex.core.gadget.gadgets.ItemCoinBomb;
import mineplex.core.gadget.gadgets.ItemEtherealPearl;
import mineplex.core.gadget.gadgets.ItemFirework;
import mineplex.core.gadget.gadgets.ItemFleshHook;
import mineplex.core.gadget.gadgets.ItemMelonLauncher;
import mineplex.core.gadget.gadgets.ItemPaintballGun;
import mineplex.core.gadget.gadgets.ItemTNT;
import mineplex.core.gadget.gadgets.MorphBat;
import mineplex.core.gadget.gadgets.MorphBlaze;
import mineplex.core.gadget.gadgets.MorphBlock;
import mineplex.core.gadget.gadgets.MorphBunny;
import mineplex.core.gadget.gadgets.MorphChicken;
import mineplex.core.gadget.gadgets.MorphCow;
import mineplex.core.gadget.gadgets.MorphCreeper;
import mineplex.core.gadget.gadgets.MorphEnderman;
import mineplex.core.gadget.gadgets.MorphPig;
import mineplex.core.gadget.gadgets.MorphPumpkinKing;
import mineplex.core.gadget.gadgets.MorphVillager;
import mineplex.core.gadget.gadgets.MorphWither;
import mineplex.core.gadget.gadgets.OutfitRaveSuit;
import mineplex.core.gadget.gadgets.OutfitSpaceSuit;
import mineplex.core.gadget.gadgets.ParticleBlizzard;
import mineplex.core.gadget.gadgets.ParticleEnchant;
import mineplex.core.gadget.gadgets.ParticleFairy;
import mineplex.core.gadget.gadgets.ParticleFireRings;
import mineplex.core.gadget.gadgets.ParticleFoot;
import mineplex.core.gadget.gadgets.ParticleGreen;
import mineplex.core.gadget.gadgets.ParticleHeart;
import mineplex.core.gadget.gadgets.ParticleHelix;
import mineplex.core.gadget.gadgets.ParticleLegend;
import mineplex.core.gadget.gadgets.ParticleRain;
import mineplex.core.gadget.types.Gadget;
import mineplex.core.gadget.types.GadgetType;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.gadget.types.MusicGadget;
import mineplex.core.gadget.types.OutfitGadget;
import mineplex.core.gadget.types.OutfitGadget.ArmorSlot;
import mineplex.core.gadget.types.ParticleGadget;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.mount.MountManager;
import mineplex.core.pet.PetManager;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.projectile.ProjectileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GadgetManager
  extends MiniPlugin
{
  private CoreClientManager _clientManager;
  private DonationManager _donationManager;
  private InventoryManager _inventoryManager;
  private PetManager _petManager;
  private PreferencesManager _preferencesManager;
  private DisguiseManager _disguiseManager;
  private BlockRestore _blockRestore;
  private ProjectileManager _projectileManager;
  private NautHashMap<GadgetType, List<Gadget>> _gadgets;
  private NautHashMap<Player, Long> _lastMove = new NautHashMap();
  private NautHashMap<Player, NautHashMap<GadgetType, Gadget>> _playerActiveGadgetMap = new NautHashMap();
  private boolean _hideParticles = false;
  private int _activeItemSlot = 3;
  
  public GadgetManager(JavaPlugin plugin, CoreClientManager clientManager, DonationManager donationManager, InventoryManager inventoryManager, MountManager mountManager, PetManager petManager, PreferencesManager preferencesManager, DisguiseManager disguiseManager, BlockRestore blockRestore, ProjectileManager projectileManager)
  {
    super("Gadget Manager", plugin);
    
    this._clientManager = clientManager;
    this._donationManager = donationManager;
    this._inventoryManager = inventoryManager;
    this._petManager = petManager;
    this._preferencesManager = preferencesManager;
    this._disguiseManager = disguiseManager;
    this._blockRestore = blockRestore;
    this._projectileManager = projectileManager;
    
    CreateGadgets();
  }
  
  private void CreateGadgets()
  {
    this._gadgets = new NautHashMap();
    
    addGadget(new ItemEtherealPearl(this));
    addGadget(new ItemFirework(this));
    addGadget(new ItemTNT(this));
    addGadget(new ItemMelonLauncher(this));
    addGadget(new ItemFleshHook(this));
    addGadget(new ItemPaintballGun(this));
    addGadget(new ItemBatGun(this));
    addGadget(new ItemCoinBomb(this));
    
    addGadget(new OutfitRaveSuit(this, "Rave Hat", -2, OutfitGadget.ArmorSlot.Helmet, Material.LEATHER_HELMET, (byte)0));
    addGadget(new OutfitRaveSuit(this, "Rave Shirt", -2, OutfitGadget.ArmorSlot.Chest, Material.LEATHER_CHESTPLATE, (byte)0));
    addGadget(new OutfitRaveSuit(this, "Rave Pants", -2, OutfitGadget.ArmorSlot.Legs, Material.LEATHER_LEGGINGS, (byte)0));
    addGadget(new OutfitRaveSuit(this, "Rave Boots", -2, OutfitGadget.ArmorSlot.Boots, Material.LEATHER_BOOTS, (byte)0));
    
    addGadget(new OutfitSpaceSuit(this, "Space Helmet", -2, OutfitGadget.ArmorSlot.Helmet, Material.GLASS, (byte)0));
    addGadget(new OutfitSpaceSuit(this, "Space Jacket", -2, OutfitGadget.ArmorSlot.Chest, Material.GOLD_CHESTPLATE, (byte)0));
    addGadget(new OutfitSpaceSuit(this, "Space Pants", -2, OutfitGadget.ArmorSlot.Legs, Material.GOLD_LEGGINGS, (byte)0));
    addGadget(new OutfitSpaceSuit(this, "Space Boots", -2, OutfitGadget.ArmorSlot.Boots, Material.GOLD_BOOTS, (byte)0));
    
    addGadget(new MorphVillager(this));
    addGadget(new MorphCow(this));
    addGadget(new MorphChicken(this));
    addGadget(new MorphBlock(this));
    addGadget(new MorphEnderman(this));
    addGadget(new MorphBat(this));
    
    addGadget(new MorphPumpkinKing(this));
    addGadget(new MorphPig(this));
    addGadget(new MorphCreeper(this));
    addGadget(new MorphBlaze(this));
    
    addGadget(new MorphWither(this));
    addGadget(new MorphBunny(this));
    
    addGadget(new ParticleFoot(this));
    addGadget(new ParticleEnchant(this));
    addGadget(new ParticleFireRings(this));
    addGadget(new ParticleRain(this));
    addGadget(new ParticleHelix(this));
    addGadget(new ParticleGreen(this));
    addGadget(new ParticleHeart(this));
    addGadget(new ParticleFairy(this));
    addGadget(new ParticleLegend(this));
    addGadget(new ParticleBlizzard(this));
    
    addGadget(new MusicGadget(this, "13 Disc", new String[] { "" }, -2, 2256, 178000L));
    addGadget(new MusicGadget(this, "Cat Disc", new String[] { "" }, -2, 2257, 185000L));
    addGadget(new MusicGadget(this, "Blocks Disc", new String[] { "" }, -2, 2258, 345000L));
    addGadget(new MusicGadget(this, "Chirp Disc", new String[] { "" }, -2, 2259, 185000L));
    addGadget(new MusicGadget(this, "Far Disc", new String[] { "" }, -2, 2260, 174000L));
    addGadget(new MusicGadget(this, "Mall Disc", new String[] { "" }, -2, 2261, 197000L));
    addGadget(new MusicGadget(this, "Mellohi Disc", new String[] { "" }, -2, 2262, 96000L));
    addGadget(new MusicGadget(this, "Stal Disc", new String[] { "" }, -2, 2263, 150000L));
    addGadget(new MusicGadget(this, "Strad Disc", new String[] { "" }, -2, 2264, 188000L));
    addGadget(new MusicGadget(this, "Ward Disc", new String[] { "" }, -2, 2265, 251000L));
    
    addGadget(new MusicGadget(this, "Wait Disc", new String[] { "" }, -2, 2267, 238000L));
  }
  
  private void addGadget(Gadget gadget)
  {
    if (!this._gadgets.containsKey(gadget.getGadgetType())) {
      this._gadgets.put(gadget.getGadgetType(), new ArrayList());
    }
    ((List)this._gadgets.get(gadget.getGadgetType())).add(gadget);
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    if (this._clientManager.Get(event.getPlayer()).GetRank().Has(Rank.MODERATOR))
    {
      Iterator localIterator2;
      label149:
      for (Iterator localIterator1 = this._gadgets.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
      {
        GadgetType gadgetType = (GadgetType)localIterator1.next();
        if ((gadgetType != GadgetType.Particle) || (!this._clientManager.Get(event.getPlayer()).GetRank().Has(Rank.ADMIN))) {
          break label149;
        }
        localIterator2 = ((List)this._gadgets.get(gadgetType)).iterator(); continue;Gadget gadget = (Gadget)localIterator2.next();
        
        ((Donor)this._donationManager.Get(event.getPlayer().getName())).AddUnknownSalesPackagesOwned(gadget.GetName());
      }
    }
  }
  
  public List<Gadget> getGadgets(GadgetType gadgetType)
  {
    return (List)this._gadgets.get(gadgetType);
  }
  
  public void RemoveOutfit(Player player, OutfitGadget.ArmorSlot slot)
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = this._gadgets.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      GadgetType gadgetType = (GadgetType)localIterator1.next();
      
      localIterator2 = ((List)this._gadgets.get(gadgetType)).iterator(); continue;Gadget gadget = (Gadget)localIterator2.next();
      if ((gadget instanceof OutfitGadget))
      {
        OutfitGadget armor = (OutfitGadget)gadget;
        if (armor.GetSlot() == slot) {
          armor.RemoveArmor(player);
        }
      }
    }
  }
  
  public void RemoveItem(Player player)
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = this._gadgets.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      GadgetType gadgetType = (GadgetType)localIterator1.next();
      
      localIterator2 = ((List)this._gadgets.get(gadgetType)).iterator(); continue;Gadget gadget = (Gadget)localIterator2.next();
      if ((gadget instanceof ItemGadget))
      {
        ItemGadget item = (ItemGadget)gadget;
        
        item.RemoveItem(player);
      }
    }
  }
  
  public void RemoveParticle(Player player)
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = this._gadgets.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      GadgetType gadgetType = (GadgetType)localIterator1.next();
      
      localIterator2 = ((List)this._gadgets.get(gadgetType)).iterator(); continue;Gadget gadget = (Gadget)localIterator2.next();
      if ((gadget instanceof ParticleGadget))
      {
        ParticleGadget part = (ParticleGadget)gadget;
        
        part.Disable(player);
      }
    }
  }
  
  public void RemoveMorph(Player player)
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = this._gadgets.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      GadgetType gadgetType = (GadgetType)localIterator1.next();
      
      localIterator2 = ((List)this._gadgets.get(gadgetType)).iterator(); continue;Gadget gadget = (Gadget)localIterator2.next();
      if ((gadget instanceof MorphGadget))
      {
        MorphGadget part = (MorphGadget)gadget;
        
        part.Disable(player);
      }
    }
  }
  
  public void DisableAll()
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = this._gadgets.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      GadgetType gadgetType = (GadgetType)localIterator1.next();
      
      localIterator2 = ((List)this._gadgets.get(gadgetType)).iterator(); continue;Gadget gadget = (Gadget)localIterator2.next();
      if (!(gadget instanceof ParticleGadget))
      {
        Player[] arrayOfPlayer;
        int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
        for (int i = 0; i < j; i++)
        {
          Player player = arrayOfPlayer[i];
          gadget.Disable(player);
        }
      }
    }
  }
  
  public void DisableAll(Player player)
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = this._gadgets.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      GadgetType gadgetType = (GadgetType)localIterator1.next();
      
      localIterator2 = ((List)this._gadgets.get(gadgetType)).iterator(); continue;Gadget gadget = (Gadget)localIterator2.next();
      
      gadget.Disable(player);
    }
  }
  
  public PetManager getPetManager()
  {
    return this._petManager;
  }
  
  public CoreClientManager getClientManager()
  {
    return this._clientManager;
  }
  
  public DonationManager getDonationManager()
  {
    return this._donationManager;
  }
  
  public PreferencesManager getPreferencesManager()
  {
    return this._preferencesManager;
  }
  
  public ProjectileManager getProjectileManager()
  {
    return this._projectileManager;
  }
  
  public DisguiseManager getDisguiseManager()
  {
    return this._disguiseManager;
  }
  
  public InventoryManager getInventoryManager()
  {
    return this._inventoryManager;
  }
  
  public boolean collideEvent(Gadget gadget, Player other)
  {
    GadgetCollideEntityEvent collideEvent = new GadgetCollideEntityEvent(gadget, other);
    
    Bukkit.getServer().getPluginManager().callEvent(collideEvent);
    
    return collideEvent.isCancelled();
  }
  
  public BlockRestore getBlockRestore()
  {
    return this._blockRestore;
  }
  
  @EventHandler
  public void setMoving(PlayerMoveEvent event)
  {
    if (UtilMath.offset(event.getFrom(), event.getTo()) <= 0.0D) {
      return;
    }
    this._lastMove.put(event.getPlayer(), Long.valueOf(System.currentTimeMillis()));
  }
  
  public boolean isMoving(Player player)
  {
    if (!this._lastMove.containsKey(player)) {
      return false;
    }
    return !UtilTime.elapsed(((Long)this._lastMove.get(player)).longValue(), 500L);
  }
  
  @EventHandler
  public void quit(PlayerQuitEvent event)
  {
    DisableAll(event.getPlayer());
    
    this._lastMove.remove(event.getPlayer());
    
    this._playerActiveGadgetMap.remove(event.getPlayer());
  }
  
  @EventHandler
  public void death(PlayerDeathEvent event)
  {
    this._lastMove.remove(event.getEntity());
    
    this._playerActiveGadgetMap.remove(event.getEntity());
  }
  
  public void setActive(Player player, Gadget gadget)
  {
    if (!this._playerActiveGadgetMap.containsKey(player)) {
      this._playerActiveGadgetMap.put(player, new NautHashMap());
    }
    ((NautHashMap)this._playerActiveGadgetMap.get(player)).put(gadget.getGadgetType(), gadget);
  }
  
  public Gadget getActive(Player player, GadgetType gadgetType)
  {
    if (!this._playerActiveGadgetMap.containsKey(player)) {
      this._playerActiveGadgetMap.put(player, new NautHashMap());
    }
    return (Gadget)((NautHashMap)this._playerActiveGadgetMap.get(player)).get(gadgetType);
  }
  
  public void removeActive(Player player, Gadget gadget)
  {
    if (!this._playerActiveGadgetMap.containsKey(player)) {
      this._playerActiveGadgetMap.put(player, new NautHashMap());
    }
    ((NautHashMap)this._playerActiveGadgetMap.get(player)).remove(gadget.getGadgetType());
  }
  
  public void setHideParticles(boolean b)
  {
    this._hideParticles = b;
  }
  
  public boolean hideParticles()
  {
    return this._hideParticles;
  }
  
  public void setActiveItemSlot(int i)
  {
    this._activeItemSlot = i;
  }
  
  public int getActiveItemSlot()
  {
    return this._activeItemSlot;
  }
  
  public void redisplayActiveItem(Player player)
  {
    for (Gadget gadget : (List)this._gadgets.get(GadgetType.Item)) {
      if ((gadget instanceof ItemGadget)) {
        if (gadget.IsActive(player)) {
          ((ItemGadget)gadget).ApplyItem(player, false);
        }
      }
    }
  }
  
  public boolean canPlaySongAt(Location location)
  {
    for (Gadget gadget : (List)this._gadgets.get(GadgetType.MusicDisc)) {
      if ((gadget instanceof MusicGadget)) {
        if (!((MusicGadget)gadget).canPlayAt(location)) {
          return false;
        }
      }
    }
    return true;
  }
  
  @EventHandler
  public void chissMeow(PlayerToggleSneakEvent event)
  {
    if (event.getPlayer().getName().equals("Chiss")) {
      if (!event.getPlayer().isSneaking()) {
        event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.CAT_MEOW, 1.0F, 1.0F);
      }
    }
  }
}
