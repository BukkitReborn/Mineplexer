package mineplex.core.stats.command;

import java.sql.SQLException;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilTime;
import mineplex.core.common.util.UtilTime.TimeUnit;
import mineplex.core.stats.PlayerStats;
import mineplex.core.stats.StatsManager;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class TimeCommand
  extends CommandBase<StatsManager>
{
  public TimeCommand(StatsManager plugin)
  {
    super(plugin, Rank.MODERATOR, new String[] { "time" });
  }
  
  public void Execute(final Player caller, final String[] args)
  {
    if ((args == null) || (args.length == 0))
    {
      UtilPlayer.message(caller, F.main("Time", "Usage: /time <playerName>"));
    }
    else
    {
      Player target = UtilPlayer.searchOnline(caller, args[0], false);
      if (target == null)
      {
        ((StatsManager)this.Plugin).getPlugin().getServer().getScheduler().runTaskAsynchronously(((StatsManager)this.Plugin).getPlugin(), new Runnable()
        {
          public void run()
          {
            try
            {
              final PlayerStats stats = ((StatsManager)TimeCommand.this.Plugin).getOfflinePlayerStats(args[0]);
              
              ((StatsManager)TimeCommand.this.Plugin).getPlugin().getServer().getScheduler().runTask(((StatsManager)TimeCommand.this.Plugin).getPlugin(), new Runnable()
              {
                public void run()
                {
                  if (stats == null)
                  {
                    UtilPlayer.message(this.val$caller, F.main("Time", "Player " + F.elem(this.val$args[0]) + " not found!"));
                  }
                  else
                  {
                    long time = stats.getStat("Global.TimeInGame");
                    UtilPlayer.message(this.val$caller, F.main("Time", F.name(this.val$args[0]) + " has spent " + F.elem(UtilTime.convertString(time * 1000L, 1, UtilTime.TimeUnit.FIT)) + " in game"));
                  }
                }
              });
            }
            catch (SQLException e)
            {
              e.printStackTrace();
            }
          }
        });
      }
      else
      {
        long time = ((PlayerStats)((StatsManager)this.Plugin).Get(target)).getStat("Global.TimeInGame");
        UtilPlayer.message(caller, F.main("Time", F.name(target.getName()) + " has spent " + F.elem(UtilTime.convertString(time * 1000L, 1, UtilTime.TimeUnit.FIT)) + " in game"));
      }
    }
  }
}
