package redis.clients.jedis;

import java.util.List;
import java.util.Set;

public abstract interface MultiKeyCommandsPipeline
{
  public abstract Response<Long> del(String... paramVarArgs);
  
  public abstract Response<List<String>> blpop(String... paramVarArgs);
  
  public abstract Response<List<String>> brpop(String... paramVarArgs);
  
  public abstract Response<Set<String>> keys(String paramString);
  
  public abstract Response<List<String>> mget(String... paramVarArgs);
  
  public abstract Response<String> mset(String... paramVarArgs);
  
  public abstract Response<Long> msetnx(String... paramVarArgs);
  
  public abstract Response<String> rename(String paramString1, String paramString2);
  
  public abstract Response<Long> renamenx(String paramString1, String paramString2);
  
  public abstract Response<String> rpoplpush(String paramString1, String paramString2);
  
  public abstract Response<Set<String>> sdiff(String... paramVarArgs);
  
  public abstract Response<Long> sdiffstore(String paramString, String... paramVarArgs);
  
  public abstract Response<Set<String>> sinter(String... paramVarArgs);
  
  public abstract Response<Long> sinterstore(String paramString, String... paramVarArgs);
  
  public abstract Response<Long> smove(String paramString1, String paramString2, String paramString3);
  
  public abstract Response<Long> sort(String paramString1, SortingParams paramSortingParams, String paramString2);
  
  public abstract Response<Long> sort(String paramString1, String paramString2);
  
  public abstract Response<Set<String>> sunion(String... paramVarArgs);
  
  public abstract Response<Long> sunionstore(String paramString, String... paramVarArgs);
  
  public abstract Response<String> watch(String... paramVarArgs);
  
  public abstract Response<Long> zinterstore(String paramString, String... paramVarArgs);
  
  public abstract Response<Long> zinterstore(String paramString, ZParams paramZParams, String... paramVarArgs);
  
  public abstract Response<Long> zunionstore(String paramString, String... paramVarArgs);
  
  public abstract Response<Long> zunionstore(String paramString, ZParams paramZParams, String... paramVarArgs);
  
  public abstract Response<String> brpoplpush(String paramString1, String paramString2, int paramInt);
  
  public abstract Response<Long> publish(String paramString1, String paramString2);
  
  public abstract Response<String> randomKey();
  
  public abstract Response<Long> bitop(BitOP paramBitOP, String paramString, String... paramVarArgs);
}
