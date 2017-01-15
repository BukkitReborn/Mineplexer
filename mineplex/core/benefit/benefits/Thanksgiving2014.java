package mineplex.core.benefit.benefits;

import mineplex.core.benefit.BenefitManager;
import mineplex.core.benefit.BenefitManagerRepository;
import mineplex.core.common.util.C;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.inventory.InventoryManager;
import org.bukkit.entity.Player;

public class Thanksgiving2014
  extends BenefitBase
{
  private InventoryManager _inventoryManager;
  
  public Thanksgiving2014(BenefitManager plugin, BenefitManagerRepository repository, InventoryManager inventoryManager)
  {
    super(plugin, "Thanksgiving2014", repository);
    this._inventoryManager = inventoryManager;
  }
  
  public void rewardPlayer(final Player player)
  {
    this._inventoryManager.addItemToInventory(new Callback()
    {
      public void run(Boolean success)
      {
        if (success.booleanValue())
        {
          UtilPlayer.message(player, C.cPurple + C.Strike + "=============================================");
          UtilPlayer.message(player, "");
          UtilPlayer.message(player, C.cGold + "HAPPY THANKSGIVING");
          UtilPlayer.message(player, "You received 1 Treasure Key!");
          UtilPlayer.message(player, "");
          UtilPlayer.message(player, C.cPurple + C.Strike + "=============================================");
        }
      }
    }, player, "Treasure", "Treasure Key", 1);
  }
}
