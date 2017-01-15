package mineplex.core.gadget.gadgets;

import java.util.UUID;
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
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class MorphNotch
  extends MorphGadget
{
  private GameProfile _notchProfile = null;
  
  public MorphNotch(GadgetManager manager)
  {
    super(manager, "Notch", new String[] {"Who wouldn't want to be Notch?!" }, 50000, Material.SKULL_ITEM, (byte)3);
    
    this._notchProfile = new ProfileLoader(UUIDFetcher.getUUIDOf("Notch").toString(), "Notch").loadProfile();
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguisePlayer disguise = new DisguisePlayer(player, this._notchProfile);
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    this.Manager.getDisguiseManager().undisguise(player);
  }
  
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
    player.sendMessage("You have enforced the EULA.");
  }
}
