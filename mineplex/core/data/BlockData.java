package mineplex.core.data;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockData
{
  public Block Block;
  public Material Material;
  public byte Data;
  public long Time;
  
  public BlockData(Block block)
  {
    this.Block = block;
    this.Material = block.getType();
    this.Data = block.getData();
    this.Time = System.currentTimeMillis();
  }
  
  public void restore()
  {
    restore(false);
  }
  
  public void restore(boolean requireNotAir)
  {
    if ((requireNotAir) && (this.Block.getType() == Material.AIR)) {
      return;
    }
    this.Block.setTypeIdAndData(this.Material.getId(), this.Data, true);
  }
}
