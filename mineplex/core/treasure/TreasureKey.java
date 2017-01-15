package mineplex.core.treasure;

import mineplex.core.common.CurrencyType;
import mineplex.core.shop.item.SalesPackageBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class TreasureKey
  extends SalesPackageBase
{
  public TreasureKey()
  {
    super("Treasure Key", Material.TRIPWIRE_HOOK, (byte)0, new String[] { ChatColor.RESET + "Used to open Treasure Chests" }, 1000);
    
    this.KnownPackage = false;
    this.OneTimePurchaseOnly = false;
  }
  
  public void Sold(Player player, CurrencyType currencyType) {}
}
