package redis.clients.jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import redis.clients.util.SafeEncoder;

public class Client
  extends BinaryClient
  implements Commands
{
  public Client(String host)
  {
    super(host);
  }
  
  public Client(String host, int port)
  {
    super(host, port);
  }
  
  public void set(String key, String value)
  {
    set(SafeEncoder.encode(key), SafeEncoder.encode(value));
  }
  
  public void set(String key, String value, String nxxx, String expx, long time)
  {
    set(SafeEncoder.encode(key), SafeEncoder.encode(value), SafeEncoder.encode(nxxx), SafeEncoder.encode(expx), time);
  }
  
  public void get(String key)
  {
    get(SafeEncoder.encode(key));
  }
  
  public void exists(String key)
  {
    exists(SafeEncoder.encode(key));
  }
  
  public void del(String... keys)
  {
    byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < keys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    del(bkeys);
  }
  
  public void type(String key)
  {
    type(SafeEncoder.encode(key));
  }
  
  public void keys(String pattern)
  {
    keys(SafeEncoder.encode(pattern));
  }
  
  public void rename(String oldkey, String newkey)
  {
    rename(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
  }
  
  public void renamenx(String oldkey, String newkey)
  {
    renamenx(SafeEncoder.encode(oldkey), SafeEncoder.encode(newkey));
  }
  
  public void expire(String key, int seconds)
  {
    expire(SafeEncoder.encode(key), seconds);
  }
  
  public void expireAt(String key, long unixTime)
  {
    expireAt(SafeEncoder.encode(key), unixTime);
  }
  
  public void ttl(String key)
  {
    ttl(SafeEncoder.encode(key));
  }
  
  public void move(String key, int dbIndex)
  {
    move(SafeEncoder.encode(key), dbIndex);
  }
  
  public void getSet(String key, String value)
  {
    getSet(SafeEncoder.encode(key), SafeEncoder.encode(value));
  }
  
  public void mget(String... keys)
  {
    byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    mget(bkeys);
  }
  
  public void setnx(String key, String value)
  {
    setnx(SafeEncoder.encode(key), SafeEncoder.encode(value));
  }
  
  public void setex(String key, int seconds, String value)
  {
    setex(SafeEncoder.encode(key), seconds, SafeEncoder.encode(value));
  }
  
  public void mset(String... keysvalues)
  {
    byte[][] bkeysvalues = new byte[keysvalues.length][];
    for (int i = 0; i < keysvalues.length; i++) {
      bkeysvalues[i] = SafeEncoder.encode(keysvalues[i]);
    }
    mset(bkeysvalues);
  }
  
  public void msetnx(String... keysvalues)
  {
    byte[][] bkeysvalues = new byte[keysvalues.length][];
    for (int i = 0; i < keysvalues.length; i++) {
      bkeysvalues[i] = SafeEncoder.encode(keysvalues[i]);
    }
    msetnx(bkeysvalues);
  }
  
  public void decrBy(String key, long integer)
  {
    decrBy(SafeEncoder.encode(key), integer);
  }
  
  public void decr(String key)
  {
    decr(SafeEncoder.encode(key));
  }
  
  public void incrBy(String key, long integer)
  {
    incrBy(SafeEncoder.encode(key), integer);
  }
  
  public void incr(String key)
  {
    incr(SafeEncoder.encode(key));
  }
  
  public void append(String key, String value)
  {
    append(SafeEncoder.encode(key), SafeEncoder.encode(value));
  }
  
  public void substr(String key, int start, int end)
  {
    substr(SafeEncoder.encode(key), start, end);
  }
  
  public void hset(String key, String field, String value)
  {
    hset(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(value));
  }
  
  public void hget(String key, String field)
  {
    hget(SafeEncoder.encode(key), SafeEncoder.encode(field));
  }
  
  public void hsetnx(String key, String field, String value)
  {
    hsetnx(SafeEncoder.encode(key), SafeEncoder.encode(field), SafeEncoder.encode(value));
  }
  
  public void hmset(String key, Map<String, String> hash)
  {
    Map<byte[], byte[]> bhash = new HashMap(hash.size());
    for (Map.Entry<String, String> entry : hash.entrySet()) {
      bhash.put(SafeEncoder.encode((String)entry.getKey()), SafeEncoder.encode((String)entry.getValue()));
    }
    hmset(SafeEncoder.encode(key), bhash);
  }
  
  public void hmget(String key, String... fields)
  {
    byte[][] bfields = new byte[fields.length][];
    for (int i = 0; i < bfields.length; i++) {
      bfields[i] = SafeEncoder.encode(fields[i]);
    }
    hmget(SafeEncoder.encode(key), bfields);
  }
  
  public void hincrBy(String key, String field, long value)
  {
    hincrBy(SafeEncoder.encode(key), SafeEncoder.encode(field), value);
  }
  
  public void hexists(String key, String field)
  {
    hexists(SafeEncoder.encode(key), SafeEncoder.encode(field));
  }
  
  public void hdel(String key, String... fields)
  {
    hdel(SafeEncoder.encode(key), SafeEncoder.encodeMany(fields));
  }
  
  public void hlen(String key)
  {
    hlen(SafeEncoder.encode(key));
  }
  
  public void hkeys(String key)
  {
    hkeys(SafeEncoder.encode(key));
  }
  
  public void hvals(String key)
  {
    hvals(SafeEncoder.encode(key));
  }
  
  public void hgetAll(String key)
  {
    hgetAll(SafeEncoder.encode(key));
  }
  
  public void rpush(String key, String... string)
  {
    rpush(SafeEncoder.encode(key), SafeEncoder.encodeMany(string));
  }
  
  public void lpush(String key, String... string)
  {
    lpush(SafeEncoder.encode(key), SafeEncoder.encodeMany(string));
  }
  
  public void llen(String key)
  {
    llen(SafeEncoder.encode(key));
  }
  
  public void lrange(String key, long start, long end)
  {
    lrange(SafeEncoder.encode(key), start, end);
  }
  
  public void ltrim(String key, long start, long end)
  {
    ltrim(SafeEncoder.encode(key), start, end);
  }
  
  public void lindex(String key, long index)
  {
    lindex(SafeEncoder.encode(key), index);
  }
  
  public void lset(String key, long index, String value)
  {
    lset(SafeEncoder.encode(key), index, SafeEncoder.encode(value));
  }
  
  public void lrem(String key, long count, String value)
  {
    lrem(SafeEncoder.encode(key), count, SafeEncoder.encode(value));
  }
  
  public void lpop(String key)
  {
    lpop(SafeEncoder.encode(key));
  }
  
  public void rpop(String key)
  {
    rpop(SafeEncoder.encode(key));
  }
  
  public void rpoplpush(String srckey, String dstkey)
  {
    rpoplpush(SafeEncoder.encode(srckey), SafeEncoder.encode(dstkey));
  }
  
  public void sadd(String key, String... members)
  {
    sadd(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
  }
  
  public void smembers(String key)
  {
    smembers(SafeEncoder.encode(key));
  }
  
  public void srem(String key, String... members)
  {
    srem(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
  }
  
  public void spop(String key)
  {
    spop(SafeEncoder.encode(key));
  }
  
  public void smove(String srckey, String dstkey, String member)
  {
    smove(SafeEncoder.encode(srckey), SafeEncoder.encode(dstkey), SafeEncoder.encode(member));
  }
  
  public void scard(String key)
  {
    scard(SafeEncoder.encode(key));
  }
  
  public void sismember(String key, String member)
  {
    sismember(SafeEncoder.encode(key), SafeEncoder.encode(member));
  }
  
  public void sinter(String... keys)
  {
    byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sinter(bkeys);
  }
  
  public void sinterstore(String dstkey, String... keys)
  {
    byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sinterstore(SafeEncoder.encode(dstkey), bkeys);
  }
  
  public void sunion(String... keys)
  {
    byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sunion(bkeys);
  }
  
  public void sunionstore(String dstkey, String... keys)
  {
    byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sunionstore(SafeEncoder.encode(dstkey), bkeys);
  }
  
  public void sdiff(String... keys)
  {
    byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sdiff(bkeys);
  }
  
  public void sdiffstore(String dstkey, String... keys)
  {
    byte[][] bkeys = new byte[keys.length][];
    for (int i = 0; i < bkeys.length; i++) {
      bkeys[i] = SafeEncoder.encode(keys[i]);
    }
    sdiffstore(SafeEncoder.encode(dstkey), bkeys);
  }
  
  public void srandmember(String key)
  {
    srandmember(SafeEncoder.encode(key));
  }
  
  public void zadd(String key, double score, String member)
  {
    zadd(SafeEncoder.encode(key), score, SafeEncoder.encode(member));
  }
  
  public void zrange(String key, long start, long end)
  {
    zrange(SafeEncoder.encode(key), start, end);
  }
  
  public void zrem(String key, String... members)
  {
    zrem(SafeEncoder.encode(key), SafeEncoder.encodeMany(members));
  }
  
  public void zincrby(String key, double score, String member)
  {
    zincrby(SafeEncoder.encode(key), score, SafeEncoder.encode(member));
  }
  
  public void zrank(String key, String member)
  {
    zrank(SafeEncoder.encode(key), SafeEncoder.encode(member));
  }
  
  public void zrevrank(String key, String member)
  {
    zrevrank(SafeEncoder.encode(key), SafeEncoder.encode(member));
  }
  
  public void zrevrange(String key, long start, long end)
  {
    zrevrange(SafeEncoder.encode(key), start, end);
  }
  
  public void zrangeWithScores(String key, long start, long end)
  {
    zrangeWithScores(SafeEncoder.encode(key), start, end);
  }
  
  public void zrevrangeWithScores(String key, long start, long end)
  {
    zrevrangeWithScores(SafeEncoder.encode(key), start, end);
  }
  
  public void zcard(String key)
  {
    zcard(SafeEncoder.encode(key));
  }
  
  public void zscore(String key, String member)
  {
    zscore(SafeEncoder.encode(key), SafeEncoder.encode(member));
  }
  
  public void watch(String... keys)
  {
    byte[][] bargs = new byte[keys.length][];
    for (int i = 0; i < bargs.length; i++) {
      bargs[i] = SafeEncoder.encode(keys[i]);
    }
    watch(bargs);
  }
  
  public void sort(String key)
  {
    sort(SafeEncoder.encode(key));
  }
  
  public void sort(String key, SortingParams sortingParameters)
  {
    sort(SafeEncoder.encode(key), sortingParameters);
  }
  
  public void blpop(String[] args)
  {
    byte[][] bargs = new byte[args.length][];
    for (int i = 0; i < bargs.length; i++) {
      bargs[i] = SafeEncoder.encode(args[i]);
    }
    blpop(bargs);
  }
  
  public void blpop(int timeout, String... keys)
  {
    List<String> args = new ArrayList();
    for (String arg : keys) {
      args.add(arg);
    }
    args.add(String.valueOf(timeout));
    blpop((String[])args.toArray(new String[args.size()]));
  }
  
  public void sort(String key, SortingParams sortingParameters, String dstkey)
  {
    sort(SafeEncoder.encode(key), sortingParameters, SafeEncoder.encode(dstkey));
  }
  
  public void sort(String key, String dstkey)
  {
    sort(SafeEncoder.encode(key), SafeEncoder.encode(dstkey));
  }
  
  public void brpop(String[] args)
  {
    byte[][] bargs = new byte[args.length][];
    for (int i = 0; i < bargs.length; i++) {
      bargs[i] = SafeEncoder.encode(args[i]);
    }
    brpop(bargs);
  }
  
  public void brpop(int timeout, String... keys)
  {
    List<String> args = new ArrayList();
    for (String arg : keys) {
      args.add(arg);
    }
    args.add(String.valueOf(timeout));
    brpop((String[])args.toArray(new String[args.size()]));
  }
  
  public void zcount(String key, double min, double max)
  {
    zcount(SafeEncoder.encode(key), Protocol.toByteArray(min), Protocol.toByteArray(max));
  }
  
  public void zcount(String key, String min, String max)
  {
    zcount(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
  }
  
  public void zrangeByScore(String key, double min, double max)
  {
    zrangeByScore(SafeEncoder.encode(key), Protocol.toByteArray(min), Protocol.toByteArray(max));
  }
  
  public void zrangeByScore(String key, String min, String max)
  {
    zrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
  }
  
  public void zrangeByScore(String key, double min, double max, int offset, int count)
  {
    zrangeByScore(SafeEncoder.encode(key), Protocol.toByteArray(min), Protocol.toByteArray(max), offset, count);
  }
  
  public void zrangeByScoreWithScores(String key, double min, double max)
  {
    zrangeByScoreWithScores(SafeEncoder.encode(key), Protocol.toByteArray(min), Protocol.toByteArray(max));
  }
  
  public void zrangeByScoreWithScores(String key, double min, double max, int offset, int count)
  {
    zrangeByScoreWithScores(SafeEncoder.encode(key), Protocol.toByteArray(min), Protocol.toByteArray(max), offset, count);
  }
  
  public void zrevrangeByScore(String key, double max, double min)
  {
    zrevrangeByScore(SafeEncoder.encode(key), Protocol.toByteArray(max), Protocol.toByteArray(min));
  }
  
  public void zrangeByScore(String key, String min, String max, int offset, int count)
  {
    zrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max), offset, count);
  }
  
  public void zrangeByScoreWithScores(String key, String min, String max)
  {
    zrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max));
  }
  
  public void zrangeByScoreWithScores(String key, String min, String max, int offset, int count)
  {
    zrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(min), SafeEncoder.encode(max), offset, count);
  }
  
  public void zrevrangeByScore(String key, String max, String min)
  {
    zrevrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min));
  }
  
  public void zrevrangeByScore(String key, double max, double min, int offset, int count)
  {
    zrevrangeByScore(SafeEncoder.encode(key), Protocol.toByteArray(max), Protocol.toByteArray(min), offset, count);
  }
  
  public void zrevrangeByScore(String key, String max, String min, int offset, int count)
  {
    zrevrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min), offset, count);
  }
  
  public void zrevrangeByScoreWithScores(String key, double max, double min)
  {
    zrevrangeByScoreWithScores(SafeEncoder.encode(key), Protocol.toByteArray(max), Protocol.toByteArray(min));
  }
  
  public void zrevrangeByScoreWithScores(String key, String max, String min)
  {
    zrevrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min));
  }
  
  public void zrevrangeByScoreWithScores(String key, double max, double min, int offset, int count)
  {
    zrevrangeByScoreWithScores(SafeEncoder.encode(key), Protocol.toByteArray(max), Protocol.toByteArray(min), offset, count);
  }
  
  public void zrevrangeByScoreWithScores(String key, String max, String min, int offset, int count)
  {
    zrevrangeByScoreWithScores(SafeEncoder.encode(key), SafeEncoder.encode(max), SafeEncoder.encode(min), offset, count);
  }
  
  public void zremrangeByRank(String key, long start, long end)
  {
    zremrangeByRank(SafeEncoder.encode(key), start, end);
  }
  
  public void zremrangeByScore(String key, double start, double end)
  {
    zremrangeByScore(SafeEncoder.encode(key), Protocol.toByteArray(start), Protocol.toByteArray(end));
  }
  
  public void zremrangeByScore(String key, String start, String end)
  {
    zremrangeByScore(SafeEncoder.encode(key), SafeEncoder.encode(start), SafeEncoder.encode(end));
  }
  
  public void zunionstore(String dstkey, String... sets)
  {
    byte[][] bsets = new byte[sets.length][];
    for (int i = 0; i < bsets.length; i++) {
      bsets[i] = SafeEncoder.encode(sets[i]);
    }
    zunionstore(SafeEncoder.encode(dstkey), bsets);
  }
  
  public void zunionstore(String dstkey, ZParams params, String... sets)
  {
    byte[][] bsets = new byte[sets.length][];
    for (int i = 0; i < bsets.length; i++) {
      bsets[i] = SafeEncoder.encode(sets[i]);
    }
    zunionstore(SafeEncoder.encode(dstkey), params, bsets);
  }
  
  public void zinterstore(String dstkey, String... sets)
  {
    byte[][] bsets = new byte[sets.length][];
    for (int i = 0; i < bsets.length; i++) {
      bsets[i] = SafeEncoder.encode(sets[i]);
    }
    zinterstore(SafeEncoder.encode(dstkey), bsets);
  }
  
  public void zinterstore(String dstkey, ZParams params, String... sets)
  {
    byte[][] bsets = new byte[sets.length][];
    for (int i = 0; i < bsets.length; i++) {
      bsets[i] = SafeEncoder.encode(sets[i]);
    }
    zinterstore(SafeEncoder.encode(dstkey), params, bsets);
  }
  
  public void strlen(String key)
  {
    strlen(SafeEncoder.encode(key));
  }
  
  public void lpushx(String key, String... string)
  {
    lpushx(SafeEncoder.encode(key), getByteParams(string));
  }
  
  public void persist(String key)
  {
    persist(SafeEncoder.encode(key));
  }
  
  public void rpushx(String key, String... string)
  {
    rpushx(SafeEncoder.encode(key), getByteParams(string));
  }
  
  public void echo(String string)
  {
    echo(SafeEncoder.encode(string));
  }
  
  public void linsert(String key, BinaryClient.LIST_POSITION where, String pivot, String value)
  {
    linsert(SafeEncoder.encode(key), where, SafeEncoder.encode(pivot), SafeEncoder.encode(value));
  }
  
  public void brpoplpush(String source, String destination, int timeout)
  {
    brpoplpush(SafeEncoder.encode(source), SafeEncoder.encode(destination), timeout);
  }
  
  public void setbit(String key, long offset, boolean value)
  {
    setbit(SafeEncoder.encode(key), offset, value);
  }
  
  public void setbit(String key, long offset, String value)
  {
    setbit(SafeEncoder.encode(key), offset, SafeEncoder.encode(value));
  }
  
  public void getbit(String key, long offset)
  {
    getbit(SafeEncoder.encode(key), offset);
  }
  
  public void setrange(String key, long offset, String value)
  {
    setrange(SafeEncoder.encode(key), offset, SafeEncoder.encode(value));
  }
  
  public void getrange(String key, long startOffset, long endOffset)
  {
    getrange(SafeEncoder.encode(key), startOffset, endOffset);
  }
  
  public void publish(String channel, String message)
  {
    publish(SafeEncoder.encode(channel), SafeEncoder.encode(message));
  }
  
  public void unsubscribe(String... channels)
  {
    byte[][] cs = new byte[channels.length][];
    for (int i = 0; i < cs.length; i++) {
      cs[i] = SafeEncoder.encode(channels[i]);
    }
    unsubscribe(cs);
  }
  
  public void psubscribe(String... patterns)
  {
    byte[][] ps = new byte[patterns.length][];
    for (int i = 0; i < ps.length; i++) {
      ps[i] = SafeEncoder.encode(patterns[i]);
    }
    psubscribe(ps);
  }
  
  public void punsubscribe(String... patterns)
  {
    byte[][] ps = new byte[patterns.length][];
    for (int i = 0; i < ps.length; i++) {
      ps[i] = SafeEncoder.encode(patterns[i]);
    }
    punsubscribe(ps);
  }
  
  public void subscribe(String... channels)
  {
    byte[][] cs = new byte[channels.length][];
    for (int i = 0; i < cs.length; i++) {
      cs[i] = SafeEncoder.encode(channels[i]);
    }
    subscribe(cs);
  }
  
  public void pubsubChannels(String pattern)
  {
    pubsub("channels", new String[] { pattern });
  }
  
  public void pubsubNumPat()
  {
    pubsub("numpat", new String[0]);
  }
  
  public void pubsubNumSub(String... channels)
  {
    pubsub("numsub", channels);
  }
  
  public void configSet(String parameter, String value)
  {
    configSet(SafeEncoder.encode(parameter), SafeEncoder.encode(value));
  }
  
  public void configGet(String pattern)
  {
    configGet(SafeEncoder.encode(pattern));
  }
  
  private byte[][] getByteParams(String... params)
  {
    byte[][] p = new byte[params.length][];
    for (int i = 0; i < params.length; i++) {
      p[i] = SafeEncoder.encode(params[i]);
    }
    return p;
  }
  
  public void eval(String script, int keyCount, String... params)
  {
    eval(SafeEncoder.encode(script), Protocol.toByteArray(keyCount), getByteParams(params));
  }
  
  public void evalsha(String sha1, int keyCount, String... params)
  {
    evalsha(SafeEncoder.encode(sha1), Protocol.toByteArray(keyCount), getByteParams(params));
  }
  
  public void scriptExists(String... sha1)
  {
    byte[][] bsha1 = new byte[sha1.length][];
    for (int i = 0; i < bsha1.length; i++) {
      bsha1[i] = SafeEncoder.encode(sha1[i]);
    }
    scriptExists(bsha1);
  }
  
  public void scriptLoad(String script)
  {
    scriptLoad(SafeEncoder.encode(script));
  }
  
  public void zadd(String key, Map<String, Double> scoreMembers)
  {
    HashMap<byte[], Double> binaryScoreMembers = new HashMap();
    for (Map.Entry<String, Double> entry : scoreMembers.entrySet()) {
      binaryScoreMembers.put(SafeEncoder.encode((String)entry.getKey()), entry.getValue());
    }
    zaddBinary(SafeEncoder.encode(key), binaryScoreMembers);
  }
  
  public void objectRefcount(String key)
  {
    objectRefcount(SafeEncoder.encode(key));
  }
  
  public void objectIdletime(String key)
  {
    objectIdletime(SafeEncoder.encode(key));
  }
  
  public void objectEncoding(String key)
  {
    objectEncoding(SafeEncoder.encode(key));
  }
  
  public void bitcount(String key)
  {
    bitcount(SafeEncoder.encode(key));
  }
  
  public void bitcount(String key, long start, long end)
  {
    bitcount(SafeEncoder.encode(key), start, end);
  }
  
  public void bitop(BitOP op, String destKey, String... srcKeys)
  {
    bitop(op, SafeEncoder.encode(destKey), getByteParams(srcKeys));
  }
  
  public void sentinel(String... args)
  {
    byte[][] arg = new byte[args.length][];
    for (int i = 0; i < arg.length; i++) {
      arg[i] = SafeEncoder.encode(args[i]);
    }
    sentinel(arg);
  }
  
  public void dump(String key)
  {
    dump(SafeEncoder.encode(key));
  }
  
  public void restore(String key, int ttl, byte[] serializedValue)
  {
    restore(SafeEncoder.encode(key), ttl, serializedValue);
  }
  
  public void pexpire(String key, int milliseconds)
  {
    pexpire(SafeEncoder.encode(key), milliseconds);
  }
  
  public void pexpireAt(String key, long millisecondsTimestamp)
  {
    pexpireAt(SafeEncoder.encode(key), millisecondsTimestamp);
  }
  
  public void pttl(String key)
  {
    pttl(SafeEncoder.encode(key));
  }
  
  public void incrByFloat(String key, double increment)
  {
    incrByFloat(SafeEncoder.encode(key), increment);
  }
  
  public void psetex(String key, int milliseconds, String value)
  {
    psetex(SafeEncoder.encode(key), milliseconds, SafeEncoder.encode(value));
  }
  
  public void set(String key, String value, String nxxx)
  {
    set(SafeEncoder.encode(key), SafeEncoder.encode(value), SafeEncoder.encode(nxxx));
  }
  
  public void set(String key, String value, String nxxx, String expx, int time)
  {
    set(SafeEncoder.encode(key), SafeEncoder.encode(value), SafeEncoder.encode(nxxx), SafeEncoder.encode(expx), time);
  }
  
  public void srandmember(String key, int count)
  {
    srandmember(SafeEncoder.encode(key), count);
  }
  
  public void clientKill(String client)
  {
    clientKill(SafeEncoder.encode(client));
  }
  
  public void clientSetname(String name)
  {
    clientSetname(SafeEncoder.encode(name));
  }
  
  public void migrate(String host, int port, String key, int destinationDb, int timeout)
  {
    migrate(SafeEncoder.encode(host), port, SafeEncoder.encode(key), destinationDb, timeout);
  }
  
  public void hincrByFloat(String key, String field, double increment)
  {
    hincrByFloat(SafeEncoder.encode(key), SafeEncoder.encode(field), increment);
  }
  
  @Deprecated
  public void hscan(String key, int cursor, ScanParams params)
  {
    hscan(SafeEncoder.encode(key), cursor, params);
  }
  
  @Deprecated
  public void sscan(String key, int cursor, ScanParams params)
  {
    sscan(SafeEncoder.encode(key), cursor, params);
  }
  
  @Deprecated
  public void zscan(String key, int cursor, ScanParams params)
  {
    zscan(SafeEncoder.encode(key), cursor, params);
  }
  
  public void scan(String cursor, ScanParams params)
  {
    scan(SafeEncoder.encode(cursor), params);
  }
  
  public void hscan(String key, String cursor, ScanParams params)
  {
    hscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
  }
  
  public void sscan(String key, String cursor, ScanParams params)
  {
    sscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
  }
  
  public void zscan(String key, String cursor, ScanParams params)
  {
    zscan(SafeEncoder.encode(key), SafeEncoder.encode(cursor), params);
  }
  
  public void cluster(String subcommand, int... args)
  {
    byte[][] arg = new byte[args.length + 1][];
    for (int i = 1; i < arg.length; i++) {
      arg[i] = Protocol.toByteArray(args[(i - 1)]);
    }
    arg[0] = SafeEncoder.encode(subcommand);
    cluster(arg);
  }
  
  public void pubsub(String subcommand, String... args)
  {
    byte[][] arg = new byte[args.length + 1][];
    for (int i = 1; i < arg.length; i++) {
      arg[i] = SafeEncoder.encode(args[(i - 1)]);
    }
    arg[0] = SafeEncoder.encode(subcommand);
    pubsub(arg);
  }
  
  public void cluster(String subcommand, String... args)
  {
    byte[][] arg = new byte[args.length + 1][];
    for (int i = 1; i < arg.length; i++) {
      arg[i] = SafeEncoder.encode(args[(i - 1)]);
    }
    arg[0] = SafeEncoder.encode(subcommand);
    cluster(arg);
  }
  
  public void cluster(String subcommand)
  {
    byte[][] arg = new byte[1][];
    arg[0] = SafeEncoder.encode(subcommand);
    cluster(arg);
  }
  
  public void clusterNodes()
  {
    cluster("nodes");
  }
  
  public void clusterMeet(String ip, int port)
  {
    cluster("meet", new String[] { ip, String.valueOf(port) });
  }
  
  public void clusterAddSlots(int... slots)
  {
    cluster("addslots", slots);
  }
  
  public void clusterDelSlots(int... slots)
  {
    cluster("delslots", slots);
  }
  
  public void clusterInfo()
  {
    cluster("info");
  }
  
  public void clusterGetKeysInSlot(int slot, int count)
  {
    int[] args = { slot, count };
    cluster("getkeysinslot", args);
  }
  
  public void clusterSetSlotNode(int slot, String nodeId)
  {
    cluster("setslot", new String[] { String.valueOf(slot), "node", nodeId });
  }
  
  public void clusterSetSlotMigrating(int slot, String nodeId)
  {
    cluster("setslot", new String[] { String.valueOf(slot), "migrating", nodeId });
  }
  
  public void clusterSetSlotImporting(int slot, String nodeId)
  {
    cluster("setslot", new String[] { String.valueOf(slot), "importing", nodeId });
  }
}
