package mineplex.core.gadget.gadgets;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseCow;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.recharge.Recharge;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class MorphCow
  extends MorphGadget
{
  public MorphCow(GadgetManager manager)
  {
    super(manager, "Cow Morph", new String[] {C.cWhite + "How now brown cow?", " ", C.cYellow + "Left Click" + C.cGray + " to use " + C.cGreen + "Moo" }, 6000, Material.LEATHER, (byte)0);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguiseCow disguise = new DisguiseCow(player);
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
  public void Audio(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (!IsActive(player)) {
      return;
    }
    if (!UtilEvent.isAction(event, UtilEvent.ActionType.L)) {
      return;
    }
    if (!Recharge.Instance.use(player, GetName(), 2500L, false, false)) {
      return;
    }
    player.getWorld().playSound(player.getLocation(), Sound.COW_IDLE, 1.0F, 1.0F);
  }
}
