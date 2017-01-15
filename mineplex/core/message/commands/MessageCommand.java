package mineplex.core.message.commands;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.message.MessageManager;
import org.bukkit.entity.Player;

public class MessageCommand
  extends CommandBase<MessageManager>
{
  public MessageCommand(MessageManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "m", "msg", "message", "tell", "t", "w", "whisper", "MSG" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if (args == null)
    {
      ((MessageManager)this.Plugin).Help(caller);
    }
    else
    {
      if (args.length == 0)
      {
        UtilPlayer.message(caller, F.main(((MessageManager)this.Plugin).getName(), "Player argument missing."));
        return;
      }
      String message = "Beep!";
      if (args.length > 1) {
        message = F.combine(args, 1, null, false);
      } else {
        message = ((MessageManager)this.Plugin).GetRandomMessage();
      }
      ((MessageManager)this.Plugin).sendMessage(caller, args[0], message, false, false);
    }
  }
}
