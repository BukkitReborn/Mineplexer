package mineplex.core.cosmetic.ui.button;

import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class CloseButton
  implements IButton
{
  public void onClick(Player player, ClickType clickType)
  {
    player.closeInventory();
  }
}
