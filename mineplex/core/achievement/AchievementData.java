package mineplex.core.achievement;

public class AchievementData
{
  private int _level;
  private long _expRemainder;
  private long _expNextLevel;
  
  public AchievementData(int level, long expRemainder, long expNextLevel)
  {
    this._level = level;
    this._expRemainder = expRemainder;
    this._expNextLevel = expNextLevel;
  }
  
  public int getLevel()
  {
    return this._level;
  }
  
  public long getExpRemainder()
  {
    return this._expRemainder;
  }
  
  public long getExpNextLevel()
  {
    return this._expNextLevel;
  }
}
