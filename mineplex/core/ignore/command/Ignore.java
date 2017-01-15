package mineplex.core.ignore.command;

import mineplex.core.account.CoreClientManager;
import mineplex.core.command.CommandBase;
import mineplex.core.command.CommandCenter;
import mineplex.core.common.Rank;
import mineplex.core.common.util.Callback;
import mineplex.core.ignore.IgnoreManager;
import org.bukkit.entity.Player;

public class Ignore
  extends CommandBase<IgnoreManager>
{
  public Ignore(IgnoreManager plugin)
  {
    super(plugin, Rank.ALL, new String[] { "ignore" });
  }
  
  public void Execute(final Player caller, String[] args)
  {
    if (args == null) {
      ((IgnoreManager)this.Plugin).showIgnores(caller);
    } else {
      this.CommandCenter.GetClientManager().checkPlayerName(caller, args[0], new Callback()
      {
        public void run(String result)
        {
          if (result != null) {
            ((IgnoreManager)Ignore.this.Plugin).addIgnore(caller, result);
          }
        }
      });
    }
  }
}
