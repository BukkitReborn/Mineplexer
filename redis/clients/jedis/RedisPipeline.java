package redis.clients.jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface RedisPipeline
{
  public abstract Response<Long> append(String paramString1, String paramString2);
  
  public abstract Response<List<String>> blpop(String paramString);
  
  public abstract Response<List<String>> brpop(String paramString);
  
  public abstract Response<Long> decr(String paramString);
  
  public abstract Response<Long> decrBy(String paramString, long paramLong);
  
  public abstract Response<Long> del(String paramString);
  
  public abstract Response<String> echo(String paramString);
  
  public abstract Response<Boolean> exists(String paramString);
  
  public abstract Response<Long> expire(String paramString, int paramInt);
  
  public abstract Response<Long> expireAt(String paramString, long paramLong);
  
  public abstract Response<String> get(String paramString);
  
  public abstract Response<Boolean> getbit(String paramString, long paramLong);
  
  public abstract Response<String> getrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract Response<String> getSet(String paramString1, String paramString2);
  
  public abstract Response<Long> hdel(String paramString, String... paramVarArgs);
  
  public abstract Response<Boolean> hexists(String paramString1, String paramString2);
  
  public abstract Response<String> hget(String paramString1, String paramString2);
  
  public abstract Response<Map<String, String>> hgetAll(String paramString);
  
  public abstract Response<Long> hincrBy(String paramString1, String paramString2, long paramLong);
  
  public abstract Response<Set<String>> hkeys(String paramString);
  
  public abstract Response<Long> hlen(String paramString);
  
  public abstract Response<List<String>> hmget(String paramString, String... paramVarArgs);
  
  public abstract Response<String> hmset(String paramString, Map<String, String> paramMap);
  
  public abstract Response<Long> hset(String paramString1, String paramString2, String paramString3);
  
  public abstract Response<Long> hsetnx(String paramString1, String paramString2, String paramString3);
  
  public abstract Response<List<String>> hvals(String paramString);
  
  public abstract Response<Long> incr(String paramString);
  
  public abstract Response<Long> incrBy(String paramString, long paramLong);
  
  public abstract Response<String> lindex(String paramString, long paramLong);
  
  public abstract Response<Long> linsert(String paramString1, BinaryClient.LIST_POSITION paramLIST_POSITION, String paramString2, String paramString3);
  
  public abstract Response<Long> llen(String paramString);
  
  public abstract Response<String> lpop(String paramString);
  
  public abstract Response<Long> lpush(String paramString, String... paramVarArgs);
  
  public abstract Response<Long> lpushx(String paramString, String... paramVarArgs);
  
  public abstract Response<List<String>> lrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract Response<Long> lrem(String paramString1, long paramLong, String paramString2);
  
  public abstract Response<String> lset(String paramString1, long paramLong, String paramString2);
  
  public abstract Response<String> ltrim(String paramString, long paramLong1, long paramLong2);
  
  public abstract Response<Long> move(String paramString, int paramInt);
  
  public abstract Response<Long> persist(String paramString);
  
  public abstract Response<String> rpop(String paramString);
  
  public abstract Response<Long> rpush(String paramString, String... paramVarArgs);
  
  public abstract Response<Long> rpushx(String paramString, String... paramVarArgs);
  
  public abstract Response<Long> sadd(String paramString, String... paramVarArgs);
  
  public abstract Response<Long> scard(String paramString);
  
  public abstract Response<Boolean> sismember(String paramString1, String paramString2);
  
  public abstract Response<String> set(String paramString1, String paramString2);
  
  public abstract Response<Boolean> setbit(String paramString, long paramLong, boolean paramBoolean);
  
  public abstract Response<String> setex(String paramString1, int paramInt, String paramString2);
  
  public abstract Response<Long> setnx(String paramString1, String paramString2);
  
  public abstract Response<Long> setrange(String paramString1, long paramLong, String paramString2);
  
  public abstract Response<Set<String>> smembers(String paramString);
  
  public abstract Response<List<String>> sort(String paramString);
  
  public abstract Response<List<String>> sort(String paramString, SortingParams paramSortingParams);
  
  public abstract Response<String> spop(String paramString);
  
  public abstract Response<String> srandmember(String paramString);
  
  public abstract Response<Long> srem(String paramString, String... paramVarArgs);
  
  public abstract Response<Long> strlen(String paramString);
  
  public abstract Response<String> substr(String paramString, int paramInt1, int paramInt2);
  
  public abstract Response<Long> ttl(String paramString);
  
  public abstract Response<String> type(String paramString);
  
  public abstract Response<Long> zadd(String paramString1, double paramDouble, String paramString2);
  
  public abstract Response<Long> zcard(String paramString);
  
  public abstract Response<Long> zcount(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Response<Double> zincrby(String paramString1, double paramDouble, String paramString2);
  
  public abstract Response<Set<String>> zrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract Response<Set<String>> zrangeByScore(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Response<Set<String>> zrangeByScore(String paramString1, String paramString2, String paramString3);
  
  public abstract Response<Set<String>> zrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Response<Set<Tuple>> zrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Response<Set<Tuple>> zrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Response<Set<String>> zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Response<Set<String>> zrevrangeByScore(String paramString1, String paramString2, String paramString3);
  
  public abstract Response<Set<String>> zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Response<Set<Tuple>> zrevrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Response<Set<Tuple>> zrevrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Response<Set<Tuple>> zrangeWithScores(String paramString, long paramLong1, long paramLong2);
  
  public abstract Response<Long> zrank(String paramString1, String paramString2);
  
  public abstract Response<Long> zrem(String paramString, String... paramVarArgs);
  
  public abstract Response<Long> zremrangeByRank(String paramString, long paramLong1, long paramLong2);
  
  public abstract Response<Long> zremrangeByScore(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Response<Set<String>> zrevrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract Response<Set<Tuple>> zrevrangeWithScores(String paramString, long paramLong1, long paramLong2);
  
  public abstract Response<Long> zrevrank(String paramString1, String paramString2);
  
  public abstract Response<Double> zscore(String paramString1, String paramString2);
  
  public abstract Response<Long> bitcount(String paramString);
  
  public abstract Response<Long> bitcount(String paramString, long paramLong1, long paramLong2);
}
