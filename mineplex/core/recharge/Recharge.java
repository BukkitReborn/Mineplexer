package mineplex.core.recharge;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import mineplex.core.MiniPlugin;
import mineplex.core.account.event.ClientUnloadEvent;
import mineplex.core.common.util.F;
import mineplex.core.common.util.NautHashMap;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.common.util.UtilTime.TimeUnit;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Recharge
  extends MiniPlugin
{
  public static Recharge Instance;
  public HashSet<String> informSet = new HashSet();
  public NautHashMap<String, NautHashMap<String, RechargeData>> _recharge = new NautHashMap();
  
  protected Recharge(JavaPlugin plugin)
  {
    super("Recharge", plugin);
  }
  
  public static void Initialize(JavaPlugin plugin)
  {
    Instance = new Recharge(plugin);
  }
  
  @EventHandler
  public void PlayerDeath(PlayerDeathEvent event)
  {
    Get(event.getEntity().getName()).clear();
  }
  
  public NautHashMap<String, RechargeData> Get(String name)
  {
    if (!this._recharge.containsKey(name)) {
      this._recharge.put(name, new NautHashMap());
    }
    return (NautHashMap)this._recharge.get(name);
  }
  
  public NautHashMap<String, RechargeData> Get(Player player)
  {
    return Get(player.getName());
  }
  
  @EventHandler
  public void update(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    recharge();
  }
  
  public void recharge()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player cur = arrayOfPlayer[i];
      
      LinkedList<String> rechargeList = new LinkedList();
      for (String ability : Get(cur).keySet()) {
        if (((RechargeData)Get(cur).get(ability)).Update()) {
          rechargeList.add(ability);
        }
      }
      for (String ability : rechargeList)
      {
        Get(cur).remove(ability);
        
        RechargedEvent rechargedEvent = new RechargedEvent(cur, ability);
        UtilServer.getServer().getPluginManager().callEvent(rechargedEvent);
        if (this.informSet.contains(ability)) {
          UtilPlayer.message(cur, F.main("Recharge", "You can use " + F.skill(ability) + "."));
        }
      }
    }
  }
  
  public boolean use(Player player, String ability, long recharge, boolean inform, boolean attachItem)
  {
    return use(player, ability, ability, recharge, inform, attachItem);
  }
  
  public boolean use(Player player, String ability, String abilityFull, long recharge, boolean inform, boolean attachItem)
  {
    return use(player, ability, abilityFull, recharge, inform, attachItem, false);
  }
  
  public boolean use(Player player, String ability, long recharge, boolean inform, boolean attachItem, boolean attachDurability)
  {
    return use(player, ability, ability, recharge, inform, attachItem, attachDurability);
  }
  
  public boolean use(Player player, String ability, String abilityFull, long recharge, boolean inform, boolean attachItem, boolean attachDurability)
  {
    if (recharge == 0L) {
      return true;
    }
    recharge();
    if ((inform) && (recharge > 1000L)) {
      this.informSet.add(ability);
    }
    if (Get(player).containsKey(ability))
    {
      if (inform) {
        UtilPlayer.message(player, F.main("Recharge", "You cannot use " + F.skill(abilityFull) + " for " + 
          F.time(UtilTime.convertString(((RechargeData)Get(player).get(ability)).GetRemaining(), 1, UtilTime.TimeUnit.FIT)) + "."));
      }
      return false;
    }
    UseRecharge(player, ability, recharge, attachItem, attachDurability);
    
    return true;
  }
  
  public void useForce(Player player, String ability, long recharge)
  {
    useForce(player, ability, recharge, false);
  }
  
  public void useForce(Player player, String ability, long recharge, boolean attachItem)
  {
    UseRecharge(player, ability, recharge, attachItem, false);
  }
  
  public boolean usable(Player player, String ability)
  {
    return usable(player, ability, false);
  }
  
  public boolean usable(Player player, String ability, boolean inform)
  {
    if (!Get(player).containsKey(ability)) {
      return true;
    }
    if (((RechargeData)Get(player).get(ability)).GetRemaining() <= 0L) {
      return true;
    }
    if (inform) {
      UtilPlayer.message(player, F.main("Recharge", "You cannot use " + F.skill(ability) + " for " + 
        F.time(UtilTime.convertString(((RechargeData)Get(player).get(ability)).GetRemaining(), 1, UtilTime.TimeUnit.FIT)) + "."));
    }
    return false;
  }
  
  public void UseRecharge(Player player, String ability, long recharge, boolean attachItem, boolean attachDurability)
  {
    RechargeEvent rechargeEvent = new RechargeEvent(player, ability, recharge);
    UtilServer.getServer().getPluginManager().callEvent(rechargeEvent);
    
    Get(player).put(ability, new RechargeData(this, player, ability, player.getItemInHand(), 
      rechargeEvent.GetRecharge(), attachItem, attachDurability));
  }
  
  public void recharge(Player player, String ability)
  {
    Get(player).remove(ability);
  }
  
  @EventHandler
  public void clearPlayer(ClientUnloadEvent event)
  {
    this._recharge.remove(event.GetName());
  }
  
  public void setDisplayForce(Player player, String ability, boolean displayForce)
  {
    if (!this._recharge.containsKey(player.getName())) {
      return;
    }
    if (!((NautHashMap)this._recharge.get(player.getName())).containsKey(ability)) {
      return;
    }
    ((RechargeData)((NautHashMap)this._recharge.get(player.getName())).get(ability)).DisplayForce = displayForce;
  }
  
  public void setCountdown(Player player, String ability, boolean countdown)
  {
    if (!this._recharge.containsKey(player.getName())) {
      return;
    }
    if (!((NautHashMap)this._recharge.get(player.getName())).containsKey(ability)) {
      return;
    }
    ((RechargeData)((NautHashMap)this._recharge.get(player.getName())).get(ability)).Countdown = countdown;
  }
  
  public void Reset(Player player)
  {
    this._recharge.put(player.getName(), new NautHashMap());
  }
  
  public void Reset(Player player, String stringContains)
  {
    NautHashMap<String, RechargeData> data = (NautHashMap)this._recharge.get(player.getName());
    if (data == null) {
      return;
    }
    Iterator<String> rechargeIter = data.keySet().iterator();
    while (rechargeIter.hasNext())
    {
      String key = (String)rechargeIter.next();
      if (key.toLowerCase().contains(stringContains.toLowerCase())) {
        rechargeIter.remove();
      }
    }
  }
  
  public void debug(Player player, String ability)
  {
    if (!this._recharge.containsKey(player.getName()))
    {
      player.sendMessage("No Recharge Map.");
      return;
    }
    if (!((NautHashMap)this._recharge.get(player.getName())).containsKey(ability))
    {
      player.sendMessage("Ability Not Found.");
      return;
    }
    ((RechargeData)((NautHashMap)this._recharge.get(player.getName())).get(ability)).debug(player);
  }
}
