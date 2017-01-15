package mineplex.core.give.commands;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import mineplex.core.give.Give;
import org.bukkit.entity.Player;

public class GiveCommand
  extends CommandBase<Give>
{
  public GiveCommand(Give plugin)
  {
    super(plugin, Rank.ADMIN, new String[] { "give", "g", "item", "i" });
  }
  
  public void Execute(Player caller, String[] args)
  {
    ((Give)this.Plugin).parseInput(caller, args);
  }
}
