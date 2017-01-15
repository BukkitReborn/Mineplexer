package mineplex.core.gadget.gadgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilFirework;
import mineplex.core.common.util.UtilGear;
import mineplex.core.common.util.UtilInv;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.event.ItemGadgetOutOfAmmoEvent;
import mineplex.core.gadget.types.GadgetType;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.inventory.ClientInventory;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.Vector;

public class ItemGemBomb
  extends ItemGadget
{
  private HashMap<Item, Long> _activeBombs = new HashMap();
  private HashSet<Item> _gems = new HashSet();
  
  public ItemGemBomb(GadgetManager manager)
  {
    super(manager, "Gem Party Bomb", new String[] {C.cWhite + "It's party time! You will be", C.cWhite + "everyones favourite player", C.cWhite + "when you use one of these!", " ", C.cRed + C.Bold + "WARNING: " + ChatColor.RESET + "This uses 2000 Gems" }, -1, Material.EMERALD, (byte)0, 30000L, new Ammo("Gem Party Bomb", "10 Gem Party Bomb", Material.EMERALD, (byte)0, new String[] { C.cWhite + "10 Coin Party Bomb to PARTY!" }, 10, 10));
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
    if (((Donor)this.Manager.getDonationManager().Get(player.getName())).GetBalance(CurrencyType.Gems) < 2000)
    {
      UtilPlayer.message(player, F.main("Inventory", new StringBuilder("You do not have the required ").append(C.cGreen).append("2000 Gems").toString()) + ".");
      return;
    }
    if (!this._activeBombs.isEmpty())
    {
      UtilPlayer.message(player, F.main("Inventory", new StringBuilder("There is already a ").append(F.elem(new StringBuilder(String.valueOf(C.cGreen)).append("Gem Bomb").toString())).toString()) + " being used.");
      return;
    }
    if (!Recharge.Instance.use(player, GetName(), this._recharge, this._recharge > 1000L, false))
    {
      UtilInv.Update(player);
      return;
    }
    this.Manager.getInventoryManager().addItemToInventory(player, getGadgetType().name(), GetName(), -1);
    this.Manager.getDonationManager().RewardGems(null, GetName(), event.getPlayer().getName(), event.getPlayer().getUniqueId(), 63536);
    
    player.getInventory().setItem(this.Manager.getActiveItemSlot(), ItemStackFactory.Instance.CreateStack(GetDisplayMaterial(), GetDisplayData(), 1, F.item(GetName())));
    
    ActivateCustom(event.getPlayer());
  }
  
  public void ActivateCustom(Player player)
  {
    Item item = player.getWorld().dropItem(player.getEyeLocation().add(player.getLocation().getDirection()), new ItemStack(Material.EMERALD_BLOCK));
    UtilAction.velocity(item, player.getLocation().getDirection(), 1.0D, false, 0.0D, 0.2D, 1.0D, false);
    this._activeBombs.put(item, Long.valueOf(System.currentTimeMillis()));
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player other = arrayOfPlayer[i];
      UtilPlayer.message(other, C.cGreen + C.Bold + player.getName() + C.cWhite + C.Bold + " has thrown a " + C.cGreen + C.Bold + "Gem Party Bomb" + C.cWhite + C.Bold + "!");
    }
  }
  
  @EventHandler
  public void Update(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    Iterator<Item> itemIterator = this._activeBombs.keySet().iterator();
    while (itemIterator.hasNext())
    {
      Item item = (Item)itemIterator.next();
      long time = ((Long)this._activeBombs.get(item)).longValue();
      if (UtilTime.elapsed(time, 3000L))
      {
        if (Math.random() > 0.8D) {
          UtilFirework.playFirework(item.getLocation(), FireworkEffect.builder().flicker(false).withColor(Color.GREEN).with(FireworkEffect.Type.BURST).trail(false).build());
        } else {
          item.getWorld().playSound(item.getLocation(), Sound.FIREWORK_LAUNCH, 1.0F, 1.0F);
        }
        Item gem = item.getWorld().dropItem(item.getLocation().add(0.0D, 1.0D, 0.0D), new ItemStack(Material.EMERALD));
        
        long passed = System.currentTimeMillis() - time;
        Vector vel = new Vector(Math.sin(passed / 300.0D), 0.0D, Math.cos(passed / 300.0D));
        
        UtilAction.velocity(gem, vel, Math.abs(Math.sin(passed / 3000.0D)), false, 0.0D, 0.2D + Math.abs(Math.cos(passed / 3000.0D)) * 0.8D, 1.0D, false);
        
        gem.setPickupDelay(40);
        
        this._gems.add(gem);
      }
      if (UtilTime.elapsed(time, 23000L))
      {
        item.remove();
        itemIterator.remove();
      }
    }
  }
  
  @EventHandler
  public void Pickup(PlayerPickupItemEvent event)
  {
    if (this._activeBombs.keySet().contains(event.getItem()))
    {
      event.setCancelled(true);
    }
    else if (this._gems.contains(event.getItem()))
    {
      event.setCancelled(true);
      event.getItem().remove();
      
      this.Manager.getDonationManager().RewardGemsLater(GetName() + " Pickup", event.getPlayer(), 4);
      
      event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0F, 2.0F);
    }
  }
  
  @EventHandler
  public void Clean(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    Iterator<Item> gemIterator = this._gems.iterator();
    while (gemIterator.hasNext())
    {
      Item gem = (Item)gemIterator.next();
      if ((!gem.isValid()) || (gem.getTicksLived() > 1200))
      {
        gem.remove();
        gemIterator.remove();
      }
    }
  }
}
