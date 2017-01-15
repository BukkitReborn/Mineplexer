package mineplex.core.treasure.animation;

import java.util.List;
import mineplex.core.common.util.UtilAlg;
import mineplex.core.common.util.UtilMath;
import mineplex.core.common.util.UtilParticle;
import mineplex.core.common.util.UtilParticle.ParticleType;
import mineplex.core.common.util.UtilParticle.ViewDist;
import mineplex.core.common.util.UtilServer;
import mineplex.core.treasure.BlockInfo;
import mineplex.core.treasure.Treasure;
import mineplex.core.treasure.TreasureStyle;
import mineplex.core.treasure.TreasureType;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.util.Vector;

public class ChestSpawnAnimation
  extends Animation
{
  private static final int ANIMATION_DURATION = 80;
  private Block _block;
  private byte _direction;
  private Location _centerLocation;
  private Location _particleLocation;
  private Vector _particleDirection;
  private List<BlockInfo> _chestBlockInfo;
  private double _radialOffset;
  
  public ChestSpawnAnimation(Treasure tresure, Block block, List<BlockInfo> chestBlockInfo, Block openingCenter, double radialOffset)
  {
    super(tresure);
    this._block = block;
    int relX = getTreasure().getCenterBlock().getX() - block.getX();
    int relZ = getTreasure().getCenterBlock().getZ() - block.getZ();
    if (Math.abs(relX) > Math.abs(relZ))
    {
      if (relX > 0) {
        this._direction = 5;
      } else {
        this._direction = 4;
      }
    }
    else if (relZ > 0) {
      this._direction = 3;
    } else {
      this._direction = 2;
    }
    this._centerLocation = block.getLocation().clone().add(0.5D, 0.5D, 0.5D);
    this._chestBlockInfo = chestBlockInfo;
    
    this._particleLocation = openingCenter.getLocation().add(0.5D, 4.0D, 0.5D);
    
    this._particleDirection = UtilAlg.getTrajectory(this._particleLocation, this._centerLocation);
    this._particleDirection.multiply(UtilMath.offset(this._particleLocation, this._centerLocation) / 80.0D);
    
    this._radialOffset = radialOffset;
  }
  
  public void tick()
  {
    float scale = (float)((80 - getTicks()) / 80.0D);
    
    this._particleLocation.add(this._particleDirection);
    if (getTreasure().getTreasureType() == TreasureType.OLD)
    {
      UtilParticle.PlayParticle(getTreasure().getTreasureType().getStyle().getSecondaryParticle(), this._centerLocation, 0.1F, 0.1F, 0.1F, 0.0F, 1, 
        UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    }
    else if (getTreasure().getTreasureType() == TreasureType.ANCIENT)
    {
      float x = (float)Math.sin(getTicks() / 4.0D);
      float z = (float)Math.cos(getTicks() / 4.0D);
      
      Location newLoc = this._particleLocation.clone();
      newLoc.add(UtilAlg.getLeft(this._particleDirection).multiply(x * scale));
      newLoc.add(UtilAlg.getUp(this._particleDirection).multiply(z * scale));
      
      UtilParticle.PlayParticle(getTreasure().getTreasureType().getStyle().getSecondaryParticle(), newLoc, 0.0F, 0.0F, 0.0F, 0.0F, 1, 
        UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
    }
    else if (getTreasure().getTreasureType() == TreasureType.MYTHICAL)
    {
      float y = 5.0F * scale;
      double width = 0.7D * (getTicks() / 80.0D);
      for (int i = 0; i < 2; i++)
      {
        double lead = i * 3.141592653589793D;
        
        float x = (float)Math.sin(getTicks() / 4.0D + lead);
        float z = (float)Math.cos(getTicks() / 4.0D + lead);
        
        UtilParticle.PlayParticle(getTreasure().getTreasureType().getStyle().getSecondaryParticle(), this._centerLocation.clone().add(x * width, y, z * width), 0.0F, 0.0F, 0.0F, 0.0F, 1, 
          UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
      }
    }
    if (getTicks() >= 80)
    {
      this._chestBlockInfo.add(new BlockInfo(this._block));
      getTreasure().setBlock(this._block, getTreasure().getTreasureType().getMaterial(), this._direction);
      this._block.getLocation().getWorld().playSound(this._centerLocation, getTreasure().getTreasureType().getStyle().getChestSpawnSound(), 0.5F, 1.0F);
      
      UtilParticle.ParticleType particleType = getTreasure().getTreasureType().getStyle().getChestSpawnParticle();
      if (particleType != null)
      {
        UtilParticle.PlayParticle(particleType, this._centerLocation, 0.2F, 0.2F, 0.2F, 0.0F, 50, 
          UtilParticle.ViewDist.NORMAL, UtilServer.getPlayers());
      }
      else
      {
        int i = MathHelper.floor(this._centerLocation.getX());
        int j = MathHelper.floor(this._centerLocation.getY() - 0.20000000298023224D - 0.5D);
        int k = MathHelper.floor(this._centerLocation.getZ());
        ((CraftWorld)this._centerLocation.getWorld()).getHandle().triggerEffect(2006, i, j, k, MathHelper.f(57.0F));
      }
      finish();
    }
  }
  
  protected void onFinish() {}
}
