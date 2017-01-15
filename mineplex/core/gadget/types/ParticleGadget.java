package mineplex.core.gadget.types;

import java.util.HashSet;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.gadget.GadgetManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class ParticleGadget
  extends Gadget
{
  public ParticleGadget(GadgetManager manager, String name, String[] desc, int cost, Material mat, byte data)
  {
    super(manager, GadgetType.Particle, name, desc, cost, mat, data);
  }
  
  public void EnableCustom(Player player)
  {
    this.Manager.RemoveParticle(player);
    
    this._active.add(player);
    
    UtilPlayer.message(player, F.main("Gadget", "You summoned " + F.elem(GetName()) + "."));
  }
  
  public void DisableCustom(Player player)
  {
    if (this._active.remove(player)) {
      UtilPlayer.message(player, F.main("Gadget", "You unsummoned " + F.elem(GetName()) + "."));
    }
  }
  
  public boolean shouldDisplay(Player player)
  {
    if (UtilPlayer.isSpectator(player)) {
      return false;
    }
    if (this.Manager.hideParticles()) {
      return false;
    }
    return true;
  }
}
