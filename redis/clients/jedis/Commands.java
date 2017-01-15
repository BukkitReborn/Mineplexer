package redis.clients.jedis;

import java.util.Map;

public abstract interface Commands
{
  public abstract void set(String paramString1, String paramString2);
  
  public abstract void set(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong);
  
  public abstract void get(String paramString);
  
  public abstract void exists(String paramString);
  
  public abstract void del(String... paramVarArgs);
  
  public abstract void type(String paramString);
  
  public abstract void keys(String paramString);
  
  public abstract void rename(String paramString1, String paramString2);
  
  public abstract void renamenx(String paramString1, String paramString2);
  
  public abstract void expire(String paramString, int paramInt);
  
  public abstract void expireAt(String paramString, long paramLong);
  
  public abstract void ttl(String paramString);
  
  public abstract void setbit(String paramString, long paramLong, boolean paramBoolean);
  
  public abstract void setbit(String paramString1, long paramLong, String paramString2);
  
  public abstract void getbit(String paramString, long paramLong);
  
  public abstract void setrange(String paramString1, long paramLong, String paramString2);
  
  public abstract void getrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract void move(String paramString, int paramInt);
  
  public abstract void getSet(String paramString1, String paramString2);
  
  public abstract void mget(String... paramVarArgs);
  
  public abstract void setnx(String paramString1, String paramString2);
  
  public abstract void setex(String paramString1, int paramInt, String paramString2);
  
  public abstract void mset(String... paramVarArgs);
  
  public abstract void msetnx(String... paramVarArgs);
  
  public abstract void decrBy(String paramString, long paramLong);
  
  public abstract void decr(String paramString);
  
  public abstract void incrBy(String paramString, long paramLong);
  
  public abstract void incr(String paramString);
  
  public abstract void append(String paramString1, String paramString2);
  
  public abstract void substr(String paramString, int paramInt1, int paramInt2);
  
  public abstract void hset(String paramString1, String paramString2, String paramString3);
  
  public abstract void hget(String paramString1, String paramString2);
  
  public abstract void hsetnx(String paramString1, String paramString2, String paramString3);
  
  public abstract void hmset(String paramString, Map<String, String> paramMap);
  
  public abstract void hmget(String paramString, String... paramVarArgs);
  
  public abstract void hincrBy(String paramString1, String paramString2, long paramLong);
  
  public abstract void hexists(String paramString1, String paramString2);
  
  public abstract void hdel(String paramString, String... paramVarArgs);
  
  public abstract void hlen(String paramString);
  
  public abstract void hkeys(String paramString);
  
  public abstract void hvals(String paramString);
  
  public abstract void hgetAll(String paramString);
  
  public abstract void rpush(String paramString, String... paramVarArgs);
  
  public abstract void lpush(String paramString, String... paramVarArgs);
  
  public abstract void llen(String paramString);
  
  public abstract void lrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract void ltrim(String paramString, long paramLong1, long paramLong2);
  
  public abstract void lindex(String paramString, long paramLong);
  
  public abstract void lset(String paramString1, long paramLong, String paramString2);
  
  public abstract void lrem(String paramString1, long paramLong, String paramString2);
  
  public abstract void lpop(String paramString);
  
  public abstract void rpop(String paramString);
  
  public abstract void rpoplpush(String paramString1, String paramString2);
  
  public abstract void sadd(String paramString, String... paramVarArgs);
  
  public abstract void smembers(String paramString);
  
  public abstract void srem(String paramString, String... paramVarArgs);
  
  public abstract void spop(String paramString);
  
  public abstract void smove(String paramString1, String paramString2, String paramString3);
  
  public abstract void scard(String paramString);
  
  public abstract void sismember(String paramString1, String paramString2);
  
  public abstract void sinter(String... paramVarArgs);
  
  public abstract void sinterstore(String paramString, String... paramVarArgs);
  
  public abstract void sunion(String... paramVarArgs);
  
  public abstract void sunionstore(String paramString, String... paramVarArgs);
  
  public abstract void sdiff(String... paramVarArgs);
  
  public abstract void sdiffstore(String paramString, String... paramVarArgs);
  
  public abstract void srandmember(String paramString);
  
  public abstract void zadd(String paramString1, double paramDouble, String paramString2);
  
  public abstract void zadd(String paramString, Map<String, Double> paramMap);
  
  public abstract void zrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract void zrem(String paramString, String... paramVarArgs);
  
  public abstract void zincrby(String paramString1, double paramDouble, String paramString2);
  
  public abstract void zrank(String paramString1, String paramString2);
  
  public abstract void zrevrank(String paramString1, String paramString2);
  
  public abstract void zrevrange(String paramString, long paramLong1, long paramLong2);
  
  public abstract void zrangeWithScores(String paramString, long paramLong1, long paramLong2);
  
  public abstract void zrevrangeWithScores(String paramString, long paramLong1, long paramLong2);
  
  public abstract void zcard(String paramString);
  
  public abstract void zscore(String paramString1, String paramString2);
  
  public abstract void watch(String... paramVarArgs);
  
  public abstract void sort(String paramString);
  
  public abstract void sort(String paramString, SortingParams paramSortingParams);
  
  public abstract void blpop(String[] paramArrayOfString);
  
  public abstract void sort(String paramString1, SortingParams paramSortingParams, String paramString2);
  
  public abstract void sort(String paramString1, String paramString2);
  
  public abstract void brpop(String[] paramArrayOfString);
  
  public abstract void brpoplpush(String paramString1, String paramString2, int paramInt);
  
  public abstract void zcount(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract void zcount(String paramString1, String paramString2, String paramString3);
  
  public abstract void zrangeByScore(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract void zrangeByScore(String paramString1, String paramString2, String paramString3);
  
  public abstract void zrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract void zrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract void zrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract void zrangeByScoreWithScores(String paramString1, String paramString2, String paramString3);
  
  public abstract void zrangeByScoreWithScores(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract void zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract void zrevrangeByScore(String paramString1, String paramString2, String paramString3);
  
  public abstract void zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract void zrevrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract void zrevrangeByScoreWithScores(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);
  
  public abstract void zrevrangeByScoreWithScores(String paramString1, String paramString2, String paramString3);
  
  public abstract void zrevrangeByScoreWithScores(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2);
  
  public abstract void zremrangeByRank(String paramString, long paramLong1, long paramLong2);
  
  public abstract void zremrangeByScore(String paramString, double paramDouble1, double paramDouble2);
  
  public abstract void zremrangeByScore(String paramString1, String paramString2, String paramString3);
  
  public abstract void zunionstore(String paramString, String... paramVarArgs);
  
  public abstract void zunionstore(String paramString, ZParams paramZParams, String... paramVarArgs);
  
  public abstract void zinterstore(String paramString, String... paramVarArgs);
  
  public abstract void zinterstore(String paramString, ZParams paramZParams, String... paramVarArgs);
  
  public abstract void strlen(String paramString);
  
  public abstract void lpushx(String paramString, String... paramVarArgs);
  
  public abstract void persist(String paramString);
  
  public abstract void rpushx(String paramString, String... paramVarArgs);
  
  public abstract void echo(String paramString);
  
  public abstract void linsert(String paramString1, BinaryClient.LIST_POSITION paramLIST_POSITION, String paramString2, String paramString3);
  
  public abstract void bgrewriteaof();
  
  public abstract void bgsave();
  
  public abstract void lastsave();
  
  public abstract void save();
  
  public abstract void configSet(String paramString1, String paramString2);
  
  public abstract void configGet(String paramString);
  
  public abstract void configResetStat();
  
  public abstract void multi();
  
  public abstract void exec();
  
  public abstract void discard();
  
  public abstract void objectRefcount(String paramString);
  
  public abstract void objectIdletime(String paramString);
  
  public abstract void objectEncoding(String paramString);
  
  public abstract void bitcount(String paramString);
  
  public abstract void bitcount(String paramString, long paramLong1, long paramLong2);
  
  public abstract void bitop(BitOP paramBitOP, String paramString, String... paramVarArgs);
  
  @Deprecated
  public abstract void scan(int paramInt, ScanParams paramScanParams);
  
  @Deprecated
  public abstract void hscan(String paramString, int paramInt, ScanParams paramScanParams);
  
  @Deprecated
  public abstract void sscan(String paramString, int paramInt, ScanParams paramScanParams);
  
  @Deprecated
  public abstract void zscan(String paramString, int paramInt, ScanParams paramScanParams);
  
  public abstract void scan(String paramString, ScanParams paramScanParams);
  
  public abstract void hscan(String paramString1, String paramString2, ScanParams paramScanParams);
  
  public abstract void sscan(String paramString1, String paramString2, ScanParams paramScanParams);
  
  public abstract void zscan(String paramString1, String paramString2, ScanParams paramScanParams);
  
  public abstract void waitReplicas(int paramInt, long paramLong);
}
