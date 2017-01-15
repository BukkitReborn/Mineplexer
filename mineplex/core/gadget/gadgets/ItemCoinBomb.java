package mineplex.core.gadget.gadgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilFirework;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.donation.DonationManager;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ItemCoinBomb
  extends ItemGadget
{
  private HashMap<Item, Long> _active = new HashMap();
  private HashSet<Item> _coins = new HashSet();
  
  public ItemCoinBomb(GadgetManager manager)
  {
    super(manager, "Coin Party Bomb", new String[] {C.cWhite + "It's party time! You will be", C.cWhite + "everyones favourite player", C.cWhite + "when you use one of these!" }, -1, Material.getMaterial(175), (byte)0, 30000L, new Ammo("Coin Party Bomb", "1 Coin Party Bomb", Material.getMaterial(175), (byte)0, new String[] { C.cWhite + "1 Coin Party Bomb to PARTY!" }, 2000, 1));
  }
  
  public void ActivateCustom(Player player)
  {
    Item item = player.getWorld().dropItem(player.getEyeLocation().add(player.getLocation().getDirection()), new ItemStack(Material.GOLD_BLOCK));
    UtilAction.velocity(item, player.getLocation().getDirection(), 1.0D, false, 0.0D, 0.2D, 1.0D, false);
    this._active.put(item, Long.valueOf(System.currentTimeMillis()));
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player other = arrayOfPlayer[i];
      UtilPlayer.message(other, C.cYellow + C.Bold + player.getName() + C.cWhite + C.Bold + " has thrown a " + C.cYellow + C.Bold + "Coin Party Bomb" + C.cWhite + C.Bold + "!");
    }
  }
  
  @EventHandler
  public void Update(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    Iterator<Item> itemIterator = this._active.keySet().iterator();
    while (itemIterator.hasNext())
    {
      Item item = (Item)itemIterator.next();
      long time = ((Long)this._active.get(item)).longValue();
      if (UtilTime.elapsed(time, 3000L))
      {
        if (Math.random() > 0.8D) {
          UtilFirework.playFirework(item.getLocation(), FireworkEffect.builder().flicker(false).withColor(Color.YELLOW).with(FireworkEffect.Type.BURST).trail(false).build());
        } else {
          item.getWorld().playSound(item.getLocation(), Sound.FIREWORK_LAUNCH, 1.0F, 1.0F);
        }
        Item coin = item.getWorld().dropItem(item.getLocation().add(0.0D, 1.0D, 0.0D), new ItemStack(Material.getMaterial(175)));
        
        long passed = System.currentTimeMillis() - time;
        Vector vel = new Vector(Math.sin(passed / 300.0D), 0.0D, Math.cos(passed / 300.0D));
        
        UtilAction.velocity(coin, vel, Math.abs(Math.sin(passed / 3000.0D)), false, 0.0D, 0.2D + Math.abs(Math.cos(passed / 3000.0D)) * 0.8D, 1.0D, false);
        
        coin.setPickupDelay(40);
        
        this._coins.add(coin);
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
    if (this._active.keySet().contains(event.getItem()))
    {
      event.setCancelled(true);
    }
    else if (this._coins.contains(event.getItem()))
    {
      event.setCancelled(true);
      event.getItem().remove();
      
      this.Manager.getDonationManager().RewardCoinsLater(GetName() + " Pickup", event.getPlayer(), 4);
      
      event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.0F, 2.0F);
    }
  }
  
  @EventHandler
  public void Clean(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    Iterator<Item> coinIterator = this._coins.iterator();
    while (coinIterator.hasNext())
    {
      Item coin = (Item)coinIterator.next();
      if ((!coin.isValid()) || (coin.getTicksLived() > 1200))
      {
        coin.remove();
        coinIterator.remove();
      }
    }
  }
}
