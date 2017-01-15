package mineplex.core.database;

public class DatabaseRunnable
{
  private Runnable _runnable;
  private int _failedAttempts = 0;
  
  public DatabaseRunnable(Runnable runnable)
  {
    this._runnable = runnable;
  }
  
  public void run()
  {
    this._runnable.run();
  }
  
  public void incrementFailCount()
  {
    this._failedAttempts += 1;
  }
  
  public int getFailedCounts()
  {
    return this._failedAttempts;
  }
}
