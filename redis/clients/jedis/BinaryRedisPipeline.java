package redis.clients.jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface BinaryRedisPipeline
{
  public abstract Response<Long> append(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<List<byte[]>> blpop(byte[] paramArrayOfByte);
  
  public abstract Response<List<byte[]>> brpop(byte[] paramArrayOfByte);
  
  public abstract Response<Long> decr(byte[] paramArrayOfByte);
  
  public abstract Response<Long> decrBy(byte[] paramArrayOfByte, long paramLong);
  
  public abstract Response<Long> del(byte[] paramArrayOfByte);
  
  public abstract Response<byte[]> echo(byte[] paramArrayOfByte);
  
  public abstract Response<Boolean> exists(byte[] paramArrayOfByte);
  
  public abstract Response<Long> expire(byte[] paramArrayOfByte, int paramInt);
  
  public abstract Response<Long> expireAt(byte[] paramArrayOfByte, long paramLong);
  
  public abstract Response<byte[]> get(byte[] paramArrayOfByte);
  
  public abstract Response<Boolean> getbit(byte[] paramArrayOfByte, long paramLong);
  
  public abstract Response<byte[]> getSet(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Long> getrange(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Response<Long> hdel(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Boolean> hexists(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<byte[]> hget(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Map<byte[], byte[]>> hgetAll(byte[] paramArrayOfByte);
  
  public abstract Response<Long> hincrBy(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong);
  
  public abstract Response<Set<byte[]>> hkeys(byte[] paramArrayOfByte);
  
  public abstract Response<Long> hlen(byte[] paramArrayOfByte);
  
  public abstract Response<List<byte[]>> hmget(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<String> hmset(byte[] paramArrayOfByte, Map<byte[], byte[]> paramMap);
  
  public abstract Response<Long> hset(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Response<Long> hsetnx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Response<List<byte[]>> hvals(byte[] paramArrayOfByte);
  
  public abstract Response<Long> incr(byte[] paramArrayOfByte);
  
  public abstract Response<Long> incrBy(byte[] paramArrayOfByte, long paramLong);
  
  public abstract Response<byte[]> lindex(byte[] paramArrayOfByte, long paramLong);
  
  public abstract Response<Long> linsert(byte[] paramArrayOfByte1, BinaryClient.LIST_POSITION paramLIST_POSITION, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Response<Long> llen(byte[] paramArrayOfByte);
  
  public abstract Response<byte[]> lpop(byte[] paramArrayOfByte);
  
  public abstract Response<Long> lpush(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Long> lpushx(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<List<byte[]>> lrange(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Response<Long> lrem(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2);
  
  public abstract Response<String> lset(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2);
  
  public abstract Response<String> ltrim(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Response<Long> move(byte[] paramArrayOfByte, int paramInt);
  
  public abstract Response<Long> persist(byte[] paramArrayOfByte);
  
  public abstract Response<byte[]> rpop(byte[] paramArrayOfByte);
  
  public abstract Response<Long> rpush(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Long> rpushx(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Long> sadd(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Long> scard(byte[] paramArrayOfByte);
  
  public abstract Response<String> set(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Boolean> setbit(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2);
  
  public abstract Response<Long> setrange(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2);
  
  public abstract Response<String> setex(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2);
  
  public abstract Response<Long> setnx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Long> setrange(String paramString1, long paramLong, String paramString2);
  
  public abstract Response<Set<byte[]>> smembers(byte[] paramArrayOfByte);
  
  public abstract Response<Boolean> sismember(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<List<byte[]>> sort(byte[] paramArrayOfByte);
  
  public abstract Response<List<byte[]>> sort(byte[] paramArrayOfByte, SortingParams paramSortingParams);
  
  public abstract Response<byte[]> spop(byte[] paramArrayOfByte);
  
  public abstract Response<byte[]> srandmember(byte[] paramArrayOfByte);
  
  public abstract Response<Long> srem(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Long> strlen(byte[] paramArrayOfByte);
  
  public abstract Response<String> substr(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract Response<Long> ttl(byte[] paramArrayOfByte);
  
  public abstract Response<String> type(byte[] paramArrayOfByte);
  
  public abstract Response<Long> zadd(byte[] paramArrayOfByte1, double paramDouble, byte[] paramArrayOfByte2);
  
  public abstract Response<Long> zcard(byte[] paramArrayOfByte);
  
  public abstract Response<Long> zcount(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Response<Double> zincrby(byte[] paramArrayOfByte1, double paramDouble, byte[] paramArrayOfByte2);
  
  public abstract Response<Set<byte[]>> zrange(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Response<Set<byte[]>> zrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Response<Set<byte[]>> zrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Response<Set<byte[]>> zrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Response<Set<byte[]>> zrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2);
  
  public abstract Response<Set<Tuple>> zrangeByScoreWithScores(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Response<Set<Tuple>> zrangeByScoreWithScores(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Response<Set<Tuple>> zrangeByScoreWithScores(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Response<Set<Tuple>> zrangeByScoreWithScores(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2);
  
  public abstract Response<Set<byte[]>> zrevrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Response<Set<byte[]>> zrevrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Response<Set<byte[]>> zrevrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Response<Set<byte[]>> zrevrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2);
  
  public abstract Response<Set<Tuple>> zrevrangeByScoreWithScores(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Response<Set<Tuple>> zrevrangeByScoreWithScores(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Response<Set<Tuple>> zrevrangeByScoreWithScores(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Response<Set<Tuple>> zrevrangeByScoreWithScores(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2);
  
  public abstract Response<Set<Tuple>> zrangeWithScores(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Response<Long> zrank(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Long> zrem(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Response<Long> zremrangeByRank(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Response<Long> zremrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Response<Long> zremrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Response<Set<byte[]>> zrevrange(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Response<Set<Tuple>> zrevrangeWithScores(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Response<Long> zrevrank(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Double> zscore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Response<Long> bitcount(byte[] paramArrayOfByte);
  
  public abstract Response<Long> bitcount(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
}
