package mineplex.core.antihack.types;

import java.util.ArrayList;
import java.util.HashMap;
import mineplex.core.MiniPlugin;
import mineplex.core.antihack.AntiHack;
import mineplex.core.antihack.Detector;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class Reach
  extends MiniPlugin
  implements Detector
{
  private AntiHack Host;
  private HashMap<Player, ArrayList<Location>> _history = new HashMap();
  
  public Reach(AntiHack host)
  {
    super("Reach Detector", host.getPlugin());
    this.Host = host;
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void recordMove(UpdateEvent event)
  {
    if (!this.Host.isEnabled()) {
      return;
    }
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      if ((player.getGameMode() == GameMode.SURVIVAL) && (!UtilPlayer.isSpectator(player)))
      {
        if (!this._history.containsKey(player)) {
          this._history.put(player, new ArrayList());
        }
        ((ArrayList)this._history.get(player)).add(0, player.getLocation());
        while (((ArrayList)this._history.get(player)).size() > 40) {
          ((ArrayList)this._history.get(player)).remove(((ArrayList)this._history.get(player)).size() - 1);
        }
      }
    }
  }
  
  @EventHandler(priority=EventPriority.LOWEST)
  public void reachDetect(EntityDamageEvent event)
  {
    if (event.getCause() != EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
      return;
    }
    if (!(event.getEntity() instanceof Player)) {
      return;
    }
    LivingEntity damagerEntity = UtilEvent.GetDamagerEntity(event, false);
    if (!(damagerEntity instanceof Player)) {
      return;
    }
    Player damager = (Player)damagerEntity;
    Player damagee = (Player)event.getEntity();
    ArrayList<Location> damageeHistory;
    if ((!isInRange(damager.getLocation(), damagee.getLocation())) && ((damageeHistory = (ArrayList)this._history.get(damagee)) != null)) {
      for (Location historyLoc : damageeHistory) {
        if (isInRange(damager.getLocation(), historyLoc)) {
          return;
        }
      }
    }
  }
  
  private boolean isInRange(Location a, Location b)
  {
    double distFlat = UtilMath.offset2d(a, b);
    if (distFlat > 3.4D) {
      return true;
    }
    double dist = UtilMath.offset(a, b);
    if (dist > 6.0D) {
      return true;
    }
    return false;
  }
  
  public void Reset(Player player)
  {
    this._history.remove(player);
  }
}
