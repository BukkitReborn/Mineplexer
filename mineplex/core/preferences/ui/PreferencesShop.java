package mineplex.core.preferences.ui;

import mineplex.core.account.CoreClientManager;
import mineplex.core.common.CurrencyType;
import mineplex.core.donation.DonationManager;
import mineplex.core.preferences.PreferencesManager;
import mineplex.core.shop.ShopBase;
import mineplex.core.shop.page.ShopPageBase;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PreferencesShop
  extends ShopBase<PreferencesManager>
{
  public PreferencesShop(PreferencesManager plugin, CoreClientManager clientManager, DonationManager donationManager)
  {
    super(plugin, clientManager, donationManager, "User Preferences", new CurrencyType[0]);
  }
  
  protected ShopPageBase<PreferencesManager, ? extends ShopBase<PreferencesManager>> buildPagesFor(Player player)
  {
    return new PreferencesPage((PreferencesManager)getPlugin(), this, getClientManager(), getDonationManager(), "          " + ChatColor.UNDERLINE + "User Preferences", player);
  }
}
