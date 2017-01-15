package redis.clients.jedis;

public abstract interface BasicRedisPipeline
{
  public abstract Response<String> bgrewriteaof();
  
  public abstract Response<String> bgsave();
  
  public abstract Response<String> configGet(String paramString);
  
  public abstract Response<String> configSet(String paramString1, String paramString2);
  
  public abstract Response<String> configResetStat();
  
  public abstract Response<String> save();
  
  public abstract Response<Long> lastsave();
  
  public abstract Response<String> flushDB();
  
  public abstract Response<String> flushAll();
  
  public abstract Response<String> info();
  
  public abstract Response<Long> dbSize();
  
  public abstract Response<String> shutdown();
  
  public abstract Response<String> ping();
  
  public abstract Response<String> select(int paramInt);
}
