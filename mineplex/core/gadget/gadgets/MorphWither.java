package mineplex.core.gadget.gadgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseBase;
import mineplex.core.disguise.disguises.DisguiseWither;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.Vector;

public class MorphWither
  extends MorphGadget
{
  private ArrayList<WitherSkull> _skulls = new ArrayList();
  
  public MorphWither(GadgetManager manager)
  {
    super(manager, "Wither Morph", new String[] {C.cWhite + "Become a legendary Wither!", " ", C.cYellow + "Left Click" + C.cGray + " to use " + C.cGreen + "Wither Skull", " ", C.cPurple + "Unlocked with Legend Rank" }, -1, Material.SKULL_ITEM, (byte)1);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    player.setMaxHealth(300.0D);
    player.setHealth(300.0D);
    
    DisguiseWither disguise = new DisguiseWither(player);
    disguise.setName(player.getName(), this.Manager.getClientManager().Get(player).GetRank());
    
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
    
    player.setMaxHealth(20.0D);
    player.setHealth(20.0D);
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    this.Manager.getDisguiseManager().undisguise(player);
    
    player.setAllowFlight(false);
    player.setFlying(false);
    
    player.setMaxHealth(20.0D);
    player.setHealth(20.0D);
  }
  
  @EventHandler
  public void witherSkull(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (!IsActive(player)) {
      return;
    }
    if (!UtilEvent.isAction(event, UtilEvent.ActionType.L)) {
      return;
    }
    if (!Recharge.Instance.use(player, GetName(), 1600L, false, false)) {
      return;
    }
    Vector offset = player.getLocation().getDirection();
    if (offset.getY() < 0.0D) {
      offset.setY(0);
    }
    this._skulls.add((WitherSkull)player.launchProjectile(WitherSkull.class));
    
    player.getWorld().playSound(player.getLocation(), Sound.WITHER_SHOOT, 0.5F, 1.0F);
  }
  
  @EventHandler
  public void explode(EntityExplodeEvent event)
  {
    if (!this._skulls.contains(event.getEntity())) {
      return;
    }
    event.setCancelled(true);
    
    WitherSkull skull = (WitherSkull)event.getEntity();
    
    UtilParticle.PlayParticle(UtilParticle.ParticleType.LARGE_EXPLODE, skull.getLocation(), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
      UtilParticle.ViewDist.MAX, UtilServer.getPlayers());
    skull.getWorld().playSound(skull.getLocation(), Sound.EXPLODE, 2.0F, 1.0F);
    
    HashMap<Player, Double> players = UtilPlayer.getInRadius(event.getLocation(), 6.0D);
    for (Player player : players.keySet()) {
      if (!this.Manager.collideEvent(this, player))
      {
        double mult = ((Double)players.get(player)).doubleValue();
        
        UtilAction.velocity(player, UtilAlg.getTrajectory(event.getLocation(), player.getLocation()), 2.0D * mult, false, 0.0D, 0.6D + 0.4D * mult, 2.0D, true);
      }
    }
  }
  
  @EventHandler
  public void clean(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    Iterator<WitherSkull> skullIterator = this._skulls.iterator();
    while (skullIterator.hasNext())
    {
      WitherSkull skull = (WitherSkull)skullIterator.next();
      if (!skull.isValid())
      {
        skullIterator.remove();
        skull.remove();
      }
    }
  }
  
  @EventHandler
  public void flight(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Player player : GetActive()) {
      if (!UtilPlayer.isSpectator(player))
      {
        player.setAllowFlight(true);
        player.setFlying(true);
        if (UtilEnt.isGrounded(player)) {
          player.setVelocity(new Vector(0, 1, 0));
        }
      }
    }
  }
  
  @EventHandler
  public void legendOwner(PlayerJoinEvent event)
  {
    if ((this.Manager.getClientManager().Get(event.getPlayer()).GetRank() == Rank.LEGEND) || 
      (this.Manager.getClientManager().Get(event.getPlayer()).GetRank() == Rank.ADMIN) || 
      (this.Manager.getClientManager().Get(event.getPlayer()).GetRank() == Rank.DEVELOPER) || 
      (this.Manager.getClientManager().Get(event.getPlayer()).GetRank() == Rank.OWNER)) {
      ((Donor)this.Manager.getDonationManager().Get(event.getPlayer().getName())).AddUnknownSalesPackagesOwned(GetName());
    }
  }
  
  public void setWitherData(String text, double healthPercent)
  {
    Iterator<Player> activeIterator = GetActive().iterator();
    while (activeIterator.hasNext())
    {
      Player player = (Player)activeIterator.next();
      
      DisguiseBase disguise = this.Manager.getDisguiseManager().getDisguise(player);
      if ((disguise == null) || (!(disguise instanceof DisguiseWither)))
      {
        DisableCustom(player);
        activeIterator.remove();
      }
      else
      {
        ((DisguiseWither)disguise).setName(text);
        ((DisguiseWither)disguise).setHealth((float)(healthPercent * 300.0D));
        this.Manager.getDisguiseManager().updateDisguise(disguise);
      }
    }
  }
}
