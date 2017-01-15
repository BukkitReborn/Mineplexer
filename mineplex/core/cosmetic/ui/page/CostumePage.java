package mineplex.core.cosmetic.ui.page;

import java.util.ArrayList;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.donation.DonationManager;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.Gadget;
import mineplex.core.gadget.types.GadgetType;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.gadget.types.OutfitGadget;
import mineplex.core.gadget.types.OutfitGadget.ArmorSlot;
import mineplex.core.inventory.ClientInventory;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class CostumePage
  extends GadgetPage
{
  public CostumePage(CosmeticManager plugin, CosmeticShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player)
  {
    super(plugin, shop, clientManager, donationManager, name, player);
    buildPage();
  }
  
  protected void buildPage()
  {
    ArrayList costumeClasses = new ArrayList();
    for (Gadget gadget : ((CosmeticManager)getPlugin()).getGadgetManager().getGadgets(GadgetType.Costume))
    {
      OutfitGadget outfitGadget = (OutfitGadget)gadget;
      Class clazz = gadget.getClass();
      if (!costumeClasses.contains(clazz)) {
        costumeClasses.add(clazz);
      }
      int slot = costumeClasses.indexOf(clazz) * 2 + 3 + 18;
      if (outfitGadget.GetSlot() == OutfitGadget.ArmorSlot.Chest) {
        slot += 9;
      } else if (outfitGadget.GetSlot() == OutfitGadget.ArmorSlot.Legs) {
        slot += 18;
      } else if (outfitGadget.GetSlot() == OutfitGadget.ArmorSlot.Boots) {
        slot += 27;
      }
      addGadget(gadget, slot);
      if (gadget.IsActive(getPlayer())) {
        addGlow(slot);
      }
    }
    addButton(8, new ShopItem(Material.TNT, C.cRed + C.Bold + "Remove all Clothing", new String[0], 1, false), new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        boolean gadgetDisabled = false;
        for (Gadget gadget : ((CosmeticManager)CostumePage.this.getPlugin()).getGadgetManager().getGadgets(GadgetType.Costume)) {
          if (gadget.IsActive(player))
          {
            gadgetDisabled = true;
            gadget.Disable(player);
          }
        }
        if (gadgetDisabled)
        {
          CostumePage.this.buildPage();
          player.playSound(player.getEyeLocation(), Sound.SPLASH, 1.0F, 1.0F);
        }
      }
    });
    addButton(4, new ShopItem(Material.BED, C.cGray + " ⇽ Go Back", new String[0], 1, false), new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ((CosmeticShop)CostumePage.this.getShop()).openPageForPlayer(CostumePage.this.getPlayer(), new Menu((CosmeticManager)CostumePage.this.getPlugin(), (CosmeticShop)CostumePage.this.getShop(), CostumePage.this.getClientManager(), CostumePage.this.getDonationManager(), player));
      }
    });
  }
  
  public void activateGadget(Player player, Gadget gadget)
  {
    if (((gadget instanceof ItemGadget)) && (((ClientInventory)((CosmeticManager)getPlugin()).getInventoryManager().Get(player)).getItemCount(gadget.GetName()) <= 0))
    {
      purchaseGadget(player, gadget);
      return;
    }
    playAcceptSound(player);
    gadget.Enable(player);
    buildPage();
  }
}
