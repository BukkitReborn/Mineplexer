package mineplex.core.gadget.gadgets;

import java.util.HashSet;
import java.util.Iterator;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseVillager;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.projectile.IThrown;
import mineplex.core.projectile.ProjectileManager;
import mineplex.core.projectile.ProjectileUser;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class MorphVillager
  extends MorphGadget
  implements IThrown
{
  private HashSet<Item> _gems = new HashSet();
  
  public MorphVillager(GadgetManager manager)
  {
    super(manager, "Villager Morph", new String[] {C.cWhite + "HURRRR! MURR HURRR!", " ", C.cYellow + "Left Click" + C.cGray + " to use " + C.cGreen + "Gem Throw", " ", C.cRed + C.Bold + "WARNING: " + ChatColor.RESET + "Gem Throw uses 20 Gems" }, 12000, Material.EMERALD, (byte)0);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguiseVillager disguise = new DisguiseVillager(player);
    disguise.setName(player.getName(), this.Manager.getClientManager().Get(player).GetRank());
    disguise.setCustomNameVisible(true);
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    this.Manager.getDisguiseManager().undisguise(player);
  }
  
  @EventHandler
  public void skill(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (!IsActive(player)) {
      return;
    }
    if (!UtilEvent.isAction(event, UtilEvent.ActionType.L)) {
      return;
    }
    if (((Donor)this.Manager.getDonationManager().Get(player.getName())).GetBalance(CurrencyType.Gems) < 20)
    {
      UtilPlayer.message(player, F.main("Gadget", "You do not have enough Gems."));
      return;
    }
    if (!Recharge.Instance.use(player, GetName(), 800L, false, false)) {
      return;
    }
    player.getWorld().playSound(player.getLocation(), Sound.VILLAGER_IDLE, 1.0F, 1.0F);
    
    Item gem = player.getWorld().dropItem(player.getEyeLocation().add(player.getLocation().getDirection()), new ItemStack(Material.EMERALD));
    UtilAction.velocity(gem, player.getLocation().getDirection(), 1.0D, false, 0.0D, 0.2D, 1.0D, false);
    
    this.Manager.getProjectileManager().AddThrow(gem, player, this, -1L, true, true, true, 
      null, 1.4F, 0.8F, null, null, 0, UpdateType.TICK, 0.5F);
    
    this.Manager.getDonationManager().RewardGems(null, GetName() + " Throw", player.getName(), player.getUniqueId(), -20);
    
    gem.setPickupDelay(40);
    
    this._gems.add(gem);
  }
  
  public void Collide(LivingEntity target, Block block, ProjectileUser data)
  {
    if (target == null) {
      return;
    }
    if (((target instanceof Player)) && 
      (this.Manager.collideEvent(this, (Player)target))) {
      return;
    }
    UtilAction.velocity(target, 
      UtilAlg.getTrajectory(data.GetThrown().getLocation(), target.getEyeLocation()), 
      1.0D, false, 0.0D, 0.2D, 0.8D, true);
    
    UtilAction.velocity(data.GetThrown(), 
      UtilAlg.getTrajectory(target, data.GetThrown()), 
      0.5D, false, 0.0D, 0.0D, 0.8D, true);
    
    target.playEffect(EntityEffect.HURT);
  }
  
  public void Idle(ProjectileUser data) {}
  
  public void Expire(ProjectileUser data) {}
  
  @EventHandler
  public void Pickup(PlayerPickupItemEvent event)
  {
    if (this._gems.contains(event.getItem()))
    {
      event.setCancelled(true);
      event.getItem().remove();
      
      this.Manager.getDonationManager().RewardGemsLater(GetName() + " Pickup", event.getPlayer(), 16);
      
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
