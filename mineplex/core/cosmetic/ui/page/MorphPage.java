package mineplex.core.cosmetic.ui.page;

import mineplex.core.account.CoreClientManager;
import mineplex.core.common.util.C;
import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.donation.DonationManager;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.Gadget;
import mineplex.core.gadget.types.GadgetType;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class MorphPage
  extends GadgetPage
{
  public MorphPage(CosmeticManager plugin, CosmeticShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player)
  {
    super(plugin, shop, clientManager, donationManager, name, player);
  }
  
  protected void buildPage()
  {
    int slot = 19;
    for (Gadget gadget : ((CosmeticManager)getPlugin()).getGadgetManager().getGadgets(GadgetType.Morph))
    {
      addGadget(gadget, slot);
      if (((CosmeticManager)getPlugin()).getGadgetManager().getActive(getPlayer(), GadgetType.Morph) == gadget) {
        addGlow(slot);
      }
      slot++;
      if (slot == 26) {
        slot = 28;
      }
    }
    addButton(4, new ShopItem(Material.BED, C.cGray + " ⇽ Go Back", new String[0], 1, false), new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ((CosmeticShop)MorphPage.this.getShop()).openPageForPlayer(MorphPage.this.getPlayer(), new Menu((CosmeticManager)MorphPage.this.getPlugin(), (CosmeticShop)MorphPage.this.getShop(), MorphPage.this.getClientManager(), MorphPage.this.getDonationManager(), player));
      }
    });
  }
}
