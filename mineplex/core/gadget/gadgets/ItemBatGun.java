package mineplex.core.gadget.gadgets;

import java.util.ArrayList;
import java.util.HashMap;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilAction;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilEnt;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.preferences.UserPreferences;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.util.Vector;

public class ItemBatGun
  extends ItemGadget
{
  private HashMap<Player, Long> _active = new HashMap();
  private HashMap<Player, Location> _velocity = new HashMap();
  private HashMap<Player, ArrayList<Bat>> _bats = new HashMap();
  
  public ItemBatGun(GadgetManager manager)
  {
    super(manager, "Bat Blaster", new String[] {C.cWhite + "Launch waves of annoying bats", C.cWhite + "at people you don't like!" }, -1, Material.IRON_BARDING, (byte)0, 5000L, new Ammo("Bat Blaster", "50 Bats", Material.IRON_BARDING, (byte)0, new String[] { C.cWhite + "50 Bats for your Bat Blaster!" }, 500, 50));
  }
  
  public void DisableCustom(Player player)
  {
    super.DisableCustom(player);
    
    Clear(player);
  }
  
  public void ActivateCustom(Player player)
  {
    this._velocity.put(player, player.getEyeLocation());
    this._active.put(player, Long.valueOf(System.currentTimeMillis()));
    
    this._bats.put(player, new ArrayList());
    for (int i = 0; i < 16; i++) {
      ((ArrayList)this._bats.get(player)).add((Bat)player.getWorld().spawn(player.getEyeLocation(), Bat.class));
    }
    UtilPlayer.message(player, F.main("Skill", "You used " + F.skill(GetName()) + "."));
  }
  
  @EventHandler
  public void Update(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    Player[] arrayOfPlayer1;
    int j = (arrayOfPlayer1 = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player cur = arrayOfPlayer1[i];
      if (this._active.containsKey(cur)) {
        if (UtilTime.elapsed(((Long)this._active.get(cur)).longValue(), 3000L))
        {
          Clear(cur);
        }
        else
        {
          Location loc = (Location)this._velocity.get(cur);
          for (Bat bat : (ArrayList)this._bats.get(cur)) {
            if (bat.isValid())
            {
              Vector rand = new Vector((Math.random() - 0.5D) / 3.0D, (Math.random() - 0.5D) / 3.0D, (Math.random() - 0.5D) / 3.0D);
              bat.setVelocity(loc.getDirection().clone().multiply(0.5D).add(rand));
              Player[] arrayOfPlayer2;
              int m = (arrayOfPlayer2 = UtilServer.getPlayers()).length;
              for (int k = 0; k < m; k++)
              {
                Player other = arrayOfPlayer2[k];
                if (!other.equals(cur)) {
                  if ((((UserPreferences)this.Manager.getPreferencesManager().Get(other)).HubGames) && (((UserPreferences)this.Manager.getPreferencesManager().Get(other)).ShowPlayers)) {
                    if (Recharge.Instance.usable(other, "Hit by Bat")) {
                      if (UtilEnt.hitBox(bat.getLocation(), other, 2.0D, null)) {
                        if (!this.Manager.collideEvent(this, other))
                        {
                          UtilAction.velocity(other, UtilAlg.getTrajectory(cur, other), 0.4D, false, 0.0D, 0.2D, 10.0D, true);
                          
                          bat.getWorld().playSound(bat.getLocation(), Sound.BAT_HURT, 1.0F, 1.0F);
                          UtilParticle.PlayParticle(UtilParticle.ParticleType.LARGE_SMOKE, bat.getLocation(), 0.0F, 0.0F, 0.0F, 0.0F, 3, 
                            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
                          
                          bat.remove();
                          
                          Recharge.Instance.useForce(other, "Hit by Bat", 200L);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
  }
  
  public void Clear(Player player)
  {
    this._active.remove(player);
    this._velocity.remove(player);
    if (this._bats.containsKey(player))
    {
      for (Bat bat : (ArrayList)this._bats.get(player))
      {
        if (bat.isValid()) {
          UtilParticle.PlayParticle(UtilParticle.ParticleType.LARGE_SMOKE, bat.getLocation(), 0.0F, 0.0F, 0.0F, 0.0F, 3, 
            UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
        }
        bat.remove();
      }
      this._bats.remove(player);
    }
  }
}
