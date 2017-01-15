package redis.clients.jedis;

import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.util.Hashing;
import redis.clients.util.Pool;

public class ShardedJedisPool
  extends Pool<ShardedJedis>
{
  public ShardedJedisPool(GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards)
  {
    this(poolConfig, shards, Hashing.MURMUR_HASH);
  }
  
  public ShardedJedisPool(GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards, Hashing algo)
  {
    this(poolConfig, shards, algo, null);
  }
  
  public ShardedJedisPool(GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards, Pattern keyTagPattern)
  {
    this(poolConfig, shards, Hashing.MURMUR_HASH, keyTagPattern);
  }
  
  public ShardedJedisPool(GenericObjectPoolConfig poolConfig, List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern)
  {
    super(poolConfig, new ShardedJedisFactory(shards, algo, keyTagPattern));
  }
  
  private static class ShardedJedisFactory
    implements PooledObjectFactory<ShardedJedis>
  {
    private List<JedisShardInfo> shards;
    private Hashing algo;
    private Pattern keyTagPattern;
    
    public ShardedJedisFactory(List<JedisShardInfo> shards, Hashing algo, Pattern keyTagPattern)
    {
      this.shards = shards;
      this.algo = algo;
      this.keyTagPattern = keyTagPattern;
    }
    
    public PooledObject<ShardedJedis> makeObject()
      throws Exception
    {
      ShardedJedis jedis = new ShardedJedis(this.shards, this.algo, this.keyTagPattern);
      return new DefaultPooledObject(jedis);
    }
    
    public void destroyObject(PooledObject<ShardedJedis> pooledShardedJedis)
      throws Exception
    {
      ShardedJedis shardedJedis = (ShardedJedis)pooledShardedJedis.getObject();
      for (Jedis jedis : shardedJedis.getAllShards()) {
        try
        {
          try
          {
            jedis.quit();
          }
          catch (Exception e) {}
          jedis.disconnect();
        }
        catch (Exception e) {}
      }
    }
    
    public boolean validateObject(PooledObject<ShardedJedis> pooledShardedJedis)
    {
      try
      {
        ShardedJedis jedis = (ShardedJedis)pooledShardedJedis.getObject();
        for (Jedis shard : jedis.getAllShards()) {
          if (!shard.ping().equals("PONG")) {
            return false;
          }
        }
        return true;
      }
      catch (Exception ex) {}
      return false;
    }
    
    public void activateObject(PooledObject<ShardedJedis> p)
      throws Exception
    {}
    
    public void passivateObject(PooledObject<ShardedJedis> p)
      throws Exception
    {}
  }
}
