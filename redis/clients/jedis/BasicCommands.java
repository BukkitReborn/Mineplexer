package redis.clients.jedis;

public abstract interface BasicCommands
{
  public abstract String ping();
  
  public abstract String quit();
  
  public abstract String flushDB();
  
  public abstract Long dbSize();
  
  public abstract String select(int paramInt);
  
  public abstract String flushAll();
  
  public abstract String auth(String paramString);
  
  public abstract String save();
  
  public abstract String bgsave();
  
  public abstract String bgrewriteaof();
  
  public abstract Long lastsave();
  
  public abstract String shutdown();
  
  public abstract String info();
  
  public abstract String info(String paramString);
  
  public abstract String slaveof(String paramString, int paramInt);
  
  public abstract String slaveofNoOne();
  
  public abstract Long getDB();
  
  public abstract String debug(DebugParams paramDebugParams);
  
  public abstract String configResetStat();
  
  public abstract Long waitReplicas(int paramInt, long paramLong);
}
