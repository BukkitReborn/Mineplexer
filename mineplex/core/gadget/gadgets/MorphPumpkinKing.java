package mineplex.core.gadget.gadgets;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseSkeleton;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.visibility.VisibilityManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MorphPumpkinKing
  extends MorphGadget
{
  public MorphPumpkinKing(GadgetManager manager)
  {
    super(manager, "Pumpkin Kings Head", new String[] {C.cWhite + "Transforms the wearer into", C.cWhite + "the dreaded Pumpkin King!", "", C.cYellow + "Earned by defeating the Pumpkin King", C.cYellow + "in the 2013 Halloween Horror Event." }, -1, Material.PUMPKIN, (byte)0);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguiseSkeleton disguise = new DisguiseSkeleton(player);
    disguise.showArmor();
    disguise.setName(player.getName(), this.Manager.getClientManager().Get(player).GetRank());
    disguise.setCustomNameVisible(true);
    disguise.SetSkeletonType(Skeleton.SkeletonType.WITHER);
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
    
    player.getInventory().setHelmet(new ItemStack(Material.JACK_O_LANTERN));
    
    VisibilityManager.Instance.setVisibility(player, false, UtilServer.getPlayers());
    VisibilityManager.Instance.setVisibility(player, true, UtilServer.getPlayers());
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    this.Manager.getDisguiseManager().undisguise(player);
    player.getInventory().setHelmet(null);
  }
}
