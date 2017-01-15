package mineplex.core.message.commands;

import mineplex.core.account.CoreClient;
import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.C;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.common.util.UtilServer;
import mineplex.core.message.MessageManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class AdminCommand
  extends CommandBase<MessageManager>
{
  public AdminCommand(MessageManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "a", "admin" });
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
        UtilPlayer.message(caller, F.main(((MessageManager)this.Plugin).getName(), "Message argument missing."));
        return;
      }
      if (((MessageManager)this.Plugin).isMuted(caller)) {
        return;
      }
      String message = F.combine(args, 0, null, false);
      
      UtilPlayer.message(caller, F.rank(((MessageManager)this.Plugin).GetClientManager().Get(caller).GetRank()) + " " + caller.getName() + " " + C.cPurple + message);
      
      boolean staff = false;
      Player[] arrayOfPlayer;
      int j = (arrayOfPlayer = UtilServer.getPlayers()).length;
      for (int i = 0; i < j; i++)
      {
        Player to = arrayOfPlayer[i];
        if (((MessageManager)this.Plugin).GetClientManager().Get(to).GetRank().Has(Rank.HELPER))
        {
          if (!to.equals(caller)) {
            UtilPlayer.message(to, F.rank(((MessageManager)this.Plugin).GetClientManager().Get(caller).GetRank()) + " " + caller.getName() + " " + C.cPurple + message);
          }
          staff = true;
          
          to.playSound(to.getLocation(), Sound.NOTE_PLING, 0.5F, 2.0F);
        }
      }
      if (!staff) {
        UtilPlayer.message(caller, F.main(((MessageManager)this.Plugin).getName(), "There are no Staff Members online."));
      }
    }
  }
}
