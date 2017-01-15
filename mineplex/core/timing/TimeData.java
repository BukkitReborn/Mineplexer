package mineplex.core.timing;

import java.io.PrintStream;

public class TimeData
{
  public String Title;
  public long Started;
  public long LastMarker;
  public long Total;
  
  public TimeData(String title, long time)
  {
    this.Title = title;
    this.Started = time;
    this.LastMarker = time;
    this.Total = 0L;
  }
  
  public int Count = 0;
  
  public void addTime()
  {
    this.Total += System.currentTimeMillis() - this.LastMarker;
    this.LastMarker = System.currentTimeMillis();
    this.Count += 1;
  }
  
  public void printInfo()
  {
    System.out.println("]==[TIME DATA]==[ " + this.Count + " " + this.Title + " took " + this.Total + "ms in the last " + (System.currentTimeMillis() - this.Started) + "ms");
  }
}
