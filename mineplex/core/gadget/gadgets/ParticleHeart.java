package mineplex.core.gadget.gadgets;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ParticleGadget;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

public class ParticleHeart
  extends ParticleGadget
{
  private HashMap<Player, HashMap<Player, Location>> _target = new HashMap();
  
  public ParticleHeart(GadgetManager manager)
  {
    super(manager, "I Heart You", new String[] {C.cWhite + "With these particles, you can", C.cWhite + "show off how much you heart", C.cWhite + "everyone on Mineplex!" }, -2, Material.APPLE, (byte)0);
  }
  
  @EventHandler
  public void playParticle(UpdateEvent event)
  {
    if (event.getType() != UpdateType.FASTEST) {
      return;
    }
    for (Player player : GetActive()) {
      if (shouldDisplay(player))
      {
        if (!this._target.containsKey(player)) {
          this._target.put(player, new HashMap());
        }
        if (Recharge.Instance.use(player, GetName(), 500L, false, false))
        {
          Player[] arrayOfPlayer;
          int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
          for (int i = 0; i < j; i++)
          {
            Player other = arrayOfPlayer[i];
            if (!other.equals(player)) {
              if (UtilPlayer.isSpectator(other)) {
                if (!((HashMap)this._target.get(player)).containsKey(other)) {
                  if (UtilMath.offset(player, other) <= 6.0D)
                  {
                    ((HashMap)this._target.get(player)).put(other, player.getLocation().add(0.0D, 1.0D, 0.0D));
                    
                    break;
                  }
                }
              }
            }
          }
        }
        if (this.Manager.isMoving(player)) {
          UtilParticle.PlayParticle(UtilParticle.ParticleType.HEART, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        } else {
          UtilParticle.PlayParticle(UtilParticle.ParticleType.HEART, player.getLocation().add(0.0D, 1.0D, 0.0D), 0.5F, 0.5F, 0.5F, 0.0F, 1, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
      }
    }
    Iterator<Map.Entry<Player, Location>> heartIterator;
    for (??? = this._target.values().iterator(); ???.hasNext(); heartIterator.hasNext())
    {
      HashMap<Player, Location> heart = (HashMap)???.next();
      
      heartIterator = heart.entrySet().iterator();
      
      continue;
      
      Object entry = (Map.Entry)heartIterator.next();
      
      ((Location)((Map.Entry)entry).getValue()).add(UtilAlg.getTrajectory((Location)((Map.Entry)entry).getValue(), ((Player)((Map.Entry)entry).getKey()).getEyeLocation()).multiply(0.6D));
      
      UtilParticle.PlayParticle(UtilParticle.ParticleType.HEART, (Location)((Map.Entry)entry).getValue(), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
        UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
      if (UtilMath.offset((Location)((Map.Entry)entry).getValue(), ((Player)((Map.Entry)entry).getKey()).getEyeLocation()) < 0.6D) {
        heartIterator.remove();
      }
    }
  }
  
  public void DisableCustom(Player player)
  {
    if (this._active.remove(player)) {
      UtilPlayer.message(player, F.main("Gadget", "You unsummoned " + F.elem(GetName()) + "."));
    }
    clean(player);
  }
  
  @EventHandler
  public void quit(PlayerQuitEvent event)
  {
    clean(event.getPlayer());
  }
  
  private void clean(Player player)
  {
    this._target.remove(player);
    for (HashMap<Player, Location> map : this._target.values()) {
      map.remove(player);
    }
  }
}
