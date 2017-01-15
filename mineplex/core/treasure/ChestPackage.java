package mineplex.core.treasure;

import mineplex.core.common.CurrencyType;
import mineplex.core.shop.item.SalesPackageBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ChestPackage
  extends SalesPackageBase
{
  public ChestPackage(String name, Material mat, int cost)
  {
    super(name, mat, (byte)0, new String[0], cost);
    
    this.KnownPackage = false;
    this.OneTimePurchaseOnly = false;
  }
  
  public void Sold(Player player, CurrencyType currencyType) {}
}
