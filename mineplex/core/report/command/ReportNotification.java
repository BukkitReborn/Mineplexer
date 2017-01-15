package mineplex.core.report.command;

import mineplex.core.common.util.UtilServer;
import mineplex.core.report.ReportManager;
import mineplex.serverdata.commands.ServerCommand;
import org.bukkit.entity.Player;

public class ReportNotification
  extends ServerCommand
{
  private String notification;
  
  public ReportNotification(String notification)
  {
    super(new String[0]);
  }
  
  public void run()
  {
    Player[] arrayOfPlayer;
    int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
    for (int i = 0; i < j; i++)
    {
      Player player = arrayOfPlayer[i];
      if (ReportManager.getInstance().hasReportNotifications(player)) {
        player.sendMessage(this.notification);
      }
    }
  }
}
