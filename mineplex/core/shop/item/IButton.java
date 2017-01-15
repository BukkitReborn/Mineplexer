package mineplex.core.shop.item;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public abstract interface IButton
{
  public abstract void onClick(Player paramPlayer, ClickType paramClickType);
}
