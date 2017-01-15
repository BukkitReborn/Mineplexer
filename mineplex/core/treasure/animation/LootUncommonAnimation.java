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
import org.bukkit.block.Block;

public class LootUncommonAnimation
  extends Animation
{
  private Random _random = new Random();
  private Block _block;
  
  public LootUncommonAnimation(Treasure treasure, Block block)
  {
    super(treasure);
    
    this._block = block;
  }
  
  protected void tick()
  {
    if (getTicks() >= 10) {
      finish();
    }
    if (getTicks() == 10) {
      UtilFirework.playFirework(this._block.getLocation().add(0.5D, 0.5D, 0.5D), FireworkEffect.Type.BURST, Color.AQUA, false, false);
    } else if (getTicks() % 2 == 0) {
      UtilParticle.PlayParticle(UtilParticle.ParticleType.HEART, this._block.getLocation().add(0.5D, 1.2D, 0.5D), 0.5F, 0.2F, 0.5F, 0.0F, 1, 
        UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    }
  }
  
  protected void onFinish() {}
}
