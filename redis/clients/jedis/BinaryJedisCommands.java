package redis.clients.jedis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface BinaryJedisCommands
{
  public abstract String set(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract byte[] get(byte[] paramArrayOfByte);
  
  public abstract Boolean exists(byte[] paramArrayOfByte);
  
  public abstract Long persist(byte[] paramArrayOfByte);
  
  public abstract String type(byte[] paramArrayOfByte);
  
  public abstract Long expire(byte[] paramArrayOfByte, int paramInt);
  
  public abstract Long expireAt(byte[] paramArrayOfByte, long paramLong);
  
  public abstract Long ttl(byte[] paramArrayOfByte);
  
  public abstract Boolean setbit(byte[] paramArrayOfByte, long paramLong, boolean paramBoolean);
  
  public abstract Boolean setbit(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2);
  
  public abstract Boolean getbit(byte[] paramArrayOfByte, long paramLong);
  
  public abstract Long setrange(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2);
  
  public abstract byte[] getrange(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract byte[] getSet(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Long setnx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract String setex(byte[] paramArrayOfByte1, int paramInt, byte[] paramArrayOfByte2);
  
  public abstract Long decrBy(byte[] paramArrayOfByte, long paramLong);
  
  public abstract Long decr(byte[] paramArrayOfByte);
  
  public abstract Long incrBy(byte[] paramArrayOfByte, long paramLong);
  
  public abstract Long incr(byte[] paramArrayOfByte);
  
  public abstract Long append(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract byte[] substr(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract Long hset(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract byte[] hget(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Long hsetnx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract String hmset(byte[] paramArrayOfByte, Map<byte[], byte[]> paramMap);
  
  public abstract List<byte[]> hmget(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Long hincrBy(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong);
  
  public abstract Boolean hexists(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Long hdel(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Long hlen(byte[] paramArrayOfByte);
  
  public abstract Set<byte[]> hkeys(byte[] paramArrayOfByte);
  
  public abstract Collection<byte[]> hvals(byte[] paramArrayOfByte);
  
  public abstract Map<byte[], byte[]> hgetAll(byte[] paramArrayOfByte);
  
  public abstract Long rpush(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Long lpush(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Long llen(byte[] paramArrayOfByte);
  
  public abstract List<byte[]> lrange(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract String ltrim(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract byte[] lindex(byte[] paramArrayOfByte, long paramLong);
  
  public abstract String lset(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2);
  
  public abstract Long lrem(byte[] paramArrayOfByte1, long paramLong, byte[] paramArrayOfByte2);
  
  public abstract byte[] lpop(byte[] paramArrayOfByte);
  
  public abstract byte[] rpop(byte[] paramArrayOfByte);
  
  public abstract Long sadd(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Set<byte[]> smembers(byte[] paramArrayOfByte);
  
  public abstract Long srem(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract byte[] spop(byte[] paramArrayOfByte);
  
  public abstract Long scard(byte[] paramArrayOfByte);
  
  public abstract Boolean sismember(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract byte[] srandmember(byte[] paramArrayOfByte);
  
  public abstract Long strlen(byte[] paramArrayOfByte);
  
  public abstract Long zadd(byte[] paramArrayOfByte1, double paramDouble, byte[] paramArrayOfByte2);
  
  public abstract Long zadd(byte[] paramArrayOfByte, Map<byte[], Double> paramMap);
  
  public abstract Set<byte[]> zrange(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Long zrem(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Double zincrby(byte[] paramArrayOfByte1, double paramDouble, byte[] paramArrayOfByte2);
  
  public abstract Long zrank(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Long zrevrank(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract Set<byte[]> zrevrange(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Set<Tuple> zrangeWithScores(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Set<Tuple> zrevrangeWithScores(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Long zcard(byte[] paramArrayOfByte);
  
  public abstract Double zscore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
  
  public abstract List<byte[]> sort(byte[] paramArrayOfByte);
  
  public abstract List<byte[]> sort(byte[] paramArrayOfByte, SortingParams paramSortingParams);
  
  public abstract Long zcount(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Long zcount(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Set<byte[]> zrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Set<byte[]> zrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Set<byte[]> zrevrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Set<byte[]> zrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Set<byte[]> zrevrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Set<byte[]> zrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2);
  
  public abstract Set<byte[]> zrevrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Set<Tuple> zrangeByScoreWithScores(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Set<Tuple> zrevrangeByScoreWithScores(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Set<Tuple> zrangeByScoreWithScores(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Set<byte[]> zrevrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2);
  
  public abstract Set<Tuple> zrangeByScoreWithScores(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Set<Tuple> zrevrangeByScoreWithScores(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Set<Tuple> zrangeByScoreWithScores(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2);
  
  public abstract Set<Tuple> zrevrangeByScoreWithScores(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract Set<Tuple> zrevrangeByScoreWithScores(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int paramInt1, int paramInt2);
  
  public abstract Long zremrangeByRank(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
  
  public abstract Long zremrangeByScore(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2);
  
  public abstract Long zremrangeByScore(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Long linsert(byte[] paramArrayOfByte1, BinaryClient.LIST_POSITION paramLIST_POSITION, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);
  
  public abstract Long lpushx(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract Long rpushx(byte[] paramArrayOfByte, byte[]... paramVarArgs);
  
  public abstract List<byte[]> blpop(byte[] paramArrayOfByte);
  
  public abstract List<byte[]> brpop(byte[] paramArrayOfByte);
  
  public abstract Long del(byte[] paramArrayOfByte);
  
  public abstract byte[] echo(byte[] paramArrayOfByte);
  
  public abstract Long move(byte[] paramArrayOfByte, int paramInt);
  
  public abstract Long bitcount(byte[] paramArrayOfByte);
  
  public abstract Long bitcount(byte[] paramArrayOfByte, long paramLong1, long paramLong2);
}
