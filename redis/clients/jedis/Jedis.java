package redis.clients.jedis;

import java.net.URI;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import redis.clients.util.SafeEncoder;
import redis.clients.util.Slowlog;

public class Jedis
  extends BinaryJedis
  implements JedisCommands, MultiKeyCommands, AdvancedJedisCommands, ScriptingCommands, BasicCommands, ClusterCommands
{
  public Jedis(String host)
  {
    super(host);
  }
  
  public Jedis(String host, int port)
  {
    super(host, port);
  }
  
  public Jedis(String host, int port, int timeout)
  {
    super(host, port, timeout);
  }
  
  public Jedis(JedisShardInfo shardInfo)
  {
    super(shardInfo);
  }
  
  public Jedis(URI uri)
  {
    super(uri);
  }
  
  public String set(String key, String value)
  {
    checkIsInMulti();
    this.client.set(key, value);
    return this.client.getStatusCodeReply();
  }
  
  public String set(String key, String value, String nxxx, String expx, long time)
  {
    checkIsInMulti();
    this.client.set(key, value, nxxx, expx, time);
    return this.client.getStatusCodeReply();
  }
  
  public String get(String key)
  {
    checkIsInMulti();
    this.client.sendCommand(Protocol.Command.GET, new String[] { key });
    return this.client.getBulkReply();
  }
  
  public Boolean exists(String key)
  {
    checkIsInMulti();
    this.client.exists(key);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Long del(String... keys)
  {
    checkIsInMulti();
    this.client.del(keys);
    return this.client.getIntegerReply();
  }
  
  public Long del(String key)
  {
    this.client.del(new String[] { key });
    return this.client.getIntegerReply();
  }
  
  public String type(String key)
  {
    checkIsInMulti();
    this.client.type(key);
    return this.client.getStatusCodeReply();
  }
  
  public Set<String> keys(String pattern)
  {
    checkIsInMulti();
    this.client.keys(pattern);
    return (Set)BuilderFactory.STRING_SET.build(this.client.getBinaryMultiBulkReply());
  }
  
  public String randomKey()
  {
    checkIsInMulti();
    this.client.randomKey();
    return this.client.getBulkReply();
  }
  
  public String rename(String oldkey, String newkey)
  {
    checkIsInMulti();
    this.client.rename(oldkey, newkey);
    return this.client.getStatusCodeReply();
  }
  
  public Long renamenx(String oldkey, String newkey)
  {
    checkIsInMulti();
    this.client.renamenx(oldkey, newkey);
    return this.client.getIntegerReply();
  }
  
  public Long expire(String key, int seconds)
  {
    checkIsInMulti();
    this.client.expire(key, seconds);
    return this.client.getIntegerReply();
  }
  
  public Long expireAt(String key, long unixTime)
  {
    checkIsInMulti();
    this.client.expireAt(key, unixTime);
    return this.client.getIntegerReply();
  }
  
  public Long ttl(String key)
  {
    checkIsInMulti();
    this.client.ttl(key);
    return this.client.getIntegerReply();
  }
  
  public Long move(String key, int dbIndex)
  {
    checkIsInMulti();
    this.client.move(key, dbIndex);
    return this.client.getIntegerReply();
  }
  
  public String getSet(String key, String value)
  {
    checkIsInMulti();
    this.client.getSet(key, value);
    return this.client.getBulkReply();
  }
  
  public List<String> mget(String... keys)
  {
    checkIsInMulti();
    this.client.mget(keys);
    return this.client.getMultiBulkReply();
  }
  
  public Long setnx(String key, String value)
  {
    checkIsInMulti();
    this.client.setnx(key, value);
    return this.client.getIntegerReply();
  }
  
  public String setex(String key, int seconds, String value)
  {
    checkIsInMulti();
    this.client.setex(key, seconds, value);
    return this.client.getStatusCodeReply();
  }
  
  public String mset(String... keysvalues)
  {
    checkIsInMulti();
    this.client.mset(keysvalues);
    return this.client.getStatusCodeReply();
  }
  
  public Long msetnx(String... keysvalues)
  {
    checkIsInMulti();
    this.client.msetnx(keysvalues);
    return this.client.getIntegerReply();
  }
  
  public Long decrBy(String key, long integer)
  {
    checkIsInMulti();
    this.client.decrBy(key, integer);
    return this.client.getIntegerReply();
  }
  
  public Long decr(String key)
  {
    checkIsInMulti();
    this.client.decr(key);
    return this.client.getIntegerReply();
  }
  
  public Long incrBy(String key, long integer)
  {
    checkIsInMulti();
    this.client.incrBy(key, integer);
    return this.client.getIntegerReply();
  }
  
  public Long incr(String key)
  {
    checkIsInMulti();
    this.client.incr(key);
    return this.client.getIntegerReply();
  }
  
  public Long append(String key, String value)
  {
    checkIsInMulti();
    this.client.append(key, value);
    return this.client.getIntegerReply();
  }
  
  public String substr(String key, int start, int end)
  {
    checkIsInMulti();
    this.client.substr(key, start, end);
    return this.client.getBulkReply();
  }
  
  public Long hset(String key, String field, String value)
  {
    checkIsInMulti();
    this.client.hset(key, field, value);
    return this.client.getIntegerReply();
  }
  
  public String hget(String key, String field)
  {
    checkIsInMulti();
    this.client.hget(key, field);
    return this.client.getBulkReply();
  }
  
  public Long hsetnx(String key, String field, String value)
  {
    checkIsInMulti();
    this.client.hsetnx(key, field, value);
    return this.client.getIntegerReply();
  }
  
  public String hmset(String key, Map<String, String> hash)
  {
    checkIsInMulti();
    this.client.hmset(key, hash);
    return this.client.getStatusCodeReply();
  }
  
  public List<String> hmget(String key, String... fields)
  {
    checkIsInMulti();
    this.client.hmget(key, fields);
    return this.client.getMultiBulkReply();
  }
  
  public Long hincrBy(String key, String field, long value)
  {
    checkIsInMulti();
    this.client.hincrBy(key, field, value);
    return this.client.getIntegerReply();
  }
  
  public Boolean hexists(String key, String field)
  {
    checkIsInMulti();
    this.client.hexists(key, field);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Long hdel(String key, String... fields)
  {
    checkIsInMulti();
    this.client.hdel(key, fields);
    return this.client.getIntegerReply();
  }
  
  public Long hlen(String key)
  {
    checkIsInMulti();
    this.client.hlen(key);
    return this.client.getIntegerReply();
  }
  
  public Set<String> hkeys(String key)
  {
    checkIsInMulti();
    this.client.hkeys(key);
    return (Set)BuilderFactory.STRING_SET.build(this.client.getBinaryMultiBulkReply());
  }
  
  public List<String> hvals(String key)
  {
    checkIsInMulti();
    this.client.hvals(key);
    List<String> lresult = this.client.getMultiBulkReply();
    return lresult;
  }
  
  public Map<String, String> hgetAll(String key)
  {
    checkIsInMulti();
    this.client.hgetAll(key);
    return (Map)BuilderFactory.STRING_MAP.build(this.client.getBinaryMultiBulkReply());
  }
  
  public Long rpush(String key, String... strings)
  {
    checkIsInMulti();
    this.client.rpush(key, strings);
    return this.client.getIntegerReply();
  }
  
  public Long lpush(String key, String... strings)
  {
    checkIsInMulti();
    this.client.lpush(key, strings);
    return this.client.getIntegerReply();
  }
  
  public Long llen(String key)
  {
    checkIsInMulti();
    this.client.llen(key);
    return this.client.getIntegerReply();
  }
  
  public List<String> lrange(String key, long start, long end)
  {
    checkIsInMulti();
    this.client.lrange(key, start, end);
    return this.client.getMultiBulkReply();
  }
  
  public String ltrim(String key, long start, long end)
  {
    checkIsInMulti();
    this.client.ltrim(key, start, end);
    return this.client.getStatusCodeReply();
  }
  
  public String lindex(String key, long index)
  {
    checkIsInMulti();
    this.client.lindex(key, index);
    return this.client.getBulkReply();
  }
  
  public String lset(String key, long index, String value)
  {
    checkIsInMulti();
    this.client.lset(key, index, value);
    return this.client.getStatusCodeReply();
  }
  
  public Long lrem(String key, long count, String value)
  {
    checkIsInMulti();
    this.client.lrem(key, count, value);
    return this.client.getIntegerReply();
  }
  
  public String lpop(String key)
  {
    checkIsInMulti();
    this.client.lpop(key);
    return this.client.getBulkReply();
  }
  
  public String rpop(String key)
  {
    checkIsInMulti();
    this.client.rpop(key);
    return this.client.getBulkReply();
  }
  
  public String rpoplpush(String srckey, String dstkey)
  {
    checkIsInMulti();
    this.client.rpoplpush(srckey, dstkey);
    return this.client.getBulkReply();
  }
  
  public Long sadd(String key, String... members)
  {
    checkIsInMulti();
    this.client.sadd(key, members);
    return this.client.getIntegerReply();
  }
  
  public Set<String> smembers(String key)
  {
    checkIsInMulti();
    this.client.smembers(key);
    List<String> members = this.client.getMultiBulkReply();
    return new HashSet(members);
  }
  
  public Long srem(String key, String... members)
  {
    checkIsInMulti();
    this.client.srem(key, members);
    return this.client.getIntegerReply();
  }
  
  public String spop(String key)
  {
    checkIsInMulti();
    this.client.spop(key);
    return this.client.getBulkReply();
  }
  
  public Long smove(String srckey, String dstkey, String member)
  {
    checkIsInMulti();
    this.client.smove(srckey, dstkey, member);
    return this.client.getIntegerReply();
  }
  
  public Long scard(String key)
  {
    checkIsInMulti();
    this.client.scard(key);
    return this.client.getIntegerReply();
  }
  
  public Boolean sismember(String key, String member)
  {
    checkIsInMulti();
    this.client.sismember(key, member);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Set<String> sinter(String... keys)
  {
    checkIsInMulti();
    this.client.sinter(keys);
    List<String> members = this.client.getMultiBulkReply();
    return new HashSet(members);
  }
  
  public Long sinterstore(String dstkey, String... keys)
  {
    checkIsInMulti();
    this.client.sinterstore(dstkey, keys);
    return this.client.getIntegerReply();
  }
  
  public Set<String> sunion(String... keys)
  {
    checkIsInMulti();
    this.client.sunion(keys);
    List<String> members = this.client.getMultiBulkReply();
    return new HashSet(members);
  }
  
  public Long sunionstore(String dstkey, String... keys)
  {
    checkIsInMulti();
    this.client.sunionstore(dstkey, keys);
    return this.client.getIntegerReply();
  }
  
  public Set<String> sdiff(String... keys)
  {
    checkIsInMulti();
    this.client.sdiff(keys);
    return (Set)BuilderFactory.STRING_SET.build(this.client.getBinaryMultiBulkReply());
  }
  
  public Long sdiffstore(String dstkey, String... keys)
  {
    checkIsInMulti();
    this.client.sdiffstore(dstkey, keys);
    return this.client.getIntegerReply();
  }
  
  public String srandmember(String key)
  {
    checkIsInMulti();
    this.client.srandmember(key);
    return this.client.getBulkReply();
  }
  
  public List<String> srandmember(String key, int count)
  {
    checkIsInMulti();
    this.client.srandmember(key, count);
    return this.client.getMultiBulkReply();
  }
  
  public Long zadd(String key, double score, String member)
  {
    checkIsInMulti();
    this.client.zadd(key, score, member);
    return this.client.getIntegerReply();
  }
  
  public Long zadd(String key, Map<String, Double> scoreMembers)
  {
    checkIsInMulti();
    this.client.zadd(key, scoreMembers);
    return this.client.getIntegerReply();
  }
  
  public Set<String> zrange(String key, long start, long end)
  {
    checkIsInMulti();
    this.client.zrange(key, start, end);
    List<String> members = this.client.getMultiBulkReply();
    return new LinkedHashSet(members);
  }
  
  public Long zrem(String key, String... members)
  {
    checkIsInMulti();
    this.client.zrem(key, members);
    return this.client.getIntegerReply();
  }
  
  public Double zincrby(String key, double score, String member)
  {
    checkIsInMulti();
    this.client.zincrby(key, score, member);
    String newscore = this.client.getBulkReply();
    return Double.valueOf(newscore);
  }
  
  public Long zrank(String key, String member)
  {
    checkIsInMulti();
    this.client.zrank(key, member);
    return this.client.getIntegerReply();
  }
  
  public Long zrevrank(String key, String member)
  {
    checkIsInMulti();
    this.client.zrevrank(key, member);
    return this.client.getIntegerReply();
  }
  
  public Set<String> zrevrange(String key, long start, long end)
  {
    checkIsInMulti();
    this.client.zrevrange(key, start, end);
    List<String> members = this.client.getMultiBulkReply();
    return new LinkedHashSet(members);
  }
  
  public Set<Tuple> zrangeWithScores(String key, long start, long end)
  {
    checkIsInMulti();
    this.client.zrangeWithScores(key, start, end);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  public Set<Tuple> zrevrangeWithScores(String key, long start, long end)
  {
    checkIsInMulti();
    this.client.zrevrangeWithScores(key, start, end);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  public Long zcard(String key)
  {
    checkIsInMulti();
    this.client.zcard(key);
    return this.client.getIntegerReply();
  }
  
  public Double zscore(String key, String member)
  {
    checkIsInMulti();
    this.client.zscore(key, member);
    String score = this.client.getBulkReply();
    return score != null ? new Double(score) : null;
  }
  
  public String watch(String... keys)
  {
    this.client.watch(keys);
    return this.client.getStatusCodeReply();
  }
  
  public List<String> sort(String key)
  {
    checkIsInMulti();
    this.client.sort(key);
    return this.client.getMultiBulkReply();
  }
  
  public List<String> sort(String key, SortingParams sortingParameters)
  {
    checkIsInMulti();
    this.client.sort(key, sortingParameters);
    return this.client.getMultiBulkReply();
  }
  
  public List<String> blpop(int timeout, String... keys)
  {
    checkIsInMulti();
    List<String> args = new ArrayList();
    for (String arg : keys) {
      args.add(arg);
    }
    args.add(String.valueOf(timeout));
    
    this.client.blpop((String[])args.toArray(new String[args.size()]));
    this.client.setTimeoutInfinite();
    List<String> multiBulkReply = this.client.getMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public List<String> blpop(String... args)
  {
    this.client.blpop(args);
    this.client.setTimeoutInfinite();
    List<String> multiBulkReply = this.client.getMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public List<String> brpop(String... args)
  {
    this.client.brpop(args);
    this.client.setTimeoutInfinite();
    List<String> multiBulkReply = this.client.getMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public List<String> blpop(String arg)
  {
    String[] args = new String[1];
    args[0] = arg;
    this.client.blpop(args);
    this.client.setTimeoutInfinite();
    List<String> multiBulkReply = this.client.getMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public List<String> brpop(String arg)
  {
    String[] args = new String[1];
    args[0] = arg;
    this.client.brpop(args);
    this.client.setTimeoutInfinite();
    List<String> multiBulkReply = this.client.getMultiBulkReply();
    this.client.rollbackTimeout();
    return multiBulkReply;
  }
  
  public Long sort(String key, SortingParams sortingParameters, String dstkey)
  {
    checkIsInMulti();
    this.client.sort(key, sortingParameters, dstkey);
    return this.client.getIntegerReply();
  }
  
  public Long sort(String key, String dstkey)
  {
    checkIsInMulti();
    this.client.sort(key, dstkey);
    return this.client.getIntegerReply();
  }
  
  public List<String> brpop(int timeout, String... keys)
  {
    checkIsInMulti();
    List<String> args = new ArrayList();
    for (String arg : keys) {
      args.add(arg);
    }
    args.add(String.valueOf(timeout));
    
    this.client.brpop((String[])args.toArray(new String[args.size()]));
    this.client.setTimeoutInfinite();
    List<String> multiBulkReply = this.client.getMultiBulkReply();
    this.client.rollbackTimeout();
    
    return multiBulkReply;
  }
  
  public Long zcount(String key, double min, double max)
  {
    checkIsInMulti();
    this.client.zcount(key, min, max);
    return this.client.getIntegerReply();
  }
  
  public Long zcount(String key, String min, String max)
  {
    checkIsInMulti();
    this.client.zcount(key, min, max);
    return this.client.getIntegerReply();
  }
  
  public Set<String> zrangeByScore(String key, double min, double max)
  {
    checkIsInMulti();
    this.client.zrangeByScore(key, min, max);
    return new LinkedHashSet(this.client.getMultiBulkReply());
  }
  
  public Set<String> zrangeByScore(String key, String min, String max)
  {
    checkIsInMulti();
    this.client.zrangeByScore(key, min, max);
    return new LinkedHashSet(this.client.getMultiBulkReply());
  }
  
  public Set<String> zrangeByScore(String key, double min, double max, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrangeByScore(key, min, max, offset, count);
    return new LinkedHashSet(this.client.getMultiBulkReply());
  }
  
  public Set<String> zrangeByScore(String key, String min, String max, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrangeByScore(key, min, max, offset, count);
    return new LinkedHashSet(this.client.getMultiBulkReply());
  }
  
  public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max)
  {
    checkIsInMulti();
    this.client.zrangeByScoreWithScores(key, min, max);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max)
  {
    checkIsInMulti();
    this.client.zrangeByScoreWithScores(key, min, max);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  public Set<Tuple> zrangeByScoreWithScores(String key, double min, double max, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrangeByScoreWithScores(key, min, max, offset, count);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  public Set<Tuple> zrangeByScoreWithScores(String key, String min, String max, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrangeByScoreWithScores(key, min, max, offset, count);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  private Set<Tuple> getTupledSet()
  {
    checkIsInMulti();
    List<String> membersWithScores = this.client.getMultiBulkReply();
    Set<Tuple> set = new LinkedHashSet();
    Iterator<String> iterator = membersWithScores.iterator();
    while (iterator.hasNext()) {
      set.add(new Tuple((String)iterator.next(), Double.valueOf((String)iterator.next())));
    }
    return set;
  }
  
  public Set<String> zrevrangeByScore(String key, double max, double min)
  {
    checkIsInMulti();
    this.client.zrevrangeByScore(key, max, min);
    return new LinkedHashSet(this.client.getMultiBulkReply());
  }
  
  public Set<String> zrevrangeByScore(String key, String max, String min)
  {
    checkIsInMulti();
    this.client.zrevrangeByScore(key, max, min);
    return new LinkedHashSet(this.client.getMultiBulkReply());
  }
  
  public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrevrangeByScore(key, max, min, offset, count);
    return new LinkedHashSet(this.client.getMultiBulkReply());
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min)
  {
    checkIsInMulti();
    this.client.zrevrangeByScoreWithScores(key, max, min);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrevrangeByScoreWithScores(key, max, min, offset, count);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrevrangeByScoreWithScores(key, max, min, offset, count);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  public Set<String> zrevrangeByScore(String key, String max, String min, int offset, int count)
  {
    checkIsInMulti();
    this.client.zrevrangeByScore(key, max, min, offset, count);
    return new LinkedHashSet(this.client.getMultiBulkReply());
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(String key, String max, String min)
  {
    checkIsInMulti();
    this.client.zrevrangeByScoreWithScores(key, max, min);
    Set<Tuple> set = getTupledSet();
    return set;
  }
  
  public Long zremrangeByRank(String key, long start, long end)
  {
    checkIsInMulti();
    this.client.zremrangeByRank(key, start, end);
    return this.client.getIntegerReply();
  }
  
  public Long zremrangeByScore(String key, double start, double end)
  {
    checkIsInMulti();
    this.client.zremrangeByScore(key, start, end);
    return this.client.getIntegerReply();
  }
  
  public Long zremrangeByScore(String key, String start, String end)
  {
    checkIsInMulti();
    this.client.zremrangeByScore(key, start, end);
    return this.client.getIntegerReply();
  }
  
  public Long zunionstore(String dstkey, String... sets)
  {
    checkIsInMulti();
    this.client.zunionstore(dstkey, sets);
    return this.client.getIntegerReply();
  }
  
  public Long zunionstore(String dstkey, ZParams params, String... sets)
  {
    checkIsInMulti();
    this.client.zunionstore(dstkey, params, sets);
    return this.client.getIntegerReply();
  }
  
  public Long zinterstore(String dstkey, String... sets)
  {
    checkIsInMulti();
    this.client.zinterstore(dstkey, sets);
    return this.client.getIntegerReply();
  }
  
  public Long zinterstore(String dstkey, ZParams params, String... sets)
  {
    checkIsInMulti();
    this.client.zinterstore(dstkey, params, sets);
    return this.client.getIntegerReply();
  }
  
  public Long strlen(String key)
  {
    this.client.strlen(key);
    return this.client.getIntegerReply();
  }
  
  public Long lpushx(String key, String... string)
  {
    this.client.lpushx(key, string);
    return this.client.getIntegerReply();
  }
  
  public Long persist(String key)
  {
    this.client.persist(key);
    return this.client.getIntegerReply();
  }
  
  public Long rpushx(String key, String... string)
  {
    this.client.rpushx(key, string);
    return this.client.getIntegerReply();
  }
  
  public String echo(String string)
  {
    this.client.echo(string);
    return this.client.getBulkReply();
  }
  
  public Long linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value)
  {
    this.client.linsert(key, where, pivot, value);
    return this.client.getIntegerReply();
  }
  
  public String brpoplpush(String source, String destination, int timeout)
  {
    this.client.brpoplpush(source, destination, timeout);
    this.client.setTimeoutInfinite();
    String reply = this.client.getBulkReply();
    this.client.rollbackTimeout();
    return reply;
  }
  
  public Boolean setbit(String key, long offset, boolean value)
  {
    this.client.setbit(key, offset, value);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Boolean setbit(String key, long offset, String value)
  {
    this.client.setbit(key, offset, value);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Boolean getbit(String key, long offset)
  {
    this.client.getbit(key, offset);
    return Boolean.valueOf(this.client.getIntegerReply().longValue() == 1L);
  }
  
  public Long setrange(String key, long offset, String value)
  {
    this.client.setrange(key, offset, value);
    return this.client.getIntegerReply();
  }
  
  public String getrange(String key, long startOffset, long endOffset)
  {
    this.client.getrange(key, startOffset, endOffset);
    return this.client.getBulkReply();
  }
  
  public List<String> configGet(String pattern)
  {
    this.client.configGet(pattern);
    return this.client.getMultiBulkReply();
  }
  
  public String configSet(String parameter, String value)
  {
    this.client.configSet(parameter, value);
    return this.client.getStatusCodeReply();
  }
  
  public Object eval(String script, int keyCount, String... params)
  {
    this.client.setTimeoutInfinite();
    this.client.eval(script, keyCount, params);
    
    return getEvalResult();
  }
  
  public void subscribe(JedisPubSub jedisPubSub, String... channels)
  {
    this.client.setTimeoutInfinite();
    jedisPubSub.proceed(this.client, channels);
    this.client.rollbackTimeout();
  }
  
  public Long publish(String channel, String message)
  {
    checkIsInMulti();
    connect();
    this.client.publish(channel, message);
    return this.client.getIntegerReply();
  }
  
  public void psubscribe(JedisPubSub jedisPubSub, String... patterns)
  {
    checkIsInMulti();
    connect();
    this.client.setTimeoutInfinite();
    jedisPubSub.proceedWithPatterns(this.client, patterns);
    this.client.rollbackTimeout();
  }
  
  protected static String[] getParams(List<String> keys, List<String> args)
  {
    int keyCount = keys.size();
    int argCount = args.size();
    
    String[] params = new String[keyCount + args.size()];
    for (int i = 0; i < keyCount; i++) {
      params[i] = ((String)keys.get(i));
    }
    for (int i = 0; i < argCount; i++) {
      params[(keyCount + i)] = ((String)args.get(i));
    }
    return params;
  }
  
  public Object eval(String script, List<String> keys, List<String> args)
  {
    return eval(script, keys.size(), getParams(keys, args));
  }
  
  public Object eval(String script)
  {
    return eval(script, 0, new String[0]);
  }
  
  public Object evalsha(String script)
  {
    return evalsha(script, 0, new String[0]);
  }
  
  private Object getEvalResult()
  {
    return evalResult(this.client.getOne());
  }
  
  private Object evalResult(Object result)
  {
    if ((result instanceof byte[])) {
      return SafeEncoder.encode((byte[])result);
    }
    if ((result instanceof List))
    {
      List<?> list = (List)result;
      List<Object> listResult = new ArrayList(list.size());
      for (Object bin : list) {
        listResult.add(evalResult(bin));
      }
      return listResult;
    }
    return result;
  }
  
  public Object evalsha(String sha1, List<String> keys, List<String> args)
  {
    return evalsha(sha1, keys.size(), getParams(keys, args));
  }
  
  public Object evalsha(String sha1, int keyCount, String... params)
  {
    checkIsInMulti();
    this.client.evalsha(sha1, keyCount, params);
    
    return getEvalResult();
  }
  
  public Boolean scriptExists(String sha1)
  {
    String[] a = new String[1];
    a[0] = sha1;
    return (Boolean)scriptExists(a).get(0);
  }
  
  public List<Boolean> scriptExists(String... sha1)
  {
    this.client.scriptExists(sha1);
    List<Long> result = this.client.getIntegerMultiBulkReply();
    List<Boolean> exists = new ArrayList();
    for (Long value : result) {
      exists.add(Boolean.valueOf(value.longValue() == 1L));
    }
    return exists;
  }
  
  public String scriptLoad(String script)
  {
    this.client.scriptLoad(script);
    return this.client.getBulkReply();
  }
  
  public List<Slowlog> slowlogGet()
  {
    this.client.slowlogGet();
    return Slowlog.from(this.client.getObjectMultiBulkReply());
  }
  
  public List<Slowlog> slowlogGet(long entries)
  {
    this.client.slowlogGet(entries);
    return Slowlog.from(this.client.getObjectMultiBulkReply());
  }
  
  public Long objectRefcount(String string)
  {
    this.client.objectRefcount(string);
    return this.client.getIntegerReply();
  }
  
  public String objectEncoding(String string)
  {
    this.client.objectEncoding(string);
    return this.client.getBulkReply();
  }
  
  public Long objectIdletime(String string)
  {
    this.client.objectIdletime(string);
    return this.client.getIntegerReply();
  }
  
  public Long bitcount(String key)
  {
    this.client.bitcount(key);
    return this.client.getIntegerReply();
  }
  
  public Long bitcount(String key, long start, long end)
  {
    this.client.bitcount(key, start, end);
    return this.client.getIntegerReply();
  }
  
  public Long bitop(BitOP op, String destKey, String... srcKeys)
  {
    this.client.bitop(op, destKey, srcKeys);
    return this.client.getIntegerReply();
  }
  
  public List<Map<String, String>> sentinelMasters()
  {
    this.client.sentinel(new String[] { "masters" });
    List<Object> reply = this.client.getObjectMultiBulkReply();
    
    List<Map<String, String>> masters = new ArrayList();
    for (Object obj : reply) {
      masters.add(BuilderFactory.STRING_MAP.build((List)obj));
    }
    return masters;
  }
  
  public List<String> sentinelGetMasterAddrByName(String masterName)
  {
    this.client.sentinel(new String[] { "get-master-addr-by-name", masterName });
    List<Object> reply = this.client.getObjectMultiBulkReply();
    return (List)BuilderFactory.STRING_LIST.build(reply);
  }
  
  public Long sentinelReset(String pattern)
  {
    this.client.sentinel(new String[] { "reset", pattern });
    return this.client.getIntegerReply();
  }
  
  public List<Map<String, String>> sentinelSlaves(String masterName)
  {
    this.client.sentinel(new String[] { "slaves", masterName });
    List<Object> reply = this.client.getObjectMultiBulkReply();
    
    List<Map<String, String>> slaves = new ArrayList();
    for (Object obj : reply) {
      slaves.add(BuilderFactory.STRING_MAP.build((List)obj));
    }
    return slaves;
  }
  
  public String sentinelFailover(String masterName)
  {
    this.client.sentinel(new String[] { "failover", masterName });
    return this.client.getStatusCodeReply();
  }
  
  public String sentinelMonitor(String masterName, String ip, int port, int quorum)
  {
    this.client.sentinel(new String[] { "monitor", masterName, ip, String.valueOf(port), String.valueOf(quorum) });
    
    return this.client.getStatusCodeReply();
  }
  
  public String sentinelRemove(String masterName)
  {
    this.client.sentinel(new String[] { "remove", masterName });
    return this.client.getStatusCodeReply();
  }
  
  public String sentinelSet(String masterName, Map<String, String> parameterMap)
  {
    int index = 0;
    int paramsLength = parameterMap.size() * 2 + 2;
    String[] params = new String[paramsLength];
    
    params[(index++)] = "set";
    params[(index++)] = masterName;
    for (Map.Entry<String, String> entry : parameterMap.entrySet())
    {
      params[(index++)] = ((String)entry.getKey());
      params[(index++)] = ((String)entry.getValue());
    }
    this.client.sentinel(params);
    return this.client.getStatusCodeReply();
  }
  
  public byte[] dump(String key)
  {
    checkIsInMulti();
    this.client.dump(key);
    return this.client.getBinaryBulkReply();
  }
  
  public String restore(String key, int ttl, byte[] serializedValue)
  {
    checkIsInMulti();
    this.client.restore(key, ttl, serializedValue);
    return this.client.getStatusCodeReply();
  }
  
  public Long pexpire(String key, int milliseconds)
  {
    checkIsInMulti();
    this.client.pexpire(key, milliseconds);
    return this.client.getIntegerReply();
  }
  
  public Long pexpireAt(String key, long millisecondsTimestamp)
  {
    checkIsInMulti();
    this.client.pexpireAt(key, millisecondsTimestamp);
    return this.client.getIntegerReply();
  }
  
  public Long pttl(String key)
  {
    checkIsInMulti();
    this.client.pttl(key);
    return this.client.getIntegerReply();
  }
  
  public Double incrByFloat(String key, double increment)
  {
    checkIsInMulti();
    this.client.incrByFloat(key, increment);
    String relpy = this.client.getBulkReply();
    return relpy != null ? new Double(relpy) : null;
  }
  
  public String psetex(String key, int milliseconds, String value)
  {
    checkIsInMulti();
    this.client.psetex(key, milliseconds, value);
    return this.client.getStatusCodeReply();
  }
  
  public String set(String key, String value, String nxxx)
  {
    checkIsInMulti();
    this.client.set(key, value, nxxx);
    return this.client.getStatusCodeReply();
  }
  
  public String set(String key, String value, String nxxx, String expx, int time)
  {
    checkIsInMulti();
    this.client.set(key, value, nxxx, expx, time);
    return this.client.getStatusCodeReply();
  }
  
  public String clientKill(String client)
  {
    checkIsInMulti();
    this.client.clientKill(client);
    return this.client.getStatusCodeReply();
  }
  
  public String clientSetname(String name)
  {
    checkIsInMulti();
    this.client.clientSetname(name);
    return this.client.getStatusCodeReply();
  }
  
  public String migrate(String host, int port, String key, int destinationDb, int timeout)
  {
    checkIsInMulti();
    this.client.migrate(host, port, key, destinationDb, timeout);
    return this.client.getStatusCodeReply();
  }
  
  public Double hincrByFloat(String key, String field, double increment)
  {
    checkIsInMulti();
    this.client.hincrByFloat(key, field, increment);
    String relpy = this.client.getBulkReply();
    return relpy != null ? new Double(relpy) : null;
  }
  
  @Deprecated
  public ScanResult<String> scan(int cursor)
  {
    return scan(cursor, new ScanParams());
  }
  
  @Deprecated
  public ScanResult<String> scan(int cursor, ScanParams params)
  {
    checkIsInMulti();
    this.client.scan(cursor, params);
    List<Object> result = this.client.getObjectMultiBulkReply();
    int newcursor = Integer.parseInt(new String((byte[])result.get(0)));
    List<String> results = new ArrayList();
    List<byte[]> rawResults = (List)result.get(1);
    for (byte[] bs : rawResults) {
      results.add(SafeEncoder.encode(bs));
    }
    return new ScanResult(newcursor, results);
  }
  
  @Deprecated
  public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor)
  {
    return hscan(key, cursor, new ScanParams());
  }
  
  @Deprecated
  public ScanResult<Map.Entry<String, String>> hscan(String key, int cursor, ScanParams params)
  {
    checkIsInMulti();
    this.client.hscan(key, cursor, params);
    List<Object> result = this.client.getObjectMultiBulkReply();
    int newcursor = Integer.parseInt(new String((byte[])result.get(0)));
    List<Map.Entry<String, String>> results = new ArrayList();
    List<byte[]> rawResults = (List)result.get(1);
    Iterator<byte[]> iterator = rawResults.iterator();
    while (iterator.hasNext()) {
      results.add(new AbstractMap.SimpleEntry(SafeEncoder.encode((byte[])iterator.next()), SafeEncoder.encode((byte[])iterator.next())));
    }
    return new ScanResult(newcursor, results);
  }
  
  @Deprecated
  public ScanResult<String> sscan(String key, int cursor)
  {
    return sscan(key, cursor, new ScanParams());
  }
  
  @Deprecated
  public ScanResult<String> sscan(String key, int cursor, ScanParams params)
  {
    checkIsInMulti();
    this.client.sscan(key, cursor, params);
    List<Object> result = this.client.getObjectMultiBulkReply();
    int newcursor = Integer.parseInt(new String((byte[])result.get(0)));
    List<String> results = new ArrayList();
    List<byte[]> rawResults = (List)result.get(1);
    for (byte[] bs : rawResults) {
      results.add(SafeEncoder.encode(bs));
    }
    return new ScanResult(newcursor, results);
  }
  
  @Deprecated
  public ScanResult<Tuple> zscan(String key, int cursor)
  {
    return zscan(key, cursor, new ScanParams());
  }
  
  @Deprecated
  public ScanResult<Tuple> zscan(String key, int cursor, ScanParams params)
  {
    checkIsInMulti();
    this.client.zscan(key, cursor, params);
    List<Object> result = this.client.getObjectMultiBulkReply();
    int newcursor = Integer.parseInt(new String((byte[])result.get(0)));
    List<Tuple> results = new ArrayList();
    List<byte[]> rawResults = (List)result.get(1);
    Iterator<byte[]> iterator = rawResults.iterator();
    while (iterator.hasNext()) {
      results.add(new Tuple(SafeEncoder.encode((byte[])iterator.next()), Double.valueOf(SafeEncoder.encode((byte[])iterator.next()))));
    }
    return new ScanResult(newcursor, results);
  }
  
  public ScanResult<String> scan(String cursor)
  {
    return scan(cursor, new ScanParams());
  }
  
  public ScanResult<String> scan(String cursor, ScanParams params)
  {
    checkIsInMulti();
    this.client.scan(cursor, params);
    List<Object> result = this.client.getObjectMultiBulkReply();
    String newcursor = new String((byte[])result.get(0));
    List<String> results = new ArrayList();
    List<byte[]> rawResults = (List)result.get(1);
    for (byte[] bs : rawResults) {
      results.add(SafeEncoder.encode(bs));
    }
    return new ScanResult(newcursor, results);
  }
  
  public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor)
  {
    return hscan(key, cursor, new ScanParams());
  }
  
  public ScanResult<Map.Entry<String, String>> hscan(String key, String cursor, ScanParams params)
  {
    checkIsInMulti();
    this.client.hscan(key, cursor, params);
    List<Object> result = this.client.getObjectMultiBulkReply();
    String newcursor = new String((byte[])result.get(0));
    List<Map.Entry<String, String>> results = new ArrayList();
    List<byte[]> rawResults = (List)result.get(1);
    Iterator<byte[]> iterator = rawResults.iterator();
    while (iterator.hasNext()) {
      results.add(new AbstractMap.SimpleEntry(SafeEncoder.encode((byte[])iterator.next()), SafeEncoder.encode((byte[])iterator.next())));
    }
    return new ScanResult(newcursor, results);
  }
  
  public ScanResult<String> sscan(String key, String cursor)
  {
    return sscan(key, cursor, new ScanParams());
  }
  
  public ScanResult<String> sscan(String key, String cursor, ScanParams params)
  {
    checkIsInMulti();
    this.client.sscan(key, cursor, params);
    List<Object> result = this.client.getObjectMultiBulkReply();
    String newcursor = new String((byte[])result.get(0));
    List<String> results = new ArrayList();
    List<byte[]> rawResults = (List)result.get(1);
    for (byte[] bs : rawResults) {
      results.add(SafeEncoder.encode(bs));
    }
    return new ScanResult(newcursor, results);
  }
  
  public ScanResult<Tuple> zscan(String key, String cursor)
  {
    return zscan(key, cursor, new ScanParams());
  }
  
  public ScanResult<Tuple> zscan(String key, String cursor, ScanParams params)
  {
    checkIsInMulti();
    this.client.zscan(key, cursor, params);
    List<Object> result = this.client.getObjectMultiBulkReply();
    String newcursor = new String((byte[])result.get(0));
    List<Tuple> results = new ArrayList();
    List<byte[]> rawResults = (List)result.get(1);
    Iterator<byte[]> iterator = rawResults.iterator();
    while (iterator.hasNext()) {
      results.add(new Tuple(SafeEncoder.encode((byte[])iterator.next()), Double.valueOf(SafeEncoder.encode((byte[])iterator.next()))));
    }
    return new ScanResult(newcursor, results);
  }
  
  public String clusterNodes()
  {
    checkIsInMulti();
    this.client.clusterNodes();
    return this.client.getBulkReply();
  }
  
  public String clusterMeet(String ip, int port)
  {
    checkIsInMulti();
    this.client.clusterMeet(ip, port);
    return this.client.getStatusCodeReply();
  }
  
  public String clusterAddSlots(int... slots)
  {
    checkIsInMulti();
    this.client.clusterAddSlots(slots);
    return this.client.getStatusCodeReply();
  }
  
  public String clusterDelSlots(int... slots)
  {
    checkIsInMulti();
    this.client.clusterDelSlots(slots);
    return this.client.getStatusCodeReply();
  }
  
  public String clusterInfo()
  {
    checkIsInMulti();
    this.client.clusterInfo();
    return this.client.getStatusCodeReply();
  }
  
  public List<String> clusterGetKeysInSlot(int slot, int count)
  {
    checkIsInMulti();
    this.client.clusterGetKeysInSlot(slot, count);
    return this.client.getMultiBulkReply();
  }
  
  public String clusterSetSlotNode(int slot, String nodeId)
  {
    checkIsInMulti();
    this.client.clusterSetSlotNode(slot, nodeId);
    return this.client.getStatusCodeReply();
  }
  
  public String clusterSetSlotMigrating(int slot, String nodeId)
  {
    checkIsInMulti();
    this.client.clusterSetSlotMigrating(slot, nodeId);
    return this.client.getStatusCodeReply();
  }
  
  public String clusterSetSlotImporting(int slot, String nodeId)
  {
    checkIsInMulti();
    this.client.clusterSetSlotImporting(slot, nodeId);
    return this.client.getStatusCodeReply();
  }
  
  public String asking()
  {
    checkIsInMulti();
    this.client.asking();
    return this.client.getStatusCodeReply();
  }
  
  public List<String> pubsubChannels(String pattern)
  {
    checkIsInMulti();
    this.client.pubsubChannels(pattern);
    return this.client.getMultiBulkReply();
  }
  
  public Long pubsubNumPat()
  {
    checkIsInMulti();
    this.client.pubsubNumPat();
    return this.client.getIntegerReply();
  }
  
  public Map<String, String> pubsubNumSub(String... channels)
  {
    checkIsInMulti();
    this.client.pubsubNumSub(channels);
    return (Map)BuilderFactory.STRING_MAP.build(this.client.getBinaryMultiBulkReply());
  }
}
