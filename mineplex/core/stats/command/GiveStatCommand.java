package mineplex.core.stats.command;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.stats.StatsManager;
import org.bukkit.entity.Player;

public class GiveStatCommand
  extends CommandBase<StatsManager>
{
  public GiveStatCommand(StatsManager plugin)
  {
    super(plugin, Rank.OWNER, new String[] { "givestat" });
  }
  
  public void Execute(final Player caller, final String[] args)
  {
    if (args.length < 3)
    {
      UtilPlayer.message(caller, F.main("Stats", "/givestat <Target> <Name> <Amount>"));
      return;
    }
    try
    {
      Player player = UtilPlayer.searchOnline(caller, args[0], true);
      
      String tempStatName = args[1];
      for (int i = 2; i < args.length - 1; i++) {
        tempStatName = tempStatName + " " + args[i];
      }
      final String statName = tempStatName;
      if (player == null) {
        ((StatsManager)this.Plugin).getClientManager().loadClientByName(args[0], new Runnable()
        {
          public void run()
          {
            CoreClient client = ((StatsManager)GiveStatCommand.this.Plugin).getClientManager().Get(args[0]);
            if (client != null) {
              ((StatsManager)GiveStatCommand.this.Plugin).incrementStat(client.getAccountId(), statName, Long.parseLong(args[(args.length - 1)]));
            } else {
              caller.sendMessage(F.main(((StatsManager)GiveStatCommand.this.Plugin).getName(), "Couldn't find " + args[0] + "'s account!"));
            }
          }
        });
      } else {
        ((StatsManager)this.Plugin).incrementStat(player, statName, Integer.parseInt(args[(args.length - 1)]));
      }
      UtilPlayer.message(caller, F.main("Stats", "Applied " + F.elem(new StringBuilder(String.valueOf(Integer.parseInt(args[(args.length - 1)]))).append(" ").append(statName).toString()) + " to " + F.elem(player.getName()) + "."));
    }
    catch (Exception e)
    {
      UtilPlayer.message(caller, F.main("Stats", "/givestat <Name> <Amount>"));
    }
  }
}
