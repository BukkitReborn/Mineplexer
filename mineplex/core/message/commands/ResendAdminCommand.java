package mineplex.core.message.commands;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.message.ClientMessage;
import mineplex.core.message.MessageManager;
import org.bukkit.entity.Player;

public class ResendAdminCommand
  extends CommandBase<MessageManager>
{
  public ResendAdminCommand(MessageManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "ra" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if (args == null)
    {
      ((MessageManager)this.Plugin).Help(caller);
    }
    else
    {
      if (!((MessageManager)this.Plugin).GetClientManager().Get(caller).GetRank().Has(caller, Rank.HELPER, true)) {
        return;
      }
      String lastTo = ((ClientMessage)((MessageManager)this.Plugin).Get(caller)).LastAdminTo;
      if (lastTo == null)
      {
        UtilPlayer.message(caller, F.main(((MessageManager)this.Plugin).getName(), "You have not admin messaged anyone recently."));
        return;
      }
      String message = "Beep!";
      if (args.length > 0) {
        message = F.combine(args, 0, null, false);
      } else {
        message = ((MessageManager)this.Plugin).GetRandomMessage();
      }
      ((MessageManager)this.Plugin).sendMessage(caller, lastTo, message, true, true);
    }
  }
}
