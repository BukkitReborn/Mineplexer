package mineplex.core.chat.command;

import mineplex.core.chat.Chat;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.UtilServer;
import org.bukkit.entity.Player;

public class BroadcastCommand
  extends CommandBase<Chat>
{
  public BroadcastCommand(Chat plugin)
  {
    super(plugin, Rank.MODERATOR, new String[] { "s" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    String announcement = "";
    String[] arrayOfString;
    int j = (arrayOfString = args).length;
    for (int i = 0; i < j; i++)
    {
      String arg = arrayOfString[i];
      announcement = announcement + arg + " ";
    }
    if (announcement.length() > 0) {
      announcement = announcement.substring(0, announcement.length() - 1);
    }
    UtilServer.broadcast(caller.getName(), announcement);
  }
}
