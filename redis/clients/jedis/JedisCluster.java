package redis.clients.jedis;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class JedisCluster
  implements JedisCommands, BasicCommands
{
  public static final short HASHSLOTS = 16384;
  private static final int DEFAULT_TIMEOUT = 1;
  private static final int DEFAULT_MAX_REDIRECTIONS = 5;
  private int timeout;
  private int maxRedirections;
  private JedisClusterConnectionHandler connectionHandler;
  
  public JedisCluster(Set<HostAndPort> nodes, int timeout)
  {
    this(nodes, timeout, 5);
  }
  
  public JedisCluster(Set<HostAndPort> nodes)
  {
    this(nodes, 1);
  }
  
  public JedisCluster(Set<HostAndPort> jedisClusterNode, int timeout, int maxRedirections)
  {
    this.connectionHandler = new JedisSlotBasedConnectionHandler(jedisClusterNode);
    
    this.timeout = timeout;
    this.maxRedirections = maxRedirections;
  }
  
  public String set(final String key, final String value)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.set(key, value);
      }
    }.run(key);
  }
  
  public String get(final String key)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.get(key);
      }
    }.run(key);
  }
  
  public Boolean exists(final String key)
  {
    (Boolean)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Boolean execute(Jedis connection)
      {
        return connection.exists(key);
      }
    }.run(key);
  }
  
  public Long persist(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.persist(key);
      }
    }.run(key);
  }
  
  public String type(final String key)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.type(key);
      }
    }.run(key);
  }
  
  public Long expire(final String key, final int seconds)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.expire(key, seconds);
      }
    }.run(key);
  }
  
  public Long expireAt(final String key, final long unixTime)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.expireAt(key, unixTime);
      }
    }.run(key);
  }
  
  public Long ttl(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.ttl(key);
      }
    }.run(key);
  }
  
  public Boolean setbit(final String key, final long offset, boolean value)
  {
    (Boolean)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Boolean execute(Jedis connection)
      {
        return connection.setbit(key, offset, this.val$value);
      }
    }.run(key);
  }
  
  public Boolean setbit(final String key, final long offset, String value)
  {
    (Boolean)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Boolean execute(Jedis connection)
      {
        return connection.setbit(key, offset, this.val$value);
      }
    }.run(key);
  }
  
  public Boolean getbit(final String key, final long offset)
  {
    (Boolean)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Boolean execute(Jedis connection)
      {
        return connection.getbit(key, offset);
      }
    }.run(key);
  }
  
  public Long setrange(final String key, final long offset, String value)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.setrange(key, offset, this.val$value);
      }
    }.run(key);
  }
  
  public String getrange(final String key, final long startOffset, long endOffset)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.getrange(key, startOffset, this.val$endOffset);
      }
    }.run(key);
  }
  
  public String getSet(final String key, final String value)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.getSet(key, value);
      }
    }.run(key);
  }
  
  public Long setnx(final String key, final String value)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.setnx(key, value);
      }
    }.run(key);
  }
  
  public String setex(final String key, final int seconds, final String value)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.setex(key, seconds, value);
      }
    }.run(key);
  }
  
  public Long decrBy(final String key, final long integer)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.decrBy(key, integer);
      }
    }.run(key);
  }
  
  public Long decr(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.decr(key);
      }
    }.run(key);
  }
  
  public Long incrBy(final String key, final long integer)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.incrBy(key, integer);
      }
    }.run(key);
  }
  
  public Long incr(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.incr(key);
      }
    }.run(key);
  }
  
  public Long append(final String key, final String value)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.append(key, value);
      }
    }.run(key);
  }
  
  public String substr(final String key, final int start, final int end)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.substr(key, start, end);
      }
    }.run(key);
  }
  
  public Long hset(final String key, final String field, final String value)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.hset(key, field, value);
      }
    }.run(key);
  }
  
  public String hget(final String key, final String field)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.hget(key, field);
      }
    }.run(key);
  }
  
  public Long hsetnx(final String key, final String field, final String value)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.hsetnx(key, field, value);
      }
    }.run(key);
  }
  
  public String hmset(final String key, final Map<String, String> hash)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.hmset(key, hash);
      }
    }.run(key);
  }
  
  public List<String> hmget(final String key, final String... fields)
  {
    (List)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public List<String> execute(Jedis connection)
      {
        return connection.hmget(key, fields);
      }
    }.run(key);
  }
  
  public Long hincrBy(final String key, final String field, final long value)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.hincrBy(key, field, value);
      }
    }.run(key);
  }
  
  public Boolean hexists(final String key, final String field)
  {
    (Boolean)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Boolean execute(Jedis connection)
      {
        return connection.hexists(key, field);
      }
    }.run(key);
  }
  
  public Long hdel(final String key, final String... field)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.hdel(key, field);
      }
    }.run(key);
  }
  
  public Long hlen(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.hdel(key, new String[0]);
      }
    }.run(key);
  }
  
  public Set<String> hkeys(final String key)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.hkeys(key);
      }
    }.run(key);
  }
  
  public List<String> hvals(final String key)
  {
    (List)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public List<String> execute(Jedis connection)
      {
        return connection.hvals(key);
      }
    }.run(key);
  }
  
  public Map<String, String> hgetAll(final String key)
  {
    (Map)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Map<String, String> execute(Jedis connection)
      {
        return connection.hgetAll(key);
      }
    }.run(key);
  }
  
  public Long rpush(final String key, final String... string)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.rpush(key, string);
      }
    }.run(key);
  }
  
  public Long lpush(final String key, final String... string)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.lpush(key, string);
      }
    }.run(key);
  }
  
  public Long llen(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.llen(key);
      }
    }.run(key);
  }
  
  public List<String> lrange(final String key, final long start, long end)
  {
    (List)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public List<String> execute(Jedis connection)
      {
        return connection.lrange(key, start, this.val$end);
      }
    }.run(key);
  }
  
  public String ltrim(final String key, final long start, long end)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.ltrim(key, start, this.val$end);
      }
    }.run(key);
  }
  
  public String lindex(final String key, final long index)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.lindex(key, index);
      }
    }.run(key);
  }
  
  public String lset(final String key, final long index, String value)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.lset(key, index, this.val$value);
      }
    }.run(key);
  }
  
  public Long lrem(final String key, final long count, String value)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.lrem(key, count, this.val$value);
      }
    }.run(key);
  }
  
  public String lpop(final String key)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.lpop(key);
      }
    }.run(key);
  }
  
  public String rpop(final String key)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.rpop(key);
      }
    }.run(key);
  }
  
  public Long sadd(final String key, final String... member)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.sadd(key, member);
      }
    }.run(key);
  }
  
  public Set<String> smembers(final String key)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.smembers(key);
      }
    }.run(key);
  }
  
  public Long srem(final String key, final String... member)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.srem(key, member);
      }
    }.run(key);
  }
  
  public String spop(final String key)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.spop(key);
      }
    }.run(key);
  }
  
  public Long scard(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.scard(key);
      }
    }.run(key);
  }
  
  public Boolean sismember(final String key, final String member)
  {
    (Boolean)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Boolean execute(Jedis connection)
      {
        return connection.sismember(key, member);
      }
    }.run(key);
  }
  
  public String srandmember(final String key)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.srandmember(key);
      }
    }.run(key);
  }
  
  public Long strlen(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.strlen(key);
      }
    }.run(key);
  }
  
  public Long zadd(final String key, final double score, String member)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zadd(key, score, this.val$member);
      }
    }.run(key);
  }
  
  public Long zadd(final String key, final Map<String, Double> scoreMembers)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zadd(key, scoreMembers);
      }
    }.run(key);
  }
  
  public Set<String> zrange(final String key, final long start, long end)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrange(key, start, this.val$end);
      }
    }.run(key);
  }
  
  public Long zrem(final String key, final String... member)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zrem(key, member);
      }
    }.run(key);
  }
  
  public Double zincrby(final String key, final double score, String member)
  {
    (Double)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Double execute(Jedis connection)
      {
        return connection.zincrby(key, score, this.val$member);
      }
    }.run(key);
  }
  
  public Long zrank(final String key, final String member)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zrank(key, member);
      }
    }.run(key);
  }
  
  public Long zrevrank(final String key, final String member)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zrevrank(key, member);
      }
    }.run(key);
  }
  
  public Set<String> zrevrange(final String key, final long start, long end)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrevrange(key, start, this.val$end);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrangeWithScores(final String key, final long start, long end)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrangeWithScores(key, start, this.val$end);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrevrangeWithScores(final String key, final long start, long end)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrevrangeWithScores(key, start, this.val$end);
      }
    }.run(key);
  }
  
  public Long zcard(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zcard(key);
      }
    }.run(key);
  }
  
  public Double zscore(final String key, final String member)
  {
    (Double)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Double execute(Jedis connection)
      {
        return connection.zscore(key, member);
      }
    }.run(key);
  }
  
  public List<String> sort(final String key)
  {
    (List)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public List<String> execute(Jedis connection)
      {
        return connection.sort(key);
      }
    }.run(key);
  }
  
  public List<String> sort(final String key, final SortingParams sortingParameters)
  {
    (List)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public List<String> execute(Jedis connection)
      {
        return connection.sort(key, sortingParameters);
      }
    }.run(key);
  }
  
  public Long zcount(final String key, final double min, double max)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zcount(key, min, this.val$max);
      }
    }.run(key);
  }
  
  public Long zcount(final String key, final String min, final String max)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zcount(key, min, max);
      }
    }.run(key);
  }
  
  public Set<String> zrangeByScore(final String key, final double min, double max)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrangeByScore(key, min, this.val$max);
      }
    }.run(key);
  }
  
  public Set<String> zrangeByScore(final String key, final String min, final String max)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrangeByScore(key, min, max);
      }
    }.run(key);
  }
  
  public Set<String> zrevrangeByScore(final String key, double max, final double min)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrevrangeByScore(key, min, this.val$max);
      }
    }.run(key);
  }
  
  public Set<String> zrangeByScore(final String key, final double min, double max, final int offset, int count)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrangeByScore(key, min, offset, this.val$offset, this.val$count);
      }
    }.run(key);
  }
  
  public Set<String> zrevrangeByScore(final String key, final String max, final String min)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrevrangeByScore(key, min, max);
      }
    }.run(key);
  }
  
  public Set<String> zrangeByScore(final String key, final String min, final String max, final int offset, final int count)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrangeByScore(key, min, max, offset, count);
      }
    }.run(key);
  }
  
  public Set<String> zrevrangeByScore(final String key, double max, final double min, final int offset, int count)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrevrangeByScore(key, min, offset, this.val$offset, this.val$count);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, double max)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrangeByScoreWithScores(key, min, this.val$max);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(final String key, double max, final double min)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrevrangeByScoreWithScores(key, min, this.val$max);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrangeByScoreWithScores(final String key, final double min, double max, final int offset, int count)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrangeByScoreWithScores(key, min, offset, this.val$offset, this.val$count);
      }
    }.run(key);
  }
  
  public Set<String> zrevrangeByScore(final String key, final String max, final String min, final int offset, final int count)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<String> execute(Jedis connection)
      {
        return connection.zrevrangeByScore(key, min, max, offset, count);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrangeByScoreWithScores(key, min, max);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrevrangeByScoreWithScores(key, min, max);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrangeByScoreWithScores(final String key, final String min, final String max, final int offset, final int count)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrangeByScoreWithScores(key, min, max, offset, count);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(final String key, final double max, double min, final int offset, int count)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrevrangeByScoreWithScores(key, max, offset, this.val$offset, this.val$count);
      }
    }.run(key);
  }
  
  public Set<Tuple> zrevrangeByScoreWithScores(final String key, final String max, final String min, final int offset, final int count)
  {
    (Set)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Set<Tuple> execute(Jedis connection)
      {
        return connection.zrevrangeByScoreWithScores(key, max, min, offset, count);
      }
    }.run(key);
  }
  
  public Long zremrangeByRank(final String key, final long start, long end)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zremrangeByRank(key, start, this.val$end);
      }
    }.run(key);
  }
  
  public Long zremrangeByScore(final String key, final double start, double end)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zremrangeByScore(key, start, this.val$end);
      }
    }.run(key);
  }
  
  public Long zremrangeByScore(final String key, final String start, final String end)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.zremrangeByScore(key, start, end);
      }
    }.run(key);
  }
  
  public Long linsert(final String key, final BinaryClient.LIST_POSITION where, final String pivot, final String value)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.linsert(key, where, pivot, value);
      }
    }.run(key);
  }
  
  public Long lpushx(final String key, final String... string)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.lpushx(key, string);
      }
    }.run(key);
  }
  
  public Long rpushx(final String key, final String... string)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.rpushx(key, string);
      }
    }.run(key);
  }
  
  public List<String> blpop(final String arg)
  {
    (List)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public List<String> execute(Jedis connection)
      {
        return connection.blpop(arg);
      }
    }.run(null);
  }
  
  public List<String> brpop(final String arg)
  {
    (List)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public List<String> execute(Jedis connection)
      {
        return connection.brpop(arg);
      }
    }.run(null);
  }
  
  public Long del(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.del(key);
      }
    }.run(key);
  }
  
  public String echo(final String string)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.echo(string);
      }
    }.run(null);
  }
  
  public Long move(final String key, final int dbIndex)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.move(key, dbIndex);
      }
    }.run(key);
  }
  
  public Long bitcount(final String key)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.bitcount(key);
      }
    }.run(key);
  }
  
  public Long bitcount(final String key, final long start, long end)
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.bitcount(key, start, this.val$end);
      }
    }.run(key);
  }
  
  public String ping()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.ping();
      }
    }.run(null);
  }
  
  public String quit()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.quit();
      }
    }.run(null);
  }
  
  public String flushDB()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.flushDB();
      }
    }.run(null);
  }
  
  public Long dbSize()
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.dbSize();
      }
    }.run(null);
  }
  
  public String select(final int index)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.select(index);
      }
    }.run(null);
  }
  
  public String flushAll()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.flushAll();
      }
    }.run(null);
  }
  
  public String auth(final String password)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.auth(password);
      }
    }.run(null);
  }
  
  public String save()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.save();
      }
    }.run(null);
  }
  
  public String bgsave()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.bgsave();
      }
    }.run(null);
  }
  
  public String bgrewriteaof()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.bgrewriteaof();
      }
    }.run(null);
  }
  
  public Long lastsave()
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.lastsave();
      }
    }.run(null);
  }
  
  public String shutdown()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.shutdown();
      }
    }.run(null);
  }
  
  public String info()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.info();
      }
    }.run(null);
  }
  
  public String info(final String section)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.info(section);
      }
    }.run(null);
  }
  
  public String slaveof(final String host, final int port)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.slaveof(host, port);
      }
    }.run(null);
  }
  
  public String slaveofNoOne()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.slaveofNoOne();
      }
    }.run(null);
  }
  
  public Long getDB()
  {
    (Long)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public Long execute(Jedis connection)
      {
        return connection.getDB();
      }
    }.run(null);
  }
  
  public String debug(final DebugParams params)
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.debug(params);
      }
    }.run(null);
  }
  
  public String configResetStat()
  {
    (String)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public String execute(Jedis connection)
      {
        return connection.configResetStat();
      }
    }.run(null);
  }
  
  public Map<String, JedisPool> getClusterNodes()
  {
    return this.connectionHandler.getNodes();
  }
  
  public Long waitReplicas(int replicas, long timeout)
  {
    return null;
  }
  
  @Deprecated
  public ScanResult<Map.Entry<String, String>> hscan(final String key, final int cursor)
  {
    (ScanResult)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public ScanResult<Map.Entry<String, String>> execute(Jedis connection)
      {
        return connection.hscan(key, cursor);
      }
    }.run(null);
  }
  
  @Deprecated
  public ScanResult<String> sscan(final String key, final int cursor)
  {
    (ScanResult)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public ScanResult<String> execute(Jedis connection)
      {
        return connection.sscan(key, cursor);
      }
    }.run(null);
  }
  
  @Deprecated
  public ScanResult<Tuple> zscan(final String key, final int cursor)
  {
    (ScanResult)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public ScanResult<Tuple> execute(Jedis connection)
      {
        return connection.zscan(key, cursor);
      }
    }.run(null);
  }
  
  public ScanResult<Map.Entry<String, String>> hscan(final String key, final String cursor)
  {
    (ScanResult)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public ScanResult<Map.Entry<String, String>> execute(Jedis connection)
      {
        return connection.hscan(key, cursor);
      }
    }.run(null);
  }
  
  public ScanResult<String> sscan(final String key, final String cursor)
  {
    (ScanResult)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public ScanResult<String> execute(Jedis connection)
      {
        return connection.sscan(key, cursor);
      }
    }.run(null);
  }
  
  public ScanResult<Tuple> zscan(final String key, final String cursor)
  {
    (ScanResult)new JedisClusterCommand(this.connectionHandler, this.timeout, this.maxRedirections)
    {
      public ScanResult<Tuple> execute(Jedis connection)
      {
        return connection.zscan(key, cursor);
      }
    }.run(null);
  }
}
