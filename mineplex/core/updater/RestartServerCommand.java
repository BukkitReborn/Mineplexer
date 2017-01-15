package mineplex.core.updater;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.portal.Portal;
import mineplex.serverdata.commands.RestartCommand;
import org.bukkit.entity.Player;

public class RestartServerCommand
  extends CommandBase<FileUpdater>
{
  public RestartServerCommand(FileUpdater plugin)
  {
    super(plugin, Rank.ADMIN, new Rank[] { Rank.JNR_DEV }, new String[] { "restart" });
  }
  
  public void Execute(final Player caller, final String[] args)
  {
    if ((args == null) || (args.length < 1))
    {
      UtilPlayer.message(caller, F.main("Restart", "You must specify a server to restart."));
      return;
    }
    Portal.getInstance().doesServerExist(args[0], new Callback()
    {
      public void run(Boolean serverExists)
      {
        if (serverExists.booleanValue())
        {
          new RestartCommand(args[0], ((FileUpdater)RestartServerCommand.this.Plugin).getRegion()).publish();
          UtilPlayer.message(caller, F.main("Restart", "Sent restart command to " + C.cGold + args[0] + C.cGray + "."));
        }
        else
        {
          UtilPlayer.message(caller, F.main("Restart", C.cGold + args[0] + C.cGray + " doesn't exist."));
        }
      }
    });
  }
}
