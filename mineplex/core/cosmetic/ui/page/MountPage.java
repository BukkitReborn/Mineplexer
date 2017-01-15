package mineplex.core.cosmetic.ui.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.common.util.C;
import mineplex.core.cosmetic.CosmeticManager;
import mineplex.core.cosmetic.ui.CosmeticShop;
import mineplex.core.cosmetic.ui.button.ActivateMountButton;
import mineplex.core.cosmetic.ui.button.DeactivateMountButton;
import mineplex.core.cosmetic.ui.button.MountButton;
import mineplex.core.donation.DonationManager;
import mineplex.core.donation.Donor;
import mineplex.core.mount.Mount;
import mineplex.core.mount.MountManager;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import mineplex.core.shop.page.ShopPageBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class MountPage
  extends ShopPageBase<CosmeticManager, CosmeticShop>
{
  public MountPage(CosmeticManager plugin, CosmeticShop shop, CoreClientManager clientManager, DonationManager donationManager, String name, Player player)
  {
    super(plugin, shop, clientManager, donationManager, name, player, 54);
    buildPage();
  }
  
  protected void buildPage()
  {
    int slot = 19;
    for (Mount mount : ((CosmeticManager)getPlugin()).getMountManager().getMounts())
    {
      addMount(mount, slot);
      slot++;
      if (slot == 26) {
        slot = 28;
      }
    }
  }
  
  protected void addMount(Mount<?> mount, int slot)
  {
    ArrayList<String> itemLore = new ArrayList();
    if (mount.GetCost(CurrencyType.Coins) != -1) {
      itemLore.add(C.cAqua + mount.GetCost(CurrencyType.Coins) + " Shards");
    }
    itemLore.add(C.cBlack);
    itemLore.addAll(Arrays.asList(mount.GetDescription()));
    if (((Donor)getDonationManager().Get(getPlayer().getName())).OwnsUnknownPackage(mount.GetName()))
    {
      if (mount.GetActive().containsKey(getPlayer())) {
        addButton(slot, new ShopItem(mount.GetDisplayMaterial(), mount.GetDisplayData(), "Deactivate " + mount.GetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new DeactivateMountButton(mount, this));
      } else {
        addButton(slot, new ShopItem(mount.GetDisplayMaterial(), mount.GetDisplayData(), "Activate " + mount.GetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new ActivateMountButton(mount, this));
      }
    }
    else if ((mount.GetCost(CurrencyType.Coins) != -1) && (((Donor)getDonationManager().Get(getPlayer().getName())).GetBalance(CurrencyType.Coins) >= mount.GetCost(CurrencyType.Coins))) {
      addButton(slot, new ShopItem(mount.GetDisplayMaterial(), mount.GetDisplayData(), (mount.GetCost(CurrencyType.Coins) < 0 ? "" : "Purchase ") + mount.GetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, false, false), new MountButton(mount, this));
    } else {
      setItem(slot, new ShopItem(mount.GetDisplayMaterial(), mount.GetDisplayData(), (mount.GetCost(CurrencyType.Coins) < 0 ? "" : "Purchase ") + mount.GetName(), (String[])itemLore.toArray(new String[itemLore.size()]), 1, true, false));
    }
    addButton(4, new ShopItem(Material.BED, C.cGray + " ⇽ Go Back", new String[0], 1, false), new IButton()
    {
      public void onClick(Player player, ClickType clickType)
      {
        ((CosmeticShop)MountPage.this.getShop()).openPageForPlayer(MountPage.this.getPlayer(), new Menu((CosmeticManager)MountPage.this.getPlugin(), (CosmeticShop)MountPage.this.getShop(), MountPage.this.getClientManager(), MountPage.this.getDonationManager(), player));
      }
    });
  }
}
