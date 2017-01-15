package mineplex.core.treasure.animation;

import java.util.ArrayList;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import mineplex.core.treasure.Treasure;
import mineplex.core.treasure.TreasureStyle;
import mineplex.core.treasure.TreasureType;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class ParticleAnimation
  extends Animation
{
  private static double MODIFIER = 0.5D;
  private static ArrayList<Vector> PATH = new ArrayList();
  
  static
  {
    double y = 5.0D;
    double x = 3.0D;
    double z = -3.0D;
    for (z = -3.0D; z <= 3.0D; z += MODIFIER) {
      PATH.add(new Vector(x, y, z));
    }
    for (x = 3.0D; x >= -3.0D; x -= MODIFIER) {
      PATH.add(new Vector(x, y, z));
    }
    for (z = 3.0D; z >= -3.0D; z -= MODIFIER) {
      PATH.add(new Vector(x, y, z));
    }
    for (x = -3.0D; x <= 3.0D; x += MODIFIER) {
      PATH.add(new Vector(x, y, z));
    }
  }
  
  private int pathPosition = 0;
  
  public ParticleAnimation(Treasure treasure)
  {
    super(treasure);
  }
  
  protected void tick()
  {
    Vector position = (Vector)PATH.get(this.pathPosition);
    
    UtilParticle.PlayParticle(getTreasure().getTreasureType().getStyle().getSecondaryParticle(), getTreasure().getCenterBlock().getLocation().add(0.5D, 0.0D, 0.5D).add(position), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
      UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    
    this.pathPosition = ((this.pathPosition + 1) % PATH.size());
  }
  
  protected void onFinish() {}
}
