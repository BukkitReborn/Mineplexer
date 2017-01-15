package mineplex.core.personalServer;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.recharge.Recharge;
import org.bukkit.entity.Player;

public class HostEventServerCommand
  extends CommandBase<PersonalServerManager>
{
  public HostEventServerCommand(PersonalServerManager plugin)
  {
    super(plugin, Rank.ADMIN, new String[] { "hostevent" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if (!Recharge.Instance.use(caller, "Host Event", 30000L, false, false)) {
      return;
    }
    ((PersonalServerManager)this.Plugin).hostServer(caller, caller.getName(), true);
  }
}
