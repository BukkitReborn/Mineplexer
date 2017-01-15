package redis.clients.util;

import java.util.ArrayList;
import java.util.List;

public class Slowlog
{
  private final long id;
  private final long timeStamp;
  private final long executionTime;
  private final List<String> args;
  
  public static List<Slowlog> from(List<Object> nestedMultiBulkReply)
  {
    List<Slowlog> logs = new ArrayList(nestedMultiBulkReply.size());
    for (Object obj : nestedMultiBulkReply)
    {
      List<Object> properties = (List)obj;
      logs.add(new Slowlog(properties));
    }
    return logs;
  }
  
  private Slowlog(List<Object> properties)
  {
    this.id = ((Long)properties.get(0)).longValue();
    this.timeStamp = ((Long)properties.get(1)).longValue();
    this.executionTime = ((Long)properties.get(2)).longValue();
    
    List<byte[]> bargs = (List)properties.get(3);
    this.args = new ArrayList(bargs.size());
    for (byte[] barg : bargs) {
      this.args.add(SafeEncoder.encode(barg));
    }
  }
  
  public long getId()
  {
    return this.id;
  }
  
  public long getTimeStamp()
  {
    return this.timeStamp;
  }
  
  public long getExecutionTime()
  {
    return this.executionTime;
  }
  
  public List<String> getArgs()
  {
    return this.args;
  }
}
