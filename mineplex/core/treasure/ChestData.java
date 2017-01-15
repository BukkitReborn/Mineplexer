package mineplex.core.treasure;

import org.bukkit.block.Block;

public class ChestData
{
  private Block _block;
  private boolean _opened;
  
  public ChestData(Block block)
  {
    this._block = block;
    this._opened = false;
  }
  
  public boolean isOpened()
  {
    return this._opened;
  }
  
  public void setOpened(boolean opened)
  {
    this._opened = opened;
  }
  
  public Block getBlock()
  {
    return this._block;
  }
}
