package mineplex.core.gadget.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilGear;
import mineplex.core.common.util.UtilInv;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.event.ItemGadgetOutOfAmmoEvent;
import mineplex.core.gadget.gadgets.Ammo;
import mineplex.core.inventory.ClientInventory;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.recharge.Recharge;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class ItemGadget
  extends Gadget
{
  private Ammo _ammo;
  protected long _recharge;
  
  public ItemGadget(GadgetManager manager, String name, String[] desc, int cost, Material mat, byte data, long recharge, Ammo ammo)
  {
    super(manager, GadgetType.Item, name, desc, cost, mat, data);
    
    this._ammo = ammo;
    this._recharge = recharge;
    this.Free = true;
  }
  
  public void EnableCustom(Player player)
  {
    ApplyItem(player, true);
  }
  
  public void DisableCustom(Player player)
  {
    RemoveItem(player);
  }
  
  public HashSet<Player> GetActive()
  {
    return this._active;
  }
  
  public boolean IsActive(Player player)
  {
    return this._active.contains(player);
  }
  
  public void ApplyItem(Player player, boolean inform)
  {
    this.Manager.RemoveItem(player);
    
    this._active.add(player);
    
    List<String> itemLore = new ArrayList();
    itemLore.addAll(Arrays.asList(GetDescription()));
    itemLore.add(C.cBlack);
    itemLore.add(C.cWhite + "Your Ammo : " + ((ClientInventory)this.Manager.getInventoryManager().Get(player)).getItemCount(GetName()));
    
    player.getInventory().setItem(this.Manager.getActiveItemSlot(), ItemStackFactory.Instance.CreateStack(GetDisplayMaterial(), GetDisplayData(), 1, F.item(((ClientInventory)this.Manager.getInventoryManager().Get(player)).getItemCount(GetName()) + " " + GetName())));
    if (inform) {
      UtilPlayer.message(player, F.main("Gadget", "You equipped " + F.elem(GetName()) + "."));
    }
  }
  
  @EventHandler
  public void orderThatChest(PlayerDropItemEvent event)
  {
    if ((IsActive(event.getPlayer())) && (event.getItemDrop().getItemStack().getType() == GetDisplayMaterial()))
    {
      final Player player = event.getPlayer();
      
      Bukkit.getScheduler().scheduleSyncDelayedTask(this.Manager.getPlugin(), new Runnable()
      {
        public void run()
        {
          if (player.isOnline())
          {
            player.getInventory().remove(ItemGadget.this.GetDisplayMaterial());
            player.getInventory().setItem(ItemGadget.this.Manager.getActiveItemSlot(), ItemStackFactory.Instance.CreateStack(ItemGadget.this.GetDisplayMaterial(), ItemGadget.this.GetDisplayData(), 1, F.item(((ClientInventory)ItemGadget.this.Manager.getInventoryManager().Get(player)).getItemCount(ItemGadget.this.GetName()) + " " + ItemGadget.this.GetName())));
            UtilInv.Update(player);
          }
        }
      });
    }
  }
  
  public void RemoveItem(Player player)
  {
    if (this._active.remove(player))
    {
      player.getInventory().setItem(this.Manager.getActiveItemSlot(), null);
      
      UtilPlayer.message(player, F.main("Gadget", "You unequipped " + F.elem(GetName()) + "."));
    }
  }
  
  public Ammo getAmmo()
  {
    return this._ammo;
  }
  
  public boolean IsItem(Player player)
  {
    return UtilInv.IsItem(player.getItemInHand(), GetDisplayMaterial(), GetDisplayData());
  }
  
  @EventHandler
  public void Activate(PlayerInteractEvent event)
  {
    if ((event.getAction() != Action.RIGHT_CLICK_AIR) && (event.getAction() != Action.RIGHT_CLICK_BLOCK)) {
      return;
    }
    if (UtilBlock.usable(event.getClickedBlock())) {
      return;
    }
    if (!UtilGear.isMat(event.getPlayer().getItemInHand(), GetDisplayMaterial())) {
      return;
    }
    Player player = event.getPlayer();
    if (!IsActive(player)) {
      return;
    }
    event.setCancelled(true);
    if (((ClientInventory)this.Manager.getInventoryManager().Get(player)).getItemCount(GetName()) <= 0)
    {
      UtilPlayer.message(player, F.main("Gadget", "You do not have any " + GetName() + " left."));
      
      ItemGadgetOutOfAmmoEvent ammoEvent = new ItemGadgetOutOfAmmoEvent(event.getPlayer(), this);
      Bukkit.getServer().getPluginManager().callEvent(ammoEvent);
      
      return;
    }
    if (!Recharge.Instance.use(player, GetName(), this._recharge, this._recharge > 1000L, false))
    {
      UtilInv.Update(player);
      return;
    }
    this.Manager.getInventoryManager().addItemToInventory(player, getGadgetType().name(), GetName(), -1);
    
    player.getInventory().setItem(this.Manager.getActiveItemSlot(), ItemStackFactory.Instance.CreateStack(GetDisplayMaterial(), GetDisplayData(), 1, F.item(((ClientInventory)this.Manager.getInventoryManager().Get(player)).getItemCount(GetName()) + " " + GetName())));
    
    ActivateCustom(event.getPlayer());
  }
  
  public abstract void ActivateCustom(Player paramPlayer);
}
