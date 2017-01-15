package mineplex.core.treasure;

import org.bukkit.block.Block;

public class BlockInfo
{
  private Block _block;
  private int _id;
  private byte _data;
  
  public BlockInfo(Block block)
  {
    this._block = block;
    this._id = block.getTypeId();
    this._data = block.getData();
  }
  
  public Block getBlock()
  {
    return this._block;
  }
  
  public int getId()
  {
    return this._id;
  }
  
  public byte getData()
  {
    return this._data;
  }
}
