package mineplex.core.elo;

public class KFactor
{
  public int startIndex;
  public int endIndex;
  public double value;
  
  public KFactor(int startIndex, int endIndex, double value)
  {
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.value = value;
  }
  
  public int getStartIndex()
  {
    return this.startIndex;
  }
  
  public int getEndIndex()
  {
    return this.endIndex;
  }
  
  public double getValue()
  {
    return this.value;
  }
  
  public String toString()
  {
    return "kfactor: " + this.startIndex + " " + this.endIndex + " " + this.value;
  }
}
