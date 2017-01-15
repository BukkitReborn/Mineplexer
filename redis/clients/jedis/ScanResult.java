package redis.clients.jedis;

import java.util.List;

public class ScanResult<T>
{
  private String cursor;
  private List<T> results;
  
  @Deprecated
  public ScanResult(int cursor, List<T> results)
  {
    this.cursor = String.valueOf(cursor);
    this.results = results;
  }
  
  public ScanResult(String cursor, List<T> results)
  {
    this.cursor = cursor;
    this.results = results;
  }
  
  @Deprecated
  public int getCursor()
  {
    return Integer.parseInt(this.cursor);
  }
  
  public String getStringCursor()
  {
    return this.cursor;
  }
  
  public List<T> getResult()
  {
    return this.results;
  }
}
