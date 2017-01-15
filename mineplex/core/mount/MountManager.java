package mineplex.core.mount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import mineplex.core.MiniPlugin;
import mineplex.core.account.CoreClientManager;
import mineplex.core.blockrestore.BlockRestore;
import mineplex.core.common.util.F;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.donation.DonationManager;
import mineplex.core.mount.types.MountCart;
import mineplex.core.mount.types.MountDragon;
import mineplex.core.mount.types.MountFrost;
import mineplex.core.mount.types.MountMule;
import mineplex.core.mount.types.MountSlime;
import mineplex.core.mount.types.MountUndead;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class MountManager
  extends MiniPlugin
{
  private CoreClientManager _clientManager;
  private DonationManager _donationManager;
  private BlockRestore _blockRestore;
  private DisguiseManager _disguiseManager;
  private List<Mount<?>> _types;
  private NautHashMap<Player, Mount<?>> _playerActiveMountMap = new NautHashMap();
  
  public MountManager(JavaPlugin plugin, CoreClientManager clientManager, DonationManager donationManager, BlockRestore blockRestore, DisguiseManager disguiseManager)
  {
    super("Mount Manager", plugin);
    
    this._clientManager = clientManager;
    this._donationManager = donationManager;
    this._blockRestore = blockRestore;
    this._disguiseManager = disguiseManager;
    
    CreateGadgets();
  }
  
  private void CreateGadgets()
  {
    this._types = new ArrayList();
    
    this._types.add(new MountUndead(this));
    this._types.add(new MountFrost(this));
    this._types.add(new MountMule(this));
    this._types.add(new MountDragon(this));
    this._types.add(new MountSlime(this));
    this._types.add(new MountCart(this));
  }
  
  public List<Mount<?>> getMounts()
  {
    return this._types;
  }
  
  public void DeregisterAll(Player player)
  {
    for (Mount<?> mount : this._types) {
      mount.Disable(player);
    }
  }
  
  @EventHandler
  public void HorseInteract(PlayerInteractEntityEvent event)
  {
    if (!(event.getRightClicked() instanceof Horse)) {
      return;
    }
    boolean found = false;
    for (Mount mount : this._playerActiveMountMap.values()) {
      if (mount.GetActive().containsValue(event.getRightClicked()))
      {
        found = true;
        break;
      }
    }
    if (!found) {
      return;
    }
    Player player = event.getPlayer();
    Horse horse = (Horse)event.getRightClicked();
    if ((horse.getOwner() == null) || (!horse.getOwner().equals(player)))
    {
      UtilPlayer.message(player, F.main("Mount", "This is not your Mount!"));
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void LeashDropCancel(ItemSpawnEvent event)
  {
    if (event.getEntity().getItemStack().getType() == Material.LEASH) {
      event.setCancelled(true);
    }
  }
  
  public void DisableAll()
  {
    int j;
    int i;
    for (Iterator localIterator = this._types.iterator(); localIterator.hasNext(); i < j)
    {
      Mount<?> mount = (Mount)localIterator.next();
      Player[] arrayOfPlayer;
      j = (arrayOfPlayer = UtilServer.getPlayers()).length;i = 0; continue;Player player = arrayOfPlayer[i];
      mount.Disable(player);i++;
    }
  }
  
  @EventHandler
  public void quit(PlayerQuitEvent event)
  {
    this._playerActiveMountMap.remove(event.getPlayer());
  }
  
  @EventHandler
  public void death(PlayerDeathEvent event)
  {
    this._playerActiveMountMap.remove(event.getEntity());
  }
  
  public void setActive(Player player, Mount<?> mount)
  {
    this._playerActiveMountMap.put(player, mount);
  }
  
  public Mount<?> getActive(Player player)
  {
    return (Mount)this._playerActiveMountMap.get(player);
  }
  
  public void removeActive(Player player)
  {
    this._playerActiveMountMap.remove(player);
  }
  
  public CoreClientManager getClientManager()
  {
    return this._clientManager;
  }
  
  public DonationManager getDonationManager()
  {
    return this._donationManager;
  }
  
  public BlockRestore getBlockRestore()
  {
    return this._blockRestore;
  }
  
  public DisguiseManager getDisguiseManager()
  {
    return this._disguiseManager;
  }
}
