package mineplex.core.leaderboard;

public enum StatType
{
  WIN(1),  LOSS(2),  KILL(3),  DEATH(4);
  
  private int _typeId;
  
  public int getTypeId()
  {
    return this._typeId;
  }
  
  private StatType(int typeId)
  {
    this._typeId = typeId;
  }
  
  public static StatType getType(String stat)
  {
    String str;
    switch ((str = stat.toUpperCase().trim()).hashCode())
    {
    case -2043639023: 
      if (str.equals("LOSSES")) {}
      break;
    case 2664471: 
      if (str.equals("WINS")) {
        break;
      }
      break;
    case 71514293: 
      if (str.equals("KILLS")) {}
      break;
    case 2012524671: 
      if (!str.equals("DEATHS"))
      {
        break label120;
        return WIN;
        
        return LOSS;
        
        return KILL;
      }
      else
      {
        return DEATH;
      }
      break;
    }
    label120:
    return null;
  }
}
