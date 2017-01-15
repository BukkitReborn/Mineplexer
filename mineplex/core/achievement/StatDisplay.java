package mineplex.core.achievement;

public class StatDisplay
{
  public String _displayName;
  public String[] _stats;
  public static final StatDisplay WINS = new StatDisplay("Wins");
  public static final StatDisplay LOSSES = new StatDisplay("Losses");
  public static final StatDisplay KILLS = new StatDisplay("Kills");
  public static final StatDisplay DEATHS = new StatDisplay("Deaths");
  public static final StatDisplay GEMS_EARNED = new StatDisplay("Gems Earned", new String[] { "GemsEarned" });
  public static final StatDisplay TIME_IN_GAME = new StatDisplay("Time In Game", new String[] { "TimeInGame" });
  public static final StatDisplay GAMES_PLAYED = new StatDisplay("Games Played", new String[] { "Wins", "Losses" });
  
  public StatDisplay(String stat)
  {
    this._displayName = stat;
    this._stats = new String[] { stat };
  }
  
  public StatDisplay(String displayName, String... stats)
  {
    this._displayName = displayName;
    this._stats = stats;
  }
  
  public String getDisplayName()
  {
    return this._displayName;
  }
  
  public String[] getStats()
  {
    return this._stats;
  }
}
