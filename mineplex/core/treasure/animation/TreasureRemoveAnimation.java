package mineplex.core.treasure.animation;

import java.util.List;
import java.util.Random;
import mineplex.core.treasure.BlockInfo;
import mineplex.core.treasure.Treasure;

public class TreasureRemoveAnimation
  extends Animation
{
  private Random _random = new Random();
  private List<BlockInfo> _openedChests;
  private List<BlockInfo> _otherChests;
  
  public TreasureRemoveAnimation(Treasure treasure, List<BlockInfo> openedChests, List<BlockInfo> otherChests)
  {
    super(treasure);
    this._openedChests = openedChests;
    this._otherChests = otherChests;
  }
  
  protected void tick()
  {
    if ((getTicks() >= 20) && (getTicks() % 10 == 0)) {
      if (!this._otherChests.isEmpty())
      {
        BlockInfo info = (BlockInfo)this._otherChests.remove(this._random.nextInt(this._otherChests.size()));
        getTreasure().resetBlockInfo(info);
      }
      else
      {
        finish();
      }
    }
  }
  
  protected void onFinish() {}
}
