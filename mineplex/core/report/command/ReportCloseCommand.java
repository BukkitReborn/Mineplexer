package mineplex.core.report.command;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.report.ReportManager;
import mineplex.core.report.ReportPlugin;
import org.bukkit.entity.Player;

public class ReportCloseCommand
  extends CommandBase<ReportPlugin>
{
  public ReportCloseCommand(ReportPlugin plugin)
  {
    super(plugin, Rank.ADMIN, new String[] { "reportclose", "rc" });
  }
  
  public void Execute(Player player, String[] args)
  {
    if ((args == null) || (args.length < 2))
    {
      UtilPlayer.message(player, F.main(((ReportPlugin)this.Plugin).getName(), C.cRed + "Your arguments are inappropriate for this command!"));
      return;
    }
    int reportId = Integer.parseInt(args[0]);
    String reason = F.combine(args, 1, null, false);
    
    ReportManager.getInstance().closeReport(reportId, player, reason);
  }
}
