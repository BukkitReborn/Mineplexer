package mineplex.core.portal.Commands;

import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.command.CommandCenter;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.portal.Portal;
import org.bukkit.entity.Player;

public class SendCommand
  extends CommandBase<Portal>
{
  public SendCommand(Portal plugin)
  {
    super(plugin, Rank.ADMIN, new Rank[] { Rank.JNR_DEV }, new String[] { "send" });
  }
  
  public void Execute(final Player player, String[] args)
  {
    if ((args == null) || (args.length == 0))
    {
      UtilPlayer.message(player, F.main(((Portal)this.Plugin).getName(), C.cRed + "Your arguments are inappropriate for this command!"));
      return;
    }
    if (args.length != 2)
    {
      UtilPlayer.message(player, F.main(((Portal)this.Plugin).getName(), C.cRed + "Your arguments are inappropriate for this command!"));
      return;
    }
    final String playerTarget = args[0];
    final String serverTarget = args[1];
    this.CommandCenter.GetClientManager().checkPlayerName(player, playerTarget, new Callback()
    {
      public void run(final String playerName)
      {
        if (playerName == null)
        {
          UtilPlayer.message(player, F.main(((Portal)SendCommand.this.Plugin).getName(), C.cGray + "Player " + C.cGold + playerTarget + C.cGray + " does not exist!"));
          return;
        }
        ((Portal)SendCommand.this.Plugin).doesServerExist(serverTarget, new Callback()
        {
          public void run(Boolean serverExists)
          {
            if (!serverExists.booleanValue())
            {
              UtilPlayer.message(this.val$player, F.main(((Portal)SendCommand.this.Plugin).getName(), C.cGray + "Server " + C.cGold + this.val$serverTarget + C.cGray + " does not exist!"));
              return;
            }
            Portal.transferPlayer(playerName, this.val$serverTarget);
            UtilPlayer.message(this.val$player, F.main(((Portal)SendCommand.this.Plugin).getName(), C.cGray + "You have sent player: " + C.cGold + playerName + C.cGray + " to server: " + C.cGold + this.val$serverTarget + C.cGray + "!"));
          }
        });
      }
    });
  }
}
