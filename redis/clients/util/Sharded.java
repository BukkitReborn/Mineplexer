package redis.clients.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sharded<R, S extends ShardInfo<R>>
{
  public static final int DEFAULT_WEIGHT = 1;
  private TreeMap<Long, S> nodes;
  private final Hashing algo;
  private final Map<ShardInfo<R>, R> resources = new LinkedHashMap();
  private Pattern tagPattern = null;
  public static final Pattern DEFAULT_KEY_TAG_PATTERN = Pattern.compile("\\{(.+?)\\}");
  
  public Sharded(List<S> shards)
  {
    this(shards, Hashing.MURMUR_HASH);
  }
  
  public Sharded(List<S> shards, Hashing algo)
  {
    this.algo = algo;
    initialize(shards);
  }
  
  public Sharded(List<S> shards, Pattern tagPattern)
  {
    this(shards, Hashing.MURMUR_HASH, tagPattern);
  }
  
  public Sharded(List<S> shards, Hashing algo, Pattern tagPattern)
  {
    this.algo = algo;
    this.tagPattern = tagPattern;
    initialize(shards);
  }
  
  private void initialize(List<S> shards)
  {
    this.nodes = new TreeMap();
    for (int i = 0; i != shards.size(); i++)
    {
      S shardInfo = (ShardInfo)shards.get(i);
      if (shardInfo.getName() == null) {
        for (int n = 0; n < 160 * shardInfo.getWeight(); n++) {
          this.nodes.put(Long.valueOf(this.algo.hash("SHARD-" + i + "-NODE-" + n)), shardInfo);
        }
      } else {
        for (int n = 0; n < 160 * shardInfo.getWeight(); n++) {
          this.nodes.put(Long.valueOf(this.algo.hash(shardInfo.getName() + "*" + shardInfo.getWeight() + n)), shardInfo);
        }
      }
      this.resources.put(shardInfo, shardInfo.createResource());
    }
  }
  
  public R getShard(byte[] key)
  {
    return (R)this.resources.get(getShardInfo(key));
  }
  
  public R getShard(String key)
  {
    return (R)this.resources.get(getShardInfo(key));
  }
  
  public S getShardInfo(byte[] key)
  {
    SortedMap<Long, S> tail = this.nodes.tailMap(Long.valueOf(this.algo.hash(key)));
    if (tail.isEmpty()) {
      return (ShardInfo)this.nodes.get(this.nodes.firstKey());
    }
    return (ShardInfo)tail.get(tail.firstKey());
  }
  
  public S getShardInfo(String key)
  {
    return getShardInfo(SafeEncoder.encode(getKeyTag(key)));
  }
  
  public String getKeyTag(String key)
  {
    if (this.tagPattern != null)
    {
      Matcher m = this.tagPattern.matcher(key);
      if (m.find()) {
        return m.group(1);
      }
    }
    return key;
  }
  
  public Collection<S> getAllShardInfo()
  {
    return Collections.unmodifiableCollection(this.nodes.values());
  }
  
  public Collection<R> getAllShards()
  {
    return Collections.unmodifiableCollection(this.resources.values());
  }
}
