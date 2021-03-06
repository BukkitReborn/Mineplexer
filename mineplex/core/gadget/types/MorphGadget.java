package mineplex.core.gadget.types;

import java.util.HashSet;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.gadget.GadgetManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public abstract class MorphGadget
  extends Gadget
{
  public MorphGadget(GadgetManager manager, String name, String[] desc, int cost, Material mat, byte data)
  {
    super(manager, GadgetType.Morph, name, desc, cost, mat, data);
  }
  
  public void ApplyArmor(Player player)
  {
    this.Manager.RemoveMorph(player);
    
    this._active.add(player);
    
    UtilPlayer.message(player, F.main("Gadget", "You morphed into " + F.elem(GetName()) + "."));
  }
  
  public void RemoveArmor(Player player)
  {
    if (this._active.remove(player)) {
      UtilPlayer.message(player, F.main("Gadget", "You unmorphed from " + F.elem(GetName()) + "."));
    }
  }
  
  @EventHandler
  public void playerDeath(PlayerDeathEvent event)
  {
    Disable(event.getEntity());
  }
}
