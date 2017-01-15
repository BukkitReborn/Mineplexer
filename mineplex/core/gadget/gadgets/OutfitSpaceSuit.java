package mineplex.core.gadget.gadgets;

import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.OutfitGadget;
import mineplex.core.gadget.types.OutfitGadget.ArmorSlot;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class OutfitSpaceSuit
  extends OutfitGadget
{
  public OutfitSpaceSuit(GadgetManager manager, String name, int cost, OutfitGadget.ArmorSlot slot, Material mat, byte data)
  {
    super(manager, name, new String[] { "Wear the complete set for", "awesome bonus effects!", "Bonus coming soon..." }, cost, slot, mat, data);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
  }
}
