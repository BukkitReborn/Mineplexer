package redis.clients.jedis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract interface JedisCommands
{
  public abstract String set(String paramString1, String paramString2);
  
  public abstract String get(String paramString);
  
  public abstract Boolean exists(String paramString);
  
  public abstract Long persist(String paramString);
  
  public abstract String type(String paramString);
  
  public abstract Long expire(String paramString, int paramInt);
  
  public abstract Long expireAt(String paramString, long paramLong);
  
  public abstract Long ttl(String paramString);
  
  public abstract Boolean setbit(String paramString, long paramLong, boolean paramBoolean);
  
  public abstract Boolean setbit(String paramString1, long paramLong, String paramString2);
  
  public abstract Boolean getbit(String paramString, long paramLong);
  
  public abstract Long setrange(String paramString1, long paramLong, String paramString2);
  
  public abstract String getrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract String getSet(String paramString1, String paramString2);
  
  public abstract Long setnx(String paramString1, String paramString2);
  
  public abstract String setex(String paramString1, int paramInt, String paramString2);
  
  public abstract Long decrBy(String paramString, long paramLong);
  
  public abstract Long decr(String paramString);
  
  public abstract Long incrBy(String paramString, long paramLong);
  
  public abstract Long incr(String paramString);
  
  public abstract Long append(String paramString1, String paramString2);
  
  public abstract String substr(String paramString, int paramInt1, int paramInt2);
  
  public abstract Long hset(String paramString1, String paramString2, String paramString3);
  
  public abstract String hget(String paramString1, String paramString2);
  
  public abstract Long hsetnx(String paramString1, String paramString2, String paramString3);
  
  public abstract String hmset(String paramString, Map<String, String> paramMap);
  
  public abstract List<String> hmget(String paramString, String... paramVarArgs);
  
  public abstract Long hincrBy(String paramString1, String paramString2, long paramLong);
  
  public abstract Boolean hexists(String paramString1, String paramString2);
  
  public abstract Long hdel(String paramString, String... paramVarArgs);
  
  public abstract Long hlen(String paramString);
  
  public abstract Set<String> hkeys(String paramString);
  
  public abstract List<String> hvals(String paramString);
  
  public abstract Map<String, String> hgetAll(String paramString);
  
  public abstract Long rpush(String paramString, String... paramVarArgs);
  
  public abstract Long lpush(String paramString, String... paramVarArgs);
  
  public abstract Long llen(String paramString);
  
  public abstract List<String> lrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract String ltrim(String paramString, long paramLong1, long paramLong2);
  
  public abstract String lindex(String paramString, long paramLong);
  
  public abstract String lset(String paramString1, long paramLong, String paramString2);
  
  public abstract Long lrem(String paramString1, long paramLong, String paramString2);
  
  public abstract String lpop(String paramString);
  
  public abstract String rpop(String paramString);
  
  public abstract Long sadd(String paramString, String... paramVarArgs);
  
  public abstract Set<String> smembers(String paramString);
  
  public abstract Long srem(String paramString, String... paramVarArgs);
  
  public abstract String spop(String paramString);
  
  public abstract Long scard(String paramString);
  
  public abstract Boolean sismember(String paramString1, String paramString2);
  
  public abstract String srandmember(String paramString);
  
  public abstract Long strlen(String paramString);
  
  public abstract Long zadd(String paramString1, double paramDouble, String paramString2);
  
  public abstract Long zadd(String paramString, Map<String, Double> paramMap);
  
  public abstract Set<String> zrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract Long zrem(String paramString, String... paramVarArgs);
  
  public abstract Double zincrby(String paramString1, double paramDouble, String paramString2);
  
  public abstract Long zrank(String paramString1, String paramString2);
  
  public abstract Long zrevrank(String paramString1, String paramString2);
  
  public abstract Set<String> zrevrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract Set<Tuple> zrangeWithScores(String paramString, long paramLong1, long paramLong2);
  
  public abstract Set<Tuple> zrevrangeWithScores(String paramString, long paramLong1, long paramLong2);
  
  public abstract Long zcard(String paramString);
  
  public abstract Double zscore(String paramString1, String paramString2);
  
  public abstract List<String> sort(String paramString);
  
  public abstract List<String> sort(String paramString, SortingParams paramSortingParams);
  
  public abstract Long zcount(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Long zcount(String paramString1, String paramString2, String paramString3);
  
  public abstract Set<String> zrangeByScore(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Set<String> zrangeByScore(String paramString1, String paramString2, String paramString3);
  
  public abstract Set<String> zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Set<String> zrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Set<String> zrevrangeByScore(String paramString1, String paramString2, String paramString3);
  
  public abstract Set<String> zrangeByScore(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Set<String> zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Set<Tuple> zrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Set<Tuple> zrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Set<String> zrevrangeByScore(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Set<Tuple> zrangeByScoreWithScores(String paramString1, String paramString2, String paramString3);
  
  public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramString1, String paramString2, String paramString3);
  
  public abstract Set<Tuple> zrangeByScoreWithScores(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Set<Tuple> zrevrangeByScoreWithScores(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract Long zremrangeByRank(String paramString, long paramLong1, long paramLong2);
  
  public abstract Long zremrangeByScore(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract Long zremrangeByScore(String paramString1, String paramString2, String paramString3);
  
  public abstract Long linsert(String paramString1, BinaryClient.LIST_POSITION paramLIST_POSITION, String paramString2, String paramString3);
  
  public abstract Long lpushx(String paramString, String... paramVarArgs);
  
  public abstract Long rpushx(String paramString, String... paramVarArgs);
  
  public abstract List<String> blpop(String paramString);
  
  public abstract List<String> brpop(String paramString);
  
  public abstract Long del(String paramString);
  
  public abstract String echo(String paramString);
  
  public abstract Long move(String paramString, int paramInt);
  
  public abstract Long bitcount(String paramString);
  
  public abstract Long bitcount(String paramString, long paramLong1, long paramLong2);
  
  @Deprecated
  public abstract ScanResult<Map.Entry<String, String>> hscan(String paramString, int paramInt);
  
  @Deprecated
  public abstract ScanResult<String> sscan(String paramString, int paramInt);
  
  @Deprecated
  public abstract ScanResult<Tuple> zscan(String paramString, int paramInt);
  
  public abstract ScanResult<Map.Entry<String, String>> hscan(String paramString1, String paramString2);
  
  public abstract ScanResult<String> sscan(String paramString1, String paramString2);
  
  public abstract ScanResult<Tuple> zscan(String paramString1, String paramString2);
}
