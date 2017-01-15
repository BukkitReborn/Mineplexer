package mineplex.core.treasure.gui;

import mineplex.core.shop.item.IButton;
import mineplex.core.treasure.TreasureLocation;
import mineplex.core.treasure.TreasureType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class OpenTreasureButton
  implements IButton
{
  private Player _player;
  private TreasureLocation _treasureLocation;
  private TreasureType _treasureType;
  
  public OpenTreasureButton(Player player, TreasureLocation treasureLocation, TreasureType treasureType)
  {
    this._player = player;
    this._treasureLocation = treasureLocation;
    this._treasureType = treasureType;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._treasureLocation.attemptOpenTreasure(player, this._treasureType);
    player.closeInventory();
  }
}
