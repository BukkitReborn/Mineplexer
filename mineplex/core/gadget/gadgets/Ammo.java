package mineplex.core.gadget.gadgets;

import mineplex.core.common.CurrencyType;
import mineplex.core.shop.item.SalesPackageBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Ammo
  extends SalesPackageBase
{
  public Ammo(String name, String displayName, Material material, byte displayData, String[] description, int coins, int quantity)
  {
    super(name, material, displayData, description, coins, quantity);
    
    this.DisplayName = displayName;
    this.KnownPackage = false;
    this.OneTimePurchaseOnly = false;
  }
  
  public void Sold(Player player, CurrencyType currencyType) {}
}
