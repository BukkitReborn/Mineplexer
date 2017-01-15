package mineplex.core.treasure.animation;

import mineplex.core.common.util.UtilServer;
import mineplex.core.hologram.Hologram;
import mineplex.core.hologram.HologramManager;
import mineplex.core.reward.RewardData;
import mineplex.core.treasure.ChestData;
import mineplex.core.treasure.Treasure;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockAction;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.TileEntity;
import net.minecraft.server.v1_7_R4.TileEntityEnderChest;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ChestOpenAnimation
  extends Animation
{
  private ChestData _chestData;
  private RewardData _rewardData;
  private HologramManager _hologramManager;
  private Item _itemEntity;
  private Hologram _hologram;
  
  public ChestOpenAnimation(Treasure treasure, ChestData chestData, RewardData rewardData, HologramManager hologramManager)
  {
    super(treasure);
    this._hologramManager = hologramManager;
    this._chestData = chestData;
    this._rewardData = rewardData;
    
    Block block = chestData.getBlock();
    PacketPlayOutBlockAction packet = new PacketPlayOutBlockAction(block.getX(), block.getY(), block.getZ(), 
      CraftMagicNumbers.getBlock(block), 1, 1);
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player other = arrayOfPlayer[i];
      
      ((CraftPlayer)other).getHandle().playerConnection.sendPacket(packet);
      if (block.getType() == Material.ENDER_CHEST)
      {
        TileEntity tileEntity = ((CraftWorld)block.getWorld()).getTileEntityAt(block.getX(), block.getY(), block.getZ());
        if ((tileEntity instanceof TileEntityEnderChest)) {
          ((TileEntityEnderChest)tileEntity).j = 1;
        }
      }
      other.playSound(block.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
    }
  }
  
  protected void tick()
  {
    if (getTicks() == 5)
    {
      Location location = this._chestData.getBlock().getLocation().add(0.5D, 0.8D, 0.5D);
      this._itemEntity = location.getWorld().dropItem(location, this._rewardData.getDisplayItem());
      this._itemEntity.setVelocity(new Vector(0, 0, 0));
      this._itemEntity.setPickupDelay(Integer.MAX_VALUE);
    }
    else if (getTicks() == 15)
    {
      this._hologram = new Hologram(this._hologramManager, this._chestData.getBlock().getLocation().add(0.5D, 1.4D, 0.5D), new String[] {
        this._rewardData.getFriendlyName() });
      this._hologram.start();
    }
  }
  
  public void onFinish()
  {
    if (this._hologram != null)
    {
      this._hologram.stop();
      this._itemEntity.remove();
    }
  }
}
