package mineplex.core.gadget.gadgets;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilFirework;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseEnderman;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class MorphEnderman
  extends MorphGadget
{
  public MorphEnderman(GadgetManager manager)
  {
    super(manager, "Enderman Morph", new String[] {C.cWhite + "Transforms the wearer into an Enderman!", " ", C.cYellow + "Double Jump" + C.cGray + " to use " + C.cGreen + "Blink" }, 30000, Material.ENDER_PEARL, (byte)0);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    DisguiseEnderman disguise = new DisguiseEnderman(player);
    disguise.setName(player.getName(), this.Manager.getClientManager().Get(player).GetRank());
    disguise.setCustomNameVisible(true);
    this.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    this.Manager.getDisguiseManager().undisguise(player);
    
    player.setAllowFlight(false);
    player.setFlying(false);
  }
  
  @EventHandler
  public void teleport(PlayerToggleFlightEvent event)
  {
    Player player = event.getPlayer();
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }
    if (!IsActive(player)) {
      return;
    }
    event.setCancelled(true);
    player.setFlying(false);
    
    player.setAllowFlight(false);
    
    Recharge.Instance.use(player, GetName(), 2000L, false, false);
    
    Block lastSmoke = player.getLocation().getBlock();
    
    double curRange = 0.0D;
    while (curRange <= 16.0D)
    {
      Location newTarget = player.getLocation().add(new Vector(0.0D, 0.2D, 0.0D)).add(player.getLocation().getDirection().multiply(curRange));
      if ((!UtilBlock.airFoliage(newTarget.getBlock())) || 
        (!UtilBlock.airFoliage(newTarget.getBlock().getRelative(BlockFace.UP)))) {
        break;
      }
      curRange += 0.2D;
      if (!lastSmoke.equals(newTarget.getBlock())) {
        lastSmoke.getWorld().playEffect(lastSmoke.getLocation(), Effect.SMOKE, 4);
      }
      lastSmoke = newTarget.getBlock();
    }
    curRange -= 0.4D;
    if (curRange < 0.0D) {
      curRange = 0.0D;
    }
    Location loc = player.getLocation().add(player.getLocation().getDirection().multiply(curRange).add(new Vector(0.0D, 0.4D, 0.0D)));
    if (curRange > 0.0D)
    {
      FireworkEffect effect = FireworkEffect.builder().flicker(false).withColor(Color.BLACK).with(FireworkEffect.Type.BALL).trail(false).build();
      try
      {
        UtilFirework.playFirework(player.getEyeLocation(), effect);
        player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_UNFECT, 2.0F, 2.0F);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
      player.teleport(loc);
      try
      {
        UtilFirework.playFirework(player.getEyeLocation(), effect);
        player.getWorld().playSound(player.getLocation(), Sound.ZOMBIE_UNFECT, 2.0F, 2.0F);
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    player.setFallDistance(0.0F);
  }
  
  @EventHandler
  public void teleportUpdate(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (Player player : GetActive()) {
      if (player.getGameMode() != GameMode.CREATIVE) {
        if (Recharge.Instance.usable(player, GetName())) {
          player.setAllowFlight(true);
        }
      }
    }
  }
}
