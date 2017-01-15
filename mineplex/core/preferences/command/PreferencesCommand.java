package mineplex.core.preferences.command;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.preferences.PreferencesManager;
import org.bukkit.entity.Player;

public class PreferencesCommand
  extends CommandBase<PreferencesManager>
{
  public PreferencesCommand(PreferencesManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "prefs" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    ((PreferencesManager)this.Plugin).openShop(caller);
  }
}
