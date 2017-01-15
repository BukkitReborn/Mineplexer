package mineplex.core.gadget.gadgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.common.util.UtilFirework;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseRabbit;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MorphBunny
  extends MorphGadget
{
  private HashSet<Player> _jumpCharge = new HashSet();
  private HashMap<Item, String> _eggs = new HashMap();
  
  public MorphBunny(GadgetManager manager)
  {
    super(manager, "Easter Bunny Morph", new String[] {C.cWhite + "Happy Easter!", " ", C.cYellow + "Charge Crouch" + C.cGray + " to use " + C.cGreen + "Super Jump", C.cYellow + "Left Click" + C.cGray + " to use " + C.cGreen + "Hide Easter Egg", " ", C.cRed + C.Bold + "WARNING: " + ChatColor.RESET + "Hide Easter Egg uses 500 Shards", " ", C.cPurple + "Special Limited Time Morph", C.cPurple + "Purchase at www.mineplex.com/shop" }, -1, Material.MONSTER_EGG, (byte)98);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguiseRabbit disguise = new DisguiseRabbit(player);
    disguise.setName(player.getName(), this.Manager.getClientManager().Get(player).GetRank());
    disguise.setCustomNameVisible(true);
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
    
    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 1));
  }
  
  public void DisableCustom(Player player)
  {
    this._jumpCharge.remove(player);
    RemoveArmor(player);
    this.Manager.getDisguiseManager().undisguise(player);
    
    player.removePotionEffect(PotionEffectType.SPEED);
    player.removePotionEffect(PotionEffectType.JUMP);
  }
  
  @EventHandler
  public void jumpTrigger(PlayerToggleSneakEvent event)
  {
    Player player = event.getPlayer();
    if (!IsActive(player)) {
      return;
    }
    if (!event.getPlayer().isSneaking())
    {
      if (UtilEnt.isGrounded(event.getPlayer())) {
        this._jumpCharge.add(event.getPlayer());
      }
    }
    else if (this._jumpCharge.remove(event.getPlayer()))
    {
      float power = player.getExp();
      player.setExp(0.0F);
      
      UtilAction.velocity(player, power * 4.0F, 0.4D, 4.0D, true);
      
      player.getWorld().playSound(player.getLocation(), Sound.CAT_HIT, 0.75F, 2.0F);
    }
  }
  
  @EventHandler
  public void jumpBoost(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    Iterator<Player> jumpIter = this._jumpCharge.iterator();
    while (jumpIter.hasNext())
    {
      Player player = (Player)jumpIter.next();
      if ((!player.isValid()) || (!player.isOnline()) || (!player.isSneaking()))
      {
        jumpIter.remove();
      }
      else
      {
        player.setExp(Math.min(0.9999F, player.getExp() + 0.03F));
        
        player.playSound(player.getLocation(), Sound.FIZZ, 0.25F + player.getExp() * 0.5F, 0.5F + player.getExp());
      }
    }
  }
  
  @EventHandler
  public void eggHide(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (!IsActive(player)) {
      return;
    }
    if (!UtilEvent.isAction(event, UtilEvent.ActionType.L)) {
      return;
    }
    if (((Donor)this.Manager.getDonationManager().Get(player.getName())).GetBalance(CurrencyType.Coins) < 500)
    {
      UtilPlayer.message(player, F.main("Gadget", "You do not have enough Coins."));
      return;
    }
    if (!Recharge.Instance.use(player, "Hide Egg", 30000L, true, false)) {
      return;
    }
    ItemStack eggStack = ItemStackFactory.Instance.CreateStack(Material.MONSTER_EGG, (byte)0, 1, "Hidden Egg" + System.currentTimeMillis());
    eggStack.setDurability((short)98);
    
    Item egg = player.getWorld().dropItem(player.getEyeLocation().add(player.getLocation().getDirection()), eggStack);
    UtilAction.velocity(egg, player.getLocation().getDirection(), 0.2D, false, 0.0D, 0.2D, 1.0D, false);
    
    this.Manager.getDonationManager().RewardCoinsLater(GetName() + " Egg Hide", player, 65036);
    
    egg.setPickupDelay(40);
    
    this._eggs.put(egg, player.getName());
    
    Bukkit.broadcastMessage(C.cYellow + C.Bold + player.getName() + 
      ChatColor.RESET + C.Bold + " hid an " + 
      C.cYellow + C.Bold + "Easter Egg" + 
      ChatColor.RESET + C.Bold + " worth " + 
      C.cYellow + C.Bold + "450 Coins");
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player other = arrayOfPlayer[i];
      other.playSound(other.getLocation(), Sound.CAT_HIT, 1.5F, 1.5F);
    }
  }
  
  @EventHandler
  public void eggPickup(PlayerPickupItemEvent event)
  {
    if ((this._eggs.containsKey(event.getItem())) && (!((String)this._eggs.get(event.getItem())).equals(event.getPlayer().getName())))
    {
      this._eggs.remove(event.getItem());
      
      event.setCancelled(true);
      event.getItem().remove();
      
      this.Manager.getDonationManager().RewardCoinsLater(GetName() + " Egg Pickup", event.getPlayer(), 450);
      
      event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.5F, 0.75F);
      event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ORB_PICKUP, 1.5F, 1.25F);
      
      UtilFirework.playFirework(event.getItem().getLocation(), FireworkEffect.Type.BURST, Color.YELLOW, true, true);
      
      Bukkit.broadcastMessage(C.cGold + C.Bold + event.getPlayer().getName() + 
        ChatColor.RESET + C.Bold + " found an " + 
        C.cGold + C.Bold + "Easter Egg" + 
        ChatColor.RESET + C.Bold + "! " + this._eggs.size() + " Eggs left!");
    }
  }
  
  @EventHandler
  public void eggClean(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    Iterator<Item> eggIter = this._eggs.keySet().iterator();
    while (eggIter.hasNext())
    {
      Item egg = (Item)eggIter.next();
      if ((!egg.isValid()) || (egg.getTicksLived() > 24000))
      {
        egg.remove();
        eggIter.remove();
        
        Bukkit.broadcastMessage(
          ChatColor.RESET + C.Bold + "No one found an " + 
          C.cGold + C.Bold + "Easter Egg" + 
          ChatColor.RESET + C.Bold + "! " + this._eggs.size() + " Eggs left!");
      }
      else
      {
        UtilParticle.PlayParticle(UtilParticle.ParticleType.SPELL, egg.getLocation().add(0.0D, 0.1D, 0.0D), 0.1F, 0.1F, 0.1F, 0.0F, 1, 
          UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
      }
    }
  }
  
  @EventHandler
  public void eggDespawnCancel(ItemDespawnEvent event)
  {
    if (this._eggs.containsKey(event.getEntity())) {
      event.setCancelled(true);
    }
  }
}
