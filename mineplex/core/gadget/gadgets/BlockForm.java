package mineplex.core.gadget.gadgets;

import java.util.ArrayList;
import java.util.List;
import mineplex.core.common.util.F;
import mineplex.core.common.util.MapUtil;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.DisguiseManager;
import mineplex.core.disguise.disguises.DisguiseCat;
import mineplex.core.disguise.disguises.DisguiseChicken;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.event.GadgetBlockEvent;
import mineplex.core.itemstack.ItemStackFactory;
import mineplex.core.recharge.Recharge;
import net.minecraft.server.v1_7_R4.DataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftFallingSand;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class BlockForm
{
  private MorphBlock _host;
  private Player _player;
  private Material _mat;
  private Block _block;
  private Location _loc;
  
  public BlockForm(MorphBlock host, Player player, Material mat)
  {
    this._host = host;
    this._player = player;
    
    this._mat = mat;
    this._loc = player.getLocation();
    
    Apply();
  }
  
  public void Apply()
  {
    if (this._player.getPassenger() != null)
    {
      Recharge.Instance.useForce(this._player, "PassengerChange", 100L);
      
      this._player.getPassenger().remove();
      this._player.eject();
    }
    ((CraftEntity)this._player).getHandle().getDataWatcher().watch(0, Byte.valueOf((byte)32));
    
    DisguiseChicken disguise = new DisguiseChicken(this._player);
    disguise.setBaby();
    disguise.setSoundDisguise(new DisguiseCat(this._player));
    disguise.setInvisible(true);
    this._host.Manager.getDisguiseManager().disguise(disguise, new Player[0]);
    
    FallingBlockCheck();
    
    String blockName = F.elem(ItemStackFactory.Instance.GetName(this._mat, (byte)0, false));
    if (!blockName.contains("Block")) {
      UtilPlayer.message(this._player, F.main("Morph", "You are now a " + F.elem(new StringBuilder(String.valueOf(ItemStackFactory.Instance.GetName(this._mat, (byte)0, false))).append(" Block").toString()) + "!"));
    } else {
      UtilPlayer.message(this._player, F.main("Morph", "You are now a " + F.elem(ItemStackFactory.Instance.GetName(this._mat, (byte)0, false)) + "!"));
    }
    this._player.playSound(this._player.getLocation(), Sound.ZOMBIE_UNFECT, 2.0F, 2.0F);
  }
  
  public void Remove()
  {
    SolidifyRemove();
    
    this._host.Manager.getDisguiseManager().undisguise(this._player);
    if (this._player.getPassenger() != null)
    {
      Recharge.Instance.useForce(this._player, "PassengerChange", 100L);
      
      this._player.getPassenger().remove();
      this._player.eject();
    }
    ((CraftEntity)this._player).getHandle().getDataWatcher().watch(0, Byte.valueOf((byte)0));
  }
  
  public void SolidifyUpdate()
  {
    if (!this._player.isSprinting()) {
      ((CraftEntity)this._player).getHandle().getDataWatcher().watch(0, Byte.valueOf((byte)32));
    }
    if (this._block == null)
    {
      if (!this._loc.getBlock().equals(this._player.getLocation().getBlock()))
      {
        this._player.setExp(0.0F);
        this._loc = this._player.getLocation();
      }
      else
      {
        double hideBoost = 0.025D;
        
        this._player.setExp((float)Math.min(0.9990000128746033D, this._player.getExp() + hideBoost));
        if (this._player.getExp() >= 0.999F)
        {
          Block block = this._player.getLocation().getBlock();
          
          List<Block> blockList = new ArrayList();
          blockList.add(block);
          
          GadgetBlockEvent event = new GadgetBlockEvent(this._host, blockList);
          
          Bukkit.getServer().getPluginManager().callEvent(event);
          if ((block.getType() != Material.AIR) || (!UtilBlock.solid(block.getRelative(BlockFace.DOWN))) || (event.getBlocks().isEmpty()) || (event.isCancelled()))
          {
            UtilPlayer.message(this._player, F.main("Morph", "You cannot become a Solid Block here."));
            this._player.setExp(0.0F);
            return;
          }
          this._block = block;
          
          this._player.playEffect(this._player.getLocation(), Effect.STEP_SOUND, this._mat);
          
          SolidifyVisual();
          
          this._player.playSound(this._player.getLocation(), Sound.NOTE_PLING, 1.0F, 2.0F);
        }
      }
    }
    else if (!this._loc.getBlock().equals(this._player.getLocation().getBlock())) {
      SolidifyRemove();
    } else {
      SolidifyVisual();
    }
  }
  
  public void SolidifyRemove()
  {
    if (this._block != null)
    {
      MapUtil.QuickChangeBlockAt(this._block.getLocation(), 0, (byte)0);
      this._block = null;
    }
    this._player.setExp(0.0F);
    
    this._player.playSound(this._player.getLocation(), Sound.NOTE_PLING, 1.0F, 0.5F);
    
    FallingBlockCheck();
  }
  
  public void SolidifyVisual()
  {
    if (this._block == null) {
      return;
    }
    if (this._player.getPassenger() != null)
    {
      Recharge.Instance.useForce(this._player, "PassengerChange", 100L);
      
      this._player.getPassenger().remove();
      this._player.eject();
    }
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player other = arrayOfPlayer[i];
      other.sendBlockChange(this._player.getLocation(), this._mat, (byte)0);
    }
    this._player.sendBlockChange(this._player.getLocation(), 36, (byte)0);
    
    FallingBlockCheck();
  }
  
  public void FallingBlockCheck()
  {
    if (this._block != null) {
      return;
    }
    if ((this._player.getPassenger() == null) || (!this._player.getPassenger().isValid()))
    {
      if (!Recharge.Instance.use(this._player, "PassengerChange", 100L, false, false)) {
        return;
      }
      FallingBlock block = this._player.getWorld().spawnFallingBlock(this._player.getEyeLocation(), this._mat, (byte)0);
      
      ((CraftFallingSand)block).getHandle().spectating = true;
      
      this._player.setPassenger(block);
      
      this._host.fallingBlockRegister(block);
    }
    else
    {
      ((CraftFallingSand)this._player.getPassenger()).getHandle().ticksLived = 1;
      this._player.getPassenger().setTicksLived(1);
    }
  }
  
  public Block GetBlock()
  {
    return this._block;
  }
}
