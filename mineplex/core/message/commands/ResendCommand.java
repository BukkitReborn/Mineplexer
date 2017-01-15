package mineplex.core.message.commands;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.message.ClientMessage;
import mineplex.core.message.MessageManager;
import org.bukkit.entity.Player;

public class ResendCommand
  extends CommandBase<MessageManager>
{
  public ResendCommand(MessageManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "r" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if (args == null)
    {
      ((MessageManager)this.Plugin).Help(caller);
    }
    else
    {
      String lastTo = ((ClientMessage)((MessageManager)this.Plugin).Get(caller)).LastTo;
      if (lastTo == null)
      {
        UtilPlayer.message(caller, F.main(((MessageManager)this.Plugin).getName(), "You have not messaged anyone recently."));
        return;
      }
      String message = "Beep!";
      if (args.length > 0) {
        message = F.combine(args, 0, null, false);
      } else {
        message = ((MessageManager)this.Plugin).GetRandomMessage();
      }
      ((MessageManager)this.Plugin).sendMessage(caller, lastTo, message, true, false);
    }
  }
}
