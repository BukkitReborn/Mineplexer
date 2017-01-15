package redis.clients.jedis;

import java.util.Set;

public class JedisRandomConnectionHandler
  extends JedisClusterConnectionHandler
{
  public JedisRandomConnectionHandler(Set<HostAndPort> nodes)
  {
    super(nodes);
  }
  
  public Jedis getConnection()
  {
    return (Jedis)getRandomConnection().getResource();
  }
  
  Jedis getConnectionFromSlot(int slot)
  {
    return (Jedis)getRandomConnection().getResource();
  }
}
