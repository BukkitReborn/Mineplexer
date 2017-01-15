package mineplex.core.personalServer;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.recharge.Recharge;
import org.bukkit.entity.Player;

public class HostServerCommand
  extends CommandBase<PersonalServerManager>
{
  public HostServerCommand(PersonalServerManager plugin)
  {
    super(plugin, Rank.LEGEND, new String[] { "hostserver" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if (!Recharge.Instance.use(caller, "Host Server", 30000L, false, false)) {
      return;
    }
    ((PersonalServerManager)this.Plugin).hostServer(caller, caller.getName(), false);
  }
}
