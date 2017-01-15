package mineplex.core.scoreboard.elements;

import java.util.ArrayList;
import java.util.HashMap;
import mineplex.core.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;

public class ScoreboardElementScores
  extends ScoreboardElement
{
  private String _key;
  private HashMap<String, Integer> _scores;
  private boolean _higherIsBetter;
  
  public ScoreboardElementScores(String key, String line, int value, boolean higherIsBetter)
  {
    this._scores = new HashMap();
    
    this._key = key;
    
    AddScore(line, value);
    
    this._higherIsBetter = higherIsBetter;
  }
  
  public ArrayList<String> GetLines(ScoreboardManager manager, Player player)
  {
    ArrayList<String> orderedScores = new ArrayList();
    while (orderedScores.size() < this._scores.size())
    {
      String bestKey = null;
      int bestScore = 0;
      for (String key : this._scores.keySet()) {
        if (!orderedScores.contains(key)) {
          if ((bestKey == null) || 
            ((this._higherIsBetter) && (((Integer)this._scores.get(key)).intValue() >= bestScore)) || (
            (!this._higherIsBetter) && (((Integer)this._scores.get(key)).intValue() <= bestScore)))
          {
            bestKey = key;
            bestScore = ((Integer)this._scores.get(key)).intValue();
          }
        }
      }
      orderedScores.add(bestKey);
    }
    return orderedScores;
  }
  
  public boolean IsKey(String key)
  {
    return this._key.equals(key);
  }
  
  public void AddScore(String line, int value)
  {
    this._scores.put(line, Integer.valueOf(value));
  }
}
