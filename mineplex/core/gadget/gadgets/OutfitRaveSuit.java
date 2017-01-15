package mineplex.core.gadget.gadgets;

import java.util.HashMap;
import mineplex.core.common.util.UtilGear;
import mineplex.core.common.util.UtilServer;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.OutfitGadget;
import mineplex.core.gadget.types.OutfitGadget.ArmorSlot;
import mineplex.core.updater.UpdateType;
import mineplex.core.updater.event.UpdateEvent;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class OutfitRaveSuit
  extends OutfitGadget
{
  private HashMap<String, Integer> _colorPhase = new HashMap();
  
  public OutfitRaveSuit(GadgetManager manager, String name, int cost, OutfitGadget.ArmorSlot slot, Material mat, byte data)
  {
    super(manager, name, new String[] { "Wear the complete set for", "awesome bonus effects!", "Bonus coming soon..." }, cost, slot, mat, data);
  }
  
  public void EnableCustom(Player player)
  {
    ApplyArmor(player);
    this._colorPhase.put(player.getName(), Integer.valueOf(-1));
  }
  
  public void DisableCustom(Player player)
  {
    RemoveArmor(player);
    this._colorPhase.remove(player.getName());
  }
  
  @EventHandler
  public void updateColor(UpdateEvent event)
  {
    if (event.getType() != UpdateType.TICK) {
      return;
    }
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      if (IsActive(player))
      {
        ItemStack stack;
        if (GetSlot() == OutfitGadget.ArmorSlot.Helmet)
        {
          ItemStack stack = player.getInventory().getHelmet();
          if (!UtilGear.isMat(stack, GetDisplayMaterial()))
          {
            Disable(player);
            continue;
          }
        }
        else if (GetSlot() == OutfitGadget.ArmorSlot.Chest)
        {
          ItemStack stack = player.getInventory().getChestplate();
          if (!UtilGear.isMat(stack, GetDisplayMaterial()))
          {
            Disable(player);
            continue;
          }
        }
        else if (GetSlot() == OutfitGadget.ArmorSlot.Legs)
        {
          ItemStack stack = player.getInventory().getLeggings();
          if (!UtilGear.isMat(stack, GetDisplayMaterial()))
          {
            Disable(player);
            continue;
          }
        }
        else
        {
          if (GetSlot() != OutfitGadget.ArmorSlot.Boots) {
            continue;
          }
          stack = player.getInventory().getBoots();
          if (!UtilGear.isMat(stack, GetDisplayMaterial()))
          {
            Disable(player);
            continue;
          }
        }
        int phase = ((Integer)this._colorPhase.get(player.getName())).intValue();
        
        LeatherArmorMeta meta = (LeatherArmorMeta)stack.getItemMeta();
        if (phase == -1)
        {
          meta.setColor(Color.fromRGB(250, 0, 0));
          this._colorPhase.put(player.getName(), Integer.valueOf(0));
        }
        else if (phase == 0)
        {
          meta.setColor(Color.fromRGB(250, Math.min(250, meta.getColor().getGreen() + 25), 0));
          if (meta.getColor().getGreen() >= 250) {
            this._colorPhase.put(player.getName(), Integer.valueOf(1));
          }
        }
        else if (phase == 1)
        {
          meta.setColor(Color.fromRGB(Math.max(0, meta.getColor().getRed() - 25), 250, 0));
          if (meta.getColor().getRed() <= 0) {
            this._colorPhase.put(player.getName(), Integer.valueOf(2));
          }
        }
        else if (phase == 2)
        {
          meta.setColor(Color.fromRGB(0, Math.max(0, meta.getColor().getGreen() - 25), Math.min(250, meta.getColor().getBlue() + 25)));
          if (meta.getColor().getGreen() <= 0) {
            this._colorPhase.put(player.getName(), Integer.valueOf(3));
          }
        }
        else if (phase == 3)
        {
          meta.setColor(Color.fromRGB(Math.min(250, meta.getColor().getRed() + 25), 0, Math.max(0, meta.getColor().getBlue() - 25)));
          if (meta.getColor().getBlue() <= 0) {
            this._colorPhase.put(player.getName(), Integer.valueOf(0));
          }
        }
        stack.setItemMeta(meta);
      }
    }
  }
}
