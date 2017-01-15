package mineplex.core.npc.command;

import java.sql.SQLException;
import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.npc.NpcManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class AddCommand
  extends CommandBase<NpcManager>
{
  public AddCommand(NpcManager plugin)
  {
    super(plugin, Rank.DEVELOPER, new String[] { "add" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    if (args == null)
    {
      ((NpcManager)this.Plugin).help(caller);
    }
    else
    {
      try
      {
        type = EntityType.valueOf(args[0].toUpperCase());
      }
      catch (IllegalArgumentException e)
      {
        EntityType type;
        ((NpcManager)this.Plugin).help(caller, "Invalid entity."); return;
      }
      EntityType type;
      double radius = 0.0D;
      if (args.length >= 2) {
        try
        {
          radius = Double.parseDouble(args[1]);
        }
        catch (NumberFormatException e)
        {
          ((NpcManager)this.Plugin).help(caller, "Invalid radius.");
          
          return;
        }
      }
      boolean adult = true;
      if (args.length >= 3) {
        adult = Boolean.parseBoolean(args[2]);
      }
      String name = null;
      if (args.length >= 4)
      {
        name = args[3];
        for (int i = 4; i < args.length; i++) {
          name = name + " " + args[i];
        }
      }
      try
      {
        ((NpcManager)this.Plugin).addNpc(caller, type, radius, adult, name, null);
      }
      catch (SQLException e)
      {
        ((NpcManager)this.Plugin).help(caller, "Database error.");
      }
    }
  }
}
