package redis.clients.jedis;

import java.util.List;
import java.util.Set;

public abstract interface MultiKeyCommands
{
  public abstract Long del(String... paramVarArgs);
  
  public abstract List<String> blpop(int paramInt, String... paramVarArgs);
  
  public abstract List<String> brpop(int paramInt, String... paramVarArgs);
  
  public abstract List<String> blpop(String... paramVarArgs);
  
  public abstract List<String> brpop(String... paramVarArgs);
  
  public abstract Set<String> keys(String paramString);
  
  public abstract List<String> mget(String... paramVarArgs);
  
  public abstract String mset(String... paramVarArgs);
  
  public abstract Long msetnx(String... paramVarArgs);
  
  public abstract String rename(String paramString1, String paramString2);
  
  public abstract Long renamenx(String paramString1, String paramString2);
  
  public abstract String rpoplpush(String paramString1, String paramString2);
  
  public abstract Set<String> sdiff(String... paramVarArgs);
  
  public abstract Long sdiffstore(String paramString, String... paramVarArgs);
  
  public abstract Set<String> sinter(String... paramVarArgs);
  
  public abstract Long sinterstore(String paramString, String... paramVarArgs);
  
  public abstract Long smove(String paramString1, String paramString2, String paramString3);
  
  public abstract Long sort(String paramString1, SortingParams paramSortingParams, String paramString2);
  
  public abstract Long sort(String paramString1, String paramString2);
  
  public abstract Set<String> sunion(String... paramVarArgs);
  
  public abstract Long sunionstore(String paramString, String... paramVarArgs);
  
  public abstract String watch(String... paramVarArgs);
  
  public abstract String unwatch();
  
  public abstract Long zinterstore(String paramString, String... paramVarArgs);
  
  public abstract Long zinterstore(String paramString, ZParams paramZParams, String... paramVarArgs);
  
  public abstract Long zunionstore(String paramString, String... paramVarArgs);
  
  public abstract Long zunionstore(String paramString, ZParams paramZParams, String... paramVarArgs);
  
  public abstract String brpoplpush(String paramString1, String paramString2, int paramInt);
  
  public abstract Long publish(String paramString1, String paramString2);
  
  public abstract void subscribe(JedisPubSub paramJedisPubSub, String... paramVarArgs);
  
  public abstract void psubscribe(JedisPubSub paramJedisPubSub, String... paramVarArgs);
  
  public abstract String randomKey();
  
  public abstract Long bitop(BitOP paramBitOP, String paramString, String... paramVarArgs);
  
  @Deprecated
  public abstract ScanResult<String> scan(int paramInt);
  
  public abstract ScanResult<String> scan(String paramString);
}
