package mineplex.core.gadget.gadgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ItemTNT
  extends ItemGadget
{
  private HashSet<TNTPrimed> _tnt = new HashSet();
  
  public ItemTNT(GadgetManager manager)
  {
    super(manager, "TNT", new String[] {C.cWhite + "Blow some people up!", C.cWhite + "KABOOM!" }, -1, Material.TNT, (byte)0, 1000L, new Ammo("TNT", "20 TNT", Material.TNT, (byte)0, new String[] { C.cWhite + "20 TNT for you to explode!" }, 500, 20));
  }
  
  public void ActivateCustom(Player player)
  {
    TNTPrimed tnt = (TNTPrimed)player.getWorld().spawn(player.getEyeLocation().add(player.getLocation().getDirection()), TNTPrimed.class);
    UtilAction.velocity(tnt, player.getLocation().getDirection(), 0.6D, false, 0.0D, 0.2D, 1.0D, false);
    this._tnt.add(tnt);
    
    UtilPlayer.message(player, F.main("Skill", "You threw " + F.skill(GetName()) + "."));
  }
  
  @EventHandler
  public void Update(EntityExplodeEvent event)
  {
    if (!(event.getEntity() instanceof TNTPrimed)) {
      return;
    }
    if (!this._tnt.remove(event.getEntity())) {
      return;
    }
    HashMap<Player, Double> players = UtilPlayer.getInRadius(event.getLocation(), 10.0D);
    for (Player player : players.keySet()) {
      if (!this.Manager.collideEvent(this, player))
      {
        double mult = ((Double)players.get(player)).doubleValue();
        
        UtilAction.velocity(player, UtilAlg.getTrajectory(event.getLocation(), player.getLocation()), 3.0D * mult, false, 0.0D, 0.5D + 2.0D * mult, 10.0D, true);
      }
    }
  }
  
  @EventHandler
  public void Clean(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FAST) {
      return;
    }
    Iterator<TNTPrimed> tntIterator = this._tnt.iterator();
    while (tntIterator.hasNext())
    {
      TNTPrimed tnt = (TNTPrimed)tntIterator.next();
      if ((!tnt.isValid()) || (tnt.getTicksLived() > 200))
      {
        tnt.remove();
        tntIterator.remove();
      }
    }
  }
}
