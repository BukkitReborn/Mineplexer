package mineplex.core.gadget.gadgets;

import java.util.HashSet;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilFirework;
import mineplex.core.common.util.UtilInv;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class ItemEtherealPearl
  extends ItemGadget
{
  private HashSet<String> _riding = new HashSet();
  
  public ItemEtherealPearl(GadgetManager manager)
  {
    super(manager, "Ethereal Pearl", new String[] {C.cWhite + "Take a ride through the skies", C.cWhite + "on your very own Ethereal Pearl!" }, -1, Material.ENDER_PEARL, (byte)0, 500L, new Ammo("Ethereal Pearl", "50 Pearls", Material.ENDER_PEARL, (byte)0, new String[] { C.cWhite + "50 Pearls to get around with!" }, 500, 50));
  }
  
  public void DisableCustom(Player player)
  {
    super.DisableCustom(player);
  }
  
  public void ActivateCustom(Player player)
  {
    player.eject();
    player.leaveVehicle();
    
    EnderPearl pearl = (EnderPearl)player.launchProjectile(EnderPearl.class);
    pearl.setPassenger(player);
    
    UtilPlayer.message(player, F.main("Skill", "You threw " + F.skill(GetName()) + "."));
    
    ((CraftPlayer)player).getHandle().spectating = true;
    
    UtilInv.Update(player);
    
    this._riding.add(player.getName());
  }
  
  @EventHandler
  public void teleportCancel(PlayerTeleportEvent event)
  {
    if (!IsActive(event.getPlayer())) {
      return;
    }
    if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
    {
      FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(Color.PURPLE).with(FireworkEffect.Type.BALL).trail(true).build();
      try
      {
        UtilFirework.playFirework(event.getTo(), effect);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void disableNoCollide(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SEC) {
      return;
    }
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      if ((this._riding.contains(player.getName())) && 
        (player.getVehicle() == null))
      {
        ((CraftPlayer)player).getHandle().spectating = false;
        this._riding.remove(player.getName());
      }
    }
  }
  
  @EventHandler
  public void clean(PlayerQuitEvent event)
  {
    this._riding.remove(event.getPlayer().getName());
  }
}
