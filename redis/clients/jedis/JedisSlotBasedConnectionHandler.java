package redis.clients.jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class JedisSlotBasedConnectionHandler
  extends JedisClusterConnectionHandler
{
  public JedisSlotBasedConnectionHandler(Set<HostAndPort> nodes)
  {
    super(nodes);
  }
  
  public Jedis getConnection()
  {
    List<JedisPool> pools = getShuffledNodesPool();
    for (JedisPool pool : pools)
    {
      Jedis jedis = null;
      try
      {
        jedis = (Jedis)pool.getResource();
        if (jedis != null)
        {
          String result = jedis.ping();
          if (result.equalsIgnoreCase("pong")) {
            return jedis;
          }
          pool.returnBrokenResource(jedis);
        }
      }
      catch (JedisConnectionException ex)
      {
        if (jedis != null) {
          pool.returnBrokenResource(jedis);
        }
      }
    }
    throw new JedisConnectionException("no reachable node in cluster");
  }
  
  public void assignSlotToNode(int slot, HostAndPort targetNode)
  {
    super.assignSlotToNode(slot, targetNode);
  }
  
  public Jedis getConnectionFromSlot(int slot)
  {
    JedisPool connectionPool = (JedisPool)this.slots.get(Integer.valueOf(slot));
    if (connectionPool != null) {
      return (Jedis)connectionPool.getResource();
    }
    return getConnection();
  }
  
  private List<JedisPool> getShuffledNodesPool()
  {
    List<JedisPool> pools = new ArrayList();
    pools.addAll(this.nodes.values());
    Collections.shuffle(pools);
    return pools;
  }
}
