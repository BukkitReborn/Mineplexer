package mineplex.core.gadget.types;

import java.util.HashSet;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.itemstack.ItemStackFactory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public abstract class OutfitGadget
  extends Gadget
{
  private ArmorSlot _slot;
  
  public static enum ArmorSlot
  {
    Helmet,  Chest,  Legs,  Boots;
  }
  
  public OutfitGadget(GadgetManager manager, String name, String[] desc, int cost, ArmorSlot slot, Material mat, byte data)
  {
    super(manager, GadgetType.Costume, name, desc, cost, mat, data);
    
    this._slot = slot;
  }
  
  public ArmorSlot GetSlot()
  {
    return this._slot;
  }
  
  public void ApplyArmor(Player player)
  {
    this.Manager.RemoveMorph(player);
    
    this.Manager.RemoveOutfit(player, this._slot);
    
    this._active.add(player);
    
    UtilPlayer.message(player, F.main("Gadget", "You put on " + F.elem(GetName()) + "."));
    if (this._slot == ArmorSlot.Helmet) {
      player.getInventory().setHelmet(
        ItemStackFactory.Instance.CreateStack(GetDisplayMaterial().getId(), GetDisplayData(), 1, GetName()));
    } else if (this._slot == ArmorSlot.Chest) {
      player.getInventory().setChestplate(
        ItemStackFactory.Instance.CreateStack(GetDisplayMaterial().getId(), GetDisplayData(), 1, GetName()));
    } else if (this._slot == ArmorSlot.Legs) {
      player.getInventory().setLeggings(
        ItemStackFactory.Instance.CreateStack(GetDisplayMaterial().getId(), GetDisplayData(), 1, GetName()));
    } else if (this._slot == ArmorSlot.Boots) {
      player.getInventory().setBoots(
        ItemStackFactory.Instance.CreateStack(GetDisplayMaterial().getId(), GetDisplayData(), 1, GetName()));
    }
  }
  
  public void RemoveArmor(Player player)
  {
    if (!this._active.remove(player)) {
      return;
    }
    UtilPlayer.message(player, F.main("Gadget", "You took off " + F.elem(GetName()) + "."));
    if (this._slot == ArmorSlot.Helmet) {
      player.getInventory().setHelmet(null);
    } else if (this._slot == ArmorSlot.Chest) {
      player.getInventory().setChestplate(null);
    } else if (this._slot == ArmorSlot.Legs) {
      player.getInventory().setLeggings(null);
    } else if (this._slot == ArmorSlot.Boots) {
      player.getInventory().setBoots(null);
    }
  }
}
