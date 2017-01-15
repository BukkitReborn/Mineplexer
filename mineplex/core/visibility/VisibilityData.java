package mineplex.core.visibility;

import java.util.Iterator;
import java.util.Set;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.recharge.Recharge;
import mineplex.core.timing.TimingManager;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class VisibilityData
{
  private NautHashMap<Player, Boolean> _shouldHide = new NautHashMap();
  private NautHashMap<Player, Boolean> _lastState = new NautHashMap();
  
  public void updatePlayer(Player player, Player target, boolean hide)
  {
    TimingManager.stopTotal("VisData updatePlayer");
    if ((this._lastState.containsKey(target)) && (((Boolean)this._lastState.get(target)).booleanValue() == hide))
    {
      TimingManager.stopTotal("VisData updatePlayer");
      return;
    }
    if (attemptToProcess(player, target, hide)) {
      this._shouldHide.remove(target);
    } else {
      this._shouldHide.put(target, Boolean.valueOf(hide));
    }
    TimingManager.stopTotal("VisData updatePlayer");
  }
  
  private boolean attemptToProcess(Player player, Player target, boolean hide)
  {
    TimingManager.startTotal("VisData attemptToProcess");
    if (Recharge.Instance.use(player, "VIS " + target.getName(), 250L, false, false))
    {
      if (hide)
      {
        TimingManager.startTotal("Hide Player");
        ((CraftPlayer)player).hidePlayer(target, true, true);
        TimingManager.stopTotal("Hide Player");
      }
      else
      {
        TimingManager.startTotal("Show Player");
        player.showPlayer(target);
        TimingManager.stopTotal("Show Player");
      }
      this._lastState.put(target, Boolean.valueOf(hide));
      
      TimingManager.stopTotal("VisData attemptToProcess");
      return true;
    }
    TimingManager.stopTotal("VisData attemptToProcess");
    return false;
  }
  
  public void attemptToProcessUpdate(Player player)
  {
    TimingManager.startTotal("VisData attemptToProcessUpdate shouldHide");
    if (!this._shouldHide.isEmpty()) {
      for (Iterator<Player> targetIter = this._shouldHide.keySet().iterator(); targetIter.hasNext();)
      {
        Player target = (Player)targetIter.next();
        boolean hide = ((Boolean)this._shouldHide.get(target)).booleanValue();
        if ((!target.isOnline()) || (!target.isValid()) || (attemptToProcess(player, target, hide))) {
          targetIter.remove();
        }
      }
    }
    TimingManager.stopTotal("VisData attemptToProcessUpdate shouldHide");
    
    TimingManager.startTotal("VisData attemptToProcessUpdate lastState");
    if (!this._lastState.isEmpty()) {
      for (Iterator<Player> targetIter = this._lastState.keySet().iterator(); targetIter.hasNext();)
      {
        Player target = (Player)targetIter.next();
        if ((!target.isOnline()) || (!target.isValid())) {
          targetIter.remove();
        }
      }
    }
    TimingManager.stopTotal("VisData attemptToProcessUpdate lastState");
  }
}
