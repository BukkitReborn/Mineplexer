package mineplex.core.gadget.gadgets;

import java.util.UUID;
import mineplex.core.common.util.C;
import mineplex.core.common.util.ProfileLoader;
import mineplex.core.common.util.UUIDFetcher;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguisePlayer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.recharge.Recharge;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class MorphGeno
  extends MorphGadget
{
  private GameProfile _profile = null;
  
  public MorphGeno(GadgetManager manager)
  {
    super(manager, "Genocide604", new String[] {"Say goodbye to Genocide604 by burping", "and eating a lot.", " ", C.cYellow + "Left Click" + C.cGray + " to use " + C.cGreen + "Burp", "  ", C.cRed + C.Bold + "WARNING: " + ChatColor.RESET + "This is a temporary item!" }, 10, Material.SKULL_ITEM, (byte)3);
    
    this._profile = new ProfileLoader(UUIDFetcher.getUUIDOf("Genocide604").toString(), "Genocide604").loadProfile();
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguisePlayer disguise = new DisguisePlayer(player, this._profile);
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    this.Manager.getDisguiseManager().undisguise(player);
  }
  
  @EventHandler
  public void Action(PlayerInteractEvent event)
  {
    Player player = event.getPlayer();
    if (!IsActive(player)) {
      return;
    }
    if (!UtilEvent.isAction(event, UtilEvent.ActionType.L)) {
      return;
    }
    if (!Recharge.Instance.use(player, GetName(), 1500L, false, false)) {
      return;
    }
    player.getWorld().playSound(player.getEyeLocation(), Sound.BURP, 1.0F, (float)(0.800000011920929D + Math.random() * 0.4000000059604645D));
  }
}
