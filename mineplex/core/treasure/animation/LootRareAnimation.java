package mineplex.core.treasure.animation;

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

public class LootRareAnimation
  extends Animation
{
  private Location _centerLocation;
  
  public LootRareAnimation(Treasure treasure, Location centerLocation)
  {
    super(treasure);
    
    this._centerLocation = centerLocation;
  }
  
  protected void tick()
  {
    if (getTicks() == 2)
    {
      UtilFirework.playFirework(this._centerLocation, FireworkEffect.Type.BALL, Color.FUCHSIA, false, false);
      
      this._centerLocation.getWorld().playSound(this._centerLocation, Sound.WITHER_SPAWN, 10.0F, 1.2F);
    }
    else if (getTicks() >= 60)
    {
      finish();
    }
    double currentRotation = getTicks() / 20.0D;
    double radius = currentRotation;
    double yDiff = currentRotation;
    double xDiff = Math.sin(currentRotation * 2.0D * 3.141592653589793D) * radius;
    double zDiff = Math.cos(currentRotation * 2.0D * 3.141592653589793D) * radius;
    
    Location location = this._centerLocation.clone().add(xDiff, yDiff, zDiff);
    
    UtilParticle.PlayParticle(UtilParticle.ParticleType.WITCH_MAGIC, location, 0.0F, 0.0F, 0.0F, 0.0F, 1, 
      UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    
    double radius = getTicks() / 20.0D;
    int particleAmount = getTicks() / 2;
    for (int i = 0; i < particleAmount; i++)
    {
      double xDiff = Math.sin(i / particleAmount * 2.0D * 3.141592653589793D) * radius;
      double zDiff = Math.cos(i / particleAmount * 2.0D * 3.141592653589793D) * radius;
      
      Location location = this._centerLocation.clone().add(xDiff, -1.3D, zDiff);
      UtilParticle.PlayParticle(UtilParticle.ParticleType.WITCH_MAGIC, location, 0.0F, 0.0F, 0.0F, 0.0F, 1, 
        UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    }
  }
  
  protected void onFinish() {}
}
