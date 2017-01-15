package mineplex.core.mount;

import java.util.HashMap;
import java.util.HashSet;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.mount.event.MountActivateEvent;
import mineplex.core.shop.item.SalesPackageBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Mount<T>
  extends SalesPackageBase
  implements Listener
{
  protected HashSet<Player> _owners = new HashSet();
  protected HashMap<Player, T> _active = new HashMap();
  public MountManager Manager;
  
  public Mount(MountManager manager, String name, Material material, byte displayData, String[] description, int coins)
  {
    super(name, material, displayData, description, coins);
    
    this.Manager = manager;
    
    this.Manager.getPlugin().getServer().getPluginManager().registerEvents(this, this.Manager.getPlugin());
  }
  
  public void Sold(Player player, CurrencyType currencyType) {}
  
  public final void Enable(Player player)
  {
    MountActivateEvent gadgetEvent = new MountActivateEvent(player, this);
    Bukkit.getServer().getPluginManager().callEvent(gadgetEvent);
    if (gadgetEvent.isCancelled())
    {
      UtilPlayer.message(player, F.main("Inventory", GetName() + " is not enabled."));
      return;
    }
    this.Manager.setActive(player, this);
    EnableCustom(player);
  }
  
  public abstract void EnableCustom(Player paramPlayer);
  
  public abstract void Disable(Player paramPlayer);
  
  public void DisableForAll()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      Disable(player);
    }
  }
  
  @EventHandler
  public void PlayerJoin(PlayerJoinEvent event)
  {
    if (event.getPlayer().isOp()) {
      this._owners.add(event.getPlayer());
    }
  }
  
  @EventHandler
  public void PlayerQuit(PlayerQuitEvent event)
  {
    this._owners.remove(event.getPlayer());
    Disable(event.getPlayer());
  }
  
  public HashSet<Player> GetOwners()
  {
    return this._owners;
  }
  
  public HashMap<Player, T> GetActive()
  {
    return this._active;
  }
  
  public boolean IsActive(Player player)
  {
    return this._active.containsKey(player);
  }
  
  public boolean HasMount(Player player)
  {
    return this._owners.contains(player);
  }
}
