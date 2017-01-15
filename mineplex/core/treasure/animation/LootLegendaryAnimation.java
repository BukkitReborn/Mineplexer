package mineplex.core.treasure.animation;

import java.util.Random;
import mineplex.core.common.util.UtilFirework;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import mineplex.core.treasure.Treasure;
import org.bukkit.Color;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;

public class LootLegendaryAnimation
  extends Animation
{
  private Random _random = new Random();
  private Block _chestBlock;
  
  public LootLegendaryAnimation(Treasure treasure, Block chestBlock)
  {
    super(treasure);
    
    this._chestBlock = chestBlock;
  }
  
  protected void tick()
  {
    if ((getTicks() < 12) && (getTicks() % 3 == 0)) {
      UtilFirework.playFirework(this._chestBlock.getLocation().add(0.5D, 0.5D, 0.5D), FireworkEffect.Type.BALL_LARGE, Color.LIME, true, true);
    }
    if (getTicks() == 1)
    {
      this._chestBlock.getLocation().getWorld().playSound(this._chestBlock.getLocation().add(0.5D, 0.5D, 0.5D), Sound.ENDERDRAGON_DEATH, 10.0F, 2.0F);
    }
    else if (getTicks() < 35)
    {
      double radius = 2.0D - getTicks() / 10.0D * 2.0D;
      int particleAmount = 20 - getTicks() * 2;
      Location _centerLocation = this._chestBlock.getLocation().add(0.5D, 0.1D, 0.5D);
      for (int i = 0; i < particleAmount; i++)
      {
        double xDiff = Math.sin(i / particleAmount * 2.0D * 3.141592653589793D) * radius;
        double zDiff = Math.cos(i / particleAmount * 2.0D * 3.141592653589793D) * radius;
        
        Location location = _centerLocation.clone().add(xDiff, 0.0D, zDiff);
        UtilParticle.PlayParticle(UtilParticle.ParticleType.HAPPY_VILLAGER, location, 0.0F, 0.0F, 0.0F, 0.0F, 1, 
          UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
      }
    }
    else if (getTicks() < 40)
    {
      double xDif = this._random.nextGaussian() * 0.5D;
      double zDif = this._random.nextGaussian() * 0.5D;
      double yDif = this._random.nextGaussian() * 0.5D;
      
      Location loc = this._chestBlock.getLocation().add(0.5D, 0.5D, 0.5D).add(xDif, zDif, yDif);
      
      loc.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 0.0F);
    }
    else
    {
      finish();
    }
  }
  
  protected void onFinish() {}
}
