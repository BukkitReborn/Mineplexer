package mineplex.core.gadget.gadgets;

import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class SongData
{
  public Block Block;
  public long EndTime;
  
  public SongData(Block block, long duration)
  {
    this.Block = block;
    this.EndTime = (System.currentTimeMillis() + duration);
    
    this.Block.setType(Material.JUKEBOX);
  }
  
  public boolean update()
  {
    if (System.currentTimeMillis() > this.EndTime)
    {
      if (this.Block.getType() == Material.JUKEBOX) {
        this.Block.setType(Material.AIR);
      }
      return true;
    }
    UtilParticle.PlayParticle(UtilParticle.ParticleType.NOTE, this.Block.getLocation().add(0.5D, 1.0D, 0.5D), 0.5F, 0.5F, 0.5F, 0.0F, 2, 
      UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    
    return false;
  }
}
