package mineplex.core.punish.UI;

import mineplex.core.punish.Punishment;
import mineplex.core.shop.item.IButton;
import mineplex.core.shop.item.ShopItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class RemovePunishmentButton
  implements IButton
{
  private PunishPage _punishPage;
  private Punishment _punishment;
  private ShopItem _item;
  
  public RemovePunishmentButton(PunishPage punishPage, Punishment punishment, ShopItem item)
  {
    this._punishPage = punishPage;
    this._punishment = punishment;
    this._item = item;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._punishPage.RemovePunishment(this._punishment, this._item);
  }
}
