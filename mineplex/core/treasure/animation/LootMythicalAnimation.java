package mineplex.core.treasure.animation;

import java.util.Random;
import mineplex.core.common.util.UtilFirework;
import mineplex.core.treasure.Treasure;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class LootMythicalAnimation
  extends Animation
{
  private Random _random = new Random();
  private Block _chestBlock;
  
  public LootMythicalAnimation(Treasure treasure, Block chestBlock)
  {
    super(treasure);
    
    this._chestBlock = chestBlock;
  }
  
  protected void tick()
  {
    if ((getTicks() < 30) && (getTicks() % 3 == 0)) {
      UtilFirework.playFirework(this._chestBlock.getLocation().add(0.5D, 0.5D, 0.5D), FireworkEffect.Type.BALL_LARGE, Color.RED, true, true);
    }
    if (getTicks() == 1)
    {
      this._chestBlock.getLocation().getWorld().playSound(this._chestBlock.getLocation().add(0.5D, 0.5D, 0.5D), Sound.PORTAL_TRAVEL, 10.0F, 2.0F);
      this._chestBlock.getLocation().getWorld().playSound(this._chestBlock.getLocation().add(0.5D, 0.5D, 0.5D), Sound.ZOMBIE_UNFECT, 10.0F, 0.1F);
    }
    else if (getTicks() < 60)
    {
      UtilFirework.launchFirework(this._chestBlock.getLocation().add(0.5D, 0.5D, 0.5D), FireworkEffect.Type.BALL_LARGE, Color.RED, true, true, 
        new Vector((Math.random() - 0.5D) * 0.05D, 0.1D, (Math.random() - 0.5D) * 0.05D), 1);
    }
    else
    {
      finish();
    }
  }
  
  protected void onFinish() {}
}
