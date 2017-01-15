package mineplex.core.leaderboard;

import mineplex.core.command.CommandBase;
import mineplex.core.common.Rank;
import org.bukkit.entity.Player;

public class SetTournamentCommand
  extends CommandBase<LeaderboardManager>
{
  public SetTournamentCommand(LeaderboardManager plugin)
  {
    super(plugin, Rank.ADMIN, new String[] { "settournament", "set-tournament" });
  }
  
  public void Execute(Player caller, String[] args) {}
}
