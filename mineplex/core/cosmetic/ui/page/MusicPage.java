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

public class MusicPage
  extends GadgetPage
{
  public MusicPage(CosmeticManager plugin, CosmeticShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player)
  {
    super(plugin, shop, clientManager, donationManager, name, player);
  }
  
  protected void buildPage()
  {
    int slot = 19;
    for (Gadget gadget : ((CosmeticManager)getPlugin()).getGadgetManager().getGadgets(GadgetType.MusicDisc))
    {
      addGadget(gadget, slot);
      slot++;
      if (slot == 26) {
        slot = 28;
      }
    }
    addButton(4, new ShopItem(Material.BED, C.cGray + " ⇽ Go Back", new String[0], 1, false), new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ((CosmeticShop)MusicPage.this.getShop()).openPageForPlayer(MusicPage.this.getPlayer(), new Menu((CosmeticManager)MusicPage.this.getPlugin(), (CosmeticShop)MusicPage.this.getShop(), MusicPage.this.getClientManager(), MusicPage.this.getDonationManager(), player));
      }
    });
  }
  
  public void activateGadget(Player player, Gadget gadget)
  {
    super.activateGadget(player, gadget);
    player.closeInventory();
  }
}
