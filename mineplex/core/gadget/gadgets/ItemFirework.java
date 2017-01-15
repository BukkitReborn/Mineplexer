package mineplex.core.gadget.gadgets;

import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilFirework;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.ItemGadget;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ItemFirework
  extends ItemGadget
{
  public ItemFirework(GadgetManager manager)
  {
    super(manager, "Fireworks", new String[] {C.cWhite + "Need to celebrate?!", C.cWhite + "Use some fireworks!", C.cWhite + "Pew pew pew!" }, -1, Material.FIREWORK, (byte)0, 100L, new Ammo("Fireworks", "50 Fireworks", Material.FIREWORK, (byte)0, new String[] { C.cWhite + "50 Fireworks for you to launch!" }, 500, 50));
  }
  
  public void ActivateCustom(Player player)
  {
    Location loc = player.getEyeLocation().add(player.getLocation().getDirection());
    for (Block block : UtilBlock.getSurrounding(loc.getBlock(), true)) {
      if (block.getTypeId() == 90)
      {
        UtilPlayer.message(player, F.main("Skill", "You cannot use " + F.skill(GetName()) + " near Portals."));
        return;
      }
    }
    UtilPlayer.message(player, F.main("Skill", "You used " + F.skill(GetName()) + "."));
    
    double r = Math.random();
    
    Color color = Color.FUCHSIA;
    if (r > 0.9D) {
      color = Color.RED;
    } else if (r > 0.8D) {
      color = Color.YELLOW;
    } else if (r > 0.7D) {
      color = Color.GREEN;
    } else if (r > 0.6D) {
      color = Color.BLUE;
    } else if (r > 0.5D) {
      color = Color.AQUA;
    } else if (r > 0.4D) {
      color = Color.LIME;
    } else if (r > 0.3D) {
      color = Color.ORANGE;
    } else if (r > 0.2D) {
      color = Color.TEAL;
    } else if (r > 0.1D) {
      color = Color.WHITE;
    }
    r = Math.random();
    
    FireworkEffect.Type type = FireworkEffect.Type.BURST;
    if (r > 0.66D) {
      type = FireworkEffect.Type.BALL;
    } else if (r > 0.33D) {
      type = FireworkEffect.Type.BALL_LARGE;
    }
    UtilFirework.launchFirework(loc, 
      FireworkEffect.builder().flicker(Math.random() > 0.5D).withColor(color).with(type).trail(Math.random() > 0.5D).build(), 
      new Vector(0, 0, 0), 0 + (int)(Math.random() * 3.0D));
  }
}
