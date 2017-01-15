package redis.clients.jedis;

import java.io.Closeable;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.JedisByteHashMap;
import redis.clients.util.SafeEncoder;

public class BinaryJedis
  implements BasicCommands, BinaryJedisCommands, MultiKeyBinaryCommands, AdvancedBinaryJedisCommands, BinaryScriptingCommands, Closeable
{
  protected Client client = null;
  
  public BinaryJedis(String host)
  {
    URI uri = URI.create(host);
    if ((uri.getScheme() != null) && (uri.getScheme().equals("redis")))
    {
      this.client = new Client(uri.getHost(), uri.getPort());
      this.client.auth(uri.getUserInfo().split(":", 2)[1]);
      this.client.getStatusCodeReply();
      this.client.select(Integer.parseInt(uri.getPath().split("/", 2)[1]));
      this.client.getStatusCodeReply();
    }
    else
    {
      this.client = new Client(host);
    }
  }
  
  public BinaryJedis(String host, int port)
  {
    this.client = new Client(host, port);
  }
  
  public BinaryJedis(String host, int port, int timeout)
  {
    this.client = new Client(host, port);
    this.client.setTimeout(timeout);
  }
  
  public BinaryJedis(JedisShardInfo shardInfo)
  {
    this.client = new Client(shardInfo.getHost(), shardInfo.getPort());
    this.client.setTimeout(shardInfo.getTimeout());
    this.client.setPassword(shardInfo.getPassword());
  }
  
  public BinaryJedis(URI uri)
  {
    this.client = new Client(uri.getHost(), uri.getPort());
    this.client.auth(uri.getUserInfo().split(":", 2)[1]);
    this.client.getStatusCodeReply();
    this.client.select(Integer.parseInt(uri.getPath().split("/", 2)[1]));
    this.client.getStatusCodeReply();
  }
  
  public String ping()
  {
    checkIsInMulti();
    this.client.ping();
    return this.client.getStatusCodeReply();
  }
  
  public String set(byte[] key, byte[] value)
  {
    checkIsInMulti();
    this.client.set(key, value);
    return this.client.getStatusCodeReply();
  }
  
  public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, long time)
  {
    checkIsInMulti();
    this.client.set(key, value, nxxx, expx, time);
    return this.client.getStatusCodeReply();
  }
  
  public byte[] get(byte[] key)
  {
    checkIsInMulti();
    this.client.get(key);
    return this.client.getBinaryBulkReply();
  }
  
  public String quit()
  {
    checkIsInMulti();
    this.client.quit();
    return this.client.getStatusCodeReply();
  }
  
  public Boolean exists(byte[] key)
  {
    checkIsInMulti();
    this.client.exists(key);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Long del(byte[]... keys)
  {
    checkIsInMulti();
    this.client.del(keys);
    return this.client.getIntegerReply();
  }
  
  public Long del(byte[] key)
  {
    checkIsInMulti();
    this.client.del(new byte[][] { key });
    return this.client.getIntegerReply();
  }
  
  public String type(byte[] key)
  {
    checkIsInMulti();
    this.client.type(key);
    return this.client.getStatusCodeReply();
  }
  
  public String flushDB()
  {
    checkIsInMulti();
    this.client.flushDB();
    return this.client.getStatusCodeReply();
  }
  
  public Set<byte[]> keys(byte[] pattern)
  {
    checkIsInMulti();
    this.client.keys(pattern);
    HashSet<byte[]> keySet = new HashSet(this.client.getBinaryMultiBulkReply());
    
    return keySet;
  }
  
  public byte[] randomBinaryKey()
  {
    checkIsInMulti();
    this.client.randomKey();
    return this.client.getBinaryBulkReply();
  }
  
  public String rename(byte[] oldkey, byte[] newkey)
  {
    checkIsInMulti();
    this.client.rename(oldkey, newkey);
    return this.client.getStatusCodeReply();
  }
  
  public Long renamenx(byte[] oldkey, byte[] newkey)
  {
    checkIsInMulti();
    this.client.renamenx(oldkey, newkey);
    return this.client.getIntegerReply();
  }
  
  public Long dbSize()
  {
    checkIsInMulti();
    this.client.dbSize();
    return this.client.getIntegerReply();
  }
  
  public Long expire(byte[] key, int seconds)
  {
    checkIsInMulti();
    this.client.expire(key, seconds);
    return this.client.getIntegerReply();
  }
  
  public Long expireAt(byte[] key, long unixTime)
  {
    checkIsInMulti();
    this.client.expireAt(key, unixTime);
    return this.client.getIntegerReply();
  }
  
  public Long ttl(byte[] key)
  {
    checkIsInMulti();
    this.client.ttl(key);
    return this.client.getIntegerReply();
  }
  
  public String select(int index)
  {
    checkIsInMulti();
    this.client.select(index);
    return this.client.getStatusCodeReply();
  }
  
  public Long move(byte[] key, int dbIndex)
  {
    checkIsInMulti();
    this.client.move(key, dbIndex);
    return this.client.getIntegerReply();
  }
  
  public String flushAll()
  {
    checkIsInMulti();
    this.client.flushAll();
    return this.client.getStatusCodeReply();
  }
  
  public byte[] getSet(byte[] key, byte[] value)
  {
    checkIsInMulti();
    this.client.getSet(key, value);
    return this.client.getBinaryBulkReply();
  }
  
  public List<byte[]> mget(byte[]... keys)
  {
    checkIsInMulti();
    this.client.mget(keys);
    return this.client.getBinaryMultiBulkReply();
  }
  
  public Long setnx(byte[] key, byte[] value)
  {
    checkIsInMulti();
    this.client.setnx(key, value);
    return this.client.getIntegerReply();
  }
  
  public String setex(byte[] key, int seconds, byte[] value)
  {
    checkIsInMulti();
    this.client.setex(key, seconds, value);
    return this.client.getStatusCodeReply();
  }
  
  public String mset(byte[]... keysvalues)
  {
    checkIsInMulti();
    this.client.mset(keysvalues);
    return this.client.getStatusCodeReply();
  }
  
  public Long msetnx(byte[]... keysvalues)
  {
    checkIsInMulti();
    this.client.msetnx(keysvalues);
    return this.client.getIntegerReply();
  }
  
  public Long decrBy(byte[] key, long integer)
  {
    checkIsInMulti();
    this.client.decrBy(key, integer);
    return this.client.getIntegerReply();
  }
  
  public Long decr(byte[] key)
  {
    checkIsInMulti();
    this.client.decr(key);
    return this.client.getIntegerReply();
  }
  
  public Long incrBy(byte[] key, long integer)
  {
    checkIsInMulti();
    this.client.incrBy(key, integer);
    return this.client.getIntegerReply();
  }
  
  public Long incr(byte[] key)
  {
    checkIsInMulti();
    this.client.incr(key);
    return this.client.getIntegerReply();
  }
  
  public Long append(byte[] key, byte[] value)
  {
    checkIsInMulti();
    this.client.append(key, value);
    return this.client.getIntegerReply();
  }
  
  public byte[] substr(byte[] key, int start, int end)
  {
    checkIsInMulti();
    this.client.substr(key, start, end);
    return this.client.getBinaryBulkReply();
  }
  
  public Long hset(byte[] key, byte[] field, byte[] value)
  {
    checkIsInMulti();
    this.client.hset(key, field, value);
    return this.client.getIntegerReply();
  }
  
  public byte[] hget(byte[] key, byte[] field)
  {
    checkIsInMulti();
    this.client.hget(key, field);
    return this.client.getBinaryBulkReply();
  }
  
  public Long hsetnx(byte[] key, byte[] field, byte[] value)
  {
    checkIsInMulti();
    this.client.hsetnx(key, field, value);
    return this.client.getIntegerReply();
  }
  
  public String hmset(byte[] key, Map<byte[], byte[]> hash)
  {
    checkIsInMulti();
    this.client.hmset(key, hash);
    return this.client.getStatusCodeReply();
  }
  
  public List<byte[]> hmget(byte[] key, byte[]... fields)
  {
    checkIsInMulti();
    this.client.hmget(key, fields);
    return this.client.getBinaryMultiBulkReply();
  }
  
  public Long hincrBy(byte[] key, byte[] field, long value)
  {
    checkIsInMulti();
    this.client.hincrBy(key, field, value);
    return this.client.getIntegerReply();
  }
  
  public Boolean hexists(byte[] key, byte[] field)
  {
    checkIsInMulti();
    this.client.hexists(key, field);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Long hdel(byte[] key, byte[]... fields)
  {
    checkIsInMulti();
    this.client.hdel(key, fields);
    return this.client.getIntegerReply();
  }
  
  public Long hlen(byte[] key)
  {
    checkIsInMulti();
    this.client.hlen(key);
    return this.client.getIntegerReply();
  }
  
  public Set<byte[]> hkeys(byte[] key)
  {
    checkIsInMulti();
    this.client.hkeys(key);
    List<byte[]> lresult = this.client.getBinaryMultiBulkReply();
    return new HashSet(lresult);
  }
  
  public List<byte[]> hvals(byte[] key)
  {
    checkIsInMulti();
    this.client.hvals(key);
    List<byte[]> lresult = this.client.getBinaryMultiBulkReply();
    return lresult;
  }
  
  public Map<byte[], byte[]> hgetAll(byte[] key)
  {
    checkIsInMulti();
    this.client.hgetAll(key);
    List<byte[]> flatHash = this.client.getBinaryMultiBulkReply();
    Map<byte[], byte[]> hash = new JedisByteHashMap();
    Iterator<byte[]> iterator = flatHash.iterator();
    while (iterator.hasNext()) {
      hash.put(iterator.next(), iterator.next());
    }
    return hash;
  }
  
  public Long rpush(byte[] key, byte[]... strings)
  {
    checkIsInMulti();
    this.client.rpush(key, strings);
    return this.client.getIntegerReply();
  }
  
  public Long lpush(byte[] key, byte[]... strings)
  {
    checkIsInMulti();
    this.client.lpush(key, strings);
    return this.client.getIntegerReply();
  }
  
  public Long llen(byte[] key)
  {
    checkIsInMulti();
    this.client.llen(key);
    return this.client.getIntegerReply();
  }
  
  public List<byte[]> lrange(byte[] key, long start, long end)
  {
    checkIsInMulti();
    this.client.lrange(key, start, end);
    return this.client.getBinaryMultiBulkReply();
  }
  
  public String ltrim(byte[] key, long start, long end)
  {
    checkIsInMulti();
    this.client.ltrim(key, start, end);
    return this.client.getStatusCodeReply();
  }
  
  public byte[] lindex(byte[] key, long index)
  {
    checkIsInMulti();
    this.client.lindex(key, index);
    return this.client.getBinaryBulkReply();
  }
  
  public String lset(byte[] key, long index, byte[] value)
  {
    checkIsInMulti();
    this.client.lset(key, index, value);
    return this.client.getStatusCodeReply();
  }
  
  public Long lrem(byte[] key, long count, byte[] value)
  {
    checkIsInMulti();
    this.client.lrem(key, count, value);
    return this.client.getIntegerReply();
  }
  
  public byte[] lpop(byte[] key)
  {
    checkIsInMulti();
    this.client.lpop(key);
    return this.client.getBinaryBulkReply();
  }
  
  public byte[] rpop(byte[] key)
  {
    checkIsInMulti();
    this.client.rpop(key);
    return this.client.getBinaryBulkReply();
  }
  
  public byte[] rpoplpush(byte[] srckey, byte[] dstkey)
  {
    checkIsInMulti();
    this.client.rpoplpush(srckey, dstkey);
    return this.client.getBinaryBulkReply();
  }
  
  public Long sadd(byte[] key, byte[]... members)
  {
    checkIsInMulti();
    this.client.sadd(key, members);
    return this.client.getIntegerReply();
  }
  
  public Set<byte[]> smembers(byte[] key)
  {
    checkIsInMulti();
    this.client.smembers(key);
    List<byte[]> members = this.client.getBinaryMultiBulkReply();
    return new HashSet(members);
  }
  
  public Long srem(byte[] key, byte[]... member)
  {
    checkIsInMulti();
    this.client.srem(key, member);
    return this.client.getIntegerReply();
  }
  
  public byte[] spop(byte[] key)
  {
    checkIsInMulti();
    this.client.spop(key);
    return this.client.getBinaryBulkReply();
  }
  
  public Long smove(byte[] srckey, byte[] dstkey, byte[] member)
  {
    checkIsInMulti();
    this.client.smove(srckey, dstkey, member);
    return this.client.getIntegerReply();
  }
  
  public Long scard(byte[] key)
  {
    checkIsInMulti();
    this.client.scard(key);
    return this.client.getIntegerReply();
  }
  
  public Boolean sismember(byte[] key, byte[] member)
  {
    checkIsInMulti();
    this.client.sismember(key, member);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Set<byte[]> sinter(byte[]... keys)
  {
    checkIsInMulti();
    this.client.sinter(keys);
    List<byte[]> members = this.client.getBinaryMultiBulkReply();
    return new HashSet(members);
  }
  
  public Long sinterstore(byte[] dstkey, byte[]... keys)
  {
    checkIsInMulti();
    this.client.sinterstore(dstkey, keys);
    return this.client.getIntegerReply();
  }
  
  public Set<byte[]> sunion(byte[]... keys)
  {
    checkIsInMulti();
    this.client.sunion(keys);
    List<byte[]> members = this.client.getBinaryMultiBulkReply();
    return new HashSet(members);
  }
  
  public Long sunionstore(byte[] dstkey, byte[]... keys)
  {
    checkIsInMulti();
    this.client.sunionstore(dstkey, keys);
    return this.client.getIntegerReply();
  }
  
  public Set<byte[]> sdiff(byte[]... keys)
  {
    checkIsInMulti();
    this.client.sdiff(keys);
    List<byte[]> members = this.client.getBinaryMultiBulkReply();
    return new HashSet(members);
  }
  
  public Long sdiffstore(byte[] dstkey, byte[]... keys)
  {
    checkIsInMulti();
    this.client.sdiffstore(dstkey, keys);
    return this.client.getIntegerReply();
  }
  
  public byte[] srandmember(byte[] key)
  {
    checkIsInMulti();
    this.client.srandmember(key);
    return this.client.getBinaryBulkReply();
  }
  
  public List<byte[]> srandmember(byte[] key, int count)
  {
    checkIsInMulti();
    this.client.srandmember(key, count);
    return this.client.getBinaryMultiBulkReply();
  }
  
  public Long zadd(byte[] key, double score, byte[] member)
  {
    checkIsInMulti();
    this.client.zadd(key, score, member);
    return this.client.getIntegerReply();
  }
  
  public Long zadd(byte[] key, Map<byte[], Double> scoreMembers)
  {
    checkIsInMulti();
    this.client.zaddBinary(key, scoreMembers);
    return this.client.getIntegerReply();
  }
  
  public Set<byte[]> zrange(byte[] key, long start, long end)
  {
    checkIsInMulti();
    this.client.zrange(key, start, end);
    List<byte[]> members = this.client.getBinaryMultiBulkReply();
    return new LinkedHashSet(members);
  }
  
  public Long zrem(byte[] key, byte[]... members)
  {
    checkIsInMulti();
    this.client.zrem(key, members);
    return this.client.getIntegerReply();
  }
  
  public Double zincrby(byte[] key, double score, byte[] member)
  {
    checkIsInMulti();
    this.client.zincrby(key, score, member);
    String newscore = this.client.getBulkReply();
    return Double.valueOf(newscore);
  }
  
  public Long zrank(byte[] key, byte[] member)
  {
    checkIsInMulti();
    this.client.zrank(key, member);
    return this.client.getIntegerReply();
  }
  
  public Long zrevrank(byte[] key, byte[] member)
  {
    checkIsInMulti();
    this.client.zrevrank(key, member);
    return this.client.getIntegerReply();
  }
  
  public Set<byte[]> zrevrange(byte[] key, long start, long end)
  {
    checkIsInMulti();
    this.client.zrevrange(key, start, end);
    List<byte[]> members = this.client.getBinaryMultiBulkReply();
    return new LinkedHashSet(members);
  }
  
  public Set<Tuple> zrangeWithScores(byte[] key, long start, long end)
  {
    checkIsInMulti();
    this.client.zrangeWithScores(key, start, end);
    Set<Tuple> set = getBinaryTupledSet();
    return set;
  }
  
  public Set<Tuple> zrevrangeWithScores(byte[] key, long start, long end)
  {
    checkIsInMulti();
    this.client.zrevrangeWithScores(key, start, end);
    Set<Tuple> set = getBinaryTupledSet();
    return set;
  }
  
  public Long zcard(byte[] key)
  {
    checkIsInMulti();
    this.client.zcard(key);
    return this.client.getIntegerReply();
  }
  
  public Double zscore(byte[] key, byte[] member)
  {
    checkIsInMulti();
    this.client.zscore(key, member);
    String score = this.client.getBulkReply();
    return score != null ? new Double(score) : null;
  }
  
  public Transaction multi()
  {
    this.client.multi();
    return new Transaction(this.client);
  }
  
  @Deprecated
  public List<Object> multi(TransactionBlock jedisTransaction)
  {
    List<Object> results = null;
    jedisTransaction.setClient(this.client);
    this.client.multi();
    jedisTransaction.execute();
    results = jedisTransaction.exec();
    return results;
  }
  
  protected void checkIsInMulti()
  {
    if (this.client.isInMulti()) {
      throw new JedisDataException("Cannot use Jedis when in Multi. Please use JedisTransaction instead.");
    }
  }
  
  public void connect()
  {
    this.client.connect();
  }
  
  public void disconnect()
  {
    this.client.disconnect();
  }
  
  public void resetState()
  {
    this.client.resetState();
    this.client.getAll();
  }
  
  public String watch(byte[]... keys)
  {
    this.client.watch(keys);
    return this.client.getStatusCodeReply();
  }
  
  public String unwatch()
  {
    this.client.unwatch();
    return this.client.getStatusCodeReply();
  }
  
  public void close()
  {
    this.client.close();
  }
  
  public List<byte[]> sort(byte[] key)
  {
    checkIsInMulti();
    this.client.sort(key);
    return this.client.getBinaryMultiBulkReply();
  }
  
  public List<byte[]> sort(byte[] key, SortingParams sortingParameters)
  {
    checkIsInMulti();
    this.client.sort(key, sortingParameters);
    return this.client.getBinaryMultiBulkReply();
  }
  
  public List<byte[]> blpop(int timeout, byte[]... keys)
  {
    checkIsInMulti();
    List<byte[]> args = new ArrayList();
    for (byte[] arg : keys) {
      args.add(arg);
    }
    args.add(Protocol.toByteArray(timeout));
    
    this.client.blpop((byte[][])args.toArray(new byte[args.size()][]));
    this.client.setTimeoutInfinite();
    List<byte[]> multiBulkReply = this.client.getBinaryMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public Long sort(byte[] key, SortingParams sortingParameters, byte[] dstkey)
  {
    checkIsInMulti();
    this.client.sort(key, sortingParameters, dstkey);
    return this.client.getIntegerReply();
  }
  
  public Long sort(byte[] key, byte[] dstkey)
  {
    checkIsInMulti();
    this.client.sort(key, dstkey);
    return this.client.getIntegerReply();
  }
  
  public List<byte[]> brpop(int timeout, byte[]... keys)
  {
    checkIsInMulti();
    List<byte[]> args = new ArrayList();
    for (byte[] arg : keys) {
      args.add(arg);
    }
    args.add(Protocol.toByteArray(timeout));
    
    this.client.brpop((byte[][])args.toArray(new byte[args.size()][]));
    this.client.setTimeoutInfinite();
    List<byte[]> multiBulkReply = this.client.getBinaryMultiBulkReply();
    this.client.rollbackTimeout();
    
    return multiBulkReply;
  }
  
  public List<byte[]> blpop(byte[] arg)
  {
    checkIsInMulti();
    byte[][] args = new byte[1][];
    args[0] = arg;
    this.client.blpop(args);
    this.client.setTimeoutInfinite();
    List<byte[]> multiBulkReply = this.client.getBinaryMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public List<byte[]> brpop(byte[] arg)
  {
    checkIsInMulti();
    byte[][] args = new byte[1][];
    args[0] = arg;
    this.client.brpop(args);
    this.client.setTimeoutInfinite();
    List<byte[]> multiBulkReply = this.client.getBinaryMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public List<byte[]> blpop(byte[]... args)
  {
    checkIsInMulti();
    this.client.blpop(args);
    this.client.setTimeoutInfinite();
    List<byte[]> multiBulkReply = this.client.getBinaryMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public List<byte[]> brpop(byte[]... args)
  {
    checkIsInMulti();
    this.client.brpop(args);
    this.client.setTimeoutInfinite();
    List<byte[]> multiBulkReply = this.client.getBinaryMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public String auth(String password)
  {
    checkIsInMulti();
    this.client.auth(password);
    return this.client.getStatusCodeReply();
  }
  
  @Deprecated
  public List<Object> pipelined(PipelineBlock jedisPipeline)
  {
    jedisPipeline.setClient(this.client);
    jedisPipeline.execute();
    return jedisPipeline.syncAndReturnAll();
  }
  
  public Pipeline pipelined()
  {
    Pipeline pipeline = new Pipeline();
    pipeline.setClient(this.client);
    return pipeline;
  }
  
  public Long zcount(byte[] key, double min, double max)
  {
    return zcount(key, Protocol.toByteArray(min), Protocol.toByteArray(max));
  }
  
  public Long zcount(byte[] key, byte[] min, byte[] max)
  {
    checkIsInMulti();
    this.client.zcount(key, min, max);
    return this.client.getIntegerReply();
  }
  
  public Set<byte[]> zrangeByScore(byte[] key, double min, double max)
  {
    return zrangeByScore(key, Protocol.toByteArray(min), Protocol.toByteArray(max));
  }
  
  public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max)
  {
    checkIsInMulti();
    this.client.zrangeByScore(key, min, max);
    return new LinkedHashSet(this.client.getBinaryMultiBulkReply());
  }
  
  public Set<byte[]> zrangeByScore(byte[] key, double min, double max, int offset, int count)
  {
    return zrangeByScore(key, Protocol.toByteArray(min), Protocol.toByteArray(max), offset, count);
  }
  
  public Set<byte[]> zrangeByScore(byte[] key, byte[] min, byte[] max, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrangeByScore(key, min, max, offset, count);
    return new LinkedHashSet(this.client.getBinaryMultiBulkReply());
  }
  
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max)
  {
    return zrangeByScoreWithScores(key, Protocol.toByteArray(min), Protocol.toByteArray(max));
  }
  
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max)
  {
    checkIsInMulti();
    this.client.zrangeByScoreWithScores(key, min, max);
    Set<Tuple> set = getBinaryTupledSet();
    return set;
  }
  
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, double min, double max, int offset, int count)
  {
    return zrangeByScoreWithScores(key, Protocol.toByteArray(min), Protocol.toByteArray(max), offset, count);
  }
  
  public Set<Tuple> zrangeByScoreWithScores(byte[] key, byte[] min, byte[] max, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrangeByScoreWithScores(key, min, max, offset, count);
    Set<Tuple> set = getBinaryTupledSet();
    return set;
  }
  
  private Set<Tuple> getBinaryTupledSet()
  {
    checkIsInMulti();
    List<byte[]> membersWithScores = this.client.getBinaryMultiBulkReply();
    Set<Tuple> set = new LinkedHashSet();
    Iterator<byte[]> iterator = membersWithScores.iterator();
    while (iterator.hasNext()) {
      set.add(new Tuple((byte[])iterator.next(), Double.valueOf(SafeEncoder.encode((byte[])iterator.next()))));
    }
    return set;
  }
  
  public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min)
  {
    return zrevrangeByScore(key, Protocol.toByteArray(max), Protocol.toByteArray(min));
  }
  
  public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min)
  {
    checkIsInMulti();
    this.client.zrevrangeByScore(key, max, min);
    return new LinkedHashSet(this.client.getBinaryMultiBulkReply());
  }
  
  public Set<byte[]> zrevrangeByScore(byte[] key, double max, double min, int offset, int count)
  {
    return zrevrangeByScore(key, Protocol.toByteArray(max), Protocol.toByteArray(min), offset, count);
  }
  
  public Set<byte[]> zrevrangeByScore(byte[] key, byte[] max, byte[] min, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrevrangeByScore(key, max, min, offset, count);
    return new LinkedHashSet(this.client.getBinaryMultiBulkReply());
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min)
  {
    return zrevrangeByScoreWithScores(key, Protocol.toByteArray(max), Protocol.toByteArray(min));
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, double max, double min, int offset, int count)
  {
    return zrevrangeByScoreWithScores(key, Protocol.toByteArray(max), Protocol.toByteArray(min), offset, count);
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min)
  {
    checkIsInMulti();
    this.client.zrevrangeByScoreWithScores(key, max, min);
    Set<Tuple> set = getBinaryTupledSet();
    return set;
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(byte[] key, byte[] max, byte[] min, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrevrangeByScoreWithScores(key, max, min, offset, count);
    Set<Tuple> set = getBinaryTupledSet();
    return set;
  }
  
  public Long zremrangeByRank(byte[] key, long start, long end)
  {
    checkIsInMulti();
    this.client.zremrangeByRank(key, start, end);
    return this.client.getIntegerReply();
  }
  
  public Long zremrangeByScore(byte[] key, double start, double end)
  {
    return zremrangeByScore(key, Protocol.toByteArray(start), Protocol.toByteArray(end));
  }
  
  public Long zremrangeByScore(byte[] key, byte[] start, byte[] end)
  {
    checkIsInMulti();
    this.client.zremrangeByScore(key, start, end);
    return this.client.getIntegerReply();
  }
  
  public Long zunionstore(byte[] dstkey, byte[]... sets)
  {
    checkIsInMulti();
    this.client.zunionstore(dstkey, sets);
    return this.client.getIntegerReply();
  }
  
  public Long zunionstore(byte[] dstkey, ZParams params, byte[]... sets)
  {
    checkIsInMulti();
    this.client.zunionstore(dstkey, params, sets);
    return this.client.getIntegerReply();
  }
  
  public Long zinterstore(byte[] dstkey, byte[]... sets)
  {
    checkIsInMulti();
    this.client.zinterstore(dstkey, sets);
    return this.client.getIntegerReply();
  }
  
  public Long zinterstore(byte[] dstkey, ZParams params, byte[]... sets)
  {
    checkIsInMulti();
    this.client.zinterstore(dstkey, params, sets);
    return this.client.getIntegerReply();
  }
  
  public String save()
  {
    this.client.save();
    return this.client.getStatusCodeReply();
  }
  
  public String bgsave()
  {
    this.client.bgsave();
    return this.client.getStatusCodeReply();
  }
  
  public String bgrewriteaof()
  {
    this.client.bgrewriteaof();
    return this.client.getStatusCodeReply();
  }
  
  public Long lastsave()
  {
    this.client.lastsave();
    return this.client.getIntegerReply();
  }
  
  public String shutdown()
  {
    this.client.shutdown();
    String status = null;
    try
    {
      status = this.client.getStatusCodeReply();
    }
    catch (JedisException ex)
    {
      status = null;
    }
    return status;
  }
  
  public String info()
  {
    this.client.info();
    return this.client.getBulkReply();
  }
  
  public String info(String section)
  {
    this.client.info(section);
    return this.client.getBulkReply();
  }
  
  public void monitor(JedisMonitor jedisMonitor)
  {
    this.client.monitor();
    this.client.getStatusCodeReply();
    jedisMonitor.proceed(this.client);
  }
  
  public String slaveof(String host, int port)
  {
    this.client.slaveof(host, port);
    return this.client.getStatusCodeReply();
  }
  
  public String slaveofNoOne()
  {
    this.client.slaveofNoOne();
    return this.client.getStatusCodeReply();
  }
  
  public List<byte[]> configGet(byte[] pattern)
  {
    this.client.configGet(pattern);
    return this.client.getBinaryMultiBulkReply();
  }
  
  public String configResetStat()
  {
    this.client.configResetStat();
    return this.client.getStatusCodeReply();
  }
  
  public byte[] configSet(byte[] parameter, byte[] value)
  {
    this.client.configSet(parameter, value);
    return this.client.getBinaryBulkReply();
  }
  
  public boolean isConnected()
  {
    return this.client.isConnected();
  }
  
  public Long strlen(byte[] key)
  {
    this.client.strlen(key);
    return this.client.getIntegerReply();
  }
  
  public void sync()
  {
    this.client.sync();
  }
  
  public Long lpushx(byte[] key, byte[]... string)
  {
    this.client.lpushx(key, string);
    return this.client.getIntegerReply();
  }
  
  public Long persist(byte[] key)
  {
    this.client.persist(key);
    return this.client.getIntegerReply();
  }
  
  public Long rpushx(byte[] key, byte[]... string)
  {
    this.client.rpushx(key, string);
    return this.client.getIntegerReply();
  }
  
  public byte[] echo(byte[] string)
  {
    this.client.echo(string);
    return this.client.getBinaryBulkReply();
  }
  
  public Long linsert(byte[] key, BinaryClient.LIST_POSITION where, byte[] pivot, byte[] value)
  {
    this.client.linsert(key, where, pivot, value);
    return this.client.getIntegerReply();
  }
  
  public String debug(DebugParams params)
  {
    this.client.debug(params);
    return this.client.getStatusCodeReply();
  }
  
  public Client getClient()
  {
    return this.client;
  }
  
  public byte[] brpoplpush(byte[] source, byte[] destination, int timeout)
  {
    this.client.brpoplpush(source, destination, timeout);
    this.client.setTimeoutInfinite();
    byte[] reply = this.client.getBinaryBulkReply();
    this.client.rollbackTimeout();
    return reply;
  }
  
  public Boolean setbit(byte[] key, long offset, boolean value)
  {
    this.client.setbit(key, offset, value);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Boolean setbit(byte[] key, long offset, byte[] value)
  {
    this.client.setbit(key, offset, value);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Boolean getbit(byte[] key, long offset)
  {
    this.client.getbit(key, offset);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Long setrange(byte[] key, long offset, byte[] value)
  {
    this.client.setrange(key, offset, value);
    return this.client.getIntegerReply();
  }
  
  public byte[] getrange(byte[] key, long startOffset, long endOffset)
  {
    this.client.getrange(key, startOffset, endOffset);
    return this.client.getBinaryBulkReply();
  }
  
  public Long publish(byte[] channel, byte[] message)
  {
    this.client.publish(channel, message);
    return this.client.getIntegerReply();
  }
  
  public void subscribe(BinaryJedisPubSub jedisPubSub, byte[]... channels)
  {
    this.client.setTimeoutInfinite();
    jedisPubSub.proceed(this.client, channels);
    this.client.rollbackTimeout();
  }
  
  public void psubscribe(BinaryJedisPubSub jedisPubSub, byte[]... patterns)
  {
    this.client.setTimeoutInfinite();
    jedisPubSub.proceedWithPatterns(this.client, patterns);
    this.client.rollbackTimeout();
  }
  
  public Long getDB()
  {
    return this.client.getDB();
  }
  
  public Object eval(byte[] script, List<byte[]> keys, List<byte[]> args)
  {
    this.client.setTimeoutInfinite();
    this.client.eval(script, Protocol.toByteArray(keys.size()), getParams(keys, args));
    return this.client.getOne();
  }
  
  private byte[][] getParams(List<byte[]> keys, List<byte[]> args)
  {
    int keyCount = keys.size();
    int argCount = args.size();
    byte[][] params = new byte[keyCount + args.size()][];
    for (int i = 0; i < keyCount; i++) {
      params[i] = ((byte[])keys.get(i));
    }
    for (int i = 0; i < argCount; i++) {
      params[(keyCount + i)] = ((byte[])args.get(i));
    }
    return params;
  }
  
  public Object eval(byte[] script, byte[] keyCount, byte[]... params)
  {
    this.client.setTimeoutInfinite();
    this.client.eval(script, keyCount, params);
    return this.client.getOne();
  }
  
  public Object eval(byte[] script, int keyCount, byte[]... params)
  {
    this.client.setTimeoutInfinite();
    this.client.eval(script, SafeEncoder.encode(Integer.toString(keyCount)), params);
    
    return this.client.getOne();
  }
  
  public Object eval(byte[] script)
  {
    this.client.setTimeoutInfinite();
    this.client.eval(script, 0, new byte[0][]);
    return this.client.getOne();
  }
  
  public Object evalsha(byte[] sha1)
  {
    this.client.setTimeoutInfinite();
    this.client.evalsha(sha1, 0, new byte[0][]);
    return this.client.getOne();
  }
  
  public Object evalsha(byte[] sha1, List<byte[]> keys, List<byte[]> args)
  {
    int keyCount = keys == null ? 0 : keys.size();
    int argCount = args == null ? 0 : args.size();
    
    byte[][] params = new byte[keyCount + argCount][];
    for (int i = 0; i < keyCount; i++) {
      params[i] = ((byte[])keys.get(i));
    }
    for (int i = 0; i < argCount; i++) {
      params[(keyCount + i)] = ((byte[])args.get(i));
    }
    return evalsha(sha1, keyCount, params);
  }
  
  public Object evalsha(byte[] sha1, int keyCount, byte[]... params)
  {
    this.client.setTimeoutInfinite();
    this.client.evalsha(sha1, keyCount, params);
    return this.client.getOne();
  }
  
  public String scriptFlush()
  {
    this.client.scriptFlush();
    return this.client.getStatusCodeReply();
  }
  
  public List<Long> scriptExists(byte[]... sha1)
  {
    this.client.scriptExists(sha1);
    return this.client.getIntegerMultiBulkReply();
  }
  
  public byte[] scriptLoad(byte[] script)
  {
    this.client.scriptLoad(script);
    return this.client.getBinaryBulkReply();
  }
  
  public String scriptKill()
  {
    this.client.scriptKill();
    return this.client.getStatusCodeReply();
  }
  
  public String slowlogReset()
  {
    this.client.slowlogReset();
    return this.client.getBulkReply();
  }
  
  public Long slowlogLen()
  {
    this.client.slowlogLen();
    return this.client.getIntegerReply();
  }
  
  public List<byte[]> slowlogGetBinary()
  {
    this.client.slowlogGet();
    return this.client.getBinaryMultiBulkReply();
  }
  
  public List<byte[]> slowlogGetBinary(long entries)
  {
    this.client.slowlogGet(entries);
    return this.client.getBinaryMultiBulkReply();
  }
  
  public Long objectRefcount(byte[] key)
  {
    this.client.objectRefcount(key);
    return this.client.getIntegerReply();
  }
  
  public byte[] objectEncoding(byte[] key)
  {
    this.client.objectEncoding(key);
    return this.client.getBinaryBulkReply();
  }
  
  public Long objectIdletime(byte[] key)
  {
    this.client.objectIdletime(key);
    return this.client.getIntegerReply();
  }
  
  public Long bitcount(byte[] key)
  {
    this.client.bitcount(key);
    return this.client.getIntegerReply();
  }
  
  public Long bitcount(byte[] key, long start, long end)
  {
    this.client.bitcount(key, start, end);
    return this.client.getIntegerReply();
  }
  
  public Long bitop(BitOP op, byte[] destKey, byte[]... srcKeys)
  {
    this.client.bitop(op, destKey, srcKeys);
    return this.client.getIntegerReply();
  }
  
  public byte[] dump(byte[] key)
  {
    checkIsInMulti();
    this.client.dump(key);
    return this.client.getBinaryBulkReply();
  }
  
  public String restore(byte[] key, int ttl, byte[] serializedValue)
  {
    checkIsInMulti();
    this.client.restore(key, ttl, serializedValue);
    return this.client.getStatusCodeReply();
  }
  
  public Long pexpire(byte[] key, int milliseconds)
  {
    checkIsInMulti();
    this.client.pexpire(key, milliseconds);
    return this.client.getIntegerReply();
  }
  
  public Long pexpireAt(byte[] key, long millisecondsTimestamp)
  {
    checkIsInMulti();
    this.client.pexpireAt(key, millisecondsTimestamp);
    return this.client.getIntegerReply();
  }
  
  public Long pttl(byte[] key)
  {
    checkIsInMulti();
    this.client.pttl(key);
    return this.client.getIntegerReply();
  }
  
  public Double incrByFloat(byte[] key, double increment)
  {
    checkIsInMulti();
    this.client.incrByFloat(key, increment);
    String relpy = this.client.getBulkReply();
    return relpy != null ? new Double(relpy) : null;
  }
  
  public String psetex(byte[] key, int milliseconds, byte[] value)
  {
    checkIsInMulti();
    this.client.psetex(key, milliseconds, value);
    return this.client.getStatusCodeReply();
  }
  
  public String set(byte[] key, byte[] value, byte[] nxxx)
  {
    checkIsInMulti();
    this.client.set(key, value, nxxx);
    return this.client.getStatusCodeReply();
  }
  
  public String set(byte[] key, byte[] value, byte[] nxxx, byte[] expx, int time)
  {
    checkIsInMulti();
    this.client.set(key, value, nxxx, expx, time);
    return this.client.getStatusCodeReply();
  }
  
  public String clientKill(byte[] client)
  {
    checkIsInMulti();
    this.client.clientKill(client);
    return this.client.getStatusCodeReply();
  }
  
  public String clientGetname()
  {
    checkIsInMulti();
    this.client.clientGetname();
    return this.client.getBulkReply();
  }
  
  public String clientList()
  {
    checkIsInMulti();
    this.client.clientList();
    return this.client.getBulkReply();
  }
  
  public String clientSetname(byte[] name)
  {
    checkIsInMulti();
    this.client.clientSetname(name);
    return this.client.getBulkReply();
  }
  
  public List<String> time()
  {
    checkIsInMulti();
    this.client.time();
    return this.client.getMultiBulkReply();
  }
  
  public String migrate(byte[] host, int port, byte[] key, int destinationDb, int timeout)
  {
    checkIsInMulti();
    this.client.migrate(host, port, key, destinationDb, timeout);
    return this.client.getStatusCodeReply();
  }
  
  public Double hincrByFloat(byte[] key, byte[] field, double increment)
  {
    checkIsInMulti();
    this.client.hincrByFloat(key, field, increment);
    String relpy = this.client.getBulkReply();
    return relpy != null ? new Double(relpy) : null;
  }
  
  public Long waitReplicas(int replicas, long timeout)
  {
    checkIsInMulti();
    this.client.waitReplicas(replicas, timeout);
    return this.client.getIntegerReply();
  }
}
