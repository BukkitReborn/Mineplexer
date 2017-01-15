package mineplex.core.gadget.types;

import java.util.HashSet;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.event.GadgetActivateEvent;
import mineplex.core.shop.item.SalesPackageBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Gadget
  extends SalesPackageBase
  implements Listener
{
  public GadgetManager Manager;
  private GadgetType _gadgetType;
  protected HashSet<Player> _active = new HashSet();
  
  public Gadget(GadgetManager manager, GadgetType gadgetType, String name, String[] desc, int cost, Material mat, byte data)
  {
    this(manager, gadgetType, name, desc, cost, mat, data, 1);
  }
  
  public Gadget(GadgetManager manager, GadgetType gadgetType, String name, String[] desc, int cost, Material mat, byte data, int quantity)
  {
    super(name, mat, data, desc, cost, quantity);
    
    this._gadgetType = gadgetType;
    this.KnownPackage = false;
    
    this.Manager = manager;
    
    this.Manager.getPlugin().getServer().getPluginManager().registerEvents(this, this.Manager.getPlugin());
  }
  
  public GadgetType getGadgetType()
  {
    return this._gadgetType;
  }
  
  public HashSet<Player> GetActive()
  {
    return this._active;
  }
  
  public boolean IsActive(Player player)
  {
    return this._active.contains(player);
  }
  
  @EventHandler
  public void PlayerQuit(PlayerQuitEvent event)
  {
    Disable(event.getPlayer());
  }
  
  public void Enable(Player player)
  {
    GadgetActivateEvent gadgetEvent = new GadgetActivateEvent(player, this);
    Bukkit.getServer().getPluginManager().callEvent(gadgetEvent);
    if (gadgetEvent.isCancelled())
    {
      UtilPlayer.message(player, F.main("Inventory", GetName() + " is not enabled."));
      return;
    }
    EnableCustom(player);
    this.Manager.setActive(player, this);
  }
  
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
  
  public void Disable(Player player)
  {
    if (IsActive(player)) {
      this.Manager.removeActive(player, this);
    }
    DisableCustom(player);
  }
  
  public abstract void EnableCustom(Player paramPlayer);
  
  public abstract void DisableCustom(Player paramPlayer);
  
  public void Sold(Player player, CurrencyType currencyType) {}
}
