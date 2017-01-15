package mineplex.core.treasure.animation;

import java.util.List;
import mineplex.core.treasure.BlockInfo;
import mineplex.core.treasure.Treasure;
import mineplex.core.treasure.TreasureType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class BlockChangeAnimation
  extends Animation
{
  private static final int MAX_RADIUS = 4;
  private int _currentRadius;
  private List<BlockInfo> _blockInfoList;
  
  public BlockChangeAnimation(Treasure treasure, List<BlockInfo> blockInfoList)
  {
    super(treasure);
    
    this._currentRadius = 0;
    this._blockInfoList = blockInfoList;
  }
  
  protected void tick()
  {
    if (this._currentRadius == 4)
    {
      finish();
      return;
    }
    if (getTicks() % 10 == 0)
    {
      Block centerBlock = getTreasure().getCenterBlock().getRelative(BlockFace.DOWN);
      for (int x = -this._currentRadius; x <= this._currentRadius; x++) {
        for (int y = 0; y <= this._currentRadius; y++) {
          for (int z = -this._currentRadius; z <= this._currentRadius; z++)
          {
            Block b = centerBlock.getRelative(x, y, z);
            if ((y > 0) && ((b.getType() == Material.SMOOTH_BRICK) || (b.getType() == Material.STEP) || (b.getType() == Material.SMOOTH_STAIRS)))
            {
              this._blockInfoList.add(new BlockInfo(b));
              b.setType(Material.AIR);
            }
            else if (b.getType() == Material.SMOOTH_BRICK)
            {
              if (getTreasure().getTreasureType() != TreasureType.OLD)
              {
                Material newMaterial = getTreasure().getTreasureType() == TreasureType.ANCIENT ? Material.NETHER_BRICK : Material.QUARTZ_BLOCK;
                this._blockInfoList.add(new BlockInfo(b));
                b.setType(newMaterial);
              }
            }
            else if ((b.getType() == Material.SMOOTH_STAIRS) || (b.getType() == Material.COBBLESTONE_STAIRS))
            {
              if (getTreasure().getTreasureType() != TreasureType.OLD)
              {
                Material newMaterial = getTreasure().getTreasureType() == TreasureType.ANCIENT ? Material.NETHER_BRICK_STAIRS : Material.QUARTZ_STAIRS;
                this._blockInfoList.add(new BlockInfo(b));
                b.setType(newMaterial);
              }
            }
          }
        }
      }
      this._currentRadius += 1;
    }
  }
  
  protected void onFinish() {}
}
