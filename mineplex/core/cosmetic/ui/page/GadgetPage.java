package mineplex.core.cosmetic.ui.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.C;
import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.cosmetic.ui.button.ActivateGadgetButton;
import mineplex.core.cosmetic.ui.button.DeactivateGadgetButton;
import mineplex.core.cosmetic.ui.button.GadgetButton;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.gadgets.Ammo;
import mineplex.core.gadget.gadgets.MorphBlock;
import mineplex.core.gadget.gadgets.MorphNotch;
import mineplex.core.gadget.types.Gadget;
import mineplex.core.gadget.types.GadgetType;
import mineplex.core.gadget.types.ItemGadget;
import mineplex.core.inventory.ClientInventory;
import mineplex.core.inventory.InventoryManager;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import mineplex.core.shop.page.ConfirmationPage;
import mineplex.core.shop.page.ShopPageBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class GadgetPage
  extends ShopPageBase<CosmeticManager, CosmeticShop>
{
  public GadgetPage(CosmeticManager plugin, CosmeticShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player)
  {
    super(plugin, shop, clientManager, donationManager, name, player, 54);
    
    buildPage();
  }
  
  protected void buildPage()
  {
    int slot = 19;
    for (Gadget gadget : ((CosmeticManager)getPlugin()).getGadgetManager().getGadgets(GadgetType.Item))
    {
      addGadget(gadget, slot);
      if (((ClientInventory)((CosmeticManager)getPlugin()).getInventoryManager().Get(getPlayer())).getItemCount(gadget.GetDisplayName()) > 0) {
        addGlow(slot);
      }
      slot++;
      if (slot == 26) {
        slot = 28;
      }
    }
    addButton(4, new ShopItem(Material.BED, C.cGray + " ? Go Back", new String[0], 1, false), new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ((CosmeticShop)GadgetPage.this.getShop()).openPageForPlayer(GadgetPage.this.getPlayer(), new Menu((CosmeticManager)GadgetPage.this.getPlugin(), (CosmeticShop)GadgetPage.this.getShop(), GadgetPage.this.getClientManager(), GadgetPage.this.getDonationManager(), player));
      }
    });
  }
  
  protected void addGadget(Gadget gadget, int slot)
  {
    if ((!(gadget instanceof MorphNotch)) && 
      ((gadget instanceof MorphBlock)) && 
      (getPlayer().getPassenger() != null)) {
      return;
    }
    List<String> itemLore = new ArrayList();
    if (gadget.GetCost(CurrencyType.Coins) >= 0) {
      itemLore.add(C.cAqua + gadget.GetCost(CurrencyType.Coins) + " Shards");
    } else if (gadget.GetCost(CurrencyType.Coins) == -2) {
      itemLore.add(C.cGold + "Found in Treasure Chests.");
    }
    itemLore.add(C.cBlack);
    itemLore.addAll(Arrays.asList(gadget.GetDescription()));
    if ((gadget instanceof ItemGadget))
    {
      itemLore.add(C.cBlack);
      itemLore.add(C.cGreen + "Right-Click To Purchase:");
      itemLore.add(C.cWhite + ((ItemGadget)gadget).getAmmo().GetDisplayName() + " for " + C.cAqua + ((ItemGadget)gadget).getAmmo().GetCost(CurrencyType.Coins) + " Shards");
      itemLore.add(C.cBlack);
      itemLore.add(C.cWhite + "Your Ammo : " + C.cGreen + ((ClientInventory)((CosmeticManager)getPlugin()).getInventoryManager().Get(getPlayer())).getItemCount(gadget.GetName()));
    }
    if ((gadget.IsFree()) || (((Donor)getDonationManager().Get(getPlayer().getName())).OwnsUnknownPackage(gadget.GetName())))
    {
      if (gadget.GetActive().contains(getPlayer())) {
        addButton(slot, new ShopItem(gadget.GetDisplayMaterial(), gadget.GetDisplayData(), "Deactivate " + gadget.GetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new DeactivateGadgetButton(gadget, this));
      } else {
        addButton(slot, new ShopItem(gadget.GetDisplayMaterial(), gadget.GetDisplayData(), "Activate " + gadget.GetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new ActivateGadgetButton(gadget, this));
      }
    }
    else if ((gadget.GetCost(CurrencyType.Coins) > 0) && (((Donor)getDonationManager().Get(getPlayer().getName())).GetBalance(CurrencyType.Coins) >= gadget.GetCost(CurrencyType.Coins))) {
      addButton(slot, new ShopItem(Material.INK_SACK, (byte)8, (gadget.GetCost(CurrencyType.Coins) < 0 ? "" : "Purchase ") + gadget.GetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new GadgetButton(gadget, this));
    } else {
      setItem(slot, new ShopItem(Material.INK_SACK, (byte)8, (gadget.GetCost(CurrencyType.Coins) < 0 ? "" : "Purchase ") + gadget.GetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, true, false));
    }
  }
  
  public void purchaseGadget(Player player, final Gadget gadget)
  {
    ((CosmeticShop)getShop()).openPageForPlayer(getPlayer(), new ConfirmationPage(getPlugin(), getShop(), getClientManager(), getDonationManager(), new Runnable()
    {
      public void run()
      {
        ((CosmeticManager)GadgetPage.this.getPlugin()).getInventoryManager().addItemToInventory(GadgetPage.this.getPlayer(), gadget.getGadgetType().name(), gadget.GetName(), (gadget instanceof ItemGadget) ? ((ItemGadget)gadget).getAmmo().getQuantity() : gadget.getQuantity());
        GadgetPage.this.refresh();
      }
    }, this, (gadget instanceof ItemGadget) ? ((ItemGadget)gadget)
    
      .getAmmo() : gadget, CurrencyType.Coins, getPlayer()));
  }
  
  public void activateGadget(Player player, Gadget gadget)
  {
    if (((gadget instanceof ItemGadget)) && 
      (((ClientInventory)((CosmeticManager)getPlugin()).getInventoryManager().Get(player)).getItemCount(gadget.GetName()) <= 0))
    {
      purchaseGadget(player, gadget);
      return;
    }
    playAcceptSound(player);
    gadget.Enable(player);
    
    ((CosmeticShop)getShop()).openPageForPlayer(getPlayer(), new Menu((CosmeticManager)getPlugin(), (CosmeticShop)getShop(), getClientManager(), getDonationManager(), player));
  }
  
  public void handleRightClick(Player player, Gadget gadget)
  {
    if ((gadget instanceof ItemGadget)) {
      purchaseGadget(player, gadget);
    }
  }
  
  public void deactivateGadget(Player player, Gadget gadget)
  {
    playAcceptSound(player);
    gadget.Disable(player);
    refresh();
  }
}
