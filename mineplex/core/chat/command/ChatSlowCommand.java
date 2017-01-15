package mineplex.core.chat.command;

import mineplex.core.chat.Chat;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import org.bukkit.entity.Player;

public class ChatSlowCommand
  extends CommandBase<Chat>
{
  public ChatSlowCommand(Chat plugin)
  {
    super(plugin, Rank.SNR_MODERATOR, new String[] { "chatslow" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if ((args != null) && (args.length == 1)) {
      try
      {
        int seconds = Integer.parseInt(args[0]);
        if (seconds < 0)
        {
          UtilPlayer.message(caller, F.main("Chat", "Seconds must be a positive integer"));
          return;
        }
        ((Chat)this.Plugin).setChatSlow(seconds, true);
        UtilPlayer.message(caller, F.main("Chat", "Set chat slow to " + F.time(new StringBuilder().append(seconds).append(" seconds").toString())));
      }
      catch (Exception e)
      {
        showUsage(caller);
      }
    }
    showUsage(caller);
  }
  
  private void showUsage(Player caller)
  {
    UtilPlayer.message(caller, F.main("Chat", "Usage: /chatslow <seconds>"));
  }
}
