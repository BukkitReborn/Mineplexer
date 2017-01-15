package redis.clients.jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import redis.clients.util.SafeEncoder;

public class BinaryClient
  extends Connection
{
  private boolean isInMulti;
  private String password;
  private long db;
  private boolean isInWatch;
  
  public static enum LIST_POSITION
  {
    BEFORE,  AFTER;
    
    public final byte[] raw;
    
    private LIST_POSITION()
    {
      this.raw = SafeEncoder.encode(name());
    }
  }
  
  public boolean isInMulti()
  {
    return this.isInMulti;
  }
  
  public boolean isInWatch()
  {
    return this.isInWatch;
  }
  
  public BinaryClient(String host)
  {
    super(host);
  }
  
  public BinaryClient(String host, int port)
  {
    super(host, port);
  }
  
  private byte[][] joinParameters(byte[] first, byte[][] rest)
  {
    byte[][] result = new byte[rest.length + 1][];
    result[0] = first;
    for (int i = 0; i < rest.length; i++) {
      result[(i + 1)] = rest[i];
    }
    return result;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public void connect()
  {
    if (!isConnected())
    {
      super.connect();
      if (this.password != null)
      {
        auth(this.password);
        getStatusCodeReply();
      }
      if (this.db > 0L)
      {
        select(Long.valueOf(this.db).intValue());
        getStatusCodeReply();
      }
    }
  }
  
  public void ping()
  {
    sendCommand(Protocol.Command.PING);
  }
  
  public void set(byte[] key, byte[] value)
  {
    sendCommand(Protocol.Command.SET, new byte[][] { key, value });
  }
  
  public void set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time)
  {
    sendCommand(Protocol.Command.SET, new byte[][] { key, value, nxxx, expx, Protocol.toByteArray(time) });
  }
  
  public void get(byte[] key)
  {
    sendCommand(Protocol.Command.GET, new byte[][] { key });
  }
  
  public void quit()
  {
    this.db = 0L;
    sendCommand(Protocol.Command.QUIT);
  }
  
  public void exists(byte[] key)
  {
    sendCommand(Protocol.Command.EXISTS, new byte[][] { key });
  }
  
  public void del(byte[]... keys)
  {
    sendCommand(Protocol.Command.DEL, keys);
  }
  
  public void type(byte[] key)
  {
    sendCommand(Protocol.Command.TYPE, new byte[][] { key });
  }
  
  public void flushDB()
  {
    sendCommand(Protocol.Command.FLUSHDB);
  }
  
  public void keys(byte[] pattern)
  {
    sendCommand(Protocol.Command.KEYS, new byte[][] { pattern });
  }
  
  public void randomKey()
  {
    sendCommand(Protocol.Command.RANDOMKEY);
  }
  
  public void rename(byte[] oldkey, byte[] newkey)
  {
    sendCommand(Protocol.Command.RENAME, new byte[][] { oldkey, newkey });
  }
  
  public void renamenx(byte[] oldkey, byte[] newkey)
  {
    sendCommand(Protocol.Command.RENAMENX, new byte[][] { oldkey, newkey });
  }
  
  public void dbSize()
  {
    sendCommand(Protocol.Command.DBSIZE);
  }
  
  public void expire(byte[] key, int seconds)
  {
    sendCommand(Protocol.Command.EXPIRE, new byte[][] { key, Protocol.toByteArray(seconds) });
  }
  
  public void expireAt(byte[] key, long unixTime)
  {
    sendCommand(Protocol.Command.EXPIREAT, new byte[][] { key, Protocol.toByteArray(unixTime) });
  }
  
  public void ttl(byte[] key)
  {
    sendCommand(Protocol.Command.TTL, new byte[][] { key });
  }
  
  public void select(int index)
  {
    this.db = index;
    sendCommand(Protocol.Command.SELECT, new byte[][] { Protocol.toByteArray(index) });
  }
  
  public void move(byte[] key, int dbIndex)
  {
    sendCommand(Protocol.Command.MOVE, new byte[][] { key, Protocol.toByteArray(dbIndex) });
  }
  
  public void flushAll()
  {
    sendCommand(Protocol.Command.FLUSHALL);
  }
  
  public void getSet(byte[] key, byte[] value)
  {
    sendCommand(Protocol.Command.GETSET, new byte[][] { key, value });
  }
  
  public void mget(byte[]... keys)
  {
    sendCommand(Protocol.Command.MGET, keys);
  }
  
  public void setnx(byte[] key, byte[] value)
  {
    sendCommand(Protocol.Command.SETNX, new byte[][] { key, value });
  }
  
  public void setex(byte[] key, int seconds, byte[] value)
  {
    sendCommand(Protocol.Command.SETEX, new byte[][] { key, Protocol.toByteArray(seconds), value });
  }
  
  public void mset(byte[]... keysvalues)
  {
    sendCommand(Protocol.Command.MSET, keysvalues);
  }
  
  public void msetnx(byte[]... keysvalues)
  {
    sendCommand(Protocol.Command.MSETNX, keysvalues);
  }
  
  public void decrBy(byte[] key, long integer)
  {
    sendCommand(Protocol.Command.DECRBY, new byte[][] { key, Protocol.toByteArray(integer) });
  }
  
  public void decr(byte[] key)
  {
    sendCommand(Protocol.Command.DECR, new byte[][] { key });
  }
  
  public void incrBy(byte[] key, long integer)
  {
    sendCommand(Protocol.Command.INCRBY, new byte[][] { key, Protocol.toByteArray(integer) });
  }
  
  public void incr(byte[] key)
  {
    sendCommand(Protocol.Command.INCR, new byte[][] { key });
  }
  
  public void append(byte[] key, byte[] value)
  {
    sendCommand(Protocol.Command.APPEND, new byte[][] { key, value });
  }
  
  public void substr(byte[] key, int start, int end)
  {
    sendCommand(Protocol.Command.SUBSTR, new byte[][] { key, Protocol.toByteArray(start), Protocol.toByteArray(end) });
  }
  
  public void hset(byte[] key, byte[] field, byte[] value)
  {
    sendCommand(Protocol.Command.HSET, new byte[][] { key, field, value });
  }
  
  public void hget(byte[] key, byte[] field)
  {
    sendCommand(Protocol.Command.HGET, new byte[][] { key, field });
  }
  
  public void hsetnx(byte[] key, byte[] field, byte[] value)
  {
    sendCommand(Protocol.Command.HSETNX, new byte[][] { key, field, value });
  }
  
  public void hmset(byte[] key, Map<byte[], byte[]> hash)
  {
    List<byte[]> params = new ArrayList();
    params.add(key);
    for (Map.Entry<byte[], byte[]> entry : hash.entrySet())
    {
      params.add(entry.getKey());
      params.add(entry.getValue());
    }
    sendCommand(Protocol.Command.HMSET, (byte[][])params.toArray(new byte[params.size()][]));
  }
  
  public void hmget(byte[] key, byte[]... fields)
  {
    byte[][] params = new byte[fields.length + 1][];
    params[0] = key;
    System.arraycopy(fields, 0, params, 1, fields.length);
    sendCommand(Protocol.Command.HMGET, params);
  }
  
  public void hincrBy(byte[] key, byte[] field, long value)
  {
    sendCommand(Protocol.Command.HINCRBY, new byte[][] { key, field, Protocol.toByteArray(value) });
  }
  
  public void hexists(byte[] key, byte[] field)
  {
    sendCommand(Protocol.Command.HEXISTS, new byte[][] { key, field });
  }
  
  public void hdel(byte[] key, byte[]... fields)
  {
    sendCommand(Protocol.Command.HDEL, joinParameters(key, fields));
  }
  
  public void hlen(byte[] key)
  {
    sendCommand(Protocol.Command.HLEN, new byte[][] { key });
  }
  
  public void hkeys(byte[] key)
  {
    sendCommand(Protocol.Command.HKEYS, new byte[][] { key });
  }
  
  public void hvals(byte[] key)
  {
    sendCommand(Protocol.Command.HVALS, new byte[][] { key });
  }
  
  public void hgetAll(byte[] key)
  {
    sendCommand(Protocol.Command.HGETALL, new byte[][] { key });
  }
  
  public void rpush(byte[] key, byte[]... strings)
  {
    sendCommand(Protocol.Command.RPUSH, joinParameters(key, strings));
  }
  
  public void lpush(byte[] key, byte[]... strings)
  {
    sendCommand(Protocol.Command.LPUSH, joinParameters(key, strings));
  }
  
  public void llen(byte[] key)
  {
    sendCommand(Protocol.Command.LLEN, new byte[][] { key });
  }
  
  public void lrange(byte[] key, long start, long end)
  {
    sendCommand(Protocol.Command.LRANGE, new byte[][] { key, Protocol.toByteArray(start), Protocol.toByteArray(end) });
  }
  
  public void ltrim(byte[] key, long start, long end)
  {
    sendCommand(Protocol.Command.LTRIM, new byte[][] { key, Protocol.toByteArray(start), Protocol.toByteArray(end) });
  }
  
  public void lindex(byte[] key, long index)
  {
    sendCommand(Protocol.Command.LINDEX, new byte[][] { key, Protocol.toByteArray(index) });
  }
  
  public void lset(byte[] key, long index, byte[] value)
  {
    sendCommand(Protocol.Command.LSET, new byte[][] { key, Protocol.toByteArray(index), value });
  }
  
  public void lrem(byte[] key, long count, byte[] value)
  {
    sendCommand(Protocol.Command.LREM, new byte[][] { key, Protocol.toByteArray(count), value });
  }
  
  public void lpop(byte[] key)
  {
    sendCommand(Protocol.Command.LPOP, new byte[][] { key });
  }
  
  public void rpop(byte[] key)
  {
    sendCommand(Protocol.Command.RPOP, new byte[][] { key });
  }
  
  public void rpoplpush(byte[] srckey, byte[] dstkey)
  {
    sendCommand(Protocol.Command.RPOPLPUSH, new byte[][] { srckey, dstkey });
  }
  
  public void sadd(byte[] key, byte[]... members)
  {
    sendCommand(Protocol.Command.SADD, joinParameters(key, members));
  }
  
  public void smembers(byte[] key)
  {
    sendCommand(Protocol.Command.SMEMBERS, new byte[][] { key });
  }
  
  public void srem(byte[] key, byte[]... members)
  {
    sendCommand(Protocol.Command.SREM, joinParameters(key, members));
  }
  
  public void spop(byte[] key)
  {
    sendCommand(Protocol.Command.SPOP, new byte[][] { key });
  }
  
  public void smove(byte[] srckey, byte[] dstkey, byte[] member)
  {
    sendCommand(Protocol.Command.SMOVE, new byte[][] { srckey, dstkey, member });
  }
  
  public void scard(byte[] key)
  {
    sendCommand(Protocol.Command.SCARD, new byte[][] { key });
  }
  
  public void sismember(byte[] key, byte[] member)
  {
    sendCommand(Protocol.Command.SISMEMBER, new byte[][] { key, member });
  }
  
  public void sinter(byte[]... keys)
  {
    sendCommand(Protocol.Command.SINTER, keys);
  }
  
  public void sinterstore(byte[] dstkey, byte[]... keys)
  {
    byte[][] params = new byte[keys.length + 1][];
    params[0] = dstkey;
    System.arraycopy(keys, 0, params, 1, keys.length);
    sendCommand(Protocol.Command.SINTERSTORE, params);
  }
  
  public void sunion(byte[]... keys)
  {
    sendCommand(Protocol.Command.SUNION, keys);
  }
  
  public void sunionstore(byte[] dstkey, byte[]... keys)
  {
    byte[][] params = new byte[keys.length + 1][];
    params[0] = dstkey;
    System.arraycopy(keys, 0, params, 1, keys.length);
    sendCommand(Protocol.Command.SUNIONSTORE, params);
  }
  
  public void sdiff(byte[]... keys)
  {
    sendCommand(Protocol.Command.SDIFF, keys);
  }
  
  public void sdiffstore(byte[] dstkey, byte[]... keys)
  {
    byte[][] params = new byte[keys.length + 1][];
    params[0] = dstkey;
    System.arraycopy(keys, 0, params, 1, keys.length);
    sendCommand(Protocol.Command.SDIFFSTORE, params);
  }
  
  public void srandmember(byte[] key)
  {
    sendCommand(Protocol.Command.SRANDMEMBER, new byte[][] { key });
  }
  
  public void zadd(byte[] key, double score, byte[] member)
  {
    sendCommand(Protocol.Command.ZADD, new byte[][] { key, Protocol.toByteArray(score), member });
  }
  
  public void zaddBinary(byte[] key, Map<byte[], Double> scoreMembers)
  {
    ArrayList<byte[]> args = new ArrayList(scoreMembers.size() * 2 + 1);
    
    args.add(key);
    for (Map.Entry<byte[], Double> entry : scoreMembers.entrySet())
    {
      args.add(Protocol.toByteArray(((Double)entry.getValue()).doubleValue()));
      args.add(entry.getKey());
    }
    byte[][] argsArray = new byte[args.size()][];
    args.toArray(argsArray);
    
    sendCommand(Protocol.Command.ZADD, argsArray);
  }
  
  public void zrange(byte[] key, long start, long end)
  {
    sendCommand(Protocol.Command.ZRANGE, new byte[][] { key, Protocol.toByteArray(start), Protocol.toByteArray(end) });
  }
  
  public void zrem(byte[] key, byte[]... members)
  {
    sendCommand(Protocol.Command.ZREM, joinParameters(key, members));
  }
  
  public void zincrby(byte[] key, double score, byte[] member)
  {
    sendCommand(Protocol.Command.ZINCRBY, new byte[][] { key, Protocol.toByteArray(score), member });
  }
  
  public void zrank(byte[] key, byte[] member)
  {
    sendCommand(Protocol.Command.ZRANK, new byte[][] { key, member });
  }
  
  public void zrevrank(byte[] key, byte[] member)
  {
    sendCommand(Protocol.Command.ZREVRANK, new byte[][] { key, member });
  }
  
  public void zrevrange(byte[] key, long start, long end)
  {
    sendCommand(Protocol.Command.ZREVRANGE, new byte[][] { key, Protocol.toByteArray(start), Protocol.toByteArray(end) });
  }
  
  public void zrangeWithScores(byte[] key, long start, long end)
  {
    sendCommand(Protocol.Command.ZRANGE, new byte[][] { key, Protocol.toByteArray(start), Protocol.toByteArray(end), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrevrangeWithScores(byte[] key, long start, long end)
  {
    sendCommand(Protocol.Command.ZREVRANGE, new byte[][] { key, Protocol.toByteArray(start), Protocol.toByteArray(end), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zcard(byte[] key)
  {
    sendCommand(Protocol.Command.ZCARD, new byte[][] { key });
  }
  
  public void zscore(byte[] key, byte[] member)
  {
    sendCommand(Protocol.Command.ZSCORE, new byte[][] { key, member });
  }
  
  public void multi()
  {
    sendCommand(Protocol.Command.MULTI);
    this.isInMulti = true;
  }
  
  public void discard()
  {
    sendCommand(Protocol.Command.DISCARD);
    this.isInMulti = false;
    this.isInWatch = false;
  }
  
  public void exec()
  {
    sendCommand(Protocol.Command.EXEC);
    this.isInMulti = false;
    this.isInWatch = false;
  }
  
  public void watch(byte[]... keys)
  {
    sendCommand(Protocol.Command.WATCH, keys);
    this.isInWatch = true;
  }
  
  public void unwatch()
  {
    sendCommand(Protocol.Command.UNWATCH);
    this.isInWatch = false;
  }
  
  public void sort(byte[] key)
  {
    sendCommand(Protocol.Command.SORT, new byte[][] { key });
  }
  
  public void sort(byte[] key, SortingParams sortingParameters)
  {
    List<byte[]> args = new ArrayList();
    args.add(key);
    args.addAll(sortingParameters.getParams());
    sendCommand(Protocol.Command.SORT, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void blpop(byte[][] args)
  {
    sendCommand(Protocol.Command.BLPOP, args);
  }
  
  public void blpop(int timeout, byte[]... keys)
  {
    List<byte[]> args = new ArrayList();
    for (byte[] arg : keys) {
      args.add(arg);
    }
    args.add(Protocol.toByteArray(timeout));
    blpop((byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void sort(byte[] key, SortingParams sortingParameters, byte[] dstkey)
  {
    List<byte[]> args = new ArrayList();
    args.add(key);
    args.addAll(sortingParameters.getParams());
    args.add(Protocol.Keyword.STORE.raw);
    args.add(dstkey);
    sendCommand(Protocol.Command.SORT, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void sort(byte[] key, byte[] dstkey)
  {
    sendCommand(Protocol.Command.SORT, new byte[][] { key, Protocol.Keyword.STORE.raw, dstkey });
  }
  
  public void brpop(byte[][] args)
  {
    sendCommand(Protocol.Command.BRPOP, args);
  }
  
  public void brpop(int timeout, byte[]... keys)
  {
    List<byte[]> args = new ArrayList();
    for (byte[] arg : keys) {
      args.add(arg);
    }
    args.add(Protocol.toByteArray(timeout));
    brpop((byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void auth(String password)
  {
    setPassword(password);
    sendCommand(Protocol.Command.AUTH, new String[] { password });
  }
  
  public void subscribe(byte[]... channels)
  {
    sendCommand(Protocol.Command.SUBSCRIBE, channels);
  }
  
  public void publish(byte[] channel, byte[] message)
  {
    sendCommand(Protocol.Command.PUBLISH, new byte[][] { channel, message });
  }
  
  public void unsubscribe()
  {
    sendCommand(Protocol.Command.UNSUBSCRIBE);
  }
  
  public void unsubscribe(byte[]... channels)
  {
    sendCommand(Protocol.Command.UNSUBSCRIBE, channels);
  }
  
  public void psubscribe(byte[]... patterns)
  {
    sendCommand(Protocol.Command.PSUBSCRIBE, patterns);
  }
  
  public void punsubscribe()
  {
    sendCommand(Protocol.Command.PUNSUBSCRIBE);
  }
  
  public void punsubscribe(byte[]... patterns)
  {
    sendCommand(Protocol.Command.PUNSUBSCRIBE, patterns);
  }
  
  public void pubsub(byte[]... args)
  {
    sendCommand(Protocol.Command.PUBSUB, args);
  }
  
  public void zcount(byte[] key, double min, double max)
  {
    byte[] byteArrayMin = min == Double.NEGATIVE_INFINITY ? "-inf".getBytes() : Protocol.toByteArray(min);
    
    byte[] byteArrayMax = max == Double.POSITIVE_INFINITY ? "+inf".getBytes() : Protocol.toByteArray(max);
    
    sendCommand(Protocol.Command.ZCOUNT, new byte[][] { key, byteArrayMin, byteArrayMax });
  }
  
  public void zcount(byte[] key, byte[] min, byte[] max)
  {
    sendCommand(Protocol.Command.ZCOUNT, new byte[][] { key, min, max });
  }
  
  public void zcount(byte[] key, String min, String max)
  {
    sendCommand(Protocol.Command.ZCOUNT, new byte[][] { key, min.getBytes(), max.getBytes() });
  }
  
  public void zrangeByScore(byte[] key, double min, double max)
  {
    byte[] byteArrayMin = min == Double.NEGATIVE_INFINITY ? "-inf".getBytes() : Protocol.toByteArray(min);
    
    byte[] byteArrayMax = max == Double.POSITIVE_INFINITY ? "+inf".getBytes() : Protocol.toByteArray(max);
    
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, byteArrayMin, byteArrayMax });
  }
  
  public void zrangeByScore(byte[] key, byte[] min, byte[] max)
  {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, min, max });
  }
  
  public void zrangeByScore(byte[] key, String min, String max)
  {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, min.getBytes(), max.getBytes() });
  }
  
  public void zrevrangeByScore(byte[] key, double max, double min)
  {
    byte[] byteArrayMin = min == Double.NEGATIVE_INFINITY ? "-inf".getBytes() : Protocol.toByteArray(min);
    
    byte[] byteArrayMax = max == Double.POSITIVE_INFINITY ? "+inf".getBytes() : Protocol.toByteArray(max);
    
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, byteArrayMax, byteArrayMin });
  }
  
  public void zrevrangeByScore(byte[] key, byte[] max, byte[] min)
  {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, max, min });
  }
  
  public void zrevrangeByScore(byte[] key, String max, String min)
  {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, max.getBytes(), min.getBytes() });
  }
  
  public void zrangeByScore(byte[] key, double min, double max, int offset, int count)
  {
    byte[] byteArrayMin = min == Double.NEGATIVE_INFINITY ? "-inf".getBytes() : Protocol.toByteArray(min);
    
    byte[] byteArrayMax = max == Double.POSITIVE_INFINITY ? "+inf".getBytes() : Protocol.toByteArray(max);
    
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, byteArrayMin, byteArrayMax, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count) });
  }
  
  public void zrangeByScore(byte[] key, String min, String max, int offset, int count)
  {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, min.getBytes(), max.getBytes(), Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count) });
  }
  
  public void zrevrangeByScore(byte[] key, double max, double min, int offset, int count)
  {
    byte[] byteArrayMin = min == Double.NEGATIVE_INFINITY ? "-inf".getBytes() : Protocol.toByteArray(min);
    
    byte[] byteArrayMax = max == Double.POSITIVE_INFINITY ? "+inf".getBytes() : Protocol.toByteArray(max);
    
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, byteArrayMax, byteArrayMin, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count) });
  }
  
  public void zrevrangeByScore(byte[] key, String max, String min, int offset, int count)
  {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, max.getBytes(), min.getBytes(), Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count) });
  }
  
  public void zrangeByScoreWithScores(byte[] key, double min, double max)
  {
    byte[] byteArrayMin = min == Double.NEGATIVE_INFINITY ? "-inf".getBytes() : Protocol.toByteArray(min);
    
    byte[] byteArrayMax = max == Double.POSITIVE_INFINITY ? "+inf".getBytes() : Protocol.toByteArray(max);
    
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, byteArrayMin, byteArrayMax, Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrangeByScoreWithScores(byte[] key, String min, String max)
  {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, min.getBytes(), max.getBytes(), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrevrangeByScoreWithScores(byte[] key, double max, double min)
  {
    byte[] byteArrayMin = min == Double.NEGATIVE_INFINITY ? "-inf".getBytes() : Protocol.toByteArray(min);
    
    byte[] byteArrayMax = max == Double.POSITIVE_INFINITY ? "+inf".getBytes() : Protocol.toByteArray(max);
    
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, byteArrayMax, byteArrayMin, Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrevrangeByScoreWithScores(byte[] key, String max, String min)
  {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, max.getBytes(), min.getBytes(), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count)
  {
    byte[] byteArrayMin = min == Double.NEGATIVE_INFINITY ? "-inf".getBytes() : Protocol.toByteArray(min);
    
    byte[] byteArrayMax = max == Double.POSITIVE_INFINITY ? "+inf".getBytes() : Protocol.toByteArray(max);
    
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, byteArrayMin, byteArrayMax, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrangeByScoreWithScores(byte[] key, String min, String max, int offset, int count)
  {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, min.getBytes(), max.getBytes(), Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count)
  {
    byte[] byteArrayMin = min == Double.NEGATIVE_INFINITY ? "-inf".getBytes() : Protocol.toByteArray(min);
    
    byte[] byteArrayMax = max == Double.POSITIVE_INFINITY ? "+inf".getBytes() : Protocol.toByteArray(max);
    
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, byteArrayMax, byteArrayMin, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrevrangeByScoreWithScores(byte[] key, String max, String min, int offset, int count)
  {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, max.getBytes(), min.getBytes(), Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count)
  {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, min, max, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count) });
  }
  
  public void zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count)
  {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, max, min, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count) });
  }
  
  public void zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max)
  {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, min, max, Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min)
  {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, max, min, Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count)
  {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, new byte[][] { key, min, max, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count)
  {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, new byte[][] { key, max, min, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw });
  }
  
  public void zremrangeByRank(byte[] key, long start, long end)
  {
    sendCommand(Protocol.Command.ZREMRANGEBYRANK, new byte[][] { key, Protocol.toByteArray(start), Protocol.toByteArray(end) });
  }
  
  public void zremrangeByScore(byte[] key, byte[] start, byte[] end)
  {
    sendCommand(Protocol.Command.ZREMRANGEBYSCORE, new byte[][] { key, start, end });
  }
  
  public void zremrangeByScore(byte[] key, String start, String end)
  {
    sendCommand(Protocol.Command.ZREMRANGEBYSCORE, new byte[][] { key, start.getBytes(), end.getBytes() });
  }
  
  public void zunionstore(byte[] dstkey, byte[]... sets)
  {
    byte[][] params = new byte[sets.length + 2][];
    params[0] = dstkey;
    params[1] = Protocol.toByteArray(sets.length);
    System.arraycopy(sets, 0, params, 2, sets.length);
    sendCommand(Protocol.Command.ZUNIONSTORE, params);
  }
  
  public void zunionstore(byte[] dstkey, ZParams params, byte[]... sets)
  {
    List<byte[]> args = new ArrayList();
    args.add(dstkey);
    args.add(Protocol.toByteArray(sets.length));
    for (byte[] set : sets) {
      args.add(set);
    }
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.ZUNIONSTORE, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void zinterstore(byte[] dstkey, byte[]... sets)
  {
    byte[][] params = new byte[sets.length + 2][];
    params[0] = dstkey;
    params[1] = Protocol.toByteArray(sets.length);
    System.arraycopy(sets, 0, params, 2, sets.length);
    sendCommand(Protocol.Command.ZINTERSTORE, params);
  }
  
  public void zinterstore(byte[] dstkey, ZParams params, byte[]... sets)
  {
    List<byte[]> args = new ArrayList();
    args.add(dstkey);
    args.add(Protocol.toByteArray(sets.length));
    for (byte[] set : sets) {
      args.add(set);
    }
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.ZINTERSTORE, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void save()
  {
    sendCommand(Protocol.Command.SAVE);
  }
  
  public void bgsave()
  {
    sendCommand(Protocol.Command.BGSAVE);
  }
  
  public void bgrewriteaof()
  {
    sendCommand(Protocol.Command.BGREWRITEAOF);
  }
  
  public void lastsave()
  {
    sendCommand(Protocol.Command.LASTSAVE);
  }
  
  public void shutdown()
  {
    sendCommand(Protocol.Command.SHUTDOWN);
  }
  
  public void info()
  {
    sendCommand(Protocol.Command.INFO);
  }
  
  public void info(String section)
  {
    sendCommand(Protocol.Command.INFO, new String[] { section });
  }
  
  public void monitor()
  {
    sendCommand(Protocol.Command.MONITOR);
  }
  
  public void slaveof(String host, int port)
  {
    sendCommand(Protocol.Command.SLAVEOF, new String[] { host, String.valueOf(port) });
  }
  
  public void slaveofNoOne()
  {
    sendCommand(Protocol.Command.SLAVEOF, new byte[][] { Protocol.Keyword.NO.raw, Protocol.Keyword.ONE.raw });
  }
  
  public void configGet(byte[] pattern)
  {
    sendCommand(Protocol.Command.CONFIG, new byte[][] { Protocol.Keyword.GET.raw, pattern });
  }
  
  public void configSet(byte[] parameter, byte[] value)
  {
    sendCommand(Protocol.Command.CONFIG, new byte[][] { Protocol.Keyword.SET.raw, parameter, value });
  }
  
  public void strlen(byte[] key)
  {
    sendCommand(Protocol.Command.STRLEN, new byte[][] { key });
  }
  
  public void sync()
  {
    sendCommand(Protocol.Command.SYNC);
  }
  
  public void lpushx(byte[] key, byte[]... string)
  {
    sendCommand(Protocol.Command.LPUSHX, joinParameters(key, string));
  }
  
  public void persist(byte[] key)
  {
    sendCommand(Protocol.Command.PERSIST, new byte[][] { key });
  }
  
  public void rpushx(byte[] key, byte[]... string)
  {
    sendCommand(Protocol.Command.RPUSHX, joinParameters(key, string));
  }
  
  public void echo(byte[] string)
  {
    sendCommand(Protocol.Command.ECHO, new byte[][] { string });
  }
  
  public void linsert(byte[] key, LIST_POSITION where, byte[] pivot, byte[] value)
  {
    sendCommand(Protocol.Command.LINSERT, new byte[][] { key, where.raw, pivot, value });
  }
  
  public void debug(DebugParams params)
  {
    sendCommand(Protocol.Command.DEBUG, params.getCommand());
  }
  
  public void brpoplpush(byte[] source, byte[] destination, int timeout)
  {
    sendCommand(Protocol.Command.BRPOPLPUSH, new byte[][] { source, destination, Protocol.toByteArray(timeout) });
  }
  
  public void configResetStat()
  {
    sendCommand(Protocol.Command.CONFIG, new String[] { Protocol.Keyword.RESETSTAT.name() });
  }
  
  public void setbit(byte[] key, long offset, byte[] value)
  {
    sendCommand(Protocol.Command.SETBIT, new byte[][] { key, Protocol.toByteArray(offset), value });
  }
  
  public void setbit(byte[] key, long offset, boolean value)
  {
    sendCommand(Protocol.Command.SETBIT, new byte[][] { key, Protocol.toByteArray(offset), Protocol.toByteArray(value) });
  }
  
  public void getbit(byte[] key, long offset)
  {
    sendCommand(Protocol.Command.GETBIT, new byte[][] { key, Protocol.toByteArray(offset) });
  }
  
  public void setrange(byte[] key, long offset, byte[] value)
  {
    sendCommand(Protocol.Command.SETRANGE, new byte[][] { key, Protocol.toByteArray(offset), value });
  }
  
  public void getrange(byte[] key, long startOffset, long endOffset)
  {
    sendCommand(Protocol.Command.GETRANGE, new byte[][] { key, Protocol.toByteArray(startOffset), Protocol.toByteArray(endOffset) });
  }
  
  public Long getDB()
  {
    return Long.valueOf(this.db);
  }
  
  public void disconnect()
  {
    this.db = 0L;
    super.disconnect();
  }
  
  public void close()
  {
    this.db = 0L;
    super.close();
  }
  
  public void resetState()
  {
    if (isInMulti()) {
      discard();
    }
    if (isInWatch()) {
      unwatch();
    }
  }
  
  private void sendEvalCommand(Protocol.Command command, byte[] script, byte[] keyCount, byte[][] params)
  {
    byte[][] allArgs = new byte[params.length + 2][];
    
    allArgs[0] = script;
    allArgs[1] = keyCount;
    for (int i = 0; i < params.length; i++) {
      allArgs[(i + 2)] = params[i];
    }
    sendCommand(command, allArgs);
  }
  
  public void eval(byte[] script, byte[] keyCount, byte[][] params)
  {
    sendEvalCommand(Protocol.Command.EVAL, script, keyCount, params);
  }
  
  public void eval(byte[] script, int keyCount, byte[]... params)
  {
    eval(script, Protocol.toByteArray(keyCount), params);
  }
  
  public void evalsha(byte[] sha1, byte[] keyCount, byte[]... params)
  {
    sendEvalCommand(Protocol.Command.EVALSHA, sha1, keyCount, params);
  }
  
  public void evalsha(byte[] sha1, int keyCount, byte[]... params)
  {
    sendEvalCommand(Protocol.Command.EVALSHA, sha1, Protocol.toByteArray(keyCount), params);
  }
  
  public void scriptFlush()
  {
    sendCommand(Protocol.Command.SCRIPT, new byte[][] { Protocol.Keyword.FLUSH.raw });
  }
  
  public void scriptExists(byte[]... sha1)
  {
    byte[][] args = new byte[sha1.length + 1][];
    args[0] = Protocol.Keyword.EXISTS.raw;
    for (int i = 0; i < sha1.length; i++) {
      args[(i + 1)] = sha1[i];
    }
    sendCommand(Protocol.Command.SCRIPT, args);
  }
  
  public void scriptLoad(byte[] script)
  {
    sendCommand(Protocol.Command.SCRIPT, new byte[][] { Protocol.Keyword.LOAD.raw, script });
  }
  
  public void scriptKill()
  {
    sendCommand(Protocol.Command.SCRIPT, new byte[][] { Protocol.Keyword.KILL.raw });
  }
  
  public void slowlogGet()
  {
    sendCommand(Protocol.Command.SLOWLOG, new byte[][] { Protocol.Keyword.GET.raw });
  }
  
  public void slowlogGet(long entries)
  {
    sendCommand(Protocol.Command.SLOWLOG, new byte[][] { Protocol.Keyword.GET.raw, Protocol.toByteArray(entries) });
  }
  
  public void slowlogReset()
  {
    sendCommand(Protocol.Command.SLOWLOG, new byte[][] { Protocol.Keyword.RESET.raw });
  }
  
  public void slowlogLen()
  {
    sendCommand(Protocol.Command.SLOWLOG, new byte[][] { Protocol.Keyword.LEN.raw });
  }
  
  public void objectRefcount(byte[] key)
  {
    sendCommand(Protocol.Command.OBJECT, new byte[][] { Protocol.Keyword.REFCOUNT.raw, key });
  }
  
  public void objectIdletime(byte[] key)
  {
    sendCommand(Protocol.Command.OBJECT, new byte[][] { Protocol.Keyword.IDLETIME.raw, key });
  }
  
  public void objectEncoding(byte[] key)
  {
    sendCommand(Protocol.Command.OBJECT, new byte[][] { Protocol.Keyword.ENCODING.raw, key });
  }
  
  public void bitcount(byte[] key)
  {
    sendCommand(Protocol.Command.BITCOUNT, new byte[][] { key });
  }
  
  public void bitcount(byte[] key, long start, long end)
  {
    sendCommand(Protocol.Command.BITCOUNT, new byte[][] { key, Protocol.toByteArray(start), Protocol.toByteArray(end) });
  }
  
  public void bitop(BitOP op, byte[] destKey, byte[]... srcKeys)
  {
    Protocol.Keyword kw = Protocol.Keyword.AND;
    int len = srcKeys.length;
    switch (op)
    {
    case AND: 
      kw = Protocol.Keyword.AND;
      break;
    case OR: 
      kw = Protocol.Keyword.OR;
      break;
    case XOR: 
      kw = Protocol.Keyword.XOR;
      break;
    case NOT: 
      kw = Protocol.Keyword.NOT;
      len = Math.min(1, len);
    }
    byte[][] bargs = new byte[len + 2][];
    bargs[0] = kw.raw;
    bargs[1] = destKey;
    for (int i = 0; i < len; i++) {
      bargs[(i + 2)] = srcKeys[i];
    }
    sendCommand(Protocol.Command.BITOP, bargs);
  }
  
  public void sentinel(byte[]... args)
  {
    sendCommand(Protocol.Command.SENTINEL, args);
  }
  
  public void dump(byte[] key)
  {
    sendCommand(Protocol.Command.DUMP, new byte[][] { key });
  }
  
  public void restore(byte[] key, int ttl, byte[] serializedValue)
  {
    sendCommand(Protocol.Command.RESTORE, new byte[][] { key, Protocol.toByteArray(ttl), serializedValue });
  }
  
  public void pexpire(byte[] key, int milliseconds)
  {
    sendCommand(Protocol.Command.PEXPIRE, new byte[][] { key, Protocol.toByteArray(milliseconds) });
  }
  
  public void pexpireAt(byte[] key, long millisecondsTimestamp)
  {
    sendCommand(Protocol.Command.PEXPIREAT, new byte[][] { key, Protocol.toByteArray(millisecondsTimestamp) });
  }
  
  public void pttl(byte[] key)
  {
    sendCommand(Protocol.Command.PTTL, new byte[][] { key });
  }
  
  public void incrByFloat(byte[] key, double increment)
  {
    sendCommand(Protocol.Command.INCRBYFLOAT, new byte[][] { key, Protocol.toByteArray(increment) });
  }
  
  public void psetex(byte[] key, int milliseconds, byte[] value)
  {
    sendCommand(Protocol.Command.PSETEX, new byte[][] { key, Protocol.toByteArray(milliseconds), value });
  }
  
  public void set(byte[] key, byte[] value, byte[] nxxx)
  {
    sendCommand(Protocol.Command.SET, new byte[][] { key, value, nxxx });
  }
  
  public void set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, int time)
  {
    sendCommand(Protocol.Command.SET, new byte[][] { key, value, nxxx, expx, Protocol.toByteArray(time) });
  }
  
  public void srandmember(byte[] key, int count)
  {
    sendCommand(Protocol.Command.SRANDMEMBER, new byte[][] { key, Protocol.toByteArray(count) });
  }
  
  public void clientKill(byte[] client)
  {
    sendCommand(Protocol.Command.CLIENT, new byte[][] { Protocol.Keyword.KILL.raw, client });
  }
  
  public void clientGetname()
  {
    sendCommand(Protocol.Command.CLIENT, new byte[][] { Protocol.Keyword.GETNAME.raw });
  }
  
  public void clientList()
  {
    sendCommand(Protocol.Command.CLIENT, new byte[][] { Protocol.Keyword.LIST.raw });
  }
  
  public void clientSetname(byte[] name)
  {
    sendCommand(Protocol.Command.CLIENT, new byte[][] { Protocol.Keyword.SETNAME.raw, name });
  }
  
  public void time()
  {
    sendCommand(Protocol.Command.TIME);
  }
  
  public void migrate(byte[] host, int port, byte[] key, int destinationDb, int timeout)
  {
    sendCommand(Protocol.Command.MIGRATE, new byte[][] { host, Protocol.toByteArray(port), key, Protocol.toByteArray(destinationDb), Protocol.toByteArray(timeout) });
  }
  
  public void hincrByFloat(byte[] key, byte[] field, double increment)
  {
    sendCommand(Protocol.Command.HINCRBYFLOAT, new byte[][] { key, field, Protocol.toByteArray(increment) });
  }
  
  @Deprecated
  public void scan(int cursor, ScanParams params)
  {
    List<byte[]> args = new ArrayList();
    args.add(Protocol.toByteArray(cursor));
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.SCAN, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  @Deprecated
  public void hscan(byte[] key, int cursor, ScanParams params)
  {
    List<byte[]> args = new ArrayList();
    args.add(key);
    args.add(Protocol.toByteArray(cursor));
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.HSCAN, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  @Deprecated
  public void sscan(byte[] key, int cursor, ScanParams params)
  {
    List<byte[]> args = new ArrayList();
    args.add(key);
    args.add(Protocol.toByteArray(cursor));
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.SSCAN, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  @Deprecated
  public void zscan(byte[] key, int cursor, ScanParams params)
  {
    List<byte[]> args = new ArrayList();
    args.add(key);
    args.add(Protocol.toByteArray(cursor));
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.ZSCAN, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void scan(byte[] cursor, ScanParams params)
  {
    List<byte[]> args = new ArrayList();
    args.add(cursor);
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.SCAN, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void hscan(byte[] key, byte[] cursor, ScanParams params)
  {
    List<byte[]> args = new ArrayList();
    args.add(key);
    args.add(cursor);
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.HSCAN, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void sscan(byte[] key, byte[] cursor, ScanParams params)
  {
    List<byte[]> args = new ArrayList();
    args.add(key);
    args.add(cursor);
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.SSCAN, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void zscan(byte[] key, byte[] cursor, ScanParams params)
  {
    List<byte[]> args = new ArrayList();
    args.add(key);
    args.add(cursor);
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.ZSCAN, (byte[][])args.toArray(new byte[args.size()][]));
  }
  
  public void waitReplicas(int replicas, long timeout)
  {
    sendCommand(Protocol.Command.WAIT, new byte[][] { Protocol.toByteArray(replicas), Protocol.toByteArray(timeout) });
  }
  
  public void cluster(byte[]... args)
  {
    sendCommand(Protocol.Command.CLUSTER, args);
  }
  
  public void asking()
  {
    sendCommand(Protocol.Command.ASKING);
  }
}
