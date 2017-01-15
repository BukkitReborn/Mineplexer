package mineplex.core.elo;

import java.io.PrintStream;

public class EloPlayer
{
  public String UniqueId;
  public int Rating;
  
  public void printInfo()
  {
    System.out.println(this.UniqueId + "'s elo is " + this.Rating);
  }
}
