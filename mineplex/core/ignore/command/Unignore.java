package mineplex.core.ignore.command;

import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.command.CommandCenter;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.common.util.F;
import mineplex.core.ignore.IgnoreManager;
import org.bukkit.entity.Player;

public class Unignore
  extends CommandBase<IgnoreManager>
{
  public Unignore(IgnoreManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "unignore" });
  }
  
  public void Execute(final Player caller, String[] args)
  {
    if (args == null) {
      caller.sendMessage(F.main(((IgnoreManager)this.Plugin).getName(), "You need to include a player's name."));
    } else {
      this.CommandCenter.GetClientManager().checkPlayerName(caller, args[0], new Callback()
      {
        public void run(String result)
        {
          if (result != null) {
            ((IgnoreManager)Unignore.this.Plugin).removeIgnore(caller, result);
          }
        }
      });
    }
  }
}
