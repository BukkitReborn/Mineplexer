package mineplex.core.scoreboard;

import java.util.ArrayList;
import mineplex.core.scoreboard.elements.ScoreboardElement;
import mineplex.core.scoreboard.elements.ScoreboardElementCoinCount;
import mineplex.core.scoreboard.elements.ScoreboardElementGemCount;
import mineplex.core.scoreboard.elements.ScoreboardElementRank;
import mineplex.core.scoreboard.elements.ScoreboardElementScores;
import mineplex.core.scoreboard.elements.ScoreboardElementText;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ScoreboardData
{
  private ArrayList<ScoreboardElement> _elements = new ArrayList();
  
  public ArrayList<String> getLines(ScoreboardManager manager, Player player)
  {
    ArrayList<String> output = new ArrayList();
    for (ScoreboardElement elem : this._elements) {
      output.addAll(elem.GetLines(manager, player));
    }
    return output;
  }
  
  public void reset()
  {
    this._elements.clear();
  }
  
  public String prepareLine(String line)
  {
    if (line.length() > 28)
    {
      String line1 = line.substring(0, 16);
      String color = ChatColor.getLastColors(line1);
      String line2 = line.substring(16);
      
      int length = 16 - (color + line2).length();
      if (length > 0) {
        return line1 + line2.substring(0, line2.length() - length);
      }
    }
    return line;
  }
  
  public void write(String line)
  {
    line = prepareLine(line);
    
    this._elements.add(new ScoreboardElementText(line));
  }
  
  public void writeOrdered(String key, String line, int value, boolean prependScore)
  {
    if (prependScore) {
      line = value + " " + line;
    }
    line = prepareLine(line);
    for (ScoreboardElement elem : this._elements) {
      if ((elem instanceof ScoreboardElementScores))
      {
        ScoreboardElementScores scores = (ScoreboardElementScores)elem;
        if (scores.IsKey(key))
        {
          scores.AddScore(line, value);
          return;
        }
      }
    }
    this._elements.add(new ScoreboardElementScores(key, line, value, true));
  }
  
  public void writeEmpty()
  {
    this._elements.add(new ScoreboardElementText(" "));
  }
  
  public void writePlayerGems()
  {
    this._elements.add(new ScoreboardElementGemCount());
  }
  
  public void writePlayerCoins()
  {
    this._elements.add(new ScoreboardElementCoinCount());
  }
  
  public void writePlayerRank()
  {
    this._elements.add(new ScoreboardElementRank());
  }
}
