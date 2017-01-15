package mineplex.core.teleport.command;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.teleport.Teleport;
import org.bukkit.entity.Player;

public class LocateCommand
  extends CommandBase<Teleport>
{
  public LocateCommand(Teleport plugin)
  {
    super(plugin, Rank.MODERATOR, new String[] { "locate", "where", "find" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if ((args == null) || (args.length == 0))
    {
      UtilPlayer.message(caller, F.main("Locate", "Player argument missing."));
      return;
    }
    ((Teleport)this.Plugin).locatePlayer(caller, args[0]);
  }
}
