package mineplex.core.scoreboard.elements;

import java.util.ArrayList;
import mineplex.core.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;

public abstract class ScoreboardElement
{
  public abstract ArrayList<String> GetLines(ScoreboardManager paramScoreboardManager, Player paramPlayer);
}
