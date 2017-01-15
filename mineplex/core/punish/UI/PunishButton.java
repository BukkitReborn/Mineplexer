package mineplex.core.punish.UI;

import mineplex.core.punish.Category;
import mineplex.core.shop.item.IButton;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class PunishButton
  implements IButton
{
  private PunishPage _punishPage;
  private Category _category;
  private int _severity;
  private boolean _ban;
  private long _time;
  
  public PunishButton(PunishPage punishPage, Category category, int severity, boolean ban, long time)
  {
    this._punishPage = punishPage;
    this._category = category;
    this._severity = severity;
    this._ban = ban;
    this._time = time;
  }
  
  public void onClick(Player player, ClickType clickType)
  {
    this._punishPage.AddInfraction(this._category, this._severity, this._ban, this._time);
  }
}
