package mineplex.core.npc.command;

import java.sql.SQLException;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.common.util.F;
import mineplex.core.common.util.UtilPlayer;
import mineplex.core.npc.NpcManager;
import org.bukkit.entity.Player;

public class ClearCommand
  extends CommandBase<NpcManager>
{
  public ClearCommand(NpcManager plugin)
  {
    super(plugin, Rank.DEVELOPER, new String[] { "clear" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if (args != null) {
      ((NpcManager)this.Plugin).help(caller);
    } else {
      try
      {
        ((NpcManager)this.Plugin).clearNpcs(true);
        
        UtilPlayer.message(caller, F.main(((NpcManager)this.Plugin).getName(), "Cleared NPCs."));
      }
      catch (SQLException e)
      {
        ((NpcManager)this.Plugin).help(caller, "Database error.");
      }
    }
  }
}
