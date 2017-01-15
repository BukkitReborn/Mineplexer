package redis.clients.jedis;

import java.util.List;
import redis.clients.util.Slowlog;

public abstract interface AdvancedJedisCommands
{
  public abstract List<String> configGet(String paramString);
  
  public abstract String configSet(String paramString1, String paramString2);
  
  public abstract String slowlogReset();
  
  public abstract Long slowlogLen();
  
  public abstract List<Slowlog> slowlogGet();
  
  public abstract List<Slowlog> slowlogGet(long paramLong);
  
  public abstract Long objectRefcount(String paramString);
  
  public abstract String objectEncoding(String paramString);
  
  public abstract Long objectIdletime(String paramString);
}
