package mineplex.core.achievement.command;

import mineplex.core.achievement.AchievementManager;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.UtilPlayer;
import org.bukkit.entity.Player;

public class StatsCommand
  extends CommandBase<AchievementManager>
{
  public StatsCommand(AchievementManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "stats" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if ((args == null) || (args.length == 0))
    {
      ((AchievementManager)this.Plugin).openShop(caller);
    }
    else
    {
      Player target = UtilPlayer.searchOnline(caller, args[0], true);
      if (target == null) {
        return;
      }
      ((AchievementManager)this.Plugin).openShop(caller, target);
    }
  }
}
