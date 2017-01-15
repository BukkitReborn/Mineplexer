package mineplex.core.gadget.gadgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilBlock;
import mineplex.core.common.util.UtilEvent;
import mineplex.core.common.util.UtilEvent.ActionType;
import mineplex.core.common.util.UtilMath;
import mineplex.core.event.StackerEvent;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.recharge.Recharge;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MorphBlock
  extends MorphGadget
{
  private HashMap<Player, BlockForm> _active = new HashMap();
  private HashSet<FallingBlock> _blocks = new HashSet();
  
  public MorphBlock(GadgetManager manager)
  {
    super(manager, "Block Morph", new String[] {C.cWhite + "The blockiest block that ever blocked.", " ", C.cYellow + "Left Click" + C.cGray + " to use " + C.cGreen + "Change Block", C.cYellow + "Stay Still" + C.cGray + " to use " + C.cGreen + "Solidify" }, 30000, Material.EMERALD_BLOCK, (byte)0);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    
    this._active.put(player, new BlockForm(this, player, Material.EMERALD_BLOCK));
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    
    BlockForm form = (BlockForm)this._active.remove(player);
    if (form != null) {
      form.Remove();
    }
  }
  
  @EventHandler
  public void formUpdate(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    for (BlockForm form : this._active.values())
    {
      form.SolidifyUpdate();
      form.FallingBlockCheck();
    }
  }
  
  @EventHandler
  public void formChange(PlayerInteractEvent event)
  {
    if (event.getClickedBlock() == null) {
      return;
    }
    if ((!UtilEvent.isAction(event, UtilEvent.ActionType.L_BLOCK)) && (!UtilEvent.isAction(event, UtilEvent.ActionType.R_BLOCK))) {
      return;
    }
    if (!UtilBlock.solid(event.getClickedBlock())) {
      return;
    }
    if (!Recharge.Instance.use(event.getPlayer(), GetName(), 500L, false, false)) {
      return;
    }
    BlockForm form = (BlockForm)this._active.get(event.getPlayer());
    if (form == null) {
      return;
    }
    form.Remove();
    
    this._active.put(event.getPlayer(), new BlockForm(this, event.getPlayer(), event.getClickedBlock().getType()));
  }
  
  @EventHandler
  public void stacker(StackerEvent event)
  {
    if (this._active.containsKey(event.getEntity())) {
      event.setCancelled(true);
    }
  }
  
  public void fallingBlockRegister(FallingBlock block)
  {
    this._blocks.add(block);
  }
  
  @EventHandler
  public void fallingBlockForm(EntityChangeBlockEvent event)
  {
    if (this._blocks.remove(event.getEntity()))
    {
      event.getEntity().remove();
      event.setCancelled(true);
    }
  }
  
  @EventHandler
  public void fallingBlockClean(UpdateEvent event)
  {
    if (event.getType() != UpdateType.SEC) {
      return;
    }
    Iterator<FallingBlock> blockIterator = this._blocks.iterator();
    while (blockIterator.hasNext())
    {
      FallingBlock block = (FallingBlock)blockIterator.next();
      if ((!block.isValid()) || (block.getVehicle() == null))
      {
        block.remove();
        blockIterator.remove();
      }
    }
  }
  
  @EventHandler
  public void itemSpawnCancel(ItemSpawnEvent event)
  {
    Iterator<FallingBlock> blockIterator = this._blocks.iterator();
    while (blockIterator.hasNext())
    {
      FallingBlock block = (FallingBlock)blockIterator.next();
      if (UtilMath.offset(block, event.getEntity()) < 0.1D)
      {
        block.remove();
        blockIterator.remove();
        event.setCancelled(true);
      }
    }
  }
}
