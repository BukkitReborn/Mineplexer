package mineplex.core.scoreboard.elements;

import java.util.ArrayList;
import mineplex.core.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;

public class ScoreboardElementText
  extends ScoreboardElement
{
  private String _line;
  
  public ScoreboardElementText(String line)
  {
    this._line = line;
  }
  
  public ArrayList<String> GetLines(ScoreboardManager manager, Player player)
  {
    ArrayList<String> orderedScores = new ArrayList();
    
    orderedScores.add(this._line);
    
    return orderedScores;
  }
}
